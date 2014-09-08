@echo

set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_67

java.exe -jar target/firewall-0.0.1-SNAPSHOT.jar -action drop -destip 192.168.1.70 -time 1h