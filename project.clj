(defproject codemining "0.1.0-SNAPSHOT"
            :description "an exploratory implementation of Code Mining"
            :url "https://github.com/WorldWideHack/codemining"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [prismatic/schema "0.4.3"]
                           [org.clojure/data.json "0.2.6"]
                           [clj-http "1.1.2"]
                           [com.taoensso/timbre "4.0.0-beta4"]
                           [dire "0.5.3"]]
            :target-path "target/%s"
            :profiles {:uberjar {:aot :all}
                       :dev     {:dependencies [[midje "1.6.3"]]}})
