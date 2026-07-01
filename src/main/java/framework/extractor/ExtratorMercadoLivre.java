package framework.extractor;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import framework.model.Produto;

/**
 * Robô de extração feito especificamente para o Mercado Livre.
 * Ele usa a ferramenta Playwright para abrir um navegador de verdade,
 * imitando um humano.
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com ) 
 */
public class ExtratorMercadoLivre implements IExtratorEcommerce {

	/**
     * Abre o navegador, acessa a página do Mercado Livre e copia os dados
     * de todos os anúncios que aparecerem na tela.
     * 
     * @param termoBusca O nome do produto que você quer pesquisar .
     * @return Uma lista cheia de produtos encontrados. Se der erro ou não achar nada, devolve uma lista vazia.
     */
	
    @Override
    public List<Produto> buscarItens(String termoBusca) {
        List<Produto> listaProdutos = new ArrayList<>();
        String termoFormatado = formatarTermo(termoBusca);
        String url = "https://lista.mercadolivre.com.br/" + termoFormatado;

        System.out.println("-> Iniciando navegador Playwright para acessar: " + url);

        try (Playwright playwright = Playwright.create()) {
            
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate(url);

            page.waitForSelector(".poly-card");

            Locator itens = page.locator(".poly-card");
            System.out.println("-> Encontrados " + itens.count() + " itens. Lendo dados...");

            for (int i = 0; i < itens.count(); i++) {
                Locator item = itens.nth(i);
                
                try {
                    Locator tituloLoc = item.locator("h2.ui-search-item__title, a.poly-component__title").first();
                    String titulo = tituloLoc.innerText();
                    String link = tituloLoc.getAttribute("href");

                    Locator precoLoc = item.locator("div.poly-price__current span.andes-money-amount__fraction, div.ui-search-price__second-line span.andes-money-amount__fraction").first();
                    double preco = Double.parseDouble(precoLoc.innerText().replace(".", ""));

                    Locator vendedorLoc = item.locator("span.poly-component__seller, p.ui-search-official-store-label").first();
                    String vendedor = vendedorLoc.count() > 0 ? vendedorLoc.innerText() : "Vendedor Desconhecido";

                    Locator avaliacaoLoc = item.locator("span.polylabel-label").first();
                    double avaliacao = avaliacaoLoc.count() > 0 && avaliacaoLoc.innerText().matches(".*\\d\\.\\d.*") 
                                       ? Double.parseDouble(avaliacaoLoc.innerText()) 
                                       : 0.0;

                    listaProdutos.add(new Produto(titulo, preco, vendedor, link, avaliacao));

                } catch (Exception e) {
                    
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao rodar o Playwright: " + e.getMessage());
        }

        return listaProdutos;
    }
    
    /**
     * Limpa o texto que o usuário digitou para conseguirmos montar um link de internet perfeito.
     * Ele tira os acentos, letras maiúsculas e troca os espaços por traços.
     * 
     * @param str O texto original (ex: "Vaio Fe 16").
     * @return O texto arrumado para virar link (ex: "vaio-fe-16").
     */
    
    private String formatarTermo(String str) {
        String semAcentos = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return semAcentos.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}