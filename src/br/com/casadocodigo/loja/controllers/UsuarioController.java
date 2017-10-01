package br.com.casadocodigo.loja.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.daos.UsuarioDao;
import br.com.casadocodigo.loja.models.Usuario;

@Controller

public class UsuarioController {
	
	@Autowired
	private UsuarioDao dao;
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/usuario")
	public String usuario(Principal principal, Model model){
		Usuario retornoUsuario = dao.procuraUsuario(principal.getName());
		model.addAttribute("usuario", retornoUsuario);
		return "usuarioLogado";
	}
	
}
