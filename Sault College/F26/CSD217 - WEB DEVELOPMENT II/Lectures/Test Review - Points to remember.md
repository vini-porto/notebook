# DOM

```
http://www.saultcollege.ca/about
```

| Part             | Example        |
| ---------------- | -------------- |
| protocol         | `http`         |
| sub domain       | `www`          |
| domain           | `saultcollege` |
| top-level domain | `.ca`          |
| path             | `/about`       |

> [!tip]
> `defer` is the modern, recommended approach. You'll still see the "script at the bottom of body" trick in a lot of older code, so it helps to recognize it.

> [!important] Sandboxing
> This restriction is called **sandboxing**. It's the browser's way of balancing "let websites be genuinely useful and interactive" against "keep users safe and private." This connects closely to [[Web Security]] and [[Authentication]] as topics.

>[!NOTe] DOM Analogy
>Think of the DOM as a **tree**: a structure made of connected nodes, where each node can have other nodes as its children.

| Property                                        | What it gives you                                                    |
| ----------------------------------------------- | -------------------------------------------------------------------- |
| `parentNode`                                    | the node's parent                                                    |
| `childNodes`                                    | a collection with **all** the node's children (including text nodes) |
| `children`                                      | a collection with only the children that are **elements**            |
| `firstChild` / `lastChild`                      | the first/last child node                                            |
| `previousElementSibling` / `nextElementSibling` | the closest sibling element before/after this one                    |
The whole DOM tree is reachable through one global variable: `document`.

- `document.body` — the `<body>` element
- `document.head` — the `<head>` element
---

| Method                              | What it does                                                                      |
| ----------------------------------- | --------------------------------------------------------------------------------- |
| `appendChild(node)`                 | Adds `node` as the **last child** of the node it's called on                      |
| `insertBefore(node, nodeToPrecede)` | Inserts `node` immediately before `nodeToPrecede`                                 |
| `replaceChild(node, nodeToReplace)` | Swaps `nodeToReplace` out for `node`                                              |
| `cloneNode(deep)`                   | Makes a copy of the node. If `deep` is `true`, all its child nodes are cloned too |

> [!warning] A node can only exist in one place at a time
> A DOM node can only occupy **one spot** in the tree. If you `appendChild` or `insertBefore` a node that *already exists elsewhere* in the DOM, it gets **moved** to the new position — it does **not** get duplicated.
>
> ```html
> <div id="myDiv">
>   <p id="a">A</p>
>   <p id="b">B</p>
>   <p id="c">C</p>
> </div>
> <script>
>   // Move paragraph A to the end
>   document.getElementById('myDiv')
>     .appendChild(document.getElementById('a'));
> </script>
> ```
> After this runs, the order becomes B, C, A — paragraph A wasn't copied, it was relocated. If you actually want a copy, use `cloneNode()` first.

> [!warning] Common mistake
> All CSS values set through JavaScript must be **strings** — and for anything that needs a unit (like width or margin), don't forget to include it (`"75px"`, not `75`). Leaving out the unit is a very easy mistake to make.


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

# TypeScript

You can also state the type directly, which matters most for function parameters or a variable that starts without a value:

```ts
let count: number = 42;
```

Arrays hold values of one type:

```ts
let numbers: number[] = [1, 2, 3];
let strings: Array<string> = ["a", "b"];
```

A **tuple** is an array with a fixed length where each position has its own type, handy for something like a `[key, value]` pair coming back from an API:

```ts
let person: [string, number] = ["Alice", 30];
```

- `any`: disables type checking for that value entirely.
- `unknown`: also accepts any value, but forces you to narrow it (check what it actually is) before you can use it.

> [!warning] Avoid `any` in frontend and backend code alike
> `any` is easy to reach for when a third-party library or an API response doesn't have types, but it silently turns off checking everywhere that value flows. Prefer `unknown` and narrow it, or write a proper interface for the response shape.

`never` marks a function that never returns normally, either because it always throws or loops forever:

```ts
function fail(message: string): never {
  throw new Error(message);
}
```

A **literal type** restricts a value to one exact set of options, useful for things like a fixed set of HTTP methods or UI states:

```ts
type Direction = "up" | "down" | "left" | "right";
```

An **enum** names a fixed set of related constants:

```ts
enum Color { Red, Green, Blue }
```

> [!note] Literal types vs enums
> Literal unions are a compile-time-only check that leaves no trace in the compiled JavaScript. Enums generate an actual JavaScript object at runtime.

Interface syntax:

```ts
interface Person {
  name: string;
  age: number;
}
```

Class Syntax:

```ts
class Animal {
  constructor(public name: string) {}
  speak() { console.log(`${this.name} makes a noise`); }
}
```

A class can extend another one, reusing its properties and methods and overriding what needs to change:

```ts
class Dog extends Animal {
  speak() { console.log(`${this.name} barks`); }
}
```

| Modifier           | Visibility                                               |
| ------------------ | -------------------------------------------------------- |
| `public` (default) | Accessible from anywhere                                 |
| `private`          | Only accessible inside the declaring class               |
| `protected`        | Accessible inside the declaring class and its subclasses |

```ts
class Calculator {
  add(a: number, b: number): number;
  add(a: string, b: string): string;
  add(a: any, b: any): any { return a + b; }
}
```

The first two lines declare the valid call signatures; the third is the actual implementation, which has to handle every case those signatures allow.

---

Generic Function

```ts
function identity<T>(arg: T): T { return arg; }
```

Generic Interface

```ts
interface Box<T> {
  content: T;
}
```

---
Built-in helpers that transform an existing type into a new one:

| Utility        | Effect                                                                       |
| -------------- | ---------------------------------------------------------------------------- |
| `Partial<T>`   | Makes every property of `T` optional, useful for a PATCH-style update object |
| `Readonly<T>`  | Makes every property of `T` read-only                                        |
| `Record<K, V>` | Builds an object type with keys of type `K` and values of type `V`           |
An **intersection** combines multiple types into one that must satisfy all of them:

```ts
type Admin = { level: number };
type User = { name: string };
type SuperUser = Admin & User;
```

A [[Union Types|union]] means a value can be one of several types:

```ts
type ID = number | string;
```


---
A **type guard** narrows a union down to one specific type inside a conditional block, so the code after the check can safely assume that narrower type:

- `typeof` for primitives (`typeof value === "string"`)
- `instanceof` for class instances
- Custom guard functions for anything more specific, such as checking the shape of an API response before trusting it


```ts
type IsString<T> = T extends string ? true : false;
```

Conditional types pick between two types based on whether `T` matches a condition, similar to a ternary operator, but evaluated at the type level instead of at runtime.


```ts
const name = user?.profile?.name;
```

If `user` or `user.profile` is `null` or `undefined`, the whole expression short-circuits to `undefined` instead of throwing, which matters a lot when rendering UI from data that might not have loaded yet.


```ts
const value = input ?? defaultValue;
```

Falls back to `defaultValue` only when `input` is `null` or `undefined`, unlike `||`, which would also fall back on `0`, `""`, or `false`, a distinction that matters for things like a quantity field where `0` is a valid, meaningful value.

# JavaScript Events

> [!note] `load` vs `DOMContentLoaded`
> Both are used for "run this once the page is ready" code. The difference is how long they wait:
> - `DOMContentLoaded` fires as soon as the HTML has been parsed into the [[DOM]]. Images might still be downloading.
> - `load` waits until *everything* is done, images and stylesheets included, so it fires later.
>
> If your code only needs to find and change elements, `DOMContentLoaded` is usually enough.

Timers are not user events, but they also run code "later" instead of right away, so they belong in the same family of ideas.

## `setTimeout()`: run code once

```javascript
let timerId = setTimeout(funcToCall, millisecondsToWait);
```

- The first parameter is a **function object** that gets called after the time has passed.
- The second parameter is how many **milliseconds** to wait before calling it (1000 ms = 1 second).
- The return value (`timerId`) identifies this timer. If you change your mind before the time runs out, cancel it with `clearTimeout(timerId)`.

> [!warning] Common mistake
> Pass the function itself, not the result of calling it.
> - `setTimeout(sayHi, 1000)` is correct: JavaScript calls `sayHi` after 1 second.
> - `setTimeout(sayHi(), 1000)` calls `sayHi` *immediately* and hands its return value (usually `undefined`) to `setTimeout`.

> [!note] Definition
> An **[[Event Listeners|event listener]]** (also called an *event handler*) is a function that runs when a specific event happens. It's attached to a DOM element, and it can be defined either inline in the HTML or through JavaScript.

```html
<button id="jsBtn">JS Click</button>
<script>
  const btn = document.getElementById('jsBtn');
  btn.addEventListener('click', () => {
    alert('Clicked via JS!');
  });
</script>
```

The pattern is always the same:

1. Find the element.
2. Call `element.addEventListener(eventType, handlerFunction)`.

This keeps structure and behavior separate, and an element can have **more than one listener** for the same event.


```html
<button id="multiBtn">Multi Listener</button>
<script>
  const btn = document.getElementById('multiBtn');
  btn.addEventListener('click', () => console.log('First listener'));
  btn.addEventListener('click', () => console.log('Second listener'));
</script>
```

Clicking the button prints `First listener` and then `Second listener`. Listeners run in the **order they were registered**.

---

When an event fires, the browser automatically passes an **[[Event Object|event object]]** to your handler. It holds information about what happened.

```html
<input id="keyInput" placeholder="Press a key" />
<script>
  const input = document.getElementById('keyInput');
  input.addEventListener('keydown', event => {
    console.log(`Key pressed: ${event.key}`);
  });
</script>
```

| Property | What it tells you |
|---|---|
| `event.key` | Which key was pressed (for keyboard events) |
| `event.target` | The element where the event actually happened |
| `event.type` | The event's name, like `"click"` or `"keydown"` |
> [!note] The three phases of an event
> 1. **Capturing phase.** The browser travels *down* the [[DOM]] tree, from the outermost element (`window`, `document`) toward the target element. Listeners configured for capturing run on the parent elements during this trip, *before* the target's own listeners.
> 2. **Target phase.** The event reaches the element that was actually clicked, and the listeners on that element run.
> 3. **Bubbling phase (the default).** The event travels back *up* from the target to its parent, then the grandparent, and so on until the root of the DOM. Normal listeners (the ones you add without options) run during this phase.

## Event bubbling demo

```html
<ul id="list">
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ul>
<script>
  const list = document.getElementById('list');
  list.addEventListener('click', event => {
    console.log(`Clicked on ${event.target.textContent}`);
  });
</script>
```

There is **one** listener, on the `<ul>`. Clicking any `<li>` still triggers it, because the click bubbles up from the `<li>` to the `<ul>`. Bubbling lets a single listener on the parent replace one listener per child.

Sometimes you *don't* want the parent to react when the child is clicked. `event.stopPropagation()` stops the event from bubbling any further.

```html
<div id="parent" style="padding:20px;background:#eee;">
  <button id="child">Child Button</button>
</div>
<script>
  const child = document.getElementById('child');
  const parent = document.getElementById('parent');
  child.addEventListener('click', event => {
    console.log('Child clicked');
    event.stopPropagation();
  });
  parent.addEventListener('click', () => {
    console.log('Parent clicked');
  });
</script>
```

> [!warning] Don't confuse these two
> - `preventDefault()` cancels the **browser's built-in action** (navigation, form submit). The event still bubbles.
> - `stopPropagation()` stops the **event from traveling** to parent elements. The browser's built-in action still happens.
>
> They solve different problems, and sometimes you need both.

## Listening during the capture phase

By default, listeners run in the bubbling phase. Passing `{ capture: true }` as a third argument makes the listener run during the capturing phase instead:

```html
<div id="capturing">
  <button id="capturedBtn">Capture</button>
</div>
<script>
  const btn = document.getElementById('capturedBtn');
  btn.addEventListener('click', e => console.log('Captured listener'), { capture: true });
  btn.addEventListener('click', e => console.log('Bubbling listener'));
</script>
```

The `capture: true` listener runs **before** the bubbling listeners, so the output is `Captured listener` and then `Bubbling listener`.

## Event delegation

> [!note] Definition
> **[[Event Delegation]]** means putting *one* listener on a parent element instead of one on each child, then using `event.target` to figure out which child was actually clicked. It works because of bubbling.

```html
<div id="menu">
  <a href="#" data-page="home">Home</a>
  <a href="#" data-page="about">About</a>
  <a href="#" data-page="contact">Contact</a>
</div>
<script>
  const menu = document.getElementById('menu');
  menu.addEventListener('click', event => {
    if (event.target.matches('a')) {
      event.preventDefault();
      console.log(`Navigate to ${event.target.dataset.page}`);
    }
  });
</script>
```

What each piece does:

- `event.target.matches('a')` checks whether the clicked element matches a [[CSS]] selector. Clicks on empty space inside the `<div>` are ignored.
- `event.preventDefault()` stops the `href="#"` link from jumping to the top of the page.
- `event.target.dataset.page` reads the custom `data-page` attribute (the `data-` attributes from last week).

> [!note] Debounce
> **[[Debouncing]]** waits until the events *stop* for a set amount of time, then runs the handler once. Every new event resets the countdown.

> [!note] Throttle
> **[[Throttling]]** lets the handler run at most once every `ms` milliseconds, no matter how many events arrive in between.

> [!tip] A way to remember the difference
> Debounce is like an elevator door: every new person resets the timer, and it only closes once people stop arriving. Throttle is like a bus that leaves every 10 minutes no matter how many people show up.

## Defining a custom event

```javascript
const myEvent = new Event('myCustom', {
  bubbles: true,
  cancelable: true
});
```

| Option | Meaning |
|---|---|
| `bubbles` | Lets the event propagate up to parent elements |
| `cancelable` | Lets a listener stop it with `preventDefault()` |
> [!warning] Custom events don't bubble by default
> If you leave out `bubbles: true`, the event only reaches listeners on the element that dispatched it. Parents won't hear it.

