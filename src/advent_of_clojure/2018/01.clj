(ns advent-of-clojure.2018.01
  "https://adventofcode.com/2018/day/1"
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest testing is]]))

;;; Puzzle 1
;;; input: https://adventofcode.com/2018/day/1/input
;;; saved to 01_input.txt because `slurp` doesn't work with HTTPS

(defn- to-numbers [lines]
  (map #(Integer/parseInt %) lines))

(defn- compute-puzzle1 [numbers]
  (reduce + numbers))

(defn puzzle1 [input-reader]
  (-> input-reader
      line-seq
      to-numbers
      compute-puzzle1))

(defn puzzle1-main []
  (with-open [input-reader (io/reader "src/advent_of_clojure/2018/01_input.txt")]
    (puzzle1 input-reader)))

(deftest puzzle1-test
  (testing "dummy test"
    (is (= 11
           (puzzle1 (io/reader (java.io.StringReader. "+3\n-15\n+23"))))))
  (testing "real input"
    (is (= 484
           (puzzle1-main)))))
