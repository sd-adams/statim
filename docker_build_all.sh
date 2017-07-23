mvn -f ./servicediscovery/pom.xml clean package docker:build
mvn -f ./authserver/pom.xml clean package docker:build
mvn -f ./edgeserver/pom.xml clean package docker:build
mvn -f ./orderservice/pom.xml clean package docker:build
mvn -f ./orderendpoint/pom.xml clean package docker:build
mvn -f ./receiveorderservice/pom.xml clean package docker:build