package com.realestate.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.domain.Role;
import com.realestate.domain.User;
import com.realestate.domain.enums.RoleType;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessages;
import com.realestate.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private JwtUtils jwtUtils;
//	@Autowired
//	private AuthenticationManager authenticationManager;

	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService,@Lazy PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, email)));
		return user;
	}

	public void createUser(RegisterRequest registerRequest) {
		Boolean existEmail = userRepository.existsByEmail(registerRequest.getEmail());

		if (existEmail) {
			throw new ConflictException(String.format(ErrorMessages.EMAIL_ALREADY_EXISTS_, registerRequest.getEmail()));
		}

		String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

		Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		User user = new User();

		user.setAddress(registerRequest.getAddress());
		user.setEmail(registerRequest.getEmail());
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setPassword(encodedPassword);
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setRoles(roles);
		user.setZipCode(registerRequest.getZipcode());

		userRepository.save(user);

	}

//	public String loginUser( LoginRequest loginRequest) {
//	
//		UsernamePasswordAuthenticationToken passwordAuthenticationToken=
//				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
//		Authentication authentication=authenticationManager.authenticate(passwordAuthenticationToken);
//		UserDetails userDetails=(UserDetails) authentication.getPrincipal();
//		String token=jwtUtils.generateToken(userDetails);
//		return token;	
//
//	}

}
