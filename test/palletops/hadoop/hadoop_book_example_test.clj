(ns palletops.hadoop.hadoop-book-example-test
  (:use
   clojure.test
   palletops.hadoop.hadoop-book-example
   [pallet.build-actions :only [build-actions]]
   [palletops.crate.hadoop :only [hadoop-settings]]
   [pallet.test-utils :only [make-node]]))

(def nn {:roles #{:name-node :job-tracker} :node (make-node "n")})

(deftest compile-test
  (is
   (build-actions {:service-state [nn]}
     (hadoop-settings {})
     (download-books)
     (import-books-to-hdfs)
     (run-books)
     (get-books-output))))
