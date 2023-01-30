package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.Function;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDefaultRequest extends UserRequest{
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Function function;
	

}
