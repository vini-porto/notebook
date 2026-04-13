Multiple choice / Written text about:
- Microsoft Access
- SQL Server
- Power Bi
# Microsoft Access

Referential Intefrity
Objects - txtLastName / iblLastName
Events - Last Focus, Click, etc
1. Expressions
2. Macro
3. VBA

- Database
- Tables
- Forms > Reports > Queries

# SQL Server

## Troubleshooting

```SQL
Select * From Supervisor
```

Error Line!!! 

1. Security: Login
2. Database: Properties - Permissions
3. Tables: Permissions (Select, Insert, Update, Delete)

## PHP
1. PHP For Windows (Prepare the IIS Web Server for PHP)
2. PHP Daluas for SQL Server
3. ODBC Drivers (Connection Tested)


# Power Bi

1. Click + Drag onto the screen
2. Select a title "customer"
3. Select yout chart type

Here are some useful basic VBA functions in Microsoft Access:

---

### 📝 String Functions

|Function|Description|Example|
|---|---|---|
|`Len(str)`|Returns length of a string|`Len("Hello")` → 5|
|`Left(str, n)`|Returns n characters from the left|`Left("Hello", 3)` → "Hel"|
|`Right(str, n)`|Returns n characters from the right|`Right("Hello", 3)` → "llo"|
|`Mid(str, start, n)`|Returns n characters from a position|`Mid("Hello", 2, 3)` → "ell"|
|`UCase(str)`|Converts to uppercase|`UCase("hello")` → "HELLO"|
|`LCase(str)`|Converts to lowercase|`LCase("HELLO")` → "hello"|
|`Trim(str)`|Removes leading/trailing spaces|`Trim(" Hi ")` → "Hi"|
|`InStr(str, search)`|Finds position of a substring|`InStr("Hello", "ll")` → 3|

---

### 🔢 Math Functions

|Function|Description|Example|
|---|---|---|
|`Abs(n)`|Absolute value|`Abs(-5)` → 5|
|`Int(n)`|Rounds down to whole number|`Int(4.9)` → 4|
|`Round(n, decimals)`|Rounds to decimal places|`Round(3.567, 2)` → 3.57|
|`Sqr(n)`|Square root|`Sqr(16)` → 4|
|`Mod`|Remainder of division|`10 Mod 3` → 1|

---

### 📅 Date & Time Functions

|Function|Description|Example|
|---|---|---|
|`Now()`|Current date and time|`Now()` → 4/12/2026 10:30 AM|
|`Date()`|Current date only|`Date()` → 4/12/2026|
|`Time()`|Current time only|`Time()` → 10:30 AM|
|`Year(date)`|Extracts the year|`Year(Now())` → 2026|
|`Month(date)`|Extracts the month|`Month(Now())` → 4|
|`Day(date)`|Extracts the day|`Day(Now())` → 12|
|`DateDiff(interval, d1, d2)`|Difference between dates|`DateDiff("d", #1/1/2026#, #4/12/2026#)` → 101|

---

### ✅ Conversion Functions

|Function|Description|Example|
|---|---|---|
|`CInt(val)`|Converts to Integer|`CInt("42")` → 42|
|`CDbl(val)`|Converts to Double (decimal)|`CDbl("3.14")` → 3.14|
|`CStr(val)`|Converts to String|`CStr(100)` → "100"|
|`CDate(val)`|Converts to Date|`CDate("12/25/2026")`|
|`IsNull(val)`|Checks if value is Null|`IsNull(Null)` → True|
|`IsNumeric(val)`|Checks if value is a number|`IsNumeric("123")` → True|
|`Nz(val, replacement)`|Replaces Null with a value|`Nz(Null, 0)` → 0|

---

### 💬 Message & Input

|Function|Description|
|---|---|
|`MsgBox "text"`|Displays a message popup|
|`InputBox("prompt")`|Asks user to type input|

---

`Nz()` is especially handy in Access since database fields often contain Null values, which can cause errors in calculations if not handled.