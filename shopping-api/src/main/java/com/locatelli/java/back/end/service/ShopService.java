package com.locatelli.java.back.end.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locatelli.java.back.end.converter.DTOConverter;
import com.locatelli.java.back.end.dto.ItemDTO;
import com.locatelli.java.back.end.dto.ProductDTO;
import com.locatelli.java.back.end.dto.ShopDTO;
import com.locatelli.java.back.end.dto.ShopReportDTO;
import com.locatelli.java.back.end.dto.UserDTO;
import com.locatelli.java.back.end.model.Shop;
import com.locatelli.java.back.end.repository.ReportRepository;
import com.locatelli.java.back.end.repository.ShopRepository;

@Service
public class ShopService {

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	public List<ShopDTO> getAll(){
		List<Shop> shops = shopRepository.findAll();
		return shops
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public List<ShopDTO> getByUser(String userIdentifier){
		List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
		return shops
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public List<ShopDTO> getByDate(ShopDTO shopDTO){
		List<Shop> shops = shopRepository.findAllByDateGreaterThanEquals(shopDTO.getDate());
		return shops
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public ShopDTO findById(Long shopId) {
		Optional<Shop> shop = shopRepository.findById(shopId);
		if(shop.isPresent()) {
			return DTOConverter.convert(shop.get());
		}
		return null;
	}

	public List<ShopDTO> getShopsByFilter(
			Date dataInicio,
			Date dataFim,
			Float valorMinimo) {

		List<Shop> shops = reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
		return shops
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}

	public ShopReportDTO getReportByDate(
			Date dataInicio,
			Date dataFim) {

		return reportRepository.getReportByDate(dataInicio, dataFim);
	}

	public ShopDTO save(ShopDTO shopDTO, String key) {
		
		UserDTO userDTO = userService
				.getUserByCpf(shopDTO.getUserIdentifier(), key);
		validateProducts(shopDTO.getItems());
		
//		if(userService.getUserByCpf(shopDTO.getUserIdentifier()) == null) {
//			return null;
//		}

//		if(!validateProducts(shopDTO.getItems())) {
//			return null;
//		}

		shopDTO.setTotal(shopDTO.getItems()
				.stream()
				.map(x -> x.getPrice())
				.reduce((float) 0, Float::sum));

		Shop shop = Shop.convert(shopDTO);
		shop.setDate(new Date());

		shopRepository.save(shop);
		return DTOConverter.convert(shop);
	}

	private boolean validateProducts(List<ItemDTO> items) {
		for (ItemDTO item : items) {
			ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
			if(productDTO == null) {
				return false;
			}
			item.setPrice(productDTO.getPreco());
		}
		return true;
	}
}
