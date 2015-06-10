(ns codemining.sourceunit
  (:require [schema.core :as sch])
  (:import (org.joda.time DateTime)))

(def Dependency
  {:id sch/Str})

(def SourceUnit
  {:id           sch/Str
   :name         sch/Str
   :updated_at   DateTime
   :dependencies [Dependency]})

