package br.com.reps.dtos.requests;

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
	private String Url;
}
