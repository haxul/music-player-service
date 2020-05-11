# music-player-service

### Scheme:

![image](https://github.com/haxul/music-player-service/blob/master/draw.png)

### Technologies:

* User-ws:
    * Ereuka server
    * Zuul
    * Postgres
    * Spring (Boot, Security, Data, MVC) based on Java
    * RabbitMq
* Log-ws:
    * Ereuka client
    * Spring (Boot) based on Java
    * RabbitMq
* Track-ws
    * Ereuka client
    * Spring (Boot, Data, MVC) based on Scala
    * MongoDb