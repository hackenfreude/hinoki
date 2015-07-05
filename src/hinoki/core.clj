(ns hinoki.core
  (:require [clj-http.client :as client]
            [ring.util.response :use get-header]
            [clj-uuid :as uuid]))

(defn get-trace [request]
  (get-header request "trace"))

(defn get-span [request]
  (get-header request "span"))

(defn get-parent [request]
  (get-header request "parent"))

(defn get-correlations [request]
  (hash-map :trace (get-trace request) :span (get-span request) :parent (get-parent request)))

(defn log-entry [request caller]
  (println "BEGIN: " caller (get-trace request)))

(defn first-handler [request]
  (do
    (log-entry request "first-handler")
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str (get-correlations request))}))

(defn second-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "2 two"})

(defn make-correlation []
  (hash-map :trace (uuid/v1) :span (uuid/v1) :parent (uuid/null)))

(defn begin []
  (client/get "http://localhost:11111"
              {:headers (make-correlation)}))

