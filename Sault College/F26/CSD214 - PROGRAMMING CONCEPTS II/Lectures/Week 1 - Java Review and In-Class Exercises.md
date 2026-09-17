# The Platform Problem and Why Java Exists

> [!note] What is a "platform"?
> A **platform** is the combination of an **Operating System** (Windows, Linux, macOS) and the underlying **Hardware Architecture** (Intel x86, ARM). For example:
> - Windows + Intel = "WinTel"
> - Linux + Intel = "LinTel"
> - macOS + Intel = "MacTel"
> - Linux + ARM

Traditional languages like C or C++ compile straight down to **machine code**: a set of instructions that only one specific CPU and OS combination understands. That means if you want your app to run on Windows, Linux, and macOS, you basically have to recompile (and sometimes rewrite parts of) your code for each one. This is called the **Platform Problem**: maintaining software across many different hardware/OS combinations is expensive and time-consuming.

## The "Write Once, Run Anywhere" (WORA) Philosophy

Java's answer to the platform problem is an abstraction layer. The idea: write your source code **once**, and run it on **any** device that has a Java Runtime Environment (JRE) installed.

Here's how it pulls this off:

| Step | Tool | What happens |
|---|---|---|
| 1 | **`javac`** (the compiler) | Translates `.java` source files into **Java Bytecode** (`.class` files) instead of machine code |
| 2 | **Bytecode** | An intermediate, platform-independent format, not tied to any specific CPU |
| 3 | **JVM** (Java Virtual Machine) | Reads the bytecode and translates it into the real machine code for whatever hardware it's running on, in real time (acting like an interpreter or Just-In-Time compiler) |

> [!important] The key idea
> You don't ship your program to the operating system. You ship it to the **JVM**. As long as a JVM exists for a given platform, your Java program runs there without any changes.

Compare the two flows:

```
Traditional: Source Code → Compiler → Machine Code (platform-specific) → Execution

Java: Source Code → javac → Bytecode → JVM (Windows / Linux / macOS) → Execution
```

The JVM sits between your application and the hardware, hiding all the messy platform-specific details so your code behaves the same no matter where it runs.

> [!tip]
> If you want a deeper dive into how compilation and interpretation differ across languages, [GeeksforGeeks](https://www.geeksforgeeks.org) has good breakdowns of compiled vs. interpreted languages. This connects to what you'll see later in [[Programming Languages]] and [[Operating Systems]].

---

# POJOs and JavaBeans

In real Java development, we rarely just write loose, disconnected code. Instead, data gets structured into encapsulated units called **POJOs**, and when a POJO follows a specific set of rules, it becomes a **JavaBean**.

> [!note] POJO vs. JavaBean
> - **POJO** (Plain Old Java Object): a simple Java object that isn't tied to any restrictive framework.
> - **JavaBean**: a POJO that follows a strict convention so that frameworks (like Spring Boot or Hibernate) can reliably read and write its data.

## Anatomy of a solid POJO

1. **Encapsulation**: always make attributes `private`. This stops other classes from touching your data directly and forces them to go through controlled methods.
2. **Constructors**: provide at least two:
   - A **no-argument constructor**, which frameworks (like JPA or JSON libraries) need so they can create your objects automatically (often through reflection).
   - A **full-argument constructor**, so you can quickly build a fully-populated object in one line.
3. **Accessors and Mutators (getters/setters)**: the public "front door" to your private data.
   - Convention: `public T getPropertyName()` and `public void setPropertyName(T value)`.

> [!tip]
> Don't type these by hand every time. In IntelliJ, press `Alt+Insert` (or `Cmd+N` on Mac) inside a class to auto-generate constructors, getters, and setters. It keeps your code consistent and saves a lot of typing.

## The JavaBean standard: 4 strict rules

A **JavaBean** is a POJO that specifically follows these rules:

1. **Private fields**: no `public String name`; access must be restricted.
2. **Public accessors**: data is reached through `getFieldName()` and `setFieldName()`.
3. **No-argument constructor**: a public `ClassName() {}` must exist so frameworks can instantiate the object.
4. **Serializable**: implements `java.io.Serializable`, which allows the object to be converted into a byte stream (useful for saving to disk or sending over a network).

> [!example]
> ```java
> public class Person implements java.io.Serializable {
>     private String firstName;
>     private int age;
>
>     public Person() {}  // no-arg constructor
>
>     public Person(String firstName, int age) {
>         this.firstName = firstName;
>         this.age = age;
>     }
>
>     public String getFirstName() { return firstName; }
>     public void setFirstName(String firstName) { this.firstName = firstName; }
>
>     public int getAge() { return age; }
>     public void setAge(int age) { this.age = age; }
> }
> ```

## Essential overrides: toString, equals, hashCode

Every class inherits three methods from `Object` that you'll almost always want to override once your objects need to be printed, compared, or stored in collections like `ArrayList` or `HashSet`.

- **`toString()`**: by default, printing an object gives you a useless memory reference like `Person@15db9742`. Override it to return something human-readable instead.
- **`equals(Object o)`**: by default, `==` (and the default `equals`) only checks whether two variables point to the exact same memory location. Overriding `equals` lets you define **logical equality** instead, e.g., "do these two `Person` objects have the same ID?"
- **`hashCode()`**: whenever you override `equals`, you **must** also override `hashCode`. This keeps the contract that equal objects must produce equal hash codes, which matters a lot for hash-based collections like `HashMap` and `HashSet`.

> [!warning] Common mistake
> If you override `equals()` but forget `hashCode()`, your objects can behave strangely in a `HashSet` or as `HashMap` keys. For example, the same logical item might get added twice because its hash code doesn't match what `equals()` says.

A typical professional implementation of `equals()` looks like this:

```java
@Override
public boolean equals(Object o) {
    // 1. Check if they are the same reference
    if (this == o) return true;

    // 2. Check if o is null or a different class type
    if (o == null || getClass() != o.getClass()) return false;

    // 3. Cast to the specific type
    Person person = (Person) o;

    // 4. Compare relevant fields (logical equality)
    return age == person.age &&
           Objects.equals(firstname, person.firstname) &&
           Objects.equals(lastname, person.lastname);
}

@Override
public int hashCode() {
    // Always override hashCode when you override equals
    return Objects.hash(age, firstname, lastname);
}
```

Why bother overriding `equals()` at all?

1. **Semantic meaning**: it lets objects with different identities but identical data be treated as equal (two different `Product` objects representing the same item, for instance).
2. **Collection integrity**: `HashSet` and `HashMap` rely on `equals()` and `hashCode()` to work correctly. Skip these and you risk duplicate entries in a `Set`.
3. **Encapsulation**: defining equality inside the class means the class itself owns the business rule for what makes two of its objects "equal."

> [!tip]
> Never trust the default `Object.equals()`. Generate `equals()`/`hashCode()` with your IDE (`Alt+Insert`) or write them by hand using `java.util.Objects` to handle `null` checks safely.

## A quick checklist for building a new class

When you're asked to build a new class, this is a solid order of operations:

1. **Define attributes**: declare them `private`.
2. **Generate constructors**: the no-arg and the full-arg versions.
3. **Generate getters/setters**: expose only what needs to be public.
4. **Override lifecycle methods**: `toString`, `equals`, and `hashCode`.
5. **Validate**: if an object can't logically exist without a certain attribute, enforce that in the constructor (e.g., throw an exception if it's missing or invalid).

> [!note] Professional tip
> Writing all of this by hand is a great way to *learn* it. In real enterprise code, though, teams often use **Lombok** (annotations like `@Data` auto-generate the boilerplate at compile time) or plain Java **records**. For this course, you're expected to write it out manually so you understand what's actually happening underneath.

---

# Primitives vs. References

Every variable in Java is either a **primitive type** or a **reference type**, and understanding the difference is key to understanding memory in Java.

| Feature | Primitive Type | Reference Type |
|---|---|---|
| Content | Stores the actual value | Stores an address (pointer) |
| Memory | Stack | Heap (the object itself) |
| Default value | `0`, `false`, etc. | `null` |
| Operations | Direct value manipulation | Method calls on the object |

## Primitive types

Primitives store the **actual value** directly where the variable lives. They're lightweight and fast. Java has eight of them: `byte`, `short`, `int`, `long`, `float`, `double`, `boolean`, and `char`.

```java
int age = 25;
```
Here, `age` holds the number `25` directly in its own little block of memory.

## Reference types

Reference types don't store the object itself. Instead, they store a **memory address (a pointer)** to where the actual object lives, in the heap. Anything that's an instance of a class (`String`, `Person`, `Scanner`, etc.) is a reference type.

```java
Person p = new Person();
```
Here, `p` is a reference. It holds the *location* of the `Person` object. If you later write `p = null;`, you're not deleting the object. You're just telling `p` to stop pointing at it. If nothing else points to that object, it becomes eligible for **Garbage Collection**.

---

# Stack vs. Heap

This is one of the most important mental models in Java, and it explains a lot of "why does this behave like that?" moments later in the course.

```
Stack                          Heap
┌───────────────┐              ┌──────────────────┐
│ Variable: p    │──(address)──▶│  Object: Person   │
└───────────────┘              └──────────────────┘
```

## The Stack: "local" memory

- **Life cycle**: variables here are created when a method is called and destroyed the moment that method finishes.
- **Performance**: very fast, managed as a Last-In, First-Out (LIFO) structure.
- **Contents**:
  - Primitives (`int`, `double`, `boolean`, `char`): store the actual value.
  - References: when you write `Person p = new Person();`, the variable `p` itself (the reference/pointer) lives on the stack.

## The Heap: "global" memory

- **Life cycle**: objects live here as long as at least one active reference (somewhere on a stack) points to them. Once nothing points to an object anymore, the **Garbage Collector** eventually reclaims that memory.
- **Performance**: slower than the stack, since object sizes and lifetimes vary a lot.
- **Contents**: everything created with `new`, including class instances, arrays, and strings (unless specifically interned).

> [!important] Why this distinction matters
> - **Identity vs. equality**: `==` checks if two stack variables hold the *same address*. `.equals()` checks if the *data inside* the heap objects is logically the same.
> - **Passing by value (reference copying)**: when you pass an object into a method, Java copies the *value of the reference* (the address), not the object itself. That's why a method can change the *fields* of an object you pass in, but it can't make your original variable point to a totally different object.
> - **Null references**: setting `p = null;` empties that stack variable's address. If it was the last reference to that heap object, the object is now eligible for garbage collection.

---

## The Equality Trap: Why `==` Isn't Enough

> [!warning] Common misconception
> Two objects with identical data are **not** automatically equal in Java, unless you tell Java how to compare them.

There are two very different questions you can ask about two objects:

- **`==`** asks: *"Do these two variables point to the exact same memory address?"* → this is **reference equality**.
- **`.equals()`** asks: *"Do these two objects have the same content or state?"* → this is **logical equality**.

```java
Person p1 = new Person("John", "Doe");
Person p2 = new Person("John", "Doe");

if (p1 == p2) {
    // This will ALWAYS be false, because p1 and p2 hold
    // different memory addresses in the Heap, even though
    // their data looks identical.
}
```

The fix is to override `.equals()` (and `hashCode()`, as covered above) so the class itself defines what "equal" means for its data (see the [`equals()` example above](#essential-overrides-tostring-equals-hashcode) in the POJOs and JavaBeans section).

> [!tip]
> [W3Schools](https://www.w3schools.com) has a simple side-by-side comparison of `==` and `.equals()` in Java if you want another way to see the difference.

---

## Variable Scope and Lifecycle

**Scope** defines *where* a variable can be accessed and *how long* it stays in memory. Getting this right matters for avoiding memory leaks and accidental data corruption. (Reference: [Oracle's Java Tutorials on variables](http://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html))

### Fields (member variables)

Defined at the class level, fields exist as long as the object does. There are two kinds:

- **Instance variables** (non-`static`): each object gets its *own* independent copy. Two `Person` objects each have their own separate `age` field.
- **Class variables** (`static`): belong to the class itself, not to any one object. There's exactly **one copy** shared across every instance, so if one object changes it, every other instance sees the change.

### Local variables

Declared inside a method body.

- **Scope**: only visible inside the block (`{ ... }`) where they're declared.
- **Lifetime**: created when the method is called, destroyed the moment it finishes.
- **Accessibility**: can't be reached from other methods or outside the class, since they only exist during that method's "stack frame."

### Parameters

Parameters are just local variables that get initialized with whatever the caller passes in.

> [!important]
> Java is **always pass-by-value**:
> - For a primitive, the method gets a copy of the actual value.
> - For an object, the method gets a copy of the *reference* (the address). You can't make the original reference point somewhere else, but you *can* use that reference to modify the object's internal state.

| Variable Type | Declared where? | Lifetime | Visibility |
|---|---|---|---|
| Instance field | Inside class, outside methods | Bound to the object's lifespan | Entire class (via methods) |
| Class field | Inside class, marked `static` | Bound to the JVM process | Entire class (global to all objects) |
| Local variable | Inside a method | During method execution | Only inside that method block |
| Parameter | In the method signature | During method execution | Only inside that method block |

> [!tip] Academic tip
> When debugging, check scope first. A "cannot find symbol" error usually means you're trying to reach a local variable from outside its method, or trying to access a private instance variable from a static context (like `main`) without going through an object reference.

---

## IDE Workflow: Setting Up a Project in IntelliJ

The lecture also walks through the practical setup for the course's in-class exercises:

1. **Create a new IntelliJ project**: Language = Java, Build System = Maven, JDK = version 21.
2. **Create a package** inside `src/main/java` for your exercise (e.g., `lecture_1_1_in_class_exercises`).
3. **Set up version control**: initialize Git and share the project on GitHub as a **private** repository (this matters for academic integrity, so always check the "Private" box).

> [!tip] Shortcut Reference Cheat Sheet
>
> | Action | Windows / Linux | Mac |
> |---|---|---|
> | Generate menu (constructors, getters/setters, etc.) | `Alt+Insert` | `Cmd+N` |
> | Fix error / implement methods | `Alt+Enter` | `Option+Enter` |
> | Reformat code | `Ctrl+Alt+L` | `Cmd+Option+L` |
> | Main method shortcut | type `main` + Tab | type `main` + Tab |
> | Print line shortcut | type `sout` + Tab | type `sout` + Tab |

Writing `public String getSomething() { return this.something; }` by hand fifty times is tedious and error-prone. That's exactly what these generation tools exist to avoid.

---

## In-Class Exercise: The Vehicle Hierarchy

The instructor live-codes a "vertical slice" of this hierarchy: `Vehicle → LandVehicle → Car`.

```
                    «Abstract» Vehicle
                 +String manufacturer, +int year
                        +start() +stop()
                    /        |        \
             LandVehicle  WaterCraft  Aircraft
                 |             |          |
            Car, Motorcycle  Yacht    JetPlane
```

This is a classic **inheritance** structure: `Vehicle` is an abstract base class with shared fields and abstract methods, and each subclass adds its own specific fields and behavior. If inheritance and abstract classes feel new, it's worth reviewing [[Data Structures]] and general [[Software Engineering]] material on OOP design, since this pattern shows up constantly.

### Step-by-step build

**Step 1: Base class (`Vehicle`)**

```java
public abstract class Vehicle {
    private String manufacturer;
    private int year;
    // Do not write methods yet!
}
```
- Generate the no-arg constructor (`Alt+Insert` → Constructor → select none).
- Generate the full constructor (`Alt+Insert` → Constructor → select both fields).
- Generate getters/setters for all fields.
- Manually add the abstract methods:
```java
public abstract void start();
public abstract void stop();
```

**Step 2: Child class (`LandVehicle`)**

```java
public class LandVehicle extends Vehicle {
    private int numWheels;
}
```
- Generate a constructor with `Alt+Insert`. IntelliJ will ask you to "choose constructor to call in super." Pick the one with arguments (`Vehicle(String, int)`). It automatically adds the parent's fields as parameters plus the `super(...)` call. Add `numWheels` to the list too.
- Implement the inherited abstract methods (hover the red error → "Implement methods", or `Alt+Enter`). Simple print statements are fine for now.

**Step 3: Leaf class (`Car`)**

```java
public class Car extends LandVehicle {
    private int numDoors;
}
```
- Generate a constructor that includes the parent constructor's fields plus `numDoors`.
- Generate `toString()` with all fields selected. Tip: check "Append content of super.toString()" if you also want the parent's fields shown.

> [!example]
> This same three-layer pattern (`Vehicle → LandVehicle → Car`) repeats for `WaterCraft → Yacht` and `Aircraft → JetPlane` in the full diagram. The point of the exercise is to practice the "super chain" of constructors as you move down an inheritance tree.

---

## Student Exercise: The Electronics Store

**Task**: use IntelliJ's generation tools to build this hierarchy:

```
ElectronicDevice (abstract)
  -String serialNumber, -double price
  +turnOn()
       │
   Computer extends ElectronicDevice
       -int ramSizeGB, -String processor
       │
   Laptop extends Computer
       -double batteryLifeHours
```

### Requirements

1. **`ElectronicDevice`** (abstract):
   - Fields: `private String serialNumber`, `private double price`.
   - Generate the no-arg constructor and getters/setters.
   - **Manual task**: inside `setPrice`, add an `if` check that throws `IllegalArgumentException` when the price is negative. This is business logic the IDE can't generate for you.

   ```java
   public void setPrice(double price) {
       if (price < 0) {
           throw new IllegalArgumentException("Price cannot be negative");
       }
       this.price = price;
   }
   ```

2. **`Computer`**:
   - Fields: `ramSizeGB`, `processor`.
   - Generate a constructor that accepts all 4 fields (serial number, price, RAM, processor).

3. **`Laptop`**:
   - Field: `batteryLifeHours`.
   - Generate `equals()` and `hashCode()` using `Alt+Insert` (IntelliJ's default template), choosing the field(s) that define identity, usually the serial number.

4. **`Main`**:
   - Create two `Laptop` objects with the **same** serial number.
   - Print `laptop1.equals(laptop2)`. It should print `true`, proving the overridden `equals()` is working correctly.

> [!warning] Common mistake to watch for
> If your two `Laptop` objects print `false` for `.equals()` even with the same serial number, double-check you actually selected the right identity field(s) when generating `equals()`/`hashCode()`. Remember, the default `Object.equals()` would have compared references, not data.

---

## Summary

- Java solves the **Platform Problem** through **WORA**: `javac` compiles to bytecode, and the **JVM** runs that bytecode anywhere.
- **POJOs** are simple encapsulated objects; **JavaBeans** are POJOs that follow four strict rules (private fields, public accessors, no-arg constructor, serializable).
- Always consider overriding **`toString()`**, **`equals()`**, and **`hashCode()`** together, never `equals()` alone.
- **Primitives** store values directly; **references** store addresses pointing into the **heap**. Local variables and references themselves live on the **stack**.
- `==` checks reference equality; `.equals()` checks logical equality. They are not the same thing.
- Java is always **pass-by-value**, even for objects (you pass a copy of the reference, not the object).
- IntelliJ's generation tools (`Alt+Insert`) save time and reduce typos when writing boilerplate, but business logic (like validation) still has to be written by hand.

## Tags

#computer_science #java #programming #oop #software #study_notes
