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
 * Implementação real usando Playwright para abrir um navegador invisível
 * e extrair os dados, burlando a proteção do Mercado Livre.
 */
public class ExtratorMercadoLivre implements IExtratorEcommerce {

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

            page.waitForSelector(".poly-card, .ui-search-layout__item");

            Locator itens = page.locator(".poly-card, .ui-search-layout__item");
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

    private String formatarTermo(String str) {
        String semAcentos = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return semAcentos.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}