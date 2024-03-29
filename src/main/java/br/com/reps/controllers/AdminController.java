package br.com.reps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.reps.dtos.requests.UserAdminRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.entities.enums.Officie;
import br.com.reps.entities.enums.UserRole;
import br.com.reps.services.UserService;
import br.com.reps.services.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService service;
	
	@GetMapping("/novo")
	public ModelAndView userForm() {
		ModelAndView mv = new ModelAndView("user/form-user");
		mv.addObject("form", new UserAdminRequest());
		return mv; 
		
	}
	
	@PostMapping("/novo")
	public String supportForm(@Valid @ModelAttribute("form") UserAdminRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors())
			return "user/form-user";
		try {
		service.insert(request);
		attrs.addFlashAttribute("alert",new AlertMessage("Gremista adicionado com sucesso","alert-primary"));		
		return "redirect:/admin/novo";
		}catch(ValidationException e) {
			result.addError(e.getFieldError());
			return "user/form-user";
		}
	}
	
	@GetMapping("/usuarios")
	public ModelAndView allUsers(@PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView("user/list");
		mv.addObject("users", service.findAll(pageable, UserRole.ALUNO));
		mv.addObject("isUser", true);
		
		return mv;
	}
	
	@GetMapping("/gremistas")
	public ModelAndView allGremistas(@PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView("user/list");
		mv.addObject("users", service.findAll(pageable, UserRole.GREMISTA));
		mv.addObject("isUser", false);

		return mv;
	}
	
	@GetMapping("/deletar-usuario/{id}")
	public String delete(@PathVariable Long id,HttpServletRequest request,RedirectAttributes attrs) {
		service.delete(id);
		attrs.addFlashAttribute("alert",new AlertMessage("Usuário removido com sucesso","alert-primary"));
		String path = request.getHeader("Referer");
		if(path.endsWith("/usuarios"))
			return "redirect:/admin/usuarios";
		return "redirect:/admin/gremistas";
	}
	
	
	
	
	@ModelAttribute("positions")
	public Officie[] getOfficie() {
		return Officie.values();
	}
	
}
