package framework;

public class Produto {
	private String nome;
    private double preco;
    private String vendedor;
    private String link;

    public Produto(String nome, double preco, String vendedor, String link) {
        this.nome = nome;
        this.preco = preco;
        this.vendedor = vendedor;
        this.link = link;
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
    
    
}
