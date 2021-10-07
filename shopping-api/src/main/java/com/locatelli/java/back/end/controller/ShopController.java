package com.locatelli.java.back.end.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locatelli.java.back.end.dto.ShopDTO;
import com.locatelli.java.back.end.dto.ShopReportDTO;
import com.locatelli.java.back.end.service.ShopService;

@RestController
public class ShopController {

	@Autowired
	private ShopService shopService;

	@GetMapping("/shopping")
	public List<ShopDTO> getShops(){
		return shopService.getAll();
	}

	@GetMapping("/shopping/showByUser/{userIdentifier}")
	public List<ShopDTO> getShops(@PathVariable String userIdentifier){
		return shopService.getByUser(userIdentifier);
	}

	@GetMapping("/shopping/showByDate")
	public List<ShopDTO> getShops(@PathVariable ShopDTO shopDTO){
		return shopService.getByDate(shopDTO);
	}

	@GetMapping("/shopping/{id}")
	public ShopDTO findById(@PathVariable Long id){
		return shopService.findById(id);
	}

	@PostMapping("/shopping")
	public ShopDTO newShops(
			@RequestHeader(name = "key", required = true) String key,
			@Valid @RequestBody ShopDTO shopDTO){
		return shopService.save(shopDTO, key);
	}

	@GetMapping("/shopping/search")
	public List<ShopDTO> getReportByFilter(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy")
			Date dataInicio,
			@RequestParam(name = "dataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy")
			Date dataFim,
			@RequestParam(name = "valorMinimo", required = false)
			Float valorMinimo) {

		return shopService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
	}

	@GetMapping("/shopping/report")
	public ShopReportDTO getReportByDate(
			@RequestParam(name = "dataInicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy")
			Date dataInicio,
			@RequestParam(name = "dataFim", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy")
			Date dataFim) {

		return shopService.getReportByDate(dataInicio, dataFim);
	}

}
