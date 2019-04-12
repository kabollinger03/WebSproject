# sProject
**Purpose**: To create performence reports for employees undergoing S training sessions. Data is input via excel, output via PDF.

ZenHub (backlog) link: https://app.zenhub.com/workspaces/sproject-5c91685fd76ef51f6b0fe2ba/boards?repos=176605552

# Domain Words Dictionary

**User** - An instructor or admin. A person who manipulates data in the system. Does not have scores.

**Course** - A collection of programming concepts to be taught.

**Module** - A collection of one or more *courses*. Assessments are given on modules, so there is a 1:1 between modules and *scores*.

**Score** - The decimal grade an *employee* recieves on a *module*. Primary data point we are interested in.

**Category** - Classification of *modules*. Currently, **_Fundamental_**, **_Specalication_**, and **_Domain Knowledge_**.

**Stream** - A labeled, related collection of *modules*.

**Class** - A group of *employees*, all simultaniously partaking in a *stream*.

**Employee** - The main piece of data being described. Does not use the application. Belongs to a *class*. Takes many *modules*, receiving a *score* for each.

**Student** - Exact same thing as employee, but old term. Some people still use interchangably.
