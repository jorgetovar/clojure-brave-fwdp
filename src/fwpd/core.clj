(ns fwpd.core)
(require '[clojure.string :as cs])
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
      [str]
      (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn parse
      "Convert a CSV into rows of columns"
      [string]
      (map #(cs/split % #",")
           (cs/split string #"\n")))

(defn convert
      [vamp-key value]
      ((get conversions vamp-key) value))


(defn vector->map [unmapped-row]
      (reduce (fn [row-map [vamp-key value]]
                  (assoc row-map vamp-key (convert vamp-key value)))
              {}
              (map vector vamp-keys unmapped-row)))

(defn glitter-filter
      [minimum-glitter records]
      (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn mapify
      "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
      [rows]
      (map vector->map rows))