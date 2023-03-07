package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.Function;
import br.com.reps.entities.enums.Officie;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAlterResquest {

	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@Email
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Officie officie;
	
	@Enumerated(EnumType.STRING)
	private Function function;
}
