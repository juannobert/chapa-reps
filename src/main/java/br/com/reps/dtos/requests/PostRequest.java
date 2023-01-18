package br.com.reps.dtos.requests;

import br.com.reps.entities.enums.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {


	private String title;
	
	private String text;
	
	
	/*
	@ManyToOne
	private User author;
	*/
	@Enumerated(EnumType.STRING)
	private PostType postType;
}
