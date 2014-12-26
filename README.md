# codemining-clojure

Experimental implementation of "code mining", as defined in the
[World Wide Hack Manifesto](https://github.com/WorldWideHack/manifesto).

For an overview of use cases and architecture, see [doc/intro.md](doc/intro.md).

Here are some motivations behind using Clojure as the first target for code mining:

* Clojure is strong at analyzing Clojure code. Partly this is because it is homoiconic, 
  but largely it is the existence of [tools.analyzer](https://github.com/clojure/tools.analyzer).

* Clojure is well architected for composability: it has a bias toward immutability and pure
  functions, a pragmatic approach to state, and good tools for abstraction.

* Clojure's REPL is an easy first target for code-mining capabilities.

* Clojure's dynamic, interactive nature and its homoiconicity make it a natural, easy
  environment for experimenting with learnable programming techniques.

## License

Copyright © 2014 Dean Thompson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
