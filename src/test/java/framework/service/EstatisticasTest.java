package framework.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import framework.model.Produto;

class EstatisticasTest {

	private List<Produto> produtos;
    private Estatisticas estatisticas;

    @BeforeEach
    public void setUp() {
        produtos = new ArrayList<>();
        produtos.add(new Produto("Notebook Caro", 5000.0, "Loja A", "link1", 4.0));
        produtos.add(new Produto("Notebook Barato", 2000.0, "Loja B", "link2", 4.5));
        produtos.add(new Produto("Notebook Médio", 3500.0, "Loja A", "link3", 5.0));

        estatisticas = new Estatisticas("Notebook", produtos);
    }

    @Test
    public void deveEncontrarProdutoMaisBarato() {
        Produto maisBarato = estatisticas.getProdutoMaisBarato();
        
        assertNotNull(maisBarato);
        assertEquals("Notebook Barato", maisBarato.getNome());
        assertEquals(2000.0, maisBarato.getPreco());
    }

    @Test
    public void deveEncontrarProdutoMelhorAvaliado() {
        Produto melhorAvaliado = estatisticas.getProdutoMelhorAvaliado();
        
        assertNotNull(melhorAvaliado);
        assertEquals("Notebook Médio", melhorAvaliado.getNome());
        assertEquals(5.0, melhorAvaliado.getAvaliacao());
    }

    @Test
    public void deveFiltrarProdutosAbaixoDeUmValor() {
        List<Produto> filtrados = estatisticas.getProdutosAbaixoDe(4000.0);
        
        assertEquals(2, filtrados.size(), "Devem existir apenas 2 produtos abaixo de 4000");
        assertTrue(filtrados.stream().anyMatch(p -> p.getNome().equals("Notebook Barato")));
        assertTrue(filtrados.stream().anyMatch(p -> p.getNome().equals("Notebook Médio")));
    }

    @Test
    public void deveRetornarVendedoresSemRepeticao() {
        List<String> vendedores = estatisticas.getTodosOsVendedores();
        
        assertEquals(2, vendedores.size());
        assertTrue(vendedores.contains("Loja A"));
        assertTrue(vendedores.contains("Loja B"));
    }
}
