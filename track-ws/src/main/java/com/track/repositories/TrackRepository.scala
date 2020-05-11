package com.track.repositories

import com.track.entities.TrackEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait TrackRepository extends MongoRepository[TrackEntity, Int]{}
