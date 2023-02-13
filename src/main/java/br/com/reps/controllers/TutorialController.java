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

import br.com.reps.dtos.requests.TutorialRequest;
import br.com.reps.dtos.responses.AlertMessage;
import br.com.reps.dtos.responses.TutorialResponse;
import br.com.reps.services.TutorialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tutoriais")
public class TutorialController {

	@Autowired
	private TutorialService service;
	
	
	@GetMapping
	public ModelAndView findAll(@PageableDefault(size = 5) Pageable pageable) {
		ModelAndView mv = new ModelAndView("tutorial/tutorials");
		mv.addObject("posts",service.findAll(pageable));
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView formTutorial() {
		ModelAndView mv = new ModelAndView("tutorial/form-tutorial");
		mv.addObject("form", new TutorialRequest());
		mv.addObject("alter", false);

		
		return mv;
	}
	
	@PostMapping("/novo")
	public String formTutorial(@Valid @ModelAttribute("form") TutorialRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors()) {
			return "tutorial/form-tutorial";
		}
		service.insert(request);
		attrs.addFlashAttribute("alert",new AlertMessage("Tutorial publicado com sucesso","alert-primary"));
		return "redirect:/tutoriais";
	}
	
	@GetMapping("/excluir/{id}")
	public String delete(@PathVariable Long id,HttpServletRequest request,RedirectAttributes attrs) {
		service.delete(id);
		attrs.addFlashAttribute("alert",new AlertMessage("Tutorial excluido com sucesso","alert-primary"));
		return "redirect:/tutoriais";
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("tutorial/form-tutorial");
		TutorialResponse response = service.findByid(id);
		response.setUrl(service.toLink(response.getUrl()));
		mv.addObject("form", response);
		mv.addObject("alter", true);
		
		return mv;
	}
	
	@PostMapping("/alterar/{id}")
	public String alterar(@Valid @ModelAttribute("form") @PathVariable Long id, TutorialRequest request,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors()) {
			return "tutorial/form-tutorial";
		}
		
		service.alter(id, request);
		attrs.addFlashAttribute("alert",new AlertMessage("Tutorial alterado com sucesso","alert-primary"));
		return "redirect:/tutoriais";
		
	}
}
