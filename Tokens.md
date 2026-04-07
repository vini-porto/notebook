# What are tokens?

The smallest meaningful units of code that a programming language recognises. **e.g. words, punctuation marks.**

---
# Types of Tokens

[[Keywords]]: Reserved words like `if`, `for`, `while`, `class`

[[Identifiers]]: Names you create for [[variables]], [[functions]], or [[classes]] (like `userName`, `calculateTotal`)

[[Literals]]: Actual values like `42`, `"hello"`, `true`

[[Operators]]: Symbols like `+`, `-`, `*`, `==`, `=`

**Separators/Delimiters:** Punctuation like `{`, `}`, `(`, `)`, `;`, `,`

> [!Token Importance]
> - Debug [[syntax errors]] more easily (often caused by missing or wrong tokens)
> - Learn how [[compilers]] and [[interpreters]] work
>- Write cleaner, more readable code
>- Understand error messages better

# How the compiler see

```python
age = 25
if age >= 18:
    print("Adult")
```

- `age` — identifier
- `=` — assignment operator
- `25` — numeric literal
- `if` — keyword
- `age` — identifier
- `>=` — comparison operator
- `18` — numeric literal
- `:` — delimiter
- `print` — identifier (function name)
- `(` — delimiter
- `"Adult"` — string literal
- `)` — delimiter


#programming #basics #compiler #syntax #fundamentals
