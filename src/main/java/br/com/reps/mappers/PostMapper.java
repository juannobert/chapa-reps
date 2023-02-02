package br.com.reps.mappers;

import org.springframework.stereotype.Component;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.Post;

@Component
public class PostMapper {
	
	
	public PostResponse toResponse(Post post) {
		PostResponse response = new PostResponse();
		response.setText(post.getText());
		response.setDate(post.getDate());
		response.setAuthor(post.getAuthor());
		response.setTitle(post.getTitle());
		response.setAnswers(post.getAnswers());
		return response;
	}
			
	public Post toModel(PostRequest request) {
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setText(request.getText());
		post.setPostType(request.getPostType());
		return post;
	}
	
	//Post toModel(AnswerRequest request);
	
	//Post toModel(SupportRequest request);
	
}
