(defproject advent-of-clojure "0.1.0-SNAPSHOT"
  :description "My solutions to Advent of Code: http://adventofcode.com/"
  :url "https://github.com/jumarko/advent-of-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-RC1"]]
  :main ^:skip-aot advent-of-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
