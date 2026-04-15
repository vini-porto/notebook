# Short Coding Questions - Exam Prep Notes

> [!important]
> There are **3 short coding questions worth 5 marks each**:
> - One on **improving code using dependency injection**
> - One on **creating type definitions from a description**
> - One on **defining and implementing interfaces**

## Question Type (Dependency Injection)

> [!note] What is Dependency Injection?
> **Dependency Injection (DI)** is a design pattern where an object receives the things it depends on (its "dependencies") from the outside, rather than creating them itself. This makes code more **flexible**, **testable**, and **loosely coupled**.


> [!warning] Common Mistake
> Hard-coding a dependency inside a class (e.g., calling `new DatabaseConnection()` inside a method) makes it nearly impossible to swap out that dependency for testing or future changes. That is exactly what DI fixes.

Q1. The following code is tightly coupled. Rewrite it using dependency injection.

```java
// BEFORE — tightly coupled (bad)
class ReportGenerator {
    private MySQLDatabase db;

    public ReportGenerator() {
        this.db = new MySQLDatabase(); // hard-coded dependency
    }

    public void generate() {
        db.connect();
        System.out.println("Generating report from database.");
    }
}
```

Q1. Solution:

```java
// Define an interface for the database
interface Database {
    void connect();
}

// A concrete implementation of Database
class MySQLDatabase implements Database {
    public void connect() {
        System.out.println("Connected to MySQL.");
    }
}

// ReportGenerator no longer creates its own dependency
class ReportGenerator {
    private Database db;

    // The dependency is INJECTED through the constructor
    public ReportGenerator(Database db) {
        this.db = db;
    }

    public void generate() {
        db.connect();
        System.out.println("Generating report from database.");
    }
}

// Usage
Database db = new MySQLDatabase();
ReportGenerator rg = new ReportGenerator(db); // inject it here
rg.generate();
```

> [!example] How the Solution Works
> - The `Database` **interface** defines the contract any class that `implements Database` must have a `connect()` method.
> - `MySQLDatabase` is a **concrete class** that fulfills that contract.
> - `ReportGenerator` now accepts a `Database` object **from the outside** via its constructor - this is the injection.
> - If you ever need to switch to a `PostgreSQLDatabase`, you just pass a different object in. The `ReportGenerator` code does not change at all.
> - This also makes **unit testing** easy: you can inject a fake/mock database without touching real data.

> [!tip]
> look for `new SomeClass()` buried inside another class constructor or method. That is almost always the thing to fix using DI.

## Question Type 2 (Type Definitions)

> [!note] What are Type Definitions?
> In statically typed languages (like Java or TypeScript), **type definitions** describe the structure or "shape" of data. This includes **interfaces**, **abstract classes**, or in TypeScript, `type` aliases. The goal is to clearly define *what* an object looks like without necessarily saying *how* it works.


Q2. Given the following description, create a set of type definitions:

> A `Shape` has a color (string) and can compute its area (returns a double).  
> A `Circle` is a `Shape` that also has a radius (double).  
> A `Rectangle` is a `Shape` that has a width and height (both doubles).

```java
// Base interface — defines what all Shapes must have
interface Shape {
    String getColor();   // every Shape has a color
    double area();       // every Shape can compute its area
}

// Circle extends Shape and adds its own field
interface Circle extends Shape {
    double getRadius();  // a Circle also has a radius
}

// Rectangle extends Shape and adds its own fields
interface Rectangle extends Shape {
    double getWidth();
    double getHeight();
}
```

> [!example] How the Solution Works
> - `Shape` is the **base interface**. It captures properties common to *all* shapes: a color and an area.
> - `Circle` **extends** `Shape`, meaning it inherits everything from `Shape` and also requires a `getRadius()` method.
> - `Rectangle` **extends** `Shape` and adds `getWidth()` and `getHeight()`.
> - These are *type definitions only* — no implementation code yet. They describe the **contract** that implementing classes must follow.

## Question Type 3 (Defining and Implementing Interfaces)

> [!note] Interfaces vs Classes
> An **interface** defines *what* methods a class must have — but not *how* they work. A **class** that `implements` an interface provides the actual code for those methods. One class can implement **multiple** interfaces.

Q3. 

> Define two interfaces, `Steerable` and `Drivable`.
> - `Steerable` must have a `turn(int degrees)` method.
> - `Drivable` must have `go()` and `stop()` methods.
> Define a class `Vehicle` that implements both interfaces so that:
> ```
> var v = new Vehicle("Corvette");
> v.turn(10);  // "The Corvette turned 10 degrees"
> v.go();      // "Vroom! The Corvette is driving."
> v.stop();    // "Screech! The Corvette stopped."
> ```

```java
// Interface 1 — defines turning behaviour
interface Steerable {
    void turn(int degrees);
}

// Interface 2 — defines driving behaviour
interface Drivable {
    void go();
    void stop();
}

// Vehicle implements BOTH interfaces
class Vehicle implements Steerable, Drivable {
    private String name; // stores the vehicle's name

    // Constructor sets the name when creating a Vehicle
    public Vehicle(String name) {
        this.name = name;
    }

    // Required by Steerable
    public void turn(int degrees) {
        System.out.println("The " + name + " turned " + degrees + " degrees");
    }

    // Required by Drivable
    public void go() {
        System.out.println("Vroom! The " + name + " is driving.");
    }

    // Required by Drivable
    public void stop() {
        System.out.println("Screech! The " + name + " stopped.");
    }
}
```

> [!example] How it Works
> - `Steerable` and `Drivable` are two **separate interfaces**, each listing their required methods with no implementation.
> - `Vehicle implements Steerable, Drivable` tells Java that `Vehicle` promises to provide all methods from **both** interfaces. If any method is missing, the code won't compile.
> - The `name` field is a **private instance variable** set in the constructor.
> - Each method uses `this.name` to personalise the output string.

## Question Type 4 (Inheritance with `toString()` Override)

Q.4 **Write `Point` and `LabelledPoint` classes so that:**
```
System.out.println(new Point(1, 2));           // (1,2)
System.out.println(new LabelledPoint(1, 2, "a")); // a:(1,2)
```

```java
class Point {
    private int x;
    private int y;

    // Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }

    // toString controls what gets printed by System.out.println
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

// LabelledPoint IS-A Point (inheritance)
class LabelledPoint extends Point {
    private String label;

    // Constructor calls super() to reuse Point's constructor
    public LabelledPoint(int x, int y, String label) {
        super(x, y); // passes x and y up to Point
        this.label = label;
    }

    // Override toString to add the label prefix
    @Override
    public String toString() {
        return label + ":" + super.toString(); // reuse Point's toString
    }
}
```

> [!example] How it Works
> - `Point` stores `x` and `y` as private fields and overrides `toString()` to return `"(x,y)"`.
> - `LabelledPoint extends Point` — it **inherits** `x`, `y`, and their getters from `Point`.
> - `super(x, y)` in the `LabelledPoint` constructor calls `Point`'s constructor to set those inherited fields.
> - `LabelledPoint` overrides `toString()` to prepend the label, and calls `super.toString()` to get the `"(x,y)"` part rather than rewriting it.
> - `System.out.println(obj)` automatically calls `obj.toString()`, which is why overriding it controls the printed output.

> [!warning]
> Do not re-declare `x` and `y` in `LabelledPoint`. They are already inherited from `Point`. Re-declaring them creates new, hidden fields and causes bugs.

## Tags

#computer_science #java #maven #testing #junit #unit_testing #records #enums #classes #type_definitions #software_engineering #study_notes #exam_prep