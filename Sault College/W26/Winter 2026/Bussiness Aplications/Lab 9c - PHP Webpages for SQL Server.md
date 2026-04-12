
> [!note] What is this lab about? In this lab, you are building on what you did in Lab 9b. You will take two pre-made [[PHP]] files (one for adding records, one for deleting records) and adapt them to work with your own [[SQL Server]] database and your **customers table**. Once they are working, you take screenshots and submit.

## What You Need Before Starting

> [!warning] Prerequisite You **must** have completed **Lab 9b** before starting this one. Lab 9c builds directly on the setup you did there (database, user account, web server, etc.).

You also need to:

- Start your environment at **labs.azure.com** and log in
- Be inside your **Windows Server** session

---

## Step-by-Step Walkthrough

### Step 1 - Download the Starter PHP Files

Inside your Windows Server, open the web browser and go to **welearn.saultcollege.ca**, then find the **Resources** section for this course.

Download these two files:

- `add-supervisors.php`
- `delete-supervisors.php`

> [!note] Why "supervisors"? These files were originally built for a supervisors table. Your job is to **rename the logic inside them** so they work with your **customers** table instead. The file names themselves do not need to change, but the code inside must match your setup.

---

### Step 2 - Modify the PHP Files

This is the core coding task. Open each file and update the following three things:

|What to Change|Why|
|---|---|
|**Database name**|So PHP connects to your specific database|
|**User account**|The login [[SQL Server]] uses to authenticate|
|**Password**|Matches the credentials for your user account|
|**Field names**|Replace supervisor field names with your customers table field names|
|**Titles/labels**|Update any display text to say "Customer" instead of "Supervisor"|

> [!tip] How to approach this Open the file in a text editor and look for the connection string near the top - that is where the database name, username, and password live. Then scan for any field names like `supervisor_id`, `supervisor_name`, etc., and swap them with your actual customers table column names (for example: `customer_id`, `first_name`, `email`, etc.).

#### Example of what a PHP SQL Server connection string looks like:

```php
<?php
$serverName = "localhost";
$connectionOptions = array(
    "Database" => "YourDatabaseName",   // <-- change this
    "Uid" => "csa103",                  // <-- your user account
    "PWD" => "yourpassword"             // <-- your password
);
$conn = sqlsrv_connect($serverName, $connectionOptions);
?>
```

#### Example of an INSERT query you might adapt:

```php
$sql = "INSERT INTO customers (first_name, last_name, email) 
        VALUES (?, ?, ?)";
```

> [!important] Match your actual table columns If your customers table has different column names, you must use those exact names. SQL is case-sensitive with column names in some configurations, so double-check them in [[SQL Server]] first.

---

### Step 3 - Set Permissions on the Customers Table

Before your PHP pages can insert or delete data, the **csa103 user account** needs the right permissions on the customers table in [[SQL Server]].

Go into SQL Server and grant the following privileges to the `csa103` user on the customers table:

- `SELECT` - read data
- `INSERT` - add new records
- `UPDATE` - modify existing records
- `DELETE` - remove records

> [!example] How to grant permissions in SQL Server (T-SQL) You can run this in a query window in SQL Server Management Studio:
> 
> ```sql
> USE YourDatabaseName;
> 
> GRANT SELECT, INSERT, UPDATE, DELETE 
> ON dbo.customers 
> TO csa103;
> ```
> 
> Replace `YourDatabaseName` with your actual database name.

> [!warning] Common mistake If you forget to grant DELETE or INSERT, your PHP pages will return a permissions error even if the connection itself works fine. Always verify all four privileges are set.

---

### Step 4 - Test the PHP Files

Open your browser (still inside the Windows Server) and navigate to your PHP files through the local web server (likely something like `http://localhost/add-supervisors.php`).

Test both pages:

- **Add page** - fill in the form and submit. Then check the customers table in SQL Server to confirm the new row appeared.
- **Delete page** - enter a customer ID (or however deletion is triggered) and confirm the record is removed from the table.

> [!tip] Troubleshooting If the page shows a blank screen or a PHP error, the most common causes are:
> 
> - Wrong database name, username, or password in the connection string
> - Missing permissions on the table (Step 3)
> - A typo in a column name inside the SQL query
> - The web server ([[IIS Web Server]] or Apache) is not running

---

### Step 5 - Take Screenshots and Submit

Once both PHP pages are working correctly:

1. Take screenshots showing:
    
    - The **Add Customer** form and a successful submission
    - The **Delete Customer** form and a successful deletion
    - Optionally: the updated customers table in SQL Server to confirm changes
2. Upload the screenshots into **welearn.saultcollege.ca**
    
3. Submit to the **DropBox** on welearn
    

> [!important] Submission deadline Due: **Thursday, April 2nd**. Screenshots must be uploaded to the LMS DropBox.

---

## Key Concepts Summary

|Term|What it means|
|---|---|
|**PHP**|Server-side scripting language used to create dynamic web pages that interact with databases|
|**SQL Server**|Microsoft's relational database system where your data lives|
|**`sqlsrv_connect()`**|The PHP function used to connect to a SQL Server database|
|**INSERT**|SQL command to add a new row to a table|
|**DELETE**|SQL command to remove a row from a table|
|**Privileges/Permissions**|Rules that control what a database user is allowed to do|

---

## Helpful Resources

- [PHP sqlsrv functions - Microsoft Docs](https://learn.microsoft.com/en-us/sql/connect/php/php-driver-for-sql-server-support-matrix)
- [SQL INSERT statement - W3Schools](https://www.w3schools.com/sql/sql_insert.asp)
- [SQL DELETE statement - W3Schools](https://www.w3schools.com/sql/sql_delete.asp)
- [GRANT permissions in SQL Server - Microsoft Docs](https://docs.microsoft.com/en-us/sql/t-sql/statements/grant-object-permissions-transact-sql)
- [PHP official documentation](https://www.php.net/docs.php)

---

## Related Notes

[[PHP]] | [[SQL Server]] | [[Databases]] | [[IIS Web Server]] | [[ODBC Drivers]] | [[Web Development]]

---

## Tags

#business_applications #PHP #SQL_server #databases #SQL #web_development #add_records #delete_records #study_notes #csa103