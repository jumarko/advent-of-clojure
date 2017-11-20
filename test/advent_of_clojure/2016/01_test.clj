(ns advent-of-clojure.2016.01-test
  (:require [advent-of-clojure.2016.01 :as sut]
            [clojure.test :refer :all]))

(deftest shortest-path
  (testing "ideal path"
    (is (= 5
           (sut/block-distance ["L2" "R3"]))))
  (testing "very bad path"
    (is (= 2
           (sut/block-distance ["R2" "R2" "R2"]))))
  (testing "Quiz path"
    (is (= 273
           (sut/block-distance sut/steps-1)))))

(deftest headquarter-distance
  (testing "shortest path to headquarter"
    (is (= 4
           (sut/headquarter-distance ["R8" "R4" "R4" "R8"])))))
