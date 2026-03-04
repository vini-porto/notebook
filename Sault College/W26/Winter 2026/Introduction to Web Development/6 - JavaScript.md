
# The Three-Layer Model of the Web

Think of building a website like building a person. **HTML** is the skeleton — it gives you bones and structure. **CSS** is the skin and clothes — it makes things look good. **JavaScript** is the brain and muscles — it makes the body _do_ things: think, react, calculate, and respond.

More precisely: HTML gives you _nouns_ (the things on the page), CSS gives you _adjectives_ (how those things look), and JavaScript gives you _verbs_ (what those things do).

### What JavaScript Can Do

JavaScript can change HTML text and CSS styles while the page is running, show or hide elements (like dropdown menus), validate form inputs before they're submitted, and even fetch live data from the internet (like weather or stock prices) without reloading the page.

### What JavaScript CANNOT Do

JavaScript runs in a "sandbox" inside your browser — it's deliberately limited for your security. It cannot read files from your hard drive, and it cannot access hardware like your webcam without first asking for your explicit permission. These restrictions are a feature, not a bug. They prevent malicious websites from doing things to your computer without your knowledge.

---

## How to Add JavaScript to Your Page

Just like CSS has internal and external versions, JavaScript does too.

**Internal JS** means you write your code directly inside your HTML file, wrapped in `<script>` tags:

```html
<script>
  alert('Welcome to class!');
</script>
```

**External JS** is the professional approach. You write your code in a separate `.js` file and link to it with a `<script>` tag that uses a `src` attribute, exactly like how CSS uses a `<link>` tag:

```html
<script src="app.js"></script>
```

The golden rule of the external approach: **keep your structure (HTML) separate from your logic (JS)**. It keeps your code clean, readable, and easier to maintain.

### Where Does the Script Tag Go?

> [!important] Always put your `<script>` tag at the very BOTTOM of the `<body>`, right above the closing `</body>` tag.
> 
> ```html
> <body>
>   <!-- All your HTML content here -->
> 
>   <script src="app.js" defer></script>
> </body>
> ```

The reason for this is straightforward: browsers read code from top to bottom. If your JavaScript loads at the top, it will try to interact with HTML elements that haven't been created yet — like trying to click a button that doesn't exist. You have to build the skeleton before you turn on the brain.

---

## Your First Tool: The Console

Before writing any real logic, you need to know how to check if your code is working. The answer is the **Developer Console** — a hidden panel in your browser that only developers see. Open it by right-clicking anywhere on the page, clicking "Inspect," and then clicking the "Console" tab.

`console.log()` is your single most important debugging tool. It prints a message to the console that users will never see:

```javascript
console.log('Hello! My JS is working.');
```

Think of it like leaving yourself notes while you write. If something isn't working, you can `console.log` a variable to see what value it holds at any given moment.

> [!tip] Red Errors Are Your Friend When your code breaks, the console displays errors in red text. This is actually helpful — it tells you the exact line number where the problem is. Read the error message carefully instead of panicking; it's pointing you directly to the fix.

---

## Variables: Giving Your Program a Memory

A program that can't remember anything isn't very useful. A **variable** is a labeled box where you store a piece of data so you can use it later — like a user's name, a score, or a price.

You create (or "declare") a variable using the keyword `let`:

```javascript
let myName = 'Sam';       // A labeled box named myName, holding 'Sam'
let currentScore = 100;   // A labeled box named currentScore, holding 100
```

### `let` vs `const` — Changeable vs Locked

This is an important distinction that trips up a lot of beginners. `let` creates a box whose contents **can change** over time:

```javascript
let age = 30;
age = 31; // Perfectly fine — we just updated the box
```

`const` creates a **locked** box. Once you put something in, it stays there forever. Trying to change it will throw an error:

```javascript
const birthday = 'Jan 1'; // This will never change
```

A good rule of thumb: use `const` by default, and only switch to `let` when you know the value will need to change (like a score or a counter).

### Naming Rules

Variable names in JavaScript follow three important rules: no spaces are allowed, they cannot start with a number, and they should use **camelCase** — where the first word is all lowercase and each new word starts with a capital letter.

```javascript
// Good — follows the rules
let playerHighScore = 50;

// Bad — breaks the rules (spaces, capital first word)
let Player high score = 50;
```

---

## Data Types: What Kind of Thing Are You Storing?

Not all data is the same. JavaScript has a few fundamental types you need to know.

**Strings** are text. They must always be wrapped in quotation marks (single or double — both work):

```javascript
let catBreed = 'Russian Blue';
let errorMsg = "File not found";
```

**Numbers** are for math. No quotes — ever. If you put quotes around a number, JavaScript treats it as text:

```javascript
let price = 15.99;
let quantity = 3;
let total = price * quantity; // total = 47.97
```

> [!warning] The Quote Trap — A Classic Beginner Mistake This is one of the most common early JavaScript bugs:
> 
> ```javascript
> let math1 = 5 + 5;    // math1 = 10  (addition)
> let math2 = '5' + '5'; // math2 = '55' (concatenation!)
> ```
> 
> When you use `+` on strings, JavaScript doesn't add them — it _joins_ them end-to-end. `'5' + '5'` gives you the text `'55'`, not the number `10`. Always be deliberate about whether your values are numbers or strings.

**Booleans** are the simplest type — they can only be `true` or `false`. They're used for on/off switches and decision-making logic:

```javascript
let isHungry = true;
let isDarkModeOn = false;
```

---

## The DOM: How JavaScript Sees Your HTML

This is one of the most important concepts in web development. When a browser reads your HTML file, it doesn't just display it — it builds a **tree structure in memory** called the **Document Object Model**, or **DOM**. Every HTML tag becomes a node (a "branch" or "leaf") on this tree.

JavaScript's superpower is that it can **climb this tree, grab any element, and change it**. If HTML is the physical house, the DOM is the blueprint that JavaScript uses to remodel it on the fly — without reloading the page.

### Step 1: Grabbing an Element

To change an element, you first need to get a reference to it. The most common way is `document.getElementById()`, which finds an element by its `id` attribute:

```html
<!-- In your HTML file -->
<h1 id="title">Old Text</h1>
```

```javascript
// In your JS file — this stores a reference to the h1 element
let heading = document.getElementById('title');
```

### Step 2: Changing It

Once you have the reference, you can change the element's text, HTML content, or CSS styles directly:

```javascript
// Change just the text (safe — treats everything as plain text)
heading.innerText = 'New Text!';

// Inject actual HTML tags (powerful but use carefully)
heading.innerHTML = '<strong>Bold New Text!</strong>';

// Change CSS styles directly
heading.style.color = 'red';
heading.style.fontSize = '50px'; // Note: CSS's font-size becomes fontSize in JS (camelCase!)
```

> [!tip] CSS Properties in JavaScript Use camelCase When you access CSS through JavaScript's `.style` property, hyphenated CSS names like `font-size` and `background-color` become camelCase: `fontSize` and `backgroundColor`. This is because hyphens aren't valid in JavaScript variable names.

---

## Functions: Packaging Your Code into Reusable Recipes

Imagine you had to write the same 10 lines of code every time a user clicked a button. That would be chaotic. A **function** solves this by packaging a block of code under a name, so you can run it whenever you want just by calling that name.

Think of it like a recipe in a cookbook — the recipe exists on the page but it doesn't cook anything until you actually decide to make it.

```javascript
// DEFINING the function (writing the recipe)
function sayHello() {
  console.log('Hello World');
  alert('Welcome!');
}
```

Writing the function definition does **nothing** by itself. It just saves the recipe. To actually _run_ the code, you have to **call** the function by writing its name followed by parentheses:

```javascript
// CALLING the function (cooking the recipe)
sayHello();
```

---

## Events: Making Your Page React to the User

Functions are useful, but right now they only run when you call them manually in your code. The real magic happens when you connect functions to **events** — things the user does, like clicking a button, hovering over an image, or pressing a key.

The tool that connects an event to a function is called an **Event Listener**. You attach it to an element and tell it: "When this specific thing happens, run this function."

```javascript
// The pattern: element.addEventListener('event name', functionToRun)
button.addEventListener('click', sayHello);
```

Notice that you write `sayHello` — **without parentheses**. This is a very important detail. Without parentheses, you're passing the _recipe_ to the listener. With parentheses (`sayHello()`), you'd be calling it immediately and passing the _result_, which is not what you want.

### The Three Questions of an Event Listener

Every time you write an event listener, answer these three questions:

1. **Who are we listening to?** (which element — e.g., the button)
2. **What are we listening for?** (which event — e.g., `'click'`)
3. **What do we run when it happens?** (which function — e.g., `sayHello`)

### Common Events

The most frequently used events are `'click'` (user clicks an element), `'mouseover'` (user hovers over it), `'mouseout'` (user's mouse leaves it), and `'keydown'` (user presses any key on the keyboard).

---

## Putting It All Together: The Complete Pattern

Every interactive JavaScript feature you'll ever build follows this same four-step pattern. Memorize it:

1. **Grab the button** using `document.getElementById()`.
2. **Grab the element(s) you want to change** using `document.getElementById()`.
3. **Write a function** that contains the code to make the change.
4. **Attach an event listener** to the button that calls your function.

> [!example] The Cat Feeder — A Full Mini-App Here's a simple but complete interactive app. When the user clicks a button, JS changes the status text, swaps the image, and hides the button.
> 
> **HTML (index.html):**
> 
> ```html
> <body>
>   <h2 id="cat-status">The Cat is hungry...</h2>
>   <img id="cat-pic" src="hungry-cat.jpg" alt="Hungry Cat">
>   <button id="feed-btn">Feed the Cat</button>
> 
>   <script src="app.js"></script>
> </body>
> ```
> 
> **JavaScript (app.js):**
> 
> ```javascript
> // Step 1 & 2: Grab all the elements we need
> const feedButton = document.getElementById('feed-btn');
> const catStatus  = document.getElementById('cat-status');
> const catPic     = document.getElementById('cat-pic');
> 
> // Step 3: Write the function that does the work
> function feedTheCat() {
>   catStatus.innerText = 'The Cat is happy!';          // Change the text
>   catPic.src = 'happy-cat.jpg';                       // Swap the image
>   feedButton.style.display = 'none';                  // Hide the button
> }
> 
> // Step 4: Listen for a click on the button, then run the function
> feedButton.addEventListener('click', feedTheCat);
> ```

---

## Quick Reference Summary

> [!tip] The Lecture in Four Lines
> 
> ```javascript
> console.log('message');                        // Debug and check values
> let x = 10;  /  const y = 'fixed';            // Store data
> document.getElementById('id').innerText = '…'; // Change the page
> button.addEventListener('click', myFunction);  // React to the user
> ```

For deeper reading on any of these topics, the MDN Web Docs are the gold standard: [MDN JavaScript Guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide) | [W3Schools JS Tutorial](https://www.w3schools.com/js/) | [The DOM on MDN](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model)

---

## Tags

#javascript #webdev #web #programming #dom #frontend #html #events #variables #functions