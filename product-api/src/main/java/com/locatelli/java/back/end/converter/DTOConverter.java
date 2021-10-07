package com.locatelli.java.back.end.converter;

import com.locatelli.java.back.end.dto.CategoryDTO;
import com.locatelli.java.back.end.dto.ProductDTO;
import com.locatelli.java.back.end.model.Category;
import com.locatelli.java.back.end.model.Product;

public class DTOConverter {

	public static CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setNome(category.getNome());
		return categoryDTO;
	}

	public static ProductDTO convert(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setNome(product.getNome());
		productDTO.setPreco(product.getPreco());
		if (product.getCategory() != null) {
			productDTO.setCategoryDTO(
					DTOConverter.convert(product.getCategory()));
		}
		return productDTO;
	}
}
