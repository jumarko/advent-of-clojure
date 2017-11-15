(ns advent-of-clojure.2016.01
  "Day 1: No time for a taxicab: http://adventofcode.com/2016/day/1"
  (:require [clojure.spec.alpha :as s]))

;;; https://en.wikipedia.org/wiki/Taxicab_geometry


(defn parse-steps
  "Parses steps represented as a single string into the collection of steps (strings)."
  [steps-string]
  (clojure.string/split steps-string #", "))

;; Puzzle 1 input: http://adventofcode.com/2016/day/1/input
(def steps-1 
  (parse-steps "L5, R1, R3, L4, R3, R1, L3, L2, R3, L5, L1, L2, R5, L1, R5, R1, L4, R1, R3, L4, L1, R2, R5, R3, R1, R1, L1, R1, L1, L2, L1, R2, L5, L188, L4, R1, R4, L3, R47, R1, L1, R77, R5, L2, R1, L2, R4, L5, L1, R3, R187, L4, L3, L3, R2, L3, L5, L4, L4, R1, R5, L4, L3, L3, L3, L2, L5, R1, L2, R5, L3, L4, R4, L5, R3, R4, L2, L1, L4, R1, L3, R1, R3, L2, R1, R4, R5, L3, R5, R3, L3, R4, L2, L5, L1, L1, R3, R1, L4, R3, R3, L2, R5, R4, R1, R3, L4, R3, R3, L2, L4, L5, R1, L4, L5, R4, L2, L1, L3, L3, L5, R3, L4, L3, R5, R4, R2, L4, R2, R3, L3, R4, L1, L3, R2, R1, R5, L4, L5, L5, R4, L5, L2, L4, R4, R4, R1, L3, L2, L4, R3"))

(defn- valid-step
  "Checks whether given step represented as a string is valid
  and if so returns its two components:
  direction - either \"L\" or \"R\"
  number of steps - integer"
  [step]
  (when-let [[_ direction step-count] (re-find #"(^[L|R])(\d+$)" step)]
    [direction
     (Integer/parseInt step-count)]))
#_(valid-step "L5")
#_(valid-step "L51")
#_(valid-step "D5")

(s/def ::step valid-step)
(s/def ::steps (s/coll-of ::step))


(def faces {:N [:W :E]
            :E [:N :S]
            :S [:E :W] 
            :W [:S :N]})
(defn next-location
  ;; face-direction is one of `faces`
  [[face-direction x y] [step-direction step-count]]
  (let [new-face-direction 
        (case step-direction
          "L" (first (faces face-direction))
          "R" (second (faces face-direction)))]
    (case new-face-direction
      :N [:N x (+ y step-count)]
      :E [:E (+ x step-count) y]
      :S [:S x (- y step-count)]
      :W [:W (- x step-count) y]))
  )

(s/fdef block-distance
        :args (s/cat :steps ::steps))
(defn block-distance
  "Given the sequence of steps of form Ln (n blocks to the left)
  and Rn(n blocks to the right)
  compute the shortest block distance from the beginning.
  See https://en.wikipedia.org/wiki/Taxicab_geometry."
  [steps]
  (let [[_ x-final y-final]
        (reduce next-location
                [:N 0 0]
                (map valid-step steps))]
    (+ (Math/abs x-final) (Math/abs y-final))))

(block-distance steps-1)



;;; Second part:
;;; The headquarter of Easter Bunny is at the first location you visit twice

