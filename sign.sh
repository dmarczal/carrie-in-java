cd html/jars

#keytool -genkey -keyalg rsa -alias mykey -validity 3650

jarsigner fractal.jar mykey

jarsigner mysql-connector-java-5.0.8-bin.jar mykey

jarsigner db4o-7.12.156.14667-all-java5.jar mykey

cd ..
cd ..
