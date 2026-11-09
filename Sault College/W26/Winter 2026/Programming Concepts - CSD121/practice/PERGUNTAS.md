# Perguntas e respostas: CSD121

As dúvidas que surgiram durante as sessões, com a resposta em linguagem simples.

---

## O que o `static` quer dizer num método como `converterSegundos`? (S1)

Um método `static` **pertence à classe**, não a um objeto. Por isso dá pra chamar sem criar nenhum objeto antes.

- Analogia: `static` é a **calculadora pendurada na parede da sala**, que todo mundo usa direto. Um método sem `static` é a **calculadora pessoal**: primeiro alguém precisa ter uma (criar o objeto com `new`).
- O `main` é `static`, porque é onde o programa começa e ainda não existe objeto nenhum. Por isso, **de dentro de um método `static`, só dá pra chamar direto outro `static`**. Para chamar um método sem `static`, crie um objeto antes: `new Loja().comImposto(100)`.
- Se você tentar mesmo assim, o erro é: `non-static method ... cannot be referenced from a static context`.
- Nas constantes, `static final int SEGUNDOS_POR_MINUTO = 60;`: `static` = uma cópia só, da classe; `final` = o valor nunca muda.

---

## Por que `length` não tem parênteses no array, mas tem na String? (S2)

Porque nos dois casos ele é **uma coisa diferente**:

| | Como escreve | O que é |
|---|---|---|
| Array | `notas.length` | um **field** (uma variável guardada dentro do array) |
| String | `nome.length()` | um **método** (uma ação que calcula e devolve o tamanho) |
| List / ArrayList | `lista.size()` | um **método**, com outro nome |

**Por quê?** O tamanho de um array é decidido no `new int[3]` e **nunca muda**. Então o Java só anota esse número num field fixo (`final`) dentro do array, e você lê direto, como lê qualquer variável: sem parênteses. Já `String` e `List` são **classes** escritas em Java, que guardam os dados do jeito delas por dentro (encapsulamento). Elas não deixam você mexer nessa parte interna e oferecem um **método** que responde o tamanho.

**Regra que vale sempre:** com parênteses é método (uma ação), sem parênteses é variável (um valor guardado). Isso vem do tópico de namespaces.

**Dica de prova:** é uma inconsistência histórica do Java, e cai em pegadinha. Decore a tabela: **array → `length`**, **String → `length()`**, **List → `size()`**. Errar dá erro de **compile-time** (`cannot find symbol`).

---

## Por que `new int[4]` e não só `int[4]`? (S2)

Porque `int[]` e `new int[4]` são coisas diferentes:

| Escrita | O que é | Analogia |
|---|---|---|
| `int[]` | um **tipo** (a etiqueta da caixa) | o rótulo "estante de gavetas de int" |
| `new int[4]` | uma **ação**: criar um array na memória, durante a execução | mandar **fabricar** uma estante com 4 gavetas |

Arrays são **objetos** (a variável guarda uma seta para eles), e todo objeto nasce com `new`, como em `new Ponto(1, 2)` e `new ArrayList<>()`. `int[4]` sozinho não é Java válido (erro de sintaxe).

Existe um atalho, só na declaração e quando você já sabe os valores: `int[] nums = {4, 8, 15};`, que por baixo é `new int[]{4, 8, 15}`.

**Valores iniciais de um array novo:** o Java preenche com o valor padrão do tipo: `int` → `0`, `double` → `0.0`, `boolean` → `false`, `char` → `'\u0000'`, e qualquer objeto (`String`, `Ponto`…) → `null`. Então `new int[3]` começa como `[0, 0, 0]`, e `new String[3]` como `[null, null, null]`.
