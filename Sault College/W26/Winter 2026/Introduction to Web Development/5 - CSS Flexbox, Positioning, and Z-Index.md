# Why Standard HTML Isn't Enough

By default, HTML elements just _stack_ — one on top of the next, like blocks. That works for simple text pages, but real websites need things side by side: a navbar with a logo on the left and links on the right, a gallery grid, a card with a badge in its corner. Standard HTML stacking can't do that on its own.

Before 2010, developers used a CSS trick called **floats** to force elements side-by-side. It was unreliable, full of bugs, and caused all sorts of headaches. Layouts would break and collapse in unexpected ways.

Enter the two modern heroes: **Flexbox** and **CSS Positioning**.

---

## Part 1: Flexbox — The Macro Layout Tool

Flexbox is used for arranging groups of items — putting them side-by-side, spacing them evenly, or centering them. Think of it as the tool for building _the big picture_ of your layout: grids, navbars, columns.

### The Golden Rule of Flexbox

> [!important] Style the PARENT, not the children You don't apply flex properties to individual items. You apply `display: flex` to their **parent container**, and the children automatically arrange themselves. Think of it like Gru giving orders to his Minions — Gru (the parent) decides the layout, not the Minions (the children).

```css
/* This one line activates Flexbox */
.container {
  display: flex;
}
```

Just adding `display: flex` to a container makes all its direct children line up **side by side** instead of stacking.

---

### The Two Axes

Flexbox works along two axes, and understanding them is the key to mastering it.

- The **Main Axis** is the direction items flow — by default, left to right (a row).
- The **Cross Axis** is perpendicular to the main axis — by default, top to bottom.

Think of a train: the **main axis** is the direction the train is moving, and the **cross axis** is the direction crossing the tracks.

You can flip the main axis with:

```css
.container {
  flex-direction: column; /* Now items stack top to bottom */
}
```

---

### justify-content — Controlling the Main Axis

`justify-content` moves items _along_ the main axis (horizontally, when direction is row).

```css
justify-content: center;        /* All items grouped in the middle */
justify-content: space-between; /* Items pushed to the edges, space distributed between */
```

> [!example] Building a Navbar A classic navbar has a logo on the left and nav links on the right. With Flexbox, this is trivial:
> 
> ```css
> nav {
>   display: flex;
>   justify-content: space-between; /* Logo left, links right */
>   align-items: center;            /* Vertically centered */
> }
> ```
> 
> No manual margins, no math — it just works.

---

### align-items — Controlling the Cross Axis

`align-items` moves items _along_ the cross axis (vertically, when direction is row).

```css
align-items: center;  /* Vertically centered inside the container */
align-items: stretch; /* Items stretch to fill the full height */
```

> [!example] Perfect Center on Screen Want to center a hero message dead in the middle of the screen? Combine both properties:
> 
> ```css
> .hero-section {
>   display: flex;
>   height: 100vh;          /* Full viewport height */
>   justify-content: center;
>   align-items: center;
> }
> ```

---

### gap — Spacing Between Items

Instead of adding margins to every individual child, use `gap` on the parent. It automatically adds space _between_ items (not around the outside).

```css
.gallery {
  display: flex;
  gap: 20px; /* 20px of space between every item */
}
```

---

### flex-wrap — Handling Overflow

By default, Flexbox squeezes all items onto one line. If you have too many items and want them to flow onto a new row when space runs out:

```css
.container {
  display: flex;
  flex-wrap: wrap; /* "If you run out of room, start a new line" */
}
```

---

### flex-grow — Making Items Fill Space

You can tell a specific child item to grow and absorb any leftover empty space.

```css
.content {
  flex-grow: 1; /* "Eat up all the remaining empty space" */
}
```

> [!example] Sidebar Layout A very common layout: a fixed-width sidebar and a content area that fills the rest.
> 
> ```css
> .container { display: flex; }
> .sidebar   { width: 250px; }  /* Fixed size */
> .content   { flex-grow: 1; }  /* Fills everything left over */
> ```

---

### Common Flexbox Mistakes

> [!warning] Watch out for these
> 
> 1. **Putting `display: flex` on the child instead of the parent.** It always goes on the parent container.
> 2. **Forgetting `height`** when trying to vertically center — if the container has no height, there's no space to center within.
> 3. **Confusing `justify-content` and `align-items`** — `justify` works on the Main Axis, `align` works on the Cross Axis.

---

## Part 2: CSS Positioning — The Micro Layout Tool

While Flexbox handles the big structural layout, **positioning** is for fine-tuning specific elements. Use it when you want to:

- Overlay text on top of an image
- Stick a navbar to the top of the screen
- Add a "Sale!" badge to the corner of a product card

There are **five types** of CSS positioning.

---

### 1. Static (Default)

Every element is `position: static` by default. Elements simply follow the normal document flow. Importantly, positioning properties like `top`, `left`, and `z-index` **do not work** on static elements.

---

### 2. Relative — The Nudge

```css
.box {
  position: relative;
  top: 10px; /* Move 10px down from where it would normally sit */
}
```

`position: relative` moves an element _relative to its normal position in the flow_. The critical detail:

> [!important] The Ghost Space When you move a relatively positioned element, it **leaves its original space behind** like a ghost. Other elements do not fill in the gap — they behave as if the element is still in its original spot.

Relative positioning also serves a second, arguably more important purpose: it creates a **containing block** for absolutely positioned children (more on this below).

---

### 3. Absolute — The Jetpack

```css
.badge {
  position: absolute;
  top: 0;
  right: 0;
}
```

An absolutely positioned element is **completely removed from the document flow**. Other elements ignore it and close in around the empty space it left behind. It then gets placed using `top`, `right`, `bottom`, and `left` coordinates.

> [!warning] The Absolute Danger By default, an absolutely positioned element measures its coordinates relative to the entire **page body**. This usually sends it flying off to a completely wrong position.
> 
> The fix: give its **parent** `position: relative`. This creates a "fence" that traps the absolute child, making it position itself relative to that parent instead of the page.
> 
> ```css
> .parent { position: relative; } /* The fence */
> .child  { position: absolute; } /* Trapped inside the fence */
> ```
> 
> **Memorize this pattern.** You will use it constantly.

> [!example] The "New!" Badge Put a badge in the top-right corner of a card:
> 
> ```css
> .card  { position: relative; }   /* The fence */
> .badge {
>   position: absolute;
>   top: -10px;   /* Slightly outside the top edge */
>   right: -10px; /* Slightly outside the right edge */
> }
> ```

---

### 4. Fixed — The Stalker

A fixed element is removed from the document flow like `absolute`, but instead of being relative to a parent or the page, it's glued to the **screen viewport**. It does not scroll with the page — it always stays in the same spot on screen.

Common uses: sticky navbars, chat widgets, cookie banners.

> [!example] Sticky Navigation Bar
> 
> ```css
> nav {
>   position: fixed;
>   top: 0;       /* Stick to the very top */
>   width: 100%;  /* Stretch across the full width */
> }
> ```
> 
> Now the menu follows the user as they scroll down.

---

### 5. Sticky — The Hybrid

`position: sticky` is the best of both worlds. The element behaves like `relative` (normal flow) _until you scroll past it_, at which point it switches to `fixed` behaviour and sticks to the screen.

Perfect for section headers inside long articles — they scroll with content until they reach the top, then stick there.

---

### Quick Reference Table

|Value|Behaviour|Removed from flow?|Reference point|
|---|---|---|---|
|`static`|Normal flow|No|—|
|`relative`|Nudged from normal spot|No (ghost remains)|Its own original position|
|`absolute`|Freely positioned|Yes|Nearest positioned ancestor|
|`fixed`|Pinned to screen|Yes|The viewport|
|`sticky`|Relative until scroll, then fixed|No|Parent container|

---

## Part 3: Z-Index — The Third Dimension

When elements overlap (especially with absolute/fixed positioning), you need to control **which one appears on top**. That's what `z-index` does — it controls the stacking order along the imaginary Z axis (depth).

```css
.background { z-index: 1; }   /* Bottom */
.middle     { z-index: 10; }  /* Middle */
.foreground { z-index: 999; } /* Top */
```

Higher number = closer to the viewer (on top). Lower number = behind other elements.

> [!warning] Z-Index only works with positioning `z-index` has **no effect** on `position: static` elements. You must have `position` set to `relative`, `absolute`, `fixed`, or `sticky` for `z-index` to work.

---

## Summary: Which Tool to Use?

> [!tip] The decision rule
> 
> - **Building a layout with rows or columns?** → Use **Flexbox** (`display: flex` on the parent).
> - **Overlaying, pinning, or precisely tweaking a specific element?** → Use **Positioning** (`position: absolute/fixed/sticky`).
> - **Multiple positioned elements overlapping?** → Use **Z-Index** to control who's on top.
> 
> These tools complement each other — real-world layouts use all three together.

---

## Lab #5 Preview — The Ultimate Gallery

The lab combines everything from this lecture:

1. **Flexbox** to build the photo grid layout
2. **`position: fixed`** for the navbar that stays at the top while scrolling
3. **`position: absolute`** (with a `position: relative` parent) for text captions overlaid on images
4. **`z-index`** to ensure the navbar always appears above the image captions

For further reading and interactive demos: [MDN — Flexbox](https://developer.mozilla.org/en-US/docs/Learn/CSS/CSS_layout/Flexbox) | [CSS Tricks — A Complete Guide to Flexbox](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) | [W3Schools — CSS Positioning](https://www.w3schools.com/css/css_positioning.asp)

---

## Tags

#web #css #flexbox #layout #positioning #frontend #webdev #html #responsive