package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductCartDTO;
import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;

@Service
public class VisionService {

	//TODO change the logic to get the correct price when there is multiple prices in the image
	public ProductCartDTO processImageText(String textFromImage) {
		String productName = textFromImage.split("\n")[0].strip();
		String[] texts = textFromImage.split(" ");
		String price = null;
		for (String text : texts) {
			price = getPrice(text);
		}
		ProductDTO productDTO = new ProductDTO(0, productName, "");
		ProductCartDTO productCartDTO = new ProductCartDTO(0, productDTO, 1, Float.parseFloat(price.replace(",", ".")));
		return productCartDTO;
	}

	private String getPrice(String input) {
		String output = "";

		Pattern pattern = Pattern.compile("\\d{1,3}[,\\.]?(\\d{1,2})?");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			output = matcher.group(0);
		}

		return output;
	}

}
