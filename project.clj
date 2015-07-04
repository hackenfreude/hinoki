(defproject hinoki "0.1.0-SNAPSHOT"
  :description "a project to learn about web and logging"
  :license {:name "The MIT License"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :plugins [[lein-midje "3.1.3"]]
  :test-paths ["test"])

