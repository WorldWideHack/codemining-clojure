# Code Mining Implementation Ideas

## Use Case: Rust Hub

As a simple initial use case, we will create rusthub.co with simple support for package/author rankings.
This doesn't involve any code analysis -- just search and authority analysis at the package/dependency level.

## Use Case: Clojure Hub

Similar to Rust Hub, on clojurehub.co.

## Use Case: Clojure REPL

As an early proving ground, we will enable a programmer in a Clojure REPL to search for a suitable
function for a particular purpose. 

For example, suppose the programmer is in a REPL in a namespace that isn't using anything related to
bar charts. They type

    (how "bar chart")

Since Incanter is the most authoritative Clojure project that provides bar charts, the output looks something
like this:

    incanter.charts/bar-chart
    macro
    Usage: (bar-chart categories values & options)
     Returns a JFreeChart object representing a bar-chart of the given data.
    Use the 'view' function to display the chart, or the 'save' function
    to write it to a file.

    Arguments:
      categories -- a sequence of categories
      values -- a sequence of numeric values

    Options:
      :title (default '') main title
      :x-label (default 'Categories')
      :y-label (default 'Value')
      :series-label
      :legend (default false) prints legend
      :vertical (default true) the orientation of the plot
      :group-by (default nil) -- a vector of values used to group the values into
                                 series within each category.

    Examples:

      (use '(incanter core stats charts datasets))

      (with-data (get-dataset :co2)
        (view (bar-chart :Type :uptake
                         :title "CO2 Uptake"
                         :group-by :Treatment
                         :x-label "Grass Types" :y-label "Uptake"
                        :legend true)))


      (def data (get-dataset :airline-passengers))
      (view (bar-chart :year :passengers :group-by :month :legend true :data data))

      (with-data  (get-dataset :airline-passengers)
        (view (bar-chart :month :passengers :group-by :year :legend true)))

However, suppose another programmer is working on [interface.clj in sam-white/sym-regression](https://github.com/sam-white/sym-regression/blob/7862c28639067f8f174cbf1e6d2f97ec0c33d1be/interface.clj)
and wants to produce a bar chart. They also type

    (how "bar chart")

but datamining-clojure sees that they are using gorilla-plot. So their output looks something like
this:

    gorilla-plot/bar-chart
    function
    Usage: (bar-chart [categories values & {:keys [plot-size aspect-ratio colour color plot-range opacity]
                                 :or   {plot-size    400
                                        aspect-ratio 1.618
                                        plot-range   [:all :all]
                                        opacity      1
                                        }}])

## Upcoming Use Case: API for Clojure Development Environments

Once we get the simple `how` function working in a REPL, we will deploy it in a free, public
HTTP/JSON service and get it integrated into as many Clojure development environments as possible.

## Additional Use Cases Over Time

Over time, we would like to extend datamining-clojure to additional use cases such as the following.

* within a Clojure development environment

  * While editing source code, either an auto-complete-style gesture or an automatic background
     process suggests functions relevant to data values in scope.

  * Define a function with `pre` and `post` conditions but an empty body, and have datamining-clojure suggest a body.

* support Java

* support other languages

## Overview of the Conceptual Architecture

A "project" is initially defined by a GitHub repo.

A project has "entry points" -- public protocols, functions, etc. Each entry point may have
"documentation" -- human-readable text describing that entry point.

One project can "reference" the entry point of another. A "reference" is a single mention in one
project of an entry point in another project. (A project cannot reference itself in this model.)

More specifically, a project references entry points through its "files".

Other entities can "reference" a project. One early example will be a Stack Exchange answer. Then we
may use a broader web crawl, such as by filtering [Common Crawl](http://commoncrawl.org) down to web
pages containing Clojure code.

A project has "versions". We are going to try not to think too much about this too soon, nor too
late.

An "author" is initially defined by a GitHub user.

## Relevance Counterpart to Page-Rank 

We need some sort of transitive-authority mechanism that would be the counterpart to page-rank in
this domain. Eventually, this will no doubt become mathematically complex. Before we go there,
however, let's get experience with a simple, intutive algorithm. The real-world dynamics of
transitive software references may be quite different from the dynamics of transitive HTML links.

Here is a starting point:

A project has "value", which we define for now to be the number of references to that project in
(the current versions of) other projects.

An entry point also has "value", defined the same way as for a project.

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

Query results are entry points. Here are some candidate relevance signals, starting with the ones we
suspect will be the strongest:

* query match to entry point symbols

* query match to entry point documentation

* authority of the entry point's project

* value of the entry point

* value of the entry point's project

* query match to combined descriptive context across a sample of references to the entry point

* query code context match to combined code context across a sample of references to the entry point

## Implementation Overview

The main activities that must be supported by the implementation architecture are

* fetching inputs

  * Clojure GitHub repos

  * Stack Exchange answers

  * Other web pages

* parsing inputs

  * identifying all references in a Clojure GitHub repo

  * resolving references to the projects that define them and the referenced entry points

  * identifying committers to a GitHub repo and gathering statistics for them

  * separating Clojure code, relevant human-readable text, and junk from a Stack Exchange answer

  * separating Clojure code, relevant human-readable text, and junk from an arbitary web page

* gathering our index inputs by entry point

  * Conceptually, this is the "map" in map-reduce: the key is a unique identifier for the entry point.

  * [Apache Spark](https://spark.apache.org) is the leading candidate, perhaps [running on Elastic Map-Reduce](https://aws.amazon.com/articles/4926593393724923).

  * indexing entry points

* This is the "reduce" in our map-reduce.

  * [Lucene](http://lucene.apache.org) is the leading candidate. It probably wins over something like
     [Amazon CloudSearch](http://aws.amazon.com/cloudsearch/) due to giving us more fine-grain control
     over the indexing and search algorithms.
