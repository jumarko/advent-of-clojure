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

(def testing-claims
  [{:id 1 :left 1 :top 3 :width 4 :height 4}
   {:id 2 :left 3 :top 1 :width 4 :height 4}
   {:id 3 :left 5 :top 5 :width 2 :height 2}])
(def testing-claim-str "#10 @ 108,350: 22x29")

(def claim-pattern #"#(\d+)\s+@\s+(\d+),(\d+):\s+(\d+)x(\d+)*")

(defn str-to-claim [claim-str]
  (let [[id left-distance top-distance width height :as data] (rest (re-matches claim-pattern claim-str))]
    (when (seq data) 
      {:id id
       :left (Integer/valueOf left-distance)
       :top (Integer/valueOf top-distance)
       :width (Integer/valueOf width)
       :height (Integer/valueOf height)})))

(def testing-claim (str-to-claim testing-claim-str))
;; => {:id "10", :left 108, :top 350, :width 22, :height 29}

(defn claim-to-squares [{:keys [left top width height]}]
  (for [dx (range width)
        dy (range height)
        :let [x (inc (+ left dx))
              y (inc (+ top dy))]]
    [x y]))
(claim-to-squares (first testing-claims))

(defn overlapping-squares [claims]
  (let [all-claims (vec claims)]
    (->> all-claims
         (mapcat claim-to-squares)
         frequencies
         (filter (fn [[square freq]] (> freq 1)))
         (map (fn [[square freq]] square)))))

#_(overlapping-squares testing-claims)

(defn puzzle1 []
  (io/with-input "03_input.txt" overlapping-squares str-to-claim))

(deftest puzzle1-test
  (testing "dummy test"
    (is (= 4 (count (overlapping-squares testing-claims)))))
  (testing "real input"
    (is (= 107663 (count (puzzle1))))))


(comment

  ;; first try to parse the input
  ;; Regex seems to be a straightforward solution to this
  ;; => see `claim-pattern`

  (puzzle1)

  ;; the first try with overlapping-squares -> generating squares and their frequencies
  ;; for all claims (idea similar to the game of life neighbors)
  (time (puzzle1))
  ;; "Elapsed time: 3824.114837 msecs"

  )
