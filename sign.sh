cd html/jars

#keytool -genkey -keyalg rsa -alias mykey -validity 3650

if ! [ -z $1 ]; then
  if [ $1 = "f" ]; then 
  	jarsigner fractal.jar mykey 
  fi
else
    jarsigner fractal.jar mykey

    jarsigner mysql-connector-java-5.0.8-bin.jar mykey

    jarsigner db4o-7.12.156.14667-all-java5.jar mykey

    jarsigner balloontip-1.1.1.jar  mykey
 fi

cd ..
cd ..
