package framework.enums;

/**
 * Define os marketplaces (lojas virtuais) suportados pelo framework.
 * Utilizado pela ExtratorFactory para determinar qual motor de extração instanciar.
 * 
 * @author Leonardo Dumes de Souza ( leonardodumesdesouza2006@gmail.com )
 * 
 */
public enum Ecommerce {
    
    /**
     * Representa o Mercado Livre (www.mercadolivre.com.br).
     */
    MERCADO_LIVRE,
    
    /**
     * Representa a Amazon (www.amazon.com.br).
     * Observação: Na versão atual este marketplace ainda não é suportado.
     */
    AMAZON,
    
    /**
     * Representa a Shopee Brasil (shopee.com.br).
     * Observação: Na versão atual este marketplace ainda não é suportado
     */
    SHOPEE
}