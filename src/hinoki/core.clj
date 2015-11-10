(ns hinoki.core
  (:require [clj-http.client :as client]
            [ring.util.response :use get-header]
            [clj-uuid :as uuid]
            [clj-time.core :use now]
            [clj-time.format :as fmt]))

(defn get-trace [request]
  (get-header request "trace"))

(defn get-span [request]
  (get-header request "span"))

(def built-in-formatter
  (fmt/formatters :basic-date-time))

(defn timestamp []
  (fmt/unparse built-in-formatter (now)))

(defn better-log [ids caller step]
  (merge ids (hash-map :caller caller :step step :time (timestamp))))

(defn output-log [logmap]
  (let [entry (println-str (interleave (map name (keys logmap)) (repeat ":") (vals logmap) (repeat "|")))]
    (do
      (spit "test.log" entry :append true)
      (println entry))))

(defn step-ids [request]
  (hash-map :trace (get-trace request) :span (str (uuid/v1)) :parent (get-span request)))

(defn first-handler [request]
  (let [my-id (step-ids request)]
    (do
      (output-log (better-log my-id "first-handler" "ENTER"))
      (dotimes [n (rand-int 10)] (client/get "http://localhost:22222" {:headers my-id}))
      (output-log (better-log my-id "first-handler" "EXIT"))
      {:status 200})))

(defn second-handler [request]
  (let [my-id (step-ids request)]
    (do
      (output-log (better-log my-id "second-handler" "ENTER"))
      (Thread/sleep (* 1000 (rand-int 5)))
      (output-log (better-log my-id "second-handler" "EXIT"))
      {:status 200})))

(defn begin []
  (do
    (client/get "http://localhost:11111" {:headers (hash-map :trace (uuid/v1) :span (uuid/v1) :parent (uuid/null))})
    nil))

