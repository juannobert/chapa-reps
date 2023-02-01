package br.com.reps.dtos.responses;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

	private String text;
	
	private Date date;
}
