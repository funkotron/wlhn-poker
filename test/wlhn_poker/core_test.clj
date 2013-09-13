(ns wlhn-poker.core-test
  (:use clojure.test
        wlhn-poker.core))

(deftest straight?-test
  (is (straight? [[:clubs 12]
                  [:clubs 10]
                  [:hearts 9]
                  [:diamonds 11]
                  [:clubs 8]]))
  (is (not (straight? [[:clubs 12]
                       [:diamonds 3]
                       [:clubs 10]
                       [:hearts 9]
                       [:clubs 8]]))))

(deftest flush?-test
  (is (flush? [[:clubs 12]
               [:clubs 4]
               [:clubs 9]
               [:clubs 5]
               [:clubs 8]]))
  (is (not (flush? [[:clubs 12]
                    [:diamonds 3]
                    [:clubs 10]
                    [:hearts 9]
                    [:clubs 8]]))))

(deftest royal-flush?-test
  (is (royal-flush? [[:clubs 14]
                     [:clubs 13]
                     [:clubs 12]
                     [:clubs 11]
                     [:clubs 10]]))
  (is (not (royal-flush? [[:clubs 12]
                          [:diamonds 3]
                          [:clubs 10]
                          [:hearts 9]
                          [:clubs 8]]))))

(deftest of-a-kind?-test
  (is (of-a-kind? 4 [[:clubs 9]
                     [:diamonds 9]
                     [:spades 9]
                     [:hearts 9]
                     [:clubs 8]]))
  (is (of-a-kind? 3 [[:clubs 7]
                     [:diamonds 9]
                     [:spades 9]
                     [:hearts 9]
                     [:clubs 8]]))
  (is (not (of-a-kind? 3 [[:clubs 7]
                          [:diamonds 9]
                          [:spades 2]
                          [:hearts 9]
                          [:clubs 8]]))))

(deftest full-house?-test
  (is (full-house? [[:clubs 9]
                    [:diamonds 9]
                    [:spades 9]
                    [:hearts 8]
                    [:clubs 8]]))
  (is (not (full-house? [[:clubs 9]
                         [:diamonds 9]
                         [:spades 9]
                         [:hearts 9]
                         [:clubs 2]]))))

(deftest two-pair?-test
  (is (two-pair? [[:clubs 9]
                  [:diamonds 9]
                  [:spades 4]
                  [:hearts 4]
                  [:clubs 8]]))
  (is (not (two-pair? [[:clubs 9]
                       [:diamonds 9]
                       [:spades 8]
                       [:hearts 3]
                       [:clubs 2]])))
  (is (not (two-pair? [[:clubs 9]
                       [:diamonds 9]
                       [:spades 9]
                       [:hearts 3]
                       [:clubs 3]]))))
(deftest highest-test
  (is (= (highest [[:clubs 9]
                 [:diamonds 10]
                 [:spades 4]
                 [:hearts 4]
                 [:clubs 8]])
         10)))
