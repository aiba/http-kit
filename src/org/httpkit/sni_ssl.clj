(ns org.httpkit.sni-ssl
  "Provides an SNI-capable SSL configurer, Ref. #335.
  In a separate namespace from `org.httpkit.client` so that
  http-kit can retain backwards-compatibility with JVM < 8."
  (:require [org.httpkit.util :as util])
  (:import
   [java.net URI]
   [javax.net.ssl SNIHostName SSLEngine SSLParameters]))

(defn ssl-configurer
  "SNI-capable SSL configurer.
   May be used as an argument to `org.httpkit.client/make-client`:
    (make-client :ssl-configurer (ssl-configurer))"
  ([ssl-engine uri] (ssl-configurer {} ssl-engine uri))
  ([{:keys [hostname-verification? sni?] :as opts
     :or   {;; TODO Better option/s than hacky version check?
            hostname-verification? (>= (util/java-version) 11)
            sni?                   true}}
    ^SSLEngine ssl-engine ^URI uri]

   (let [^SSLParameters ssl-params (.getSSLParameters ssl-engine)]
     (when hostname-verification? (.setEndpointIdentificationAlgorithm ssl-params "HTTPS"))
     (when sni?                   (.setServerNames                     ssl-params
                                    [(SNIHostName. (.getHost uri))]))

     ;; TODO Better option/s than hacky version check?
     (when (and (>= (util/java-version) 11) (not (.getUseClientMode ssl-engine)))
       (.setUseClientMode ssl-engine true))

     (doto ssl-engine
       (.setSSLParameters ssl-params)))))
