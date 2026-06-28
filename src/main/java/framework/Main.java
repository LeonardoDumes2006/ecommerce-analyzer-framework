package framework;

import java.util.List;

import framework.factory.ExtratorFactory;
import framework.enums.Ecommerce;
import framework.extractor.ExtratorMercadoLivre;
import framework.extractor.IExtratorEcommerce;
import framework.model.Produto;
import framework.service.Estatisticas;
import framework.service.ServicoAnalisador;

public class Main {

    public static void main(String[] args) {

        String termoBusca = "Vaio Fe 16 ";

        
        IExtratorEcommerce extrator = ExtratorFactory.criar(Ecommerce.MERCADO_LIVRE);
        // Injeção de Dependência
       
        // sem o factory
        //IExtratorEcommerce extrator = new ExtratorMercadoLivre();

        // Serviço recebe a dependência por construtor
        ServicoAnalisador analisador = new ServicoAnalisador(extrator);

        System.out.println("==================================");
        System.out.println(" BUSCA: " + termoBusca);
        System.out.println("==================================");

        Estatisticas resultado = analisador.gerarAnalise(termoBusca);

        resultado.imprimirResumo();

        resultado.exibirListagemCompleta();

        Produto maisBarato = resultado.getProdutoMaisBarato();

        if (maisBarato != null) {
            System.out.println("\n=== PRODUTO MAIS BARATO ===");
            System.out.println("Nome: " + maisBarato.getNome());
            System.out.println("Preço: R$ " + maisBarato.getPreco());
            System.out.println("Link: " + maisBarato.getLink());
        }

        Produto melhorAvaliado = resultado.getProdutoMelhorAvaliado();

        if (melhorAvaliado != null) {
            System.out.println("\n=== MELHOR AVALIADO ===");
            System.out.println("Nome: " + melhorAvaliado.getNome());
            System.out.println("Nota: " + melhorAvaliado.getAvaliacao());
        }

        System.out.println("\n=== PRODUTOS ABAIXO DE R$ 3500 ===");

        List<Produto> baratos = resultado.getProdutosAbaixoDe(3500);

        for (Produto p : baratos) {
            System.out.printf("%s - R$ %.2f%n",
                    p.getNome(),
                    p.getPreco());
        }

        System.out.println("\n=== PRODUTOS COM NOTA >= 4.5 ===");

        List<Produto> excelentes = resultado.getProdutosExcelentes(4.5);

        for (Produto p : excelentes) {
            System.out.printf("%s - %.1f estrelas%n",
                    p.getNome(),
                    p.getAvaliacao());
        }
    }
}