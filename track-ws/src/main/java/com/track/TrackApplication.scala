package com.track

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class TrackApplication

object TrackApplication extends App {
  SpringApplication.run(classOf[TrackApplication])

}

