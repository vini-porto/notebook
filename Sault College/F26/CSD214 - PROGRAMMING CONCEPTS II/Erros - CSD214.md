---
tags: [erros, csd214]
---

# Erros — CSD214

Formato: **o que eu disse → o certo → regra de 1 linha.** Revisar 1, 3 e 7 dias depois. Quando acertar 2 vezes seguidas, mova o erro para "Dominado".

## 🔴 Test 1 real (03/11) — 67.83/100

Errei total ou parcialmente 16 das 38 questões. Elas se agrupam em 5 padrões:

### A. Processo de análise e design (4 erros, e eu achava que era ponto forte)
- **Q9 CRC:** objeto recebe um pedido que ninguém consegue atender → marquei "fora do escopo, pode descartar" (True). **Certo: False.** Isso indica uma **responsabilidade ou classe faltando** no modelo. O escopo vem dos requisitos, não dos buracos no modelo.
- **Q13:** "depois do CRC e do diagrama, o modelo é final" → marquei True. **Certo: False.** Análise e design são **iterativos**. Mudar durante a implementação é normal.
- **Q14:** `price` em GroupClass e PersonalTraining, com `getPrice()` no contrato Bookable → marquei "mover para quem faz a reserva". **Certo: generalizar o campo para cima, em Service** (o pai comum).
- **Q7 (substantivos):** troquei "season pass" (é **classe**) com "card reader" (é **contexto fora do modelo**: é *como* as pessoas usam o sistema).
- **Regra:** em questão de processo, a resposta certa quase sempre é **"iterar / o modelo evolui / falta algo no modelo"**. Desconfie de "final", "descartar", "sempre".

### B. Interfaces e abstração (4 erros, o padrão mais caro)
- **Q11:** "alguns drills e algumas paints podem ser alugados" → marquei "colocar getDailyRate() na base comum retornando zero". **Certo: interface `Rentable`** implementada só por quem pode ser alugado. **Uma capacidade que cruza hierarquias diferentes = interface.**
- **Q15 (select all):** consequências de `Checkout.process(Rentable)`. Marquei "precisa mudar Checkout a cada subclasse" e "a JVM faz instanceof" (**ambas falsas**). Faltou marcar: **só pode chamar o que Rentable declara** e **dá para testar com um fake Rentable**.
- **Q26:** ReadOnlyFile extends File e `write()` lança exceção → marquei OCP + instanceof. **Certo: LSP** (o subtipo quebra a promessa do pai). Separe as capacidades.
  - **ISP vs LSP:** uma *interface* gorda força métodos inúteis = **ISP**. Uma *subclasse* que quebra o que o pai promete = **LSP**.
- **Q6 (select all):** o que deixa o cliente quebrar a invariante? Faltou **getAttendees() devolvendo a própria ArrayList** (o cliente faz `.add()` direto). Marquei errado getCapacity() (um int é cópia, inofensivo) e o helper **private** isFull() (private não é acessível ao cliente).

### C. Fundamentos de Java em tempo de execução (3 erros)
- **Q4:** construtor sem `super(...)` → Java insere **`super()` implícito**. Se o pai não tem construtor sem argumentos = erro de compilação. Construtores **não** são herdados.
- **Q5:** `Animal a = new Dog(); a.speak()` → **o tipo real do objeto (Dog) decide qual override roda.** O tipo declarado só decide *quais métodos você pode chamar*. (Acertei a Q2 sobre isso e errei a Q5: li rápido.)
- **Q29 sealed switch:** `case Polygon` cobre Triangle porque **todo Triangle é um Polygon**. O compilador garante que todo tipo cai em *algum* case, não necessariamente no próprio.

### D. Testes (2 erros)
- **Q31:** escrever direto em `System.out` **dificulta o teste** (não dá para checar o resultado). Junto com static mutável e conexão criada dentro da classe.
- **Q38:** um teste E2E que só verifica "não lançou exceção" **só pega crash**. Um total errado (15.00 em vez de 150.00) passa despercebido. NumberFormatException e NPE são crashes, então ele pega.

### E. Collections e UML (2 erros, de novo os pontos fracos conhecidos)
- **Q23:** busca por código + **ordem de inserção** + substituir sem mudar a posição = **`LinkedHashMap`**. HashMap não tem ordem; TreeMap ordena pela chave.
- **Q20:** troquei associação e composição **de novo**. `private Instructor instructor` (existe sozinho) = **associação**. `final List<InvoiceLine>` criada dentro e nunca compartilhada = **composição**.

### Técnica de prova (vale para todas)
1. **Uma opção só está certa se TODAS as partes dela forem verdade.** Muitas erradas misturam algo verdadeiro com um mecanismo falso ("construtores são herdados", "a JVM faz instanceof", "o tipo declarado decide").
2. **Select all:** julgue cada opção como V/F **sozinha**, sem pensar "quantas devem ser".
3. Nos 4 V/F com afirmação absoluta ("final", "every", "must"), 3 eram False.

## Ativos (dos simulados, ainda fracos)
- Composição vs agregação: chamei House/Room de agregação → é composição. **Regra:** a parte é criada com `new` dentro do todo e não existe sozinha = composição. A parte é recebida de fora (`addPlayer(p)`) = agregação.
- Associação vs dependência: um parâmetro guardado num campo é **associação**. Um parâmetro só usado no método, ou um `new` numa variável local, é **dependência**.
- DI vs DIP: **DI** = receber a dependência de fora (construtor). **DIP** = depender de uma abstração (interface). Receber uma interface pelo construtor = os dois.
- ISP vs SRP: interface gorda que força `UnsupportedOperationException` = **ISP**. Classe com motivos diferentes para mudar (`saveToDatabase`, `sendEmail` dentro de Invoice) = **SRP**. `getTotal` em Invoice é OK.
- `hashCode` sem sobrescrever quebra só coleções **Hash** (HashSet/HashMap). `ArrayList.contains` usa só `equals`.
- Multiplicidade: o número fica do lado da classe que ele conta (o `1` fica junto de Publisher se cada Book tem 1 Publisher).
- `~` (package) **não** é visível para uma subclasse em outro pacote. `#` é.
- `super.x()` chama só o pai direto, e só se o código chamar.
- AAA: `c.add()` no setup é **Arrange**. O **Act** é o método que o teste testa.

## Dominado
- Collections (escolher a estrutura, removeIf, ConcurrentModificationException)
- `final List` ainda permite `.clear()`; `@Override` em `equals(Product)` dá erro de compilação
