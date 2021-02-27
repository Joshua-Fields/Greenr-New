package com.group41.Greenr.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.group41.Greenr.model.User;
import com.group41.Greenr.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
	
	void deleteAccountById(long id);
	
	User storeFile(MultipartFile file);

}
