package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductCartDTO;
import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;

@Service
public class VisionService {

	public ProductCartDTO processImageText(String textFromImage) {
		String productName = textFromImage.split("\n")[0].strip();
		String[] texts = textFromImage.split(" ");
		List<Float> price = new ArrayList<>();
		float maior = 0f;
		int i = 0;
		for (String text : texts) {
			price.add(getPrice(text));
			// If the text from image has multiple prices get the biggest
			if(price.get(i) != null && price.get(i) > maior) {
				maior = price.get(i);
			}
			i++;
		}
		ProductDTO productDTO = new ProductDTO(0, productName, "");
		ProductCartDTO productCartDTO = new ProductCartDTO(0, productDTO, 1, maior);
		return productCartDTO;
	}

	private Float getPrice(String input) {
		Float output = null;

		Pattern pattern = Pattern.compile("\\d{1,3}[,\\.](\\d{1,2})?");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			output = Float.parseFloat(matcher.group(0).replace(",", "."));
		}

		return output;
	}

}
