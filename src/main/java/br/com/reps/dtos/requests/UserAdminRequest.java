package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.Officie;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserAdminRequest extends UserRequest{
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Officie officie;
}
