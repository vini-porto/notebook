# CSD216 Multiple-Choice Mock Tests (Weeks 4–6)

Covers:
- [[Week 4 - Defining Tables and Fields]]
- [[Week 5 - Establishing Relationships]]
- [[Week 6 - Business Rules and Views]]

> [!tip] How to use these
> Your test is **20 multiple-choice questions**, so each mock test below has exactly 20. Do one in one sitting, time yourself, then open the answers. Test 1 is mostly definitions, Test 2 mixes definitions and scenarios, and Test 3 is the hardest (tricky distractors and "which is NOT" questions).
>
> **MC strategies for this course:**
> - Watch for **"NOT"** in the question.
> - Distractors often swap two similar terms: *relation/relationship*, *participation type/degree*, *Restrict/Deny*, *Generic/Replica*, *subset/validation table*.
> - "Always" and "never" are usually wrong unless the lecture says so (e.g., N:M **always** needs a linking table; table names should **avoid** proper names).
> - If two answers both sound right, pick the one using the lecture's exact wording.

---

## Mock Test 1 (20 questions)

**1.** A table named "Customer File" most likely has which problem?
a) It is too short
b) It uses an abbreviation
c) The "subject" is probably several subjects bundled together
d) It contains a proper name

> [!success]- Answer
> **c).** Words like file, record, table, and report often mean several subjects are bundled together.

**2.** Which is the best table description for an **Equipment** table?
a) "Equipment."
b) "The tools and machines we own. This data lets us schedule maintenance and plan replacements."
c) "The tools and machines we own, including serial number, purchase date and cost."
d) "The tools and machines we own. Entered by the manager on the Assets screen."

> [!success]- Answer
> **b).** It gives a definition plus a reason (*why*). (a) is too short, (c) lists fields, and (d) describes *how*, with implementation details.

**3.** A field `Phone Numbers` contains "705-555-1111, 705-555-2222". This is a:
a) Multipart field
b) Multivalued field
c) Calculated field
d) Duplicate field

> [!success]- Answer
> **b) Multivalued.** It holds several values of the same kind.

**4.** A field `Address` contains "12 King St, Toronto, ON M5V 1A1". This is a:
a) Multipart field
b) Multivalued field
c) Calculated field
d) Validation field

> [!success]- Answer
> **a) Multipart.** It holds several distinct components that can be broken apart.

**5.** How do you resolve a multipart field?
a) Move it into a new table
b) Delete it
c) Create a separate field for each subcomponent
d) Turn it into a validation table

> [!success]- Answer
> **c).** Moving it into a new table is how you resolve a *multivalued* field.

**6.** A Students table has the fields Instrument 1, Instrument 2, and Instrument 3. What should you do?
a) Keep them; they make queries easy
b) Combine them into one comma-separated field
c) Move them into a new table with one row per student per instrument
d) Make Instrument 1 the primary key

> [!success]- Answer
> **c).** They are repeating duplicate fields, a characteristic of a missed subject (Student Instruments).

**7.** When is a duplicate field acceptable?
a) When it is used as reference information
b) When it is used to relate two tables
c) When it speeds up reports
d) Never

> [!success]- Answer
> **b).** That is the only acceptable reason.

**8.** In an Inventory table, Author and ISBN are only filled for books, and Model is only filled for equipment. This suggests:
a) Linking tables
b) Validation tables
c) Subset tables
d) Calculated fields

> [!success]- Answer
> **c) Subset tables.** Fields that are only sometimes filled point to supertype/subtype tables.

**9.** Which field is the best candidate key for Employees?
a) EmpLast Name
b) EmpZipcode
c) EmpHome Phone
d) Employee ID

> [!success]- Answer
> **d).** It's unique, a single field, and never changes. The others repeat in the sample data.

**10.** When choosing a primary key, you should prefer:
a) A composite text key
b) A single-field, integer-based key
c) The longest candidate key
d) A field that users can easily edit

> [!success]- Answer
> **b).** A surrogate key is almost always a good choice.

**11.** In a Sales Invoices table, EmpHome Phone is determined by (EmpFirst Name, EmpLast Name), which is not a candidate key. What should you do?
a) Make EmpHome Phone the primary key
b) Remove EmpHome Phone from Sales Invoices
c) Add EmpHome Phone to the primary key
d) Nothing; Invoice Number also determines it

> [!success]- Answer
> **b).** Each field must depend **only** on candidate keys. The phone number is a characteristic of the employee, not the invoice.

**12.** Which is a **physical** element of a field specification?
a) Range of values
b) Null support
c) Character support
d) Edit rule

> [!success]- Answer
> **c).** The physical elements are data type, decimal places, length, and character support. The other three are logical elements.

**13.** An ID is assigned when a record is created and can never be changed. Its edit rule is:
a) Enter Now, Edits Allowed
b) Enter Now, Edits Not Allowed
c) Enter Later, Edits Allowed
d) Not Determined At This Time

> [!success]- Answer
> **b).** This matches the Employee ID Number example.

**14.** A **Replica** field specification:
a) Is used for fields unique in the database
b) Copies a Generic specification and can override its attributes
c) Lists other tables that share a primary key
d) Cannot change anything from its source

> [!success]- Answer
> **b).** A replica can (and should) override attributes, such as its parent table and description.

**15.** In a one-to-many relationship, the foreign key goes in:
a) The parent ("one" side)
b) The child ("many" side)
c) A linking table
d) Both tables

> [!success]- Answer
> **b).**

**16.** In the relationship matrix, one cell for a pair says 1:1 and the other says 1:N. The relationship is:
a) One-to-one
b) One-to-many
c) Many-to-many
d) Recursive

> [!success]- Answer
> **b).**

**17.** The primary key of a linking table is:
a) A new surrogate key only
b) The PK of the larger table
c) A composite of two foreign keys
d) Any text field

> [!success]- Answer
> **c).** It is a composite of two FKs, one for each table in the relationship.

**18.** Unless there is a reason to choose another, which deletion rule should you use?
a) Cascade
b) Restrict
c) Nullify
d) Deny

> [!success]- Answer
> **b) Restrict.**

**19.** "A room cannot have overlapping bookings" is:
a) A database oriented, field-specific rule
b) A database oriented, relationship-specific rule
c) An application oriented rule
d) A deletion rule

> [!success]- Answer
> **c).** Checking for an overlap means comparing a new booking with the other records, which field specs and relationship characteristics can't express.

**20.** Which statement about a (non-materialized) view is TRUE?
a) It stores a copy of the data
b) It requires a primary key
c) It stores only the query; the RDBMS rebuilds it each time it is read
d) It cannot use fields from more than one table

> [!success]- Answer
> **c).**

---

## Mock Test 2 (20 questions)

**1.** In what order do you use sources to build the Preliminary Table List?
a) Mission Objectives → Subject List → Preliminary Field List
b) Preliminary Field List → Subject List → Mission Objectives
c) Subject List → Preliminary Field List → Calculated Field List
d) Subject List → Mission Objectives → Preliminary Field List

> [!success]- Answer
> **b).**

**2.** Your list has both "Client" and "Customer", and they mean the same subject. You should:
a) Keep both
b) Rename them to Client1 and Customer2
c) Pick the name that best represents the subject and remove the other
d) Make one a subset of the other

> [!success]- Answer
> **c).**

**3.** "Equipment" is used for both tools and vehicles. You should:
a) Remove one of them
b) Rename them to disambiguate, e.g. Tools and Vehicles
c) Merge them into one table
d) Make Equipment a validation table

> [!success]- Answer
> **b).** Same name, different subjects means rename.

**4.** A table description should explain:
a) How the table is used in the application
b) Which fields the table contains
c) Why the table is needed (plus a definition)
d) Examples of rows in the table

> [!success]- Answer
> **c).**

**5.** An Instructors table has Categories Taught ("DB, OS, WP") and Maximum Level ("Basic, Advanced, Basic"). The counts always match. What should you do?
a) Put each in its own separate new table
b) Put both in the same new table
c) Keep them in Instructors
d) Make them calculated fields

> [!success]- Answer
> **b).** Matching counts mean the fields are related: each level belongs to one category.

**6.** Why is Social Security Number ineligible as a candidate key?
a) It is not unique
b) It can cause a security or privacy breach
c) It is a calculated value
d) It has too many digits

> [!success]- Answer
> **b).**

**7.** Full-Time Employees is a subset of Employees. Its primary key must be:
a) A new surrogate key
b) The same primary key as Employees
c) A composite of Salary and Position
d) EmpLast Name

> [!success]- Answer
> **b).**

**8.** What does "A → B" mean?
a) A is a foreign key to B
b) B is functionally dependent on A (A determines B)
c) A and B are in a one-to-many relationship
d) A is a subset of B

> [!success]- Answer
> **b).**

**9.** What is the "Shared by" general element used for?
a) In replica specs, to name the source specification
b) In primary key fields, to list other related tables
c) To list users who can edit the field
d) To list the views that use the field

> [!success]- Answer
> **b).** Option (a) describes Source specification.

**10.** In an ERD, a rounded rectangle means:
a) A view
b) A subset table
c) A linking table
d) A recursive relationship

> [!success]- Answer
> **b).**

**11.** Compensation (salary data) is split from Staff for security, but describes the same subject. How is the 1:1 relationship established?
a) Add a separate StaffId FK field to Compensation
b) Compensation's PK also becomes the FK to Staff's PK
c) Create a linking table
d) Put CompensationId into Staff as a composite key

> [!success]- Answer
> **b).** When the child is the same subject as the parent, the child's PK is also the FK.

**12.** In the relationship matrix, the diagonal cells (Staff/Staff, Classes/Classes) represent:
a) Indirect relationships
b) Recursive relationships
c) Subset tables
d) Validation views

> [!success]- Answer
> **b).** You must ask the question from both sides.

**13.** Faculty (a subtype of Staff) teach Classes. What goes in the Classes/Staff cells?
a) 1:N
b) 1:1
c) Nothing; leave them blank (indirect relationship)
d) N:M

> [!success]- Answer
> **c).** The relationship belongs between Classes and Faculty.

**14.** Each member can sponsor at most one other member, and each member has at most one sponsor. This is a:
a) Recursive one-to-one
b) Recursive one-to-many
c) Recursive many-to-many
d) One-to-many between two tables

> [!success]- Answer
> **a).**

**15.** Orders and Products are many-to-many. Where does QuotePrice (the price of a specific product on a specific order) go?
a) Orders
b) Products
c) The linking table (OrderDetails)
d) A view

> [!success]- Answer
> **c).**

**16.** When an employee is deleted, all their orders get the lead salesperson's Id. Which deletion rule is this?
a) Restrict
b) Cascade
c) Nullify
d) Set Default

> [!success]- Answer
> **d).**

**17.** On a relationship line, the inside mark is a circle. This means participation is:
a) Mandatory
b) Optional
c) Many
d) Exactly one

> [!success]- Answer
> **b).** A circle means optional (minimum 0).

**18.** Which symbol shows a participation degree of (1,N)?
a) Circle + crow's foot
b) Two bars
c) Bar + crow's foot
d) Circle + bar

> [!success]- Answer
> **c).** (0,N) is circle + crow's foot, (1,1) is two bars, and (0,1) is circle + bar.

**19.** Which is another name for a validation table?
a) Linking table
b) Lookup (or category) table
c) Subset table
d) Associative table

> [!success]- Answer
> **b).** "Linking" and "associative" both mean a join table.

**20.** A salesperson wants to pick any province each time they open the customer list. How is this handled?
a) Create one view per province
b) Put the province filter in the ER diagram
c) At the application level (application code or a stored procedure)
d) With a validation table

> [!success]- Answer
> **c).** A filter chosen at run-time is an application concern. Fixed filters go in the ER diagram.

---

## Mock Test 3 (20 questions, harder)

**1.** Every table on the Final Table List must have:
a) A name, a type, and a description
b) A name, a primary key, and a view
c) A name and a list of business rules
d) A type and a deletion rule

> [!success]- Answer
> **a).**

**2.** A field named `Qty` breaks which naming guideline?
a) It must be plural
b) Don't use acronyms or abbreviations
c) Avoid proper names
d) It must include the word "field"

> [!success]- Answer
> **b).** Use something like Quantity On Hand instead.

**3.** Why is a calculated field a problem in a table?
a) It can't be indexed
b) It needs constant maintenance to stay in sync with the values it depends on
c) It is hard to filter or sort
d) It needs string parsing to read

> [!success]- Answer
> **b).** (c) describes multivalued fields, and (d) describes multipart fields.

**4.** Using Week 5's ideas, a multivalued field often represents:
a) A recursive relationship
b) A one-to-many relationship with a previously unrecognized subject
c) A one-to-one supertype/subtype relationship
d) A validation view

> [!success]- Answer
> **b).** The relating field should be an FK to the original table's PK.

**5.** Full-time employees have Salary Amount and part-time employees have Hourly Rate. Both have Date Hired. Where does Date Hired go?
a) In both subset tables
b) In the Employees (supertype) table
c) In a linking table
d) In a validation table

> [!success]- Answer
> **b).** Common fields go in the supertype, and specific fields go in the subsets.

**6.** Which is NOT a requirement of a candidate key?
a) It cannot contain nulls
b) It has a minimal number of fields
c) Its value never (or very rarely) changes
d) It must have the same name as its table

> [!success]- Answer
> **d).**

**7.** Which is a **logical** element of a field specification?
a) Data type
b) Length
c) Range of values
d) Decimal places

> [!success]- Answer
> **c).** The other three are physical elements.

**8.** Which is NOT required of a foreign key?
a) Its field specification is a replica of the PK it references
b) It draws its values from the PK it references
c) It has the same name as the PK it references
d) It refers to a primary key

> [!success]- Answer
> **c).** This requirement is crossed out on the slide. For example, ManagerId references StaffId.

**9.** In the recursive many-to-many Parts / Part Components design, the two FKs in Part Components reference:
a) Parts and Components, two different tables
b) Both reference the PK of Parts
c) Only one references Parts; the other is a surrogate key
d) A validation table

> [!success]- Answer
> **b).**

**10.** Managers (a subset of Staff) and Departments are 1:1. Departments is a different subject from Managers. How is it established?
a) Departments' PK becomes an FK to Managers
b) Add a separate FK field (ManagerId) to Departments
c) Create a linking table
d) Merge Departments into Managers

> [!success]- Answer
> **b).** When the child is a different subject, it gets a separate FK field.

**11.** For the Nullify deletion rule to work, the foreign key must:
a) Be part of a composite PK
b) Allow nulls
c) Have a default value
d) Be unique

> [!success]- Answer
> **b).** Set Default is the rule that needs a default value.

**12.** "(0,200)" is written at the Rooms end of the Buildings–Rooms line. This means:
a) Each room belongs to 0 to 200 buildings
b) Each building is related to 0 to 200 rooms
c) There are 200 rooms in total
d) Rooms' participation is mandatory

> [!success]- Answer
> **b).** Degree for A = the minimum and maximum number of B records each A record may relate to.

**13.** Which part of referential integrity guarantees that "records can be deleted without adverse effects"?
a) FK/PK pairs
b) Participation type
c) Deletion rules
d) Participation degree

> [!success]- Answer
> **c).** Participation type covers meaningful inserts, degree covers limits, and FK/PK pairs cover a sound connection.

**14.** "Order dates cannot be earlier than May 16, 2018" changes which field element?
a) Data type
b) Range of values
c) Null support
d) Key type

> [!success]- Answer
> **b).**

**15.** "Each class must have at least 5 and at most 20 students" is:
a) Field specific; it changes range of values
b) Relationship specific; it changes the degree of participation
c) Application oriented
d) Relationship specific; it changes the deletion rule only

> [!success]- Answer
> **b).** The degree becomes (5,20).

**16.** Which action is NOT one you test for a **relationship**-specific rule?
a) Entering a new record
b) Not entering a new record
c) Deleting a record
d) Changing the value in a field

> [!success]- Answer
> **d).** Changing a value is one of the tests for *field* rules.

**17.** In a Business Rule Specification, the possible **Category** values are:
a) Database / Application
b) Field / Relationship
c) Insert / Delete / Update
d) Data / Aggregate / Validation

> [!success]- Answer
> **b).** Database/Application is the **Type**, and Insert/Delete/Update is **Tested on**.

**18.** Why does the instructor say "Preferred customers get 15% off" is a weak example of an application rule?
a) Discounts are illegal to store
b) It can be modelled in the database with Statuses and Promotions (DiscountRate) tables
c) It is really a deletion rule
d) It should be a view

> [!success]- Answer
> **b).**

**19.** A view includes `LastPurchaseDate = MAX(OrderDate)`. What kind of view is it, and how is it implemented?
a) Validation view, with a CHECK constraint
b) Aggregated view, with GROUP BY
c) Single-table data view, with no query
d) Materialized view, always

> [!success]- Answer
> **b).**

**20.** Which is NOT required when drawing a view in an ER diagram?
a) Making it visually distinct from tables
b) Connecting it to base tables with non-relationship lines
c) Listing calculated field expressions and filters
d) Marking its primary key and deletion rule

> [!success]- Answer
> **d).** Views don't need a PK and aren't relationships, so there's no deletion rule.

---

## Score yourself

| Score | What it means |
|---|---|
| 18–20 | Ready. Skim the Quick-Find index in the Weeks 4–6 practice file. |
| 14–17 | Review the questions you missed in the lecture sections they come from. |
| < 14 | Revisit the flashcards at the end of each lecture note, then retake the test. |

## Tags

#CSD216 #databases #database_design #exam_prep #multiple_choice
