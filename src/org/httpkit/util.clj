(ns org.httpkit.util)

(defn- parse-java-version
  "Ref. https://stackoverflow.com/a/2591122"
  [^String s]

  (let [dot-idx (.indexOf s ".")    ; e.g. "1.6.0_23"
        dash-idx (.indexOf s "-")]  ; e.g. "16-ea"
    (cond
      (.startsWith s "1.") ; e.g. "1.6.0_23"
      (Integer/parseInt (.substring s 2 3))

      (pos? dot-idx)
      (Integer/parseInt (.substring s 0 dot-idx))

      (pos? dash-idx)
      (Integer/parseInt (.substring s 0 dash-idx))

      :else
      (Integer/parseInt s))))

(comment
  (parse-java-version "1.6.0_23")  ; 6
  (parse-java-version "1.8.0_302") ; 8
  (parse-java-version "9.0.1")     ; 9
  (parse-java-version "11.0.12")   ; 11
  (parse-java-version "16-ea")     ; 16
  (parse-java-version "17")        ; 17
  )

(def java-version
  (memoize
   (fn []
     (parse-java-version (str (System/getProperty "java.version"))))))

(comment
  (java-version)
  )
