package framework;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorMercadoLivre implements IExtratorEcommerce {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

    @Override
    public List<Produto> buscarItens(String termoBusca) {
        List<Produto> listaProdutos = new ArrayList<>();
        
        try {
            String termoFormatado = formatarTermo(termoBusca);
            String url = "https://lista.mercadolivre.com.br/" + termoFormatado;
            
            System.out.println("-> Conectando na URL: " + url);

            // Adicionamos headers, removendo o "Connection" que causava erro de header restrito
            Document doc = Jsoup.connect(url)
                                .userAgent(USER_AGENT)
                                .header("Accept-Language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                                .timeout(20000)
                                .get();

            Elements itensHtml = doc.select("li.ui-search-layout__item, div.poly-card");

            if (itensHtml.isEmpty()) {
                String bodyText = doc.body().text();
                // Proteção contra StringIndexOutOfBounds
                int fim = Math.min(bodyText.length(), 500);
                System.out.println("-> AVISO: Nenhum item encontrado. Resposta do site:");
                System.out.println(bodyText.substring(0, fim));
                return listaProdutos;
            }

            System.out.println("-> Encontrados " + itensHtml.size() + " itens. Extraindo...");

            for (Element item : itensHtml) {
                try {
                    Element elemTitulo = item.select("h2.ui-search-item__title, a.poly-component__title").first();
                    if (elemTitulo == null) continue;
                    
                    String titulo = elemTitulo.text();
                    String link = elemTitulo.attr("href"); 
                    
                    Element precoElement = item.select("div.ui-search-price__second-line span.andes-money-amount__fraction, div.poly-price__current span.andes-money-amount__fraction").first();
                    double preco = (precoElement != null) ? Double.parseDouble(precoElement.text().replace(".", "")) : 0.0;
                    
                    Element vendedorElement = item.select("p.ui-search-official-store-label, span.poly-component__seller").first();
                    String vendedor = (vendedorElement != null) ? vendedorElement.text() : "Desconhecido";

                    listaProdutos.add(new Produto(titulo, preco, vendedor, link));
                    
                } catch (Exception e) {
                    // Ignora itens problemáticos
                }
            }

        } catch (IOException e) {
            System.err.println("Erro de conexão (pode ser bloqueio de IP): " + e.getMessage());
        }

        return listaProdutos;
    }

    private String formatarTermo(String str) {
        String semAcentos = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return semAcentos.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}