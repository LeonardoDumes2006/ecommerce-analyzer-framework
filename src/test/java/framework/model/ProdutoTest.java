package framework.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProdutoTest {

	 @Test
	    public void deveCriarProdutoCorretamente() {
	        String nome = "Notebook Vaio";
	        double preco = 3500.0;
	        String vendedor = "Loja Oficial";
	        String link = "http://teste.com";
	        double avaliacao = 4.8;

	        Produto produto = new Produto(nome, preco, vendedor, link, avaliacao);

	        assertEquals(nome, produto.getNome());
	        assertEquals(preco, produto.getPreco());
	        assertEquals(vendedor, produto.getVendedor());
	        assertEquals(link, produto.getLink());
	        assertEquals(avaliacao, produto.getAvaliacao());
	    }

}
