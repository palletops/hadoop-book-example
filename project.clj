(defproject com.palletops/hadoop-book-example "0.1.0-SNAPSHOT"
  :description "Hadoop book example"
  :url "https://github.com/palletops/hadoop-book-example"
  :license {:name "All rights reserved."}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.cloudhoist/pallet "0.8.0-SNAPSHOT"]]
  :profiles {:dev
             {:dependencies [[org.cloudhoist/pallet-vmfest "0.2.1"]
                             [org.cloudhoist/pallet "0.8.0-SNAPSHOT"
                              :classifier "tests"]
                             [com.palletops/hadoop-crate "0.1.4-SNAPSHOT"]
                             [ch.qos.logback/logback-classic "1.0.0"]]}}
  :repositories
  {"sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots"
   "sonatype" "https://oss.sonatype.org/content/repositories/releases/"})
