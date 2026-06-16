# Summer 2026 Project Repository

Welcome to the Summer 2026 Project Repository!

This repository is used by all students participating in the Summer 2026 program. Each student will work in their own branch and submit changes through Pull Requests.

---

# Team Members

| Name     | Branch Name       |
| -------- | ----------------- |
| Aashi    | `Summer-Aashi`    |
| Jenni    | `Summer-Jenni`    |
| Loukya   | `Summer-Loukya`   |
| Navya    | `Summer-Navya`    |
| Nimisha  | `Summer-Nimisha`  |
| Nirvi    | `Summer-Nirvi`    |
| Niyati   | `Summer-Niyati`   |
| Ruchitha | `Summer-Ruchitha` |
| Saanvi   | `Summer-Saanvi`   |
| Sarah    | `Summer-Sarah`    |
| Trisha   | `Summer-Trisha`   |

> Every student must work only on their assigned branch.

---

# Repository Setup

## 1. Clone the Repository

Clone the repository to your computer:

```bash
git clone https://github.com/FTCRoboRoar30449/Summer26.git
```

Move into the repository:

```bash
cd Summer26
```

---

## 2. Verify Current Branch

Check your current branch:

```bash
git branch
```

Expected output:

```text
* main
```

---

## 3. Create Your Personal Branch

Create your branch using the format:

```text
Summer-STUDENTNAME
```

Example:

```bash
git checkout -b Summer-Aashi
```

Push your branch to GitHub:

```bash
git push -u origin Summer-Aashi
```

Repeat using your own name.

---

# Daily Workflow

## Start Your Work Session

Always pull the latest version of the main branch before starting work:

```bash
git checkout main
git pull origin main
```

Switch back to your branch:

```bash
git checkout Summer-YOURNAME
```

Merge the latest changes from main:

```bash
git merge main
```

---

## Make Your Changes

Edit files as needed.

Check your changes:

```bash
git status
```

---

## Commit Your Changes

Stage files:

```bash
git add .
```

Commit with a meaningful message:

```bash
git commit -m "Describe your changes"
```

Examples:

```bash
git commit -m "Added Week 2 assignment"
git commit -m "Updated robot navigation code"
git commit -m "Fixed bug in scoring logic"
```

---

## Push Your Changes

Push updates to your branch:

```bash
git push
```

---

# Submitting Your Work

When your work is complete:

1. Push your latest changes.
2. Go to the GitHub repository.
3. Select your branch.
4. Click **Compare & Pull Request**.
5. Create a Pull Request into the `main` branch.
6. Add a clear description of your work.
7. Submit the Pull Request for review.

---

## Pull Request Title Format

Use the following format:

```text
[Student Name] Assignment Name
```

Examples:

```text
[Aashi] Week 1 Git Exercise
[Sarah] Robot Navigation Update
[Navya] Sensor Integration Project
```

---

## Pull Request Description Template

```text
Summary:
- Brief description of changes

Files Updated:
- List major files modified

Testing:
- Describe how the changes were tested

Notes:
- Any additional comments
```

---

# Branch Naming Rules

All branches must follow:

```text
Summer-STUDENTNAME
```

Valid Examples:

```text
Summer-Aashi
Summer-Sarah
Summer-Trisha
Summer-Nimisha
```

Invalid Examples:

```text
aashi_branch
summer-aashi
feature-aashi
student-aashi
```

---

# Git Commands Quick Reference

## Check Current Branch

```bash
git branch
```

## Pull Latest Changes

```bash
git checkout main
git pull origin main
```

## Switch Branch

```bash
git checkout Summer-YOURNAME
```

## Stage Files

```bash
git add .
```

## Commit Changes

```bash
git commit -m "Commit message"
```

## Push Changes

```bash
git push
```

## Check Repository Status

```bash
git status
```

---

# Rules & Expectations

### DO

✅ Work only in your assigned branch

✅ Commit frequently

✅ Write meaningful commit messages

✅ Pull latest changes before starting work

✅ Submit Pull Requests for review

✅ Ask questions when you need help

### DO NOT

❌ Commit directly to `main`

❌ Modify another student's branch

❌ Force-push without instructor approval

❌ Delete branches

❌ Submit incomplete work without notes

---

# Need Help?

If you encounter Git issues:

1. Run:

```bash
git status
```

2. Verify you're on the correct branch:

```bash
git branch
```

3. Pull the latest changes from `main`.

4. Reach out to your mentor or team lead.

---

Happy Coding!

Summer 2026 Program
