
This lab walks through setting up a full web stack on a Windows Server so that **PHP web pages** can talk to a **SQL Server database**. By the end, you will have a working webpage that pulls customer data from your Retail database and displays it in a browser.

Here is the big picture of what we are building:

> [!note] The Full Stack Overview **Browser** → **IIS Web Server** (with PHP) → **ODBC Driver** → **SQL Server (Retail DB)**
> 
> Each piece has to be installed and configured in order. If one piece is missing or wrong, nothing works.

---

## Step 1 – Install IIS and CGI via Server Manager

[[IIS Web Server]] is Microsoft's built-in web server for Windows. Before PHP can run on it, you need to make sure IIS is installed, and specifically that **CGI** is enabled (CGI is how IIS hands off requests to PHP).

### How to open Server Manager

1. Type **Server Manager** in the bottom search bar.
2. **Wait at least 20 seconds** before clicking anything — it needs time to gather system info.
3. Always pause a few seconds between clicks when inside Server Manager (remote desktop + server processing adds delay).

> [!warning] Patience is required Clicking too fast in Server Manager can cause the wizard to freeze or skip steps. Slow down and wait between each action.

### Selecting the right roles

When the **Add Roles and Features Wizard** opens:

- Choose **Role-based or feature-based installation**, then click Next.
- On the **Select Server Roles** screen, expand and select **Web Server (IIS)**.

Inside the Web Server section, make sure the following sub-features are checked:

**Common HTTP Features**, **Health and Diagnostics**, **Performance**, **Security** (including Request Filtering, Basic Authentication, Windows Authentication, URL Authorization, etc.)

Then expand **Application Development** and make sure these are selected:

|Feature|Why it matters|
|---|---|
|.NET Extensibility 3.5|Supports older .NET apps|
|.NET Extensibility 4.7|Supports newer .NET apps|
|Application Initialization|Warms up apps on server start|
|**CGI**|Required for PHP to run on IIS|
|ISAPI Extensions|Allows server-side scripts|
|ISAPI Filters|Intercepts and modifies requests|
|Server Side Includes|Lets pages include other files|
|WebSocket Protocol|Supports real-time connections|

> [!important] CGI is the key feature **CGI (Common Gateway Interface)** is what allows IIS to run PHP scripts. Without it, IIS cannot process `.php` files at all. Make sure it is checked.

After the installation completes (watch the progress bar), you **must restart the server** and reconnect before continuing.

---

## Step 2 – Install PHP on the Server

[[PHP]] is the scripting language that will run on IIS and connect to the database. You need to install it manually on Windows Server.

### Create the PHP folder

Inside your server (via Labs.azure.com), open **File Manager** and go to the `C:` drive. Create a new subfolder named `php`. You will now have:

```
C:\php
```

### Download the correct PHP version

Go to the PHP for Windows download page and look for:

**PHP 8.3.30 — Non-Thread Safe — 64-bit (VS16-x64)**

> [!warning] Do NOT download PHP 8.4 or 8.5 These versions have been problematic for this lab environment. Stick to **8.3.30** specifically.

- Download the **Zip** file (not the installer).
- Once downloaded, **right-click** the zip file → **Extract** → set the destination to `C:\php`.

This unpacks all the PHP files into your `C:\php` folder.

### Set up php.ini

PHP reads its configuration from a file called `php.ini`. The download includes a sample file called `php.ini-production`.

1. In File Manager, go to `C:\php`.
2. At the top, go to **View** → enable **Show File Extensions** (important — otherwise you cannot verify the file name).
3. **Right-click** `php.ini-production` → **Copy**.
4. Right-click the copy → **Rename** → type `php.ini`.
5. Confirm the prompt that warns you about changing the file extension.

> [!warning] Verify the file name carefully The file must be named exactly `php.ini` — not `php.ini.txt`. If file extensions are hidden, you cannot tell the difference. Always enable "Show File Extensions" first.

---

## Step 3 – Install the SQL Server PHP Drivers

[[PHP]] cannot talk to [[SQL Server]] by itself. You need to install special **Microsoft PHP Drivers for SQL Server**.

### Download the drivers

Visit: [https://learn.microsoft.com/en-us/sql/connect/php/download-drivers-php-sql-server?view=sql-server-ver16](https://learn.microsoft.com/en-us/sql/connect/php/download-drivers-php-sql-server?view=sql-server-ver16)

Scroll to the Download section and grab the **Microsoft Drivers for PHP for SQL Server (Windows)**.

### Install the drivers

1. When prompted, unzip the downloaded file into `C:\php\drivers`.
2. The zip may create a subfolder inside `drivers` — go into that subfolder, select all files, copy them, go back up to `C:\php\drivers`, and paste.
3. Find the file named:

```
php_sqlsrv_83_nts_x64.dll
```

4. Copy **that specific file** into `C:\php\ext`.

> [!note] Why this specific file? The filename tells you exactly what it is:
> 
> - `php_sqlsrv` → SQL Server driver for PHP
> - `83` → for PHP version 8.3
> - `nts` → Non-Thread Safe (matches the PHP version we downloaded)
> - `x64` → 64-bit
> - `.dll` → Windows dynamic library file

---

## Step 4 – Configure php.ini

Now open `C:\php\php.ini` in **Notepad** and make two edits:

### Modification 1 — Uncomment the extension directory (around line 780)

Find this line:

```ini
; extension_dir = 'ext'
```

Remove the semicolon at the start. It should now look like:

```ini
extension_dir = 'ext'
```

> [!note] What does this mean? In PHP config files, a `;` at the start of a line is a **comment** — it means PHP ignores that line. Removing the `;` "uncomments" it and activates the setting. This tells PHP to look in the `ext` subfolder for its extensions (add-on libraries).

### Modification 2 — Register the SQL Server driver (around line 920)

Scroll to around line 920, just below the **DYNAMICS EXTENSION** section, and add this new line:

```ini
extension=c:\php\drivers\php_sqlsrv_83_nts_x64.dll
```

**Save the file.**

> [!important] What does this do? This tells PHP to load the SQL Server driver every time PHP runs. Without this line, PHP has no idea that the SQL Server driver exists, even if you put it in the right folder.

---

## Step 5 – Configure IIS to Use PHP

Now you need to tell [[IIS Web Server]] what to do when someone requests a `.php` file.

### Add a Handler Mapping in IIS

1. Open **IIS Manager** from Windows Administrative Tools.
2. Click on your **Default Web Site**.
3. In the center panel, find and open **Handler Mappings**.
4. On the right side, click **Add Module Mapping**.
5. Fill in the fields exactly as follows:

|Field|Value|
|---|---|
|Request path|`*.php`|
|Module|`FastCgiModule`|
|Executable|`C:\php\php-cgi.exe`|
|Name|`PHP via FastCGI`|

6. Click **OK**.
7. When asked _"Do you want to make a FastCGI app for this executable?"_, click **Yes**.

> [!note] What is FastCGI? **FastCGI** is a more efficient version of CGI. Instead of starting a new PHP process for every single web request (which is slow), FastCGI keeps PHP processes running and reuses them. This is the standard way to run PHP on IIS.

### Add PHP to the System Path

This lets Windows find PHP from anywhere on the system.

1. Open **Control Panel** → **System** → **Advanced** → **Environment Variables**.
2. Under **User Variables**, click on **Path** → **Edit** → **New**.
3. Type `C:\php` on a new line.
4. Click **OK**, then **OK** again.

---

## Step 6 – Create the ODBC Connection

[[ODBC Drivers]] (Open Database Connectivity) act as a universal translator between your PHP code and SQL Server. Think of it as a named shortcut that PHP can use to reach your database without needing the full connection string every time.

> [!note] What is ODBC? **ODBC** is a standard interface that lets applications connect to databases. Instead of PHP needing to know the exact server address, login, and database name directly, it just refers to the ODBC connection by its name (called a **DSN — Data Source Name**).

### Steps to create the ODBC connection

1. Start **SQL Server Management Studio** and log in (let it fully load first).
2. Go to the Windows menu → **Windows Administrative Tools** → **ODBC Data Source (64-bit)**.
3. Click the **System DSN** tab at the top.
4. Click **Add** → select **SQL Server** → click **Finish**.

### Fill in the connection wizard

**Screen 1 — Name and Server:**

|Field|Value|
|---|---|
|Name|`CSA103-Retail`|
|Description|`CSA103-Retail`|
|Server|`HIN-SERVER`|

> [!warning] Do NOT use the dropdown for the Server field The dropdown can hang up your system. Type the server name manually: `HIN-SERVER`

**Screen 2 — Authentication:**

- Select **With SQL Server authentication using a login ID and password entered by the user**.
- Login ID: `csa103`
- Password: `@Saultcollege443`

**Screen 3 — Database:**

- Check **Change the default database to:** and select **Retail** from the dropdown.

Then click **Next**, **Next**, **Finish**, and finally **Test Data Source**.

> [!important] A successful test looks like this
> 
> ```
> Microsoft SQL Server ODBC Driver Version 10.00.17763
> Running connectivity tests...
> Attempting connection
> Connection established
> Verifying option settings
> Disconnecting from server
> TESTS COMPLETED SUCCESSFULLY!
> ```
> 
> If the test fails, go back through the steps carefully. A failed ODBC test means your PHP pages will not work.

---

## Step 7 – Deploy and Test the PHP Webpage

### Copy the file to the web root

Download **view-customers.php** from the CSA103 course on Welearn, then copy it into:

```
C:\inetpub\wwwroot\
```

> [!note] What is wwwroot? `C:\inetpub\wwwroot` is the **web root** — the folder that IIS serves files from by default. Any file you place here becomes accessible through your web browser. This is where all your PHP pages will live.

### Test in the browser

Open a web browser and go to:

```
127.0.0.1/view-customers.php
```

`127.0.0.1` is the **loopback address** — it always refers to the current machine (your server). If everything is configured correctly, you will see a table of Retail Customers like this:

|Customer ID|Phone #|First Name|Last Name|Email|
|---|---|---|---|---|
|0001|7055551212|Susan|Alba|susan.alba@sales.ca|
|0002|7055551313|Larry|Styles|larry.styles@gmail.com|
|0003|7055551414|Shelly|Croche|sheely.croche@ssm-sales.ca|

### Take the required screenshot

Take a screenshot of your `view-customers.php` page showing your customers, then upload it to the **DropBox** on Welearn for marking.

---

## Full Setup Checklist

> [!tip] Use this to verify you have not missed anything
> 
> - [ ] IIS installed with CGI enabled via Server Manager
> - [ ] Server restarted after IIS installation
> - [ ] `C:\php` folder created
> - [ ] PHP 8.3.30 Non-Thread Safe 64-bit downloaded and extracted to `C:\php`
> - [ ] `php.ini-production` copied and renamed to `php.ini`
> - [ ] File extensions visible in File Manager
> - [ ] SQL Server PHP drivers downloaded and unzipped to `C:\php\drivers`
> - [ ] `php_sqlsrv_83_nts_x64.dll` copied to `C:\php\ext`
> - [ ] `php.ini` edited: `extension_dir = 'ext'` uncommented (line ~780)
> - [ ] `php.ini` edited: SQL Server driver extension added (line ~920)
> - [ ] IIS Handler Mapping added for `*.php` using FastCGI
> - [ ] `C:\php` added to the system Environment Variable Path
> - [ ] ODBC System DSN `CSA103-Retail` created and tested successfully
> - [ ] `view-customers.php` copied to `C:\inetpub\wwwroot`
> - [ ] Webpage loads at `127.0.0.1/view-customers.php`
> - [ ] Screenshot taken and uploaded to DropBox

---

## Helpful External Resources

- [PHP for Windows Downloads](https://windows.php.net/download/) — official PHP binaries for Windows
- [Microsoft PHP Drivers for SQL Server](https://learn.microsoft.com/en-us/sql/connect/php/download-drivers-php-sql-server?view=sql-server-ver16) — the driver download page used in this lab
- [PHP Documentation](https://www.php.net/docs.php) — official PHP reference
- [W3Schools PHP Tutorial](https://www.w3schools.com/php/) — beginner-friendly PHP guide
- [IIS Documentation on Microsoft Learn](https://learn.microsoft.com/en-us/iis/) — IIS configuration reference
- [GeeksForGeeks – What is ODBC?](https://www.geeksforgeeks.org/odbc-full-form/) — plain-language ODBC explanation

---

## Tags

#business_applications #PHP #IIS #SQL_server #ODBC #web_development #databases #windows_server #CGI #FastCGI #study_notes #CSA103