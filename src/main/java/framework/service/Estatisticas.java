package framework.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import framework.model.Produto;

public class Estatisticas {
    
    private String termoBusca;
    private List<Produto> produtos;
    private int totalItens;
    private double precoMedio;
    private double maiorPreco;
    private double menorPreco;
    
    private double avaliacaoMedia;
    private double maiorAvaliacao;
    private double menorAvaliacao;

    public Estatisticas(String termoBusca, List<Produto> produtos) {
        this.termoBusca = termoBusca;
        this.produtos = produtos;
        this.totalItens = produtos.size();
        calcularMetricasBasicas();
    }
    

    private void calcularMetricasBasicas() {
        if (produtos.isEmpty()) {
            this.precoMedio = 0.0;
            this.maiorPreco = 0.0;
            this.menorPreco = 0.0;
            this.avaliacaoMedia = 0.0;
            this.maiorAvaliacao = 0.0;
            this.menorAvaliacao = 0.0;
            return;
        }

        double somaPreco = 0;
        double maiorP = Double.MIN_VALUE;
        double menorP = Double.MAX_VALUE;

        double somaAvaliacao = 0;
        double maiorA = Double.MIN_VALUE;
        double menorA = Double.MAX_VALUE;

        for (Produto p : produtos) {
            somaPreco += p.getPreco();
            if (p.getPreco() > maiorP) maiorP = p.getPreco();
            if (p.getPreco() < menorP) menorP = p.getPreco();

            somaAvaliacao += p.getAvaliacao();
            if (p.getAvaliacao() > maiorA) maiorA = p.getAvaliacao();
            if (p.getAvaliacao() < menorA) menorA = p.getAvaliacao();
        }

        this.precoMedio = somaPreco / totalItens;
        this.maiorPreco = maiorP;
        this.menorPreco = menorP;

        this.avaliacaoMedia = somaAvaliacao / totalItens;
        this.maiorAvaliacao = maiorA;
        this.menorAvaliacao = menorA;
    }

    public List<Double> getTodosOsPrecos() {
        return produtos.stream()
                       .map(Produto::getPreco)
                       .collect(Collectors.toList());
    }

    public List<String> getTodosOsVendedores() {
        return produtos.stream()
                       .map(Produto::getVendedor)
                       .distinct()
                       .collect(Collectors.toList());
    }

    public List<Produto> getProdutosAbaixoDe(double valorMaximo) {
        return produtos.stream()
                       .filter(p -> p.getPreco() <= valorMaximo)
                       .collect(Collectors.toList());
    }

    public Produto getProdutoMaisBarato() {
        return produtos.stream()
                       .min(Comparator.comparing(Produto::getPreco))
                       .orElse(null);
    }


    public void imprimirResumo() {
        System.out.println("\n--- RESUMO ESTATÍSTICO: " + termoBusca.toUpperCase() + " ---");
        System.out.println("Total de itens encontrados: " + totalItens);
        if (totalItens > 0) {
            System.out.printf("Preço Médio: R$ %.2f\n", precoMedio);
            System.out.printf("Maior Preço: R$ %.2f\n", maiorPreco);
            System.out.printf("Menor Preço: R$ %.2f\n", menorPreco);
            System.out.printf("Avaliação Média: %.1f estrelas\n", avaliacaoMedia);
            System.out.printf("Maior Avaliação: %.1f estrelas\n", maiorAvaliacao);
            System.out.printf("Menor Avaliação: %.1f estrelas\n", menorAvaliacao);
        }
        System.out.println("----------------------------------------");
    }

    public void exibirListagemCompleta() {
        System.out.println("\n=== LISTAGEM COMPLETA DOS PRODUTOS ===");
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.printf("Produto %d: %s\n", (i + 1), p.getNome());
            System.out.printf("  Preço:    R$ %.2f\n", p.getPreco());
            System.out.printf("  Opinião do produto:    %s\n", p.getAvaliacao());
            System.out.printf("  Vendedor: %s\n", p.getVendedor());
            System.out.printf("  Link:     %s\n", p.getLink());
            System.out.println("  --------------------------------------");
        }
    }
    /**
     * Retorna o produto com a maior nota (mais estrelas).
     */
    public Produto getProdutoMelhorAvaliado() {
        return produtos.stream()
                       .max(Comparator.comparing(Produto::getAvaliacao))
                       .orElse(null);
    }

    /**
     * Filtra o mercado e retorna apenas produtos com nota excelente (ex: acima de 4.5).
     */
    public List<Produto> getProdutosExcelentes(double notaMinima) {
        return produtos.stream()
                       .filter(p -> p.getAvaliacao() >= notaMinima)
                       .collect(Collectors.toList());
    }
}
