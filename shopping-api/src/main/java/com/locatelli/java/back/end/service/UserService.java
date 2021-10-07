package com.locatelli.java.back.end.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.locatelli.java.back.end.dto.UserDTO;
import com.locatelli.java.back.end.dto.exception.UserNotFoundException;

@Service
public class UserService {

	@Value("${USER_API_URL:http://localhost:8080}")
	private String userApiURL;

	public UserDTO getUserByCpf(String cpf, String key) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = userApiURL + cpf;
			ResponseEntity<UserDTO> response =
					restTemplate.getForEntity(url, UserDTO.class);

			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new UserNotFoundException();
		}
	}

}
