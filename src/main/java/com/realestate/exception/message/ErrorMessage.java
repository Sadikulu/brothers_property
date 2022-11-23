package com.realestate.exception.message;

public class ErrorMessage {

	public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";
	public final static String ROLE_NOT_FOUND_MESSAGE="Role : %s not found";
	public final static String USER_NOT_FOUND_MESSAGE="User with email %s not found";
	public final static String PRINCIPAL_FOUND_MESSAGE="User not found";
	public final static String JWTTOKEN_ERROR_MESSAGE="Jwt Token Validation Error %s";
	public final static String EMAIL_ALREADY_EXIST_MESSAGE="Email: %s already exist";
	public final static String NOT_PERMİTTED_METHOD_MESSAGE="You don't have any permission to change this data";
	public final static String PASSWORD_NOT_MATCHED="Your password are not matched";
	public final static String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";
	public final static String IMAGE_USED_MESSAGE = "Image is used by other agent";
	public final static String IMAGE_PROPERTY_USED_MESSAGE = "Image is used by other property";
	public final static String PROPERTY_USED_MESSAGE = "Property is used by other agent";
	public final static String RESERVATION_TIME_INCORRECT_MESSAGE = "Reservation time not correct";
	public final static String RESERVATION_STATUS_OR_TIME_INCORRECT_MESSAGE = "Reservation status or time not correct";
	public final static String PROPERTY_NOT_AVAILABLE_MESSAGE = "Property is not available for selected time";
	public final static String TOUR_REQUEST_NOT_CANCELED_MESSAGE = "Tour Request cannot canceled";
	public final static String USER_USED_BY_TOUR_REQUEST_MESSAGE = "User couldn't be deleted.User is used by a tour request";
	public final static String PROPERTY_USED_BY_TOUR_REQUEST_MESSAGE = "Property couldn't be deleted.Property is used by a tour request";

}
