;; First pass brute force solution.
;;  (first ever clojure code :))
;; TODO: make it fast enough for large input.

(ns hotdog
  (:use [clojure.contrib.duck-streams :only (read-lines)])
  (:use [clojure.contrib.string :only (split)]))

(defn read-file [f] 
  (read-lines f)
)

(defn getTestCase [file]
  (let [ [cur, others] (split-at (. Integer parseInt (first file)) (rest file))]
       [(map #(. Integer parseInt %) (split #"\s+" (apply str (interpose " " cur)))), others] )
)

(defn getExplodable [m ks]
    (if (empty? ks)
      nil
      (if (> (get m (first ks)) 1)
        (first ks)
        (recur m (rest ks))))
)

(defn solve [currentCase i]
    (let [explodeIdx (getExplodable currentCase (keys currentCase))]
      (if (= explodeIdx nil)
        i
        (recur (merge-with + currentCase { (+ explodeIdx 1) 1, (- explodeIdx 1) 1, explodeIdx  -2}) (+ i 1))))
)

(defn process [file testcases current]
  (if (> testcases current)
    (let [[currentCase, others] (getTestCase file)]
      (println (format "Case #%d: %d"
                       (+ 1 current)
                       (solve (zipmap (take-nth 2 currentCase) (take-nth 2 (rest currentCase))) 0 )))
      (process others testcases (+ 1 current))))
)

(defn main [args] 
  (let [fileName (first (first args))
        file (read-file fileName)]
        (process (rest file) (. Integer parseInt (first file)) 0))   
)

(defn command-line? []                               
 (.isAbsolute (java.io.File. *file*)))

(if (command-line?) (main [*command-line-args*] ))