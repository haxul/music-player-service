package com.track.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.{Document, Field}

import scala.beans.BeanProperty

@Document(collection = "Tracks")
class TrackEntity {

  @Id
  @BeanProperty
  var id: String = _

  @Field(value = "name")
  @BeanProperty
  var name: String = _

  @Field(value = "url")
  @BeanProperty
  var url: String = _

  @Field(value = "userList")
  @BeanProperty
  var userIdList: Set[Int] = _
}
