package br.com.reps.dtos.responses;

import br.com.reps.entities.enums.Function;
import br.com.reps.entities.enums.Officie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private Long id;
	
	private String name;
	
	private String email;
	
	private Officie officie;
	
	private Function function;
}
