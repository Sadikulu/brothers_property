package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.realestate.domain.User;
import com.realestate.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO userToUserDTO(User user);
	
	List<UserDTO> map(List<User> userList);
}
