# Why Do We Need to Model Data?

Every program operates on data. Depending on the domain, that data looks very different:

|Domain|Example data to represent|
|---|---|
|E-commerce|Customer, Product, Order|
|Personal finances|Account, Transaction, Loan|
|Video game|Player, NPC, Item|
|Web browser|Page, Link, Bookmark, URL|
|IDE|Code file, Undo history, Plugin|

Data can be stored in variables, and values are either **scalar** or **composite**.

## Scalar vs Composite Values

> [!note] Definitions
> - **Scalar** — a single, indivisible value. Examples: `int`, `boolean`, `char`
> - **Composite** — made up of multiple parts. Examples: `String`, Lists, Maps, Objects with multiple properties

## Flat vs Nested Data

- **Flat**: one level of sub-components — e.g. a list of integers, or a `Point(x, y, z)`
- **Nested**: multiple levels — e.g. a list of lists, or a list of `Point` objects

# The Problem with Simple Approaches

Let's say we are building a conference center event manager. An event has a title, start/end datetime, and a list of requested services.

## Just Variables — Not Enough

```java
String hackathon_title = "Hackathon";
String hackathon_start = "2023-10-01T10:00:00";
String hackathon_end   = "2023-10-01T18:00:00";
```

This falls apart as soon as you have multiple events — there's no structure connecting them.

# Lists of Tuples — Fragile

```python
events = [
    ("Hackathon", "2023-10-01T10:00:00", "2023-10-01T18:00:00", ["wifi"]),
]

def show_event_titles(event_list):
    for event in event_list:
        print(event[0])  # You need to know title is at index 0 — brittle!
```

# Maps — Better, but Still Problematic

```python
events = [show_event_titles
    { "title": "Hackathon", "start": "...", "end": "...", "services": ["wifi"] },
]

for event in events:
    print(event["title"])  # Better... but typos cause run-time errors
```

> [!warning] Problems with Maps for Complex Data
> 
> - Typos in key names (`"titl"` instead of `"title"`) are only caught at **run time**
> - When you rename or add a field, every place that uses those keys must be updated manually
> - The IDE/compiler cannot help you catch these issues ahead of time


# The Solution: Define Your Own Types

In statically typed languages like Java, you can define **custom types** so the **compiler helps you catch errors** at compile time rather than at run time.

```java
var event = new Event(
    "Hackathon",
    LocalDateTime.of(2023, 10, 1, 10, 0),
    LocalDateTime.of(2023, 10, 1, 18, 0),
    List.of(Event.Service.WIFI)
);
```

# Four Type-Definition Structures in Java

## `class` — General Purpose Types

The basic structure of a Java class:

```java
class <TypeName> {
    // instance variables (state)
    // constructors
    // instance methods (behaviour)
}
```

> [!example] Example: Event class
>```java
>class Event {
  >  String title;
    >LocalDateTime startTime;
    >LocalDateTime endTime;
>
  >  Event(String title, LocalDateTime startTime, LocalDateTime endTime) {
    >    this.title = title;
    >    this.startTime = startTime;
    >    this.endTime = endTime;
    >}
>
  >  Duration getDuration() {
    >    return Duration.between(startTime, endTime);
    >}
>}
>```

## The `this` Keyword

> [!note] What does `this` mean? 
> Inside a type definition, **`this`** refers to _the current instance_ being accessed.
> 
> - When you call `e1.getDuration()`, `this` inside `Event` refers to `e1`
> - When you call `e2.getDuration()`, `this` refers to `e2`
> - `this` cannot be used in _class (static) methods_ — only instance methods

# Types vs Objects

> [!important] Key Distinction
> 
> - **Type** = the _blueprint_. The code that defines the variables and methods.
> - **Object** = a _thing made from the blueprint_. A live, in-memory instance at runtime.

```java
// Type definition (blueprint)
class House {
    boolean isDoorOpen = false;
    boolean isFireLit = false;

    void openDoor() { isDoorOpen = true; }
    void lightFire() { isFireLit = true; }
}

// Creating two separate object instances
House house1 = new House();
House house2 = new House();
house2.openDoor();
house2.lightFire();
// house1 is unaffected — each object has its own state!
```

Objects of the **same type** share the same _behaviour_ (methods) but can have different _state_ (variable values). Objects of **different types** have entirely different state and behaviour.

# Constructors

- A constructor always has **the same name as the type**
- It has **no explicit return type** (the return type is implicitly the type itself)
- Java auto-inserts a no-argument constructor **only if no constructor is defined**

> [!warning] Common Gotcha with Constructors 
> If you define _any_ constructor, Java will **not** add a default no-arg constructor automatically.
> 
> ```java
> class Counter {
>     int count;
>     Counter(int initialCount) { count = initialCount; }
> }
> var c = new Counter(); // COMPILE ERROR — no default constructor!
> ```

## Overloading Constructors

You can create **multiple constructors** with different parameter lists (different _signatures_):

```java
// Constructor 1: explicit start and end times
Event(String title, LocalDateTime startTime, LocalDateTime endTime) { ... }

// Constructor 2: start time + duration
Event(String title, LocalDateTime start, Duration duration) {
    this.title = title;
    this.startTime = start;
    this.endTime = start.plus(duration);
}
```

## Calling One Constructor from Another

Use `this(...)` inside a constructor to call another constructor of the same class:

```java
class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point() {
        this(12, 34); // calls the other constructor with default values
    }
}
```

> [!warning] Common Error: Forgetting to Initialize Instance Variables
> 
> ```java
> class Foo {
>     List<Integer> list; // never initialized!
>     void add(int x) {
>         list.add(x); // NullPointerException at runtime!
>     }
> }
> ```
> 
> **Fix:** always initialize your instance variables:
> 
> ```java
> List<Integer> list = List.of(); // initialized to an empty list
> ```

# Access Modifiers

Access modifiers control _who can see_ a member of a type.

|Modifier|Accessible from|
|---|---|
|`private`|Only within the same type definition|
|_(none — package-private)_|Any file within the same package|
|`public`|Any file, anywhere|

```java
class Foo {
    int a;          // package-private (default)
    private int b;  // only accessible inside Foo
    public int c;   // accessible everywhere
}
```

Top-level type definitions can be package-private or `public`. Nested types can also be `private`.

## Why Access Modifiers Matter

Access modifiers let type designers:

- **Expose a clean API** — `public` members form the _public interface_ of the type (also called **abstraction**)
- **Hide internal details** — `private`/package-private members are _encapsulated_ implementation details

> [!important] Abstraction vs Encapsulation
> 
> - **Abstraction** — focusing on essential details and ignoring irrelevant complexity. The user sees _what_ the type does, not _how_.
> - **Encapsulation** — hiding the internal complexity behind a simple interface. The designer builds the complex internals; the user only sees the clean surface.

# Common Pattern: Private Variables, Public Methods

```java
public class Counter {
    private int count; // hidden detail

    public void increment() { count++; }    // public interface
    public int getCount() { return count; } // public interface
}

var c1 = new Counter();
var c2 = new Counter();
c1.increment();
c1.increment();
c1.getCount(); // 2
c2.increment();
c2.getCount(); // 1
```

**Why not just make `count` public?** If you later want to change the internal implementation (say, store a list of timestamps instead of a plain integer), you can do so _without breaking any code that uses Counter_ — because the public interface (`increment()`, `getCount()`) stays the same.

> [!example] Same public interface, different implementation
> 
> ```java
> // Original
> class Counter {
>     private int count;
>     public void increment() { count++; }
>     public int getCount() { return count; }
> }
> 
> // Refactored internally — users of Counter don't need to change anything!
> class Counter {
>     private List<Long> timestamps = new ArrayList<>();
>     public void increment() { timestamps.add(System.currentTimeMillis()); }
>     public int getCount() { return timestamps.size(); }
> }
> ```

> [!tip] Minimize the Public Interface 
> Every `public` member is a **promise**: "This will always be here." The less you expose, the more freedom you have to change internals later.

# Accessors, Mutators, Getters, and Setters

| Term         | What it does                                                       |
| ------------ | ------------------------------------------------------------------ |
| **Accessor** | Reads values; never changes any state                              |
| **Mutator**  | Changes one or more values                                         |
| **Getter**   | Accessor that reads one specific instance variable; named `getX()` |
| **Setter**   | Mutator that sets one specific instance variable; named `setX()`   |

# Useful Methods to Implement

## `toString`

Converts an object to a human-readable string. Called automatically when an object is passed to `println`.

```java
public String toString() {
    return "Event %s starts at %s and ends at %s".formatted(title, start, end);
}
```

## `equals`

Determines **value equality** (are the contents the same?) rather than **reference equality** (are they the same object in memory?).

```java
public boolean equals(Object other) {
    if (other == null || this.getClass() != other.getClass()) return false;
    Event otherEvent = (Event) other;
    return title.equals(otherEvent.title)
        && start.equals(otherEvent.start)
        && end.equals(otherEvent.end);
}
```

## `hashCode`

Used by hash-based collections like `HashMap`. Objects that are _equal_ according to `.equals` **must** return the same `hashCode`.

```java
public int hashCode() {
    return Objects.hash(title, start, end);
}
```

> [!important] Whenever you implement `.equals`, always implement `.hashCode` too!

# Copy Constructor

Creates a new object as a copy of an existing one:

```java
public Event(Event other) {
    this.title = other.title;
    this.start = other.start;
    this.end = other.end;
}
```

This is a _shallow copy_, but that is fine here because `String` and `LocalDateTime` are **immutable** — you cannot change them through the copy, so the original is safe.

> [!tip] IDE Code Generation 
> Most modern IDEs (like IntelliJ) can auto-generate boilerplate like getters, setters, `equals`, `hashCode`, `toString`, and constructors. Use the "Generate" menu to save time!

# `enum` — Fixed Sets of Values

Sometimes a type should only have a specific, limited set of possible values.

> [!warning] Don't Use Strings for This Using strings like `"NORTH"`, `"SOUTH"` etc. is error-prone — typos like `"nrht"` only cause errors at **run time**, and switches need a messy `default` case.

Instead, use an **enum**:

```java
enum Direction { NORTH, SOUTH, EAST, WEST }
enum Answer    { YES, NO, MAYBE }
enum Status    { ORDERED, SHIPPED, COMPLETE }
enum Service   { WIFI, CATERING, SECURITY }

var a = Answer.YES;
var s = Service.WIFI;
```

```java
public static Direction opposite(Direction d) {
    return switch (d) {
        case NORTH -> SOUTH;
        case SOUTH -> NORTH;
        case EAST  -> WEST;
        case WEST  -> EAST;
        // No default needed — compiler ensures all cases are handled!
    };
}

opposite(Direction.NRTH); // COMPILE ERROR — caught immediately!
opposite("NORTH");        // TYPE ERROR — wrong type entirely!
```

> [!important] Enum advantages
> 
> - Typos are **compile-time** errors, not run-time surprises
> - The compiler ensures all values are handled in a `switch`
> - Full type safety — you can't pass the wrong type by accident

# `record` — Data-Focused Types

When you just need a type to hold a fixed set of data fields, use `record` instead of `class`.

```java
public record Person(String name, int age) {}

var person = new Person("Alice", 30);
IO.println(person.name()); // Alice
IO.println(person.age());  // 30

public record Rectangle(int x, int y, int width, int height) {}
var r = new Rectangle(10, 10, 100, 200);
```

A `record` automatically gives you:

- A constructor with all fields as parameters
- Accessor methods with the same name as each field (note: no `get` prefix — just `name()`, not `getName()`)
- `equals`, `hashCode`, and `toString` — all auto-generated
- **Immutability** — no setters; fields cannot be changed after creation

> [!tip] In Java, when you need a composite type that isn't a collection, **start with `record`** instead of `class`. Upgrade to `class` only if you need mutability or more complex behaviour.

Both `enum` and `record` can have additional methods and class members attached to them just like a regular class.

# `interface` — Defining Behaviour

While `record` defines a type in terms of _data_, an `interface` defines a type in terms of _behaviour_ — a set of methods that implementing types **must** provide.

```java
public interface Runnable {
    void run();
}

public interface Comparable<T> {
    int compareTo(T other);
}
```

A class _implements_ an interface, promising to provide all the methods it defines. A class may implement multiple interfaces.

|Interface|Key method(s)|Implemented by|
|---|---|---|
|`Comparable`|`compareTo`|`Integer`, `LocalDateTime`, all enums|
|`List`|`add`, `get`, `set`, etc.|`ArrayList`, `LinkedList`|
|`Map`|`put`, `get`, `containsKey`, etc.|`HashMap`, `TreeMap`|
|`Runnable`|`run`|`Thread`, `FutureTask`|

> [!example] Why interfaces are powerful
> 
> ```java
> // Any object whose type implements Runnable can be passed here —
> // regardless of what it looks like internally
> public void logTheRun(Runnable task) {
>     IO.println("Starting...");
>     task.run();
>     IO.println("Done.");
> }
> ```
> 
> This allows types with completely different internal structure to be treated uniformly through a shared interface.


External references:

- [GeeksForGeeks — Java Classes and Objects](https://www.geeksforgeeks.org/classes-objects-java/)
- [GeeksForGeeks — Java Enums](https://www.geeksforgeeks.org/enum-in-java/)
- [GeeksForGeeks — Java Records](https://www.geeksforgeeks.org/record-classes-in-java/)

## Tags

#computer_science #java #object_oriented #types #classes #encapsulation #abstraction #enums #records #interfaces #study_notes #programming