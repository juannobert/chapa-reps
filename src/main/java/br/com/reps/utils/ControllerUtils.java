package br.com.reps.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.Post;
import br.com.reps.entities.enums.PostType;
import br.com.reps.services.PostService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ControllerUtils {

	public String alterPost(Long id, PostRequest request, BindingResult result, RedirectAttributes attrs,PostService service) {
		PostResponse postResponse = service.findById(id);
		if (result.hasErrors()) {
			if(postResponse.getPostType().equals(PostType.SUPPORT)) 
				return "post/form-support";
			return "post/form-post";
		}
		Post post = service.alter(id, request);
		attrs.addFlashAttribute("alert", new AlertMessage("Postagem alterada com sucesso", "alert-primary"));

		if (post.getPostType().equals(PostType.NOTICE))
			return "redirect:/post/avisos";
		else if (post.getPostType().equals(PostType.TRANSPARENCY))
			return "redirect:/post/transparencia";
		return "redirect:/post/ouvidoria";
	}
	
	public String deletePost(Long id, HttpServletRequest request, RedirectAttributes attrs,PostService service) {
		service.delete(id);
		attrs.addFlashAttribute("alert", new AlertMessage("Publicação excluida com sucesso", "alert-primary"));
		String path = request.getHeader("Referer");

		if (path.endsWith("/avisos"))
			return "redirect:/post/avisos";
		else if (path.endsWith("/transparencia"))
			return "redirect:/post/transparencia";
		else if(path.endsWith("/ouvidoria"))
			return "redirect:/post/ouvidoria";
		path = path.replace("/excluir", "");
		return "redirect:" + path.substring(path.indexOf('/'));
	}
}
