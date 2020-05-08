package com.nws.minhnb.converttool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nws.minhnb.converttool.data.dto.InputDto;
import com.nws.minhnb.converttool.services.ConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConvertController {

  private static final Logger logger = LoggerFactory.getLogger(ConvertController.class);

  @Autowired
  private ConvertService convertService;

  @GetMapping(value = "/convert")
  public ResponseEntity<Resource> convert(@RequestParam(value = "inputDto", required = false) String inputDtoString,
                                          @RequestParam(value = "file", required = false) MultipartFile file) {
    logger.info("run convert...");

    ObjectMapper om = new ObjectMapper();
    try {
      InputDto inputDto = om.readValue(inputDtoString, InputDto.class);
      List<String> dataConvert = convertService.convert(inputDto, file);

      if (dataConvert.isEmpty()) {
        logger.error("Convert error.");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      Path out = Paths.get("output.ts");
      Files.write(out, dataConvert, StandardCharsets.UTF_8);

      ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(out));
      Files.delete(out);

      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);
    } catch (IOException e) {
      logger.error("Convert error.", e.getCause());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
