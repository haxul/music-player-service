package com.track.controllers

import com.track.entities.TrackEntity
import com.track.services.TrackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RequestParam, RestController}
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(path = Array("/tracks"))
class TrackController {

  @Autowired
  private val trackService: TrackService = null

  @PostMapping
  def downloadFile(@RequestParam(value = "file") file:MultipartFile): TrackEntity = {
      trackService.downloadTrack(file, 1)
  }
}
