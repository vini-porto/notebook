# Java Syntax

Programming languages consist of:

- A set of **[[Tokens]]**
	- A set of **rules** (the grammar of the language) for how tokens may be combined

![[Tokens#Types of Tokens]]

# The Naming Problem

In a large platform like the [[JDK]] (Java Development Kit), there are times when two different identifiers should have the same name.

> **[[Scope]]** and **[[Namespaces]]** is how do we distinguish between two identifiers with the same name.

# Scopes

**[[Scope]]**: A portion of code in which an identifier is "known" or usable.

## Block Scoping in Java

![[Scope#Types of Scope#Languages with Block Scope]]

**[[Java Block Scoping]]**: Java is block scoped.

# Shadowing

![[Shadowing#What Is Shadowing?]]

**Rule**: In a single scope, you cannot have two identifiers with the same name.

**However**: A narrower (inner) scope can reuse a name from an outer scope.

**Result**: The identifier in the innermost scope is the one used. Identifiers in outer scopes with the same name become unusable (are "shadowed").

```java
void sum(int a, int b) {
    // Whoops! This inner 'a' shadows the parameter 'a'
    int a = 3;
    return a + b; // Always returns 3 + b
}
```

>[!NOTE] Best practice 
>**Avoid shadowing** as it makes code confusing and error-prone.

# Namespaces

![[Namespaces#What Are Namespaces?]]

## Characteristics:

- No duplicate names within one namespace
- Different namespaces may have the same names inside

**Example**: `java.util.List` vs `java.awt.List`

- Two types both named `List`
- They are in different **[[Packages]]**, so there is a way to distinguish them

>[!NOTE] Purpose
>Organize identifiers and prevent naming conflicts in large codebases.

## Namespaces and the Dot Operator

![[Dot Operator#What is the Dot Operator]]

### Examples of Dot Operator Usage

|Example|Kind of namespace|Meaning|
|---|---|---|
|`java.util.List`|Package|The `List` type inside the `java.util` package|
|`myArray.length`|Object|The `length` variable inside the `myArray` object|
|`myList.add(..)`|Object|The `add` method inside the `myList` object|
|`List.of(..)`|Type|The `of` method inside the `List` type|
|`Map.Entry`|Type|The `Entry` type inside the `Map` type|

## Namespaces in Java

In Java, the following structures can be considered [[namespaces]]:

- **[[Types]]** (class, record, enum, interface)
- **[[Objects]]**
- **[[Packages]]**

## Type Definitions 

**Rule**: In Java, all code is ultimately inside one [[Type definition]] or another.

**Note**: Yes, even in [[Compact Source Files]] that contain only functions.

### Four Code Structures for Defining Types

**[[Type Definition Structures]]**: Each can be used to define a custom type:

```java
class MyType { .. }
record MyType() { .. }
enum MyType { .. }
interface MyType { .. }
```

**Course focus**: Much of the rest of the course will be spent learning which of these to use for which purposes.

## Types as Namespaces

**[[Types as Namespaces]]**: Types themselves may contain:

- **Variables** (class variables)
- **Functions** (class methods)
- **Other (nested) types**

**Syntactic indicator**: Usually indicated with the `static` keyword.

**Terminology**: An identifier associated with a type namespace is often called a **[[Class Member]]**.

**Examples**:

- **[[Class Variable]]**: A variable belonging to the type itself
- **[[Class Method]]**: A method belonging to the type itself

## Class Members Example

```java
// In MyType.java
interface MyType {
    // NOTE the 'static' keyword indicating CLASS MEMBERS
    static int myClassVariable;
    static void myClassMethod() { .. }
}

// In some other code file...
// Access class members directly via the type namespace
var x = MyType.myClassVariable;
MyType.myClassMethod();
```

**Access pattern**: Class members are accessed via the **type name**, not via an object.

## Nested Types

**[[Nested Types]]**: Types defined inside other types.

**Example**:

```java
// In MyType.java
interface MyType {
    static class MyNestedType {
        static enum MyReallyNestedType { .. }
    }
}

// In some other code file...
// Use the . operator to access nested types:
MyType x;                                    // A variable of type MyType
MyType.MyNestedType y;                       // A variable of type MyNestedType
MyType.MyNestedType.MyReallyNestedType z;    // A variable of type MyReallyNestedType
```

**Navigation**: Use the dot operator to access progressively nested types.

## Hierarchical Types

**[[Hierarchical Types]]**: Types can be arranged into hierarchies where one type can have many **[[Subtypes]]** to any depth.

**Important distinction**: This is **NOT** the same as nested types.

**Example**:

```java
class MyType { .. }                 // In MyType.java
class MySubType extends MyType { .. } // In MySubType.java

// In some other code file...
MyType x;       // A variable of type MyType
MySubType y;    // A variable of type MySubType
                // (which is ALSO of type MyType)
```

**Inheritance relationship**: A subtype "extends" its parent type.

**More later**: This topic will be covered in more detail later in the course.

## Type Definitions for Creating Values

In addition to using type definitions as namespaces (via the `static` keyword), **ANY TIME** you see a `class`, `record`, `enum`, or `interface` block, you are **ALSO** looking at a **[[Type definition]]**.

**What this means**: You will be able to:

- Create variables of that type
- Use them as parameter types
- Use them as return types

**These variables are [[Objects]]**.

**Example**:

```java
class A { .. }      // In A.java
record B() { .. }   // In B.java
enum C { .. }       // In C.java
interface D { .. }  // In D.java

// In some other code file...
A a;  // A variable of type A
B b;  // A variable of type B
C c;  // A variable of type C
D d;  // A variable of type D
```

## Types vs. Objects

### What is a Type?

**[[Type]]**: A kind of value.

**Characteristics**:

- All values of the same type share the same properties and behaviour
- Example: All Lists have a `get` method; all arrays have a `length` property

**Metaphor**: Think of a type (class/record/enum/interface) as a **blueprint** or **recipe** for a certain kind of value.

### What is an Object?

**[[Object]]**: One specific value of some type.

**Characteristics**:

- Every object has a type (e.g., Integer, List, etc.)

**Metaphor**: Think of an object as one specific **instance** of a certain blueprint/recipe.

> [!important]
> Here, we are considering only [[Reference Types]], NOT [[Primitive Types]].

## Objects as Namespaces

**[[Objects as Namespaces]]**: Objects may contain:

- **Variables** (also called [[Instance Variables]], [[Properties]], [[Attributes]], or [[Fields]])
- **Functions** (also called [[Instance Methods]])

**Important**: These instance members are defined in the type definition for that object's type.

**Example**:

```java
// In Point.java
class Point {
    int x;
    int y;
    
    double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
}

// In some other code file...
// A variable of type Point
Point p = new Point(); 

// Accessing fields via the object namespace
p.x = 3; 
p.y = 4; 

// Calling method via the object namespace
double d = p.distanceFromOrigin();
```

## Class Members vs. Instance Members

**CRITICAL DISTINCTION**: The `static` keyword determines whether a member is a **class member** vs. an **instance member**.

**[[Class Member]]**:

- Defined **with** the `static` keyword
- Belongs to the type itself
- Accessed via the type name

**[[Instance Member]]**:

- Defined **without** the `static` keyword
- Belongs to individual objects
- Accessed via an object reference

## Class Members vs. Instance Members: Complete Example

```java
// In Point.java
class Point {
    // A CLASS variable
    static Point ORIGIN = new Point(0, 0); 
    
    // A CLASS method
    static double distanceBetween(Point p1, Point p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // INSTANCE variables
    int x;
    int y;
    
    // An INSTANCE method
    double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
}

// In some other code file...
Point p = new Point(); 

// INSTANCE members accessed via the OBJECT namespace
p.x = 3; 
p.y = 4; 
double d = p.distanceFromOrigin();

// CLASS members accessed via the TYPE namespace
double d2 = Point.distanceBetween(p, Point.ORIGIN);
```

**Key observation**: Notice how class members are accessed using `Point.` (the type) while instance members are accessed using `p.` (the object).

## Reading Java Code: Naming Conventions

### Case Patterns

Use case patterns to identify what kind of thing you're looking at:

|Case pattern|Means you're looking at a...|
|---|---|
|`ALL_CAPS`|[[Constant]] (unchangeable variable)|
|`startsWithLowerCase`|Variable or function/method name|
|`StartsWithUpperCase`|Type (class/interface/record/enum) name|

**Note**: These are conventions, not enforced by the compiler, but universally followed in Java.

### Function Call Detection

**Check if the identifier is followed by parentheses or not**:

- **With parentheses** `()`: A function/method is being called
- **Without parentheses**: A variable is being accessed

## Reading Java Code: Context Clues

Understand what you're looking at based on context:

| Context                                        | Example                  | Means you're looking at a...                    |
| ---------------------------------------------- | ------------------------ | ----------------------------------------------- |
| `new` keyword followed by function call        | `new Rectangle()`        | [[Constructor]] call creating a new object      |
| Variable identifier inside a type namespace    | `Math.PI`                | CLASS variable of that type being accessed      |
| Function identifier inside a type namespace    | `Math.sqrt(9)`           | CLASS method of that type being called          |
| Variable identifier inside an object namespace | `p.x`                    | INSTANCE variable of that object being accessed |
| Function identifier inside an object namespace | `p.distanceFromOrigin()` | INSTANCE method of that object being called     |
| Type identifier inside a type namespace        | `Map.Entry`              | Type nested within the type namespace           |

## Reading Java Code: Examples

Let's analyze each line:

```java
Math.abs(-123);              // Class method
Math.PI;                     // Class variable
Integer x = Integer.parseInt("123"); // Class method
x.toString();                // Instance method
var r = new Rectangle(5,10); // Constructor
r.grow(10,20);              // Instance method
IO.println(r.width);        // Class method (println), AND
                            // instance variable (width) 
System.out.println(x);
// System is a type
// out is a class variable on System
// println is an instance method of the 'out' variable
```

**Complex example breakdown**: In `System.out.println(x)`:

1. `System` - A type
2. `out` - A class variable (static field) of type `System`
3. `println` - An instance method of the `out` object

## Packages as Namespaces

**[[Packages]]**: In Java, code is typically organized broadly into packages.

**Structure**:

- Each package contains a set of related code files
- Package nesting corresponds to actual directory structure

**Example**: All code for a package named `java.util` will be in the `src/java/util` folder.

**Purpose**: Organize large codebases into logical modules and prevent naming conflicts.

## Import Statements

### The Problem Without Imports

**Without import statements**: You would need to **[[Fully Qualify]]** all type names with their package names.

```java
my.pkg.MyType var1;                   // A variable of type MyType
my.pkg.MyType.MyNestedType var2;      // A variable of type MyNestedType
```

**Verbose and repetitive**: This becomes tedious quickly.

### The Solution: Import Statements

**[[Import Statements]]**: Allow you to use short type names without full package qualification.

```java
// With import statements (at top of code file):
import my.pkg.MyType;               // Import MyType
import my.pkg.MyType.MyNestedType;  // Import MyNestedType

// Later in code...
MyType var1;          // A variable of type MyType
MyNestedType var2;    // A variable of type MyNestedType
```

**Benefit**: Code is much cleaner and more readable.

## Import Statement Syntax

Import statements can import two kinds of identifiers:

1. **Types** within a specific package
2. **Class (static) members** within a specific type

### Importing Types from Packages

```java
// Import all types from a PACKAGE
import my.pkg.*;

// Import specific types from a PACKAGE
import my.pkg.MyType;
import my.pkg.MyType.MyNestedType;
```

**[[Wildcard Import]]** (`*`): Imports all types from a package.

**Specific import**: Imports only named types.

### Importing Static Members from Types

```java
// Import all static (class) members from a TYPE
import static my.pkg.MyType.*;

// Import specific static (class) member from a TYPE
import static my.pkg.MyType.myStaticMember;
```

**[[Static Import]]**: Allows you to use static members without qualifying them with the type name.

**Example use case**: After `import static java.lang.Math.*;` you can write `sqrt(9)` instead of `Math.sqrt(9)`.

# Tags

#Java #Namespaces #Scope #Identifiers #OOP #ObjectOriented #JavaSyntax #ClassMembers #InstanceMembers #Packages #Imports #TypeSystem #CodeOrganization #JavaFundamentals #Programming #SoftwareDevelopment #JavaBasics #StaticKeyword #DotOperator #CodeReading