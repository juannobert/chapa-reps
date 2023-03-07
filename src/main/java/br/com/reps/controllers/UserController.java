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

import br.com.reps.dtos.requests.UserAlterResquest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.entities.enums.Function;
import br.com.reps.entities.enums.Officie;
import br.com.reps.services.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/alterar")
	public ModelAndView alter() {
		ModelAndView mv = new ModelAndView("user/alter-user");
		mv.addObject("form", service.getAuthenticatedUser());
		
		
		return mv;
	}
	
	@PostMapping("/alterar")
	public String alter(@Valid @ModelAttribute("form") UserAlterResquest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors())
			return "user/alter-user";
		service.alterUser(request);
		attrs.addFlashAttribute("alert",new AlertMessage("Usuário alterado com sucesso","alert-primary"));
		return "redirect:/home";
		
	}
	
	
	@ModelAttribute("positions")
	public Officie[] getOfficie() {
		return Officie.values();
	}
	@ModelAttribute("functions")
	public Function[] getFunctions() {
		return Function.values();
	}
	
	
}
