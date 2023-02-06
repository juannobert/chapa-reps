package br.com.reps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.reps.dtos.requests.AnswerRequest;
import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.requests.SupportRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.enums.PostType;
import br.com.reps.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService service;
	
	
	@GetMapping("/{id}")
	public ModelAndView findPostById(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("post/post-details");
		mv.addObject("post", service.findById(id));
		if(service.isSupportbyId(id)) {
			mv.addObject("form", new AnswerRequest());
		}
		
		return mv;
	}
	
	@PostMapping("/{id}")
	public String findPostById(@PathVariable Long id,@Valid @ModelAttribute("form") AnswerRequest request,BindingResult result,
			RedirectAttributes attrs) {
		service.addComents(id, request);
		attrs.addFlashAttribute("alert",new AlertMessage("Comentario adicionado com sucesso","alert-primary"));
		return "redirect:/post/ouvidoria";
	}


	/* Publicações */
	@GetMapping("/avisos")
	public ModelAndView postsAvisos(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("post/notice");
		Page<PostResponse> page = service.findAllPosts(pageable, PostType.NOTICE);
		mv.addObject("posts", page);
		mv.addObject("pageTitle", "avisos");
		
		return mv;
	}
	
	@GetMapping("/transparencia")
	public ModelAndView postsTransparencia(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("post/notice");
		Page<PostResponse> page = service.findAllPosts(pageable, PostType.TRANSPARENCY);
		mv.addObject("posts", page);
		mv.addObject("pageTitle", "transparencia");
		
		
		return mv;
	}
	
	@GetMapping("/publi/novo")
	public ModelAndView postForm() {
		ModelAndView mv = new ModelAndView("post/form-post");
		mv.addObject("form", new PostRequest());
		return mv; 
		
	}
	
	@PostMapping("/publi/novo")
	public String postForm(@Valid @ModelAttribute("form") PostRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors())
			return "post/form-post";
		service.insertNotice(request);
		attrs.addFlashAttribute("alert",new AlertMessage("Postagem publicada com sucesso","alert-primary"));
		if(request.getPostType().equals(PostType.NOTICE))
			return "redirect:/post/avisos";
		
		return "redirect:/post/transparencia";
	}
	
	
	/*Ouvidoria*/
	@GetMapping("/ouvidoria")
	public ModelAndView supportPosts( @PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("post/support");
		mv.addObject("posts", service.findAllPosts(pageable,PostType.SUPPORT));
		return mv;
	}
	 
	@GetMapping("/ouvidoria/novo")
	public ModelAndView supportForm() {
		ModelAndView mv = new ModelAndView("post/form-support");
		mv.addObject("form", new SupportRequest());
		return mv; 
		
	}
	
	
	@PostMapping("/ouvidoria/novo")
	public String supportForm(@Valid @ModelAttribute("form") SupportRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors())
			return "post/form-support";
		attrs.addFlashAttribute("alert",new AlertMessage("Pergunta publicada com sucesso","alert-primary"));
		service.insertSupport(request);
		
		return "redirect:/post/ouvidoria";
	}
	
	@GetMapping("/excluir/{id}")
	public String deleteById(@PathVariable Long id,HttpServletRequest request,RedirectAttributes attrs) {
		service.delete(id);
		attrs.addFlashAttribute("alert",new AlertMessage("Publicação excluida com sucesso","alert-primary"));
		String path = request.getHeader("Referer");
		
		if(path.endsWith("/avisos")) return "redirect:/post/avisos";
		else if(path.endsWith("/transparencia")) return "redirect:/post/transparencia";
		return "redirect:/post/ouvidoria";
		

	}
	
	@ModelAttribute("postTypes")
	public PostType[] getPostType() {
		return PostType.values();
	}
}
