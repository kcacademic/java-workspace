JAAS
====
java -Djava.security.auth.login.config=jaas-krb5.conf Jaas client
java -Djava.security.auth.login.config=jaas-krb5.conf Jaas server

java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf Jaas client
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf Jaas server

GSS-API
=======
java -Djava.security.auth.login.config=jaas-krb5.conf GssServer
java -Djava.security.auth.login.config=jaas-krb5.conf GssClient host WKWGB2257697

java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf GssServer
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf GssClient host WKWGB2257697

java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf com.kchandrakant.learning.gss.GssServer
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf com.kchandrakant.learning.gss.GssClient HTTP localhost

SASL
====
java -Djava.security.auth.login.config=jaas-krb5.conf SaslTestServer host WKWGB2257697
java -Djava.security.auth.login.config=jaas-krb5.conf SaslTestClient host WKWGB2257697

java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf SaslTestServer host WKWGB2257697
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf SaslTestClient host WKWGB2257697

JSSE
====
java -Djava.security.auth.login.config=jaas-krb5.conf JsseServer WKWGB2257697
java -Djava.security.auth.login.config=jaas-krb5.conf JsseClient WKWGB2257697

java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf JsseServer WKWGB2257697
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf JsseClient WKWGB2257697

GSS SPNEGO
==========
java -Djava.security.auth.login.config=jaas-krb5.conf GssSpNegoServer
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf GssSpNegoClient host WKWGB2257697

java -Djava.security.auth.login.config=jaas-krb5.conf GssSpNegoServer
java -Djava.security.auth.login.config=jaas-krb5.conf -Djava.security.krb5.conf=krb5.conf GssSpNegoClient host WKWGB2257697
