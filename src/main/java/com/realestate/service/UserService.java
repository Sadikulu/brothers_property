package com.realestate.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.domain.Role;
import com.realestate.domain.User;
import com.realestate.domain.enums.RoleType;
import com.realestate.dto.UserDTO;
import com.realestate.dto.request.AdminUserUpdateRequest;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.dto.request.UpdatePasswordRequest;
import com.realestate.dto.request.UserRequest;
import com.realestate.exception.BadRequestException;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.UserMapper;
import com.realestate.repository.UserRepository;
import com.realestate.security.SecurityUtils;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private UserMapper userMapper;
	private TourRequestService tourRequestService;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService,@Lazy PasswordEncoder passwordEncoder, UserMapper userMapper,TourRequestService tourRequestService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.userMapper=userMapper;
		this.tourRequestService=tourRequestService;
	}

	public User getUserByEmail(String email) {
		User user= userRepository.findByEmail(email).orElseThrow(()->
		   new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
		return user;
	}

	public void saveUser(RegisterRequest registerRequest) {
		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
		}
		Role role=roleService.findByType(RoleType.ROLE_CUSTOMER);
		Set<Role> roles=new HashSet<>();
		roles.add(role);
		String encodedPassword=passwordEncoder.encode(registerRequest.getPassword());
		User user=new User();
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodedPassword);
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setAddress(registerRequest.getAddress());
		user.setZipCode(registerRequest.getZipcode());
		user.setRoles(roles);
		userRepository.save(user);
	}

	public List<UserDTO> getAllUser() {
		List<User> listUsers= userRepository.findAll();
		List<UserDTO> userDTOs=userMapper.map(listUsers);
		return userDTOs;
	}

	public UserDTO getPrincipal() {
		User currentUser=getCurrentUser();
		UserDTO userDTO= userMapper.userToUserDTO(currentUser);
		return userDTO;
	}
	
	public User getCurrentUser() {
		String email=SecurityUtils.getCurrentUserLogin().orElseThrow(()->
		    new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
		User user=getUserByEmail(email);
		return user;
	}

	public Page<UserDTO> getUserPage(Pageable pageable) {
		Page<User> userPage = userRepository.findAll(pageable);
		Page<UserDTO> userPageDTO = userPage.map(userMapper::userToUserDTO);
		return userPageDTO;
	}

	public UserDTO getUserById(Long id) {
		User user= userRepository.findById(id).orElseThrow(()->
		   new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return userMapper.userToUserDTO(user);
	}
	
	public void updateUser(Long id, UserRequest userRequest) {
		User user=getCurrentUser();
		
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMİTTED_METHOD_MESSAGE);
		}
		
		if (userRepository.existsByEmail(userRequest.getEmail()) && !userRequest.getEmail().equals(user.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, userRequest.getEmail()));
		}
		
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPhoneNumber(userRequest.getPhoneNumber());
		user.setAddress(userRequest.getAddress());
		user.setZipCode(userRequest.getZipCode());
		
		userRepository.save(user);
	}

	public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		User user=getCurrentUser();
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMİTTED_METHOD_MESSAGE);
		}
		if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())) {
			throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
		}
		String hashedPassword= passwordEncoder.encode(updatePasswordRequest.getNewPassword());
		user.setPassword(hashedPassword);
		userRepository.save(user);
	}
	
	public void updateUserAuth(Long id, AdminUserUpdateRequest adminUserUpdateRequest) {
		User user=getById(id);
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMİTTED_METHOD_MESSAGE);
		}
		if (userRepository.existsByEmail(adminUserUpdateRequest.getEmail()) && !adminUserUpdateRequest.getEmail().equals(user.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, adminUserUpdateRequest.getEmail()));
		}
		if (adminUserUpdateRequest.getPassword()==null) {
			adminUserUpdateRequest.setPassword(user.getPassword());
		}else {
			String encodedPassword= passwordEncoder.encode(adminUserUpdateRequest.getPassword());
			adminUserUpdateRequest.setPassword(encodedPassword);
		}
		Set<String> userStrRoles=adminUserUpdateRequest.getRoles();
		Set<Role> roles=convertRoles(userStrRoles);
		
		user.setFirstName(adminUserUpdateRequest.getFirstName());
		user.setLastName(adminUserUpdateRequest.getLastName());
		user.setEmail(adminUserUpdateRequest.getEmail());
		user.setPassword(adminUserUpdateRequest.getPassword());
		user.setPhoneNumber(adminUserUpdateRequest.getPhoneNumber());
		user.setAddress(adminUserUpdateRequest.getAddress());
		user.setZipCode(adminUserUpdateRequest.getZipCode());
		user.setBuiltIn(adminUserUpdateRequest.getBuiltIn());
		user.setRoles(roles);
		
		userRepository.save(user);
		
	}
	
	public Set<Role> convertRoles(Set<String> userStrRoles){
		Set<Role> roles=new HashSet<>();
		if (userStrRoles==null) {
			Role userRole= roleService.findByType(RoleType.ROLE_CUSTOMER);
			roles.add(userRole);
		}else {
			userStrRoles.forEach(reloStr->{
				if (reloStr.equals(RoleType.ROLE_ADMIN.getName())) {//Administrator
					Role adminRole= roleService.findByType(RoleType.ROLE_ADMIN);
					roles.add(adminRole);
				}else {
					Role userRole=roleService.findByType(RoleType.ROLE_CUSTOMER);
					roles.add(userRole);
				}
			});
		}
		return roles;
	}
	
	public void deleteUserById(Long id) {
		User user=getById(id);
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMİTTED_METHOD_MESSAGE);
		}
		boolean exists=tourRequestService.existsByUser(user);
		if (exists) {
			throw new BadRequestException(ErrorMessage.USER_USED_BY_TOUR_REQUEST_MESSAGE);
		}
		userRepository.deleteById(id);
		
	}
	
	public User getById(Long id) {
		User user= userRepository.findUserById(id).orElseThrow(()->
		  new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return user;
	}

}
