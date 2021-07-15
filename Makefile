build:
	mvn clean compile assembly:single
rungui:
	cd env/client && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main
run0:
	cd env/0 && java -Xms10m -Xmx20m -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 0
run1:
	cd env/1 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 1
run2:
	cd env/2 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 2
run3:
	cd env/3 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 3
runall:
	make run0 & make run1 & make run2 & make run3 &
killall:
	jobs -p | xargs -n1 pkill -SIGINT -g # not the same bash env, doestn't work