package br.com.antunes.gustavo.shoppinglistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductCartDTO;
import br.com.antunes.gustavo.shoppinglistapi.service.VisionService;

@RestController
public class VisionController {

  @Autowired private ResourceLoader resourceLoader;

  @Autowired private CloudVisionTemplate cloudVisionTemplate;
  
  @Autowired private VisionService visionService;

  @GetMapping("/extractText")
  public ResponseEntity<ProductCartDTO> extractText(@RequestParam String imageUrl) {
    String textFromImage =
        this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(imageUrl));
    return ResponseEntity.ok(visionService.processImageText(textFromImage));
  }
}
