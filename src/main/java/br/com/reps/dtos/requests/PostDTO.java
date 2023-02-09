package br.com.reps.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	@NotEmpty
	@NotNull
	@Size(min = 3,max = 80)
	private String title;
	
	@NotEmpty
	@NotNull
	private String text;
}
