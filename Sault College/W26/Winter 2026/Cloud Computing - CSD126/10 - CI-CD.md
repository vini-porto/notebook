# Definition? 

> [!note] What is CI/CD? 
> **CI/CD** stands for **Continuous Integration** and **Continuous Delivery / Continuous Deployment**. It is a set of practices and tools that automate the process of building, testing, and releasing software — so teams can ship code changes faster and more reliably.

Think of CI/CD as an assembly line for software. Instead of a developer manually running tests and uploading files to a server, all of that happens automatically every time code is pushed.

# The Three Parts of CI/CD

|Term|Full Name|What It Does|
|---|---|---|
|**CI**|Continuous Integration|Automatically builds and tests code every time a developer pushes changes|
|**CD**|Continuous Delivery|Automatically prepares code for release, but a human still approves the final push to production|
|**CD**|Continuous Deployment|Goes one step further — deploys automatically to production without human approval|

> [!important] Delivery vs. Deployment
> These two are often confused. In **Continuous Delivery**, a human must click "approve" before the code goes live. In **Continuous Deployment**, if all tests pass, the code goes live automatically — no human needed. Most teams start with Delivery and move toward Deployment as their test coverage matures.

Together, these form a **CI/CD pipeline** — a series of automated steps that take code from a developer's machine all the way to production.

# Why Use CI/CD?

The main reason is **speed** — but the benefits go further:

- **Development velocity**: Developers can push smaller changes more often instead of waiting for big releases.
- **Stability and reliability**: Automated tests catch bugs early, keeping the codebase stable at all times.
- **Business growth**: With less time spent on manual tasks, teams can focus on building features and fixing real problems.

> [!example] Real numbers
> According to the State of DevOps report, organizations that have mastered CI/CD deploy **208 times more often** and have a lead time **106 times faster** than teams without it.

# The CI/CD Pipeline

```
Plan → Code → Build → Test → Create Artifact → Staging → Production
```

1. **Plan** — team decides what to build
2. **Code** — developer writes code and pushes it to a shared repository using [[Git]]
3. **Build** — CI tool automatically compiles/packages the code
4. **Test** — automated tests run (unit tests, integration tests, etc.)
5. **Artifact** — a deployable package is created (e.g., a Docker image or a `.jar` file)
6. **Staging** — the artifact is deployed to a test environment that mirrors production
7. **Production** — if all checks pass, the code goes live

# What Makes a Good CI/CD Pipeline?

|Quality|Why It Matters|
|---|---|
|**Automation**|Reduces human error and frees up developer time|
|**Transparency**|Logs and dashboards help teams quickly find what went wrong|
|**Speed**|Measured via DORA metrics: deployment frequency and lead time for changes|
|**Resilience**|Measured by mean time to resolution and change failure rate|
|**Security**|Automated security checks (DevSecOps) catch vulnerabilities early|
|**Scalability**|The pipeline should handle more load as the team and project grow|

> [!tip] DORA Metrics 
> **DORA** (DevOps Research and Assessment) metrics are the industry-standard way to measure CI/CD performance. The four key metrics are: deployment frequency, lead time for changes, mean time to recovery, and change failure rate.

# Building Your CI/CD Toolkit

A CI/CD workflow is built from several components working together:

- **[[Version Control]] ([[Git]])**: All CI starts here. Developers commit code to a shared repository. Every push can trigger the pipeline.
- **Build tools**: Automatically package files into release artifacts and run quality checks.
- **Test suites**: Run unit, integration, and end-to-end tests automatically.
- **Review and approvals**: Code review is a best practice that improves quality before code is merged.
- **Environments**: Code moves through different environments (dev → staging → production), each with its own rules and variables.

# GitHub Actions

[[GitHub Actions]] is GitHub's built-in CI/CD platform. It lets you automate your build, test, and deployment pipeline directly from your repository — no external tools required.

> [!note] What GitHub Actions can do
> Beyond standard CI/CD, GitHub Actions can also react to other repository events — for example, automatically adding labels to a new issue, or sending a notification when a pull request is opened.

GitHub provides **[[Linux]], [[Windows]], and [[macOS]]** virtual machines to run your workflows, or you can use your own self-hosted machines.

## The Five Core Components of GitHub Actions

Understanding these five building blocks is essential for working with [[GitHub Actions]].

### 1. Workflow

> [!note] Definition 
> A **workflow** is a configurable automated process made up of one or more jobs. It is defined in a [[YAML]] file stored in your repository under `.github/workflows/`.

- A repository can have **multiple workflows**, each for different tasks.
- Workflows can be triggered by events, on a schedule, or manually.
- One workflow can also call another workflow (reusable workflows).

```yaml
# .github/workflows/ci.yml
name: CI Pipeline

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run tests
        run: npm test
```

### 2. Event

> [!note] Definition 
> An **event** is a specific activity in a repository that triggers a workflow. Examples include pushing code, opening a pull request, or creating an issue.

Common events:

- `push` — triggered when code is pushed to a branch
- `pull_request` — triggered when a PR is opened or updated
- `schedule` — triggered on a cron schedule (e.g., every night at midnight)
- `workflow_dispatch` — triggered manually from the GitHub UI

### 3. Job

> [!note] Definition
> A **job** is a collection of **steps** that run on the same runner (virtual machine). Steps within a job run sequentially and share the same environment.

- By default, jobs run **in parallel**.
- You can set **dependencies** between jobs so they run in a specific order.
- You can use a **matrix** strategy to run the same job with different combinations of variables (e.g., test on Node 18 and Node 20 at the same time).

> [!example] Job dependency example
> You might have three build jobs (one per OS) that all run in parallel, and then a packaging job that only starts after all three builds succeed.

### 4. Step

Each job contains **steps**. A step is either:

- A **shell script** you write directly in the YAML file (using `run:`), or
- An **action** — a pre-built reusable unit (using `uses:`).

Steps inside a job share the same file system, so one step can create a file and the next step can use it.

### 5. Action

> [!note] Definition
> An **action** is a pre-built, reusable piece of automation. Instead of writing the same setup commands in every project, you use an action written by someone else (or yourself).

Actions can:

- Check out your code from GitHub (`actions/checkout`)
- Set up a programming language runtime (`actions/setup-node`)
- Authenticate with a cloud provider like [[AWS]] or Azure

You can find community-built actions in the **GitHub Marketplace**.

### 6. Runner

> [!note] Definition
> A **runner** is the server (virtual machine) that actually executes your workflow. Each runner handles one job at a time.

GitHub provides these hosted runners for free (with usage limits):

- `ubuntu-latest`
- `windows-latest`
- `macos-latest`

You can also configure **self-hosted runners** — your own servers registered with GitHub — if you need specific hardware, operating systems, or network access.

# How the Components Relate

```
Event (e.g. push)
  └── triggers Workflow
        └── contains Jobs
              └── runs on a Runner
                    └── executes Steps
                          └── each Step is a script or an Action
```

# YAML — The Language of CI/CD Pipelines

All GitHub Actions workflows are written in [[YAML]]. YAML is a human-readable data format used for configuration files. Understanding YAML is essential for writing pipelines.

> [!important] Key YAML rules
> 
> - YAML uses **indentation with spaces** — never tabs.
> - YAML is **case-sensitive** (`True` and `true` are different).
> - File extensions: `.yaml` or `.yml`.
> - YAML is a **superset of JSON** — any valid JSON is also valid YAML.

## Basic Data Types

```yaml
# Integer
count: 42

# Float
price: 9.99

# String (all three are equivalent)
name: 'Alice'
name: "Alice"
name: Alice

# Boolean
is_active: true
is_admin: false

# Date
birthday: 2000-01-15

# Null
middle_name: ~
```

## Comments

```yaml
# This is a single-line comment
name: Alice  # inline comment
```

## Lists (Sequences)

```yaml
# Block style (most common in GitHub Actions)
languages:
  - Python
  - JavaScript
  - Go

# Inline style
languages: [Python, JavaScript, Go]
```

## Dictionaries (Mappings)

```yaml
# Block style
person:
  name: Alice
  age: 30
  city: Toronto

# Inline style
person: {name: Alice, age: 30}
```

## Nested Structures

```yaml
# List of dictionaries — very common in GitHub Actions steps
steps:
  - name: Checkout code
    uses: actions/checkout@v3
  - name: Run tests
    run: npm test
```

> [!example] What this looks like in a real Actions workflow
>  The `steps:` key above is a **list of mappings**. Each `-` starts a new step (a dictionary), and each step has keys like `name`, `uses`, and `run`.

## Multiline Strings

```yaml
# Literal block (| keeps newlines)
message: |
  Hello
  World
# Result: "Hello\nWorld\n"

# Folded block (> collapses newlines into spaces)
message: >
  Hello
  World
# Result: "Hello World\n"
```

## Anchors and Aliases (Reuse Values)

```yaml
# Define a reusable value with &
defaults: &common_settings
  timeout: 30
  retry: 3

# Reference it with *
job_a:
  <<: *common_settings
  name: Job A

job_b:
  <<: *common_settings
  name: Job B
```

> [!tip] Anchors in GitHub Actions
> Anchors (`&`) and aliases (`*`) are useful for reducing repetition in complex workflow files — for example, sharing environment variable sets across multiple jobs.

## Multiple Documents in One File

```yaml
---
# Document 1
name: config-dev

---
# Document 2
name: config-prod
```

`---` separates multiple YAML documents in a single file.

## YAML Quick Reference Table

|Indicator|Meaning|
|---|---|
|`#`|Comment|
|`-`|List item|
|`:`|Key-value separator|
|`|`|
|`>`|Folded multiline string|
|`&`|Anchor (define reusable value)|
|`*`|Alias (reference an anchor)|
|`<<`|Merge keys from another mapping|
|`---`|Document separator|

> [!warning] Common YAML mistakes
> 
> - Using **tabs** instead of spaces for indentation — YAML will throw an error.
> - Misaligning indentation — a key that should be nested will be read as a top-level key if it is not indented correctly.
> - Forgetting quotes around strings that contain special characters like `:` or `#`.
> - You can validate your YAML at [yamllint.com](http://www.yamllint.com/).

# Putting It All Together

```yaml
# .github/workflows/build-and-test.yml

name: Build and Test          # Workflow name

on:                           # Event triggers
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  test:                       # Job name
    runs-on: ubuntu-latest    # Runner
    strategy:
      matrix:                 # Run this job for multiple Node versions
        node-version: [18, 20]

    steps:
      - name: Checkout code   # Step 1 — uses an Action
        uses: actions/checkout@v3

      - name: Set up Node     # Step 2 — uses an Action with input
        uses: actions/setup-node@v3
        with:
          node-version: ${{ matrix.node-version }}

      - name: Install deps    # Step 3 — runs a shell command
        run: npm install

      - name: Run tests       # Step 4 — runs a shell command
        run: npm test

  deploy:
    runs-on: ubuntu-latest
    needs: test               # Only runs after 'test' job succeeds
    if: github.ref == 'refs/heads/main'
    steps:
      - name: Deploy to production
        run: echo "Deploying..."
```

> [!example] What this workflow does
> 
> 1. Triggers on every push or PR to `main`.
> 2. Runs the `test` job on two Node.js versions in parallel (matrix strategy).
> 3. Only if all tests pass, runs the `deploy` job.
> 4. The `deploy` job only runs on the `main` branch, not on PRs.

# Other Resources

- [GitHub CI/CD Overview](https://github.com/resources/articles/ci-cd) — GitHub's full explanation of CI/CD concepts
- [GitHub Actions Documentation](https://docs.github.com/en/actions/get-started/understand-github-actions) — Official reference for all GitHub Actions components
- [YAML Quick Reference](https://quickref.me/yaml.html) — Cheat sheet for YAML syntax
- [YAML Lint](http://www.yamllint.com/) — Validate your YAML files online
- [Learn X in Y Minutes — YAML](https://learnxinyminutes.com/docs/yaml/) — Quick YAML crash course
- [GeeksForGeeks — CI/CD](https://www.geeksforgeeks.org/ci-cd-continuous-integration-and-continuous-delivery/) — Additional explanations with diagrams

# Tags

#cicd #devops #github #githubactions #yaml #automation #continuousintegration #continuousdelivery #continuousdeployment #pipeline #software #cloud-computing 