
|Week|Topic|Role|
|---|---|---|
|Week 1|[[HTML]]|The Skeleton|
|Week 4|[[CSS]]|The Skin|
|Week 8|[[JavaScript]]|The Brain|
|Week 10|[[LocalStorage]]|The Memory|
|**Week 11**|**[[APIs]] & Fetch**|**The Bridge to the World**|

# The Problem: Your Website is an Island

Right now, without APIs, your website only knows what _you_ manually typed into it. It cannot:

- Check the current weather
- Pull the latest stock prices
- Show new posts or live data

It is completely isolated — a lonely island with no connection to the outside world.

## Static vs. Dynamic Websites

> [!note] Static vs. Dynamic
> 
> - **Static website**: The text is hard-coded in your [[HTML]]. If something changes, you have to open your editor and manually rewrite it.
> - **Dynamic website**: The website _asks a server_ for the latest data every time it loads. This is how Netflix, Instagram, and Amazon work.

The goal of this lecture is to build a **bridge off the island** — to learn how to ask other computers for data and inject it directly into your [[HTML]].

# What is an API?

**API** stands for _**Application Programming Interface**_.

That sounds complicated — so think of it as a **restaurant waiter**.

> [!example] The Waiter Analogy
> 
> 1. **You** (the Client / Website) sit at a table.
> 2. You look at the **menu** (the API Documentation).
> 3. You tell the **Waiter** (the API) what you want: _"I need 5 random cat pictures."_
> 4. The Waiter takes your order to the **Kitchen** (the Server / Database).
> 5. The Kitchen prepares the data.
> 6. The Waiter brings your **food** (the Data) back to your table.

The API is the _middleman_ between you and the server. You never talk directly to the kitchen — the waiter handles that.

## Public APIs

There are thousands of free public [[APIs]] available on the internet. Some examples:

- **OpenWeatherMap** — live weather data
- **PokeAPI** — every Pokémon ever made
- **The Breaking Bad API** — quotes and characters

> [!tip] 
> A great directory of free public APIs: [https://github.com/public-apis/public-apis](https://github.com/public-apis/public-apis)

## What Does the Data Look Like?

When the API responds, the data almost always comes back in **[[JSON]]** format (_JavaScript Object Notation_). It looks like a big string of text formatted like a JavaScript Object or Array — something you already know how to work with.

## API Keys

> [!note] API Keys 
> Some APIs are completely free and open (no sign-up needed). 
> 
> Others like OpenWeatherMap or Spotify require an **API Key**. This is a secret password the service gives you when you register, so they can track how many requests you make. Think of it like a VIP pass: without it, you don't get in.

> [!warning] Keep your API Key secret! 
> Never paste your API Key directly into public code or commit it to a public GitHub repository. Others could steal it and use your quota.

# Talking to the Waiter: `fetch()`

In [[JavaScript]], the built-in tool for making API requests is called **`fetch()`**.

Think of `fetch` like sending a letter through the Post Office to another computer, asking for information.

## Basic Syntax

```javascript
fetch('https://some-api-url.com/data');
```

That is literally it. You pass the web address (URL) of the API to `fetch()`.

## HTTP GET Requests

> [!note] HTTP GET 
> By default, `fetch()` performs a **GET** request. This means you are only _reading_ or _getting_ data from the server. You are not deleting, updating, or creating anything. It is the safest and most common type of request.

[[HTTP]] methods: [https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)

# The Problem: Time

Here is the tricky part. JavaScript is **impatient** — it reads and executes code at the speed of light. But when you send a request to an API, it takes _time_ for the server to respond (like waiting for a letter in the mail).

If JavaScript doesn't wait for the response to come back before moving on, your app will **crash** or behave unexpectedly.

## The Solution: `async` and `await`

We need two magic words to tell JavaScript: _"Send this request and PAUSE this function until the data comes back."_

These words are `async` and `await`.

### Magic Word 1: `async`

Put `async` in front of your function declaration. This warns JavaScript that this function involves waiting.

```javascript
async function getCatData() {
    // Code goes here
}
```

### Magic Word 2: `await`

Put `await` in front of your `fetch()` call _inside_ the async function. This is the actual pause button — JavaScript will stop and wait here until the server responds.

```javascript
async function getCatData() {
    let response = await fetch('https://api.thecatapi.com/v1/images/search');
}
```

### Step 3: Unpacking the JSON

When the response arrives, it is still "shrink-wrapped" in [[JSON]] format. You must unpack it to turn it into a real JavaScript object you can read:

```javascript
let data = await response.json();
```

> [!important] 
> Always `await` the `.json()` call too! `response.json()` is also an operation that takes a moment, so it also needs `await` in front of it.

# The Full Recipe

Here is the complete pattern for fetching data from an API:

```javascript
async function getData() {
    // 1. Make the function async
    // 2. Fetch from the API URL (await = pause until done)
    let response = await fetch('https://some-api-url.com/data');

    // 3. Unpack the JSON (await = pause until done)
    let data = await response.json();

    // 4. Use the data
    console.log(data);
}
```

> [!tip] Always `console.log(data)` first 
> Before you try to display data on screen, always check the console first. You need to see the **shape** of the data — is it an Array? An Object? Where exactly is the piece of text or image URL you want?

# Live Demo

This example fetches a random dad joke from a public API and prints it to the console.

```javascript
// 1. Mark the function as async
async function fetchAJoke() {
    console.log("1. Sending the letter to the API...");

    // 2. Pause and wait for the response
    let response = await fetch("https://icanhazdadjoke.com/", {
        headers: { "Accept": "application/json" } // Tell the API we want JSON
    });

    console.log("2. Mail received! Unpacking the box...");

    // 3. Unpack the JSON into a real JavaScript Object
    let jokeData = await response.json();

    // 4. Inspect the full object
    console.log("3. The fully unpacked object:", jokeData);

    // 5. Target the specific property you need
    console.log("THE JOKE: " + jokeData.joke);
}

fetchAJoke();
```

> [!note] The `headers` option 
> Some APIs need you to tell them what _format_ you want the data in. `"Accept": "application/json"` tells the API: _"Please send the response in JSON format."_

# Putting Data on the Screen

`console.log` is great for debugging, but users cannot see the console. Once you have your unpacked data, you display it using the [[DOM]] — exactly the same way you learned in earlier lectures.

## The DOM Workflow

1. Fetch the data
2. Unpack the JSON with `.json()`
3. Grab an HTML element using `getElementById`
4. Set its `innerText` or `src` to the new data

## Traversing Nested Data

Sometimes the API returns an **Array** that contains **Objects** inside. You need to "dig in" to get the specific value you want.

```javascript
// If the API returns an array, access the first item with [0]
// Then access the property you need with dot notation
let imgUrl = data[0].url;
```

> [!tip] 
> Always use `console.log(data)` in the browser DevTools to explore the structure before writing your access code.

# Connecting to a Button

> [!warning] Do NOT put fetch in a loop! 
> APIs cost real money for the people hosting them. Never run `fetch()` inside an infinite loop. Always attach it to a user action like a **button click**, or run it once on page load.

```javascript
myButton.addEventListener('click', getData);
```

# Live Demo 2

This example builds a button that fetches a random cat image and displays it on the webpage.

**HTML:**

```html
<body>
    <div style="text-align: center; margin-top: 50px;">
        <h2>The Infinite Cat Machine</h2>
        <button id="cat-btn">Summon a Cat</button>
        <br>
        <img id="cat-pic" src="placeholder.jpg">
    </div>
    <script src="app.js"></script>
</body>
```

**JavaScript (`app.js`):**

```javascript
let catButton = document.getElementById("cat-btn");
let catImage  = document.getElementById("cat-pic");

async function getNewCat() {

    // Show a "loading" state while waiting
    catButton.innerText = "Summoning...";

    try {
        // A. Ask the API for data
        let response = await fetch("https://api.thecatapi.com/v1/images/search");

        // B. Unpack the JSON
        let data = await response.json();

        // C. Dig into the array to get the image URL
        let newImageUrl = data[0].url;

        // D. Inject it into the DOM
        catImage.src = newImageUrl;

    } catch (error) {
        // If anything goes wrong, catch it gracefully
        console.log("Uh oh, the cats escaped:", error);
        catImage.src = "error-placeholder.jpg";
    }

    catButton.innerText = "Summon Another Cat";
}

catButton.addEventListener("click", getNewCat);
```

# Handling Errors with `try / catch`

What if the user's Wi-Fi drops? What if the API server goes down? Without error handling, your whole page could break. This is where **`try / catch`** comes in.

> [!note] `try / catch` explained
> 
> - The **`try`** block contains your risky code (the fetch request).
> - If anything goes wrong inside `try`, JavaScript jumps straight to the **`catch`** block instead of crashing.
> - The `catch` block receives an `error` object describing what went wrong.

```javascript
try {
    // Try to fetch the data
    let response = await fetch('https://some-api-url.com/data');
    let data = await response.json();

} catch (error) {
    // If it fails, show a polite message instead
    console.log('API Failed!');
    myHeading.innerText = 'Sorry, data unavailable.';
}
```

> [!important] 
> Always wrap your `fetch` calls in a `try / catch` block in real projects. It is what separates a professional app from a fragile one.

# Where to Go From Here

- **[[React]], Vue, Angular** — JavaScript frameworks that make building complex UIs much easier
- **[[Node.js]], Python, SQL** — Backend and database technologies to build your own servers and APIs

# Quick Reference: The Fetch Pattern

```javascript
async function fetchData() {
    try {
        let response = await fetch('API_URL_HERE');
        let data     = await response.json();
        console.log(data); // Always inspect first!

        // Then update the DOM
        document.getElementById('my-element').innerText = data.someProperty;

    } catch (error) {
        console.log('Something went wrong:', error);
    }
}

document.getElementById('my-button').addEventListener('click', fetchData);
```

# Other External Resources

- [[MDN Web Docs]] — `fetch()` reference: [https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
- [[JSON]] explained: [https://www.w3schools.com/js/js_json_intro.asp](https://www.w3schools.com/js/js_json_intro.asp)
- `async` / `await` guide: [https://developer.mozilla.org/en-US/docs/Learn/JavaScript/Asynchronous/Promises](https://developer.mozilla.org/en-US/docs/Learn/JavaScript/Asynchronous/Promises)
- Free Public APIs directory: [https://www.geeksforgeeks.org/top-10-free-public-apis-for-developers/](https://www.geeksforgeeks.org/top-10-free-public-apis-for-developers/)


# Tags

#web #javascript #apis #fetch #async #json #dom #programming #http #webdevelopment