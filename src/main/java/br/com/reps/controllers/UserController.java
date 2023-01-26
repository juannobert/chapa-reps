package br.com.reps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.entities.enums.Officie;

@Controller
@RequestMapping("/usuario")
public class UserController {

	//@Autowired
	//private UserService service;
	
	@GetMapping("/admin/novo")
	public ModelAndView userForm() {
		ModelAndView mv = new ModelAndView("user/form-user");
		mv.addObject("form", new UserAdminRequest());
		return mv; 
		
	}
	
	@ModelAttribute("positions")
	public Officie[] getOfficie() {
		return Officie.values();
	}
	
}
