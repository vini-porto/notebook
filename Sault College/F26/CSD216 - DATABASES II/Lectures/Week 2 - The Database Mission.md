Recommended reading: *Database Design for Mere Mortals*, Chapters 4-5.

By the end of this lesson, you should be able to:
- Describe the purpose of the main steps in Hernandez' design methodology
- Explain the purpose of interviews in good database design
- Describe the qualities of a good database mission statement
- Describe the purpose of database objectives
- Compose a database mission statement for a given system
- Determine a list of database mission objectives for a given system

This lesson picks up where [[Week 1 - Introduction to Database Design]] left off, moving from *why* database design matters into *how* the design methodology actually works, step by step.

## Starting a database design

Before touching a single table, you need to define the end result you're aiming for. That comes down to two things:

- The **database mission**, the purpose of the database
- The **mission objectives**, a list of tasks users will need to perform with the data

Together, the mission and its objectives give the whole design process focus and direction. Without them, it's easy to lose track of what the database is even for.

## The seven-step methodology

Hernandez's methodology breaks database design into seven steps:

1. Define mission statement and objectives
2. Analyze existing database(s)
3. Choose data structures
4. Establish relationships
5. Determine business rules
6. Define views
7. Review data integrity

> [!important] Why finishing the process matters
> Good database design is genuinely hard: it's iterative, the target keeps moving as you learn more, and there are plenty of unknowns early on. Following the methodology helps reduce that iteration and surface the unknowns sooner. A database's integrity ends up proportional to how thoroughly the design process was followed.
>
> The methodology isn't perfect, but it's a lot better than designing without one.

Since this lesson focuses on step 1, here's a quick look at what the other six steps involve, before we go deeper into mission statements and objectives.

### 2. Analyze existing databases

Look at whatever the organization is already using, whether that's paper records or an existing digital system, to understand how the organization collects data, presents data, and works with data through its current applications (desktop or web). This step relies heavily on interviewing users and managers to find out how they interact with the data and what their information requirements are.

> [!warning] Ask the right questions
> Thorough interviewing is one of the most important parts of database design. It's what unveils the actual information requirements. If you don't ask the right questions, you risk missing a requirement entirely.

The output of this step is an **initial field list**: the fields the gathered information points to, with calculated fields set aside into a separate list for later. This list gets verified with users and management, and it's only a starting point, since it will keep being updated as design continues.

### 3. Choose data structures

Turn the mission objectives and gathered information into actual **tables**, assigning fields so that each table represents only one subject and no field is duplicated across tables. From there:

- Refine multipart and multivalue fields so each field stores only a single value
- Remove any field that doesn't represent a distinct aspect of the table's subject
- Verify the table structure is complete and correct
- Assign a primary key to each table
- Complete a full field specification for every field, through further interviews if needed
- Review the resulting table structure and field specifications with users and management

### 4. Establish relationships

More interviews, this time to identify relationships between tables, their characteristics, and the referential integrity they need. You establish the logical connection between tables using primary keys, foreign keys, and linking tables where needed, and you determine the type and degree of participation for each relationship based on data requirements and business rules.

### 5. Determine business rules

Yet more interviews, now aimed at the constraints and rules an organization imposes on its data, data structures, and relationships. These get documented as business rules, and validation tables get created to support them where needed.

> [!note] Business rules keep changing
> Establishing business rules is an ongoing, iterative process. It requires continuous, attentive communication with users and management, since rules surface throughout the whole design process, not just in one interview session.

### 6. Define views

Find out how different users actually work with the data. Different users need different things from the same database, and this step defines views in terms of the specific data, drawn from various tables and fields, that each of them requires.

### 7. Review data integrity

A final pass over everything you've built so far:

- Review each table for proper design: verify each field's structure, resolve any inconsistencies, and check table-level (entity) integrity
- Review each field specification, making refinements and checking field-level (domain) integrity
- Review each relationship, confirming its type and participation characteristics and checking relationship-level (referential) integrity
- Review the business rules, confirming constraints and adding anything new that's come up since the last round of interviews

> [!note] "Design complete" is a moving target
> Once the design process wraps up, the database is ready for a physical implementation in an RDBMS. But organizations keep evolving, and data needs change along with them, so the design will need updating over time. Treat this less like a finish line and more like the current best version.

## Conducting interviews

Since interviews run through nearly every step of the methodology, it's worth understanding how to run them well before diving into any specific step.

> [!important] Why interviews matter
> Interviewing is a key aspect of database design. It's the communication link between database owners and developers, and it's what unveils the database's real requirements and clarifies uncertainty in data needs.

Who you interview depends on the size of the organization: users, managers, the organization's owner, or, in a small enough setup, all of the above might just be the same person. Interviews themselves fall into three categories:

- **External**, when another organization needs a database built for them
- **Internal**, when your own organization needs a database
- **Self**, when you're building a database for yourself

### Interviewer guidelines

> [!tip] Be prepared
> Go into every interview with a specific goal in mind, and prepare questions that give the conversation focus and direction. Prefer open-ended questions: they give participants the freedom to raise what they think matters, and they tend to surface more information. Save closed questions (yes/no, multiple-choice) for when you have a specific reason to ask one.

> [!tip] Make participants comfortable and make them feel valued
> Before the interview starts, make sure participants understand its goals, its format and length, and who else will be there. They should feel confident they're not being evaluated on performance and that they can speak openly, and they need to trust the interviewer's sincerity.
>
> Beyond that, remember that each user's input actually shapes the database design. Users know how they want to use the data, so the interviewer has to genuinely understand their needs, stay attentive and interested in what they say, and show them in follow-up interviews how their contributions shaped the design.

> [!tip] Pay attention to the atmosphere
> Physically, that means good lighting, comfortable seating, plenty of workspace, and possibly a location away from the daily workplace, with refreshments if it makes sense.
>
> Psychologically, that means keeping the group small, and interviewing managers and users separately where possible. Managers tend to have a general perspective on data needs, while users know the specific, day-to-day information requirements, and having a manager in the room can make some people less willing to be open. Only combine both groups in one interview if there's a specific reason to.

> [!tip] Assign group leaders for larger groups
> A group leader acts as the main point of contact between the interviewer and the group: preparing members on the interview's goals, gathering information from the group, and directing questions to whoever's best placed to answer them. A group leader should not become the only person contributing information during the interview, and if they tend to take over, handling that diplomatically is part of the job.

> [!tip] Take thorough notes
> Consider recording interviews with permission, or assigning a dedicated note-taker. A note-taker can also be a good way to invite participation from people who are naturally more reserved.

> [!tip] Maintain control of the interview
> Keep the discussion focused on that interview's specific purpose, and redirect it quickly if it drifts into irrelevant territory. Before the interview starts, make it clear you'll arbitrate any disputes about information needs, since different users may genuinely disagree about how data should be used, and commit to deciding objectively in the best interests of the final design.
>
> Disputes about anything other than the database itself are not yours to arbitrate. Ask that those be deferred to a different time and place.

## Defining the mission statement

> [!note] Definition
> A **database mission statement** states the specific purpose of the database, in general terms. It provides focus and direction throughout the whole design process, and it keeps the design from growing unnecessarily large or complex.

A good mission statement is:
- Short and focused
- Clear about the database's general purpose
- Free of specific tasks

If a proposed mission statement includes specific tasks, remove them from the statement itself, but keep them documented separately. They're probably mission *objectives* rather than part of the mission.

> [!example] Bad vs. good mission statement
> **Bad:** "The purpose of Whatcom County Hearing Examiner's database is to keep track of applications for land use, maintain data on applicants, keep a record of all hearings, keep a record of all decisions, keep a record of all appeals, maintain data on department employees, and maintain data for general office use."
>
> This reads more like a list of tasks than a statement of purpose. It's too long, too specific, and doesn't focus on a single idea.
>
> **Good:** "The purpose of Whatcom County Hearing Examiner's database is to maintain the data used by the examiner's office to make decisions about land-use requests submitted by Whatcom County residents."
>
> This version states the general purpose clearly, without listing every individual task the database will support.

### Composing a mission statement

1. Interview the "owner" of the database (the person who can speak to its overall purpose) with high-level, open-ended questions about what the organization does and how it functions. Much of what comes up here will be useful later in the design process too.
2. Review the interview to draft a proposed mission statement, noting any specific tasks mentioned along the way, since these will likely become mission objectives.
3. Review the proposed statement with the database owner. Both the developer and the owner need to agree on it before moving on.

> [!example] Sample questions for determining the mission
> - How would you describe the purpose of your organization to a new client?
> - How would you describe what your organization does?
> - What is the major function of your organization?
> - What would you say is the main reason your organization exists?
> - Why do you believe you need a database?
> - What problem would you like to solve using this database?

### Case study: Mike's Bikes

Part of an initial interview with Mike, the owner of a bike shop:

> **Interviewer:** Can you tell me why you believe you need a database?
> **Mike:** I think we need a database just to keep track of all our inventory. I'd also like to keep track of all our sales as well.
>
> **Interviewer:** I'm sure the database will address those issues. Now, what would you say is the single most important function of your business?
> **Mike:** To provide a wide array of bicycle products and bicycle-related services to our customers. We have a lot of great customers, and regular ones too. They're our biggest asset.

From Mike's purpose statement, "to provide a wide array of bicycle products and bicycle-related services to our customers," the interviewer adapts a database mission statement:

> The purpose of the Mike's Bikes database is to maintain the data we need to support our bike sales and customer service operations.

That draft then goes back to Mike for review. Once he and the developer are both satisfied with it, the next step, mission objectives, can begin.

## Defining mission objectives

> [!note] Definition
> A **mission objective** is a statement that describes one general task supported by the data in a database. Objectives provide information that guides later design decisions, and they later help verify that the final design actually supports the mission statement.

A good mission objective:
- Clearly defines a *single* task
- Is unambiguous
- Is easy to understand
- Stays a simple statement, without describing a process

> [!example] Good mission objectives
> - Maintain complete patient address information
> - Keep track of all customer sales
> - Make sure sales representatives have at most 20 clients
> - Keep track of vehicle maintenance history
> - Produce an employee phone directory

> [!warning] A common mistake: cramming multiple tasks into one objective
> **Bad:** "Keep track of entertainers we represent and the type of entertainment they provide, as well as the engagements that we book for them."
>
> This packs several distinct tasks into a single statement, and it includes too much detail: the type of entertainment is really a characteristic of an entertainer, not a separate task.
>
> **Improved:**
> 1. Maintain complete entertainer information
> 2. Keep track of all the engagements we book
>
> Each statement now represents one task, is easy to understand, and avoids unnecessary detail.

### Composing mission objectives

1. Interview users and management, with the purpose of determining what tasks the database needs to support. Ask open-ended questions, let participants elaborate, and ask follow-ups. Try to uncover their workflows, the reports they need, and any existing problems or bottlenecks. Users and management can be interviewed together here, since there's usually enough shared understanding of general task requirements.
2. Review the interview material to determine the objectives themselves. Some will be **explicit**, stated directly by participants. Others will be **implicit**: you'll need to read between the lines and make reasonable assumptions based on what you gathered.
3. Store the resulting list in a document for future reference.

> [!example] Sample questions for eliciting objectives
> - What kind of work do you do daily?
> - How would you state your job description?
> - What kind of data do you work with?
> - What types of reports do you generate?
> - What types of things do you keep track of?
> - What types of services does your organization provide?
> - How would you describe the type of work you do?

> [!example] Explicit vs. implicit objectives
> A mechanic describes their job like this: "I try to determine the general problem with the vehicle. Then I fill out a work order and note my assessment of the problem. Finally, I send the vehicle to the next available service team."
>
> **Explicit objectives**, stated directly:
> - Maintain information on customer vehicles
> - Keep track of work orders
> - Maintain information on our service teams
>
> **Implicit objectives**, inferred from context:
> - Maintain information on our customers
> - Maintain information on our mechanics

### Case study: Mike's Bikes, continued

Part of the mission objective interview with Mike:

> **Interviewer:** What kinds of things would you like to track in the database?
> **Mike:** Mainly our inventory, our customers, and our sales.
>
> **Interviewer:** Anything else related to those subjects that you'd like to track?
> **Mike:** I guess to keep track of inventory we'll need to keep track of our suppliers.
>
> **Interviewer:** Do you have sales reps involved with your sales?
> **Mike:** Oh yeah! We definitely need to keep track of employee information.

From this, the explicit objectives for Mike's Bikes come out as:

- Maintain complete inventory information
- Maintain complete customer information
- Track all customer sales
- Maintain complete supplier information
- Maintain complete employee information

This list goes back to Mike to confirm it's complete, then gets stored for reference as the design process continues.

## Putting it together

The overall flow for step 1 looks like this: define the mission statement and objectives, starting with an interview of the database's owner, using that to compose the mission statement, then interviewing users and management to define the mission objectives.

## Summary

- You now know the high-level steps of the design methodology
- Interviews run through nearly the entire process, so conducting them well matters
- A database mission statement succinctly describes the general purpose of a database
- A mission objective clearly states a single task the database must support
- Interviews are what let you actually determine a database's mission statement and its complete set of objectives

See also [[Week 1 - Introduction to Database Design]] for the terminology and relational concepts this methodology builds on, and [[Software Engineering]] for how requirements-gathering through interviews compares to requirements analysis in general software projects.

Further reading:
- [Open-ended vs. closed-ended interview questions](https://www.nngroup.com/articles/open-ended-questions/)
- [Requirements elicitation techniques (overview)](https://en.wikipedia.org/wiki/Requirements_elicitation)

## Tags

#databases #database_design #requirements_analysis #interviews #mission_statement #CSD216
