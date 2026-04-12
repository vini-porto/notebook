
This lab walks you through setting up a cloud-based [[SQL Server]] environment using [[Microsoft Azure]] Labs, creating a database, building tables with proper data types, populating them with records, and linking them together using a Database Diagram. This is your first hands-on experience with a real enterprise database system.

## Setting Up Your Azure Lab Environment

Before touching any SQL, you need to get your Windows Server running in the cloud.

> [!note] What is Azure Labs? 
> **Azure Labs** (labs.azure.com) is Microsoft's cloud-based virtual lab platform. Your college provides you with a pre-configured **Windows Server** virtual machine that already has SQL Server installed. You access it entirely through your browser — no local installation needed.

**Steps to get connected:**

1. Check your **college email** for a registration invite from Microsoft
2. Go to **labs.azure.com** and sign in
3. Find your lab and click the **Slider** to start the virtual machine
4. Wait **3 to 4 minutes** for the server to fully boot
5. Once the slider turns **blue**, click the **TV screen icon** to open a remote desktop session
6. Accept the **yellow certificate** warning when prompted
7. Enter the password (get this from your professor — it is never posted online)
8. After logging in, wait **10–15 seconds** for background services to load

> [!warning] 
> Patience is key SQL Server Management Studio can take **1 minute or more** to fully open. Do not spam-click it — just wait. The server itself also needs a few minutes to boot before you can connect.

## Opening SQL Server Management Studio (SSMS)

**SQL Server Management Studio** (SSMS) is the graphical interface used to manage [[SQL Server]] databases. Think of it as the "control panel" for your database.

Once your desktop loads, look for the **SSMS icon** on the desktop and double-click it. When it opens, it will automatically connect to the local SQL Server instance running on your virtual machine.

> [!note] What is SQL Server Management Studio? SSMS is a free tool from Microsoft that lets you create databases, design tables, write SQL queries, and manage security — all through a visual interface. It is the standard tool used by database administrators and developers. Learn more: [https://learn.microsoft.com/en-us/sql/ssms/](https://learn.microsoft.com/en-us/sql/ssms/)

---

## Creating the "Retail" Database

Once SSMS is open, your first task is to create a new database.

**Steps:**

1. In the left panel (called the **Object Explorer**), find the **Databases** folder
2. Right-click on **Databases**
3. Select **New Database...**
4. Name it **Retail** (capital R — spelling and capitalization matter)
5. Click OK

> [!important] Naming matters Always name your database exactly as specified. SQL Server is case-aware in many contexts, and your professor and grading rubric will expect the name **Retail** with a capital R.

---

## Understanding the Tables You Will Create

Your **Retail** database will contain **4 tables** that represent a simple retail business system. Each table stores a different type of information.

> [!note] What is a Table? A table in a [[Databases|database]] is like a spreadsheet. It has **columns** (which define what kind of data is stored) and **rows** (which are the actual records/data entries). Every table needs at least one **Primary Key** — a column that uniquely identifies each row.

Here is a summary of all four tables:

|Table Name|Purpose|Primary Key|
|---|---|---|
|customers|Stores customer contact info|customer_id|
|sales|Records each sale transaction|sales_id|
|inventory|Tracks products available|product_id|
|clerks|Stores employee/clerk info|clerk_id|

---

### Table 1: customers

This table stores information about each customer who shops at the store.

|Column Name|Data Type|Allow Nulls|
|---|---|---|
|customer_phone_number|nchar(10)|No|
|customer_id|nchar(10)|Yes|
|customer_first_name|nchar(15)|Yes|
|customer_last_name|nchar(25)|No|
|customer_email|nchar(35)|Yes|

> [!important] Primary Key note Even though `customer_phone_number` appears first in the table, **make `customer_id` your primary key** — not the phone number. Phone numbers can change; a dedicated ID field is a more stable and reliable unique identifier.

> [!warning] Common mistake It is tempting to set the phone number as the primary key since it seems unique. However, phone numbers can be shared (family plans), reused, or changed. Always use a dedicated ID column as your primary key.

---

### Table 2: sales

This table records each individual sale that takes place in the store. Notice it references `customer_id`, `product_id`, and `clerk_id` — these are **foreign keys** that link to the other tables.

|Column Name|Data Type|Allow Nulls|
|---|---|---|
|sales_id|nchar(4)|No|
|sales_date|datetime|No|
|customer_id|nchar(10)|No|
|product_id|nchar(15)|No|
|clerk_id|nchar(4)|No|

> [!note] The `datetime` data type The `sales_date` column uses the **datetime** data type, which stores both the date and time of a transaction (e.g., `2025-03-20 14:35:00`). This is useful for tracking exactly when a sale occurred.

---

### Table 3: inventory

This table tracks all the products available for sale in the store.

|Column Name|Data Type|Allow Nulls|
|---|---|---|
|product_id|nchar(15)|No|
|product_name|nchar(20)|No|
|product_description|nchar(30)|No|
|wholesale_price|money|No|
|retail_price|money|No|
|product_photo|image|Yes|

> [!note] The `money` data type SQL Server has a built-in **money** data type specifically for currency values. It stores values with up to 4 decimal places and is ideal for prices. It is more appropriate than using a generic decimal for financial data.

> [!note] The `image` data type The **image** data type stores binary data — in this case, a photo of the product. It allows nulls because not every product will necessarily have a photo on file.

---

### Table 4: clerks

This table stores information about the store employees (clerks) who process sales.

|Column Name|Data Type|Allow Nulls|
|---|---|---|
|clerk_id|nchar(4)|No|
|clerk_first_name|nchar(20)|No|
|clerk_last_name|nchar(25)|No|

---

## Understanding `nchar()` — The Key Data Type

Almost every column in this lab uses **nchar()**, so it is important to understand what it means.

> [!note] What is `nchar()`? **nchar(n)** is a fixed-length character data type that supports **Unicode** (the "n" stands for national/Unicode). This means it can store letters from any language — English, French, Arabic, Chinese, etc.
> 
> The number in brackets defines the **maximum number of characters** that column can hold:
> 
> - `nchar(10)` → stores up to 10 characters
> - `nchar(35)` → stores up to 35 characters
> 
> It is _fixed-length_, meaning SQL Server always reserves that full amount of space, even if the actual value is shorter.

> [!tip] nchar vs varchar You might also encounter **varchar** in other databases. The key difference:
> 
> - `nchar` = fixed length, Unicode supported
> - `varchar` = variable length, saves space when values are shorter
> 
> For IDs and codes (like phone numbers or clerk IDs), `nchar` is commonly used since they are predictable in length.

---

## How to Create a Table in SSMS

**Steps:**

1. In the Object Explorer, expand your **Retail** database
2. Right-click on the **Tables** folder
3. Select **New Table...**
4. Enter each **Column Name**, **Data Type**, and whether **Allow Nulls** is checked
5. To set a **Primary Key**: right-click the row of the column you want as the PK and select **Set Primary Key**
6. Press **Ctrl + S** (or File > Save) to save the table — give it the correct name when prompted
7. After saving, right-click the **Tables** folder and select **Refresh** to see your new table appear

> [!warning] Tables do not appear automatically After saving a new table, it will NOT immediately show up in the Object Explorer. You must right-click the Tables folder and choose **Refresh** to see it.

---

## Adding Records with "Edit Top 200 Rows"

Once your tables are created, you need to populate them with at least **3 records each**.

**Steps:**

1. In the Object Explorer, expand Tables under your Retail database
2. Right-click the table you want to add data to
3. Select **Edit Top 200 Rows**
4. A spreadsheet-like grid will appear — click directly in the cells to type your data
5. Press **Tab** or **Enter** to move to the next cell/row
6. Your data saves automatically as you move between rows

> [!example] Sample data for the clerks table
> 
> |clerk_id|clerk_first_name|clerk_last_name|
> |---|---|---|
> |C001|Sarah|Johnson|
> |C002|Mike|Patel|
> |C003|Emma|Tremblay|

> [!tip] Fill in foreign key tables first When entering data into the **sales** table, you will reference `customer_id`, `product_id`, and `clerk_id`. Make sure those values already exist in the **customers**, **inventory**, and **clerks** tables first, so your data stays consistent.

---

## Creating the Database Diagram

The final task is to create a **Database Diagram** that visually shows how your tables are connected to each other through their keys.

> [!note] What is a Database Diagram? A **Database Diagram** in SSMS is a visual map of your tables and the **relationships** between them. You draw lines connecting a **primary key** in one table to a **foreign key** in another table. This is also called an **Entity-Relationship (ER) Diagram**.

**Steps:**

1. In the Object Explorer, right-click **Database Diagrams** under your Retail database
2. Select **New Database Diagram**
3. Add all 4 tables to the diagram
4. To create a relationship: click and drag from a **primary key** column to the matching **foreign key** column in another table
5. The key relationships to create are:
    - `customers.customer_id` → `sales.customer_id`
    - `inventory.product_id` → `sales.product_id`
    - `clerks.clerk_id` → `sales.clerk_id`
6. Save the diagram (Ctrl + S)
7. Take a **screenshot** and upload it to LMS for grading

> [!important] The sales table is your "hub" Notice that the **sales** table connects to all three other tables. This makes sense — every sale involves a customer, a product, and a clerk. The sales table stores the IDs from all three, making it the central table in this database design. This pattern is called a **fact table** in database design.

> [!tip] Primary Key vs Foreign Key recap
> 
> - **Primary Key (PK)**: the unique identifier _within_ a table (e.g., `customer_id` in the customers table)
> - **Foreign Key (FK)**: a column that _references_ a primary key from another table (e.g., `customer_id` in the sales table points back to the customers table)
> 
> Foreign keys are how relational databases link tables together.

---

## Lab Checklist

Before submitting, make sure you have completed everything:

- [ ] Registered for Microsoft Azure Labs and started your virtual machine
- [ ] Opened SQL Server Management Studio (SSMS)
- [ ] Created the **Retail** database
- [ ] Created the **customers** table with `customer_id` as the primary key
- [ ] Created the **sales** table with `sales_id` as the primary key
- [ ] Created the **inventory** table with `product_id` as the primary key
- [ ] Created the **clerks** table with `clerk_id` as the primary key
- [ ] Added at least **3 records** to each table using Edit Top 200 Rows
- [ ] Created a **Database Diagram** linking primary keys to foreign keys
- [ ] Saved the diagram and took a screenshot
- [ ] Uploaded the screenshot to LMS/Welearn

---

## Helpful Resources

- [SQL Server Management Studio overview](https://learn.microsoft.com/en-us/sql/ssms/sql-server-management-studio-ssms)
- [SQL Server data types explained](https://learn.microsoft.com/en-us/sql/t-sql/data-types/data-types-transact-sql)
- [Understanding primary and foreign keys](https://www.geeksforgeeks.org/difference-between-primary-key-and-foreign-key/)
- [SQL basics refresher](https://www.w3schools.com/sql/)

---

## Tags

#business_applications #SQL #SQL_server #databases #microsoft_azure #SSMS #primary_key #foreign_key #nchar #database_diagram #study_notes #CSA103