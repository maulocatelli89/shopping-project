package com.locatelli.java.back.end.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.locatelli.java.back.end.dto.ProductDTO;
import com.locatelli.java.back.end.exceptions.ProductNotFoundException;
import com.locatelli.java.back.end.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/product")
	public List<ProductDTO> getProducts() {
		return productService.getAll();
	}

	@GetMapping("/product/category/{categoryId}")
	public List<ProductDTO> getProductByCategory(@PathVariable Long categoryId){
		return productService.getProductByCategoryId(categoryId);
	}
	
	@GetMapping("/product/{productIdentifier}")
	public ProductDTO findById(@PathVariable String productIdentifier) {
		return productService.findByProductIdentifier(productIdentifier);
	}
	
	@PostMapping("/product")
	public ProductDTO newProduct(@Valid @RequestBody ProductDTO productDTO) {
		return productService.save(productDTO);
	}

	@DeleteMapping("/product/{id}")
	public ProductDTO delete(@PathVariable Long id) throws ProductNotFoundException {
		return productService.delete(id);
	}
	
}
