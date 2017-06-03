start "service discovery" /D servicediscovery mvn spring-boot:run
start "auth server" /D authserver mvn spring-boot:run
start "edge server" /D edgeserver mvn spring-boot:run
start "order endpoint" /D orderendpoint mvn spring-boot:run
start "order service" /D orderservice mvn spring-boot:run
start "receive order service" /D receiveorderservice mvn spring-boot:run