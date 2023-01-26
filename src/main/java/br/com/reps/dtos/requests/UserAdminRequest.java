package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.Officie;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminRequest extends UserRequest{
	
	@NotNull
	private Officie function;
}
