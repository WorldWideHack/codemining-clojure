(ns codemining.util
  (:require [clojure.algo.monads :as m]))

(defmacro when-maybe
  "Like `when-let` except there can be multiple bindings. If any binding value
   is nil, the remainder (plus the body) are not evaluated.

   Note: unlike `when-let`, a `false` value does not short-circuit evaluation.

   Picked up [from Brian Marick](https://gist.github.com/marick/6b3a9a38584737b346ce)."
  [bindings & body]
  `(m/domonad m/maybe-m ~bindings (do ~@body)))
