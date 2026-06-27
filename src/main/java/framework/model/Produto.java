package framework.model;

public class Produto {
	private String nome;
    private double preco;
    private String vendedor;
    private String link;
    private double avaliacao;

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
