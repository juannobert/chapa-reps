package br.com.reps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.reps.dtos.requests.PostRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.entities.enums.PostType;
import br.com.reps.permissions.PermissionsConfig;
import br.com.reps.services.PostService;
import br.com.reps.utils.SecurityUtils;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ouvidoria")
public class SupportController {

	@Autowired
	private PostService service;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	/* Ouvidoria */
	@GetMapping()
	public ModelAndView supportPosts(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("post/support");
		mv.addObject("posts", service.findAllPosts(pageable, PostType.SUPPORT));
		Long userId = securityUtils.getUsuarioLogado().getId();
		mv.addObject("userId", userId);
		mv.addObject("isGremista", securityUtils.isGremista());
		return mv;
	}

	@GetMapping("/novo")
	@PermissionsConfig.isUsuario
	public ModelAndView supportForm() {
		ModelAndView mv = new ModelAndView("post/form-support");
		mv.addObject("form", new PostRequest());
		mv.addObject("alter", false);
		return mv;

	}

	@PostMapping("/novo")
	public String supportForm(@Valid @ModelAttribute("form") PostRequest request, BindingResult result,
			RedirectAttributes attrs) {
		if (result.hasErrors())
			return "post/form-support";
		attrs.addFlashAttribute("alert", new AlertMessage("Pergunta publicada com sucesso", "alert-primary"));
		service.insertSupport(request);

		return "redirect:/post/ouvidoria";
	}
}
