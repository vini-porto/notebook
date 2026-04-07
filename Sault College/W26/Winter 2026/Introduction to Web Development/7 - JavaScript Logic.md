	# Recap

- **Variables** – Memory boxes that store data
- **The [[DOM]]** – How JavaScript grabs and manipulates HTML elements
- **Functions** – Reusable blocks of code (like recipes)
- **Event Listeners** – Triggers that respond to user actions

# The Foundation of Logic

## Booleans

Computers fundamentally only understand two states: **yes** or **no**, **on** or **off**. In [[JavaScript]], we represent this with a **[[Boolean]]** — a value that is either `true` or `false`.

```js
let isRaining = true;
let isSunny = false;
```

> [!note] What is a Boolean? 
> A **Boolean** is a data type that can only hold one of two values: `true` or `false`. It is the most basic building block of all logic in programming.

## Comparison Operators

To ask the computer a question, we **compare** things. The result of a comparison is always a Boolean (`true` or `false`).

|Operator|Meaning|
|---|---|
|`===`|Strictly equal to|
|`!==`|Not equal to|
|`>`|Greater than|
|`<`|Less than|
|`>=`|Greater than or equal to|

## The Critical Rule: `=` vs `===`

This is one of the most common sources of confusion for beginners.

> [!warning] Do NOT confuse `=` with `===`
> 
> - **One equals `=`** → **Assignment**. It _puts_ a value into a variable (like placing something in a box).
> - **Three equals `===`** → **Comparison**. It _asks_ if two things are exactly the same (like checking if two boxes have the same content).

```js
let age = 18;    // ASSIGNMENT: puts 18 into the variable 'age'
age === 18;      // COMPARISON: asks "is age exactly 18?" → returns true
```

> [!tip] Memory trick 
> Think of `===` as asking a question: _"Are these the same?"_ It always gives you back a `true` or `false` answer.

# Making Decisions

An **if/else statement** lets your code take different paths depending on whether a condition is `true` or `false`.

## The `if` Block

```js
if (condition) {
    // This code runs only if condition is true
}
```

> [!example] Real Example
> 
> ```js
> if (temperature < 0) {
>     console.log('Wear a heavy coat in Sault Ste. Marie!');
> }
> ```
> 
> If the temperature is below zero, the message appears. If not, nothing happens.

## The `else` Block

```js
if (password === 'secret123') {
    login();
} else {
    showError();
}
```

- If the password matches → call `login()`
- If it does NOT match → call `showError()`

## Multiple Choices: `else if`

When there are more than two possible outcomes, chain conditions with `else if`.

```js
if (score >= 90) {
    grade = 'A';
} else if (score >= 80) {
    grade = 'B';
} else {
    grade = 'F';
}
```

> [!note] How `else if` works 
> JavaScript checks conditions **from top to bottom** and stops as soon as one is `true`. The final `else` is the catch-all — it runs only if _none_ of the conditions above were `true`.

# Logical Operators

Sometimes a single comparison is not enough. We can **combine** conditions using logical operators.

## AND (`&&`) 

```js
if (hasTicket === true && age >= 18) {
    console.log('Welcome to the concert!');
}
```

The message only appears if **both** conditions are true at the same time. If either one is false, the whole condition is false.

## OR (`||`) 

```js
if (day === 'Saturday' || day === 'Sunday') {
    console.log('It is the weekend!');
}
```

The message appears if **either** condition is true. Only fails if _both_ are false.

|Operator|Symbol|Meaning|
|---|---|---|
|AND|`&&`|All conditions must be true|
|OR|`\|`|At least one must be true|

# Live Demo

This demo puts conditionals to work in a real mini-app. It calculates the total price of a chocolate order and applies a **bulk discount** based on quantity.

## HTML

```html
<body>
    <div style="padding: 30px; border: 2px solid #333; width: 300px;">
        <h2>Chocolate Outlet Checkout</h2>
        <label>How many pounds of chocolate?</label><br>
        <input type="number" id="qty-input" value="1" style="margin: 10px 0;"><br>
        <button id="calc-btn">Calculate Total</button>
        <h3 id="total-display">Total: $0.00</h3>
        <p id="promo-message" style="color: blue;"></p>
    </div>
    <script src="app.js"></script>
</body>
```

## JavaScript

```js
let calcBtn = document.getElementById("calc-btn");
let qtyInput = document.getElementById("qty-input");
let totalDisplay = document.getElementById("total-display");
let promoMessage = document.getElementById("promo-message");

const pricePerPound = 6.99;

function calculateOrder() {
    // .value grabs what the user typed into the input box!
    let quantity = qtyInput.value;
    let finalTotal = quantity * pricePerPound;

    if (quantity >= 10) {
        // If they buy 10 or more, give a $10 discount!
        finalTotal = finalTotal - 10;
        promoMessage.innerText = "Bulk applied: $10 off!";
    } else if (quantity <= 0) {
        // Catch sneaky users trying to buy 0 or negative amounts
        finalTotal = 0;
        promoMessage.innerText = "Please enter a valid amount.";
        promoMessage.style.color = "red";
    } else {
        // Normal order
        promoMessage.innerText = "Add more chocolate to get a discount!";
        promoMessage.style.color = "gray";
    }

    // Update the DOM
    totalDisplay.innerText = "Total: $" + finalTotal.toFixed(2);
}

calcBtn.addEventListener("click", calculateOrder);
```

> [!important] Key things to notice in this demo
> 
> - `.value` is used to read what the user typed into an `<input>` element.
> - `toFixed(2)` formats a number to 2 decimal places (great for currency).
> - The `else if (quantity <= 0)` branch **validates** the input — it protects against bad data.

# Arrays: Handling Lists of Data

## The Problem with Individual Variables

Imagine storing a grocery list as separate variables:

```js
let item1 = 'Milk';
let item2 = 'Eggs';
let item3 = 'Bread';
```

This becomes unmanageable fast. What if you have 100 items? 1,000?

## The Solution: [[JavaScript Arrays]]

An **[[array]]** is a single variable that holds a **list** of items. It uses **square brackets `[ ]`**.

```js
let groceries = ['Milk', 'Eggs', 'Bread'];
```

> [!note] What is an Array? 
> An **array** is an ordered list of values stored inside a single variable. Each item in the list is separated by a comma.

## Indexes

> [!warning] 
> Computers count from 0, not 1! This trips up almost every beginner. The **first** item in an array is always at **index 0**.

```js
let minions = ['Kevin', 'Stuart', 'Bob'];
// Index:         0        1         2
```

## Accessing Items by Index

To get a specific item, write the array name followed by the index in square brackets.

```js
let cats = ['Siamese', 'Russian Blue', 'Maine Coon'];
console.log(cats[1]); // Prints: 'Russian Blue'
```

## Modifying Items

You can change an item at any index after the array is created.

```js
let scores = [10, 20, 30];
scores[0] = 15; // Changes the first item from 10 to 15
// scores is now [15, 20, 30]
```

## Useful Array Properties and Methods

### `.length`
How many items are there?

```js
let names = ['Sam', 'Walter', 'Jesse'];
console.log(names.length); // Prints: 3
```

### `.push()`
Add an item to the END

```js
let cart = ['Shoes'];
cart.push('Hat');
// cart is now ['Shoes', 'Hat']
```

### `.pop()`
Remove the LAST item

```js
let queue = ['Alice', 'Bob', 'Charlie'];
queue.pop();
// queue is now ['Alice', 'Bob']
```

> [!tip] Think of `.push()` and `.pop()` like a stack of plates
> 
> - `.push()` places a new plate **on top** (end of the list).
> - `.pop()` removes the **top plate** (last item in the list).

# Combining the [[DOM]] with Logic

Now we connect everything together: [[DOM]] manipulation, [[event listeners]], [[conditionals]], and arrays, all in one app.

## Form Validation Example

A classic real-world use case: checking if the user actually filled in a field before submitting.

```js
if (usernameInput.value === '') {
    errorMessage.innerText = 'Name is required!';
    errorMessage.style.color = 'red';
} else {
    submitForm();
}
```

## Live Demo

This final demo brings everything together. It takes user input, uses an array as a "database", checks membership with `.includes()`, and updates the [[DOM]] based on a chain of `if/else if/else` conditions.

### HTML

```html
<body>
    <div style="text-align: center; margin-top: 50px;">
        <h2>Russian Blue Name Checker</h2>
        <p>Enter your cat's name to see if it's on the VIP list.</p>
        <input type="text" id="cat-name-input" placeholder="e.g. Smokey">
        <button id="check-btn">Check VIP List</button>
        <h1 id="result-text" style="margin-top: 20px;"></h1>
    </div>
    <script src="app.js"></script>
</body>
```

### JavaScript

```js
let checkBtn = document.getElementById("check-btn");
let nameInput = document.getElementById("cat-name-input");
let resultText = document.getElementById("result-text");

// Our Database (An Array)
let vipCats = ["Smokey", "Shadow", "Boris", "Luna", "Mash"];

function checkCatName() {
    // Grab what they typed
    let userCat = nameInput.value;

    // .includes() searches the array and returns true or false
    let isVip = vipCats.includes(userCat);

    if (userCat === "") {
        // Did they leave it blank?
        resultText.innerText = "Error: You must type a name!";
        resultText.style.color = "red";
    } else if (isVip === true) {
        // Is it on the list?
        resultText.innerText = "Welcome to the VIP Lounge, " + userCat + "!";
        resultText.style.color = "green";
    } else {
        // Not on the list
        resultText.innerText = "Sorry, " + userCat + " is not on the list.";
        resultText.style.color = "black";
    }
}

checkBtn.addEventListener("click", checkCatName);
```

> [!important] Introducing `.includes()` `array.includes(value)` is a built-in [[JavaScript]] method that searches an array for a specific value and returns `true` if it finds it, or `false` if it doesn't. It's a clean and readable way to check membership without needing loops.

> [!example] Logic flow of the VIP Checker
> 
> 1. User types a name and clicks the button
> 2. The function grabs the input with `.value`
> 3. `.includes()` checks if that name exists in `vipCats`
> 4. The `if/else if/else` chain decides what message to display
> 5. The [[DOM]] is updated with the result text and colour

## Summary

|Concept|What it does|
|---|---|
|`===`|Compares two values strictly (always use this, not `==`)|
|`if / else if / else`|Creates branching decision logic|
|`&&`|AND — all conditions must be true|
|`\|`|OR — at least one condition must be true|
|`[]`|Square brackets define and access arrays|
|Index 0|Arrays always start counting at zero|
|`.length`|Returns the number of items in an array|
|`.push()`|Adds an item to the end of an array|
|`.pop()`|Removes the last item from an array|
|`.includes()`|Checks if a value exists in an array|

# Further Reading

- [MDN – if...else](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/if...else)
- [MDN – Arrays](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array)
- [MDN – Comparison Operators](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Expressions_and_operators#comparison_operators)
- [W3Schools – JavaScript Arrays](https://www.w3schools.com/js/js_arrays.asp)
- [GeeksforGeeks – JavaScript Logical Operators](https://www.geeksforgeeks.org/javascript-logical-operators/)

# Tags

#javascript #programming #web #conditionals #arrays #dom #logic #boolean #frontend #beginner