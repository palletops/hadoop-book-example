(ns palletops.hadoop.hadoop-book-example
  (:use
   [pallet.action :only [with-action-options]]
   [pallet.actions
    :only [directory exec-checked-script file remote-file remote-file-content]]
   [pallet.crate :only [def-plan-fn get-settings]]
   [palletops.crate.hadoop :only [hadoop-exec hadoop-rmdir]]
   clojure.test))

(def book-examples
  ["pg132.txt" "pg972.txt" "pg1661.txt" "pg4300.txt" "pg5000.txt"
   "pg19699.txt" "pg20417.txt"])

(def book-dir "/tmp/books")
(def book-output-file "/tmp/books-output")

(def-plan-fn download-books
  []
  [{:keys [owner group] :as settings} (get-settings :hadoop {})]
  (directory book-dir :owner owner :group group :mode "0755")
  (map
   #(remote-file
    (str book-dir "/" %)
    :url (str "https://hadoopbooks.s3.amazonaws.com/" %)
    :owner owner :group group :mode "0755")
   book-examples))

(def-plan-fn import-books-to-hdfs
  []
  (hadoop-rmdir "books/")
  (hadoop-exec "dfs" "-copyFromLocal" book-dir "books/"))

(def-plan-fn run-books
  []
  [{:keys [home] :as settings} (get-settings :hadoop {})]
  (hadoop-rmdir "books-output/")
  (with-action-options {:script-dir home}
    (hadoop-exec "jar" "hadoop-examples-*.jar" "wordcount"
                 "books/" "books-output/")))

(def-plan-fn get-books-output
  []
  [{:keys [owner group] :as settings} (get-settings :hadoop {})]
  (file book-output-file :action :delete)
  (hadoop-exec "dfs" "-getmerge" "books-output" book-output-file)
  [result (exec-checked-script
           "Retrieve books output"
           ("head" ~book-output-file))])
