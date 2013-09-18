(ns wlhn-poker.core
  (:require [clojure.pprint :refer [pprint]]))


(def pack
  (for [suit #{:clubs :hearts :spades :diamonds}
        pip (range 2 15)]
    [suit pip]))

(defn straight?
  [hand]
  (let [[lowest-pip & _ :as sorted-pips] (sort (map second hand))]
    (= (take 5 (iterate inc lowest-pip))
       sorted-pips)))

(defn flush?
  [hand]
  (->> hand
       (map first)
       (apply hash-set)
       count
       (= 1)))

(defn of-a-kind?
  [n hand]
  (->> hand
       (map second)
       frequencies
       (map second)
       (some (partial = n))))

(defn full-house?
  [hand]
  (and (of-a-kind? 3 hand)
       (of-a-kind? 2 hand)))

(defn straight-flush?
  [hand]
  (and (straight? hand)
       (flush? hand)))

(defn royal-flush?
  [hand]
  (and (straight-flush? hand)
       (some (partial = 14)
             (map second hand))))

(defn two-pair?
  [hand]
  (->> hand
       (map second)
       frequencies
       (map second)
       (filter (partial = 2))
       count
       (= 2)))

(defn highest
  [hand]
  (->> hand
       (map second)
       (apply max)))

(defn remove-highest
  [hand]
  (remove #(= (second %) (highest hand)) hand))

(defn highest-kicker [hand1 hand2]
  (loop [hand1' hand1
         hand2' hand2]
    (cond
     (> (highest hand1') (highest hand2'))
     hand1
     (< (highest hand1') (highest hand2'))
     hand2
     (= (count hand1') 1)
     :draw
     (= (highest hand1') (highest hand2'))
     (recur (remove-highest hand1') (remove-highest hand2')))))

(defn score-hand
  [hand]
  (cond
   (royal-flush? hand) 110
   (straight-flush? hand) 109
   (of-a-kind? 4 hand) 107
   (full-house? hand) 106
   (flush? hand) 105
   (straight? hand) 104
   (of-a-kind? 3 hand) 103
   (two-pair? hand) 102
   (of-a-kind? 2 hand) 101
   :else (highest hand)))

(defn hands [n deck]
  (->> deck
       (partition-all 5)
       (take n)))

(defn compare-hands [hand1 hand2]
  (cond
   (> (score-hand hand1) (score-hand hand2))
    hand1
   (= (score-hand hand1) (score-hand hand2))
    (highest-kicker hand1 hand2)
   :else hand2))

(defn winner [hands]
  (reduce compare-hands hands))

(defn score-five [] 
  (dotimes [_ 10]
    (->> (shuffle pack)
         (take 5)
         ((juxt score-hand identity))
         pprint)))

(defn flush-me []
  (time
   (pprint
    (loop [n 0]
      (let [hand (take 5 (shuffle pack))]
        (if (royal-flush? hand)
          n
          (recur (inc n))))))))
