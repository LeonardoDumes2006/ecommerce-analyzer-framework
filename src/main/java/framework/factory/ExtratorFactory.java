package framework.factory;

import framework.extractor.ExtratorMercadoLivre;
import framework.extractor.IExtratorEcommerce;
import framework.enums.Ecommerce;

/**
 * Fábrica responsável por instanciar o extrator correto com base no e-commerce escolhido.
 * Utiliza o padrão de projeto Factory Method para centralizar a criação de objetos.
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com ) 
 */

public class ExtratorFactory {

	/**
     * Cria e retorna uma implementação de {@link IExtratorEcommerce}.
     * 
     * @param ecommerce O enum que representa a loja desejada (ex: Ecommerce.MERCADO_LIVRE).
     * @return Uma instância do extrator correspondente à loja.
     * @throws IllegalArgumentException Se o marketplace escolhido ainda não possuir um extrator implementado.
     */
	
    public static IExtratorEcommerce criar(Ecommerce ecommerce) {

        switch (ecommerce) {

            case MERCADO_LIVRE:
                return new ExtratorMercadoLivre();

            default:
                throw new IllegalArgumentException(
                        "Marketplace não suportado: " + ecommerce);
        }
    }
}