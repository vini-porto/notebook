Covers:

- [[Week 1 - Introduction to Database Design]]
- [[Week 2 - The Database Mission]]
- [[Week 3 - Analyzing Existing Databases]]

> [!tip] How to use this for an open-book test Open-book tests rarely ask you to recite a definition. They ask you to **apply** it: spot the bad mission statement, fix the objective, sort the nouns, refine the list. Answer each question yourself first, then open the collapsed answer. For every question, note _where_ in the lecture the answer lives so you can find it fast during the test (see the Quick-Find Index at the bottom).

---

## Part A — Week 1: Introduction to Database Design

### A1. Multiple choice

**1.** An e-commerce site stores every new order the moment a customer clicks "Buy". This database is best described as: a) Analytical / OLAP b) Operational / OLTP c) Embedded / personal d) Materialized view

> [!success]- Answer **b) Operational / OLTP.** It stores moment-to-moment updates and is constantly read from and written to.

**2.** Which statement about analytical databases is TRUE? a) Data changes constantly b) Writes are much more frequent than reads c) They often store historical snapshots pulled from an operational database d) They are the main focus of this course

> [!success]- Answer **c).** Analytical (OLAP) databases store historical data, often snapshots from operational databases; values rarely change and reads far outnumber writes. The course mostly focuses on operational design.

**3.** "If the power fails right after a transaction completes, the transaction's changes are still there when the system restarts." Which ACID property is this? a) Atomicity b) Consistency c) Isolation d) Durability

> [!success]- Answer **d) Durability**, usually achieved by writing to disk.

**4.** A bank transfer debits one account but crashes before crediting the other; the database rolls back the debit. Which ACID property is being honored? a) Atomicity b) Consistency c) Isolation d) Durability

> [!success]- Answer **a) Atomicity** — the operation completes fully or fails as a whole, never halfway.

**5.** Which of the following is an example of an embedded/personal database? a) PostgreSQL b) Oracle c) SQLite d) MySQL

> [!success]- Answer **c) SQLite** (also H2, MS SQL Express). The others are client/server RDBMSs.

**6.** Which is the most common relationship type? a) One-to-one b) One-to-many c) Many-to-many d) Self-referencing

> [!success]- Answer **b) One-to-many.**

**7.** A many-to-many relationship always requires: a) A composite foreign key in one of the two tables b) A view c) A third join (linking/associative) table d) A validation table

> [!success]- Answer **c).** The linking table's primary key is the combination of two foreign keys, one pointing to each side.

**8.** Which type of integrity guarantees that no two rows share the same primary key value? a) Entity b) Domain c) Referential d) Business rules

> [!success]- Answer **a) Entity (table-level) integrity.**

**9.** A rule that says "every value in the Price column must be a positive decimal" enforces: a) Entity integrity b) Domain integrity c) Referential integrity d) Participation degree

> [!success]- Answer **b) Domain (field-level) integrity** — data type plus additional constraints.

**10.** In a field specification, "data type, length, and display format" belong to which group? a) General b) Physical c) Logical d) Relational

> [!success]- Answer **b) Physical.** General = name, description, parent table. Logical = required?, range, default value.

### A2. True / False (justify each answer)

**11.** Null means the same thing as zero.

> [!success]- Answer **False.** Null represents a missing, unknown, or optional value — not zero and not an empty string.

**12.** A key and an index are the same thing.

> [!success]- Answer **False.** A key uniquely identifies records (logical); an index is a physical structure that speeds up finding them.

**13.** A standard (non-materialized) view stores its own copy of the data.

> [!success]- Answer **False.** A view is a virtual table; it doesn't store data (though the RDBMS may cache results). Materialized/indexed views are the exception — they _do_ store data.

**14.** "Relation" and "relationship" are two words for the same concept.

> [!success]- Answer **False.** A relation is a table with the eight properties. A relationship is a connection between two relations via matching values in a shared field.

**15.** The order of rows in a relation matters.

> [!success]- Answer **False.** Property 7: row order doesn't matter (and property 6: column order doesn't matter either).

**16.** Good relational design instincts transfer directly to NoSQL design.

> [!success]- Answer **False.** In several respects they're the opposite: NoSQL keeps related data together and avoids joins.

**17.** You must understand set theory and predicate logic to design a relational database.

> [!success]- Answer **False.** The theory guarantees predictable behavior, but the methodology packages it into practical steps.

**18.** A record may contain null in some fields and still be a complete record.

> [!success]- Answer **True.** A record includes every field the table defines, even if some hold null.

### A3. Short answer

**19.** Who conceived the relational model, and when?

> [!success]- Answer Dr. Edgar F. Codd, 1969 (paper published 1970).

**20.** Name the two main advantages of relational databases.

> [!success]- Answer Flexible querying through SQL, and built-in integrity (the database enforces validity rules).

**21.** Explain why relational databases struggle with very large volumes of data, and how NoSQL responds.

> [!success]- Answer Vertical scaling (better hardware) has limits; horizontal scaling (many machines) is hard, and maintaining ACID across machines limits operations per second. NoSQL handles bigger volumes by **loosening data integrity guarantees**, keeping related data together, and avoiding joins.

**22.** List the three phases of traditional database design and describe normalization in one sentence.

> [!success]- Answer
> 
> 1. Requirements analysis, 2) Data modeling, 3) Normalization. Normalization breaks large tables into smaller single-theme tables and tests them against rules called _normal forms_ to eliminate modification problems.

**23.** What two questions is Hernandez's methodology built around?

> [!success]- Answer Can we identify the characteristics of a properly normalized table? Can we use those characteristics as a template for every table we build during design? (Answer to both: yes.)

**24.** List three consequences of improper design and four benefits of good design.

> [!success]- Answer **Improper:** inaccurate data entry, difficulty retrieving accurate information, costly business mistakes. **Good:** accurate storage/retrieval, more efficient storage, easier maintenance, easier application development.

**25.** Explain the house/blueprint analogy.

> [!success]- Answer You don't frame walls before having a blueprint; likewise you define the _logical_ design (needs, shape of data) before the _physical_ implementation.

**26.** List the eight properties of a relation.

> [!success]- Answer
> 
> 1. Each row = one entity instance
> 2. Each column = one attribute
> 3. Each cell = a single value
> 4. All values in a column share the same domain/data type
> 5. Each column has a unique name
> 6. Column order doesn't matter
> 7. Row order doesn't matter
> 8. No two rows are identical

**27.** Why should you avoid nulls where possible?

> [!success]- Answer Any math/logic expression touching a null returns null, and nulls behave oddly inside aggregate functions.

**28.** Distinguish a data table from a validation table and give an example of each.

> [!success]- Answer **Data table:** stores data to supply information; changes often (e.g., Orders). **Validation (lookup/category) table:** stores data used to implement integrity; rarely changes; often feeds a UI dropdown (e.g., Provinces, Status codes).

**29.** Give three uses of a view.

> [!success]- Answer Consolidate data from multiple tables into one report; control which users/roles see which information; implement integrity through a validation view.

**30.** Distinguish participation type from participation degree.

> [!success]- Answer **Type:** whether participation is mandatory (a record in A must exist before adding to B) or optional. **Degree:** min and max number of related records in B for each record in A (e.g., 1 to 4).

### A4. Application / scenario

**31.** Identify and name the design problem in each field: a) `Address` = "45 King St, Sault Ste. Marie, ON, P6A 1A1" b) `Phone Numbers` = "705-555-1234, 705-555-9876" c) `Age` stored alongside `Birth Date` d) `Order Total` stored alongside the order line prices

> [!success]- Answer a) **Multipart field** — several distinct facts in one value. b) **Multivalue field** — multiple instances of the same kind of value. c) **Calculated field** — derivable from Birth Date. d) **Calculated field** — derivable from the line prices.

**32.** Classify each table as an _object_ table or an _event_ table: Employees, Appointments, Vehicles, Purchase Orders, Buildings, Bank Transactions.

> [!success]- Answer **Object:** Employees, Vehicles, Buildings. **Event:** Appointments, Purchase Orders, Bank Transactions.

**33.** For each pair, give the relationship type and say where the foreign key goes: a) Agents and Entertainers (one agent represents several entertainers) b) Employees and Compensation (each employee has exactly one compensation record) c) Students and Classes

> [!success]- Answer a) **One-to-many.** FK in Entertainers (the "many"/child side) → Agents PK. b) **One-to-one.** FK in the child (Compensation) → Employees PK. c) **Many-to-many.** Needs a linking table (e.g., Student Schedule) whose PK is the combination of the Students FK and the Classes FK.

**34.** An Orders table has a `CustomerID` FK. Someone tries to delete Customer 17, who still has orders. What should the database do, and which integrity type is at stake?

> [!success]- Answer It should **prevent the deletion**, because an FK still refers to that PK. This is **referential (relationship-level) integrity**.

**35.** A company rule says "a sales rep may manage at most 20 clients." Which of the four kinds of integrity is this, and what relationship property does it describe?

> [!success]- Answer A **business rule**. It sets the **participation degree** (max 20 clients per rep).

**36.** Place each term in its terminology group (value / structure / relationship / integrity): Null, Index, Many-to-many, Field specification, View, Participation type, Information, Referential integrity.

> [!success]- Answer **Value:** Null, Information. **Structure:** Index, View. **Relationship:** Many-to-many, Participation type. **Integrity:** Field specification, Referential integrity.

**37.** Match the equivalent terms: Table / Relation / File / Entity with Row / Tuple / Record / Entity instance and Column / Attribute / Field.

> [!success]- Answer Table–Row–Column; Relation–Tuple–Attribute; File–Record–Field; Entity–Entity instance–Attribute.

---

## Part B — Week 2: The Database Mission

### B1. Multiple choice

**38.** Which is step 4 of Hernandez's seven-step methodology? a) Determine business rules b) Establish relationships c) Choose data structures d) Define views

> [!success]- Answer **b) Establish relationships.** Full order: (1) mission statement & objectives, (2) analyze existing databases, (3) choose data structures, (4) establish relationships, (5) determine business rules, (6) define views, (7) review data integrity.

**39.** Which step produces the initial field list? a) Step 1 b) Step 2 c) Step 3 d) Step 7

> [!success]- Answer **b) Step 2 — Analyze existing database(s).**

**40.** In which step do you assign a primary key to each table and complete field specifications? a) Step 2 b) Step 3 c) Step 4 d) Step 5

> [!success]- Answer **b) Step 3 — Choose data structures.**

**41.** Building a database for your own personal use involves which interview category? a) External b) Internal c) Self d) None

> [!success]- Answer **c) Self.** External = another organization; internal = your own organization.

**42.** Which is the BEST mission objective? a) "Keep track of customers, their orders, and the products in each order" b) "Keep track of vehicle maintenance history" c) "When a customer calls, look up their file, then update their address" d) "Maintain the data needed to support our sales operations"

> [!success]- Answer **b).** (a) has multiple tasks; (c) describes a process; (d) reads like a mission statement.

### B2. True / False (justify)

**43.** A good mission statement should list the specific tasks the database will perform.

> [!success]- Answer **False.** It should be short, focused, and free of specific tasks. Specific tasks are removed but documented separately — they're likely mission objectives.

**44.** Business rules are fully determined in a single interview during step 5.

> [!success]- Answer **False.** Establishing business rules is ongoing and iterative; rules surface throughout the whole design process.

**45.** Once step 7 is done, the design is finished permanently.

> [!success]- Answer **False.** "Design complete" is a moving target — organizations evolve, so the design will need updating.

**46.** Users and management should always be interviewed separately.

> [!success]- Answer **False (as stated).** Separate is preferred in general (managers can make users less open), but for **mission objectives** they can be interviewed together because there's usually enough shared understanding of general tasks. Only combine them otherwise for a specific reason.

**47.** Closed questions should be the default in design interviews.

> [!success]- Answer **False.** Prefer open-ended questions; use closed (yes/no, multiple-choice) only with a specific reason.

**48.** A database's integrity is proportional to how thoroughly the design process was followed.

> [!success]- Answer **True.**

### B3. Short answer

**49.** Define _database mission statement_ and state its two purposes.

> [!success]- Answer A statement of the specific purpose of the database in general terms. It provides focus and direction throughout design and keeps the design from growing unnecessarily large or complex.

**50.** List the three qualities of a good mission statement.

> [!success]- Answer Short and focused; clear about the general purpose; free of specific tasks.

**51.** Define _mission objective_ and list the four qualities of a good one.

> [!success]- Answer A statement describing **one** general task supported by the database's data; it guides later decisions and helps verify the final design supports the mission. Qualities: defines a single task, unambiguous, easy to understand, simple statement (no process).

**52.** Describe the three steps for composing a mission statement.

> [!success]- Answer
> 
> 1. Interview the database owner with high-level, open-ended questions. 2) Review the interview to draft a proposed statement, noting specific tasks (future objectives). 3) Review with the owner — both must agree before moving on.

**53.** Describe the three steps for composing mission objectives.

> [!success]- Answer
> 
> 1. Interview users and management (open-ended questions, follow-ups; uncover workflows, reports, bottlenecks). 2) Review material to determine explicit and implicit objectives. 3) Store the list in a document for reference.

**54.** Why is the methodology described as reducing iteration rather than eliminating it?

> [!success]- Answer Design is inherently iterative, the target moves as you learn, and there are unknowns early on. The methodology reduces iteration and surfaces unknowns sooner; it isn't perfect but beats having none.

**55.** Name the four review activities of step 7 and the integrity type each checks.

> [!success]- Answer Review each table (entity integrity); each field specification (domain integrity); each relationship (referential integrity); business rules (confirm and add new constraints).

**56.** List the interviewer guidelines covered in the lecture.

> [!success]- Answer Be prepared (goal + questions, prefer open-ended); make participants comfortable; make them feel valued; pay attention to atmosphere (physical and psychological); assign group leaders for larger groups; take thorough notes; maintain control of the interview.

**57.** Why interview managers and users separately? What does each group know?

> [!success]- Answer Managers have a general perspective on data needs; users know the specific day-to-day requirements. A manager in the room can make users less willing to be open.

**58.** What is a group leader's role, and what should they NOT become?

> [!success]- Answer Main contact between interviewer and group: prepares members on goals, gathers information, directs questions to the right person. They should not become the **only** contributor; if they dominate, handle it diplomatically.

**59.** What disputes should the interviewer arbitrate, and which should they defer?

> [!success]- Answer Arbitrate disputes about information needs (objectively, in the design's best interest). Defer disputes about anything other than the database to another time and place.

**60.** Besides record-keeping, what is one extra benefit of a dedicated note-taker?

> [!success]- Answer It can invite participation from naturally reserved people.

### B4. Application / scenario

**61.** Critique and rewrite this mission statement: _"The purpose of the Northside Library database is to track book loans, record member fines, keep a list of staff, schedule events, and manage room bookings."_

> [!success]- Answer **Problem:** it's a list of tasks — too long, too specific, not focused on one idea (like the bad Whatcom County example). **Rewrite (example):** "The purpose of the Northside Library database is to maintain the data the library needs to support its lending and community services." The removed tasks (track loans, record fines, etc.) should be kept separately as candidate **mission objectives**.

**62.** Fix this objective: _"Keep track of our instructors and the courses they are certified to teach, and the class sessions we schedule for them."_

> [!success]- Answer It packs multiple tasks and extra detail into one statement. Split:
> 
> 1. Maintain complete instructor information
> 2. Keep track of all class sessions we schedule (Certifications are details of instructors — confirm in later interviews whether they need their own objective.)

**63.** A receptionist at a dental clinic says: _"I answer calls, book patients with a dentist, and when they arrive I pull up their chart and collect their insurance card."_ List explicit and implicit objectives.

> [!success]- Answer **Explicit (examples):** Keep track of patient appointments; Maintain patient chart information; Maintain patient insurance information. **Implicit (examples):** Maintain complete patient information; Maintain complete dentist information. (Wording can vary — each must be a single, simple task.)

**64.** Write three open-ended questions you'd ask a database owner to determine the mission.

> [!success]- Answer Any from the lecture, e.g.: "How would you describe the purpose of your organization to a new client?" / "What is the major function of your organization?" / "Why do you believe you need a database?" / "What problem would you like to solve using this database?"

**65.** In the Mike's Bikes case, what did Mike say was his business's most important function, and what mission statement came from it?

> [!success]- Answer "To provide a wide array of bicycle products and bicycle-related services to our customers." → _"The purpose of the Mike's Bikes database is to maintain the data we need to support our bike sales and customer service operations."_

**66.** List the five Mike's Bikes objectives. Which two came from follow-up questions rather than Mike's first answer?

> [!success]- Answer Maintain complete inventory / customer / supplier / employee information; Track all customer sales. **Supplier** (from "anything else related?") and **employee** (from "do you have sales reps?") came from follow-ups.

**67.** A sales-team interview drifts into a heated argument about office parking spaces. What do you do?

> [!success]- Answer Maintain control: redirect quickly to the interview's purpose, and ask that the non-database dispute be deferred to a different time and place.

---

## Part C — Week 3: Analyzing Existing Databases

### C1. Multiple choice

**68.** An organization says "we don't have a database — Pat just remembers everything." This is a: a) Paper-based database b) Digital database c) Human knowledge base d) Embedded database

> [!success]- Answer **c) Human knowledge base.** Problem: nobody else can see or check it; the data must be extracted through interviews.

**69.** Which is NOT one of the three activities of analyzing an existing database? a) Analyze how data is collected b) Analyze how data is presented c) Interview users and managers d) Assign primary keys

> [!success]- Answer **d).** That's step 3.

**70.** Which of these counts as a report? a) A form email "Your order {number} has shipped" b) A manager's morning dashboard screen c) A slide show of monthly sales d) All of the above

> [!success]- Answer **d).** A report is any document, paper or digital, that presents information generated from data.

**71.** Which is interviewed first? a) Managers b) Users c) The owner d) Suppliers

> [!success]- Answer **b) Users**, then managers — so you understand users' needs before hearing management's perspective.

**72.** Which information requirement is discussed with managers but not with users? a) Current b) Additional c) Future d) Overall

> [!success]- Answer **d) Overall** — to catch anything no previous interview revealed.

**73.** Which word most strongly signals a calculated field? a) Category b) Subtotal c) Description d) Designation

> [!success]- Answer **b) Subtotal.** Watch for total, sum, average, minimum, largest, count.

### C2. True / False (justify)

**74.** You should use the existing database's structure as the starting point for the new design.

> [!success]- Answer **False.** If it had no problems, nobody would want a new one — copying it copies its flaws. Use it to _learn_ about needs, fields, tables, deficiencies, and workflows.

**75.** When collecting samples, you only need the typical case.

> [!success]- Answer **False.** Collect typical samples **and** note exceptions (e.g., a hand-modified form with a second phone number) — exceptions often reveal data the system forgot.

**76.** The Subject List and Characteristic List can be combined into one document to save time.

> [!success]- Answer **False.** They must be separate; both help identify tables later and may point to slightly different sets of tables.

**77.** The preliminary field list is complete once the interviews are done.

> [!success]- Answer **False.** Lists are preliminary and will evolve; you've likely missed items.

**78.** A value list must always be a fixed set of words.

> [!success]- Answer **False.** It can be defined by a rule, e.g., Sales Rep = every employee whose position is sales rep.

### C3. Short answer

**79.** State the five questions the analysis of an existing database tries to answer.

> [!success]- Answer What types of data are recorded? How is data viewed and used? How is it managed and maintained? Are there deficiencies? How must things evolve to support information needs?

**80.** Compare the three kinds of existing database and the typical problem of each.

> [!success]- Answer **Human knowledge base** — in people's heads; nobody else can see/check it. **Paper-based** — notes, forms, printouts; prone to inconsistencies and errors. **Digital** — spreadsheets, apps; more structured but often has design deficiencies.

**81.** List five paper-based and five digital data sources.

> [!success]- Answer **Paper:** index cards, forms, hand-written notes, notebooks/binders, paper filing system. **Digital:** digital files/forms/documents, spreadsheets, databases, computer programs, mobile apps, web apps.

**82.** Define subject and characteristic, and explain how to tell them apart in speech.

> [!success]- Answer **Subject:** a person, place, thing, or event the database is about (tends to become a table). **Characteristic:** describes an aspect of a subject (tends to become a field). Both are usually nouns; a subject stands on its own, a characteristic belongs _to_ a subject — often following a possessive ("the client's name").

**83.** Describe the three steps of the subject identification technique, including what to filter.

> [!success]- Answer
> 
> 1. Analyze responses to open-ended questions for nouns naming a person/place/thing/event. 2) Compile a subject list. 3) Use it as the basis for further questions, while ignoring duplicates/synonyms, setting aside characteristics for later, and ignoring items irrelevant to the mission/objectives.

**84.** Describe the five steps of the characteristic identification technique.

> [!success]- Answer
> 
> 1. Pick a subject and discuss it. 2) Use subject identification to find new subjects. 3) Look for characteristics (nouns, often after a possessive). 4) Compile a characteristic list. 5) Repeat for each subject.

**85.** State the three purposes of interviewing users.

> [!success]- Answer Identify type and usage patterns of data; provide detail on collected samples; identify information required for daily work.

**86.** When reviewing samples with users, what three things do you do?

> [!success]- Answer Discuss how the objects represented by the samples are used; clarify anything unclear without assuming (e.g., ask what acronyms mean); attach a short description of each sample's purpose and use.

**87.** Explain current, additional, and future information requirements.

> [!success]- Answer **Current:** reports used now — find the data's origin and who maintains it (follow up with them if not the user). **Additional:** more information than they get now (new reports or additions). **Future:** what may be needed as the organization evolves — speculative, may involve sketching new reports/forms. Update the lists throughout.

**88.** Why are managers better sources for future requirements than users?

> [!success]- Answer They're more likely to understand the organization's direction and future needs.

**89.** List the four properties of a true characteristic.

> [!success]- Answer Describes an aspect of a subject; is a component/detail/piece of a subject; usually singular; usually can't be broken into smaller pieces.

**90.** What are the two steps for creating the preliminary field list?

> [!success]- Answer
> 
> 1. Review and refine the Characteristic List (its refined version = first Preliminary Field List). 2) Review samples for new characteristics: highlight each, cross out those already listed (matching synonyms like "Qty" = "Quantity on hand"), add the rest.

**91.** What do you do with calculated fields, and why?

> [!success]- Answer Remove them from the Preliminary Field List and add them to a separate Calculated Field List — they can always be computed from other fields, so they needn't be stored.

**92.** Give four organization tips for managing lists and samples.

> [!success]- Answer Use a tool like OneNote; date updates; archive older copies of lists; use folders.

### C4. Application / scenario

**93.** Apply the subject identification technique: _"I manage the parts counter at an auto shop. When a mechanic needs a part, they give me a work order. I check the inventory, and if we're out, I order it from a supplier and note the delivery date."_

> [!success]- Answer Raw subjects: **parts counter, auto shop, mechanic, part, work order, inventory, supplier, delivery date** (delivery date may really be a characteristic — set aside). Follow-ups: is "part" a synonym of "inventory"? Is "parts counter" or "auto shop" irrelevant (a place/single shop)? Confirm with the participant rather than deleting on a guess.

**94.** Apply the characteristic identification technique: _Interviewer: "Tell me about suppliers." Participant: "We keep the supplier's company name, contact person, phone number, and email. And the contact's cell number for emergencies."_

> [!success]- Answer Supplier company name, Supplier contact name, Supplier phone number, Supplier email address, Contact cell number. Follow-up: "contact person" has its own details (cell number), so **Contact** might be a new subject — consider adding it to the Subject List.

**95.** Refine this raw characteristic list from a gym's interviews. State the action and reason for each: `Member Name, Name, Membership Type, Member Phone, Phone, Trainer Name, Trainer Phone, Plan #, Plan Number, Monthly Fee, Annual Total, Locker` (Assume "Name" and "Phone" came from a talk about trainers.)

> [!success]- Answer
> 
> - **Name** → same characteristic already listed as Trainer Name → remove.
> - **Phone** → same as Trainer Phone → remove.
> - **Plan # / Plan Number** → different names, same characteristic → keep one (e.g., Plan Number).
> - **Annual Total** → calculated (12 × Monthly Fee) → move to Calculated Field List.
> - **Locker** → may have its own details (number, location, status) → likely a subject; ask and move to Subject List if so.
> - **Membership Type** → keep; good candidate for a **value list**.
> - Member Name, Member Phone, Trainer Name, Trainer Phone, Monthly Fee → keep.

**96.** A user says: _"Every Monday I get a sales summary by region. Someone in accounting puts it together."_ Classify the requirement and state your next steps.

> [!success]- Answer **Current information requirement**, based on data the user doesn't control. Next: find the data's origin (who in accounting, where it's entered), note that person for a follow-up interview, collect a sample report, attach its description, and run characteristic identification on it.

**97.** During an interview a staff member says, _"It would be great if the customer list also showed each customer's birthday so we can send discounts."_ What kind of requirement is this and what do you update?

> [!success]- Answer **Additional information requirement.** Add "Customer birth date" to the Characteristic List (and any new subjects/reports to the respective lists/samples) — similar to the Mike's Bikes sticky note asking for supplier email.

**98.** In the Mike's Bikes Product Information spreadsheet, some Category and Qty On Hand cells are blank. What should you do and why?

> [!success]- Answer Treat them as **exceptions** and ask in the interview — don't assume. A blank Category could mean it was forgotten or no category fits; a blank Qty On Hand could mean zero stock or not tracked. Category is also a value-list candidate (Accessories, Bikes, Clothing, Components, Maintenance, Racks, Wheels).

**99.** Compare the Mike's Bikes lists dated 02/13/20 and 02/16/20. Identify two refinement actions that were applied.

> [!success]- Answer
> 
> 1. Generic names were **renamed to disambiguate** (Name → Employee Name / Customer Name; Address → Employee Address / Customer Address). 2) Computable values were **moved to the Calculated Field List** (Discount Amount, Grand Total, Item Total, Subtotal). Also: lists were **dated**, following the organization advice.

**100.** Give an example of a noun that could be a subject in one organization and a characteristic in another, and explain why the lists must be kept separate.

> [!success]- Answer "Address": a characteristic for most businesses (Customer address), but a subject for a land-use office where an address has zoning, owner, and history. Separate lists force you to decide the noun's role for _this_ organization, and let you move items between lists during refinement.

---

## Part D — Cross-Week Integration

**101.** Trace the Mike's Bikes case across all three weeks: what was produced in Week 2 and what was produced in Week 3?

> [!success]- Answer **Week 2 (Step 1):** mission statement + five mission objectives, confirmed with Mike. **Week 3 (Step 2):** samples (customer index card, supplier phone list, product spreadsheet, bike sales summary), Subject List, Characteristic List, then a Preliminary Field List and Calculated Field List.

**102.** Week 1 names three poorly designed field types. Where in Weeks 2–3 does the methodology deal with each?

> [!success]- Answer **Calculated fields:** Step 2 (Week 3) — moved to the Calculated Field List. **Multipart and multivalue fields:** Step 3 (Choose data structures, previewed in Week 2) — refined so each field stores a single value. Also, "a characteristic usually cannot be broken down into smaller pieces" (Week 3) guards against multipart fields early.

**103.** Subjects tend to become ___, and characteristics tend to become ___. Which Week 1 terms do these correspond to?

> [!success]- Answer Tables; fields. Week 1: relation/table (object or event subject) and field/attribute.

**104.** How does a Week 3 value list relate to Week 1 concepts?

> [!success]- Answer A value list is the set of allowed values for a field — it supports **domain integrity** and will often be implemented as a **validation (lookup) table**, which may supply a UI dropdown (Step 5 creates validation tables for business rules).

**105.** Explain how interviews run through the entire methodology, naming at least four steps that rely on them.

> [!success]- Answer Step 1 (owner for mission; users/management for objectives), Step 2 (users then managers about samples and requirements), Step 3 (completing field specifications), Step 4 (relationships and referential integrity), Step 5 (business rules), Step 6 (how users work with data → views). Step 7 also reviews rules added since the last interviews.

**106.** Why does the lecture distinguish _data_ from _information_, and where does this matter in Week 3?

> [!success]- Answer Data = raw stored values; information = processed, meaningful data. In Week 3, **reports** present information generated from data — analyzing reports shows what information the organization needs, which reveals the data that must be stored.

**107.** Mini case (long answer): A small dog-grooming business keeps appointments in a paper binder, the owner remembers which dogs are aggressive, and a spreadsheet tracks shampoo stock. Plan steps 1 and 2: draft a mission statement, three objectives, the samples you'd collect, the interview order, and one likely calculated field.

> [!success]- Answer **Mission (example):** "The purpose of the Pawfect Grooming database is to maintain the data we need to support our grooming services and daily operations." **Objectives (examples):** Maintain complete customer information; Maintain complete pet information; Keep track of all grooming appointments; Maintain complete supply inventory information. **Existing databases:** paper-based (binder), human knowledge base (owner's memory of aggressive dogs), digital (spreadsheet). **Samples:** typical binder pages + exceptions (margin notes), the spreadsheet, any receipts/reminder texts (reports). **Order:** users (groomers, front desk) first, then the owner/manager — ask about current, additional, future, and overall requirements. **Calculated field:** e.g., Appointment total / Invoice total, or Stock value (qty × unit cost). Also: record a value list like Pet size (Small, Medium, Large) or Service type.

---

## Quick-Find Index (for the open book)

|If the question is about…|Look in|
|---|---|
|OLTP vs OLAP|Week 1 → Two kinds of database|
|ACID|Week 1 → Relational databases (callout)|
|NoSQL trade-offs|Week 1 → Where relational databases struggle / NoSQL|
|RDBMS vs embedded|Week 1 → Other kinds of database software|
|Good vs bad design|Week 1 → Why bother with database design?|
|Three design phases, normalization|Week 1 → The design methodology|
|Eight properties of a relation|Week 1 → Structure-related terms|
|Multipart / multivalue / calculated fields|Week 1 → Poorly designed fields (warning callout)|
|PK / FK / index / view|Week 1 → Structure-related terms|
|Relationship types, participation|Week 1 → Relationship-related terms|
|Four kinds of integrity, field specification|Week 1 → Integrity-related terms|
|Seven steps|Week 2 → The seven-step methodology|
|Interview guidelines|Week 2 → Conducting interviews|
|Mission statement (good/bad, questions)|Week 2 → Defining the mission statement|
|Mission objectives, explicit vs implicit|Week 2 → Defining mission objectives|
|Mike's Bikes (mission & objectives)|Week 2 → Case study sections|
|Three kinds of existing database|Week 3 → There is always a database of some kind|
|Data collected vs presented, reports|Week 3 → Analyzing how data is collected / presented|
|Subject & characteristic techniques|Week 3 → Subject / Characteristic identification technique|
|User interviews, current/additional/future|Week 3 → Interviewing users|
|Manager interviews, overall requirements|Week 3 → Interviewing managers|
|Refining the list (remove/rename/merge/move)|Week 3 → Review and refine the Characteristic List (table)|
|Value lists, calculated fields|Week 3 → Value lists / Calculated fields|
|Mike's Bikes (samples & field lists)|Week 3 → Case study: Mike's Bikes|
|Worked examples|Week 3 → Exercises 1–7|

## Tags

#CSD216 #databases #database_design #exam_prep #practice_questions