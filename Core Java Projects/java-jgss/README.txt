java -Djava.security.krb5.conf=krb5.conf -Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf com.sapient.learning.jgss.SampleServer 9003

java -Djava.security.krb5.conf=krb5.conf -Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf com.sapient.learning.jgss.SampleClient HTTP/localhost@EXAMPLE.COM localhost 9003

java -Djava.security.krb5.conf=krb5.conf -Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf com.sapient.learning.Main