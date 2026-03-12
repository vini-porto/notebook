---
epoch: 1773240451096
modelKey: "gemini-2.5-flash|google"
topic: "Linking Notes from Content"



tags:
  - copilot-conversation
---

**user**: Create Linking notes from relevant content in this note
[Context: Notes: Sault College/W26/Winter 2026/Introduction to Web Development/6 - JavaScript.md]
[Timestamp: 2026/03/11 10:47:31]

**ai**: Here are some linking notes based on the content of your "6 - JavaScript" note, designed to expand on key concepts:

-   **[[HTML Fundamentals]]**
    -   This note could delve deeper into HTML structure, common tags, attributes, and semantic HTML, building on its role as "The skeleton (nouns)" of the web.
-   **[[CSS Styling Basics]]**
    -   Expand on CSS selectors, properties, values, and how to link stylesheets, complementing its role as "The skin and clothes (adjectives)".
-   **[[JavaScript Capabilities and Limitations]]**
    -   A dedicated note to detail more specific examples of what JavaScript *can* do (e.g., animations, complex form validation, API interactions) and reiterate its security sandbox limitations.
-   **[[Embedding JavaScript]]**
    -   This note could provide more examples and best practices for both internal and external JavaScript, including the use of `defer` and `async` attributes for script loading.
-   **[[Debugging with Console Log]]**
    -   Beyond `console.log()`, this note could introduce other console methods like `console.warn()`, `console.error()`, `console.table()`, and the general workflow of using the browser's developer tools for debugging.
-   **[[JavaScript Variables - let vs const]]**
    -   A deeper dive into variable scope (global, function, block scope), hoisting, and the implications of choosing `let` or `const` for different scenarios.
-   **[[JavaScript Naming Conventions]]**
    -   Discuss common naming conventions beyond camelCase, such as PascalCase for classes, SCREAMING_SNAKE_CASE for constants, and general readability guidelines.
-   **[[JavaScript Data Types]]**
    -   Expand on primitive data types (Strings, Numbers, Booleans, `null`, `undefined`, `Symbol`, `BigInt`) and introduce complex data types like Objects and Arrays.
-   **[[Understanding the Document Object Model (DOM)]]**
    -   This note could illustrate the DOM tree structure with diagrams, explain nodes (element, text, attribute), and discuss how the browser constructs it.
-   **[[Manipulating DOM Elements]]**
    -   Beyond `getElementById`, introduce `querySelector`, `querySelectorAll`, `getElementsByClassName`, `getElementsByTagName`, and methods for creating, appending, and removing elements.
-   **[[JavaScript and CSS Property Mapping]]**
    -   A reference or guide for converting common CSS properties (e.g., `margin-left`, `text-align`) to their JavaScript camelCase equivalents.
-   **[[JavaScript Functions - Definition and Invocation]]**
    -   Explore different ways to define functions (function declarations, function expressions, arrow functions), parameters, arguments, and return values.
-   **[[JavaScript Event Listeners]]**
    -   Detail the `addEventListener` method, the event object, event bubbling and capturing, and how to remove event listeners.
-   **[[Common JavaScript Event Types]]**
    -   A comprehensive list of various event types beyond `click`, `mouseover`, `mouseout`, `keydown`, including form events, load events, and touch events.
-   **[[Building Interactive Web Features with JavaScript]]**
    -   This note could serve as a practical guide with more complex examples, demonstrating how to combine DOM manipulation, functions, and events to create dynamic user interfaces.
[Timestamp: 2026/03/11 10:47:39]

**user**: Place exactly here this links should be in this note. Don't change anything else then the place where you should place these links
[Context: Notes: Sault College/W26/Winter 2026/Introduction to Web Development/6 - JavaScript.md]
[Timestamp: 2026/03/11 10:52:27]

**ai**: ```markdown
# The Holy Trinity of the Web
Every website you have ever used is built from the same three technologies working together. A useful way to think about them:

|Technology|Role|Analogy|
|---|---|---|
|**HTML**|Structure & Content|The skeleton (nouns)|
|---|---|---|
|**CSS**|Appearance & Style|The skin and clothes (adjectives)|
|---|---|---|
|**JavaScript**|Logic & Behaviour|The brain and muscles (verbs)|

[[HTML Fundamentals]]
[[CSS Styling Basics]]
[[JavaScript Capabilities and Limitations]]

JavaScript is what allows a page to _do_ things:

...

# Connecting JavaScript to Your HTML Page
There are two ways to add JavaScript to a page, just like there are two ways to add CSS.

**Internal JS** is written directly inside a `<script>` tag in your HTML file. Fine for testing, not ideal for real projects:

```html
<script>
  alert('Welcome to class!');
</script>
```

**External JS** is the professional approach. ...

## The Critical Placement Rule
> [!important] Put your `<script>` tag at the very BOTTOM of your `<body>`, directly above the closing `</body>` tag.
> 
> ```html
> <!DOCTYPE html>
> <html>
>   <head>
>     <title>Page Title</title>
>   </head>
>   <body>
> 
>     <!-- All your visible HTML goes here -->
> 
>     <script src="app.js" defer></script>
>   </body>
> </html>
> ```

**Why?** Browsers read code from top to bottom. ...

[[Embedding JavaScript]]

# The Developer Console

>[!NOTE] To open it: 
**Right-click anywhere on a page → Inspect → Console tab.**

## console.log()

This is the single most important tool a JavaScript developer has. It sends a hidden message to the console. Invisible to users, visible to you:

```javascript
console.log('My JS file is connected and running!');
console.log(42);
console.log('The value of x is:', x);
```

Use it constantly while building. Whenever you are unsure what value a variable holds, or whether a section of code is even running, `console.log` it.

[[Debugging with Console Log]]

# Variables

You create a variable using the keyword `let`:

```javascript
let myName = 'Sam';
let currentScore = 100;
// Translation: "Let the box named myName hold the value 'Sam'."
```

## `let` vs `const` - The Key Difference
`let` creates a box whose contents **can be changed** at any time:

```javascript
let age = 30;
age = 31; // Totally fine — we just updated the value
```

`const` creates a **locked** box. The value is set once and cannot be reassigned. Trying to change it throws an error:

```javascript
const birthday = 'Jan 1'; // This will always be 'Jan 1'
birthday = 'Feb 2'; // ERROR! Cannot reassign a const. ...
```

[[JavaScript Variables - let vs const]]

## Variable Naming Rules

1. No spaces in the name
2. Cannot start with a number
3. Use **camelCase**: first word lowercase, each subsequent word starts with a capital, no spaces or underscores

```javascript
// Good
let playerHighScore = 50;
let isMenuOpen = true;

// Bad — breaks the rules
let Player high score = 50; // spaces not allowed
let 1stPlace = 'Gold';       // cannot start with a number
```

[[JavaScript Naming Conventions]]

# Data Types

## Strings

Any piece of text. Must always be wrapped in quotation marks (single or double, both are fine):

```javascript
let catBreed = 'Russian Blue';
let errorMessage = "File not found";
```

## Numbers
Used for any kind of calculation. Numbers without quotes are actual numbers that can do maths:

```javascript
let price = 15.99;
let quantity = 3;
let total = price * quantity; // JavaScript calculates this: 47.97
```

> [!warning] The Quote Trap
> 
> ```javascript
> let result1 = 5 + 5;    // → 10   (maths: addition)
> let result2 = '5' + '5'; // → '55' (text: joined together!)
> ```
> 
> When you use `+` on **strings**, JavaScript does not add them, it _concatenates_ (joins) them end-to-end. ...

## Booleans

The simplest type. A boolean can only ever be one of two values: `true` or `false`. Used for on/off switches and decision-making:

```javascript
let isHungry = true;
let isDarkModeOn = false;
```

[[JavaScript Data Types]]

# The DOM - JavaScript's Map of Your Page

This is arguably the most important concept in client-side web development. When a browser reads your HTML file, it does not just display it on screen. It builds a **tree structure in memory** called the **Document Object Model (DOM)**. 

>JavaScript can **climb this tree, grab any element, and modify it**, changing its text, its styles, its attributes, or even its existence on the page.

[[Understanding the Document Object Model (DOM)]]

## Grabbing an Element

To change something on the page, you first need to grab a reference to it. Give the HTML element an `id` attribute, then use `document.getElementById()` in your JS:

```html
<!-- HTML -->
<h1 id="title">Old Text</h1>
```

```javascript
// JS — this grabs a reference to that h1 and stores it in a variable
let heading = document.getElementById('title');
```

Now `heading` is your handle on that element. You can do anything to it.

### Changing an Element's Text

```javascript
heading.innerText = 'New Text!';
// innerText treats everything as plain text — safe and simple
```

### Injecting HTML

```javascript
let box = document.getElementById('content');
box.innerHTML = '<strong>Bold Text</strong>';
// innerHTML parses HTML tags — powerful, but use carefully
```

### Changing CSS Styles

```javascript
heading.style.color = 'red';
heading.style.fontSize = '50px';
```

> [!tip] CSS Properties Become camelCase in JavaScript In CSS you write `font-size`, `background-color`, `border-radius`. In JavaScript's `.style` property, hyphens are not allowed in names, so all CSS properties are written in **camelCase**: `fontSize`, `backgroundColor`, `borderRadius`. This trips people up constantly at first — just remember the pattern.

[[Manipulating DOM Elements]]
[[JavaScript and CSS Property Mapping]]

---

## Functions — Packaging Code into Reusable Recipes

Imagine needing to write the same 15 lines of code every single time a user does something. Functions solve this. A **function** is a named block of code that performs a specific task. You define it once and can run it as many times as you like, from anywhere in your program.

The recipe analogy is perfect here: a recipe in a cookbook does nothing until you decide to cook it.

### Defining a Function

```javascript
function sayHello() {
  console.log('Hello World');
  alert('Welcome!');
}
```

**Writing a function definition does nothing by itself.** The code inside the curly braces sits idle, waiting. It just saves the recipe.

### Calling a Function

To actually _execute_ the code inside, you **call** the function by writing its name followed by `()`:

```javascript
sayHello(); // NOW the code inside runs
```

> [!warning] Definition vs. Call — A Classic Point of Confusion
> 
> - `function sayHello() { ... }` → **defines** the function (writes the recipe)
> - `sayHello();` → **calls** the function (cooks the recipe)
> 
> Many beginners write a function and then wonder why nothing happens. They forgot to call it.

---

[[JavaScript Functions - Definition and Invocation]]

[... 6 more sections omitted ...]

[[JavaScript Event Listeners]]
[[Common JavaScript Event Types]]
[[Building Interactive Web Features with JavaScript]]
```
[Timestamp: 2026/03/11 10:54:18]