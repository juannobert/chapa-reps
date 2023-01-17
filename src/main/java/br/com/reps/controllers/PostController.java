package br.com.reps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.entities.enums.PostType;
import br.com.reps.services.PostService;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService service;
	
	@GetMapping("/avisos")
	public ModelAndView posts(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("post/notice");
		mv.addObject("posts", service.buscarTodos(pageable));
		mv.addObject("page", service.buscarTodos(pageable));
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView postForm() {
		ModelAndView mv = new ModelAndView("post/form-post");
		mv.addObject("form", new PostRequest());
		return mv;
	}
	
	
	
	@ModelAttribute("postTypes")
	public PostType[] getPostType() {
		return PostType.values();
	}
	
	
	

}
