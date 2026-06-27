package framework.service;

import java.util.List;

import framework.extractor.IExtratorEcommerce;
import framework.model.Produto;

import java.util.List;

public class ServicoAnalisador {

    private final IExtratorEcommerce extrator;

    public ServicoAnalisador(IExtratorEcommerce extrator) {
        this.extrator = extrator;
    }

    public Estatisticas gerarAnalise(String termoBusca) {
        List<Produto> produtos = extrator.buscarItens(termoBusca);
        return new Estatisticas(termoBusca, produtos);
    }
}	