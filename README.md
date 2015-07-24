# codemining

Experimental implementation of "code mining", as defined in the
[World Wide Hack Manifesto](https://github.com/WorldWideHack/manifesto).

Right now, this is almost entirely ideas rather than code. For ideas about the implementation approach, see [doc/implementation_ideas.md](doc/implementation_ideas.md).

## Haskell as primary implementation language

I am using Haskell as the primary implementation language for code mining because, 
although I worry about its complexity, I believe it is the best statically typed
functional language available today.
  
## Rust as first target

Rust is the first target for code mining, for the following reasons:

* Rust is a very young but promising community exploring innovative ideas
  that are relevant to the World Wide Hack.
  
* Rust is a candidate primary language for the World Wide Hack.
  
* The Rust compiler is moving toward supporting strong code analysis APIs.

* The Rust ecosystem is tiny right now, which makes it a great first target for a new project.
 
## Haskell as intended medium-term target

Here are some motivations for making Haskell a medium-term target for code mining:

* I would like to be eating my own dogfood.

* Although I see Haskell as too complex to be the primary language of the World-Wide Hack,
  and also as not suitable for the most important next wave of programming use cases
  (robotics, natural user interfaces, virtual reality, etc.), I believe it has many
  of the right ideas and would like to explore code mining in its context.

* I would like to contribute to Haskell development.

* Haskell analysis of Haskell code is well developed.

## Clojure as intended medium-term target

Here are some motivations for making Clojure a medium-term target for code mining:

* Clojure is a young but rapidly growing community exploring innovative ideas
  that are relevant to the World Wide Hack.

* Clojure is strong at analyzing Clojure code. Partly this is because it is homoiconic, 
  but largely it is the existence of [tools.analyzer](https://github.com/clojure/tools.analyzer)
  and Ambrose Bonnaire-Sergeant's work on [Typed Clojure](http://typedclojure.org).

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
