# What is Software Testing?

> [!note] Software testing 
> Is the process of running software or software components in specific ways to verify that it works as expected.

# Why Do We Test?

There are three main reasons to test software:

- **Ensure software quality** — make sure the program actually does what it is supposed to do.
- **Identify bugs** — find errors in the code before users encounter them.
- **Detect regressions** — check that new changes haven't accidentally broken something that was already working.

> [!important] 
> A **regression** is when a previously working feature breaks due to a new code change. Automated tests help catch these automatically.

# Kinds of Tests

Tests can be categorized in many ways depending on what angle you're looking at them from.

## By Scope

This refers to _how much_ of the system is being tested at once.

|Type|Tests that...|Examples|
|---|---|---|
|**Unit**|Individual functions / classes behave correctly|JUnit, pytest, NUnit|
|**Integration**|Program components interact correctly|API endpoint tests, service layer tests|
|**End-to-end / System**|Entire software system behaves correctly|UI flows, system workflows|
|**Acceptance**|System meets client requirements|User Acceptance Testing (UAT)|

> [!tip] 
> Think of scope like zoom levels. Unit tests zoom in on one small piece. End-to-end tests zoom all the way out to view the whole system.

## By Execution Pattern

This refers to _how_ the tests are run.

|Type|Description|Examples|
|---|---|---|
|**Manual**|A human tester performs the test(s)|Exploratory testing|
|**Automatic**|Code/scripts automatically perform tests|Unit tests, Selenium, Cypress|
|**Continuous**|Automated tests that run in a CI/CD pipeline|GitHub Actions, Jenkins pipelines|

## By Context

|Type|Description|Examples|
|---|---|---|
|**Static**|Examine code/documentation _without_ execution|Code review, linters, static analyzers|
|**Dynamic**|Testing performed _during_ execution|Almost all other kinds of tests|

## By Testing Purpose

|Type|Tests that...|Examples|
|---|---|---|
|**Functional**|System has certain desired behaviours|Login works, file uploads, report generation|
|**Non-functional**|System has certain desired qualities|Load testing, accessibility, security audits|
|**Regression**|New changes don't break existing functionality|Run automated test suite after updates|
|**Smoke/Sanity**|Critical functions are working|Does it start? Can a user log in?|
|**Alpha/Beta**|New features see early user testing before broad release|Internal alpha, public beta, A/B|

## By Special Focus

|Type|Description|Examples|
|---|---|---|
|**Performance**|Measures speed, scalability, stability|Load, stress, endurance tests|
|**Security**|Tests for vulnerabilities|Penetration testing, fuzzing|
|**Compatibility**|Ensures operation across environments|Browser, OS, device testing|
|**Usability**|Evaluates ease of use and UX quality|User feedback sessions|
|**Recovery/Failover**|Verifies resilience under failure|Simulated crashes, network outages|

# When Should You Test?

> [!important] 
> Test **early and often!** The sooner you catch a bug, the cheaper and easier it is to fix.

Two popular development philosophies around testing:

- **Test-Driven Development (TDD)** — write the tests _before_ you write the code. This can lead to more robust and modular code if done consistently.
- **Bug-Driven Development (BDD)** — write tests _in response to detected bugs_, so those bugs can never silently come back.

# Testing Frameworks

Most programming platforms have dedicated testing frameworks that make writing and running tests much easier.

|Platform|Popular Testing Framework(s)|
|---|---|
|Java|JUnit, TestNG|
|C#/.NET|xUnit, NUnit, MSTest|
|Python|Pytest|
|JavaScript|Jest, Mocha|
|Go|go test (built-in)|
|PHP|PHPUnit, Pest|

> [!tip]
>  For more on testing frameworks in Python, check out [GeeksforGeeks - Pytest](https://www.geeksforgeeks.org/pytest-tutorial/).

# Writing Automated Tests

You _could_ test your program manually every time but that's slow, tedious, and easy to mess up. 

Instead, we write **automated tests**: separate programs that run our code and verify it produces the right output.

Each test should clearly state:

- The **expected output**
- The **actual output**
- Ideally, a clear visual distinction between _passed_ and _failed_ states

A good automated test suite:

- Runs all tests for individual components (unit tests)
- Runs tests for the whole program end-to-end
- Can be re-run quickly and easily after any code change

# Unit Testing with JUnit

**JUnit** is the most widely used unit testing framework for Java. To use it in a Maven project, add it as a dependency in `pom.xml`:

```xml
<!-- Inside the <project> element of pom.xml -->
<dependencies>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>6.0.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

> [!note] 
> The `<scope>test</scope>` tag means this dependency is **only included during test runs**, not in the final compiled program. The default scope is `compile`.

## Anatomy of a JUnit Test

Let's say we have this method we want to test:

```java
/**
 * Determine if a given integer is prime.
 * @param n the number to check
 * @return true if the given number is prime, false otherwise
 */
public static boolean isPrime(int n) {
    // ...
}
```

Here's how we'd write tests for it:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestIntUtils {

    @Test
    public void testThatPrimesArePrime() {
        // Lowest prime (boundary condition)
        assertTrue(IntUtils.isPrime(2));

        // A few typical primes
        assertTrue(IntUtils.isPrime(3));
        assertTrue(IntUtils.isPrime(251));

        // Largish prime
        assertTrue(IntUtils.isPrime(2147483647));
    }
}
```

- **`import org.junit.jupiter.api.Test`** — imports the `@Test` annotation from JUnit.

- **`import static org.junit.jupiter.api.Assertions.*`** — imports assertion methods like `assertTrue`, `assertFalse`, `assertEquals`, etc.

- **The test class** — named `TestIntUtils` (convention: `Test` + name of class being tested).

- **`@Test` annotation** — marks a method as a JUnit test. Every method with this annotation will be automatically run by JUnit.

- **Test method name** — can be anything, but _should clearly describe what is being tested_.

- **Multiple assertions per test** — a single test method can (and often should) contain multiple assertions, as long as they all relate to the same concept being verified.

> [!important] 
> One test class should have **multiple test methods**, one for each distinct behaviour or scenario you want to verify.

Complementary test for _non-prime_ numbers:

```java
@Test
public void testThatNonPrimesAreNotPrime() {
    // 0 and 1 are not prime (edge cases)
    assertFalse(IntUtils.isPrime(0));
    assertFalse(IntUtils.isPrime(1));

    // Negative numbers are not prime
    assertFalse(IntUtils.isPrime(-1));
    assertFalse(IntUtils.isPrime(-2));
    assertFalse(IntUtils.isPrime(-1827342));

    // A few typical non-primes
    assertFalse(IntUtils.isPrime(4));
    assertFalse(IntUtils.isPrime(15));
    assertFalse(IntUtils.isPrime(2019));
}
```

## Running Tests in IntelliJ IDEA

In IDEA, you can run:

- An **individual test method** — right-click on the method
- **All tests in a class** — right-click on the class
- **All tests in the project** — run all tests in the test folder

When tests pass, the IDE shows green checkmarks and a summary like "Tests passed: 2 of 2 tests – 27 ms".

When a test **fails**, the IDE tells you:

- _Which_ test failed
- The **expected** vs **actual** output
- The **exact line** where the failure occurred

> [!example] Failing Test Output
> 
> ```
> AssertionFailedError:
> Expected :false
> Actual   :true
> at IntUtilsTest.testThatNonPrimesAreNotPrime(IntUtilsTest.java:24)
> ```
> 
> This tells you precisely where to look in your code to fix the bug.

# Choosing What to Test

You can't always test every possible input (imagine testing every possible string!). So you need to be strategic about _what_ to test.

## Key Categories to Cover

**1. Input validation**

- Test that valid inputs produce correct outputs.
- _Also_ test that invalid inputs are handled gracefully (don't crash or give wrong results).
- You don't need to test inputs that are outside the documented preconditions.

**2. Boundary conditions**

- Test values at the upper and lower bounds of acceptable ranges.
- Test `null`, empty strings, empty lists, etc.

**3. Special cases**

- Test values that trigger specific branches in your `if` statements or loops.
- Think about zero values, empty collections, single-element collections, etc.

**4. Exception handling**

- If a certain condition _should_ throw an exception, write a test to verify it actually does.

> [!tip] 
> Ask yourself: _"How could this code break or go wrong?"_ Then write tests that try to trigger exactly those circumstances.

## Code Coverage

> [!note] Code coverage
>  is a percentage that indicates how much of your code is actually executed when your tests run. 100% means every line was touched by at least one test.

In large projects, 100% coverage is often not feasible. Instead, prioritize:

- Components that are **critical to correctness**
- Components that are **frequently used**
- Components that **many other parts depend on**

# Testing Classes - Instance Methods & Constructors

Testing instance methods works just like testing static methods — but you need to **create an object first** and configure it before calling the method.

You should also **test constructors** to verify that instance variables are correctly initialized.

```java
class Hero {
    private int health;

    public Hero() { this.health = 100; }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) { health = 0; }
    }

    public int getHealth() { return health; }
}

class HeroTest {

    @Test
    public void testConstructor() {
        var hero = new Hero();
        assertEquals(100, hero.getHealth());
    }

    @Test
    public void testTakeDamage() {
        var hero = new Hero();
        hero.takeDamage(10);
        assertEquals(90, hero.getHealth());
    }

    @Test
    public void testTakeDamage2() {
        var hero = new Hero();
        hero.takeDamage(110);
        assertEquals(0, hero.getHealth(), "Health should never be negative");
    }
}
```

Notice that `testTakeDamage2` tests an **edge case** — what happens when damage exceeds health? The third argument to `assertEquals` is an optional message displayed if the assertion fails, which helps with debugging.

# Another Full Example: `titleCase()`

```java
/**
 * Convert a string to title case.
 * @param s the string to convert
 * @return the converted string
 */
public static String titleCase(String s) { ... }
```

**Testing valid inputs:**

```java
@Test
void testValidInputs() {
    assertEquals("Hello", titleCase("hello"));
    assertEquals("Hello", titleCase("hElLo"));
    assertEquals("Hello, World!", titleCase("hEllO, wOrLd!"));
}
```

**Testing boundary conditions and special cases:**

```java
@Test
void nullRemainsNull() {
    assertNull(titleCase(null));
}

@Test
void emptyStringRemainsEmpty() {
    assertEquals("", titleCase(""));
}

@Test
void singleLetterIsCapitalized() {
    assertEquals("A", titleCase("a"));
    assertEquals("Z", titleCase("z"));
}
```

> [!warning] Common Mistake 
> Many beginners only test the "happy path" — valid inputs that obviously work. Don't forget to test `null`, empty strings, negative numbers, and other edge cases. These are where bugs love to hide.

# External Resources

- [GeeksforGeeks — Software Testing](https://www.geeksforgeeks.org/software-testing-basics/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [CS50 — Introduction to Programming Concepts](https://cs50.harvard.edu/)

# Tags

#software_testing #unit_testing #junit #java #test_driven_development #software_quality #study_notes #software_engineering #computer_science