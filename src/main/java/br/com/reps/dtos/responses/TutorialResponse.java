package br.com.reps.dtos.responses;

import br.com.reps.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorialResponse {

	private String title;
	
	private String Url;
	
	private User author;
}