package br.com.reps.dtos.responses;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.reps.entities.Post;
import br.com.reps.entities.User;
import br.com.reps.entities.enums.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Date date;
	
	private User author;
	
	private List<Post> answers;
	
	@Enumerated(EnumType.STRING)
	private PostType postType;
	
	public boolean isSupport() {
		return postType.equals(PostType.SUPPORT);
	}
}
