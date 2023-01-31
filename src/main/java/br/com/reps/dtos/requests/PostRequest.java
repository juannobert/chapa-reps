package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {


	@NotEmpty
	@NotNull
	@Size(min = 3,max = 80)
	private String title;
	
	@NotEmpty
	@NotNull
	private String text;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PostType postType;
}
