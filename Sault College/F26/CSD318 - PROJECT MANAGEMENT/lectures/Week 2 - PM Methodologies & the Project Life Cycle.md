
> [!note] What this lecture covers
> Last week looked at *why* IT projects succeed or fail. This week looks at *how* a project actually gets run: the methodology you pick, the stages every project passes through, the tools teams use to track the work, and what happens when the methodology doesn't fit the project.

> [!example] Eisenhower's line on planning
> "In preparing for battle I have always found that plans are useless, but planning is indispensable."
> *Dwight D. Eisenhower*
>
> The plan you write on day one will be wrong by day thirty. The *habit* of planning (reviewing progress, adjusting, re-planning) is what keeps a project on track.

---
# Part 1: Traditional vs. Waterfall vs. Agile

There isn't one single way to manage a project. Three approaches come up again and again in IT.

| Approach                                            | How it works                                                                                                                                          |
| --------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| **[[Traditional Project Management\|Traditional]]** | A predictive, phase-gate approach built on [[PMBOK]]'s process groups. Scope, cost, and schedule are defined up front and tracked against a baseline. |
| **[[Waterfall]]**                                   | A linear-sequential method: each phase (requirements, design, build, test, deploy) has to finish before the next one starts. No going back.           |
| **[[Agile]]**                                       | An iterative, incremental approach. Work ships in short cycles called sprints, and requirements are expected to change as feedback comes in.          |

> [!note] Traditional vs. Waterfall
> These two terms get used almost interchangeably, but Waterfall is really the strict, sequential *version* of a traditional approach: one pass, start to finish, with no overlap between phases.

## Diagramming Waterfall

```
Requirements → Design → Build → Test → Deploy → Maintain
```

One pass, start to finish. If someone discovers a missed requirement during Test, the team has to go back upstream and redo earlier work.

> [!warning] Why late discovery is expensive
> The later a problem surfaces in Waterfall, the more it costs to fix. You may have already built, and tested, on top of the mistake.

## Diagramming Agile ([[Scrum]])

```
Sprint Planning → Daily Stand-up → Sprint Work → Sprint Review → Retrospective
```
*(cycle repeats every 1-4 weeks)*

Each sprint produces a working increment of the product, so feedback and course correction happen continuously instead of only at the very end.

## Waterfall vs. Agile: Key Contrasts

| | Waterfall | Agile |
|---|---|---|
| Scope | Fixed, defined up front | Evolving, re-prioritized every sprint |
| Phases | Sequential, no overlap | Overlapping, iterative cycles |
| Customer sees the product | At the end | Every sprint |
| Cost of change | Expensive once underway | Expected and welcomed |
| Best for | Stable, well-understood requirements (e.g. regulated infrastructure builds) | Uncertain requirements or fast-moving markets (e.g. new customer-facing apps) |

> [!important] Choosing the right approach
> There is no universally "correct" methodology. The right choice depends on the project's stability, risk tolerance, and how involved the customer needs to be.
> Many IT organizations run a **[[Hybrid Project Management|Hybrid]]** model: a Traditional charter and budget wrapped around Agile sprints for the actual development work. A PM's job is to match the process to the project, not force every project into the same template.

## Case Study 1: FBI Virtual Case File (2000-2005)

> [!example] The project
> The FBI's first attempt to replace paper case files with a searchable digital system. Requirements were gathered up front and locked into a traditional Waterfall contract with a single outside contractor.

> [!warning] The failure
> After nearly five years and about $170 million, the system still couldn't do the basic tasks agents needed. It was scrapped entirely in 2005 without ever going live agency-wide.

## Case Study 2: FBI Sentinel (2006-2012)

> [!example] The restart
> The FBI's second attempt at the same goal, this time managed in-house in short, iterative cycles instead of one long fixed-scope contract.

> [!tip] The outcome
> Small teams shipped working pieces every few weeks and adjusted based on agent feedback. Sentinel went live in 2012 on a far smaller budget than the first attempt, and it is still in use today.

> [!important] Lessons: same agency, two different outcomes
> Same organization, same underlying goal. The deciding factor was the methodology, not the technology. Virtual Case File's single, multi-year Waterfall contract meant nobody saw a working system until it was far too late to change course cheaply. Sentinel's short, iterative cycles meant problems surfaced, and got fixed, every few weeks instead of every few years.

---
# Part 2: The [[Project Life Cycle]]

Every project, regardless of methodology, moves through a set of stages from start to finish.

## The 4-Stage Life Cycle

1. **Initiating**: define the project and secure authorization to begin.
2. **Planning**: build the roadmap covering scope, schedule, budget, and resources.
3. **Executing**: do the work and produce the deliverables.
4. **Closing**: deliver, hand off, and formally end the project.

## The 5-Stage Life Cycle (PMBOK Process Groups)

1. Initiating: define and authorize the project.
2. Planning: build the detailed roadmap.
3. Executing: do the work.
4. **Monitoring & Controlling**: track performance and manage change.
5. Closing: deliver and formally close.

> [!note] Monitoring & Controlling isn't a separate phase
> PMBOK's 5-stage model splits "doing the work" from "watching the work." Monitoring & Controlling runs *alongside* Executing for the whole project, not after it.

> [!note] Sequential vs. repeating
> In a Waterfall project, the stages are mostly sequential, one after another. In an Agile project, the entire life cycle repeats, in miniature, inside every single sprint.

> [!warning] Common, avoidable mistake
> Skipping Initiating or Closing is one of the most common mistakes in project management, no matter which model you use. Skip Initiating and nobody has clear authorization or scope. Skip Closing and the project never really ends, lessons never get captured, and resources stay tied up.

## Case Study 3: Denver International Airport Baggage System (1995)

> [!example] The project
> An automated, computer-controlled baggage system meant to route bags across the entire airport without manual handling. It was a flagship feature of the new airport, built on an aggressive fixed opening date.

> [!warning] The failure
> Testing was compressed to hit the announced opening date. On camera, the system shredded bags, jammed, and mis-routed luggage. The airport's opening was delayed 16 months, at a reported cost of about $1 million a day, and the system was later abandoned for most flights.

> [!important] Lessons: respect the life cycle
> The Monitoring & Controlling and testing work wasn't skipped on purpose, it was compressed to protect a fixed date, which has the same effect. A life cycle stage that gets rushed doesn't disappear, it resurfaces later, in public, at a much higher cost. "We're on schedule" only means something if the schedule still has room for the stages that catch problems before Closing.

---
# Part 3: PM Software & Tools

## Categories of PM Software

| Category | Example tools | Built around |
|---|---|---|
| Traditional / [[Gantt Chart]] | Microsoft Project | Baseline schedule, dependencies, critical-path analysis |
| Agile / [[Kanban]] | Jira, Trello | Boards, backlogs, sprints, burndown charts |
| All-in-One Collaboration | Basecamp, Asana, ClickUp | To-dos, docs, messaging, and a schedule in one shared space |

> [!note] Why this course uses Basecamp
> Every project team gets one Basecamp space with a To-do list, Message Board, Docs & Files, and Schedule. It's simple enough to set up in a single lab, but it exercises the same virtual-team skills you'll need later for your capstone and in the field.

## Comparing Popular PM Tools

| Tool | Workflow | Strength | Best for |
|---|---|---|---|
| Microsoft Project | Traditional / Gantt | Deep scheduling, dependencies, critical path | Large, fixed-scope infrastructure projects |
| Jira | Agile / Kanban / Scrum | Sprint boards, backlogs, dev-tool integration | Software development teams |
| Basecamp | Flexible to-do + docs | Simplicity, message boards, client-friendly | Small teams needing one shared home base |

> [!example] Basecamp's origin story
> Basecamp wasn't originally a product, it was an internal tool a small web design agency, 37signals, built in 2004 to manage their own client projects. Other agencies started asking to use it too, so 37signals turned it into a standalone product, and it did so well that the whole company eventually renamed itself Basecamp.
>
> The lesson for a PM: the most useful PM tools often come from a team solving its own real coordination problem, not from a feature checklist built for everyone in general.

> [!example] Agile at scale: the "Spotify Model"
> Spotify popularized organizing engineering teams into small, autonomous "squads," each acting like a mini-startup responsible for one part of the product. The model spread across the tech industry and became a common reference point for scaling Agile beyond a single team.

> [!tip] Don't copy blindly
> Spotify itself has since said the model was never meant to be copied exactly, it was a snapshot of how one team organized itself at one point in time. Adapt any methodology to your own team, don't just import someone else's org chart.

---
# Part 4: Modern IT Delivery: [[CI/CD]]

## What Is CI/CD?

> [!note] Continuous Integration (CI)
> Developers merge code changes into a shared repository frequently. Every merge automatically triggers a build and a test run, catching integration problems early instead of at the end of a long release cycle.

> [!note] Continuous Deployment (CD)
> Code that passes its automated tests is automatically released toward production. It shrinks the gap between "code is written" and "customer can use it" from months to hours.

### Anatomy of a CI/CD Pipeline

```
Code Commit → Automated Build → Automated Test → Release / Deploy → Monitor
```

You'll get hands-on with this pipeline in this week's lab, and your team will keep an eye on it again in Week 10, when the course covers monitoring and analyzing CI/CD build reports.

> [!important] Why CI/CD matters to a project manager
> - It shrinks risk: small, frequent changes are far easier to diagnose and roll back than one giant release.
> - It changes how you report progress. "Percent complete" becomes less useful than "how many pipeline runs are green."
> - A PM doesn't need to write the pipeline, but does need to read its results and factor build/test failures into the schedule and [[Risk Management|risk register]].

> [!example] Amazon's deployment speed
> At one point, Amazon's engineering teams were pushing a new production deployment somewhere across their systems roughly every **11.7 seconds** on average (AWS re:Invent, 2011). That pace is only possible with heavy CI/CD automation: small, independent teams shipping small, independently-tested changes constantly, instead of batching everything into rare, risky "big bang" releases.

> [!example] Netflix and Chaos Engineering
> Netflix runs an internal tool nicknamed "Chaos Monkey" that randomly disables production servers during business hours, on purpose. The idea: if your CI/CD pipeline and monitoring can't catch and recover from a random failure automatically, you want to find that out in a controlled test, not during a real customer-facing outage.
>
> For a PM, this is [[Risk Management]] applied directly to CI/CD: assume things will break, and plan (and budget) for automated recovery instead of just hoping nothing goes wrong.

---
# Part 5: When Methodology Fails

> [!important] Why methodology choice matters
> The wrong methodology can sink a project even when the technology itself works fine. A Waterfall approach forced onto a project with unstable, evolving requirements tends to produce a system that's technically "on spec" and still unusable.

## Case Study 4: Healthcare.gov Launch (2013)

> [!example] The project
> The U.S. federal government's online health insurance marketplace, built by roughly 55 contractors under a traditional, requirements-heavy government procurement process, with a fixed October 1, 2013 launch date that could not move.

> [!warning] The failure
> The system crashed within hours of launch. End-to-end integration testing, checking that all the separately built pieces worked together, didn't happen until days before launch: far too late to catch the problems it found.

> [!important] Lessons from Healthcare.gov
> - A fixed deadline does not make requirements or integration risk disappear, it just moves the discovery of problems later, where they cost more to fix.
> - Continuous, end-to-end testing (the same principle behind CI/CD) needs to run throughout the project, all the way to launch.
> - With 55+ contractors and no single technical integrator, no one had a full view of whether the pieces fit together: a coordination failure as much as a technical one.

> [!tip] Healthcare.gov's turnaround
> Within weeks of the crash, a small "tech surge" team, including engineers pulled in from outside the original contractor structure, took over delivery. They dropped most of the original plan and worked in tight, Agile-style cycles: fix something, deploy it, monitor it, fix the next thing. By December 2013, about two months after launch, the site was handling hundreds of thousands of users a day. Same project, mostly the same underlying code, a completely different way of working.

## Blending Methodologies: [[Hybrid Project Management|Hybrid PM]]

Most real-world IT projects today aren't pure Waterfall or pure Agile, they're **Hybrid**: a Traditional charter, budget, and governance structure wrapped around Agile sprints for the technical work. Hybrid gives executives the fixed-cost, fixed-scope reporting they expect, while giving development teams the flexibility to adapt as they learn.

> [!tip] For your class project
> As you plan your class project starting next week, decide deliberately which parts of your process are fixed and which are allowed to evolve. Don't default to one extreme.

---
# Key Takeaways

- **Match the method to the project.** Traditional/Waterfall for stable, well-defined work; Agile for evolving, fast-moving work; Hybrid for most everything in between.
- **Respect the life cycle.** Initiating and Closing are not optional steps, skipping either is a common, avoidable failure.
- **Tools support process, they don't replace it.** Basecamp, Jira, and MS Project all fail the same way if the underlying methodology and communication are broken.
- **Test continuously.** Whether it's CI/CD pipelines or Agile sprint reviews, the earlier you find a problem, the cheaper it is to fix.

# Further Reading

- [PMI (Project Management Institute)](https://www.pmi.org)
- [Scrum.org](https://www.scrum.org)
- [Asana: PM Resources](https://asana.com/resources)
- [ProjectManagement.com](https://www.projectmanagement.com)

---
# Tags

#project-management #agile #waterfall #scrum #hybrid-pm #project-life-cycle #PMBOK #CI-CD #case-studies #CSD318
