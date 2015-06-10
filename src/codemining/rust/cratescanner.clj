(ns codemining.rust.cratescanner
  (:use codemining.util codemining.sourceunit)
  (:require [schema.core :as sch]
            [clojure.algo.monads :as m]
            [midje.sweet :refer [unfinished fact => provided]]
            [clojure.data.json :as json]
            [clj-http.client :as http]
            [taoensso.timbre :refer [errorf spy]]
            [clj-time.format :as time-format]))

(def CrateJson {:id sch/Str
                :name sch/Str
                :max_version sch/Str
                sch/Keyword  sch/Any
                })

(def CrateDependency {:crate_id sch/Str})

(def ^:dynamic *crates-io-base-url* "https://crates.io")

(def crates-io-api-path "/api/v1")

(def ^:dynamic *crates-io-api-url* (str *crates-io-base-url* crates-io-api-path))

(defn- crates-page-url [page per-page]
  (str *crates-io-api-url* "/crates?page=" page "&per_page=" per-page))

; We don't use the hyperlink provided by the API because, as of this writing,
; it is nil when a single crate is retrieved by URL.
(defn- versions-link [cj] (str crates-io-api-path "/crates/" (:id cj) "/versions"))

(defn- fetch-json
  "Returns a JSON data structure for the data at url,
   or nil in case of error."
  [url]
  (try
    (let [response (http/get url {:accept :json :throw-exceptions false})
          status (:status response)]
      (if (= status 200)
        (json/read-str (:body response) :key-fn keyword)
        (errorf "HTTP status %d reading %s" status url)))
    (catch Throwable t
      (errorf t "exception reading %s" url))))

(defn- follow-link
  "Returns a JSON data structure for the data at path relative to *crates-io-base-url*,
   or nil in case of error."
  [path]
  (fetch-json (str *crates-io-base-url* path)))

(fact "follow-link correctly returns data for the 'accumulator' crate"
      (-> "/api/v1/crates/accumulator" follow-link :crate :name) => "accumulator")

(def crates-io-datetime-formatter (time-format/formatters :date-time-no-ms))

(defn- parse-date-time [s]
  (when s
    (time-format/parse crates-io-datetime-formatter s)))

(defn- fetch-num-crates []
  (-> (crates-page-url 1 2) fetch-json :meta :total (or 0)))

(defn fetch-latest-dependencies
  "Fetch the dependencies for the latest version of the crate
  represented by json j"
  [cj]
  {:pre  [(sch/validate CrateJson cj)]
   :post [(sch/validate [CrateDependency] %)]}
  (when-maybe [from-version (:max_version cj)
               versions (-> cj versions-link follow-link :versions)
               latest-version (some #(when (= (:num %) from-version) %) versions)
               dependencies-link (-> latest-version :links :dependencies)
               dependencies (follow-link dependencies-link)]
              (->> dependencies :dependencies (map #(select-keys % [:crate_id])))))

(fact "fetch-latest-dependencies returns plausible dependencies for the 'accumulator' crate"
      (-> "/api/v1/crates/accumulator" follow-link :crate fetch-latest-dependencies)
      => #(and (sch/validate [CrateDependency] %)
               (some (partial = "rand") (map :crate_id %))))

(defn- make-sourceunit-from-crate-json
  "Transforms raw crate JSON j, from crates.io, into a SourceUnit.
  This involves following hypermedia links in j to load additional HTTP pages."
  [cj]
  {:pre  [(sch/validate CrateJson cj)]
   :post [(sch/validate SourceUnit %)]}
  (-> cj
      (select-keys [:id :name])
      (assoc :updated_at (-> cj :updated_at parse-date-time))
      (assoc :dependencies
             (->> cj
                  fetch-latest-dependencies
                  (map (fn [dep] {:id (:crate_id dep)}))))))

(fact "make-sourceunit-from-crate-json works for the 'accumulator' crate"
      (-> "/api/v1/crates/accumulator" follow-link :crate make-sourceunit-from-crate-json)
      => #(and (sch/validate SourceUnit %)
               (= (:id %) "accumulator")
               (= (:name %) "accumulator")
               (some (partial = "rand") (map :id (:dependencies %)))))

