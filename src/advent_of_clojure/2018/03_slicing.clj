(ns advent-of-clojure.2018.03-slicing
  "https://adventofcode.com/2018/day/3"
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-clojure.2018.input :as io]))

;;;; Elves now have the fabric for Santa's clothes
;;;; but it's a very large square.
;;;; Everyone claims that another rectangular part of the fabric (claim)
;;;; is good for the clothes and there are _overlaps_ among claims.
;;;; Claim is represented as `#ID @ T,L: WxH`; e.g. `#123 @3,2: 5x4`.

;;; Puzzle
;;; Find out how many square inches are within two or more claims

(def testing-claim-str "#1 @ 108,350: 22x29")

(def claim-pattern #"#(\d)\s+@\s+(\d+),(\d+):\s+(\d+)x(\d+)*")

(defn str-to-claim [claim-str]
  (let [[id left-distance right-distance width height] (rest (re-matches claim-pattern claim-str))]
    {:id id
     :left (Integer/valueOf left-distance)
     :right (Integer/valueOf right-distance)
     :width (Integer/valueOf width)
     :height (Integer/valueOf height)}))

(str-to-claim testing-claim-str)
;; => {:id "1", :left 108, :right 350, :width 22, :height 29}

(defn overlapping-squares [claims])

(defn puzzle1 []
  (io/with-input "03_input.txt" overlapping-squares str-to-claim))

(deftest puzzle1-test
  (testing "dummy test"
    (is (= 11 (sum-frequencies [3 -15 23]))))
  (testing "real input"
    (is (= 484 (puzzle1)))))
