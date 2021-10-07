package com.locatelli.java.back.end.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locatelli.java.back.end.dto.UserDTO;
import com.locatelli.java.back.end.dto.exception.UserNotFoundException;
import com.locatelli.java.back.end.model.User;
import com.locatelli.java.back.end.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserDTO> getAll(){
		List<User> usuarios = userRepository.findAll();
		return usuarios
				.stream()
				.map(UserDTO::convert)
				.collect(Collectors.toList());
	}

	public UserDTO findById(Long Id) {
		Optional<User> user = userRepository.findById(Id);
		if(user != null) {
			return UserDTO.convert(user.get());
		}
		return null;
	}

	public UserDTO save(UserDTO userDTO) {
		userDTO.setKey(UUID.randomUUID().toString());
		User user = userRepository.save(User.convert(userDTO));
		return UserDTO.convert(user);
	}
	
	public UserDTO delete(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			userRepository.delete(user.get());
		}
		return null;
	}
	
	public UserDTO findByCpf(String cpf, String key) {
		User user = userRepository.findByCpfAndKey(cpf, key);
		if(user != null) {
			return UserDTO.convert(user);
		}
		throw new UserNotFoundException();
	}
	
	public List<UserDTO> queryByName(String name){
		List<User> usuarios = userRepository.queryByNomeLike(name);
		return usuarios
				.stream()
				.map(UserDTO::convert)
				.collect(Collectors.toList());
	}

}
