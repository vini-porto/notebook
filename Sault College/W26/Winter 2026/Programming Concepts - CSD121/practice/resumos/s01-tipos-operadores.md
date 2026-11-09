# S1: Tipos, `var` e operadores

1. Toda variável é uma **caixa com etiqueta** (o tipo), colada na criação e que nunca muda. `var` só pede pro compilador escrever a etiqueta: `var x = 10;` é igual a `int x = 10;`, e depois `x = 2.5;` não compila.
2. Número com ponto é `double` (`3.5`), texto é `String` com S maiúsculo, `true`/`false` é `boolean`. Guardar `int` em `double` é automático (não perde nada). O contrário exige cast `(int)`, que **corta**: `(int) 9.99` dá 9.
3. **Divisão inteira:** se os dois lados forem `int`, o resultado é `int`, e a parte decimal some (`7 / 2` dá 3, `9 / 10` dá 0). Basta um lado ser `double` pra dar decimal: `(a + b) / 2.0` ou `(double) soma / qtd`. O cast vai **antes** da divisão: `(double)(17 / 2)` ainda dá 8.0.
4. **`%` devolve o que sobra**, o número de verdade (não 0 ou 1): `19 % 5` dá 4, `3 % 7` dá 3. Método: quantas vezes cabe → multiplica → subtrai. `/` e `%` juntos quebram valores (3725 s → 1 h 2 min 5 s).
5. `x += 3` é `x = x + 3` (o mesmo vale para `-=`, `*=`, `%=`), e `x++` soma 1. Multiplicação e divisão vêm antes de soma: `7 + 8 / 2` dá 11, então use parênteses.
