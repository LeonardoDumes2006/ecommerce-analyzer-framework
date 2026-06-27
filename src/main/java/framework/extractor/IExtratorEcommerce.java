package framework.extractor;

import java.util.List;

import framework.model.Produto;

public interface IExtratorEcommerce {
    List<Produto> buscarItens(String termoBusca);
}
