# The Holy Trinity of the Web

Every website you have ever used is built from the same three technologies working together. A useful way to think about them:

|Technology|Role|Analogy|
|---|---|---|
|**HTML**|Structure & Content|The skeleton (nouns)|
|**CSS**|Appearance & Style|The skin and clothes (adjectives)|
|**JavaScript**|Logic & Behaviour|The brain and muscles (verbs)|

JavaScript is what allows a page to _do_ things:

> [!tip] What JS can do
> - Change HTML content and CSS styles while the page is live
> - Show and hide elements (dropdown menus, modals, tooltips)
> - Validate form input before it gets submitted
> - Fetch live data from external sources (weather, prices, news)

> [!warning] What JS cannot do
> JavaScript runs inside a protected "sandbox" inside your browser. This is a deliberate security feature. It means JS cannot read files from your computer's hard drive, and it cannot access hardware like your webcam or microphone without your explicit permission. This protects you from malicious websites.

# Connecting JavaScript to Your HTML Page

There are two ways to add JavaScript to a page, just like there are two ways to add CSS.

**Internal JS** is written directly inside a `<script>` tag in your HTML file. Fine for testing, not ideal for real projects:

```html
<script>
  alert('Welcome to class!');
</script>
```

**External JS** is the professional approach. Your JavaScript lives in a separate `.js` file (commonly named `app.js` or `script.js`) and is linked via a `<script>` tag with a `src` attribute:

```html
<script src="app.js"></script>
```

> [!TIP] The core principle behind external files: 
> **keep your structure (HTML) separate from your logic (JS)**. It keeps projects organised and makes both files easier to read and debug.

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

**Why?** Browsers read code from top to bottom. If your `<script>` tag is in the `<head>`, the JavaScript runs before any HTML elements have been drawn on the page.

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
birthday = 'Feb 2'; // ERROR! Cannot reassign a const.
```

A practical guideline: default to `const` for everything, and only use `let` when you know the value will need to change (like a running score, a counter, or a toggle state).

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
> When you use `+` on **strings**, JavaScript does not add them, it _concatenates_ (joins) them end-to-end. `'5' + '5'` gives you the text string `'55'`, not the number `10`.

### Booleans — True/False Logic

	The simplest type. A boolean can only ever be one of two values: `true` or `false`. Used for on/off switches and decision-making:

```javascript
let isHungry = true;
let isDarkModeOn = false;
```

---

## The DOM — JavaScript's Map of Your Page

This is arguably the most important concept in client-side web development. When a browser reads your HTML file, it does not just display it on screen. It builds a **tree structure in memory** called the **Document Object Model (DOM)**. Every HTML tag becomes a node on this tree — parent elements branch into child elements, all the way down.

JavaScript can **climb this tree, grab any element, and modify it** — changing its text, its styles, its attributes, or even its existence on the page. If HTML is the physical house, the DOM is the live blueprint that JavaScript uses to renovate rooms on the fly without ever tearing the whole building down.

### Grabbing an Element

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

## Events — Making the Page React to the User

Functions that only run when you manually call them are useful but limited. The real power of JavaScript comes from connecting functions to **events** — actions the user takes, like clicking a button, hovering over an element, or pressing a key.

The tool for this is the **Event Listener**. You attach it to an element and tell it: when this specific thing happens, run this specific function.

```javascript
button.addEventListener('click', sayHello);
```

### The Three Questions to Ask Every Time

When writing an event listener, answer these three questions:

1. **Who are we listening to?** — Which element? (`button`)
2. **What are we listening for?** — Which event? (`'click'`)
3. **What do we run when it happens?** — Which function? (`sayHello`)

> [!warning] Don't Add Parentheses to the Function Name in a Listener
> 
> ```javascript
> button.addEventListener('click', sayHello);  // CORRECT
> button.addEventListener('click', sayHello()); // WRONG!
> ```
> 
> Without parentheses, you are passing the _function itself_ as the callback — the listener will call it when the click happens. With parentheses, you are calling the function _immediately_ at the moment the listener is set up, and passing its _return value_ instead. The second version almost never does what you want.

### Common Event Types

|Event|When it fires|
|---|---|
|`'click'`|User clicks the element|
|`'mouseover'`|User's cursor enters the element|
|`'mouseout'`|User's cursor leaves the element|
|`'keydown'`|User presses any key on the keyboard|

---

## The Complete Pattern: Putting It All Together

Every interactive JavaScript feature follows the same four-step flow. Once you have this pattern in your head, you can build almost anything at this level:

> [!important] The Four-Step Interactive Pattern
> 
> 1. **Grab the button** (or whatever triggers the action) with `getElementById`
> 2. **Grab the element(s) you want to change** with `getElementById`
> 3. **Write a function** that makes the changes
> 4. **Attach an event listener** to the trigger element

> [!example] Full Example: Interactive Status App **HTML:**
> 
> ```html
> <body>
>   <h1 id="main-title">Awaiting Orders...</h1>
>   <p  id="worker-status">Status: Idle</p>
>   <button id="activate-btn">Activate!</button>
> 
>   <script src="app.js"></script>
> </body>
> ```
> 
> **JavaScript (app.js):**
> 
> ```javascript
> // Step 1 & 2: Grab all elements we need
> const activateButton = document.getElementById('activate-btn');
> const title          = document.getElementById('main-title');
> const status         = document.getElementById('worker-status');
> 
> // Step 3: Write the function
> function activateWorker() {
>   title.innerText          = 'ACTIVATED!';
>   title.style.color        = 'green';
>   status.innerText         = 'Status: Working';
>   activateButton.style.display = 'none'; // Hide the button after click
> }
> 
> // Step 4: Listen for the click
> activateButton.addEventListener('click', activateWorker);
> ```

---

## Lecture Summary

> [!tip] The Four Tools You Learned Today
> 
> ```javascript
> // 1. Debug — check your code is running and inspect values
> console.log('message or variable');
> 
> // 2. Variables — store and remember data
> let score = 0;
> const playerName = 'Sam';
> 
> // 3. DOM — grab and change elements on the page
> let el = document.getElementById('some-id');
> el.innerText = 'New content';
> el.style.color = 'blue';
> 
> // 4. Events — react to what the user does
> el.addEventListener('click', myFunction);
> ```

For further reading and interactive practice: [MDN JavaScript Guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide) | [W3Schools JavaScript](https://www.w3schools.com/js/) | [JavaScript on CS50](https://cs50.harvard.edu/)

---

## Tags

#javascript #js #webdev #web #programming #dom #events #variables #functions #frontend #html