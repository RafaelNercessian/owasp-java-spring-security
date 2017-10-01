package br.com.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class UsuarioController {
	
	@RequestMapping("/login_teste")
	public String login(){
		return "login_teste";
	}
	
	@RequestMapping("/usuario")
	public String usuario(){
		return "usuarioLogado";
	}
	
}
