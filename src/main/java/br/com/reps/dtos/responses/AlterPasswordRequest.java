package br.com.reps.dtos.responses;

import br.com.reps.dtos.requests.IUserRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterPasswordRequest implements IUserRequest{

	@NotEmpty
	private String password;
	
	@NotEmpty
	private String passwordConfirmation;
}
