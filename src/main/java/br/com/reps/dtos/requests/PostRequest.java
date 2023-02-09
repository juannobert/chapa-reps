package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest extends PostDTO {

	@NotNull
	@Enumerated(EnumType.STRING)
	private PostType postType;
}
