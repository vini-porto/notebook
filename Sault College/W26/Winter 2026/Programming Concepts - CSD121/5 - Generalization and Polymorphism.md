# OOP Principles So Far

Before diving in, here's a recap of the OOP concepts you should already know:

> [!note] OOP Principles Recap
> 
> - **Abstraction** — Focus on the essential details of something; ignore everything irrelevant. If a `Person` class only needs `name` and `address` for your program, you don't need to add `height`, `birthday`, etc.
> - **Encapsulation** — Wrap up your abstraction into a class with a clean public interface. Users of your class don't need to know _how_ it works internally, only _what_ it does.
> - **Generalization** _(new this lecture!)_ — When multiple classes share common state or behaviour, move those shared parts into a broader, more general class.

# Polymorphism

> [!note] Definition **Polymorphism** means "many forms." In programming, it's the ability for different types to be used in a _uniform_ way — the same operation works across different types.

You've already seen several kinds of polymorphism:

|Type|Example|
|---|---|
|**Type coercion**|`int` automatically converted to `double`|
|**Method overloading**|`print(int x)` and `print(String s)` in one class|
|**Operator overloading**|`+` works on both `String` and `int`|
|**Generic types**|`List<String>`, `List<Integer>` — one type, many forms|

But in OOP, when people say "polymorphism," they usually mean **subtype polymorphism**.

> [!important] Subtype Polymorphism **Subtype polymorphism** is when a subtype value is allowed to be treated as though it were a supertype value. To understand this, we first need to understand **generalization**.

# Generalization

> [!note] Definition **Generalization** means identifying what multiple classes have in common and moving that shared state and behaviour into a single, broader **superclass**.

## Why ?

Imagine you have two classes:

```java
class Student {
    String name;
    String address;
    Program currentProgram;
    List<Course> enrolledClasses;

    public String letterhead() {
        return name + address;
    }
}

class Instructor {
    String name;
    String address;
    Supervisor supervisor;
    List<Course> teachingAssignments;

    public String letterhead() {
        return name + address;
    }
}
```

Notice that `name`, `address`, and `letterhead()` are **duplicated** in both classes. If you ever need to change `letterhead()`, you have to remember to change it in _both_ places. That's messy and error-prone.

**Generalization solves this** by pulling shared parts into a superclass:

```java
class Person {
    String name;
    String address;

    public String letterhead() {
        return name + address;
    }
}

class Student extends Person {
    Program currentProgram;
    List<Course> enrolledClasses;
}

class Instructor extends Person {
    Supervisor supervisor;
    List<Course> teachingAssignments;
}
```

Now `name`, `address`, and `letterhead()` live in one place — `Person`. Any change there automatically applies to both `Student` and `Instructor`.

# Inheritance

> [!note] Definition **Inheritance** is the mechanism by which a subclass automatically gains all non-private members (variables and methods) of its superclass.

The `extends` keyword sets up the inheritance relationship:

```java
class Student extends Person { ... }
```

Because `Student` extends `Person`, you can do this:

```java
Student s = new Student();
String heading = s.letterhead(); // Works! letterhead() is inherited from Person
```

`letterhead()` is not defined in `Student` at all — it's **inherited** from `Person`.

> [!tip] Think of it as "IS A" Inheritance represents an **"is a"** relationship:
> 
> - A `Student` IS A `Person`
> - An `Instructor` IS AN `Employee`
> - A `NullPointerException` IS A `RuntimeException`
> 
> If the "is a" sentence doesn't make sense, inheritance is probably the wrong choice!

> [!warning] Inheritance is NOT "has a"
> 
> - A `Student` HAS A `Program` — so `Program` should be an **attribute** (a variable) of `Student`, _not_ a superclass of it.
> - A `House` HAS A `Room` — `Room` should be an attribute of `House`, not a subclass.
> 
> Confusing "is a" and "has a" is one of the most common design mistakes in OOP.

# Inheritance Hierarchies

Classes connected through inheritance form a **hierarchy**. Higher up = more general (more abstract). Lower down = more specific.

```
          Person
         /      \
     Student   Employee
                  |
              Instructor
```

- `Student` and `Employee` are both subclasses of `Person`
- `Instructor` is a subclass of `Employee` (and therefore _also_ a subclass of `Person`)

> [!example] Java's Exception Hierarchy Java's built-in exception classes are a great real-world example of a deep inheritance hierarchy:
> 
> ```
> Throwable
> ├── Exception
> │   └── RuntimeException
> │       ├── NullPointerException
> │       ├── ArithmeticException
> │       ├── IndexOutOfBoundsException
> │       └── ...
> │   └── IOException, SQLException, ...
> └── Error
>     ├── OutOfMemoryError
>     └── StackOverflowError
> ```
> 
> A `NullPointerException` has the full public interface of `NullPointerException` **plus** `RuntimeException` **plus** `Exception` **plus** `Throwable`.

> [!tip] Not everything belongs in one hierarchy You don't need to force all your classes into a single hierarchy. `Car` and `Building` are completely separate — don't make one a subclass of the other just to use inheritance. Only use inheritance when the "is a" relationship is genuinely true.

# Key Terms

|Term|Meaning|
|---|---|
|**Supertype / Superclass**|A class that defines members common to several subtypes|
|**Subtype / Subclass**|A class that adds its own data and behaviour on top of a supertype|
|**Inherit**|A subtype automatically has all the variables and methods of its supertype|
|**Inheritance hierarchy**|A set of classes connected through supertype/subtype relationships|

# Inheritance in Java: The Details

## Single inheritance only

In Java, a class can only have **one** direct superclass:

```java
class Instructor extends Employee { ... } // OK
// You CANNOT write: class Instructor extends Employee, Person { ... }
```

If no superclass is specified, Java automatically makes `Object` the superclass:

```java
class Person { ... } // Automatically extends Object
```

---

# Visibility and Inheritance

When a superclass has `private` fields, subclasses **cannot** access them directly — even though they're inherited.

```java
class Person {
    private String name; // private!
}

class Student extends Person {
    public String classReport() {
        return "Student " + name + "..."; // ERROR! name is private
    }
}
```

You have two options to fix this:

**Option 1: Use a public getter**

```java
class Person {
    private String name;

    public String getName() { return name; }
}

class Student extends Person {
    public String classReport() {
        return "Student " + getName() + "..."; // OK
    }
}
```

**Option 2: Use `protected` visibility**

> [!note] The `protected` Modifier `protected` members are accessible within the class itself, its package, _and_ all subclasses — even subclasses many levels down the hierarchy.

```java
class Person {
    protected String name; // accessible in subclasses
    protected String address;
}

class Student extends Person {
    public String classReport() {
        return "Student " + name + "..."; // OK now!
    }
}
```

Here's the full visibility table:

|Modifier|Same Class|Same Package|Subclass|Everywhere else|
|---|---|---|---|---|
|`private`|yes|no|no|no|
|_(none)_|yes|yes|no|no|
|`protected`|yes|yes|yes|no|
|`public`|yes|yes|yes|yes|

# Inheritance and Constructors

When you create an object of a subclass, constructors are called **from the top of the hierarchy down**:

```java
// If hierarchy is: Object -> A -> B -> C
C c = new C();
// Calls: Object() → A() → B() → C()
```

## Using `super()` to call a superclass constructor

```java
class Student extends Person {
    public Student(String name) {
        super(name); // Must be the FIRST statement!
    }
}
```

> [!warning] The call to `super(...)` **must be the very first line** in your constructor. If the superclass has a default (no-argument) constructor, Java calls it automatically and you don't need to write `super()` explicitly.

# Overriding Methods

A subclass can provide its own version of a method already defined in a superclass. This is called **overriding**.

```java
class Person {
    protected String name;

    @Override
    public String toString() {
        return name;
    }
}
```

- The method **signature** (name + parameters) must match the superclass method exactly
- The **body** can be completely different
- The `@Override` annotation tells the compiler to warn you if you accidentally don't actually override anything (e.g. you misspelled the method name)

> [!example] Without vs With Override
> 
> ```java
> // Without override:
> var p = new Person("John");
> p.toString(); // Person@12345678  (default Object toString)
> 
> // With override:
> var p = new Person("John");
> p.toString(); // "John"  (our custom version)
> ```

## Using `super` to call the superclass version

Sometimes you want to _extend_ rather than fully _replace_ a superclass method:

```java
class Instructor extends Employee {
    @Override
    public String toString() {
        return super.toString() + "\n" + String.join(", ", teachingAssignments);
    }
}
```

> [!warning] Never use `this.toString()` here! If you call `this.toString()` inside `toString()`, you'd get **infinite recursion**. Always use `super.toString()` to refer to the parent class's version.

---

## Overriding vs. Overloading

These two are easy to confuse:

||Overriding|Overloading|
|---|---|---|
|Where|In a **subclass**|In the **same class**|
|Signature|Must be **identical** to superclass method|Must be **different** from all other versions|
|Purpose|Specialize inherited behaviour|Provide multiple signatures for one method name|

---

# Static vs. Dynamic Types

This is where things get really interesting. Consider:

```java
Person p = new Instructor();
```

- The **static type** of `p` is `Person` — this is what the compiler sees, checked at compile time
- The **dynamic type** of `p` is `Instructor` — this is what actually lives in memory at run time

This works because `Instructor` IS A `Person`.

> [!important] The Rule
> 
> - **Static type** controls _which methods you're allowed to call_ (compile-time check)
> - **Dynamic type** controls _which implementation actually runs_ (run-time decision — called **dynamic method dispatch**)

```java
Person p = new Instructor();

p.toString();    // Calls Instructor's toString() — dynamic method dispatch
p.getCourses();  // COMPILE ERROR: Person has no getCourses() method
```

---

# Dynamic Method Dispatch and Subtype Polymorphism

Dynamic method dispatch is what makes subtype polymorphism so powerful in practice.

> [!example] Animals making sounds
> 
> ```java
> List<Animal> animals = new ArrayList<>();
> animals.add(new Wolf());
> animals.add(new Chihuahua());
> animals.add(new Lion());
> 
> for (Animal a : animals) {
>     a.makeSound(); // Each animal uses its OWN implementation!
> }
> ```
> 
> Even though the list is typed as `List<Animal>`, each object uses its own override of `makeSound()` at run time. A `Wolf` howls, a `Chihuahua` yaps, a `Lion` roars — all from one simple `a.makeSound()` call.

> [!important] Why this matters Subtype polymorphism lets you:
> 
> - Write code using the **simple, general interface** of a superclass
> - Still get the **specialized behaviour** of each subclass at run time
> - Reuse the same code for many different types without rewriting it

# Abstract Classes

Sometimes a superclass exists _only_ to group shared state and behaviour — it should never be instantiated on its own. That's what `abstract` is for.

> [!note] Definition An **abstract class** cannot be directly instantiated with `new`. It is meant to be extended by concrete subclasses. It may define **abstract methods** — methods with no body that subclasses _must_ implement.

```java
abstract class Storage {
    double capacity;
    double used;

    public Storage(double capacity) {
        this.capacity = capacity;
        this.used = 0;
    }

    public boolean hasEnoughCapacity(double amount) {
        return this.capacity - this.used >= amount;
    }

    public abstract void write(String data); // No body — subclass must implement!
}
```

- A class containing **any** abstract method **must** itself be declared `abstract`
- Concrete subclasses **must** provide an implementation for every abstract method (or they themselves must also be declared `abstract`)

```java
class Disk extends Storage {
    @Override
    public void write(String data) {
        // Write data to disk...
    }
}

class Cloud extends Storage {
    @Override
    public void write(String data) {
        // Write data to cloud...
    }
}
```

```java
Storage s = new Storage();  // ERROR: Cannot instantiate abstract class
Storage d = new Disk();     // OK
Storage c = new Cloud();    // OK
```

> [!tip] Abstract classes are great when you want to guarantee that all subclasses implement certain behaviour (like `write()`), while still sharing common code (like `hasEnoughCapacity()`).

---

# Putting It All Together

> [!important] Summary of Key Ideas
> 
> - **Polymorphism** = uniform interaction for different types. **Subtype polymorphism** = subtypes can be used wherever supertypes are expected.
> - **Generalization** = move shared state/behaviour into a superclass. Use `extends` in Java.
> - **Inheritance** = subclasses automatically get all non-private members of their superclass.
> - Use **`protected`** when subclasses need direct access to superclass fields.
> - Constructors run **top-down** in the hierarchy. Use `super(...)` to call a superclass constructor.
> - **Overriding** = redefining a superclass method in a subclass (same signature, new body).
> - **Static type** = what the compiler checks. **Dynamic type** = what actually runs (dynamic method dispatch).
> - **Abstract classes** = cannot be instantiated; exist purely to be extended.

Other info:

- [GeeksForGeeks: Inheritance in Java](https://www.geeksforgeeks.org/inheritance-in-java/)
- [GeeksForGeeks: Polymorphism in Java](https://www.geeksforgeeks.org/polymorphism-in-java/)


## Tags

#computer_science #programming #OOP #java #inheritance #polymorphism #generalization #abstraction #encapsulation #study_notes #software_engineering