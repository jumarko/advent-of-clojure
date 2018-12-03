(ns advent-of-clojure.2018.02-inventory-management
  "https://adventofcode.com/2018/day/2"
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-clojure.2018.input :as io]))

;;;; Inventory management system
;;;; Year 1518: Warehouse boxes


;;; Puzzle 1: Count checksum of candidate boxes
;;; Simply scan find number of boxes with an ID containing any of letters repeating twice;
;;; then find number of boxes with an ID containing any of letters repeating three times;
;;; then multiply these two numbers together

(defn checksum [ids]
  (let [ids-freqs (map #(-> % frequencies vals set) ids)
        count-freqs (fn [desired-freq] (count (filter #(contains? % desired-freq) ids-freqs)))
        twos-count (count-freqs 2)
        threes-count (count-freqs 3)]
    (* twos-count threes-count)))

(defn puzzle1 []
  (io/with-input "02_input.txt" checksum))

(deftest puzzle1-test
  (testing "Simple checksum"
    (is (= 12 (checksum ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]))))
  (testing "Real input"
    (is (= 4920 (puzzle1)))))


;;; Journal:
(comment

  ;; let's start with frequencies
  (map frequencies ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"])
  ;; ({\a 1, \b 1, \c 1, \d 1, \e 1, \f 1}
  ;;  {\b 3, \a 2, \c 1}
  ;;  {\a 1, \b 2, \c 1, \d 1, \e 1}
  ;;  {\a 1, \b 1, \c 3, \d 1}
  ;;  {\a 2, \b 1, \c 1, \d 2}
  ;;  {\a 1, \b 1, \c 1, \d 1, \e 2}
  ;;  {\a 3, \b 3})


  ;; filter only those with 2 or 3 frequency

  (->> ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
       (map frequencies)
       (filter (fn [id-freqs]
                 (some (fn [[char freq]] (<= 2 freq 3))
                       id-freqs))))
  ({\b 3, \a 2, \c 1}
   {\a 1, \b 2, \c 1, \d 1, \e 1}
   {\a 1, \b 1, \c 3, \d 1}
   {\a 2, \b 1, \c 1, \d 2}
   {\a 1, \b 1, \c 1, \d 1, \e 2}
   {\a 3, \b 3})

  ;; what if I use only vals and make them sets?
  (->> ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
       #_(map (comp frequencies vals distinct))
       (map #(-> % frequencies vals set)))
  ;; (#{1} #{1 3 2} #{1 2} #{1 3} #{1 2} #{1 2} #{3})

  ;; sum twos
  (->> ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
       (map #(-> % frequencies vals set))
       (filter #(contains? % 2)))
  ;; and sum threes
  (->> ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
       (map #(-> % frequencies vals set))
       (filter #(contains? % 3)))

  ;; let's put it together
  (let [ids ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
        ids-freqs (map #(-> % frequencies vals set) ids)
        count-freqs (fn [desired-freq] (count (filter #(contains? % desired-freq) ids-freqs)))
        twos-count (count-freqs 2)
        threes-count (count-freqs 3)]
    (* twos-count

       threes-count))


  )
