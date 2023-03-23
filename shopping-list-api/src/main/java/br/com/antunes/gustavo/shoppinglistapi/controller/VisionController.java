package br.com.antunes.gustavo.shoppinglistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisionController {

  @Autowired private ResourceLoader resourceLoader;

  @Autowired private CloudVisionTemplate cloudVisionTemplate;

  @GetMapping("/extractText")
  public String extractText(@RequestParam String imageUrl) {
    String textFromImage =
        this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(imageUrl));
    return "Text from image: " + textFromImage;
  }
}
