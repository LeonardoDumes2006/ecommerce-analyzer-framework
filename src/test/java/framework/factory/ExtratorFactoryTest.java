package framework.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import framework.enums.Ecommerce;
import framework.extractor.ExtratorMercadoLivre;
import framework.extractor.IExtratorEcommerce;

class ExtratorFactoryTest {

	 @Test
	    public void deveCriarExtratorMercadoLivre() {
	        IExtratorEcommerce extrator = ExtratorFactory.criar(Ecommerce.MERCADO_LIVRE);

	        assertNotNull(extrator);
	        assertTrue(extrator instanceof ExtratorMercadoLivre, "A factory deve retornar uma instância de ExtratorMercadoLivre");
	    }
	 
	 // este teste tem como finalidade dar erro
	 @Test
	    public void deveLancarExcecaoParaAmazon() {
	        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	            ExtratorFactory.criar(Ecommerce.AMAZON);
	        });

	        assertTrue(exception.getMessage().contains("Marketplace não suportado"));
	    }

}
