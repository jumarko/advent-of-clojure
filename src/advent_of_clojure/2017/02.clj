(ns advent-of-clojure.2017.02
  "Day 2 of Advent of Clojure 2017 - Corruption Checksum: http://adventofcode.com/2017/day/2

  Part I:
  ------------------------------------------------------------------------------
  As you walk through the door, a glowing humanoid shape yells in your direction.
  \"You there! Your state appears to be idle. Come help us repair the corruption in this spreadsheet
  - if we take another millisecond, we'll have to display an hourglass cursor!\"

  The spreadsheet consists of rows of apparently-random numbers.
  To make sure the recovery process is on the right track,
  they need you to calculate the spreadsheet's checksum.
  For each row, determine the difference between the largest value and the smallest value;
  the checksum is the sum of all of these differences.

  For example, given the following spreadsheet:
    5 1 9 5
    7 5 3
    2 4 6 8
  The first row's largest and smallest values are 9 and 1, and their difference is 8.
  The second row's largest and smallest values are 7 and 3, and their difference is 4.
  The third row's difference is 6.
  In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.

  What is the checksum for the spreadsheet in your puzzle input? (http://adventofcode.com/2017/day/2/input)

  Part II:
  ------------------------------------------------------------------------------
  ")


(defn- read-input
  "Reads input from given file and returns it as a sequence of digits (numbers 0-9)."
  [file-path]
  (with-open [rdr (clojure.java.io/reader file-path)]
    ;; force sequence eval with `mapv`
    (mapv (fn [row]
            (mapv (fn [num-string] (Long/parseLong num-string))
                  (clojure.string/split row #"\s+")))
     (line-seq rdr))))

(def input-rows (read-input "src/advent_of_clojure/2017/02_input.txt"))

;;; PART I:
;;;
(defn- checksum
  "Computes the checksum of all given rows represented as a sequence of number sequences."
  [spreadsheet-rows]

  (apply + 
         (for [row spreadsheet-rows]
           (let [max-num (apply max row)
                 min-num (apply min row)]
             (- max-num min-num)))))
  
(checksum [[5 1 9 5]
           [7 5 3]
           [2 4 6 8]])
;;=> 18
(checksum input-rows)
;;=> 51833
