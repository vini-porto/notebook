
JavaFX is a **set of GUI (Graphical User Interface) components** for building graphical desktop applications in Java. Think of it as the modern, more powerful replacement for the older Swing library. Instead of building plain text-based programs, JavaFX lets you create real windows with buttons, text fields, shapes, animations, and more.

Related topics: [[Software Engineering]], [[Programming Languages]]

# Setting Up JavaFX

> [!important] 
> Since Java 11, JavaFX is **not included** in the JDK. You need to add it separately.

You have two options:

**Option 1 — Download manually** and add it as a library to your project.

**Option 2 — Use Maven** (recommended) by declaring it as a dependency:

```xml
<dependency>
  <groupId>org.openjfx</groupId>
  <artifactId>javafx-controls</artifactId>
  <version>23</version>
</dependency>
```

## Java Modules and JavaFX

JavaFX uses Java's **module system** — a way of organizing packages so that only the parts you explicitly allow are visible to the outside world.

Every JavaFX app that uses modules needs a `module-info.java` file:

```java
module myjavafxmodule {
    requires javafx.controls; // This module depends on javafx.controls

    // Export your package so JavaFX can access your Application subclass
    exports myjavafxpackage;
}
```

> [!note] A **module** is a collection of related packages. By using modules, you have fine-grained control over what is accessible from your code.


# Features of JavaFX

JavaFX is a rich framework with many capabilities:

- A large set of ready-to-use UI controls (buttons, sliders, text fields, etc.)
- Flexible event handling and data binding APIs
- **FXML** — a declarative XML-based way to define your UI layout
- **CSS styling** — you can style your app just like a web page
- A built-in web view to display web content
- GPU acceleration for smooth rendering
- Accessibility and internationalization support out of the box

> [!tip] Most GUI frameworks (like Electron, Flutter, Qt, and .NET MAUI) share similar concepts: components, layout management, event handling, and styling. Learning JavaFX will make it easier to pick up others later.


# The Core Structure: Stage, Scene, and Nodes

This is the most important concept to understand. A JavaFX application is built like a **theatre**:

|Concept|Analogy|What it is|
|---|---|---|
|**Stage**|The theatre stage|The root window of the app|
|**Scene**|The set/scenery|The content displayed inside the Stage|
|**Node**|Actors and props|Individual UI elements (buttons, shapes, etc.)|

## The Application Class

Every JavaFX app starts with a class that:

1. **Extends** `Application`
2. **Overrides** the `start(Stage stage)` method
3. Has a `main` method that calls `Application.launch()`

```java
public class MyApp extends Application {

    @Override
    public void start(Stage stage) {
        // Build your UI here
    }

    public static void main(String[] args) {
        launch(args); // This starts the JavaFX platform and calls start()
    }
}
```

> [!note] When JavaFX is ready, it automatically calls your `start()` method and passes a `Stage` object to it. You don't create the Stage yourself.

## The Stage

The **Stage** is the main window. It has:

- A title bar
- A border
- Close, minimize, and maximize buttons

The operating system controls what these decorations look like. The Stage holds a **Scene** inside it.

## The Scene and Scene Graph

The **Scene** holds the actual content of your window. It is organized as a **scene graph** — a tree structure made up of **Node** objects.

- The **root node** has no parent.
- Every other node has exactly **one parent**.
- A node can have **zero or more children**.

> [!note] Think of the scene graph like an upside-down family tree. The root is at the top, and it branches down into all the UI elements of your app.

# The Node Hierarchy

Everything you see in a JavaFX app is a `Node`. Nodes are organized into a class hierarchy:

```
Node (abstract)
├── Parent (abstract) — can have children
│   ├── Group — purely logical grouping
│   └── Region — has visual styling (CSS)
│       ├── Control (abstract) — interactive UI elements
│       │   ├── Button, CheckBox, Label, TextField, ...
│       └── Pane — manages layout of children
│           ├── VBox, HBox, StackPane, GridPane, ...
└── Shape (abstract) — drawable shapes
    ├── Arc, Ellipse, Line, Rectangle, Text, ...
```

|Node Type|Purpose|
|---|---|
|**Group**|Logically group nodes; no visual representation|
|**Region**|Has a visual appearance; can be styled with CSS|
|**Control**|Interactive element (button, checkbox, etc.)|
|**Pane**|Manages how children are laid out|
|**Shape**|Draw shapes or text on screen|

> [!important] **Regions** have visual styling and spacing concerns. **Groups** are purely logical — they let you manipulate multiple nodes together as a unit, but have no appearance of their own.

# Your First JavaFX App

Here is the simplest possible JavaFX app:

```java
public class HelloApp extends Application {

    @Override
    public void start(Stage stage) {
        var container = new HBox();
        container.getChildren().add(new Label("Hello World!"));

        stage.setScene(new Scene(container, 300, 250));
        stage.setTitle("Hello World!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

The scene graph for this app looks like:

```
Stage
└── Scene
    └── HBox (root node)
        └── Label ("Hello World!")
```

# Coordinates in JavaFX

> [!note] In JavaFX (and most GUI libraries), the **top-left corner is (0, 0)**. The x-axis goes right and the y-axis goes **down**.

This is different from what you might expect from math class, where y typically goes up. Keep this in mind when positioning shapes or elements manually.

# Layout Panes

**Panes** are special classes that arrange their child nodes in different ways. You choose a pane based on how you want your UI to be organized.

|Pane|Behaviour|
|---|---|
|**VBox**|Stacks children vertically|
|**HBox**|Stacks children horizontally|
|**StackPane**|Layers children on top of each other|
|**TilePane**|Arranges children in equal-sized tiles|
|**AnchorPane**|Anchors children to specific edges|
|**BorderPane**|Has top, bottom, left, right, and center regions|
|**FlowPane**|Wraps children like words in a paragraph|
|**GridPane**|Arranges children in a flexible grid|

## Adding Nodes to a Pane

```java
// Using the constructor
var pane = new VBox(child1, child2);

// Using add (one at a time)
pane.getChildren().add(child3);

// Using addAll (multiple at once)
pane.getChildren().addAll(child4, child5);
```

## Region Padding

All `Region` subclasses (including all Panes) support `setPadding()` to add space between the boundary of the region and its children:

```java
// Set all sides to 10px
vbox.setPadding(new Insets(10));

// Set top=10, right=50, bottom=50, left=50
vbox.setPadding(new Insets(10, 50, 50, 50));
```


# Shapes and Text

The `javafx.scene.shape` package contains many ready-to-use shape classes, all extending the abstract `Shape` class: `Arc`, `Ellipse`, `Line`, `Polygon`, `Polyline`, `Rectangle`, and more.

**Text** is also a `Shape` — it lives in `javafx.scene.text.Text`:

```java
Text text = new Text();
text.setFont(new Font("Arial", 20));
text.setWrappingWidth(200);
text.setTextAlignment(TextAlignment.JUSTIFY);
text.setText("The quick brown fox jumps over the lazy dog");
```

> [!example] A more advanced example using shapes, StackPane, and text:
> 
> ```java
> var ellipse = new Ellipse(110, 70);
> ellipse.setFill(Color.BLACK);
> ellipse.setStroke(Color.HOTPINK);
> ellipse.setStrokeWidth(2);
> 
> var text = new Text("I love UI");
> text.setFill(Color.WHITE);
> text.setFont(new Font("Arial Bold", 24));
> 
> var stackPane = new StackPane();
> stackPane.getChildren().addAll(ellipse, text);
> 
> var scene = new Scene(stackPane, 350, 230, Color.rgb(52, 5, 70));
> stage.setScene(scene);
> stage.show();
> ```
> 
> This renders a hot-pink ellipse with white text centered on top of it — the StackPane layers the two nodes together.


# Colors and Paint

## Color

The `Color` class lets you specify colors in multiple ways:

```java
Color.LIGHTBLUE;           // Named color
Color.rgb(52, 5, 70);      // RGB values (0–255)
Color.web("#340546");      // Hex code
Color.gray(0.5);           // Grayscale (0.0–1.0)
```

## Paint

`Color` is a subtype of the abstract `Paint` class. Other `Paint` types include:

- `ImagePattern` — use an image as a background fill
- `LinearGradient` — a gradient that transitions in a straight line
- `RadialGradient` — a gradient that radiates outward from a center point

# Effects

JavaFX has built-in visual effects you can apply to **any Node**:

```java
Reflection reflection = new Reflection();
reflection.setFraction(0.2);
reflection.setTopOffset(1.0);
ellipse.setEffect(reflection);

text.setEffect(new DropShadow(5, 0, 0, Color.WHITE));
```

Available effects include: `Bloom`, `DropShadow`, `GaussianBlur`, `MotionBlur`, `Reflection`, and more.

# Animation with Transitions

Many Node properties can be animated using **transition classes**:

|Transition Class|Animates|
|---|---|
|`FadeTransition`|Opacity|
|`FillTransition`|Fill color/gradient|
|`PathTransition`|Position along a path|
|`RotateTransition`|Rotation angle|
|`ScaleTransition`|Size (scales content too)|
|`StrokeTransition`|Outline color/style|
|`TranslateTransition`|Position (x, y)|

## Common Animation Methods

|Method|Description|
|---|---|
|`get/setCycleCount`|How many times to repeat|
|`get/setDelay`|Delay before starting|
|`getStatus`|PAUSED, RUNNING, or STOPPED|
|`get/setAutoReverse`|Reverse when complete|
|`setOnFinished`|Handler called when animation ends|
|`play/pause/stop`|Control playback|

> [!example] Animate a node to scale up to 120% of its size and bounce back:
> 
> ```java
> ScaleTransition t = new ScaleTransition(Duration.seconds(0.1), node);
> t.setAutoReverse(true);
> t.setCycleCount(2); // Forward + back = 2 cycles
> t.setToX(1.2);
> t.setToY(1.2);
> t.playFromStart();
> ```

# Responding to Events

To react to user actions, you use **EventHandler** objects. `EventHandler<T>` is a generic interface with a single method: `handle(T event)`.

```java
public class ClickHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        System.out.println("Clicked with button: " + event.getButton());
    }
}

// Attach the handler to a button
Button button = new Button("Click me!");
button.setOnMouseClicked(new ClickHandler());
```

> [!note] The `Event` object passed to `handle()` carries information about the event. For example, `MouseEvent` tells you which mouse button was clicked and where on the screen the click happened.
> 
> `EventHandler` is **generic** — `EventHandler<MouseEvent>` means "a handler _of_ mouse events".

> [!warning] Do not confuse the **event type** (e.g. `MouseEvent`) with the **handler** (e.g. `EventHandler<MouseEvent>`). The handler is what you write; the event type is what gets passed into it.


# Controls

**Controls** are interactive UI elements the user can interact with — like buttons, checkboxes, sliders, and text fields. They all extend `javafx.scene.controls.Control`, so they:

- Can be styled with CSS
- Can have effects applied
- Can fire events

## Three Categories of Basic Controls

**Labelled** — have an icon and/or text label:

- `Button`, `CheckBox`, `Hyperlink`, `Label`, `RadioButton`, `ToggleButton`

**Text input** — allow the user to type:

- `TextField`, `TextArea`, `PasswordField`
- Support selection, caret positioning, undo/redo, copy/paste, and formatting

**Other** — specialized controls:

- `ProgressBar`, `ProgressIndicator`, `Slider`



> [!tip] For hands-on code examples for layouts and controls, check out the open-source sample repository used in the slides:
> 
> - Layouts: https://github.com/Apress/learnjavafx17/tree/main/src/com/jdojo/container
> - Controls: https://github.com/Apress/learnjavafx17/tree/main/src/com/jdojo/control
> 
> More JavaFX learning resources: https://www.geeksforgeeks.org/javafx-tutorial/


## Tags

#computer_science #java #javafx #gui #programming #software_engineering #study_notes #ui_development