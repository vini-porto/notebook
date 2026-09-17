# Lesson objectives

By the end of this lesson you should be able to:

- Describe the nature and major components of the [[DOM]] (Document Object Model)
- Traverse and locate specific parts in the DOM of a web page using [[JavaScript]]
- Manipulate the DOM using JavaScript
- Manipulate element attributes and styles using JavaScript

# Quick review before we start

Web technology grew up without a single company controlling it. That decentralized growth is why the web spread so fast, but it's also why some old design choices still linger today. Thankfully, standardization efforts over the years have made building for the web much less painful than it used to be.

A few terms worth refreshing:

> [!note] Core internet vocabulary
> - **[[Internet]]**: the network of networks that connects devices around the world.
> - **World Wide Web**: the connected information available on top of the internet.
> - **Network Protocol**: an agreed-upon process for how devices talk to each other over a network.
> - **[[HTTP]]** (Hypertext Transfer Protocol): the process browsers use to *request* web resources, and servers use to *respond* with them.
> - **TCP** (Transmission Control Protocol): makes sure HTTP requests succeed (or fail with useful information) in a reliable way.
> - **IP** (Internet Protocol): the process that lets internet-connected devices communicate with each other. Every device online has an IP address.
> - **DNS** (Domain Name System): translates human-friendly URLs into IP addresses computers can use.
> - **[[HTML]]**: HyperText Markup Language, the document format of web pages. It's one kind of resource that lives at a URL and can be requested over HTTP.

A **URL** (Uniform Resource Locator) is just a unique address for a web resource. It breaks down into parts:

```
http://www.saultcollege.ca/about
```

| Part | Example |
|---|---|
| protocol | `http` |
| sub domain | `www` |
| domain | `saultcollege` |
| top-level domain | `.ca` |
| path | `/about` |

## Including JavaScript in a web page

There are two ways to add a `<script>` element to a page:

```html
<script>alert('Hello, world!')</script>
```

```html
<script src="url/to/code.js"></script>
```

> [!warning] Common mistake
> When you use the `src` attribute, the `<script>` element must be left empty (no code inside it). Either way, the closing `</script>` tag is always required — you can't self-close it.

**Where should `<script>` go?** Usually inside `<head>`. But there's a catch: when the browser reaches `<head>`, it hasn't parsed the `<body>` HTML yet. That means a script running at that point can't "see" or refer to elements in the page, because they don't exist yet as far as the DOM is concerned.

Two ways to solve this:

- Add the `defer` boolean attribute to `<script>`. This tells the browser: "wait until all the HTML has been loaded and parsed before running this code."
- The older-school fix: place the `<script>` element right before the closing `</body>` tag, so by the time it runs, everything above it already exists.

> [!tip]
> `defer` is the modern, recommended approach. You'll still see the "script at the bottom of body" trick in a lot of older code, so it helps to recognize it.

JavaScript doesn't only live inside `<script>` tags — some HTML attributes can hold JavaScript code directly as their value:

```html
<button onclick="alert('Hello, world!');">Click me!</button>
```

## Browser sandboxing

Browsers will run JavaScript from literally any website you visit, and not all of that code is trustworthy. To protect you, browsers restrict what a webpage's JavaScript is allowed to do — for example, it can't freely access your file system, webcam, microphone, or GPS without your permission.

> [!important] Sandboxing
> This restriction is called **sandboxing**. It's the browser's way of balancing "let websites be genuinely useful and interactive" against "keep users safe and private." This connects closely to [[Web Security]] and [[Authentication]] as topics.

# The Document Object Model (DOM)

> [!note] Definition
> When a browser loads an HTML document, it builds an internal representation of that HTML as a **hierarchy of JavaScript objects**. This data structure is called the **DOM (Document Object Model)**.

Think of the DOM as a **tree**: a structure made of connected nodes, where each node can have other nodes as its children.

- The `<html>` element is the **root** node of the tree.
- Nested elements become **child nodes** of whatever element contains them.
- The actual text inside elements becomes **leaf nodes** (the "ends" of the tree branches, with nothing nested further inside them).

> [!example] From HTML to a tree
> This HTML:
> ```html
> <!doctype html>
> <html>
>   <head>
>     <title>My home page</title>
>   </head>
>   <body>
>     <h1>My home page</h1>
>     <p>Hello, I am Marijn and this is my home page.</p>
>     <p>I also wrote a book! Read it
>       <a href="http://eloquentjavascript.net">here</a>.
>     </p>
>   </body>
> </html>
> ```
> ...becomes a tree that looks roughly like this:
> ```
> html
> ├── head
> │   └── title → "My home page"
> └── body
>     ├── h1 → "My home page"
>     ├── p → "Hello! I am..."
>     └── p → "I also wrote..."
>         ├── a → "here"
>         └── "."
> ```
> Every box in the diagram is a node, and every arrow shows a parent-child relationship.

## The DOM is "live"

> [!important]
> DOM objects are **live**. This is one of the most important ideas in this whole topic:
> - If the web page changes (say, a script adds an element), that change shows up immediately as new DOM objects.
> - If you change a DOM object in code, that change is reflected immediately in what the browser actually renders on screen.
>
> In other words, the DOM isn't a snapshot you have to "refresh" — it's a direct, two-way mirror of what's on the page.

## Node properties

Every DOM node comes with built-in properties that let you move around the tree:

| Property | What it gives you |
|---|---|
| `parentNode` | the node's parent |
| `childNodes` | a collection with **all** the node's children (including text nodes) |
| `children` | a collection with only the children that are **elements** |
| `firstChild` / `lastChild` | the first/last child node |
| `previousElementSibling` / `nextElementSibling` | the closest sibling element before/after this one |

> [!tip]
> Notice the naming pattern: anything with "Element" in the name (like `children`, `previousElementSibling`) only counts actual HTML elements, and skips plain text nodes. Properties without "Element" in the name (like `childNodes`) count everything, text included.

## Getting and setting an element's content

| Property | Gets/sets | Notes |
|---|---|---|
| `innerHTML` | the **HTML** content of an element | When you set it, the string you give it is *parsed as HTML* |
| `textContent` | the **text** content of an element | When you set it, any HTML you give it is treated as plain text (not parsed) |
| `innerText` | the "human-readable" text content | CSS-aware — text hidden via CSS is *not* included |

> [!warning] Common mistake
> Setting `innerHTML` with untrusted or user-supplied text can be a security risk (it gets parsed as real HTML/JavaScript). If you just want to insert plain text, `textContent` is the safer choice.

## HTML Collections

Some DOM properties and methods (like `childNodes` or `getElementsByTagName`) return something called an **HTML Collection** — an array-like object.

- You *can* index into it and loop over it like an array:
  ```javascript
  const element = collection[0];

  for (const element of collection) { /* ... */ }
  ```
- You *cannot* use typical array methods like `.map()` or `.slice()` on it directly — collections aren't true arrays.
- If you need real array methods, convert it first:
  ```javascript
  const a = Array.from(collection);
  ```

## Traversing the DOM

Because the DOM is a tree, **recursive functions** are a natural fit for walking through it — a function that calls itself on each child node.

> [!example] Counting all descendants of a node
> ```javascript
> function countDescendants(node) {
>   let c = 0;
>   for (const child of node.children) {
>     c += 1 + countDescendants(child);
>   }
>   return c;
> }
> ```
> This function counts a node, then asks each of its children to count themselves and their own descendants — adding it all up as the recursion "unwinds."

# Finding DOM elements

The whole DOM tree is reachable through one global variable: `document`.

- `document.body` — the `<body>` element
- `document.head` — the `<head>` element

Beyond those shortcuts, `document` provides several functions for finding specific elements anywhere in the page.

## `getElementById(id)`

Returns the single element with a given `id`.

```html
<div id="myel">Hi!</div>

<script>
  const el = document.getElementById('myel');
</script>
```

## `getElementsByTagName(name)`

Returns a **collection** of every element with the given tag name.

```html
<h1>heading</h1>
<div>one</div>
<div> two
  <div>three</div>
  <div>four</div>
</div>
<script>
  const els = document.getElementsByTagName('div');
  for (const el of els) {
    console.log(el.textContent);
  }
</script>
```

## `getElementsByClassName(name)`

Returns a collection of every element that has the given class.

```javascript
const els = document.getElementsByClassName('a');
```

## `querySelectorAll(selector)`

Returns a collection of every element matching a given [[CSS]] selector — this is the most flexible option since it accepts any valid CSS selector syntax.

```javascript
// Returns all <div>s with class 'a'
const els = document.querySelectorAll('div.a');
```

## `querySelector(selector)`

Same idea as `querySelectorAll`, but returns only **the first** matching element instead of a collection.

```javascript
// Returns only the first <div> with class 'a'
const el = document.querySelector('div.a');
```

> [!tip] Choosing between these
> `querySelector` / `querySelectorAll` are generally the most useful day-to-day, since CSS selectors can express almost anything (`id`, class, tag, nesting, attributes, and more) in one call. The others are handy shortcuts for simpler, very specific lookups.

# Manipulating the DOM

DOM nodes have built-in methods for changing the structure of the tree:

| Method | What it does |
|---|---|
| `appendChild(node)` | Adds `node` as the **last child** of the node it's called on |
| `insertBefore(node, nodeToPrecede)` | Inserts `node` immediately before `nodeToPrecede` |
| `replaceChild(node, nodeToReplace)` | Swaps `nodeToReplace` out for `node` |
| `cloneNode(deep)` | Makes a copy of the node. If `deep` is `true`, all its child nodes are cloned too |

To create brand-new content (not just move existing content around), `document` gives you:

| Method | What it does |
|---|---|
| `createElement(tagName)` | Creates a new, empty HTML element with the given tag name |
| `createTextNode(textContent)` | Creates a new text node with the given text |

> [!example] Building and inserting a new paragraph
> ```html
> <div id="myDiv">
>   <p id="a">A</p>
>   <p id="b">B</p>
>   <p id="c">C</p>
> </div>
> <script>
>   const newParagraph = document.createElement('p');
>   newParagraph.appendChild(document.createTextNode('D'));
>   document.getElementById('myDiv').appendChild(newParagraph);
> </script>
> ```
> This builds a `<p>D</p>` from scratch and appends it inside `#myDiv`.

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

# Element attributes

Most standard HTML attributes are directly accessible as a property on the element's DOM object, using the same name as the attribute.

> [!warning] Common mistake
> The one notable exception is `class`. Since `class` is a reserved word in JavaScript, its matching property is called `className` instead.

```html
<img id="my-logo" class="logo full" src="logo.png" alt="My Logo" width="75" height="75">
<script>
  const logoEl = document.getElementById('my-logo');
  console.log(logoEl.id);
  console.log(logoEl.src);
  console.log(logoEl.alt);
  console.log(logoEl.width);
  console.log(logoEl.height);
  console.log(logoEl.className);
</script>
```

These properties work both ways — you can **read** the current value or **assign** a new one.

## `classList`

The `classList` property gives you an object built specifically for managing the **set** of classes on an element, without you having to manually parse or rebuild the `class` string.

- `add()`, `remove()`, `toggle()` — add/remove/toggle a single class
- `contains()` — check whether the element currently has a given class
- Can also be looped over like an array

```html
<img id="my-logo" class="logo full" src="logo.png">
<script>
  const logoEl = document.getElementById('my-logo');

  // Print each class individually
  for (const c of logoEl.classList) {
    console.log(c);
  }

  logoEl.classList.contains('full'); // Yields: true
  logoEl.classList.remove('full');   // Removes 'full' if it's there
  logoEl.classList.add('full');      // Adds 'full' if it's not already there
  logoEl.classList.toggle('full');   // Adds it if missing, removes it if present
</script>
```

## Non-standard attributes: `getAttribute()` / `setAttribute()`

Not every attribute has a matching JavaScript property (this is common for custom, non-standard attributes). For those, use `getAttribute()` and `setAttribute()` directly:

```javascript
const firstPara = document.querySelector('p');
firstPara.setAttribute("data-myattr", "the value");
firstPara.getAttribute("data-myattr");
```

> [!tip]
> The convention for custom attributes is to prefix them with `data-`. This keeps them clearly separated from official HTML attributes and avoids naming collisions with anything the HTML spec might add in the future.

# Element styles

Every element has a `style` property: an object with a property for every possible CSS property, letting you get or set inline styles directly from JavaScript.

> [!note] Naming conversion: kebab-case to camelCase
> CSS properties with hyphenated names get converted to camelCase in JavaScript — remove the hyphens and capitalize the letter that follows each one.
> - `background-color` → `backgroundColor`
> - `font-size` → `fontSize`

```javascript
console.log(el.style.width); // Yields, for example: "75px"

el.style.width = "75px";
el.style.backgroundColor = "red";
el.style.backgroundColor = "rgba(255, 0, 0, 0.5)";
el.style.display = "block";
```

> [!warning] Common mistake
> All CSS values set through JavaScript must be **strings** — and for anything that needs a unit (like width or margin), don't forget to include it (`"75px"`, not `75`). Leaving out the unit is a very easy mistake to make.

## Element sizing

Besides `style`, there are several **read-only** properties for measuring an element as it's actually rendered on the page:

| Property | Description |
|---|---|
| `scrollTop` / `scrollLeft` | Amount in pixels the element has been scrolled |
| `offsetWidth` / `offsetHeight` | Width/height including borders, padding, and scroll bars (0 if the element is hidden) |
| `clientWidth` / `clientHeight` | Width/height *not* including border, margin, or scroll bars (0 for inline elements) |
| `scrollWidth` / `scrollHeight` | The element's full width/height, regardless of how much is currently visible in the viewport |

## `.style.width` vs `.clientWidth`

> [!important] Don't mix these up
> - `.style.width` gives back exactly what you (or a stylesheet) previously *set* it to, including the unit — it says nothing about the element's real rendered size.
> - `.clientWidth` gives the element's **actual width in pixels**, as it appears in the browser, with no units — regardless of whether `.style.width` was ever set at all.

# Summary

- JavaScript code runs exactly where the `<script>` element sits in the HTML — unless the `defer` attribute is used, in which case it waits until parsing is done.
- The DOM is a data structure representing the loaded document; the browser builds it automatically.
- The DOM API provides many methods for traversing (finding your way around) and manipulating (changing) that structure.
- Changes made to the DOM in code appear immediately in the browser viewport, and changes in the viewport are reflected immediately back in the DOM — it's live in both directions.
- Some DOM methods (like `getElementById`) return a single object representing one HTML element, while others (like `querySelectorAll`) return array-like collections of such objects.

# External resources

- [MDN: Introduction to the DOM](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Introduction)
- [MDN: Document.querySelector()](https://developer.mozilla.org/en-US/docs/Web/API/Document/querySelector)
- [W3Schools: HTML DOM](https://www.w3schools.com/js/js_htmldom.asp)
- [GeeksforGeeks: Introduction to the DOM](https://www.geeksforgeeks.org/dom-document-object-model/)

# Tags

#web #programming #javascript #dom #html #css #software
