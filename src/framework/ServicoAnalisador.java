package framework;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio: analisar a lista de produtos
 * extraídos e gerar estatísticas.
 */
public class ServicoAnalisador {

    private final IExtratorEcommerce extrator;

    // Injeção de Dependência via Construtor
    public ServicoAnalisador(IExtratorEcommerce extrator) {
        this.extrator = extrator;
    }

    public Estatisticas gerarAnalise(String termoBusca) {
        List<Produto> produtos = extrator.buscarItens(termoBusca);

        if (produtos.isEmpty()) {
            return new Estatisticas(termoBusca, 0, 0.0, 0.0, 0.0);
        }

        double soma = 0;
        double maior = Double.MIN_VALUE;
        double menor = Double.MAX_VALUE;

        for (Produto p : produtos) {
            soma += p.getPreco();
            if (p.getPreco() > maior) maior = p.getPreco();
            if (p.getPreco() < menor) menor = p.getPreco();
        }

        double media = soma / produtos.size();

        return new Estatisticas(termoBusca, produtos.size(), media, maior, menor);
    }
}