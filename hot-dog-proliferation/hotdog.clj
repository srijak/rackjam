;; First pass brute force solution.
;;  (first ever clojure code :))
;; TODO: make it fast enough for large input.

(ns hotdog
  (:use [clojure.contrib.duck-streams :only (read-lines)])
  (:use [clojure.contrib.string :only (split)]))

(defn read-file [f] 
  (read-lines f)
)

(defn getMap [xs]
  (zipmap (take-nth 2 xs) (take-nth 2 (rest xs)))
)

(defn getTestCase [file]
  (let [count (. Integer parseInt (first file))
       [cur, others] (split-at count (rest file))
       curInts (split #"\s+" (apply str (interpose " " cur)))]
       [(map #(. Integer parseInt %) curInts), others]
  )
)

(defn getKeyGreaterThanN [m ks n]
  (if (empty? ks)
    nil
    (let [curval (get m (first ks))]
      (if (> curval n)
          (first ks)
          (getKeyGreaterThanN m (rest ks) n)
      )
    )
  )
)
(defn getExplodable [m]
  (loop [k (keys m)]
    (if (empty? k)
      nil
      (if (> (get m (first k)) 1)
        (first k)
        (recur (rest k)))))
)

(defn explode [m idx]
  (if (not= idx nil)
    (let [right (merge-with + { (+ idx 1) 1} m)
        left  (merge-with + { (- idx 1) 1} right)
        mid   (merge-with + {idx -2} left)]
        mid)
  )
)

(defn solve [currentCase]
  (loop [case currentCase i 0]
    ;;(println "step: " i)
    ;;(println "\tcase: " (into (sorted-map) case))
    (let [toExplode (getExplodable case)]
      (if (= toExplode nil)
        i
        (recur (explode case toExplode) (+ i 1)))
    )
  )
)

(defn process [file testcases current]
  (if (> testcases current)
    (let [[currentCase, others] (getTestCase file)
          caseMap (getMap currentCase)]
      (println (format "Case #%d: %d" current (solve caseMap)))
      (process others testcases (+ 1 current))
    ))
)
(defn main [args] 
  (let [fileName (first (first args))
        file (read-file fileName)
        testcases (+ 1 (. Integer parseInt (first file)))]
        (process (rest file) testcases 1)
  )   
)


(defn command-line? []                               
  (.isAbsolute (java.io.File. *file*)))

(if (command-line?) (main [*command-line-args*] ))
