# Progresso: revisão CSD121

Status: `não visto` · `fraco` · `ok` · `dominado`

Diagnóstico feito em 2026-11-06 (29 perguntas no chat).

| # | Tópico | Status | Erros recorrentes / observações | Última revisão |
|---|---|---|---|---|
| 1 | Estrutura do programa, javac/java/JVM, tipos de erro | ok | — | 2026-11-06 |
| 2 | Variáveis, tipos, `var`, operadores | ok | S1 fechada. Corrigido: `var` tem tipo fixo, `double` imprime `.0`, `+=` soma o valor, `%` devolve a sobra (pensava que dava 0/1), precedência `a + b / 2`. Ainda vale revisar: `float` vs `double` e cast antes do valor (`(double) a`). | 2026-11-08 |
| 3 | Condicionais e laços | ok | Leitura de loop certa (o erro foi o `+=`, tópico 2) | 2026-11-06 |
| 4 | Métodos: parâmetros, retorno | ok | Entendeu retorno ignorado e passagem por cópia | 2026-11-06 |
| 5 | Arrays, List, Map | ok | `.length` vs `.size()` certo (Map não testado) | 2026-11-06 |
| 6 | Scope, shadowing, packages, imports | fraco | Shadowing ok. Achou que variável fora do escopo é erro de run-time (é compile-time). Imports não testados. | 2026-11-06 |
| 7 | `static` vs instância | ok | — | 2026-11-06 |
| 8 | Classes, constructors, `this` | fraco | Não sabia quando o Java cria o constructor vazio (acertou só com dica) | 2026-11-06 |
| 9 | Encapsulamento, access modifiers | ok | — | 2026-11-06 |
| 10 | `toString`, `equals`, `hashCode` | fraco | Achou que `p3 = p1` copia o objeto (copia a referência). Achou que `equals` sem override compara conteúdo. Acertou a pergunta de conferência. | 2026-11-06 |
| 11 | `enum` e `record` | ok | — | 2026-11-06 |
| 12 | Testes, JUnit, Maven | fraco | Escolha de casos ok. `assertThrows`: ordem invertida e sem lambda. Maven não testado. | 2026-11-06 |
| 13 | Herança, `super`, override, `protected` | fraco | Acertou chutando. Não conhecia o `super()` implícito. Confunde "constructor vazio automático" com "`super()` automático". | 2026-11-06 |
| 14 | Polimorfismo: tipo estático vs dinâmico | fraco | Achou que `Animal a = new Gato(); a.ronronar()` compila. Acertou a conferência depois da explicação. | 2026-11-06 |
| 15 | Classes abstratas | não visto | — | 2026-11-06 |
| 16 | Interfaces | ok | Sabe que `implements` obriga a implementar todos os métodos | 2026-11-06 |
| 17 | Lambdas, functional interfaces, `::` | não visto | — | 2026-11-06 |
| 18 | Streams e `Comparator` | não visto | Pulado (depende de lambdas) | — |
| 19 | Exceptions, `throw`, validação no constructor | ok | — | 2026-11-06 |
| 20 | JavaFX | não visto | — | 2026-11-06 |
| 21 | Princípios: KISS, coesão, acoplamento, pure functions | ok | Pure function certo, com o motivo. Coesão e acoplamento não testados. | 2026-11-06 |
| 22 | SOLID + Dependency Injection | fraco | Reconhece o problema do `new` dentro da classe, mas não sabe o conserto | 2026-11-06 |
| 23 | Strategy e Observer | não visto | — | 2026-11-06 |

## Erros recorrentes (atravessam tópicos)

- **Fronteira entre compile-time e run-time.** Disse run-time para variável fora do escopo (é compile-time) e compile-time para índice fora do array (é run-time). Regra: o compilador só vê nomes e tipos, não valores.
- **Constructors automáticos.** Mesma raiz nos diagnósticos 11 e 19 (constructor vazio automático vs `super()` automático).

## Ordem de estudo

Ver [[PLANO]]: 21 sessões de 30–60 min em 5 blocos, com checkpoints e simulado final.

## Sessões feitas

| Sessão | Data | Observações |
|---|---|---|
| S1 | 2026-11-07 a 2026-11-08 | Quiz 5/5. Exercícios N1–N3 + bug 100%. Aprendeu sobre `static` (errou que método `static` não chama não-`static` direto, revisar na S3/S4). IntelliJ configurado. |
