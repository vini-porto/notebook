---
epoch: 1775662544805
modelKey: "gemini-2.5-flash|google"
topic: "Extends vs. Implements"



tags:
  - copilot-conversation
---

**user**: Diference between extends and implements
[Context: Notes: Sault College/W26/Winter 2026/Programming Concepts - CSD121/5 - Generalization and Polymorphism.md]
[Timestamp: 2026/04/08 11:35:44]

**ai**: The `extends` and `implements` keywords are both used in Java to establish relationships between types, but they serve different purposes:

1.  **`extends`**:
    *   Used for **inheritance** between classes, or between interfaces.
    *   When a class `extends` another class, it becomes a subclass and inherits all non-private members (variables and methods) from its superclass. This establishes an "is a" relationship (e.g., `Student` IS A `Person`).
    *   A class can only `extend` one other class (single inheritance).
    *   When an interface `extends` another interface, it inherits all abstract methods from the super-interface. An interface can `extend` multiple interfaces.
    *   **Example from your note**:
        ```java
        class Student extends Person { ... }
        ```
        Here, `Student` inherits from `Person`.

2.  **`implements`**:
    *   Used by a class to **implement an interface**.
    *   When a class `implements` an interface, it promises to provide concrete implementations for all the abstract methods declared in that interface.
    *   This also establishes an "is a" relationship in a behavioral sense (e.g., a `Car` IS A `Drivable` thing if `Drivable` is an interface).
    *   A class can `implement` multiple interfaces.

**In summary:**

| Feature | `extends` | `implements` |
| :------ | :-------- | :----------- |
| **Purpose** | Inheritance (class to class, interface to interface) | Implementing an interface (class to interface) |
| **Relationship** | "Is a" (inherits state and behavior) | "Is a" (agrees to provide specific behavior) |
| **What it connects** | Class to Class, Interface to Interface | Class to Interface |
| **Number allowed** | One superclass (for classes), multiple super-interfaces (for interfaces) | Multiple interfaces |
| **Content** | Inherits concrete and abstract members | Provides implementation for abstract methods |

Your note, [[5 - Generalization and Polymorphism]], focuses heavily on `extends` for class inheritance as part of generalization and subtype polymorphism.
[Timestamp: 2026/04/08 11:35:56]