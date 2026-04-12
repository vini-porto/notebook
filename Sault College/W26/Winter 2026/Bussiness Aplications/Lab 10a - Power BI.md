
This lab walks you through connecting a live [[SQL Server]] database to [[Power BI]] Desktop and creating meaningful charts from real healthcare data. By the end, you will have built multiple visualizations from two database tables.

## What You Are Working With

> [!note] The Database Setup The lab uses a database called **health** hosted on a server called **HIN-SERVER**. Inside this database there are two tables:
> 
> - **dbo.hospitals** — contains data about each hospital (name, number of beds, number of employees, annual budget, area population, location details, etc.)
> - **dbo.patients** — contains data about individual patients (name, blood type, weight, height, etc.)

These tables live inside [[SQL Server]], and you access them first through **SQL Server Management Studio (SSMS)** before pulling them into [[Power BI]].

---

## Part 1 — Setting Up Your Data in SQL Server Management Studio

Before you can visualize anything, your data needs to be in good shape. This part is about reviewing and editing your tables directly.

### Connecting and Navigating

1. Open **SQL Server Management Studio** and click **Connect** at the login screen.
2. In the left panel (called the Object Explorer), click the **+** beside **Databases**.
3. Expand the **health** database, then expand **Tables**.
4. You should see two tables: `dbo.hospitals` and `dbo.patients`.

### Editing the Hospitals Table

Right-click **dbo.hospitals** and choose **Edit Top 200 Rows**. This lets you view and modify the data directly.

> [!important] Match the Professor's Table Structure You need to make sure your `dbo.hospitals` table has all the same fields as the one shown in the lecture. Some fields you will likely need include:
> 
> - `hospital_name`
> - `Number_of_beds`
> - `number_of_employees`
> - `annual_budget`
> - `area_population`
> - `hospital_city`, `hospital_id`, `hospital_lat`, `hospital_long`
> 
> Add any missing fields to match the example shown in class. These fields are what Power BI will use to generate charts.

Once your table matches, **Save and Close**.

### Editing the Patients Table

Right-click **dbo.patients** and choose **Edit Top 200 Rows**.

> [!important] What to add
> 
> - Add **4 new patients** of your choice. Each patient must have:
>     - A name
>     - A blood type (choose from: **O, A, B, AB**)
>     - A weight (metric)
>     - A height (metric)
> - Also add a blood type for the existing patient **Jane Smiles** (she is missing one).

Save and close when done.

---

## Part 2 — Loading Your Data into Power BI Desktop

Now that your data is ready in SQL Server, you bring it into [[Power BI]] to start building visuals.

> [!tip] Power BI takes about a minute to load. Click the icon once and wait — do not click multiple times.

### Steps to Connect Power BI to SQL Server

1. Open **Power BI Desktop** from the desktop icon.
2. In the top corner, select **New**, then choose **Report**.
3. A prompt will ask for a data source — choose the **2nd option**: **Import Data from SQL Server**.
4. Enter the following connection details:
    - **Server name:** `HIN-SERVER`
    - **Database name:** `health`
5. Select **Import**, then click **OK**.
6. A table selection screen will appear — **check both** `hospitals` and `patients`, then click **Load**.

After loading, both tables will appear in the **Data** panel on the far right of the Power BI screen. Click the arrow beside each table name to expand and see all the fields available.

---

## Part 3 — Creating Your Charts in Power BI

This is the main part of the lab. You will create **four charts** total — two guided and two on your own.

> [!note] How Building a Chart Works in Power BI The process is always the same:
> 
> 1. Drag a field from the **Data** panel onto the canvas (the blank area in the middle).
> 2. Drag another field onto the same box.
> 3. Choose a chart type from the **Visualizations** panel. Power BI automatically generates the visual. You can resize or reposition it on the canvas.

---

### Chart 1 — Area Population by Hospital (Pie Chart)

**Fields used:** `area_population` and `hospital_name` from the **hospitals** table.

Steps:

1. From the **Data** panel, drag **area_population** into the canvas box.
2. Then drag **hospital_name** into the same box.
3. In the **Visualizations** panel, click **Pie Chart**.

> [!example] Expected Result A pie chart titled _"Sum of area_population by hospital_name"_ will appear. Each slice represents one hospital, sized by how large a population it serves. Sault Area Hospital, for instance, makes up the largest slice at around 76.92%.

---

### Chart 2 — Number of Employees Per Hospital (Bar/Line Chart)

**Fields used:** `number_of_employees` and `hospital_name` from the **hospitals** table.

Steps:

1. From the top menu in Power BI, click **New Visual** to add a second chart.
2. Drag **number_of_employees** to the center of the new box.
3. Drag **hospital_name** to the center.
4. In the **Visualizations** panel, select **Line Chart** (or bar chart — the lab shows a bar-style result).

> [!example] Expected Result A chart showing _"Sum of number_of_employees by hospital_name"_ — you can immediately see which hospitals have the most staff. St. Josephs General appears to have the highest count in the example.

---

### Chart 3 — Annual Budget by Hospital (Bar Chart)

This one you create independently using the same method as above.

- **Fields:** `annual_budget` + `hospital_name`
- **Chart type:** Bar Chart

> [!tip] In a bar chart, the hospitals will be listed along one axis and the budget value along the other. This makes it easy to compare budgets at a glance.

---

### Chart 4 — Number of Beds by Hospital (Line Chart)

Again, use the same drag-and-drop method.

- **Fields:** `Number_of_beds` + `hospital_name`
- **Chart type:** Line Chart

---

## Final Product Summary

When all four charts are on your canvas, your Power BI report should display:

|Chart|Fields Used|Chart Type|
|---|---|---|
|Area Population by Hospital|`area_population`, `hospital_name`|Pie Chart|
|Employees per Hospital|`number_of_employees`, `hospital_name`|Bar/Line Chart|
|Annual Budget by Hospital|`annual_budget`, `hospital_name`|Bar Chart|
|Number of Beds by Hospital|`Number_of_beds`, `hospital_name`|Line Chart|

---

## Saving Your Work

> [!important] File Naming Convention Go to **File → Save As** and name the file: `Health-Demo.XX.pbix` Replace **XX** with your **first and last initials** (e.g., if your name is Jane Smith, save it as `Health-Demo.JS.pbix`).
> 
> Upload this file to the **LMS (Learning Management System)** for marking.

---

## Bonus / Advanced Section — Patient Charts

> [!note] Advanced Challenge Using the **patients** table (instead of hospitals), create **3 more charts** of your own design using any fields available (blood type, weight, height, etc.).
> 
> Save this file separately as: `Advanced-Health-XX.pbix` (Replace XX with your initials.)
> 
> Upload to LMS for marking as well.

This is a creativity exercise — there is no single right answer. Think about what comparisons might be interesting, such as average weight by blood type, or patient count by blood type.

---

## Key Concepts at a Glance

> [!note] Important Terms
> 
> - **[[SQL Server]]** — A database management system by Microsoft that stores and manages structured data in tables.
> - **SQL Server Management Studio (SSMS)** — The tool used to view, edit, and manage SQL Server databases.
> - **[[Power BI]]** — A Microsoft [[Data Visualization]] tool that connects to data sources and builds interactive charts and reports.
> - **[[Databases]]** — Organized collections of data. In this lab, the database is called `health`.
> - **Tables** — The individual data containers inside a database (e.g., `dbo.hospitals`, `dbo.patients`).
> - **.pbix** — The file format used to save Power BI reports.
> - **Import Mode** — The method used in this lab to pull data from SQL Server into Power BI (a snapshot is loaded in, not a live connection).

> [!tip] Extra Reading
> 
> - [Power BI Documentation](https://powerbi.microsoft.com/en-us/documentation) — Official Microsoft docs for Power BI
> - [SQL Server Docs](https://docs.microsoft.com/en-us/sql) — Reference for SQL Server concepts
> - [W3Schools SQL](https://www.w3schools.com/sql/) — Good refresher on understanding how database tables and fields work

---

## Tags

#business_applications #power_bi #SQL_server #databases #data_visualization #charting #study_notes #lab_notes #CSA103