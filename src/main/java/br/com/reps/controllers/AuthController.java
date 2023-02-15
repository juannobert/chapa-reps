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

import br.com.reps.dtos.requests.CodeRequest;
import br.com.reps.dtos.requests.ForgetPasswordRequest;
import br.com.reps.dtos.requests.UserDefaultRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.dtos.responses.AlterPasswordRequest;
import br.com.reps.entities.enums.Function;
import br.com.reps.services.UserService;
import br.com.reps.services.exceptions.ValidationException;
import jakarta.servlet.http.HttpSession;
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
		mv.addObject("form", new ForgetPasswordRequest());
		
		return mv;
	}
	
	@PostMapping("/esqueci-a-senha")
	public String forgetPassword(@Valid @ModelAttribute("form") ForgetPasswordRequest request, BindingResult result,HttpSession session) {
		if (result.hasErrors()) 
			return "auth/forget-password";
		String code = userService.recoveryCode(request);
		session.setAttribute("code", code);
		session.setAttribute("email", request.getEmail());
		
		return "redirect:/auth/verificar-codigo";
	}
	
	@GetMapping("/verificar-codigo")
	public ModelAndView codeVerification() {
		ModelAndView mv = new ModelAndView("auth/code-verification");
		mv.addObject("form", new CodeRequest());
		
		return mv;
	}
	
	@PostMapping("/verificar-codigo")
	public ModelAndView forgetPassword(@Valid @ModelAttribute("form") CodeRequest request, BindingResult result,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) 
			return new ModelAndView("auth/code-verification");
		String codeSession = (String) session.getAttribute("code");
		String codeRequest = request.getEmailCode();
		if(codeSession.equals(codeRequest)) {
			mv.setViewName("redirect:/auth/alterar-senha");
			return mv;
		}
		//try catch e atualizar link
		mv.setViewName("auth/code-verification");
		mv.addObject("isValid", false);
		return mv;
	}
	
	@GetMapping("/alterar-senha")
	public ModelAndView alterPassword() {
		ModelAndView mv = new ModelAndView("auth/alter-password");
		mv.addObject("form", new AlterPasswordRequest());
		
		return mv;
	}
	
	@PostMapping("/alterar-senha")
	public String alterPassword(@Valid @ModelAttribute("form") AlterPasswordRequest request, BindingResult result,RedirectAttributes attrs,
			HttpSession session) {
		if (result.hasErrors()) 
			return "auth/alter-password";
		try{
		String email = (String) session.getAttribute("email");
		userService.alterPassword(request,email);
		attrs.addFlashAttribute("alert", new AlertMessage("Senha alterada com sucesso", "alert-success"));
		
		return "redirect:/auth/login";
	} catch (ValidationException e) {
		result.addError(e.getFieldError());
		return "auth/alter-password";
	}
		
	}
	
	@ModelAttribute("functions")
	public Function[] getFunctions() {
		return Function.values();
	}
}
