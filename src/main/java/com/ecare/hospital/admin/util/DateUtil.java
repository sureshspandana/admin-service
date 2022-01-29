package com.ecare.hospital.admin.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class DateUtil{

	public String getDate() {

		LocalDate date = LocalDate.now();
		System.out.println("the current date is "+date);
		return date.toString();

	}
}