---
tags: [erros, csd214]
---

# Erros — CSD214

Formato: **o que eu disse → o certo → regra de 1 linha.** Revisar 1, 3 e 7 dias depois. Quando acertar 2 vezes seguidas, mova o erro para "Dominado".

## Ativos (do Test 1, ainda fracos)
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
