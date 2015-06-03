# codemining-clojure

Experimental implementation of "code mining", as defined in the
[World Wide Hack Manifesto](https://github.com/WorldWideHack/manifesto).

For an overview of use cases and architecture, see [doc/intro.md](doc/intro.md).

## Clojure as primary implementation language

I am using Clojure as the primary implementation language for code mining for the following
reasons:

* I am intrigued by Clojure's blend of functional/immutable bias and brutal pragmatism.
 
* Currently I am a programming team of one, so Clojure's focus on personal productivity is attractive.

* Clojure is an appealing second target for code mining, as described below.

* My friend [Jim Brikman](http://www.ybrikman.com) is intrigued by Clojure, 
  so this improves my odds of sucking him into the project one day.
  
## Rust as first target

Rust is the first target for code mining, for the following reasons:

* Rust is a very young but promising community exploring innovative ideas
  that are relevant to the World Wide Hack.
  
* Rust is a candidate primary language for the World Wide Hack.
  
* The Rust compiler is moving toward supporting strong code analysis APIs.

* The Rust ecosystem is tiny right now, which makes it a great first target for a new project.

## Clojure as intended second target

Here are some motivations for making Clojure the second target for code mining:

* Clojure is a young but rapidly growing community exploring innovative ideas
  that are relevant to the World Wide Hack.

* Clojure is strong at analyzing Clojure code. Partly this is because it is homoiconic, 
  but largely it is the existence of [tools.analyzer](https://github.com/clojure/tools.analyzer)
  and work on [Typed Clojure](http://typedclojure.org).

* Clojure is well architected for composability: it has a bias toward immutability and pure
  functions, a pragmatic approach to state, and good tools for abstraction.

* Clojure's REPL is an easy first target for code-mining capabilities.

* Clojure's dynamic, interactive nature and its homoiconicity make it a natural, easy
  environment for experimenting with learnable programming techniques.
  
* The Clojure ecosystem is still small enough to be a manageable second project (unlike a language
  like Java that would get us into web-scale data management).

## License

Copyright © 2014 Dean Thompson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
