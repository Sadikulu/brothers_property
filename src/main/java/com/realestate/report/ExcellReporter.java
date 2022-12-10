package com.realestate.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.realestate.domain.Agent;
import com.realestate.domain.Property;
import com.realestate.domain.Review;
import com.realestate.domain.Role;
import com.realestate.domain.TourRequest;
import com.realestate.domain.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class ExcellReporter {

	static String SHEET_USER = "Customers";
	static String[] HEADERS_USER = { "Id", "FirstName", "LastName", "PhoneNumber", "Email", "Address", "ZipCode",
			"Roles" };

	static String SHEET_PROPERTY = "Properties";
	static String[] HEADERS_PROPERTY = { "Id", "Title", "Description", "Category", "Type", "Bedrooms", "Bathrooms",
			"Garages", "Area", "location", "Country", "City", "district", "Status" };

	static String SHEET_AGENT = "Agents";
	static String[] HEADERS_AGENT = { "Id", "firstName", "lastName", "PhoneNumber", "Email", "PropertyId",
			"PropertyTitle" };

	static String SHEET_REVIEW = "Reviews";
	static String[] HEADERS_REVIEW = { "Id", "Review", "ActivationDate", "Score", "Status", "PropertyId",
			"Property_Title", "CustomerId", "CustomerFullName", };

	static String SHEET_TOURREQUEST = "TourRequests";
	static String[] HEADERS_TOURREQUEST = { "Id", "TourRequestTime", "Adult", "Child", "Status", "PropertyId",
			"PropertyTitle", "CustomerId", "CustomerFullName" };

	public static ByteArrayInputStream usersExcel(List<User> users) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET_USER);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < HEADERS_USER.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(HEADERS_USER[i]);
		}

		int rowId = 1;
		for (User user : users) {
			Row row = sheet.createRow(rowId++);

			row.createCell(0).setCellValue(user.getId());
			row.createCell(1).setCellValue(user.getFirstName());
			row.createCell(2).setCellValue(user.getLastName());
			row.createCell(3).setCellValue(user.getPhoneNumber());
			row.createCell(4).setCellValue(user.getEmail());
			row.createCell(5).setCellValue(user.getAddress());
			row.createCell(6).setCellValue(user.getZipCode());

			StringJoiner sj = new StringJoiner(",");
			for (Role role : user.getRoles()) {
				sj.add(role.getType().getName());
			}
			row.createCell(7).setCellValue(sj.toString());
		}

		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ByteArrayInputStream propertiesExcel(List<Property> properties) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET_PROPERTY);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < HEADERS_PROPERTY.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(HEADERS_PROPERTY[i]);
		}

		int rowId = 1;
		for (Property property : properties) {
			Row row = sheet.createRow(rowId++);

			row.createCell(0).setCellValue(property.getId());
			row.createCell(1).setCellValue(property.getTitle());
			row.createCell(2).setCellValue(property.getDescription());
			row.createCell(3).setCellValue(property.getCategory().toString());
			row.createCell(4).setCellValue(property.getType().toString());
			row.createCell(5).setCellValue(property.getBedrooms());
			row.createCell(6).setCellValue(property.getBathrooms());
			row.createCell(7).setCellValue(property.getGarages());
			row.createCell(8).setCellValue(property.getArea());
			row.createCell(9).setCellValue(property.getLocation());
			row.createCell(10).setCellValue(property.getStatus().toString());
		}

		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ByteArrayInputStream agentsExcel(List<Agent> agents) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET_AGENT);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < HEADERS_AGENT.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(HEADERS_AGENT[i]);
		}

		int rowId = 1;
		for (Agent agent : agents) {
			Row row = sheet.createRow(rowId++);

			row.createCell(0).setCellValue(agent.getId());
			row.createCell(1).setCellValue(agent.getFirstName());
			row.createCell(2).setCellValue(agent.getLastName());
			row.createCell(3).setCellValue(agent.getPhone());
			row.createCell(4).setCellValue(agent.getEmail());
//			row.createCell(5).setCellValue(agent.getProperties().getId());
//			row.createCell(6).setCellValue(agent.getProperties().getTitle());
		}

		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ByteArrayInputStream reviewsExcel(List<Review> reviews) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET_REVIEW);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < HEADERS_REVIEW.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(HEADERS_REVIEW[i]);
		}

		int rowId = 1;
		for (Review review : reviews) {
			Row row = sheet.createRow(rowId++);

			row.createCell(0).setCellValue(review.getId());
			row.createCell(1).setCellValue(review.getReview());
			row.createCell(2).setCellValue(review.getCreateDate().toString());
			row.createCell(3).setCellValue(review.getScore());
			row.createCell(5).setCellValue(review.getStatus().toString());
			row.createCell(6).setCellValue(review.getProperty().getId());
			row.createCell(7).setCellValue(review.getProperty().getTitle());
			row.createCell(8).setCellValue(review.getUser().getId());
			row.createCell(9).setCellValue(review.getUser().getFirstName() + " " + review.getUser().getLastName());
		}

		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ByteArrayInputStream tourRequestsExcel(List<TourRequest> tourrequests) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Sheet sheet = workbook.createSheet(SHEET_TOURREQUEST);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < HEADERS_TOURREQUEST.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(HEADERS_TOURREQUEST[i]);
		}

		int rowId = 1;
		for (TourRequest tourrequest : tourrequests) {
			Row row = sheet.createRow(rowId++);

			row.createCell(0).setCellValue(tourrequest.getId());
			row.createCell(1).setCellValue(tourrequest.getDateTime().toString());
			row.createCell(2).setCellValue(tourrequest.getAdult());
			row.createCell(3).setCellValue(tourrequest.getChild());
			row.createCell(5).setCellValue(tourrequest.getStatus().toString());
			row.createCell(6).setCellValue(tourrequest.getProperty().getId());
			row.createCell(7).setCellValue(tourrequest.getProperty().getTitle());
			row.createCell(8).setCellValue(tourrequest.getUser().getId());
			row.createCell(9)
					.setCellValue(tourrequest.getUser().getFirstName() + " " + tourrequest.getUser().getLastName());
		}

		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

}
