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

import br.com.reps.dtos.requests.ForgetPasswordResquest;
import br.com.reps.dtos.requests.UserDefaultRequest;
import br.com.reps.entities.enums.Function;
import br.com.reps.services.UserService;
import br.com.reps.services.exceptions.ValidationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@GetMapping("/cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("auth/register");
		mv.addObject("form", new UserDefaultRequest());
		return mv;
	}

	@PostMapping("/cadastro")
	public String cadastro(@Valid @ModelAttribute("form") UserDefaultRequest request, BindingResult result,
			RedirectAttributes attrs) {
		try {
			if (result.hasErrors()) {
				return "auth/register";
			}
			userService.insert(request);
			return "redirect:/auth/login";
		} catch (ValidationException e) {
			result.addError(e.getFieldError());
			return "auth/register";
		}
	}
	
	@GetMapping("/esqueci-a-senha")
	public ModelAndView forgetPassowrd() {
		ModelAndView mv = new ModelAndView("auth/forget-password");
		mv.addObject("form", new ForgetPasswordResquest());
		
		return mv;
	}

	@ModelAttribute("functions")
	public Function[] getFunctions() {
		return Function.values();
	}
}
