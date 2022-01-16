(ns org.httpkit.sni-client
  "Provided only for legacy backwards-compatibility with SNI workaround, which is no
  longer needed."
  (:require [org.httpkit.client]
            [org.httpkit.sni :as sni]))

(defonce
  ^{:doc "This client is now the default client, but this namespace and var remain for
backwards-compatibility with SNI workaround that set it to the default. See #393."}
  default-client
  (delay
    (org.httpkit.client/make-client
     {:ssl-configurer sni/ssl-configurer})))
