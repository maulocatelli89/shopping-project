package com.locatelli.java.back.end.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.locatelli.java.back.end.converter.DTOConverter;
import com.locatelli.java.back.end.dto.ProductDTO;
import com.locatelli.java.back.end.dto.exception.CategoryNotFoundException;
import com.locatelli.java.back.end.exceptions.ProductNotFoundException;
import com.locatelli.java.back.end.model.Product;
import com.locatelli.java.back.end.repository.CategoryRepository;
import com.locatelli.java.back.end.repository.ProductRepository;

public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public List<ProductDTO> getAll(){
		List<Product> products = productRepository.findAll();
		return products
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public List<ProductDTO> getProductByCategoryId(Long categoryId){
		List<Product> products = productRepository.getProductByCategory(categoryId);
		return products
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public ProductDTO findByProductIdentifier(String productIdentifier) {
		Product product = productRepository.findByProductIdentifier(productIdentifier);
		if(product != null) {
			return DTOConverter.convert(product);
		}
		throw new ProductNotFoundException();
	}

	public ProductDTO save(ProductDTO productDTO) {
		Boolean existsCategory = categoryRepository
				.existsById(productDTO.getCategoryDTO().getId());
		if(!existsCategory) {
			throw new CategoryNotFoundException();
		}
		Product product = productRepository.save(Product.convert(productDTO));
		return DTOConverter.convert(product);
	}

	public ProductDTO delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.delete(product.get());
		}
		throw new ProductNotFoundException();
	}
}
