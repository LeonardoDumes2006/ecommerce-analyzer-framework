package framework.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import framework.model.Produto;

/**
 * Guarda a lista de produtos encontrados e gera as estatísticas.
 * Também oferece funções para filtrar os produtos e mostrar os dados na tela.
 * 
 * @author Leonardo Dumes de Souza  ( leonardodumesdesouza2006@gmail.com ) 
 */
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

    /**
     * Cria o objeto de estatísticas e já faz os cálculos básicos (média, maior e menor preço).
     * 
     * @param termoBusca O nome do produto que foi pesquisado ).
     * @param produtos A lista de produtos encontrados na internet.
     */
    public Estatisticas(String termoBusca, List<Produto> produtos) {
        this.termoBusca = termoBusca;
        this.produtos = produtos;
        this.totalItens = produtos.size();
        calcularMetricasBasicas();
    }
    
    /**
     * Percorre a lista de produtos para extrair as métricas básicas de preço e avaliação
     */
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

    /**
     * Extrai isoladamente os preços de cada item da base analisada.
     * 
     * @return Uma {@link List} contendo apenas os preços de cada produto encontrado.
     */
    public List<Double> getTodosOsPrecos() {
        return produtos.stream()
                       .map(Produto::getPreco)
                       .collect(Collectors.toList());
    }

    /**
     * Identifica todos os fornecedores mapeados na busca, eliminando nomes duplicados.
     * 
     * @return Uma {@link List} com os nomes únicos de todos os vendedores ou lojas oficiais.
     */
    public List<String> getTodosOsVendedores() {
        return produtos.stream()
                       .map(Produto::getVendedor)
                       .distinct()
                       .collect(Collectors.toList());
    }

    /**
     * Filtra a base de dados coletada isolando apenas os itens que se enquadram
     * em um limite máximo de orçamento.
     * 
     * @param valorMaximo O preço limite de corte para o filtro.
     * @return Uma lista filtrada de produtos com valores menores ou iguais ao parâmetro informado.
     */
    public List<Produto> getProdutosAbaixoDe(double valorMaximo) {
        return produtos.stream()
                       .filter(p -> p.getPreco() <= valorMaximo)
                       .collect(Collectors.toList());
    }

    /**
     * Identifica o objeto correspondente à oferta mais barata da pesquisa.
     * 
     * @return O objeto {@link Produto} mais barato encontrado, ou null se a lista estiver vazia.
     */
    public Produto getProdutoMaisBarato() {
        return produtos.stream()
                       .min(Comparator.comparing(Produto::getPreco))
                       .orElse(null);
    }

    /**
     * Imprime no console um painel estruturado contendo o resumo 
     * das métricas de preço e notas médias.
     */
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

    /**
     * Percorre a base de dados e imprime no console a listagem completa de todos os
     * produtos com detalhes individuais de preço, links, opiniões e vendedores.
     */
    public void exibirListagemCompleta() {
        System.out.println("\n=== LISTAGEM COMPLETA DOS PRODUTOS ===");
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.printf("Produto %d: %s\n", (i + 1), p.getNome());
            System.out.printf("  Preço:    R$ %.2f\n", p.getPreco());
            System.out.printf("  Opinião do produto:    %.1f estrelas\n", p.getAvaliacao());
            System.out.printf("  Vendedor: %s\n", p.getVendedor());
            System.out.printf("  Link:     %s\n", p.getLink());
            System.out.println("  --------------------------------------");
        }
    }

    /**
     * Retorna a base de dados completa (a listagem bruta) sem aplicar filtros.
     * 
     * @return Uma {@link List} contendo todos os objetos {@link Produto} extraídos.
     */
    public List<Produto> getListaCompleta() {
        return this.produtos;
    }
    
    /**
     * Localiza e retorna o item que obteve o maior índice de aprovação dos compradores.
     * 
     * @return O objeto {@link Produto} melhor avaliado na listagem atual.
     */
    public Produto getProdutoMelhorAvaliado() {
        return produtos.stream()
                       .max(Comparator.comparing(Produto::getAvaliacao))
                       .orElse(null);
    }

    /**
     * Filtra o mercado isolando apenas as opções que estão acima de 
     *  um critério mínimo de estrelas.
     *  
     * @param notaMinima A nota mínima de corte estabelecida, que precisa estar entre 0 e 5.
     * @return Uma lista filtrada contendo apenas os produtos acima 
     * da avaliação mínima estabelecida.
     * @throws IllegalArgumentException se o valor da nota informada for inválido.
     */
    public List<Produto> getProdutosExcelentes(double notaMinima) {
        if (notaMinima < 0.0 || notaMinima > 5.0) {
            throw new IllegalArgumentException("Erro de validação: A nota mínima deve estar entre 0 e 5.");
        }
        
        return produtos.stream()
                       .filter(p -> p.getAvaliacao() >= notaMinima)
                       .collect(Collectors.toList());
    }
}