package br.com.reps.dtos.requests;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorialRequest {

	@NotBlank
	@NotNull
	private String title;
	
	@NotBlank
	@NotNull
	@URL
	private String Url;
}
