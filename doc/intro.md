# Introduction to codemining-clojure

## Entities

A "project" is initially defined by a GitHub repo.

A project has "entry points" -- public protocols, functions, etc. Each entry point may have
"documentation" -- human-readable text describing that entry point.

One project can "reference" the entry point of another. A "reference" is a single mention in one
project of an entry point in another project. (A project cannot reference itself in this model.)

More specifically, a project references entry points through its "files".

Other entities can "reference" a project. One early example will be a Stack Exchange answer.

A project has "versions". We are going to try not to think too much about this too soon, nor too
late.

A project has "value", which we define for now to be the number of references to that project in
(the current versions of) other projects.

An "author" is initially defined by a GitHub user.

An author "creates" a certain "creation fraction" of a project. For now, we define this to be the total
net line count of the author's commits divided by the total net line count of all of the
project's commits.

An author has "authority". For now, we define this to be the total of (project value * creation
fraction) across projects the author has created.

A project has "authority". For now, we define this to be the weighted average, by creation fraction,
of  the project's authors' authoritites.

A reference has "file code context" -- the set of other entry points referenced by the file in which
the reference appears. Eventually, we may add the notion of "local code context" -- the set of other
entry points referenced by the local scope in which the reference appears.

A reference may also have "descriptive context" -- human-readable text associated with the
reference. For example, for a reference in a Stack Exchange answer, the text of the answer is the
descriptive context.

## Searching

A "programmer" searches for a useful entry point, which may be found in his own project or in
another project. He initiates the search in the code context in which he expects to use the entry
point. He provides a "query", which is search text in Google style.

Query results are entry points, ranked by the following factors, shown roughly from highest priority down:

* Query match to entry point documentation.

* Query match to combined descriptive context across all references to the entry point.

* Query code context match to combined code context across all references to the entry point.

* Authority of the project that provides the entry point.
