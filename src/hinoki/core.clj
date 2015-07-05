(ns hinoki.core
  (:require [clj-http.client :as client]
            [ring.util.response :use get-header]))

(defn get-specific-header [request header]
  (get-header request header))

(defn get-correlations [request]
  (hash-map :trace (get-specific-header request "trace") :span (get-specific-header request "span") :parent (get-specific-header request "parent")))

(defn first-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str (get-correlations request))})

(defn second-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "2 two"})

(defn make-correlation []
  (hash-map :trace "me" :span "you" :parent "them"))

(defn begin []
  (client/get "http://localhost:11111"
              {:headers (make-correlation)}))

