# The Problem: JavaScript Has No Memory

Before diving into anything new, think about what you already know:

- You can store data in [[Arrays]] and [[Objects]]
- You can loop through that data
- You can inject it into the page using the [[DOM]]

But here is the issue: **none of that data survives a page refresh.**

> [!important] Right now, your [[JavaScript]] has the memory of a goldfish. You can push a new item to an array, but the second you hit "Refresh", it disappears forever.

## Why Does This Happen?

It comes down to the difference between two types of memory:

|Memory Type|Example|Survives Refresh?|
|---|---|---|
|**RAM** (Temporary)|JavaScript variables (`let myArray = []`)|No|
|**Hard Drive** (Permanent)|Files saved on disk|Yes|

When you declare a variable in JavaScript, it lives in **RAM** — the browser's short-term working memory. The moment the page refreshes, the browser wipes RAM completely and starts fresh.

To build a real app, we need a way to write to something permanent.

# HTML Forms

## Forms Are Powerful — But Dangerous by Default

The [[HTML]] `<form>` tag is the standard way to collect user input. It comes with nice built-in features like letting users press **Enter** to submit. However, it has one very annoying default behavior:

> [!warning] By default, submitting an HTML form **immediately refreshes the page** — sending the data to a server. This resets all your JavaScript variables, destroying any data you had in memory.

## Intercepting the Form

You need to use [[JavaScript]] to step in between the form and the browser, and say: _"I will handle this data myself — do NOT refresh."_

Instead of listening for a `click` on a button, you listen for the `submit` event on the **entire form**:

```javascript
let myForm = document.getElementById('new-item-form');
myForm.addEventListener('submit', processData);
```

## The Event Object (`e`)

When any event happens, JavaScript secretly creates an **Event Object** — a package full of details about what just occurred (what was clicked, what was typed, etc.).

You can catch this object by adding a parameter (usually named `e` or `event`) to your callback function:

```javascript
function processData(e) {
    // 'e' is the Event Object
}
```

## The Magic Spell: `e.preventDefault()`

> [!important] `e.preventDefault()` is the key method that **stops the form's default behavior** (the page refresh). Always call this as the very first line inside your submit handler.

```javascript
function processData(e) {
    e.preventDefault(); // STOPS THE REFRESH!
    console.log('Form submitted safely!');
}
```

> [!example] **Live Demo — The Interceptor**
> 
> HTML:
> 
> ```html
> <form id="guest-form">
>     <input type="text" id="visitor-name" placeholder="Your Name" required>
>     <button type="submit">Sign In</button>
> </form>
> <h3 id="welcome-message" style="color: blue;"></h3>
> ```
> 
> JavaScript:
> 
> ```javascript
> let guestForm = document.getElementById("guest-form");
> let nameInput = document.getElementById("visitor-name");
> let welcomeMsg = document.getElementById("welcome-message");
> 
> guestForm.addEventListener("submit", function(event) {
>     event.preventDefault(); // Stop the page from refreshing!
> 
>     let userText = nameInput.value;
>     welcomeMsg.innerText = "Welcome to the site, " + userText + "!";
> 
>     nameInput.value = ""; // Clear the input box
> });
> ```

# Grabbing User Input

### `.value` vs `.innerText`

This is a very common point of confusion for beginners:

> [!note]
> 
> - For standard HTML elements like `<h1>` or `<p>`, you read text content using **`.innerText`**
> - For form inputs like `<input>` or `<textarea>`, you **must** use **`.value`**

```javascript
// Reading from a regular element
let heading = document.getElementById('title').innerText;

// Reading from a form input
let userName = document.getElementById('name-input').value;
```

## Building an Object from Input

Once you have the user's input, you package it into a neat [[JavaScript]] Object:

```javascript
let newOrder = {
    customer: userName,
    item: 'Spicy Coating'
};
```

Then push that object into your master array:

```javascript
ordersArray.push(newOrder);
drawToScreen(); // Re-render the list
```

> [!warning] At this point, the data appears on screen — but it still disappears on refresh. You have not saved it to permanent memory yet!

# LocalStorage

## What Is LocalStorage?

> [!note] **[[LocalStorage]]** 
> is a small storage area built into every web browser. It acts like a "backpack" for each website — data saved here **persists even after you close the tab, close the browser, or restart your computer.**

You can inspect it yourself:

1. Right-click anywhere on a page → **Inspect**
2. Click the **Application** tab (you may need to click the `>>` arrows to find it)
3. Look under **Storage** → **Local Storage**

## The 5MB Limit

LocalStorage gives you about **5 Megabytes** of space per website. That sounds small, but for plain text data, 5MB is enough to store the entire script of a long TV series several times over. It is more than enough for typical app data.

## The Critical Limitation: Strings Only

> [!warning] 
> **LocalStorage can only hold text (strings).** It cannot store JavaScript Arrays or Objects directly.
> 
> If you try to store an array without converting it first, the browser will save it as the useless string `[object Object]` — which is completely meaningless.

# JSON

## What Is JSON?

> [!note] **[[JSON]]** stands for _JavaScript Object Notation_. It is the universal language of the web — the way servers and clients talk to each other. It converts complex JavaScript data structures into a plain text string.

Think of it like this: you have a fully built Lego castle (an Array of Objects). You cannot fit it through a mail slot (LocalStorage). JSON breaks it down into a box of individual blocks with assembly instructions so it fits through — and can be rebuilt perfectly on the other side.

## `JSON.stringify()` — Packing the Backpack

Use this to turn your Array or Object **into a String** before saving it:

```javascript
let textData = JSON.stringify(ordersArray);
// ordersArray is now a plain string — safe to save!
```

## `JSON.parse()` — Unpacking the Backpack

Use this to turn a JSON string **back into a real JavaScript Array or Object**:

```javascript
let realArray = JSON.parse(textData);
// realArray is now a usable JavaScript array again
```

> [!example] **Console demonstration:**
> 
> ```javascript
> let minionRoster = [
>     { name: "Kevin", eyes: 2 },
>     { name: "Stuart", eyes: 1 }
> ];
> 
> // BAD: Trying to store directly — results in gibberish
> localStorage.setItem("badSave", minionRoster);
> console.log(localStorage.getItem("badSave"));
> // Output: "[object Object],[object Object]" (useless!)
> 
> // GOOD: Stringify first
> let shrinkWrapped = JSON.stringify(minionRoster);
> localStorage.setItem("goodSave", shrinkWrapped);
> console.log(localStorage.getItem("goodSave"));
> // Output: '[{"name":"Kevin","eyes":2},{"name":"Stuart","eyes":1}]'
> 
> // Unpack it back to a real array
> let unpackedArray = JSON.parse(localStorage.getItem("goodSave"));
> console.log(unpackedArray); // A real JavaScript array!
> ```

# Saving and Loading

## Saving to LocalStorage

Use `.setItem()` with two arguments: a **key** (the label) and the **value** (the JSON string):

```javascript
localStorage.setItem('mySavedData', textData);
```

## Loading from LocalStorage

Use `.getItem()` with the same key:

```javascript
let savedData = localStorage.getItem('mySavedData');
```

## The "First Visit" Bug

> [!warning] 
> **What if a user visits your site for the first time?** Their LocalStorage for your site is completely empty. Calling `.getItem()` on a key that does not exist returns `null` — not an empty array, not an empty string. Just `null`.
> 
> If you then try to run `JSON.parse(null)`, **your JavaScript will crash entirely.**

## The Safety Net — Checking for Null

Always check whether LocalStorage returned `null` before parsing:

```javascript
let saved = localStorage.getItem('myData');

if (saved === null) {
    myArray = []; // First visit — start with an empty array
} else {
    myArray = JSON.parse(saved); // Returning visit — unpack the saved data
}
```

> [!tip] 
> Get into the habit of **always** writing this safety check whenever you load from LocalStorage. Skipping it is one of the most common bugs beginners encounter.

# Putting It All Together

> [!example] **Live Demo — The Permanent Order Tracker**
> 
> HTML:
> 
> ```html
> <form id="order-form">
>     <input type="text" id="customer-name" placeholder="Customer Name" required>
>     <input type="text" id="item-ordered" placeholder="Item (e.g., Spicy Coating)" required>
>     <button type="submit">Add Order</button>
> </form>
> <ul id="order-list"></ul>
> ```
> 
> JavaScript:
> 
> ```javascript
> // 1. Grab DOM elements
> let orderForm = document.getElementById("order-form");
> let customerInput = document.getElementById("customer-name");
> let itemInput = document.getElementById("item-ordered");
> let orderList = document.getElementById("order-list");
> 
> // 2. Load saved data (with null safety check)
> let ordersArray = [];
> let savedOrders = localStorage.getItem("myOrders");
> if (savedOrders !== null) {
>     ordersArray = JSON.parse(savedOrders);
> }
> 
> // 3. Render function — draws the array to the screen
> function drawOrdersOnScreen() {
>     orderList.innerHTML = ""; // Clear first to avoid duplicates
>     for (let order of ordersArray) {
>         orderList.innerHTML += `<li><strong>${order.customer}</strong> ordered ${order.item}</li>`;
>     }
> }
> 
> // 4. Draw immediately on page load (shows saved data right away)
> drawOrdersOnScreen();
> 
> // 5. Handle form submission
> orderForm.addEventListener("submit", function(e) {
>     e.preventDefault(); // Stop the refresh!
> 
>     // Package inputs into an object
>     let newOrder = {
>         customer: customerInput.value,
>         item: itemInput.value
>     };
> 
>     // Add to array
>     ordersArray.push(newOrder);
> 
>     // Save to LocalStorage (stringify first!)
>     localStorage.setItem("myOrders", JSON.stringify(ordersArray));
> 
>     // Re-render and clear inputs
>     drawOrdersOnScreen();
>     customerInput.value = "";
>     itemInput.value = "";
> });
> ```

# The Big Picture: CRUD

> [!important] **four fundamental operations of all software**. (CRUD)
> 
> |Letter|Operation|What you learned|
> |---|---|---|
> |**C**|**Create**|Forms, `.push()`, `localStorage.setItem()`|
> |**R**|**Read**|Loops, `innerHTML`, `JSON.parse()`|
> |**U**|**Update**|Modifying objects (coming next!)|
> |**D**|**Delete**|`.splice()` / `.filter()` (coming next!)|
> 
> Every app you will ever build — from social media to e-commerce to banking — is built on some version of CRUD.

# Summary

> [!note] **Key takeaways from this lecture:**
> 
> - `e.preventDefault()` — stops the form from refreshing the page and destroying your data
> - [[LocalStorage]] — the browser's permanent 5MB text-only storage, accessible with `.setItem()` and `.getItem()`
> - `JSON.stringify()` — converts an Array/Object **into a string** (packing the backpack)
> - `JSON.parse()` — converts a string **back into an Array/Object** (unpacking the backpack)
> - Always check for `null` before parsing LocalStorage data to avoid crashes on first visits

# Other Resources

- [MDN: LocalStorage](https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage)
- [MDN: Event.preventDefault()](https://developer.mozilla.org/en-US/docs/Web/API/Event/preventDefault)
- [MDN: JSON.stringify()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/stringify)
- [MDN: JSON.parse()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse)
- [W3Schools: HTML Forms](https://www.w3schools.com/html/html_forms.asp)
- [GeeksforGeeks: LocalStorage in JavaScript](https://www.geeksforgeeks.org/localstorage-in-javascript/)

# Tags

#javascript #webdevelopment #localstorage #json #forms #dom #crud #memory #frontend #programming