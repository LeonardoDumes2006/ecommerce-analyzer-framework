package framework;

/**
 * Classe de dados para exibir o resultado da análise.
 */
public class Estatisticas {
    private String termo;
    private int totalItens;
    private double precoMedio;
    private double maiorPreco;
    private double menorPreco;

    public Estatisticas(String termo, int totalItens, double precoMedio, double maiorPreco, double menorPreco) {
        this.termo = termo;
        this.totalItens = totalItens;
        this.precoMedio = precoMedio;
        this.maiorPreco = maiorPreco;
        this.menorPreco = menorPreco;
    }

    public void imprimirRelatorio() {
        System.out.println("\n--- RELATÓRIO DE MERCADO: " + termo.toUpperCase() + " ---");
        System.out.println("Total de itens analisados: " + totalItens);
        if (totalItens > 0) {
            System.out.printf("Preço Médio: R$ %.2f%n", precoMedio);
            System.out.printf("Maior Preço: R$ %.2f%n", maiorPreco);
            System.out.printf("Menor Preço: R$ %.2f%n", menorPreco);
        } else {
            System.out.println("Nenhum dado disponível para cálculo.");
        }
        System.out.println("----------------------------\n");
    }
}
