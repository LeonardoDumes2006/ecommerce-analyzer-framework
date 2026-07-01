package framework.model;

/**
 * Representa uma entidade de Produto extraída de um marketplace.
 * Contém as informações básicas necessárias para a análise de mercado.
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com ) 
 */

public class Produto {
	private String nome;
    private double preco;
    private String vendedor;
    private String link;
    private double avaliacao;

    
    /**
     * Construtor padrão para inicializar um novo produto rastreado.
     * 
     * @param nome O título do anúncio do produto.
     * @param preco O preço atual do produto.
     * @param vendedor O nome da loja ou perfil que está vendendo o item.
     * @param link A URL direta para a página de compra.
     * @param avaliacao A nota média do produto baseada nas opiniões
     */
    
    public Produto(String nome, double preco, String vendedor, String link, double avaliacao) {
        this.nome = nome;
        this.preco = preco;
        this.vendedor = vendedor;
        this.link = link;
        this.avaliacao = avaliacao;
    }

	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}

	public String getVendedor() {
		return vendedor;
	}

	public String getLink() {
		return link;
	}
	
	public double getAvaliacao() {
		return avaliacao;
	}
    
    
}
