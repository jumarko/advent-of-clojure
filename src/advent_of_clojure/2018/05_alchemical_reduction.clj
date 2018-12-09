(ns advent-of-clojure.2018.05-alchemical-reduction
  "https://adventofcode.com/2018/day/5"
  (:require [advent-of-clojure.2018.input :as io]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]))

;;;; Suit's material is composed from extremely long polymers.
;;;; Polymer consists of smaller subunits which, when triggered,
;;;; react with each other -> adjacent units of opposite polarity are destroyed;
;;;; e.g. `r` and `R` are units with the same type but opposite polarity.

(defn fixed-point
  "Finds a fixed point of given function,
  that is a point at which the function keeps returning the same value"
  [f start-value]
  (let [iter (fn [old new]
               (if (= old new)
                 new
                 (recur new (f new))))]
    (iter f start-value)))

(defn opposite-polarity?
  [a b]
  (and (not= a b)
       (= (str/lower-case a) (str/lower-case b))))

(defn polymer-reduction-1
  "Performs a single polymer reduction."
  [polymer]
  (loop [polymer polymer
         reduced-polymer ""]
    (let [[curr-unit next-unit] polymer]
      (if-not next-unit
        (str reduced-polymer curr-unit)
        (if (opposite-polarity? curr-unit next-unit)
          (recur (subs polymer 2)
                 reduced-polymer)
          (recur (subs polymer 1)
                 (str reduced-polymer curr-unit)))))))


;; doesn't seem to be a good fit for reduce...
#_(reduce
 (fn [[reduced-polymer prev-unit] curr-unit]
   (cond
     (nil? prev-unit)
     [reduced-polymer curr-unit]

     (opposite-polarity? prev-unit curr-unit)
     [reduced-polymer nil]

     :else
     [(str reduced-polymer prev-unit) curr-unit]))
 nil
 "abBA")

(defn polymer-reduction [polymer]
  (fixed-point polymer-reduction-1 polymer))

(defn puzzle1 []
  (io/with-input "05_input.txt" polymer-reduction))

(deftest polymer-reduction-test
  (testing "units with same type but opposing polarity are destroyed"
    (is (empty?
         (polymer-reduction "aA"))))
  (testing "destroy repeated"
    (is (empty?
         (polymer-reduction "abBA"))))
  (testing "nothing happens when adjacent units are of the same polarity"
    (is (= "aabAAB"
           (polymer-reduction "aabAAB"))))
  (testing "more complex example of reduction"
    (is (= "dabCBAcaDA"
           (polymer-reduction "dabAcCaCBAcCcaDA"))))
  (testing "real input"
    (is (= ""
           (puzzle1)))))
