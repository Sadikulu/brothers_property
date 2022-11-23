package com.realestate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.dto.request.LoginRequest;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.dto.response.LoginResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.dto.response.REResponse;
import com.realestate.security.jwt.JwtUtils;
import com.realestate.service.UserService;

@RestController
public class UserJwtController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	// register user
	@PostMapping("/register")
	public ResponseEntity<REResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		userService.saveUser(registerRequest);
		REResponse response = new REResponse(ResponseMessage.REGİSTER_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);
	}

	// login user
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
//	String token=userService.loginUser(loginRequest);
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(passwordAuthenticationToken);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtUtils.generateJwtToken(userDetails);
		LoginResponse loginResponse = new LoginResponse(token);
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}

}
