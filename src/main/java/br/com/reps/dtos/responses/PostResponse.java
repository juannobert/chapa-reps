package br.com.reps.dtos.responses;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {

	private String title;
	
	private String text;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
}
