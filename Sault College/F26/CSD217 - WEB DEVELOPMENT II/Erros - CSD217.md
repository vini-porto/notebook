---
tags: [erros, csd217]
---

# Erros — CSD217

Formato: **o que eu disse → o certo → regra de 1 linha.** Revisar 1, 3 e 7 dias depois.

## Ativos (reler na manhã da prova)
- `bubbles: true` vai **fora** do `detail`: `new CustomEvent("nome", { detail: {...}, bubbles: true })`. (Errei 4×.)
- `new Event(...)` **ignora** `detail`. Só `CustomEvent` tem `detail`.
- No listener, leia `e.detail`, não uma variável de fora.
- Sempre `elemento.dispatchEvent(evento)`, no elemento certo e **dentro** do intervalo/loop.
- Crie o iterador **fora** do `setInterval`: `const it = gen();`. Cada `gen()` é um iterador novo, então `g().next()` duas vezes imprime o 1º valor duas vezes.
- `it.next()` retorna `{ value, done }`: confira `done` **e** use `value`.
- `fetch` com 404 **não** rejeita: dá `res.ok === false`. Sempre `if (!res.ok) throw ...`.
- `res.json()` precisa de `await`.
- `Promise.all` retorna os resultados **na ordem do array**, não na ordem de chegada. Se um falhar, cai no catch.
- `race` = o primeiro que **termina** (sucesso ou erro). `any` = o primeiro **sucesso**.
- `allSettled` → cada item é `{status, value}` ou `{status, reason}`. Lembre de usar `.value`.
- `getJSON` só busca e retorna os dados. Quem mexe no DOM é outra função. Nunca escreva `async function*` (sem asterisco).
- URLs: `/posts/1` (plural!), `/users`, `/todos`.
- `??` só troca `null`/`undefined`: `"" ?? "x"` dá `""` e `0 ?? 5` dá `0`. `||` troca qualquer falsy.
- TS: `Partial<T>` deixa tudo opcional; `&` = intersecção; os tipos somem em runtime.
- `querySelectorAll` é estático; `splice` muta o array; arrow function não é construtor; prefira `Number.isNaN`.
- Mnemônico do Q1: **pegar, conferir, extrair, disparar.**
