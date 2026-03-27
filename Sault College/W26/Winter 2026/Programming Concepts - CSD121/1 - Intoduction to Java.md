# Hybrid Languages

Programming languages are generally categorized by how they are executed. Java is a [[Hybrid language]]

>[!NOTE] Language Types
>- [[Compiled Language]]
>- [[Interpreted Language]]
>- [[Hybrid language]]

- **Interpreted Languages**: Code is executed directly by an interpreter at run-time (e.g., [[Python]], [[JavaScript]]). 
- **Compiled Languages**: A compiler converts code into a machine-ready executable file for a specific platform (e.g., [[C++]], [[Rust]]). 

## Java Way
1. [[javac]] converts your code into an intermediate format called [[Bytecode]]
2. [[Bytecode]] is then executed by the [[Java Virtual Machine]] (JVM)

> [!NOTE] Note
> JVM acts as a middleman, but Java code can run on any device that has a JVM installed

# The Java Development Kit (JDK)

To work with Java, you need the [[Java Development Kit]] (JDK). It contains the essential tools for both creating and running programs: 

- **JVM (`java`)**: The engine that runs the compiled bytecode
- **Compiler (`javac`)**: The tool that transforms your source code into bytecode
- JShell (`jshell`): A [[REPL]] environment for testing Java code quickly without writing a full program

## Java Syntax and Basic Rules

- Semicolons: Every instruction (statement) must end with a semicolon (`;`)
- Curly Braces: Code blocks (like the contents of a loop or function) are wrapped in `{ }` 
- Case Sensitivity: Java treats `foo`, `Foo`, and `FOO` as entirely different things.

> [!NOTE] Indentation
> It's not required for the code to work, but it helps readability 
>
## Comments

- `//` for a **one-line comment**. 
- `/* ... */` for a **multi-line comment**. 

# Variables and Static Typing
## [[Static Typing]]

> In Java, you **must** declare the type of a variable when you create it.

- Java (Static): `int x = 42;` (You explicitly state) 
- Python (Dynamic): `x = 42` (figures it out at run-time) 
## [[Type Inference]]

Modern Java allows the keyword `var` if you provide a value immediately. The compiler "infers" the type for you, but the variable remains statically typed. 

**Analogy:** Think of a variable as a labeled container. In Python, you can put anything in any box. In Java, a box labeled "Shoes" can *only* ever hold shoes; trying to put a "Book" in it will cause a [[Compile-time Error]]. 

# Data Types in Java

1. [[Primitive Types]]: Basic, values that have no extra features (obs. no methods)
	- e.g. `int`, `double`, `boolean`, `char`

2. [[Object Types]]: More complex data that can have behaviors (methods). 
	- e.g. [[String]] and [[ArrayList]]

## Arrays

An [[Array]] is a collection of values. Java arrays have a fixed length and **MUST** contain only one type of data. 

## Errors

* **[[Compile-time Error]]**: Caught by the compiler before program runs. 
	* e.g Syntax mistakes and mistype 
- [[Run-time Error]]: Occurs while the program is executing 
- [[Logic Error]]: The program runs but does the wrong thing because your instructions were flawed. 

>[!NOTE] Note
> Java is case sensitive. It will consider `foo`, `Foo` and `FOO` different

#java #HybridLanguage #JDK #DataTypes
