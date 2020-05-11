package com.track.services

import java.io.File
import java.nio.file.{Files, Path, Paths, StandardCopyOption}
import com.track.entities.TrackEntity
import com.track.repositories.TrackRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.{ResourceUtils, StringUtils}
import org.springframework.web.multipart.MultipartFile

@Service
class TrackService {

  @Autowired
  val trackRepository: TrackRepository = null

  val filePath = "/home/haxul/Documents/development/music-player-service/track-ws/src/main/resources/tracks"

  def downloadTrack(file: MultipartFile, userId : Int) : TrackEntity = {

    val fullName = file.getOriginalFilename.split("\\.").toList

    val (fileName, extension) = fullName match {
      case List(a, b) => (a, b)
    }
    Files.createTempFile(fileName, s".${extension}")
    val copyLocation:Path = Paths.get(s"${filePath}${File.separator}${fileName}.${extension}")
    Files.copy(file.getInputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

    val track = new TrackEntity
    track.url = copyLocation.toString
    track.name = fileName
    trackRepository.save(track)
    track
  }

  // TO DO
  def findUserIdByAuthorizationToken() {}
}
