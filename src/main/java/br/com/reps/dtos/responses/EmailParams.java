package br.com.reps.dtos.responses;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailParams {
	
	private String from;
	
	private String to;
	
	private String subject;
	
	private String template;
	
	private Map<String,Object> props;

}
