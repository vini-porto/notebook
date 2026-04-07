# The DRY Principle

Before we even touch loops, there is one rule you need to tattoo on your brain:

> [!important] D.R.Y. — Don't Repeat Yourself If you are copying and pasting the same block of code over and over, you are doing it wrong. Repetition is the enemy. [[JavaScript]] (and programming in general) gives you tools to let the computer do the repetitive work so you do not have to.

## The Problem Loops Solve

Say you are building a website for a class of 50 students, and you need an `<li>` tag for each one. Writing that tag 50 times by hand is not just boring — it is fragile. Add a student? Edit 50 lines. Remove one? Same story.

This is exactly where loops come in.

---

# Loops

> [!note] What is a Loop? A **loop** is a set of instructions that repeats until a specific _stop condition_ is met. It is probably the single most useful tool in programming — the workhorse that turns 50 lines into 5.

There are three kinds of loops you need to know: `for`, `while`, and `for...of`.

---

### The `for` Loop

The `for` loop is the most common. It needs three pieces of information:

1. **Where do I start?**
2. **When do I stop?**
3. **How do I count?** (by 1s, by 2s, backwards...)

> [!example] Basic `for` Loop
> 
> ```javascript
> for (let i = 0; i < 5; i++) {
>     console.log('Banana! ' + i);
> }
> ```
> 
> This prints `Banana!` five times, with `i` going from 0 to 4.

Let's break down the three parts inside the parentheses:

|Part|Code|Meaning|
|---|---|---|
|Start|`let i = 0`|Create a temporary variable `i`, starting at 0|
|Stop condition|`i < 5`|Keep going as long as `i` is less than 5|
|Step|`i++`|After each loop, add 1 to `i`|

> [!note] What is `i`? The variable `i` stands for _index_ or _iterator_. It is just a counter. Every time the loop runs, it checks the stop condition first. If it is `true`, the code runs. If it is `false`, the loop stops entirely.

**Loops can also count backwards:**

```javascript
for (let i = 10; i > 0; i--) {
    console.log(i);
}
console.log('Blastoff!!!');
```

Instead of `i++` (add 1), we use `i--` (subtract 1), and the stop condition flips accordingly.

---

> [!warning] The Infinite Loop What happens if the stop condition is _never_ met?
> 
> ```javascript
> for (let i = 0; i >= 0; i++) {
>     // i will ALWAYS be greater than or equal to zero!
> }
> ```
> 
> The loop never stops. Your browser freezes, your CPU fans spin up, and you have a bad time. Always make sure your stop condition can actually be reached.

---

### The `while` Loop

The `for` loop is great when you know _exactly_ how many times to run. But sometimes you do not know the count — you just know the condition that should end things.

> [!note] `while` Loop A **while loop** keeps running as long as a condition is `true`. The moment the condition becomes `false`, it stops.

```javascript
let health = 100;

while (health > 0) {
    console.log('Keep fighting! ' + health);
    health = health - 10;
}
console.log('Respawn!');
```

The loop does not care about counting from 0 to 5. It just keeps going until `health` drops to 0 or below.

**A helpful mental model from the slides:**

- Think of a `for` loop as a staircase — you know exactly how many steps there are.
- Think of a `while` loop as a treadmill — you run _until_ something (fatigue, a timer) tells you to stop.

---

### Loops + Arrays: The Perfect Match

[[Arrays]] hold lists of items. Loops can walk through those lists one by one. Together, they are extremely powerful.

> [!example] Looping through an Array
> 
> ```javascript
> let cookies = ['Chocolate Chip', 'Peanut Butter', 'Shortbread'];
> 
> for (let i = 0; i < cookies.length; i++) {
>     console.log('I love ' + cookies[i] + ' Cookies');
> }
> ```

Notice `cookies.length` instead of a hard-coded `3`. This is important.

> [!tip] Always use `.length` If you hardcode `3`, and later add a fourth cookie to your array, the loop will silently miss it. Using `.length` makes the loop automatically adapt to whatever size the array is — it is future-proof.

---

### The Modern `for...of` Loop

[[JavaScript]] has a cleaner syntax for looping through arrays that is much easier to read:

```javascript
for (let cookie of cookies) {
    console.log(cookie);
}
```

This reads almost like plain English: _"For every cookie inside the cookies array, log it."_

Use `for...of` when you just need each item and do not care about the index number.

---

### Practical Example: Generating HTML Dynamically

This is where loops get genuinely useful. Instead of writing `<li>` tags by hand, let JavaScript do it:

**HTML:**

```html
<ul id="character-list"></ul>
<script src="app.js"></script>
```

**JavaScript:**

```javascript
let characterList = document.getElementById("character-list");

let breakingBadChars = ["Walter White", "Jesse Pinkman", "Saul Goodman", "Gus Fring", "Mike Ehrmantraut"];

for (let char of breakingBadChars) {
    let htmlString = "<li>" + char + "</li>";
    characterList.innerHTML += htmlString; // += adds to existing content, = would overwrite it
}
```

> [!warning] `+=` vs `=` with `innerHTML` Using `=` will _overwrite_ everything in the element each time the loop runs. You'd end up with only the last item. Use `+=` to _append_ each new piece of HTML.

---

## Objects

Arrays are great for lists of the same kind of thing. But what if you want to store several _different_ details about _one_ specific thing?

> [!example] The Problem with Arrays for this
> 
> ```javascript
> let myCar = ['Jeep', 'Wrangler', 2018, 'Blue']; // Confusing — what is index 2?
> ```
> 
> There is no label. You have to remember that index `0` is the make, `2` is the year, etc.

> [!note] What is an Object? A **[[JavaScript]] Object** is a collection of **key-value pairs** called _properties_. It uses curly braces `{ }`. Each key is a label, and each value is the data attached to that label.

```javascript
let myCar = {
    make: 'Jeep',
    model: 'Wrangler',
    color: 'Blue',
    year: 2018
};
```

Now `make`, `model`, `color`, and `year` are the _keys_. `'Jeep'`, `'Wrangler'`, etc. are the _values_.

---

### Accessing Data from an Object

**Dot notation** — the standard way:

```javascript
console.log(myCar.make);  // Prints 'Jeep'
console.log(myCar.year);  // Prints 2018
```

**Bracket notation** — used when the key has a space in it, or when you are using a variable as the key:

```javascript
console.log(myCar['model']); // Also prints 'Wrangler'
```

---

### Updating and Adding Properties

You can change or add properties after the object is created:

```javascript
myCar.year = 2026;           // Update an existing property
myCar.submodel = 'Rubicon'; // This ADDS a brand new property on the fly
```

---

### Object Methods

Objects can hold more than just strings and numbers. They can hold _functions_ too.

> [!note] What is a Method? When a **function lives inside an object**, it is called a **method**. Methods are the _verbs_ of an object — the things it can _do_.

```javascript
let dog = {
    bark: function() {
        console.log('Woof!');
    }
};

dog.bark(); // Runs the function — prints 'Woof!'
```

You call a method with dot notation, just like a property, but you add `()` at the end to actually execute it.

---

### A Fuller Object Example

```javascript
let myCat = {
    name: "Boris",
    breed: "Russian Blue",
    age: 4,
    isHungry: true,

    meow: function() {
        console.log(this.name + " says: MEOW!");
    }
};

// Reading data
console.log("Cat Name: " + myCat.name);
console.log("Age: " + myCat.age);

// Updating data
myCat.isHungry = false;
myCat.weight = "12 lbs"; // New property, added on the spot

// Running the method
myCat.meow(); // Prints "Boris says: MEOW!"
```

> [!note] `this` keyword Inside the `meow` method, `this.name` refers to the `name` property of the _same object_. `this` is a way for an object to refer to itself. It is a bigger topic for later, but for now just know it means "this object right here."

---

## Arrays of Objects: The Real-World Combo

In practice, you rarely use just an array or just an object. You combine them — an **array of objects**.

> [!important] The Mental Model
> 
> - An **array** is like a filing cabinet drawer.
> - An **object** is like a single folder inside that drawer, with labeled tabs (name, age, phone...).
> - An **array of objects** is the whole drawer full of labeled folders.

```javascript
let inventory = [
    { id: 1, item: 'Original Coating', stock: 50 },
    { id: 2, item: 'Spicy Coating',    stock: 12 },
    { id: 3, item: 'Fish Fry',         stock: 0  }
];
```

---

### Accessing Nested Data

To get the stock count of `Spicy Coating` (which is at index `1`):

```javascript
console.log(inventory[1].stock); // Prints 12
```

Read it left to right:

1. `inventory` — the array
2. `[1]` — the second object (index 1)
3. `.stock` — the `stock` property of that object

---

### Looping over Arrays of Objects

This is the pattern you will use constantly in real projects:

```javascript
for (let prod of inventory) {
    console.log(prod.item + ' has ' + prod.stock + ' bags left.');
}
```

Each time through the loop, `prod` is one full object from the array. You can then use dot notation to grab any property from it.

---

### Practical Example: A Dynamic Store Dashboard

This demo combines everything — an array of objects, a loop, and dynamic [[DOM]] manipulation:

```javascript
let products = [
    { name: "Original Chicken Coating", price: 6.99, stock: 50 },
    { name: "Spicy Fish Fry",           price: 7.49, stock: 12 },
    { name: "Garlic Parmesan Mix",      price: 8.99, stock: 0  },
    { name: "Cajun Heat Mix",           price: 7.99, stock: 5  }
];

let storeFront = document.getElementById("store-front");

for (let product of products) {
    let stockColor = "green";
    if (product.stock === 0) {
        stockColor = "red";
    }

    let cardHTML = `
        <div style="border: 1px solid #ccc; padding: 15px; width: 200px; border-radius: 8px;">
            <h3>${product.name}</h3>
            <p>Price: $${product.price}</p>
            <p style="color: ${stockColor}; font-weight: bold;">In Stock: ${product.stock}</p>
        </div>
    `;

    storeFront.innerHTML += cardHTML;
}
```

The loop reads each product object, builds an HTML card using its data, and appends it to the page. This is exactly how real inventory systems, product listings, and dashboards work.

> [!tip] Template Literals The backtick syntax `` ` `` used above is called a **[[Template Literal]]**. It lets you embed variables directly inside strings using `${variableName}` instead of messy `+` concatenation. Much cleaner for building HTML strings.

---

## Summary

**Arrays vs Objects:**

|Feature|Array `[]`|Object `{}`|
|---|---|---|
|Structure|Ordered list|Key-value pairs|
|Access|By index number: `arr[0]`|By key name: `obj.key`|
|Best for|Groups of similar things|Describing one specific thing|

**Loops:**

|Loop|Use when...|
|---|---|
|`for`|You know the exact count|
|`while`|You are waiting for a condition to change|
|`for...of`|You want to walk through every item in an array|

---

## External Resources

- [MDN: for loop](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/for)
- [MDN: while loop](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/while)
- [MDN: for...of](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/for...of)
- [MDN: Working with Objects](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Working_with_objects)
- [W3Schools: JavaScript Arrays](https://www.w3schools.com/js/js_arrays.asp)
- [GeeksForGeeks: Arrays of Objects in JavaScript](https://www.geeksforgeeks.org/javascript-array-of-objects/)

---

## Tags

#javascript #loops #objects #arrays #programming #dom #webdev #frontend