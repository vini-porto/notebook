# CS Study Notes: Maven, Testing, and Type Definitions

These notes cover the three topics likely to appear on your written exam. Each section explains the concept from scratch and shows you exactly what you need to know.

---

## 1. Maven Configuration

[[Software Engineering]]

Maven is a **build automation and project management tool** for Java projects. It handles compiling, testing, packaging, and managing dependencies — all through a single configuration file.

> [!note] What is Maven? Maven uses a file called **`pom.xml`** (Project Object Model) to define everything about your project: its name, version, dependencies, plugins, and build instructions. Think of it as a recipe file for your project.

### The `pom.xml` Structure

Every Maven project has a `pom.xml` at its root. Here is the essential skeleton:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <!-- Project identity (the GAV coordinates) -->
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <!-- Java version settings -->
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <!-- External libraries your project needs -->
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

### Key Concepts to Know

**GAV Coordinates** — Every Maven artifact is identified by three things:

- `groupId`: your organisation or package namespace (e.g. `com.example`)
- `artifactId`: the name of the project/module (e.g. `my-app`)
- `version`: the version string (e.g. `1.0.0`, `2.3.1-SNAPSHOT`)

**Dependency scopes** control when a dependency is available:

|Scope|When it is on the classpath|Typical use|
|---|---|---|
|`compile` (default)|Always|Core libraries|
|`test`|Only during testing|JUnit, Mockito|
|`runtime`|Run and test, not compile|JDBC drivers|
|`provided`|Compile only, not packaged|Servlet API (container provides it)|

> [!important] SNAPSHOT vs Release A version ending in `-SNAPSHOT` (e.g. `1.0.0-SNAPSHOT`) means the project is still in development. Maven will always try to fetch the latest snapshot. A release version (e.g. `1.0.0`) is fixed and immutable.

**The `<properties>` block** is used to centralise repeated values:

```xml
<properties>
    <java.version>17</java.version>
    <junit.version>5.10.0</junit.version>
</properties>

<!-- Then reference them like this: -->
<version>${junit.version}</version>
```

**Plugins** extend Maven's build lifecycle. The most important one for testing is the Surefire plugin:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.1.2</version>
        </plugin>
    </plugins>
</build>
```

> [!tip] Common Maven commands to know
> 
> - `mvn compile` — compiles source code
> - `mvn test` — runs all tests
> - `mvn package` — compiles, tests, and packages (e.g. into a `.jar`)
> - `mvn clean` — deletes the `target/` directory
> - `mvn clean install` — the classic full rebuild

> [!warning] Common mistake Forgetting to set `<scope>test</scope>` on test dependencies like JUnit means they get bundled into your production artifact, which is unnecessary and can cause issues.

---

## 2. Selecting a Thorough Set of Tests

[[Algorithms]]

When asked to design tests for a method, the goal is **maximum coverage with minimum redundancy**. You want to test all the meaningful distinct behaviours.

> [!note] What makes a test "thorough"? A thorough test suite covers: normal inputs, edge cases, boundary values, invalid inputs, and any special conditions specific to the method's logic.

### A Framework for Choosing Tests

Think about these categories every time:

**1. Normal / happy path cases** Test typical inputs that should produce correct, expected output.

**2. Boundary values** Test the edges of valid input ranges. If a method accepts integers 1–100, test 1, 100, and something in the middle like 50.

**3. Edge cases**

- Empty collections (empty list, empty string)
- Single-element collections
- Collections with duplicate values
- `null` inputs (if applicable)
- Zero, negative numbers, `Integer.MAX_VALUE`

**4. Invalid / exceptional inputs** Inputs that should throw an exception. Test that the _right_ exception is thrown.

**5. Special domain logic** Think about what the method actually does and test cases that exercise different branches of its logic.

> [!example] Example: Testing a method `int max(List<Integer> list)` This method returns the largest integer in a list, or throws `IllegalArgumentException` if the list is null or empty.
> 
> A thorough set of tests:
> 
> |Test case|Input|Expected outcome|
> |---|---|---|
> |Single element|`[5]`|`5`|
> |Multiple elements, max at end|`[1, 3, 2]`|`3`|
> |Multiple elements, max at start|`[9, 1, 2]`|`9`|
> |All elements equal|`[4, 4, 4]`|`4`|
> |Negative numbers|`[-3, -1, -5]`|`-1`|
> |Mix of positive and negative|`[-2, 0, 3]`|`3`|
> |Empty list|`[]`|throws `IllegalArgumentException`|
> |Null input|`null`|throws `IllegalArgumentException`|

> [!example] Example: Testing a method `boolean isPalindrome(String s)`
> 
> |Test case|Input|Expected|
> |---|---|---|
> |Classic palindrome|`"racecar"`|`true`|
> |Non-palindrome|`"hello"`|`false`|
> |Single character|`"a"`|`true`|
> |Empty string|`""`|`true`|
> |Two same chars|`"aa"`|`true`|
> |Two different chars|`"ab"`|`false`|
> |Null (if applicable)|`null`|exception or `false`|
> |Even-length palindrome|`"abba"`|`true`|

### Writing Tests in JUnit 5 (Java)

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaxTest {

    @Test
    void singleElement() {
        assertEquals(5, max(List.of(5)));
    }

    @Test
    void maxAtEnd() {
        assertEquals(3, max(List.of(1, 2, 3)));
    }

    @Test
    void emptyListThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> max(List.of()));
    }

    @Test
    void nullThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> max(null));
    }
}
```

> [!tip] Equivalence partitioning Group inputs into _classes_ where every input in a class behaves the same way. Pick one representative from each class. This prevents you from writing ten nearly-identical tests while still missing a whole category.

> [!warning] Common mistake Only testing the "happy path" (typical valid input) and missing empty, null, boundary, or error cases. Examiners specifically look for whether you've thought about these.

---

## 3. Writing Type Definitions: Class, Enum, and Record

[[Programming Languages]]

Java gives you several ways to define a new data type. Choosing the right one depends on what the data represents.

> [!note] The three main options
> 
> - **`class`** — a general-purpose type with mutable or immutable fields and behaviour
> - **`record`** — a concise, _immutable_ data carrier (Java 16+)
> - **`enum`** — a fixed set of named constants

---

### When to use `record`

Use a `record` when you are modelling **simple immutable data** — data that is just a bundle of values with no complex behaviour.

```java
// A 2D point with x and y coordinates
public record Point(double x, double y) {}
```

That one line gives you:

- A constructor `new Point(3.0, 4.0)`
- Accessor methods `point.x()` and `point.y()`
- `equals()`, `hashCode()`, and `toString()` — all auto-generated

> [!example] Record: Representing a student "Define a type to hold a student's ID number and full name."
> 
> ```java
> public record Student(int id, String name) {}
> ```
> 
> Usage:
> 
> ```java
> Student s = new Student(12345, "Alice Smith");
> System.out.println(s.name()); // "Alice Smith"
> System.out.println(s);        // Student[id=12345, name=Alice Smith]
> ```

You can add custom methods to a record, but you **cannot change the fields** after construction — records are always immutable.

---

### When to use `enum`

Use an `enum` when a variable can only hold **one of a fixed set of values**.

```java
public enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

Enums can also carry data and methods:

```java
public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6);

    private final double mass;   // in kilograms
    private final double radius; // in metres

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }
}
```

> [!example] Enum: Days of the week, traffic light states, card suits "Define a type representing the suit of a playing card."
> 
> ```java
> public enum Suit {
>     HEARTS, DIAMONDS, CLUBS, SPADES
> }
> ```

> [!tip] Enum with an interface Enums can implement interfaces, which is useful when each constant needs to behave differently.

---

### When to use `class`

Use a `class` when you need **mutable state**, complex behaviour, inheritance, or things that don't fit neatly into a record or enum.

```java
public class BankAccount {
    private final String owner;
    private double balance;

    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

> [!example] Class: Modelling a mutable entity "Define a type representing a temperature sensor that records the current reading and can be updated."
> 
> ```java
> public class TemperatureSensor {
>     private final String location;
>     private double currentReading;
> 
>     public TemperatureSensor(String location, double initialReading) {
>         this.location = location;
>         this.currentReading = initialReading;
>     }
> 
>     public void updateReading(double newReading) {
>         this.currentReading = newReading;
>     }
> 
>     public double getReading() {
>         return currentReading;
>     }
> 
>     public String getLocation() {
>         return location;
>     }
> }
> ```

---

### Quick Decision Guide

> [!important] Choosing between class, record, and enum
> 
> |Question|Answer|Use|
> |---|---|---|
> |Is it a fixed set of named constants?|Yes|`enum`|
> |Is it just a bundle of immutable data with no complex behaviour?|Yes|`record`|
> |Does it need mutable state or complex behaviour?|Yes|`class`|
> |Does it need to extend another class?|Yes|`class`|

> [!warning] Records cannot be mutable If the description mentions that a field "can be updated" or "changes over time", you cannot use a `record`. You need a `class`.

> [!warning] Enums are not for large or dynamic sets If the set of possible values is not known at compile time (e.g., user-defined categories), use a `class` or `enum`-like pattern instead.

---

## Putting It All Together

When you sit the exam:

For the **Maven question**, focus on what goes inside `pom.xml`: GAV coordinates, `<dependencies>` with correct scopes, `<properties>` for versions, and the compiler plugin for setting the Java version.

For the **testing question**, go through your checklist: normal cases, boundary values, empty/null inputs, exception cases, and any special logic branches in the method.

For the **type definition question**, read the description carefully. Ask: is this a fixed set of values (enum)? Is it just immutable data (record)? Does it have changing state or behaviour (class)?

---

## External Resources

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/index.html)
- [GeeksForGeeks: Java Records](https://www.geeksforgeeks.org/records-in-java/)
- [GeeksForGeeks: Java Enums](https://www.geeksforgeeks.org/enum-in-java/)
- [W3Schools Java Classes](https://www.w3schools.com/java/java_classes.asp)

---

## Tags

#computer_science #java #maven #testing #junit #unit_testing #records #enums #classes #type_definitions #software_engineering #study_notes #exam_prep