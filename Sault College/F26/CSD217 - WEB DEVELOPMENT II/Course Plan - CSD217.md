---
tags: [course-plan, csd217]
fonte: "Teach and Learning Plan-csd217.html"
---

# Course Plan — CSD217 Web Development II

Plano de estudos: [[Plano de Estudos F26]] · Erros: [[Erros - CSD217]]

> [!warning] O TLP parece genérico
> O cabeçalho diz "CSD213", o midterm aparece na semana 7 e não lista DOM, Events nem generators. A prova real é na semana 9 (Nov 5). Use as semanas abaixo como **ordem aproximada** e confirme as datas na aula e no LMS.

## Avaliações

| Avaliação | Peso | Formato | Data |
|---|---|---|---|
| Lecture Interactive REPL (wk 1–11) | 20% | entregar o trabalho feito em aula | toda aula |
| Weekly Labs | 10% | lab de 2h + reflexão curta | semanal |
| Weekly Quizzes | 10% | MC + código curto | semanal |
| **Midterm** | **30%** | open book + coding (30 MC + 2 coding de 15) | **Thu Nov 5, 1:30pm** |
| **Final Exam** | **30%** | open book + coding, conteúdo após o midterm | **Dec 14–17 (confirmar)** |

Os REPLs (20%) são ponto quase garantido: basta ir à aula e entregar.

## Conteúdo do Final (ordem do TLP, semanas 8–13)

| Tópico | Objetivos |
|---|---|
| Reactive programming (React): componente básico | 5.1, 5.2 |
| Server-side: Express server simples | 2.4, 2.8, 2.9 (CORS, proxy) |
| Web app simples: integrar client + server (fetch) | 2.1, 2.4, 2.5 |
| Web security: HTTPS, validação, XSS, CSRF | 3.1–3.5 |
| Authentication & sessions: basic auth, sessão, cookies seguros | 4.1–4.4 |

## Objetivos que provavelmente caem no Final
- 2.1 client-side vs server-side · 2.2 HTTP · 2.6 Same-Origin policy · 2.7–2.8 CORS (o que é e como habilitar no servidor) · 2.9 proxy
- 3.1 por que HTTPS · 3.3 validação no cliente **e** no servidor · 3.4 encoding contra XSS/injection · 3.5 CSRF tokens
- 4.2 fluxo de autenticação por sessão · 4.3 atributos de cookie seguro (`HttpOnly`, `Secure`, `SameSite`)
- 5.1 por que UI reativa em vez de mutar o DOM na mão
