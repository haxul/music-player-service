# music-player-service

### Scheme:

![image](https://github.com/haxul/music-player-service/blob/master/draw.png)

### Backend:

* **User-ws**:
  * Spring (MVC, Security, Data)
  * Postgres
  * RabbitMq
  * Ereuka discovery client
* **Music-ws**:
  * Spring (MVC, Data)
  * MongoDB
  * RabbitMq
  * Ereuka discovery client
* **Log-ws**:
  * Spring (MVC)
  * RabbitMq
  * Ereuka discovery client
 
### Client:

* React
* Redux
