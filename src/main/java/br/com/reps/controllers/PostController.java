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
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.dtos.responses.PostResponse;
import br.com.reps.entities.enums.PostType;
import br.com.reps.permissions.PermissionsConfig;
import br.com.reps.services.PostService;
import br.com.reps.utils.ControllerUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService service;
	
	
	@Autowired
	private ControllerUtils controllerUtils;

	@GetMapping("/{id}")
	public ModelAndView findPostById(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("post/post-details");
		mv.addObject("post", service.findById(id));
		if (service.isSupportbyId(id)) {
			mv.addObject("form", new AnswerRequest());
		}

		return mv;
	}

	@PostMapping("/{id}")
	public String findPostById(@PathVariable Long id, @Valid @ModelAttribute("form") AnswerRequest request,
			BindingResult result, RedirectAttributes attrs) {
		service.addComents(id, request);
		attrs.addFlashAttribute("alert", new AlertMessage("Comentario adicionado com sucesso", "alert-primary"));
		return "redirect:/ouvidoria";
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
	@PermissionsConfig.isGremista
	public ModelAndView postForm() {
		ModelAndView mv = new ModelAndView("post/form-post");
		mv.addObject("form", new PostRequest());
		mv.addObject("alter", false);
		return mv;

	}

	@PostMapping("/publi/novo")
	public String postForm(@Valid @ModelAttribute("form") PostRequest request, BindingResult result,
			RedirectAttributes attrs) {
		if (result.hasErrors())
			return "post/form-post";
		service.insertNotice(request);
		attrs.addFlashAttribute("alert", new AlertMessage("Postagem publicada com sucesso", "alert-primary"));
		if (request.getPostType().equals(PostType.NOTICE))
			return "redirect:/post/avisos";

		return "redirect:/post/transparencia";
	}

	

	@GetMapping("/excluir/{id}")
	@PermissionsConfig.isGremista
	public String deleteById(@PathVariable Long id, HttpServletRequest request, RedirectAttributes attrs) {
		return controllerUtils.deletePost(id, request, attrs,service);
	}


	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("post/form-post");
		mv.addObject("form", service.findById(id));
		mv.addObject("alter", true);
		return mv;
	}

	@PostMapping("/alterar/{id}")
	public String alterar(@PathVariable Long id, @Valid @ModelAttribute("form") PostRequest request, BindingResult result,
			RedirectAttributes attrs,HttpServletRequest httpRequest) {
		return controllerUtils.alterPost(id, request, result, attrs,service);
	}

	@ModelAttribute("postTypes")
	public PostType[] getPostType() {
		return PostType.values();
	}
}
