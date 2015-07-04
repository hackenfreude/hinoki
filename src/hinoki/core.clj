(ns hinoki.core)

(defn first-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "1 one"})

(defn second-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "2 two"})

