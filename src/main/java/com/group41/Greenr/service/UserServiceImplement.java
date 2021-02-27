package com.group41.Greenr.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.group41.Greenr.model.Role;
import com.group41.Greenr.model.User;
import com.group41.Greenr.repository.UserRepo;
import com.group41.Greenr.web.dto.UserRegistrationDto;

@Service
public class UserServiceImplement implements UserService{

	private UserRepo userRepo;
	
	@Autowired
	private HttpSession httpSession;
	
	public UserServiceImplement(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // For the encryption of the user password

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getUsername() ,registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getDoB(), // Changes made to encrypt the user password which is stored in the database
				Arrays.asList(new Role("ROLE_USER")));
		
		return userRepo.save(user);
	
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username);
		if(user == null ){
			throw new UsernameNotFoundException("Invalid Username or Password"); // Throws error message if username/password is incorrect					
		}
		httpSession.setAttribute("user", user);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()); //Uses stream API - used to process collections of objects
	}

	@Override
	public void deleteAccountById(long id) {
		this.userRepo.deleteById(id);
		
	}
	
	public void saveuser(User user) {

        passwordEncoder.encode(user.getPassword());
        userRepo.save(user);
    }
	
	@Override
	public User storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		try {
//			if (fileName)
//		}
		return null;
	}

}
