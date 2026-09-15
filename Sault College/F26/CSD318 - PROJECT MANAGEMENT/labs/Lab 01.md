# Part 1: Project vs. Operational Work in IT

1. Upgrading campus core switches to support multi-gigabit speeds and 802.1X port authentication.
	**ANS:** Project: Because it has a temporary time to start and end the upgrade, plus It produces a unique result, difering from the old one.
    
2. Managing daily Tier-1 helpdesk support tickets for password resets and printer connectivity.
	**ANS:** Operation: Its a continuous task that have to be done repetedely and has not end date. Produces the same result at the end as other time requests.
    
3. Conducting regular bi-weekly operating system patch management across virtualized servers.
	**ANS:** Operation: As it refers "bi-weekly" makes it an ongoing operation that have not end date, and always produces the same result. 
    
4. Designing and implementing an isolated industrial IoT VLAN network segment for a manufacturing client.
	**ANS:** Project: As it is goint to be an temporary implementation of a new "feature", and makes a compleatly different result at the end.
    
5. Performing daily tape and off-site cloud backups of company databases.
	**ANS:** Operation: As repeativite taks that happen every day, and there are not a especific end date, and produces the same resut as part of a maintenance routine. 
    
6. Planning and migrating 120 on-premises active directory accounts and file shares to Microsoft 365.
	**ANS:** Project: As a more complex out of routine taks its classified as a project, because it involves moving multiple items around, someting that would not be classified as an ongoing because it has a specific amout pre-defined thus, end date.
    
7. Replacing a failed hot-swappable hard drive in a RAID 5 server array.
	**ANS:** Project: As it have a specific purpuse and end date to finish.  
    
8. Developing and deploying an automated network configuration compliance monitoring script across all branch routers.
	**ANS:** Project: As it involves creating and deploying a specific thing or set of things in order to produce a uniuque result, thus it has an end date.

# Part 2: Project Management Software Evaluation


| **Feature / Criterion**                                                          | Tool 1 - Trello                                                                                                                                                                                                                                                                                                                                                | Tool 2 - Jira                                                                                                                                                                                                     | Tool 3 - ClickUp                                                                                                                                                                                                                                         |
| -------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Primary Workflow Model (Kanban, Scrum, Traditional/Gantt)                        | based on Kanban                                                                                                                                                                                                                                                                                                                                                | supports all other models. While Kanban and Scrum act as native                                                                                                                                                   | Does not have a single primary workflow model. Natively supports all workflows simultaneously                                                                                                                                                            |
| Free / Student Tier Limitations (User count, storage, integrations)              | Trello Free Tier is free forever limited to: Max 10 collaborators per Workspace, Max 10 active boards per Workspace, Unlimited total storage; 10 MB per file cap, and Unlimited Power-Ups per board. For a Student trello using Trello you would receave a 75% discount from the premium subscription and would have the same features as a premium user does. | Jira Free Tier: Max 10 users, 2 GB total file storage for attachments. The integration with core apps are supported, but advanced/enterprise integrations are restricted. Does not offer a separate Student Tier. | ClickUp offers a free forever plan with no strict user limits. Unlimited members can join to the workspace, capped at 60MB total workspace storage, and offers limited integration uses compared to paid tiers. Does not offer a dedicated student tier. |
| Code & Repo Integrations (GitHub, GitLab, Bitbucket)                             | Trello integrates with major code hosting platforms primarily through Power-Ups mainly with GitHub/GitLab.                                                                                                                                                                                                                                                     | Offers powerful native Code & Repo Integrations for GitHub, GitLab, and Bitbucket.                                                                                                                                | Offers powerful native Code & Repo Integrations for GitHub, GitLab, and Bitbucket.                                                                                                                                                                       |
| IT Infrastructure Relevance (Asset tracking, incident handling, network rollout) | Uses a CMDB, especially good for Incident Swarming or conducting post-mortems, manages physical infrastructure deployment with complex dependencies through structural templates                                                                                                                                                                               | Uses an uses an object schema database for asset tracking, Provides an framework to manage and resolve infrastructure alerts and outages, and exceptional for large-scale physical deployments.                   | Uses a CMDB or asset inventory system, handle with incidents using a streamline, use a dedicated Space for infrastructure deployments                                                                                                                    |
| **Key Advantage**                                                                | Easy to use, Good free plan, visual clarity, power-ups and integrations                                                                                                                                                                                                                                                                                        | Agile Depth, Workflow Customization, Ecosystem Integration                                                                                                                                                        | High customization, robust automation, scalable structure, Free Tier                                                                                                                                                                                     |
| **Key Drawback**                                                                 | No native advanced features, scalability limits, reliance on add-ons                                                                                                                                                                                                                                                                                           | Learning curve, lacks native resource, performance                                                                                                                                                                | Learning curve, performance                                                                                                                                                                                                                              |

> [!question] **Reflection Question:**
> Based on your comparison, which tool would you recommend for managing a 14-week enterprise network infrastructure upgrade project, and why?
> - **ANS:** I would recoment Click up as it offers a good set of features that would match better for a company that wants to scale in an infrastructure project update. 

# Part 3: Applying the PMI Talent Triangle to IT Roles
The Project Management Institute (PMI) defines three core competency areas in the PMI Talent Triangle:

- **Ways of Working** (Predictive, Agile, Hybrid, CI/CD)
- **Power Skills** (Leadership, communication, conflict management, emotional intelligence)
- **Business Acumen** (Organizational strategy, compliance, vendor alignment, ROI)

>[!example] Scenario B
>- (Network Infrastructure): Leading the physical cabling, switch configuration, and VLAN segmentation overhaul for a new two-story corporate building.

For your selected scenario, outline **two specific tasks or decisions** the Project Manager must execute under each of the three Talent Triangle pillars (6 total tasks).
	For this project, if scope grows (like adding VLANs for computers), I would need more time, more budget, or I would push that scope to a later phase. If the deadline moves up, I would also need more people or otherwise I would cut scope. 

# Part 4: Ethics Case Study — The Pre-Deployment Vulnerability
  
  Read the following scenario and answer the questions using the **PMI Code of Ethics and Professional Conduct** (Responsibility, Respect, Fairness, Honesty):

**Scenario:**

You are the Project Manager for a critical enterprise portal upgrade scheduled to go live in 24 hours. The client has already booked marketing campaigns announcing the launch, and your executive sponsor warns that missing this deadline will result in financial penalties. During final regression testing, your lead cybersecurity analyst discovers an unpatched, medium-severity authorization bypass vulnerability in the API backend. The lead developer tells you: _"We can suppress the alert for launch, go live tomorrow, and patch it in a maintenance release next weekend without anyone noticing."_

1. **Responsibility:** What is your professional duty regarding the security risks and potential consequences of deploying with an active vulnerability? 
	 Admitting a mistake instead of hiding it, following through on commitments, and upholding laws and regulations.
2. **Honesty:** How must you communicate this finding to the client and executive sponsors, and what information must be disclosed?
	Reporting project status accurately and avoiding half-truths or deception of any kind.
3. **Fairness:** How do you address the conflicting interests between the marketing/executive team demanding an on-time launch and the security analyst raising concerns?
	Disclosing potential conflicts of interest transparently and avoiding discrimination in hiring or vendor selection.
    
4. **Action Plan:** Outline the exact next steps you would take in the remaining 24 hours to manage this issue.
	I would report the problem to the client telling about the recent found mistake that implies in a high vunerability for the project, and would connect him with the marketing team so we could find a solution for the posponement of the marketing campain for the next day and manage the financial penalties as a security risk.