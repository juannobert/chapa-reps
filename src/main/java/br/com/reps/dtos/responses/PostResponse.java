package br.com.reps.dtos.responses;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {

	private String title;
	
	private String text;
	
	private LocalDate date;
}
