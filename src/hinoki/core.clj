(ns hinoki.core
  (:require [clj-http.client :as client]
            [ring.util.response :use get-header]))

(defn get-correlations [request]
  (hash-map :trace (get-header request "trace") :span (get-header request "span") :parent (get-header request "parent")))

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

