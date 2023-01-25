package br.com.reps.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.reps.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private long id;
	
	private String title;
	
	private String text;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	private List<Post> answers;
}
