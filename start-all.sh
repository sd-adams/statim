nohup mvn -f ./servicediscovery/pom.xml spring-boot:run &
nohup mvn -f ./authserver/pom.xml spring-boot:run &
nohup mvn -f ./edgeserver/pom.xml spring-boot:run &
nohup mvn -f ./orderservice/pom.xml spring-boot:run &
nohup mvn -f ./orderendpoint/pom.xml spring-boot:run &