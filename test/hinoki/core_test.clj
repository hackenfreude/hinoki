(ns hinoki.core-test
  (:use midje.sweet)
  (:require [hinoki.core :refer :all]))

(fact "smoke test should pass"
      (smoke-test) => false)

