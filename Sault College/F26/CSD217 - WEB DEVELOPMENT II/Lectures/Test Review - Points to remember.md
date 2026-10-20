# JavaScript
- `let` and `const` are **block-scoped** (they only exist inside the `{ }` they were declared in)

```javascript
let x = 10;      // block-scoped, mutable
const y = 20;    // block-scoped, immutable
var z = 30;      // function-scoped, mutable
```

>[!attention] Attention
>Avoid `var` in new code — it's kept mostly for compatibility with old scripts.

- `Undefined` is the default value of an uninitialized variable

> [!warning] Common mistake
> `typeof null` returns `"object"`, not `"null"`.

```javascript
// Pure
function increment(x) { return x + 1; }

// Side effect
function logAndReturn(x) {
  console.log(x);
  return x;
}
```

| Operator | Example         | Result  |
| -------- | --------------- | ------- |
| `+`      | `5 + 3`         | `8`     |
| `*`      | `2 * 4`         | `8`     |
| `&&`     | `true && false` | `false` |
| `==`     | `5 == "5"`      | `true`  |
| `===`    | `5 === "5"`     | `false` |
```javascript
console.log(Boolean(0));    // false
console.log(Boolean(""));   // false
console.log(Boolean([]));   // true
console.log(Boolean({}));   // true
```

```javascript
console.log(0 == false);   // true
console.log(0 === false);  // false
```

- `==` (loose equality) converts the values to a common type before comparing, which can lead to surprising results.
- `===` (strict equality) compares both value **and** type, with no conversion.

```javascript
function expensive() { console.log("expensive"); return 42; }
let x = false || expensive();  // logs "expensive"
```

With `||`, if the left side is falsy, JavaScript evaluates the right side too. With `&&`, if the left side is falsy, the right side is skipped entirely (and vice versa for `||` with a truthy left side).

```javascript
// Declaration
function foo() { return "declaration"; }

// Expression
const bar = function() { return "expression"; };
```

> [!important]
> Function **declarations** are hoisted (you can call them before they appear in the code). Function **expressions** are not — the variable holding them must be assigned first.

You can give a function parameter a fallback value, used only if no argument is passed for it:

```javascript
function greet(name = "Guest") {
  return `Hi, ${name}!`;
}
console.log(greet()); // Hi, Guest!
```

---
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

---
A **closure** happens when a function "remembers" the variables from the scope it was created in, even after that outer scope has finished running.

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

>[!note]  
>An **IIFE** is a function that is defined and called in the same expression. It's often used to create a private scope for variables, so nothing "leaks" into the surrounding code.

```javascript
(function() {
  const secret = "hidden";
  console.log(secret);
})();
```

---
Inside a regular function used as an object method, `this` refers to the object the method was called on:

```javascript
const obj = {
  x: 10,
  getX: function() { return this.x; }
};
console.log(obj.getX()); // 10
```

> [!warning] Common mistake
> Arrow functions do **not** get their own `this` — they inherit it from the surrounding (lexical) scope. This is usually what you want inside something like `.map()`, but it means arrow functions are a poor choice for object methods that need to refer to the object itself via `this`.


```javascript
book.year = 2018;
book.pages = 480;
delete book.pages;  // remove property
```

Properties can be added, changed, or removed at any time — objects in JavaScript are mutable by default.

```javascript
console.log(book.title);       // dot
console.log(book["author"]);   // bracket
```

> [!tip]
> Use bracket notation when the property name is stored in a variable, contains spaces, or isn't known until the code runs — e.g. `book[someVariable]`. Otherwise, dot notation is more readable.

| Method     | What it does                                                                  |
| ---------- | ----------------------------------------------------------------------------- |
| `map()`    | Transforms each item, returns a new array of the same length                  |
| `filter()` | Keeps only the items that pass a test, returns a (possibly shorter) new array |
| `reduce()` | Combines all items into a single value (like a total)                         |
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

Destructuring lets you unpack values from an object or array straight into variables:

```javascript
const { title, author } = book;
console.log(title, author);
```

The `...` (rest) syntax can also be used while destructuring, to collect "everything else" into a new array:

```javascript
const [first, ...rest] = [1, 2, 3, 4];
console.log(first, rest); // 1 [2,3,4]
```

Spread (`...`) can be used to build a new array or object by expanding an existing one, without mutating the original:

```javascript
const moreColors = [...colors, "orange"];
const newBook = { ...book, year: 2022 };
```