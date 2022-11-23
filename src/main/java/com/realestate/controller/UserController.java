package com.realestate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.dto.UserDTO;
import com.realestate.dto.request.AdminUserUpdateRequest;
import com.realestate.dto.request.UpdatePasswordRequest;
import com.realestate.dto.request.UserRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		List<UserDTO> allUsers = userService.getAllUser();
		return ResponseEntity.ok(allUsers);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<UserDTO> getUser() {
		UserDTO userDTO = userService.getPrincipal();
		return ResponseEntity.ok(userDTO);
	}

	@GetMapping("/auth/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserDTO>> getAllUsersByPage(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam("sort") String prop,
			@RequestParam(value = "direction", required = false, defaultValue = "DESC") Direction direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
		Page<UserDTO> userDTOPage = userService.getUserPage(pageable);
		return ResponseEntity.ok(userDTOPage);
	}

	@GetMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserById(id);
		return ResponseEntity.ok(userDTO);
	}

	@PatchMapping("/auth")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		userService.updatePassword(updatePasswordRequest);
		REResponse response = new REResponse(ResponseMessage.PASSWORD_CHANGED_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> updateUser(@PathVariable("id") Long id,
			@Valid @RequestBody UserRequest userRequest) {
		userService.updateUser(id, userRequest);
		REResponse response = new REResponse(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateUserAuth(@PathVariable Long id,
			@Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest) {
		userService.updateUserAuth(id, adminUserUpdateRequest);
		REResponse response = new REResponse(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);

	}

	@DeleteMapping("{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		REResponse response = new REResponse(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);
	}

}