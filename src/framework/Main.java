package framework;

public class Main {
    public static void main(String[] args) {
        
        IExtratorEcommerce meuExtrator = new ExtratorMercadoLivre();
        
        ServicoAnalisador analisador = new ServicoAnalisador(meuExtrator);
        
        Estatisticas resultado = analisador.gerarAnalise("Vaio FE 16");
        
        resultado.imprimirRelatorio();
    }
}
