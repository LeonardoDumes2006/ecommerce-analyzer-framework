package framework.service;

import java.util.List;

import framework.extractor.IExtratorEcommerce;
import framework.model.Produto;

/**
 * Serviço principal do framework, responsável por coordenar a busca de dados 
 * e gerar as análises.
 * Ele usa o padrão de Injeção de Dependência, o que significa que pode receber 
 * qualquer extrator como Mercado Livre e outros que futuramente estarão disponíveis desde que siga a interface
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com ) 
 */
public class ServicoAnalisador {

    private final IExtratorEcommerce extrator;

    /**
     * Construtor da classe ServicoAnalisador.
     * 
     * @param extrator O motor de extração específico (ex: ExtratorMercadoLivre) 
     * que será utilizado para procurar os produtos na internet ou em testes.
     */
    public ServicoAnalisador(IExtratorEcommerce extrator) {
        this.extrator = extrator;
    }

    /**
     * Pede ao extrator para buscar os produtos no site 
     * e, com os resultados, cria o objeto de estatísticas.
     * 
     * @param termoBusca O nome do produto que você quer pesquisar.
     * @return Um objeto {@link Estatisticas} com todos os produtos encontrados, 
     * pronto para gerar resumos, relatórios e filtros.
     */
    public Estatisticas gerarAnalise(String termoBusca) {
        List<Produto> produtos = extrator.buscarItens(termoBusca);
        return new Estatisticas(termoBusca, produtos);
    }
}