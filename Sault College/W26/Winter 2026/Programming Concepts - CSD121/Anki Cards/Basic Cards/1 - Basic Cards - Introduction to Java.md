What does JVM stand for?	Java Virtual Machine	
What does JDK stand for?	Java Development Kit	
What is a compiled language?	A language where source code is converted into a machine-ready executable by a compiler before execution	// C++ example: g++ main.cpp -o main
What is an interpreted language?	A language where code is executed line-by-line at run-time by an interpreter	// Python: python script.py
What is a hybrid language?	A language that combines compilation and interpretation — compiled to an intermediate format then interpreted by a VM	// Java: javac → bytecode → JVM
What is bytecode in Java?	An intermediate, platform-independent representation of Java code produced by javac and executed by the JVM	
What tool converts Java source code into bytecode?	javac (the Java compiler)	javac HelloWorld.java
What is JShell?	A REPL environment included in the JDK for testing Java code without writing a full program	jshell> int x = 5; // x ==> 5
What is static typing?	A type system where variable types must be declared explicitly at compile time and cannot change	int age = 25;
What is type inference in Java?	The ability to use the var keyword so the compiler deduces the variable type from the assigned value	var name = "Alice"; // inferred as String
What is a primitive type in Java?	A basic data type that holds a raw value directly, with no methods	int x = 10; double d = 3.14;
What is an object type in Java?	A complex data type that can hold data and expose behaviors (methods)	String s = "hello"; // s.length();
What is a compile-time error?	An error caught by the Java compiler before the program runs, usually due to syntax or type mismatches	int x = "hello"; // type mismatch
What is a run-time error?	An error that occurs while the program is executing, not caught at compile time	int[] a = new int[3]; // a[5]; // ArrayIndexOutOfBoundsException
What is a logic error?	An error where the program runs without crashing but produces incorrect results due to flawed instructions	// Intended sum, but wrote product: int result = a * b;
What is an array in Java?	A fixed-length collection of values of a single data type	int[] nums = {1, 2, 3};
Why is Java considered platform-independent?	Because Java bytecode runs on any device with a JVM installed, regardless of the underlying OS	
What does REPL stand for?	Read-Eval-Print Loop	
What character terminates every statement in Java?	Semicolon (;)	System.out.println("Hi");
What are curly braces used for in Java?	Wrapping code blocks such as loops, methods, and conditionals	if (x > 0) { // System.out.println(x); // }
What syntax is used for a single-line comment in Java?	//	// This is a comment
What syntax is used for a multi-line comment in Java?	/* ... */	/* line 1 // line 2 */
Is indentation required in Java?	No — indentation is not required for execution but is strongly recommended for readability	
int x = 5; // String x = "hi";	Compile-time error — a variable cannot be re-declared with a different type in the same scope	
var list = new ArrayList<String>(); // list.add(42);	Compile-time error — list is inferred as ArrayList<String>, so adding an int is a type mismatch	var list = new ArrayList<String>(); // list.add("hello");
String s = null; // s.length();	Throws NullPointerException at run-time — calling a method on a null reference	String s = "hello"; // s.length(); // returns 5
int[] arr = new int[3]; // arr[3] = 10;	ArrayIndexOutOfBoundsException — valid indices are 0, 1, 2	arr[2] = 10; // valid
int x = 10; // int y = 3; // System.out.println(x / y);	Prints: 3 — integer division truncates the decimal portion	double result = (double) x / y; // prints 3.3333...
