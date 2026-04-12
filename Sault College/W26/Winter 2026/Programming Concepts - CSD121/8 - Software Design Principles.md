
Good software design achieves three things:

- **Communicates clearly** — other developers (and future you) can read and understand the code
- **Enforces correctness and safety** — the design itself prevents bugs and invalid states
- **Enables testing and change** — the code is easy to test and easy to modify later

# General Principles

## Prefer Simple Over Clever (KISS)

> [!important] KISS - Keep It Simple
> Write code that is easy to _read and understand_, not code that shows off how clever you are. Simple code is almost always better.

> [!example] Simple vs Clever
> 
> ```java
> // Simple — easy to follow
> public int max(int[] nums) {
>     int max = Integer.MIN_VALUE;
>     for (int n : nums) {
>         if (n > max) {
>             max = n;
>         }
>     }
>     return max;
> }
> 
> // Clever — harder to understand at a glance
> public int max(int[] nums) {
>     int m = Integer.MIN_VALUE;
>     for (int i = 0; i < nums.length; m = Math.max(m, nums[i++]));
>     return m;
> }
> ```
> 
> Both do the same thing. The first one wins every time for readability.

## Aim for High Cohesion

> [!note] What is Cohesion? 
> **Cohesion** is how well the elements inside a package, class, or function work together toward a _single, well-defined purpose_.
> 
> Related principle: the **Single Responsibility Principle** — every component should have one job.

**High cohesion** means each component has a clear, single purpose. You always know where to find code and where to put new code.

**Low cohesion** means components have fuzzy, ill-defined responsibilities. Finding or placing code becomes guesswork.

> [!example] Package Cohesion
> 
> ```java
> // High cohesion — everything is about invoices
> // Package: com.example.invoice
> Invoice.java
> InvoiceRepository.java
> InvoiceCalculator.java
> InvoicePrinter.java
> 
> // Low cohesion — random mix of unrelated things
> // Package: com.example.util
> Date.java
> Invoice.java
> UIBuilder.java
> HttpClient.java
> ```

> [!example] Class Cohesion
> 
> ```java
> // High cohesion — PaymentProcessor only does payment things
> public class PaymentProcessor {
>     public void chargeCard(..) {..}
>     public void refundCard(..) {..}
>     public void getBalance() {..}
> }
> 
> // Low cohesion — PaymentProcessor doing totally unrelated things
> public class PaymentProcessor {
>     public void chargeCard(..) {..}
>     public void printInvoice(..) {..}   // why is this here??
>     public boolean isPrime(int n) {..}  // definitely wrong place
> }
> ```

> [!example] Function Cohesion
> 
> ```java
> // High cohesion — function only calculates total
> public double calcTotal(List<Item> items) {
>     double subtotal = 0;
>     for (Item item : items) { subtotal += item.getPrice(); }
>     double tax = subtotal * 0.05;
>     return subtotal + tax;
> }
> 
> // Low cohesion — calculates AND prints (two jobs in one function)
> public double calcAndPrintTotal(List<Item> items) {
>     double subtotal = 0;
>     for (Item item : items) { subtotal += item.getPrice(); }
>     double tax = subtotal * 0.05;
>     double total = subtotal + tax;
>     IO.println("Invoice total: " + total); // side effect mixed in
>     return total;
> }
> ```

## Aim to Reduce Coupling

> [!note] What is Coupling? 
> **Coupling** is how much one component depends on the specific internal details of another component.
> 
> A **dependency** is any object, type, or package that a piece of code relies on to do its job.

**Low (loose) coupling** — minimal dependencies; changing one component doesn't break others; easier to test.

**High (tight) coupling** — lots of intricate dependencies; changing one thing forces changes elsewhere; harder to test.

**Ways to reduce coupling:**

- Depend on more abstract/general types rather than specific ones
    - Use `List` instead of `ArrayList`
    - Use `Map` instead of `HashMap`
    - Use `InputStream` instead of `FileInputStream`
- Use **dependency injection** — provide dependencies from outside rather than creating them inside the component

# A Mindset for Software Design

|Domain|What it covers|Function style|
|---|---|---|
|**Data**|What values can exist? Type definitions.|—|
|**Core Logic**|How does data change? Computations.|Mostly _pure_ functions|
|**Boundary**|How does the program interact with the outside world? (UI, files, DB, network)|Mostly _impure_ functions|

The **Boundary** is where raw input gets transformed into safe data for the Core Logic, and where results get sent back out. Controllers live here, coordinating between boundaries and core logic.

# Data Principles

## Prefer Immutable Data

> [!important] 
> **Immutable data** cannot be changed after it is created. It is easier to reason about, safe to share across components, and especially useful in multi-threaded environments.
> 
> Prefer _immutable records_ over _mutable classes_.

## Avoid Sharing Mutable Data

> [!note] 
> Sometimes immutable data isn't worth the hassle. If you _do_ need mutable data, avoid sharing it.
> 
> - It's fine to use an `ArrayList` inside a function if you return it as a `List` (the returned, shared value is effectively immutable from the caller's perspective).
> - Work with _copies_ of mutable data rather than direct references.

## Make Illegal State Unrepresentable

> [!important] 
> Design your types so the compiler catches invalid values automatically. If invalid data _cannot be created_, it _cannot cause bugs_.

Two practical techniques:

- Use **enums** instead of raw constants
- Validate data in constructors and throw exceptions if invalid

> [!example] Enforcing valid state in a constructor
> 
> ```java
> public record Person(String name, int age) {
>     public Person {
>         if (name == null || name.isBlank()) {
>             throw new IllegalArgumentException("Name cannot be null or blank");
>         }
>         if (age < 0) {
>             throw new IllegalArgumentException("Age cannot be negative");
>         }
>     }
> }
> ```

> [!example] Choosing the right type — representing a future date
> 
> |Choice|Problem|
> |---|---|
> |`String`|No guarantee it's a valid date at all|
> |`LocalDate`|It's a date, but could be in the past|
> |`FutureDate` (custom type)|Only future dates can exist — the best option|
> 
> ```java
> record FutureDate(LocalDate value) {
>     public FutureDate {
>         Objects.requireNonNull(value, "value must not be null");
>         if (!value.isAfter(LocalDate.now(Clock.systemDefaultZone()))) {
>             throw new IllegalArgumentException("Date must be in the future");
>         }
>     }
>     public static FutureDate of(LocalDate value) {
>         return new FutureDate(value);
>     }
> }
> ```
> 
> Make it immutable, validate in the constructor, and now invalid state simply cannot exist.

# Core Logic Principles

## Prefer Pure Functions

> [!note] What is a Pure Function? 
> 
> A **pure function**:
> 
> - Has only explicit inputs (parameters) and explicit outputs (return values)
> - Has **no side effects** (no writing to files, no printing, no modifying shared state)
> - Always returns the _same result_ for the same input
> - Can be called in any order at any time without changing its output
> 
> If _when_ or _in what order_ you call the function matters, it is **not** pure.

> [!tip] Why pure functions are great for testing Testing a pure function is dead simple:
> 
> 1. Set up your inputs
> 2. Call the function
> 3. Check the output
> 
> That's it. No mocking, no setup, no teardown.

> [!important] The strategy
> 
> - Put as much **logic** as possible into pure functions
> - Make **impure** (side-effectful) functions as simple and logic-free as possible
> - Never mix logic and side effects in the same function

> [!example] Separating logic from side effects
> 
> ```java
> // Weak design — logic and side effects tangled together
> void main() {
>     List<Integer> prices = getPrices();
>     int total = 0;
>     for (int price : prices) { total += price; }
>     IO.println("Final total: " + total);  // side effect mixed with logic
> }
> 
> // Better design — logic and side effects are separate
> int sum(List<Integer> values) {       // pure function: no side effects
>     int total = 0;
>     for (int price : values) { total += price; }
>     return total;
> }
> 
> void print(String message) {          // impure but zero logic
>     IO.println(message);
> }
> 
> void main() {
>     List<Integer> prices = getPrices();
>     int total = sum(prices);           // easy to test
>     print("Final total: " + total);
> }
> ```

# SOLID-Related Principles

## Open/Closed Principle

> [!note] Definition
> A software component should be:
> 
> - **Open for extension** — you can add new behaviour
> - **Closed for modification** — you don't need to change the existing code to do so
> 
> Adhering to this principle **loosens coupling**.

> [!warning] Why violations are dangerous 
> A violation means there is a place in your code you _must remember to change_ every time you want to extend the program. The less you have to remember, the fewer bugs you introduce.

> [!example] Open/Closed in practice
> 
> ```java
> // Violation — hard-coded to CannonPrinterDriver
> class ReportCardPrinter {
>     public print() {
>         var driver = new CannonPrinterDriver(); // what if we want Epson?
>     }
> }
> 
> // Improvement — accepts any PrinterDriver
> class ReportCardPrinter {
>     public ReportCardPrinter(ReportCard c, PrinterDriver driver) { .. }
> }
> ```
> 
> ```java
> // Violation — filename is hard-coded
> class Logger {
>     public log(String s) {
>         FileWriter f = new FileWriter("log.txt", true); // always log.txt
>         ...
>     }
> }
> 
> // Improvement — filename is passed in
> class Logger {
>     public Logger(String filename) { .. }
>     public log(String s) {
>         FileWriter f = new FileWriter(filename, true);
>         ...
>     }
> }
> ```

## Liskov Substitution Principle (LSP)

> [!note] Definition 
> 
> Any **subtype** should be usable in place of its **supertype** without changing the behaviour of the program.
> 
> Formally: if a property `p(x)` holds for an object `x` of type `T`, then `p(y)` must also hold for any object `y` of subtype `S`.
> 
> Violations of LSP **increase coupling**.

To avoid violations, follow these rules in subtypes:

- Do **not** throw new exceptions that the supertype doesn't throw
- Do **not** strengthen (make more specific) preconditions
- Do **not** weaken (make less specific) postconditions
- **Preserve** all facts about the supertype

> [!warning] Why LSP violations are harmful
> Violations create places where code that _shouldn't_ care about the internals of a type is forced to handle special cases. This tightly couples that code to implementation details. It also requires every future developer to remember those special cases — which they will eventually forget.

## Interface Segregation Principle (ISP)

> [!note] Definition
> Code should not be forced to depend on methods it doesn't use.
> 
> **More, smaller interfaces** is better than **fewer, larger interfaces**.
> 
> Adhering to ISP increases cohesion and loosens coupling.

The guiding question: **Will ALL implementers need ALL the methods of this interface?**

- Yes → the interface is fine
- No → break it into smaller interfaces

> [!example] Interface Segregation
> 
> ```java
> // Violation — Circle and Sphere are forced to implement methods that don't apply
> interface Shape {
>     public void area();
>     public void perimeter();
>     public void volume();       // doesn't apply to 2D shapes
>     public void surfaceArea();  // doesn't apply to 2D shapes
> }
> 
> // Improvement — split by responsibility
> interface Shape2D {
>     public void area();
>     public void perimeter();
> }
> 
> interface Shape3D {
>     public void volume();
>     public void surfaceArea();
> }
> 
> class Circle implements Shape2D { .. }
> class Sphere implements Shape3D { .. }
> ```

> [!tip] Even small interfaces can violate ISP 
> 
> A `Codec` interface with `encode()` and `decode()` might be fine — but if some classes only encode and some only decode, split it into `Encoder` and `Decoder`. Classes that need both can implement both.

> [!warning] Don't over-segregate
> The goal isn't to have one method per interface. Only split when not all implementers need all methods.

## Dependency Inversion Principle (DIP)

> [!note] Definition
> 
> - **High-level components** should not depend on **low-level components**
> - Both should depend on **abstractions** (interfaces)
> - Abstractions should not depend on details — details should depend on abstractions
> 
> Adhering to DIP **loosens coupling**.

> [!tip] Why "inversion"?
> New programmers naturally write high-level code that directly depends on low-level code. Learning DIP means _inverting_ that natural tendency and instead having both sides depend on a shared interface.

> [!example] Dependency Inversion
> 
> ```java
> // Violation — Foo directly depends on the low-level Logger class
> public class Foo {
>     private Logger logger = new Logger(); // tightly coupled
>     public void doStuff() {
>         logger.logInformation("Something important.");
>     }
> }
> 
> // Improvement — Foo depends on the ILogger interface
> public interface ILogger {
>     void logInformation(String logInfo);
> }
> 
> public class Logger implements ILogger {
>     public void logInformation(String logInfo) {
>         System.out.println(logInfo);
>     }
> }
> 
> public class Foo {
>     private ILogger logger; // depends on abstraction
>     public Foo(ILogger loggerImpl) {
>         this.logger = loggerImpl;
>     }
>     public void doStuff() {
>         logger.logInformation("Something important.");
>     }
> }
> ```

## Dependency Injection vs Dependency Inversion

> [!warning] These are NOT the same thing — don't mix them up!
> 
> - **Dependency Inversion Principle** — a design principle: depend on abstractions, not concrete types
> - **Dependency Injection** — a technique: pass (inject) dependencies in from outside rather than creating them internally
> 
> Dependency Injection is _one way_ to fix a DIP violation, but using Dependency Injection doesn't automatically mean you're following DIP.

> [!example] Injection without inversion (still a violation)
> 
> ```java
> // Uses injection (setLogger), but still depends on concrete Logger — DIP violated
> public class Foo {
>     private Logger logger;
>     public void setLogger(Logger logger) { this.logger = logger; } // injected but wrong type
> }
> 
> // Correct — injection + abstraction = DIP satisfied
> public class Foo {
>     private ILogger logger;
>     public void setLoggerImpl(ILogger loggerImpl) { this.logger = loggerImpl; }
> }
> ```

# Principles Summary Table

|Principle|Communicates Clearly|Enforces Correctness & Safety|Enables Testing & Change|
|---|:-:|:-:|:-:|
|Prefer simple over clever|✓|||
|Aim for high cohesion|✓|✓||
|Make illegal state unrepresentable|✓|✓||
|Prefer immutable data|✓|✓|✓|
|Avoid sharing mutable data|✓|✓|✓|
|Prefer pure functions|✓|✓|✓|
|Inject dependencies instead of instantiating them|✓||✓|
|Liskov Substitution Principle||✓||

## Software Design Patterns

> [!note] 
> **Design patterns** are general, reusable solutions to problems that come up repeatedly in software design. They are not copy-paste code — they are _templates_ for how to structure your solution.

### The Strategy Pattern

> [!note] Problem & Solution 
> 
> **Problem:** A component needs to use different _approaches_ (strategies) to accomplish a task.
> 
> **Solution:** Create a class for each approach that implements a _common interface_. The component depends on the interface, not on any specific strategy.

> [!example] Concrete examples
> 
> - A TicTacToe game needs different computer player behaviours (Random, Minimax). Both implement a `HasTicTacToeStrategy` interface with a `pickNextMove()` method. The game depends on the interface.
> - Payment methods (Credit card, PayPal, Bitcoin) all implement a `processPayment` interface. The rest of the program doesn't care which one is used.

**Abstract structure:**

```
Component ──Uses──> <<interface>> Strategy
                         + method1()
                              ^
                         Implements
                    /      |      \
               Strategy1 Strategy2 Strategy3
```

The component only ever talks to the `Strategy` interface. You can add new strategies without touching the component at all — a great example of Open/Closed Principle in action.

### The Observer Pattern

> [!note] Problem & Solution 
> 
> **Problem:** A component (the **subject** / publisher) needs to notify many other components (the **observers** / subscribers) when something changes.
> 
> **Solution:** The subject holds a list of observers (stored via an interface). When the change happens, the subject loops through the list and calls the notification method on each observer.

> [!example] Concrete examples
> 
> - A **Button** must notify various parts of a program when it is clicked.
> - A **Stock** object must notify various components when its price changes.
> - A **Chat server** must notify all clients when a new message is sent.

**Abstract structure:**

```
Subject
  - List<Observer> observers
  - notifyObservers() {
      for (observer in observers) {
          observer.notify()
      }
    }
──Uses──> <<interface>> Observer
                + notify()
                     ^
                Implements
           /      |      \
      Observer1 Observer2 Observer3
```

**Specific example — Button:**

```
Button
  - List<ClickHandler> clickHandlers
  - addClickHandler(ClickHandler)
──Uses──> <<interface>> ClickHandler
                + handleClick()
                     ^
                Implements
           /      |      \
      Observer1 Observer2 Observer3
```

> [!tip]
> The Observer pattern naturally reduces coupling The subject doesn't know or care _who_ is observing it or _what_ they do with the notification. You can add or remove observers without touching the subject at all.

## Tags

#computer_science #software_engineering #software_design #design_principles #design_patterns #SOLID #cohesion #coupling #pure_functions #immutability #dependency_injection #observer_pattern #strategy_pattern #study_notes