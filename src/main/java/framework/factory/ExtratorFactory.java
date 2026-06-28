package framework.factory;

import framework.extractor.ExtratorMercadoLivre;
import framework.extractor.IExtratorEcommerce;
import framework.enums.Ecommerce;

public class ExtratorFactory {

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