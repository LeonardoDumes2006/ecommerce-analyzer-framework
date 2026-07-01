**Framework Analisador de E-commerce**

Um framework em Java construído para extrair, processar e analisar dados de produtos em marketplaces (como o Mercado Livre). O projeto foi desenvolvido com um forte foco em boas práticas de Programação Orientada a Objetos, aplicando princípios SOLID, Injeção de Dependência e o padrão de projeto Factory Method.

**Principais Funcionalidades**

Extração Automatizada: Recolha de títulos, preços, vendedores, links e notas de avaliação.

Análise Estatística: Cálculo automático de preços (médio, maior, menor) e avaliações.

Filtros Avançados: Funções prontas para filtrar produtos por orçamento máximo, nota mínima, fornecedores únicos, etc.

```plantuml
Diagrama UML

@startuml
    enum Ecommerce {
        MERCADO_LIVRE
        AMAZON
        SHOPEE
    }

    class Produto {
        - nome : String
        - preco : double
        - vendedor : String
        - link : String
        - avaliacao : double

        + Produto(nome: String, preco: double, vendedor: String, link: String, avaliacao: double)
        + getNome() : String
        + getPreco() : double
        + getVendedor() : String
        + getLink() : String
        + getAvaliacao() : double
    }

    interface IExtratorEcommerce {
        + buscarItens(termoBusca : String) : List<Produto>
    }

    class ExtratorMercadoLivre {
        + buscarItens(termoBusca : String) : List<Produto>
        - formatarTermo(str : String) : String
    }

    class ExtratorMock {
        + buscarItens(termoBusca : String) : List<Produto>
    }

    class ExtratorFactory {
        + criar(ecommerce : Ecommerce) : IExtratorEcommerce
    }

    class ServicoAnalisador {
        - extrator : IExtratorEcommerce

        + ServicoAnalisador(extrator : IExtratorEcommerce)
        + gerarAnalise(termoBusca : String) : Estatisticas
    }

    class Estatisticas {
        - termoBusca : String
        - produtos : List<Produto>
        - totalItens : int

        - precoMedio : double
        - maiorPreco : double
        - menorPreco : double

        - avaliacaoMedia : double
        - maiorAvaliacao : double
        - menorAvaliacao : double

        + Estatisticas(termoBusca : String, produtos : List<Produto>)
        - calcularMetricasBasicas() : void
        + getTodosOsPrecos() : List<Double>
        + getTodosOsVendedores() : List<String>
        + getListaCompleta() : List<Produto>
        + imprimirResumo() : void
        + exibirListagemCompleta() : void
        + getProdutoMaisBarato() : Produto
        + getProdutoMelhorAvaliado() : Produto
        + getProdutosAbaixoDe(valorMaximo : double) : List<Produto>
        + getProdutosExcelentes(notaMinima : double) : List<Produto>
    }

IExtratorEcommerce <|.. ExtratorMercadoLivre
IExtratorEcommerce <|.. ExtratorMock

ServicoAnalisador o-- IExtratorEcommerce : Injecao de Dependencia

ServicoAnalisador --> Estatisticas : gera

IExtratorEcommerce ..> Produto : retorna

Estatisticas *-- Produto : analisa

ExtratorFactory ..> Ecommerce
ExtratorFactory ..> IExtratorEcommerce : cria
@enduml
```
**Como Utilizar**

O framework foi concebido para ser simples de implementar em qualquer aplicação "cliente". Exemplo de utilização:

```java

import framework.factory.ExtratorFactory;
import framework.enums.Ecommerce;
import framework.extractor.IExtratorEcommerce;
import framework.service.ServicoAnalisador;
import framework.service.Estatisticas;

public class App {
    public static void main(String[] args) {
        // 1. Cria o extrator desejado usando a Factory
        IExtratorEcommerce extrator = ExtratorFactory.criar(Ecommerce.MERCADO_LIVRE);
        
        // 2. Injeta o extrator no Serviço Analisador
        ServicoAnalisador analisador = new ServicoAnalisador(extrator);
        
        // 3. Gera as estatísticas
        Estatisticas resultado = analisador.gerarAnalise("Vaio FE 16");
        
        // 4. Exibe os resultados
        resultado.imprimirResumo();
        
        // Produtos excelentes (Nota > 4.5)
        resultado.getProdutosExcelentes(4.5).forEach(p -> 
            System.out.println(p.getNome() + " - " + p.getAvaliacao() + " estrelas")
        );
    }

```

**Tecnologias Utilizadas**

Java (11+)

Playwright for Java: Utilizado para web scraping e automação de navegação.

JUnit 5: Cobertura de testes unitários com regras de validação (fail-fast).

Maven: Gestão de dependências e compilação do projeto.

Desenvolvido por Leonardo Dumes de Souza
