package br.com.reps.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements IUserRequest{

	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@NotNull
	@Size(min = 3, max = 15)
	private String password;
	
	@NotNull
	private String passwordConfirmation;
}
