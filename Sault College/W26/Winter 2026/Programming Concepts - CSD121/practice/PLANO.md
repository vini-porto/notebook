# Plano de estudo: revisão CSD121

Criado em 2026-11-07 a partir do diagnóstico (ver [[PROGRESSO]]).

São **21 sessões de 30 a 60 min**, em 5 blocos. As sessões **não têm data**: se você pular uns dias, é só continuar da próxima sessão. Não existe "atrasado".

## Como funciona cada sessão

| Etapa | Tempo | O que acontece |
|---|---|---|
| 1. Aquecimento | 5 min | 3 perguntas rápidas sobre sessões anteriores (prioridade para o que você errou) |
| 2. Teoria comigo | 10–15 min | Ideia simples + exemplo curto. Você prevê a saída antes de rodar. |
| 3. Prática no IntelliJ | 15–25 min | Exercícios em `exercicios/sNN-.../` (nível 1 → 2 → 3). Quando terminar, diga "pronto". |
| 4. Quiz | 5–10 min | 5 perguntas, uma de cada vez, no chat |
| 5. Fechamento | 2 min | Eu atualizo o [[PROGRESSO]] e escrevo o resumo em `resumos/` |

**Parou no meio?** Sem problema. Na próxima vez, diga "continuar sessão N" e a gente segue de onde parou.

**Sessão curta (30 min)?** Faça só as etapas 1–3. O quiz fica para o aquecimento da próxima.

## Bloco A: o que o compilador vê (fundamentos)

| # | Tema | Prática no IntelliJ |
|---|---|---|
| S1 | Tipos, `var`, divisão inteira, `+=` `*=` `++` | Prever saídas; consertar uma calculadora de média que dá resultado errado |
| S2 | Escopo de bloco, compile-time vs run-time, `import` / `import static` | "Classifique e conserte": 6 trechos quebrados |

## Bloco B: objetos por dentro

| # | Tema | Prática no IntelliJ |
|---|---|---|
| S3 | Constructors: o constructor vazio automático, overloading, `this(...)`, valores padrão | Classe `Produto` com vários constructors |
| S4 | Referências (setas), `==` vs `equals`, override de `equals` / `hashCode` / `toString` | Classe `Ponto` comparada de três jeitos; teste com `HashSet` |
| S5 | Herança, `super(...)`, `super.metodo()`, `protected`, `@Override` | `Point` / `LabelledPoint` (questão da prova 2); `Funcionario` / `Gerente` |
| S6 | Polimorfismo: tipo estático vs dinâmico, dynamic dispatch | `List<Animal>` com vários bichos; "compila ou não?" |
| S7 | ✅ **Checkpoint B**: quiz interativo de 15 questões (A + B) e revisão dos erros | — |

## Bloco C: abstração

| # | Tema | Prática no IntelliJ |
|---|---|---|
| S8 | Classes abstratas, abstract methods | `Forma` abstrata → `Circulo`, `Retangulo` |
| S9 | Interfaces a fundo: implementar várias, `default` methods, interface como tipo, interface vs abstract class | `Steerable` / `Drivable` / `Vehicle` (questão da prova 2) |
| S10 | Lambdas, functional interfaces (`Predicate`, `Consumer`, `Function`, `Supplier`), `::` | Reescrever classes inteiras como lambdas |
| S11 | Streams (`filter`, `map`, `sorted`, `reduce`, `toList`) e `Comparator` | Lista de `record Aluno`: filtrar, ordenar, calcular médias |
| S12 | ✅ **Checkpoint C**: quiz interativo de 15 questões (B + C) e revisão dos erros | — |

## Bloco D: qualidade e design

| # | Tema | Prática no IntelliJ |
|---|---|---|
| S13 | JUnit (`assertEquals`, `assertThrows`, escolher casos), Maven / `pom.xml` | Criar um projeto Maven com JUnit e testar uma classe `ContaBancaria` |
| S14 | Dependency Injection, DIP, Open/Closed | Refatorar `Relatorio` / `MySQLDatabase` (questão da prova 2) |
| S15 | LSP, ISP, coesão e acoplamento | Encontrar violações e refatorar `Shape` e `Bird` |
| S16 | Design patterns: Strategy e Observer | Formas de pagamento (Strategy); `Acao` avisando gráficos (Observer) |

## Bloco E: interface gráfica

| # | Tema | Prática no IntelliJ |
|---|---|---|
| S17 | JavaFX: setup (Maven + `module-info`), Stage / Scene / Node, panes (`VBox`, `HBox`, `StackPane`) | Primeira janela com layout |
| S18 | JavaFX: eventos com lambda, controls | App contador com botões |

## Final

| # | Tema | Formato |
|---|---|---|
| S19 | Revisão geral | Quiz misturando **todos** os 23 tópicos (inclusive os "ok"); refazer os erros do log |
| S20 | 🎯 **Simulado** (60 min, cronometrado) | Parte 1: ~25 de múltipla escolha (interativo). Parte 2: 3 questões curtas de código no IntelliJ (DI, type definitions, interfaces), no estilo da prova. |
| S21 | Correção do simulado | Revisar cada erro e marcar os tópicos que voltam para "fraco" |

## Ritmo sugerido

- **2 a 3 sessões por semana** → termina em 7 a 10 semanas. CSD214/216/217 continuam sendo a prioridade.
- Semana de prova de outra matéria: pause sem culpa.
- Os blocos C e D (interfaces, DI, SOLID) são a base do Spring / IoC da CSD214, então eles também ajudam lá.

## Comandos

`dica` · `resposta` · `explica de novo` · `quiz` · `revisão geral` · `simulado` · `progresso` · `próximo` · `continuar sessão N`
