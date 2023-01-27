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

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.entities.enums.Officie;
import br.com.reps.services.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/admin/novo")
	public ModelAndView userForm() {
		ModelAndView mv = new ModelAndView("user/form-user");
		mv.addObject("form", new UserAdminRequest());
		return mv; 
		
	}
	
	@PostMapping("/admin/novo")
	public String supportForm(@Valid @ModelAttribute("form") UserAdminRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors())
			return "user/form-user";
		service.insert(request, true);
		attrs.addFlashAttribute("alert",new AlertMessage("Gremista adicionado com sucesso","alert-primary"));		
		return "redirect:/usuario/admin/novo";
	}
	
	@ModelAttribute("positions")
	public Officie[] getOfficie() {
		return Officie.values();
	}
	
}
