
Interfaces are one of the most powerful tools in Java for writing clean, flexible, and maintainable code. This note covers everything from what an interface is, to lambda expressions, streams, and design patterns.

## Why Interfaces Exist

Recall that classes define both **state** (instance variables) and **behaviour** (methods). When we use inheritance, subclasses inherit both from their superclass. But what if a group of classes shares common _behaviour_ but has completely _different_ internal data?

> [!example] 
> Imagine you need to send alerts via Email, SMS, Discord, and a PA system. Each of these has very different internal state (different API keys, endpoints, data structures), so it makes no sense to force them into a class hierarchy. But they all share one behaviour: _sending an alert_.

This is exactly the problem **interfaces** solve.

## What is an Interface?

> [!note] Definition 
> An **interface** is a type definition structure that defines _only behaviour_ specifically, the set of methods that any implementing class must provide. It has **no instance variables** (no shared state).

```java
public interface Alerter {
    void alert(String message);
}
```

Key rules about interfaces:

- All methods in an interface are **`public` and `abstract` by default** — you don't need to write those keywords explicitly.
- Interfaces typically do **not** provide method bodies (the implementation is left to the classes that use them).

## Implementing an Interface

A class uses the `implements` keyword to declare that it provides an implementation for an interface.

```java
public class EmailAlerter implements Alerter {
    @Override
    public void alert(String message) {
        // send an email alert
    }
}
```

> [!important] 
> A class that implements an interface **MUST implement ALL methods** defined by that interface. If it doesn't, the code won't compile.

A class can implement **multiple interfaces** at once (unlike class inheritance, which only allows one superclass):

```java
class Circle implements Drawable, Scalable {
    @Override
    public void draw() { ... }

    @Override
    public void scale(double factor) { ... }
}
```

## Interfaces as Subtypes (Polymorphism)

Classes that implement an interface become **subtypes** of that interface. This means you can write code that accepts any implementation of the interface — this is [[Software Engineering]] polymorphism in action.

```java
void doSomethingThatMightAlert(Alerter alerter) {
    // ...
    if (/* some condition */) {
        alerter.alert("Something happened!");
    }
}
```

You can pass an `EmailAlerter`, `SmsAlerter`, or `DiscordAlerter` — any implementing class works here. This gives you **loose coupling**: your method doesn't care _which_ implementation is used.

> [!tip] 
> Using the interface as the **type** (e.g. `Alerter` instead of `EmailAlerter`) means future implementations can be added with zero changes to existing code. This is a massive win for maintainability.

## Clearing Up the Word "Interface"

The word "interface" gets used in a few different ways in CS. Here's a summary:

|Term|Meaning|
|---|---|
|**API** (Application Programming Interface)|Any programmable service — a web API, a library, or even a single class|
|**Function interface**|The signature of a function (name, parameters, return type)|
|**Public interface of a class**|The public members of a class|
|**Interface (type structure)**|A Java code structure defining a type by its methods|

## Default Methods

Normally, interfaces have no method bodies. But since Java 8, interfaces can provide **default method implementations** as long as the method does not depend on instance variables.

```java
public interface Service {
    void doSomething();
    String doSomethingElse(String arg);

    default void doDefault() {
        // This has a body!
    }
}
```

> [!note] 
> Implementing classes may **override** default methods, but they don't have to. Default methods exist to allow interfaces to evolve without breaking existing implementations.

## Interface Hierarchies

Just like classes, interfaces can **extend other interfaces** using the `extends` keyword. Unlike classes, an interface can extend **multiple** other interfaces.

```java
interface CanMakeSound { void makeSound(); }

interface CanMakeMelody extends CanMakeSound { void makeMelody(); }
interface CanSayWords extends CanMakeSound { void sayWords(); }

// Inherits ALL methods from both parents (plus makeSound from CanMakeSound)
interface CanSingSong extends CanMakeMelody, CanSayWords { void singSong(); }
```

## Interfaces vs Abstract Classes vs Other Types

It helps to think of Java's type structures on a spectrum from _data-oriented_ to _behaviour-oriented_:

|Type Structure|Orientation|Examples|
|---|---|---|
|`enum`|Data|`DayOfWeek`, `Month`|
|`record`|Data|`Point`, `Map.Entry`|
|`class`|Data + Behaviour|`ArrayList`, `HashMap`|
|`abstract class`|Data + Behaviour|`InputStream`, `AbstractButton`|
|`interface`|Behaviour|`List`, `Map`, `Comparable`|

### Side-by-side comparison

|Feature|Interface|Abstract Class|
|---|---|---|
|Can be instantiated with `new`?|No|No|
|Can have instance variables?|No|Yes|
|Can have abstract methods?|Yes (typically only these)|Yes|
|Can have concrete methods?|Only as `default` (no instance vars)|Yes|
|Can have static members?|Yes|Yes|

> [!important] 
> Use an **interface** when many unrelated types share a common behaviour. Use an **abstract class** when subtypes share common _data_ and _behaviour_.

## The Java Collections Hierarchy

The Java standard library (JDK) is built heavily on interfaces. The Collections framework is a great example:

- `Iterable` -> `Collection` -> `List`, `Set`, `Queue`
- `List` is implemented by `ArrayList`, `LinkedList`, `Vector`
- `Set` is implemented by `HashSet`, `LinkedHashSet`, `TreeSet`

This means you can write `List<String> myList = new ArrayList<>()` and switch to `LinkedList` later with just one change.

[[Data Structures]]

## Functional Interfaces

> [!note] Definition 
> A **functional interface** is any interface with exactly **one abstract method**. It can have any number of `default` or `static` methods — those don't count.

```java
@FunctionalInterface
public interface MyInterface {
    void myMethod(); // The ONE abstract method

    default void x() { ... } // Fine — doesn't count
    static void y() { ... }  // Fine — doesn't count
}
```

The `@FunctionalInterface` annotation is optional but recommended — if you add it, the compiler will throw an error if the interface accidentally has more or fewer than one abstract method.

Functional interfaces represent a **single abstract function**, and they are the key to lambda expressions.

## Lambda Expressions

Lambda expressions let you implement a functional interface **without writing a full class**. Think of a lambda as a nameless (anonymous) function.

### Full syntax

```java
(String s) -> { return s.contains("world"); }
```

### Step-by-step simplification

```java
// Step 1: Full form
(String s) -> { return s.contains("world"); }

// Step 2: Types can be inferred
(s) -> { return s.contains("world"); }

// Step 3: Single parameter — parentheses optional
s -> { return s.contains("world"); }

// Step 4: Single return statement — braces and 'return' optional
s -> s.contains("world")
```

> [!example]
> 
> ```java
> @FunctionalInterface
> interface StringPredicate {
>     boolean test(String s);
> }
> 
> void printIf(String s, StringPredicate predicate) {
>     if (predicate.test(s)) {
>         IO.println(s);
>     }
> }
> 
> // Using a lambda instead of a full class:
> printIf(someStr, s -> s.contains("world"));
> ```

## Function References

When a lambda simply forwards its argument(s) to an existing method, you can use a **function reference** as an even shorter alternative.

```java
// These are all equivalent:
processAll(arr, (String s) -> { IO.println(s); });
processAll(arr, s -> IO.println(s));
processAll(arr, IO::println);  // Function reference
```

The `::` operator creates a reference to a method.

|Reference Type|Syntax|Replaces|
|---|---|---|
|Static method|`IO::println`|`s -> IO.println(s)`|
|Type instance method|`String::length`|`s -> s.length()`|
|Constructor|`Person::new`|`(n, a) -> new Person(n, a)`|
|Object instance method|`list::add`|`s -> list.add(s)`|

## Anonymous Inner Classes

Under the hood, lambda expressions and function references are converted by the compiler to **anonymous inner classes** — one-time, unnamed implementations of an interface.

```java
// This lambda...
list.forEach(item -> IO.println(item));

// ...is compiled into this anonymous inner class:
list.forEach(new Consumer<String>() {
    @Override
    public void accept(String item) {
        IO.println(item);
    }
});
```

> [!note] 
> Normally you can't instantiate an interface with `new`. The anonymous class syntax (a code block immediately after `new InterfaceName()`) is a special case that creates and instantiates a one-off implementation inline.

## Useful JDK Functional Interfaces

Java provides several ready-made functional interfaces in `java.util.function`:

|Interface|Description|Example Lambda|
|---|---|---|
|`Consumer<T>`|Takes a `T`, returns nothing|`s -> IO.println(s)`|
|`Supplier<T>`|Takes nothing, returns a `T`|`() -> Math.random()`|
|`Function<T, R>`|Takes a `T`, returns an `R`|`s -> s.length()`|
|`BiFunction<T, U, R>`|Takes a `T` and `U`, returns an `R`|`(a, b) -> a + b`|
|`Predicate<T>`|Takes a `T`, returns a `boolean`|`s -> s.length() > 42`|

## The Stream Interface

The `Stream` interface provides a powerful, functional-style way to process collections. You get a stream from any collection using `.stream()`.

```java
var foods = List.of("apple", "banana", "cherry", "durian", "elderberry");

foods.stream()
    .filter(food -> food.length() > 5)   // Keep only long names
    .map(food -> food.toUpperCase())      // Convert to uppercase
    .forEach(food -> IO.println(food));   // Print each one
```

> [!important] 
> Many `Stream` operations accept **functional interfaces** (lambdas or function references) as arguments. This is what makes them so flexible.

### Chainable operations (return another Stream)

|Operation|Description|Example|
|---|---|---|
|`distinct()`|Remove duplicates|`.distinct()`|
|`filter(pred)`|Keep items matching predicate|`.filter(i -> i > 0)`|
|`limit(n)`|Keep first n items|`.limit(10)`|
|`map(fn)`|Transform each item|`.map(i -> i * 2)`|
|`skip(n)`|Skip first n items|`.skip(10)`|
|`sorted(comparator)`|Sort items|`.sorted((a, b) -> a - b)`|

### Terminal operations (end the chain)

|Operation|Description|Example|
|---|---|---|
|`allMatch(pred)`|True if ALL items match|`.allMatch(i -> i > 0)`|
|`anyMatch(pred)`|True if ANY item matches|`.anyMatch(i -> i > 0)`|
|`count()`|Number of items|`.count()`|
|`forEach(fn)`|Perform action on each item|`.forEach(IO::println)`|
|`max/min(comp)`|Largest / smallest item|`.min((a, b) -> a - b)`|
|`reduce(init, fn)`|Accumulate a result|`.reduce(0, (a, b) -> a + b)`|
|`toList()`|Collect back into a List|`.toList()`|

## Sorting with Comparator

`Comparator` is a functional interface used for sorting. Its `compare(a, b)` method returns:

- `0` if `a` and `b` are equal in ordering
- A **negative** number if `a < b`
- A **positive** number if `a > b`

```java
record Person(String name, List<Integer> luckyNumbers) {}

// Sort by name using compareTo
people.stream()
    .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
    .toList();

// Even cleaner: use Comparator.comparing()
people.stream()
    .sorted(Comparator.comparing(Person::name))
    .toList();

// Sort by second lucky number
people.stream()
    .sorted(Comparator.comparing(p -> p.luckyNumbers().get(1)))
    .toList();
```

> [!tip] 
> `Comparator.comparing(keyExtractor)` is the cleanest way to sort — just give it a function that pulls out the value to compare by.

## Higher-Order Functions in Java

Java does not technically have first-class functions, but lambda expressions and function references give us the same feel:

```java
// "Passing a function" as an argument:
list.forEach(item -> IO.println(item));
list.forEach(IO::println);
```

This works because:

1. Java defines **functional interfaces** as types that represent one function.
2. Lambda expressions / function references are automatically converted to **anonymous inner classes** that implement the expected functional interface.
3. Methods that accept functional interfaces as parameters are effectively higher-order functions.

> [!warning] 
> Java doesn't truly have higher-order functions — what's happening is syntactic sugar. The compiler is generating anonymous classes behind the scenes. Don't confuse this with languages like Python or JavaScript where functions are truly first-class.

## Putting It All Together: GUI Event Handling Example

```java
// Old, verbose way:
class MyButtonClickHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        IO.println("Button was clicked!");
    }
}
JButton button = new JButton();
button.setActionListener(new MyButtonClickHandler());

// Modern, concise way using a lambda:
JButton button = new JButton();
button.setActionListener(e -> IO.println("Button was clicked!"));
```

`ActionListener` is a **functional interface** with one method: `actionPerformed`. The lambda replaces the entire class definition.

# Other Sorces

- [GeeksForGeeks — Java Interfaces](https://www.geeksforgeeks.org/interfaces-in-java/)
- [GeeksForGeeks — Lambda Expressions](https://www.geeksforgeeks.org/lambda-expressions-java-8/)
- [GeeksForGeeks — Java Stream API](https://www.geeksforgeeks.org/stream-in-java/)
- [MDN Web Docs](https://developer.mozilla.org/) 
# Tags

#computer_science #java #interfaces #lambda_expressions #functional_programming #streams #object_oriented_programming #software_engineering #study_notes #polymorphism