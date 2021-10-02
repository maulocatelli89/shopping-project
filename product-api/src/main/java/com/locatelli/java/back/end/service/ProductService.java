package com.locatelli.java.back.end.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.locatelli.java.back.end.dto.ProductDTO;
import com.locatelli.java.back.end.model.Product;
import com.locatelli.java.back.end.repository.ProductRepository;

public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<ProductDTO> getAll(){
		List<Product> products = productRepository.findAll();
		return products
				.stream()
				.map(ProductDTO::convert)
				.collect(Collectors.toList());
	}

	public List<ProductDTO> getProductByCategoryId(Long categoryId){
		List<Product> products = productRepository.getProductByCategory(categoryId);
		return products
				.stream()
				.map(ProductDTO::convert)
				.collect(Collectors.toList());
	}

	public ProductDTO findByProductIdentifier(String productIdentifier) {
		Product product = productRepository.findByProductIdentifier(productIdentifier);
		if(product != null) {
			return ProductDTO.convert(product);
		}
		return null;
	}

	public ProductDTO save(ProductDTO productDTO) {
		Product product = productRepository.save(Product.convert(productDTO));
		return ProductDTO.convert(product);
	}

	public ProductDTO delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.delete(product.get());
		}
		return ProductDTO.convert(product.get());
	}
}
