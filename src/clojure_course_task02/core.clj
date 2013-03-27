(ns clojure-course-task02.core
  (:gen-class ))

(defn walker [pattern root]
  (let [subs (vec (.listFiles root))
        files (pmap #(.getName %)
      (filter
        (fn [x]
          (and
            (.isFile x)
            (->> (.getName x)
              (re-find pattern)
              nil?
              not)))
        subs))
        dirs (filter (fn [x] (.isDirectory x)) subs)]
    (concat files (pmap #(walker pattern %) dirs))))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (let [f (clojure.java.io/file path)
        pattern (re-pattern file-name)]
    (flatten (walker pattern f))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
        (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
