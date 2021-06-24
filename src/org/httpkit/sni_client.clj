(ns org.httpkit.sni-client
  "sni-client is now the default client for JVM >= 8. This namespace exists only for
  backwards-compatibility."
  (:require [org.httpkit.client :refer [make-client]]
            [org.httpkit.sni-ssl :refer [ssl-configurer]]))

(defonce default-client
  (delay
    (make-client {:ssl-configurer ssl-configurer})))
