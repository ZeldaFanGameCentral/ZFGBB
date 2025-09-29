package com.zfgc.zfgbb.model.forum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BBLocalDate {

	private LocalDate dateInternal;
	private String format = "MM/dd/YYYY";
	
	@JsonIgnore
	public String getDateFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateInternal.format(formatter);
	}
	
}
