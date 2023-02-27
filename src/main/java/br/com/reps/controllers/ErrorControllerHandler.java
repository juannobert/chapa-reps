package br.com.reps.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerHandler implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		ModelAndView mv = new ModelAndView("error");
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				mv.addObject("text", "A página solicitada não existe");
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				mv.addObject("text", "Ocorreu um error no servidor");
			}
			else if(statusCode == HttpStatus.UNAUTHORIZED.value() || statusCode ==  HttpStatus.FORBIDDEN.value()) {
				mv.addObject("text", "Você não tem autorização para acessar esse recurso");
			}
		}
		return mv;
	}
}
