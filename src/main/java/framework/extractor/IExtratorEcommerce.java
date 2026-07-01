package framework.extractor;

import java.util.List;

import framework.model.Produto;

/**
 * Interface que define o contrato para a criação de extratores de e-commerce.
 * Qualquer nova loja adicionada ao framework deve implementar esta interface.
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com ) 
 */

public interface IExtratorEcommerce {
	/**
     * Busca uma lista de produtos em um e-commerce com base em um termo de pesquisa.
     * 
     * @param termoBusca O nome do produto ou categoria a ser pesquisado
     * @return Uma lista de objetos {@link Produto} contendo os dados extraídos.
     */
    List<Produto> buscarItens(String termoBusca);
}
