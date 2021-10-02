package com.locatelli.java.back.end.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.locatelli.java.back.end.model.Product;

public class ProductDTO {

	@NotBlank
	private String nome;
	@NotNull
	private Float preco;
	@NotBlank
	private String descricao;
	@NotBlank
	private String productIdentifier;
	@NotNull
	private CategoryDTO categoryDTO;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getProductIdentifier() {
		return productIdentifier;
	}

	public void setProductIdentifier(String productIdentifier) {
		this.productIdentifier = productIdentifier;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public static ProductDTO convert(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setNome(product.getNome());
		productDTO.setDescricao(product.getDescricao());
		productDTO.setPreco(product.getPreco());
		productDTO.setProductIdentifier(product.getProductIdentifier());
		if(product.getCategory() != null) {
			productDTO.setCategoryDTO(CategoryDTO.convert(product.getCategory()));
		}
		return productDTO;
	}
}
