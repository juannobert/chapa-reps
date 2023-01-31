package br.com.reps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.services.TutorialService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tutoriais")
public class TutorialController {

	@Autowired
	private TutorialService service;
	
	@GetMapping("/novo")
	public ModelAndView formTutorial() {
		ModelAndView mv = new ModelAndView("tutorial/form-tutorial");
		mv.addObject("form", new TutorialRequest());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public String formTutorial(@Valid @ModelAttribute("form") TutorialRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors()) {
			return "tutorial/form-tutorial";
		}
		service.insert(request);
		return "redirect:/tutoriais/novo";
	}
}
