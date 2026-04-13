
> [!important] What is on the test? The test covers **multiple choice and written questions** on:
> 
> - [[Microsoft Access]] — Objects, Events, Expressions, Macros, VBA
> - [[SQL Server]] — Setup, Troubleshooting, Security, Permissions
> - [[PHP]] — IIS Setup, PHP Drivers, ODBC
> - [[Power BI]] — Connecting to data, building charts

# Part 1 — Microsoft Access

[[Microsoft Access]] is a desktop **Relational Database Management System (RDBMS)** from Microsoft. It lets you store, manage, and display data using a set of built-in **objects**.

## The Core Access Objects

Access is built around several object types. You need to know all of them:

|Object|What it does|
|---|---|
|**Tables**|Store the raw data (like a spreadsheet with rows and columns)|
|**Queries**|Ask questions of your data — filter, sort, calculate|
|**Forms**|User-friendly screens for viewing and entering data|
|**Reports**|Formatted printable summaries of your data|
|**Macros**|Recorded sequences of actions — no coding needed|
|**Modules**|VBA code written by a developer|

> [!note] The order to remember: 
> **Tables → Queries → Forms → Reports** Data lives in tables. Queries pull from tables. Forms and Reports display the results. Everything starts with Tables.

## Form Controls

When you build a **Form** in Access, you place **controls** on it. The two most common types you will see on the test:

- **`txtLastName`** — a **Text Box**. The `txt` prefix tells you it is a text box. It displays and accepts text input. The `LastName` part tells you what data it is bound to.
- **`lblLastName`** — a **Label**. The `lbl` prefix tells you it is a label. It just displays static text (like a title or caption) — users cannot type into it.

> [!tip] Naming conventions 
> In Access Access developers use **prefixes** in control names to quickly identify the control type:
> 
> - `txt` = Text Box
> - `lbl` = Label
> - `btn` or `cmd` = Button
> - `chk` = Check Box
> - `cmb` = Combo Box
> 
> You might see questions asking you to identify a control type just from its name.

## Events in Access

An **Event** is something that happens a user clicks, types, moves focus, etc. Access lets you attach code or macros to events so something happens in response.

Common events you need to know:

|Event|When it fires|
|---|---|
|**Click**|User clicks the control (button, label, etc.)|
|**GotFocus**|Control becomes active (cursor moves into it)|
|**LostFocus**|Control loses focus (cursor moves away)|
|**AfterUpdate**|After the user changes the value in a field|
|**BeforeUpdate**|Just before Access saves a changed value|
|**Load**|When the form first opens|
|**Open**|As the form is opening (before Load)|

> [!example] Practical example
> You have a text box `txtLastName`. You want to validate it when the user leaves the field. You would attach code to the **LostFocus** (or **AfterUpdate**) event of `txtLastName`. The code runs automatically whenever the user tabs away.

## Expressions in Access

An **Expression** in Access is a formula that calculates or evaluates something. You use them in:

- Query criteria (filter rows)
- Calculated fields in queries
- Control sources in forms/reports
- Validation rules

Expressions always start with `=` when used in a control source.

> [!example] Simple expressions
> 
> - `=[Price] * [Quantity]` — multiplies two fields together
> - `=Date()` — shows today's date
> - `=Left([LastName], 1)` — shows just the first letter of the last name
> - `=Nz([Bonus], 0)` — shows 0 instead of Null if Bonus is empty

## Macros in Access

A **Macro** is a saved sequence of actions that Access can replay. Think of it like a to-do list for Access: open this form, then filter these records, then show a message.

- No programming knowledge is required
- You build macros using a visual interface — just pick actions from a list
- Common macro actions: `OpenForm`, `OpenReport`, `GoToRecord`, `MsgBox`, `SetValue`, `CloseWindow`

> [!note] Macro vs VBA
> 
> - **Macro** = simple, visual, no code, good for basic automation
> - **VBA** = full programming language, more powerful and flexible, required for complex logic

## VBA in Access (Visual Basic for Applications)

**VBA** is the programming language built into Access (and all Microsoft Office apps). You write VBA in **Modules**, or directly in the **event procedures** of form controls.

### Key VBA Concepts

VBA code runs inside **procedures** (blocks of code). The most common is a **Sub**:

```vb
Private Sub txtLastName_LostFocus()
    If Len(txtLastName) = 0 Then
        MsgBox "Last name cannot be empty!"
    End If
End Sub
```

This code fires when `txtLastName` loses focus, checks if it is empty, and shows a message box.

## VBA Functions Reference
### String Functions

|Function|What it does|Example|
|---|---|---|
|`Len(str)`|Length of a string|`Len("Hello")` → 5|
|`Left(str, n)`|First n characters|`Left("Hello", 3)` → `"Hel"`|
|`Right(str, n)`|Last n characters|`Right("Hello", 3)` → `"llo"`|
|`Mid(str, start, n)`|n characters from position|`Mid("Hello", 2, 3)` → `"ell"`|
|`UCase(str)`|Convert to UPPERCASE|`UCase("hello")` → `"HELLO"`|
|`LCase(str)`|Convert to lowercase|`LCase("HELLO")` → `"hello"`|
|`Trim(str)`|Remove leading/trailing spaces|`Trim(" Hi ")` → `"Hi"`|
|`InStr(str, search)`|Position of substring|`InStr("Hello", "ll")` → 3|

### Math Functions

|Function|What it does|Example|
|---|---|---|
|`Abs(n)`|Absolute value (removes negative sign)|`Abs(-5)` → 5|
|`Int(n)`|Rounds down to whole number|`Int(4.9)` → 4|
|`Round(n, decimals)`|Rounds to decimal places|`Round(3.567, 2)` → 3.57|
|`Sqr(n)`|Square root|`Sqr(16)` → 4|
|`Mod`|Remainder of division|`10 Mod 3` → 1|

### Date and Time Functions

|Function|What it does|Example|
|---|---|---|
|`Now()`|Current date AND time|`Now()` → `4/12/2026 10:30 AM`|
|`Date()`|Current date only|`Date()` → `4/12/2026`|
|`Time()`|Current time only|`Time()` → `10:30 AM`|
|`Year(date)`|Extracts just the year|`Year(Now())` → 2026|
|`Month(date)`|Extracts just the month|`Month(Now())` → 4|
|`Day(date)`|Extracts just the day|`Day(Now())` → 12|
|`DateDiff(interval, d1, d2)`|Difference between two dates|`DateDiff("d", #1/1/2026#, #4/12/2026#)` → 101|

### Conversion and Utility Functions

|Function|What it does|Example|
|---|---|---|
|`CInt(val)`|Converts to Integer|`CInt("42")` → 42|
|`CDbl(val)`|Converts to Double (decimal number)|`CDbl("3.14")` → 3.14|
|`CStr(val)`|Converts to String (text)|`CStr(100)` → `"100"`|
|`CDate(val)`|Converts to Date|`CDate("12/25/2026")`|
|`IsNull(val)`|Checks if a value is Null|`IsNull(Null)` → True|
|`IsNumeric(val)`|Checks if a value is a number|`IsNumeric("123")` → True|
|`Nz(val, replacement)`|Replaces Null with something else|`Nz(Null, 0)` → 0|

> [!important] 
> Why `Nz()` matters in Access Database fields often contain **Null** values (empty, no data entered). If you try to calculate with a Null, you get an error or unexpected result. `Nz()` replaces Null with a safe default (like 0) so your calculation does not break. Example: `Nz([Bonus], 0)` — if Bonus is Null, treat it as 0.

### Message and Input Functions

| Function             | What it does                                         |
| -------------------- | ---------------------------------------------------- |
| `MsgBox "text"`      | Displays a popup message to the user                 |
| `InputBox("prompt")` | Opens a dialog box asking the user to type something |

> [!example] Using MsgBox and InputBox together
> 
> ```vb
> Dim userName As String
> userName = InputBox("What is your name?")
> MsgBox "Hello, " & userName & "!"
> ```
> 
> This asks the user to type their name, then greets them.

# Part 2 — SQL Server

[[SQL Server]] is Microsoft's enterprise-level **Relational Database Management System**. Unlike Access, it is designed for multiple users, large datasets, and server environments.

### Key SQL Server Concepts

|Term|What it means|
|---|---|
|**Database**|A container holding all your tables, views, and objects|
|**Table**|Stores data in rows (records) and columns (fields)|
|**Primary Key**|Uniquely identifies each row in a table|
|**Foreign Key**|Links a column in one table back to the primary key of another|
|**SSMS**|SQL Server Management Studio — the GUI tool to manage SQL Server|

## Troubleshooting SQL Server

> [!important] The Troubleshooting Hierarchy
> When something does not work in SQL Server, always check in this order:
> 
> **1. Security: Login** → Does the user account exist and can it log in? **2. Database: Properties - Permissions** → Does that login have access to the database? **3. Table: Permissions (SELECT, INSERT, UPDATE, DELETE)** → Does the user have permission to perform the specific action on the specific table?

This is exactly the order problems cascade. A user might log in fine (Layer 1 is OK) but still get an error because they have no permission on the table (Layer 3 fails).

## Reading a SQL Error

The test may show you a broken query and ask you to identify the problem.

```sql
Select * From Supervisor
```

> [!warning] This query has an error! 
> The most likely issues:
> 
> - The table is named `Supervisors` (plural) but the query says `Supervisor` — **table name typo**
> - The user running the query does not have **SELECT** permission on that table
> - The user is connected to the wrong **database** (e.g., connected to `master` instead of `Retail`)
> 
> Always check: spelling of the table name, which database you are connected to, and the user's permissions.

### How to Grant Table Permissions (T-SQL)

```sql
USE Retail;

GRANT SELECT, INSERT, UPDATE, DELETE
ON dbo.customers
TO csa103;
```

This gives the `csa103` user full read/write access to the customers table in the Retail database.

### SQL Server Security

**Layer 1 — Login (Server Level)**

- A **Login** is an account that can connect to the SQL Server instance itself.
- Created in SSMS under Security > Logins.
- Without a valid login, the user cannot even reach the server.

**Layer 2 — Database Access (Database Level)**

- Even with a login, the user must be mapped to a specific database as a **User**.
- Done through Database Properties > Permissions or via Security > Users inside the database.
- Without this, the login exists but cannot open any database.

**Layer 3 — Table Permissions (Object Level)**

- Even with database access, a user needs explicit permission on each table.
- Permissions include: `SELECT` (read), `INSERT` (add), `UPDATE` (edit), `DELETE` (remove).
- Without this, the user can connect to the database but cannot do anything with the tables.

> [!tip] 
> Think of it like a building Layer 1 = you have a key card to enter the building Layer 2 = you are authorized to be on a specific floor Layer 3 = you have permission to open specific rooms on that floor

# Part 3 — PHP

[[PHP]] is a server-side scripting language. In this course, it is used to build web pages that connect to [[SQL Server]] and display or modify database records.

## The Full Stack

> [!note] The Big Picture 
> **Browser** → **IIS Web Server** (with PHP enabled via CGI/FastCGI) → **ODBC Driver** → **SQL Server (Retail DB)**
> 
> Every piece must be installed and configured correctly. If one step is wrong, nothing works downstream.

## Step 1 — PHP for Windows (Preparing IIS for PHP)

[[IIS Web Server]] (Internet Information Services) is Microsoft's built-in web server for Windows. By default it cannot run PHP — you have to set it up manually.

**Key actions:**

1. Install IIS via **Server Manager** → Add Roles and Features
2. Under **Application Development**, enable **CGI** — this is what allows PHP to run
3. Create the folder `C:\php`
4. Download **PHP 8.3.30 Non-Thread Safe 64-bit** (zip) from windows.php.net
5. Extract it into `C:\php`
6. Copy `php.ini-production` → rename it to `php.ini`
7. In `php.ini`, uncomment `extension_dir = 'ext'` (around line 780)
8. In IIS Manager, add a **Handler Mapping** for `*.php` using `FastCgiModule` pointing to `C:\php\php-cgi.exe`
9. Add `C:\php` to the Windows **Environment Variables Path**

> [!warning] Common mistakes
> 
> - Downloading PHP 8.4 instead of 8.3 — stick to **8.3.30**
> - Forgetting to rename `php.ini-production` to exactly `php.ini`
> - File extensions are hidden in Windows so you cannot verify the name — always enable "Show File Extensions"
> - Forgetting to restart the server after installing IIS

## Step 2 — PHP Drivers for SQL Server

PHP cannot talk to SQL Server without a special **driver** from Microsoft.

**Key actions:**

1. Download the **Microsoft PHP Drivers for SQL Server** from Microsoft Learn
2. Extract to `C:\php\drivers`
3. Copy the file `php_sqlsrv_83_nts_x64.dll` into `C:\php\ext`
4. In `php.ini` (around line 920), add the line:

```ini
extension=c:\php\drivers\php_sqlsrv_83_nts_x64.dll
```

> [!note] Understanding the filename `php_sqlsrv_83_nts_x64.dll`
> 
> - `php_sqlsrv` → SQL Server driver for PHP
> - `83` → for PHP version 8.3
> - `nts` → Non-Thread Safe (must match your PHP download)
> - `x64` → 64-bit
> - `.dll` → Windows library file


## Step 3 — ODBC Drivers

[[ODBC Drivers]] (Open Database Connectivity) act as a bridge between your PHP code and SQL Server. Instead of hardcoding the full connection details everywhere, you create a named **DSN (Data Source Name)** that PHP refers to.

**Key actions:**

1. Open **ODBC Data Source Administrator (64-bit)** from Windows Administrative Tools
2. Go to the **System DSN** tab
3. Click **Add** → select **SQL Server**
4. Fill in:
    - Name: `CSA103-Retail`
    - Server: `HIN-SERVER` (type it manually — do not use the dropdown)
    - Authentication: SQL Server authentication with `csa103` / `@Saultcollege443`
    - Default database: `Retail`
5. Click **Test Data Source**

> [!important] A passing ODBC test looks like this:
> 
> ```
> Attempting connection
> Connection established
> Verifying option settings
> Disconnecting from server
> TESTS COMPLETED SUCCESSFULLY!
> ```
> 
> If the test fails, your PHP pages will not work. Fix the connection before moving on.

## PHP Connection String (What it Looks Like in Code)

```php
<?php
$serverName = "localhost";
$connectionOptions = array(
    "Database" => "Retail",      // your database name
    "Uid" => "csa103",           // your SQL Server login
    "PWD" => "@Saultcollege443"  // your password
);
$conn = sqlsrv_connect($serverName, $connectionOptions);
?>
```

> [!tip] 
> When adapting PHP files (like in Lab 9c) The three things you always need to change when adapting a PHP file to your setup:
> 
> 1. **Database name** — must match your database exactly
> 2. **User account (Uid)** — your SQL Server login
> 3. **Password (PWD)** — matching credentials Also update any **field names** in SQL queries to match your actual table columns.

#### SQL Commands Inside PHP

```php
// INSERT — add a new record
$sql = "INSERT INTO customers (customer_id, customer_first_name, customer_last_name)
        VALUES (?, ?, ?)";

// DELETE — remove a record
$sql = "DELETE FROM customers WHERE customer_id = ?";
```

# Part 4 — Power BI

[[Power BI]] is Microsoft's **data visualization** tool. It connects to data sources (like [[SQL Server]]) and lets you build interactive charts, graphs, and dashboards — no coding needed.

## How to Build a Chart in Power BI — The Three Steps

> [!important] The Power BI Workflow (this is likely on the test)
> 
> 1. **Click and drag** a field from the Data panel onto the canvas
> 2. **Select a title** (or drag a second field — like `hospital_name` — to define the category axis)
> 3. **Select your chart type** from the Visualizations panel (pie, bar, line, etc.)
> 
> Power BI automatically generates the chart. You can resize and reposition it on the canvas.

## Connecting Power BI to SQL Server

1. Open **Power BI Desktop**
2. Choose **New Report**
3. Select **Import Data from SQL Server**
4. Enter:
    - Server: `HIN-SERVER`
    - Database: `health`
5. Select **Import**, click **OK**
6. Check both tables (`hospitals` and `patients`) and click **Load**

After loading, all fields from both tables appear in the **Data panel** on the right.

## The Four Charts You Built in Lab 10a

|Chart|Fields Used|Chart Type|
|---|---|---|
|Area Population by Hospital|`area_population` + `hospital_name`|Pie Chart|
|Employees per Hospital|`number_of_employees` + `hospital_name`|Bar/Line Chart|
|Annual Budget by Hospital|`annual_budget` + `hospital_name`|Bar Chart|
|Number of Beds by Hospital|`Number_of_beds` + `hospital_name`|Line Chart|

> [!example] How a chart gets built (step by step)
> 
> 1. In the Data panel, find `area_population` under the hospitals table
> 2. Drag it onto the blank canvas — a default visual appears
> 3. Drag `hospital_name` onto the same visual box
> 4. In the Visualizations panel, click **Pie Chart**
> 5. A labeled pie chart titled "Sum of area_population by hospital_name" appears

## Key Power BI Terms

|Term|What it means|
|---|---|
|**Canvas**|The blank workspace where you place charts|
|**Visualizations panel**|Where you pick the chart type (bar, pie, line, etc.)|
|**Data panel**|Lists all fields available from your loaded tables|
|**Import Mode**|Data is loaded as a snapshot from SQL Server into Power BI|
|**.pbix**|The file format for saving a Power BI report|

> [!important] 
> File naming for submission Save your Power BI file as: `Health-Demo.XX.pbix` Replace **XX** with your first and last initials (e.g., `Health-Demo.JS.pbix` for Jane Smith).

# Other Resources

- [SQL Server Management Studio Docs](https://learn.microsoft.com/en-us/sql/ssms/sql-server-management-studio-ssms)
- [SQL GRANT Permissions](https://docs.microsoft.com/en-us/sql/t-sql/statements/grant-object-permissions-transact-sql)
- [PHP for Windows Downloads](https://windows.php.net/download/)
- [Microsoft PHP Drivers for SQL Server](https://learn.microsoft.com/en-us/sql/connect/php/download-drivers-php-sql-server)
- [PHP Official Documentation](https://www.php.net/docs.php)
- [W3Schools SQL Tutorial](https://www.w3schools.com/sql/)
- [W3Schools PHP Tutorial](https://www.w3schools.com/php/)
- [Power BI Documentation](https://powerbi.microsoft.com/en-us/documentation)
- [GeeksForGeeks — What is ODBC?](https://www.geeksforgeeks.org/odbc-full-form/)

# Tags

#business_applications #microsoft_access #SQL_server #PHP #IIS #ODBC #power_bi #databases #VBA #macros #expressions #data_visualization #web_development #study_notes #test_review #CSA103