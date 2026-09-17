# Overview

This lecture covers the first four chapters of *Eloquent JavaScript*: the absolute basics of [[JavaScript]] as a language — variables, data types, operators, functions, and the two core data structures (objects and arrays).

> [!note] What you'll learn
> - The building blocks of a JavaScript program (variables, values, operators)
> - How functions work, including arrow functions and closures
> - How to structure a program (scope, blocks, `this`)
> - How to store and work with collections of data (objects and arrays)

# Chapter 1 – Hello, World!

## Variables

> [!note] Definition
> A **variable** is a named container for a value. JavaScript gives you three ways to declare one, and they behave differently.

```javascript
let x = 10;      // block-scoped, mutable
const y = 20;    // block-scoped, immutable
var z = 30;      // function-scoped, mutable
```

- `let` and `const` are **block-scoped** (they only exist inside the `{ }` they were declared in). This is the modern standard, introduced in ES2015.
- `const` cannot be reassigned after it's set — use it as your default choice unless you know the value needs to change.
- `var` is **function-scoped** and **hoisted** (JavaScript "moves" the declaration to the top of the function behind the scenes). It's the old way of declaring variables.

> [!tip]
> Default to `const`. Switch to `let` only when you know the variable's value needs to change. Avoid `var` in new code — it's kept mostly for compatibility with old scripts.

## Primitive data types

| Type | Example | Notes |
|---|---|---|
| Number | `42`, `3.14` | `typeof` returns `"number"` |
| String | `"Hello"` | text data |
| Boolean | `true` | used with `===` vs `==` comparisons |
| Null | `null` | `typeof null === "object"` — a well-known quirk of the language |
| Undefined | `undefined` | the default value of an uninitialized variable |

> [!warning] Common mistake
> `typeof null` returns `"object"`, not `"null"`. This is a long-standing bug in JavaScript that was never fixed, because fixing it would break too much existing code. Just memorize it as an exception.

## The console — debugging basics

```javascript
console.log("Debug message");
console.warn("Warning!");
console.error("Error occurred");
```

- `console.log()` — general-purpose output
- `console.warn()` — flags something as a warning (usually shown in yellow)
- `console.error()` — flags something as an error (usually shown in red)

> [!tip]
> Use `console.table()` to print arrays or objects as a readable table — much easier to scan than a wall of nested `{ }` in the console.

## Arithmetic operators

```javascript
let sum = 7 + 3;          // 10
let diff = 10 - 4;        // 6
let prod = 2 * 5;         // 10
let quo = 9 / 3;          // 3
let rem = 10 % 3;         // 1
```

The `%` (modulo) operator returns the **remainder** of a division, not the quotient — it's useful for things like checking if a number is even or odd.

## Basic Math functions

The built-in `Math` object bundles common numeric constants and functions:

```javascript
Math.PI;          // 3.141592653589793
Math.pow(2, 3);   // 8
Math.sqrt(16);    // 4
Math.random();    // 0 <= x < 1
```

## Functions — the first step

> [!note] Definition
> A **function** is a reusable block of code that can take inputs (parameters) and produce an output (a return value).

```javascript
function greet(name) {
  return `Hi, ${name}!`;
}
console.log(greet("Ada"));  // Hi, Ada!
```

## Arrow functions

Arrow functions (`=>`) are a shorter way to write a function, introduced in ES2015:

```javascript
const square = x => x * x;
console.log(square(4));  // 16
```

## Return values

A function's `return` statement is what sends a value back to whoever called it. Once `return` runs, the function stops executing immediately.

```javascript
function add(a, b) {
  return a + b;
}
const result = add(3, 5);
console.log(result);  // 8
```

## Side effects vs pure functions

> [!important] Pure vs impure
> A **pure function** always returns the same output for the same input, and doesn't change anything outside itself. A function has a **side effect** when it does something beyond returning a value — like printing to the console, changing a variable outside its own scope, or modifying the page.

```javascript
// Pure
function increment(x) { return x + 1; }

// Side effect
function logAndReturn(x) {
  console.log(x);
  return x;
}
```

> [!tip]
> Pure functions are easier to test and reason about, because you never have to worry about what else they might be doing. Prefer them when you can.

## Summary of Chapter 1

- Variables (`let`, `const`, `var`)
- Primitive types
- Console API
- Math operators and the `Math` object
- Functions and arrow syntax

# Chapter 2 – Values, Variables, and Operators

## Arithmetic vs logical vs comparison operators

| Operator | Example | Result |
|---|---|---|
| `+` | `5 + 3` | `8` |
| `*` | `2 * 4` | `8` |
| `&&` | `true && false` | `false` |
| `==` | `5 == "5"` | `true` |
| `===` | `5 === "5"` | `false` |

## Operator precedence

Just like in math class, some operators run before others.

```javascript
let val = 2 + 3 * 4;    // 14
let val2 = (2 + 3) * 4; // 20
```

- Multiplication happens before addition.
- Use parentheses `()` to force a different order.

## Truthiness — what counts as "true"?

Every JavaScript value is either **truthy** or **falsy** when used somewhere a boolean is expected (like an `if` condition).

```javascript
console.log(Boolean(0));    // false
console.log(Boolean(""));   // false
console.log(Boolean([]));   // true
console.log(Boolean({}));   // true
```

> [!warning] Common mistake
> An empty array `[]` and an empty object `{}` are both **truthy**, even though they feel "empty." Only a small, fixed set of values are falsy: `0`, `""`, `null`, `undefined`, `NaN`, and `false` itself.

## Equality — `==` vs `===`

```javascript
console.log(0 == false);   // true
console.log(0 === false);  // false
```

- `==` (loose equality) converts the values to a common type before comparing, which can lead to surprising results.
- `===` (strict equality) compares both value **and** type, with no conversion.

> [!tip]
> Prefer strict equality (`===`) by default. It avoids the hidden type-conversion surprises that `==` can cause.

## Assignment operators

Shorthand operators that combine an operation with assignment:

```javascript
let a = 5;
a += 3;  // a = 8
a -= 2;  // a = 6
a *= 4;  // a = 24
a /= 3;  // a = 8
```

## Conditional (ternary) operator

A compact one-line alternative to a simple `if / else`:

```javascript
let age = 20;
let status = age >= 18 ? "adult" : "minor";
console.log(status); // adult
```

The pattern is: `condition ? valueIfTrue : valueIfFalse`.

## Logical operators — short-circuiting

> [!note] Definition
> **Short-circuiting** means JavaScript stops evaluating a logical expression as soon as the result is already determined.

```javascript
function expensive() { console.log("expensive"); return 42; }
let x = false || expensive();  // logs "expensive"
```

With `||`, if the left side is falsy, JavaScript evaluates the right side too. With `&&`, if the left side is falsy, the right side is skipped entirely (and vice versa for `||` with a truthy left side).

## The `in` operator

Checks whether a given property name exists on an object:

```javascript
let obj = {a: 1, b: 2};
console.log("a" in obj); // true
console.log("c" in obj); // false
```

## The `instanceof` operator

Checks whether an object was created from a particular class:

```javascript
class Person {}
let p = new Person();
console.log(p instanceof Person); // true
```

## Summary of Chapter 2

- Arithmetic, logical, and comparison operators
- Precedence and associativity
- Truthiness and equality
- Assignment and conditional operators
- Short-circuit logic

# Chapter 3 – Program Structure

## Block statements and scope

```javascript
if (true) {
  let local = "I exist only here";
}
console.log(local); // ReferenceError
```

`let` and `const` are **block-scoped** — meaning a variable declared inside `{ }` (like inside an `if` block) doesn't exist outside of it.

## Function declarations vs expressions

```javascript
// Declaration
function foo() { return "declaration"; }

// Expression
const bar = function() { return "expression"; };
```

> [!important]
> Function **declarations** are hoisted (you can call them before they appear in the code). Function **expressions** are not — the variable holding them must be assigned first.

## Default parameters

You can give a function parameter a fallback value, used only if no argument is passed for it:

```javascript
function greet(name = "Guest") {
  return `Hi, ${name}!`;
}
console.log(greet()); // Hi, Guest!
```

## Rest and spread syntax

The same `...` syntax means two different things depending on where it's used:

- **Rest** (in a function's parameter list): gathers any number of arguments into a single array.
- **Spread** (inside an array or object literal): expands an existing array/object out into individual elements.

```javascript
function sum(...nums) {
  return nums.reduce((a, b) => a + b, 0);
}
console.log(sum(1, 2, 3)); // 6

const arr1 = [1, 2];
const arr2 = [3, 4];
const combined = [...arr1, ...arr2]; // [1,2,3,4]
```

## Return values — function purity

```javascript
// Pure
function double(x) { return x * 2; }

// Side effect
function incrementAndLog(x) {
  console.log(x + 1);
  return x + 1;
}
```

## Nested functions and closures

> [!note] Definition
> A **closure** happens when a function "remembers" the variables from the scope it was created in, even after that outer scope has finished running.

```javascript
function makeCounter() {
  let count = 0;
  return function() {
    count += 1;
    return count;
  };
}
const counter = makeCounter();
console.log(counter()); // 1
console.log(counter()); // 2
```

Here, the inner function keeps access to `count` even though `makeCounter()` already finished running — that's the closure at work.

## IIFEs — Immediately Invoked Function Expressions

> [!note] Definition
> An **IIFE** is a function that is defined and called in the same expression. It's often used to create a private scope for variables, so nothing "leaks" into the surrounding code.

```javascript
(function() {
  const secret = "hidden";
  console.log(secret);
})();
```

## The `this` keyword

Inside a regular function used as an object method, `this` refers to the object the method was called on:

```javascript
const obj = {
  x: 10,
  getX: function() { return this.x; }
};
console.log(obj.getX()); // 10
```

## Arrow functions and `this`

```javascript
const arr = [1, 2, 3];
const squares = arr.map(n => n * n); // arrow keeps lexical this
```

> [!warning] Common mistake
> Arrow functions do **not** get their own `this` — they inherit it from the surrounding (lexical) scope. This is usually what you want inside something like `.map()`, but it means arrow functions are a poor choice for object methods that need to refer to the object itself via `this`.

## Summary of Chapter 3

- Block scope and hoisting
- Function types and default parameters
- Rest and spread syntax
- Purity and side effects
- Closures and IIFEs
- `this` in regular functions vs arrow functions

# Chapter 4 – Data Structures

## Objects — key/value pairs

> [!note] Definition
> An **object** is a collection of key/value pairs (called properties). Objects can also hold functions as properties — these are called **methods**.

```javascript
const book = {
  title: "Eloquent JavaScript",
  author: "Marijn Haverbeke",
  pages: 472,
  isBestSeller() { return true; }
};
console.log(book.title);          // Eloquent JavaScript
console.log(book.isBestSeller()); // true
```

## Adding and updating properties

```javascript
book.year = 2018;
book.pages = 480;
delete book.pages;  // remove property
```

Properties can be added, changed, or removed at any time — objects in JavaScript are mutable by default.

## Dot notation vs bracket notation

```javascript
console.log(book.title);       // dot
console.log(book["author"]);   // bracket
```

> [!tip]
> Use bracket notation when the property name is stored in a variable, contains spaces, or isn't known until the code runs — e.g. `book[someVariable]`. Otherwise, dot notation is more readable.

## Arrays — ordered lists

```javascript
const colors = ["red", "green", "blue"];
console.log(colors[0]); // red
colors.push("yellow");
console.log(colors); // [red, green, blue, yellow]
```

An **array** is an ordered list of values, accessed by numeric index starting at `0`.

## Array methods — `map`, `filter`, `reduce`

These three methods are among the most commonly used tools for working with arrays without writing manual loops:

```javascript
const nums = [1, 2, 3, 4];
const squares = nums.map(n => n * n);          // [1,4,9,16]
const evens = nums.filter(n => n % 2 === 0);   // [2,4]
const sum = nums.reduce((a, b) => a + b, 0);   // 10
```

| Method | What it does |
|---|---|
| `map()` | Transforms each item, returns a new array of the same length |
| `filter()` | Keeps only the items that pass a test, returns a (possibly shorter) new array |
| `reduce()` | Combines all items into a single value (like a total) |

## `for...of` vs `for...in`

```javascript
for (const color of colors) {
  console.log(color);
}

for (const key in book) {
  console.log(key, book[key]);
}
```

> [!warning] Common mistake
> `for...of` loops over the **values** of an iterable (like an array). `for...in` loops over the **keys** (property names) of an object. Mixing these up is a very common source of bugs — using `for...in` on an array, for instance, gives you the indexes as strings rather than the values.

## Destructuring — pulling out values

Destructuring lets you unpack values from an object or array straight into variables:

```javascript
const { title, author } = book;
console.log(title, author);
```

## Rest in destructuring

The `...` (rest) syntax can also be used while destructuring, to collect "everything else" into a new array:

```javascript
const [first, ...rest] = [1, 2, 3, 4];
console.log(first, rest); // 1 [2,3,4]
```

## Spread in arrays and objects

Spread (`...`) can be used to build a new array or object by expanding an existing one, without mutating the original:

```javascript
const moreColors = [...colors, "orange"];
const newBook = { ...book, year: 2022 };
```

> [!tip]
> Spreading is the standard way to create a copy of an array or object (with optional changes) instead of mutating the original directly — a habit that becomes important once you start working with frameworks like [[React]].

# External resources

- [MDN: JavaScript guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide)
- [Eloquent JavaScript (free online book)](https://eloquentjavascript.net)
- [W3Schools: JavaScript tutorial](https://www.w3schools.com/js/)
- [GeeksforGeeks: JavaScript basics](https://www.geeksforgeeks.org/javascript/)

# Tags

#web #programming #javascript #software
