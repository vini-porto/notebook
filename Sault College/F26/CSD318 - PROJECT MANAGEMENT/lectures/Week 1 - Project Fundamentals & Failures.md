# What is Project Management (in IT)?

Think of a company as running on two tracks at once: **operations** (the stuff that keeps happening every day, like helpdesk support or routine server maintenance) and **projects** (a one-time push to build or change something specific, like rolling out a new firewall). Project management is the discipline of steering that second kind of work — the temporary, high-stakes kind — from idea to finished result.

> "Operations keeps the lights on, strategy provides a light at the end of the tunnel, but project management is the train engine that moves the organization forward." — Joy Gumz

In other words: strategy says *where* the company wants to go, and project management is *how it actually gets there*.

---
## Projects vs. Operations

This is the first distinction to get straight, because they're managed completely differently.

| | Operations | Projects |
|---|---|---|
| **Duration** | Ongoing, no end date | Temporary — has a start and end |
| **Output** | Same result, repeated | A unique result |
| **Example** | Helpdesk support, routine server maintenance | Deploying a new enterprise firewall |

A helpdesk technician answering tickets is doing operations — it never "finishes." A team standing up a new firewall is doing a project — once it's live, the project is done (and maintaining it afterward becomes operations again).

---
## The Project Management Triangle: Time, Cost, Scope

Every project is a balancing act between three constraints:

- **Time** — the schedule. In IT, slipping the schedule can mean missing a market window or leaving a security hole open longer than planned.
- **Cost** — the budget: hardware, software licenses, and the people needed to do the work.
- **Scope** — exactly what features/tasks are included.

The key idea to remember: **these three are connected**. If you change one, you have to adjust the others, or something breaks. Add more scope without adding more time or budget, and quality (or the whole project) suffers. This single idea explains most project failures — see [[#Case Studies in Failure|the case studies]] below.

---
## Types of IT Projects

IT projects generally fall into three buckets:

1. **Hardware / Infrastructure** — physical servers, storage systems, data center builds.
2. **Software Development** — building or customizing applications.
3. **Network & Security** — wiring routers/switches together, deploying [[Subnetting|network]] changes, rolling out security controls.

### Focus: Infrastructure Projects
Infrastructure is the foundation everything else runs on, so projects here (like moving to [[Cloud Computing|cloud-based]] applications, upgrading security systems, or deploying company-wide multi-factor authentication) matter a lot. They also come with unique headaches: you often need to procure physical hardware, deal with real-world space and power constraints, and schedule disruptive downtime during off-hours so you don't take down the business while you work.

### Why This Isn't "Just Tech"
A new server isn't just a piece of hardware — it's *faster transaction times for customers*. A firewall upgrade isn't just software — it's *protecting the company's intellectual property*. The lesson: IT projects succeed or fail based on the **business problem** they solve, not just on whether the technology works. Success is measured by business impact, not technical implementation.

---
## The Project Manager's Role

If a project has a lot of moving parts, someone needs to hold it together — that's the Project Manager (PM). Picture the PM as a **bridge** between the technical team building the thing and the stakeholders (executives, clients) who asked for it. They need to understand *enough* tech to respect the engineering effort, and *enough* business to respect the budget — without necessarily being an expert at either extreme.

**A surprising stat:** roughly **90% of a PM's time is spent communicating** — status reports, stakeholder meetings, negotiating for resources, and resolving conflicts between team members. If you're picturing a PM as someone who mostly writes schedules, flip that picture: the job is mostly about talking to people.

### Hard Skills vs. Soft Skills

| Hard Skills (Technical) | Soft Skills (Interpersonal) |
|---|---|
| Scheduling software (MS Project, Basecamp, Jira) | Leadership |
| Budgeting & risk modeling | Negotiation |
| Understanding IT architecture | Conflict resolution |
| Agile / Waterfall methodologies | Empathy, adaptability |

### What Separates a Successful PM
- **Adaptability** — reacting quickly when something unexpected breaks.
- **Focus** — protecting the project's core goals from "just one more feature."
- **Empathy** — understanding the pressure the technical team is under and pushing back on unrealistic deadlines on their behalf.
- **Decisiveness** — making hard calls when time or resources run short.

---
## Industry Standards & Certifications

Project management isn't ad-hoc guesswork — there's a whole professional body behind it.

- **PMI (Project Management Institute)** is the leading global association for the profession. It sets the standardized practices used by Fortune 500 companies worldwide.
- **PMBOK Guide** (Project Management Body of Knowledge) is PMI's flagship publication — the reference book for the field, organized into knowledge areas like Scope, Time, Cost, Quality, Risk, and Communications (the exact same ideas covered in this lecture).

### The Three Main Certifications
- **CAPM** (Certified Associate in Project Management) — entry-level, no prior PM experience required.
- **PMP** (Project Management Professional) — the "gold standard," but requires 36 months of experience to sit the exam.
- **PMI-ACP** (Agile Certified Practitioner) — focused specifically on Agile methods.

The PMP in particular is a strong resume signal: PMI research shows PMP holders earn **32% higher salaries on average**, and it's frequently a hard requirement for senior IT management roles. Getting there takes four ingredients: **education** (courses like this one count toward the hours), **experience** (track any team-lead or coordination work you do), **exam preparation** (it tests situational judgment, not just definitions), and ongoing **maintenance** (earning Professional Development Units to keep the cert active).

---
## Why IT Projects Fail

IT projects fail at a notoriously high rate — and "failure" doesn't just mean a technical crash. It also means going over budget, running late, or delivering something the client didn't actually want. Three root causes come up again and again:

1. **Unclear Objectives** — nobody agreed on what "done" looks like, so the project drifts.
2. **Poor Communication** — the technical team and the business side operate in silos, so expectations quietly diverge.
3. **Scope Creep** — new features keep getting added without adjusting the budget or timeline (this is the Scope corner of the triangle breaking, from [[#The Project Management Triangle: Time, Cost, Scope|above]]).

Failing to lock down scope early is, by far, the single most common mistake project managers make.

---
## Case Studies in Failure

Four real examples make these abstract causes concrete:

| Project | What Happened | The Lesson |
|---|---|---|
| **Sainsbury's Automation** (2003, £150M) | A UK supermarket automated its warehouse with barcode systems. Barcode-reading errors caused major issues, but the team ignored the early warning signs and pushed forward — eventually scrapping the entire system. | Don't ignore problems found in the initial/testing phases — admitting failure early is cheaper than forcing a broken system into production. |
| **Apple's Copland OS** (mid-1990s) | Apple tried to build a new OS to replace System 7 and compete with Windows 95. Every product manager wanted their pet feature included, and the OS became so bloated and unstable it was abandoned. | Classic scope creep. A PM has to *ruthlessly* protect the original scope — if everything is a priority, nothing is. |
| **Nest's Software Refresh** (2016) | Google's Nest pushed a routine software update to smart thermostats. A glitch drained batteries and knocked devices offline — in the middle of winter, leaving customers without heat. | Thorough testing across all hardware configurations is non-negotiable; a small coding error can cause real physical harm to users. |
| **FoxMeyer's ERP** (1990s) | A $5B pharmaceutical distributor rolled out a massive SAP system and warehouse automation in an unrealistic 18-month window. Poor integration, no user training, and employee sabotage (fearing job losses) followed. | The $35M IT project directly caused the bankruptcy of the entire company — change management (training and supporting end-users) is vital, and unrealistic deadlines guarantee failure. |

---
## Designing for Success

Flip the failure patterns around, and you get a recipe for success:

- **Clear Scope** — requirements documented, approved, and "frozen" before development starts.
- **Stakeholder Buy-in** — active participation from the actual business owners who will use the technology, not just sign-off from a distance.
- **Realistic Budgets** — estimates that include contingency room for the technical hiccups that are always going to happen.

### Risk Management
Success isn't about avoiding every problem — it's about anticipating them. A strong PM does risk assessments early and often, so that when (not if) something fails — say, a server dies mid-deployment — there's already a mitigation plan ready to execute. This is **proactive management**, and it consistently beats **reactive firefighting**.

### Quick Pitfall-Avoidance Checklist
- **Set clear goals** — do strategic planning from day one, not after things go wrong.
- **Communicate relentlessly** — regular status meetings, but keep them focused and brief.
- **Manage scope** — use a formal change-request process for any new feature ask.
- **Involve stakeholders** — never build in isolation; show progress early to get real feedback.

---
# Tags

#project-management #PMI #PMBOK #CAPM #PMP #IT #CSD318 #risk-management #scope-creep #fundamentals
