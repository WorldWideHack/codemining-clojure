(ns codemining.rust.cratescanner.core
  (require [schema.core :as s
            :refer [validate]]
           [midje.sweet :as m
            :refer [unfinished fact => provided]]
           [clojure.data.json :as json]
           [clj-http.client :as http]
           [dire.core :refer [with-handler!]]
           [taoensso.timbre :as timbre
            :refer (log trace debug info warn error fatal report
                        logf tracef debugf infof warnf errorf fatalf reportf spy)]))

(def crates-io-base-url "https://crates.io/api/v1/")

(defn crates-page-url [page per-page & {:keys [base-url]
                                        :or   {base-url crates-io-base-url}}]
  (str base-url "crates?page=" page "&per_page=" per-page))

(defn fetch-crates-page-json [page per-page & opts]
  "Returns a JSON data structure for a crates page from crates.io,
   or nil in case of error."
  (let [url (apply crates-page-url page per-page opts)
        response (http/get url {:accept :json})
        status (:status response)]
    (if (= status 200)
      (json/read-str (:body response))
      (errorf "HTTP status %d reading %s" status url))))

(with-handler! #'fetch-crates-page-json
               Throwable
               (fn [e & args] (errorf e "Exception in fetch-crates-page-json %s" args)))
