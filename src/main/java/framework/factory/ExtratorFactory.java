package framework.factory;

import framework.extractor.ExtratorMercadoLivre;
import framework.extractor.IExtratorEcommerce;

public class ExtratorFactory {

    public static IExtratorEcommerce criar(String ecommerce) {

        switch (ecommerce.toLowerCase()) {

            case "mercadolivre":
                return new ExtratorMercadoLivre();

            default:
                throw new IllegalArgumentException(
                        "Marketplace não suportado: " + ecommerce);
        }
    }
}