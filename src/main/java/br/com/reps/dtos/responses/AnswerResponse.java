package br.com.reps.dtos.responses;

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
