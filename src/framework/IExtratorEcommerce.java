package framework;

import java.util.List;

public interface IExtratorEcommerce {
    List<Produto> buscarItens(String termoBusca);
}
