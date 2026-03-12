---
epoch: 1773152377683
modelKey: "gemini-2.5-flash|google"
topic: "Recap Active Note"



tags:
  - copilot-conversation
---

**user**: Give me a quick recap of {activeNote} in two sentences.
[Context: Notes: Sault College/W26/Winter 2026/Databases I - CSD123/6 - Subqueries — Query Inception.md]
[Timestamp: 2026/03/10 10:19:37]

**ai**: A subquery allows embedding one `SELECT` statement inside another, solving the problem of using aggregate functions directly in a `WHERE` clause by executing the inner query first to provide a result for the outer query. These nested queries come in three main structural types—scalar, list, and table—each returning a different kind of result that dictates how the outer query interacts with it, with correlated subqueries offering per-row logic but at a potential performance cost.
[Timestamp: 2026/03/10 10:19:40]

**user**: Make it shorter
[Timestamp: 2026/03/10 10:22:19]

**ai**: Subqueries are full `SELECT` statements, always enclosed in parentheses, where the inner query executes first, and they can be placed in `SELECT`, `FROM`, `WHERE`, or `HAVING` clauses.
[Timestamp: 2026/03/10 10:22:21]