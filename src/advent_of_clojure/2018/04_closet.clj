(ns advent-of-clojure.2018.04-closet
  "https://adventofcode.com/2018/day/4"
  (:require
   [clojure.test :refer [deftest testing is]]
   [advent-of-clojure.2018.input :as io])
  (:import
   (java.time LocalDateTime)
   (java.time.format DateTimeFormatter DateTimeParseException)))


;;;; Supply closet - prototype suit manufacturing lab
;;;; Guards protecting the close changing shifts
;;;; always sleep somewhen between 00:00 and 00:59
;;;; Find the guards most likely to be asleep at a specific time


;;; Strategy/Puzzle 1: find ID of the guard that has the most minutes asleep
;;;   and multiply that by the minute that guard spent asleep the most.
;;; Note: the real input isn't sorted! https://adventofcode.com/2018/day/4/input


(def actions
  {"begins shift" :begins-shift
   "wakes up" :wakes-up
   "falls asleep" :falls-asleep})

(def shift-pattern #"\[([\d- :]+)\] (?:Guard #(\d+) )?(begins shift|wakes up|falls asleep)")

;; Check https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
(def datetime-format (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm"))

(defn str->date-time
  "Converts a string date-time representation to the instance of `LocalDateTime`
  or nil if the format isn't valid"
  [date-time-str]
  (try
    (LocalDateTime/parse date-time-str datetime-format)
    (catch DateTimeParseException e
      ;; ignore and return nil
)))
      

(str->date-time "1518-11-02 00:00")

(defn parse-shift
  "Parses single shift line which can be of following types:
    [1518-11-02 00:00] Guard #433 begins shift
    [1518-11-05 00:20] wakes up
    [1518-11-03 00:10] falls asleep
  "
  [shift-line]
  (when-let [[_ time guard-id action] (re-matches shift-pattern shift-line)]
    (let [proper-action (get actions action)
          proper-time (str->date-time time)]
      (when (and proper-action proper-time)
        (cond-> {:time (str->date-time time)
                 :action proper-action}
          guard-id (assoc :guard-id (Integer/parseInt guard-id)))))))

(parse-shift
 "[1518-11-02 00:00] Guard #433 begins shift")

(deftest parse-shift-test
  (testing "begins shift"
    (is (= {:time (LocalDateTime/parse "1518-11-02T00:00")
            :guard-id 433
            :action :begins-shift}
           (parse-shift
            "[1518-11-02 00:00] Guard #433 begins shift"))))
  ;; notice that for following two cases we don't have guard-id yet
  ;; => we must sort all records first and filled them later
  (testing "wakes up"
    (is (= {:time (LocalDateTime/parse "1518-11-02T00:40")
            :action :wakes-up}
           (parse-shift
            "[1518-11-02 00:40] wakes up"))))
  (testing "falls asleep"
    (is (= {:time (LocalDateTime/parse "1518-11-02T00:10")
            :action :falls-asleep}
           (parse-shift
            "[1518-11-02 00:10] falls asleep"))))
  (testing "invalid action"
    (is (nil? (parse-shift "[1518-11-02 00:20] yawning"))))
  (testing "invalid date format"
    (is (nil? (parse-shift "[1518-11 00:00] falls asleep")))))


#_(defn str-to-claim [claim-str]
  (let [[id left-distance top-distance width height :as data] (rest (re-matches claim-pattern claim-str))]
    (when (seq data) 
      {:id id
       :left (Integer/valueOf left-distance)
       :top (Integer/valueOf top-distance)
       :width (Integer/valueOf width)
       :height (Integer/valueOf height)})))

(defn most-sleeping-guard-minute [shifts])

(defn puzzle1
  "Finds the ID of the guard sleepting the most minutes
  and multiply that by the minute he's been sleeping the most times."
  []
  (io/with-input "04_input.txt" most-sleeping-guard-minute parse-shift))
