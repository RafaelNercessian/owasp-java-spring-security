package br.com.casadocodigo.loja.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.UsuarioDao;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
@Transactional
public class UsuarioController {

	@Autowired
	private UsuarioDao dao;
	
	@Autowired
    protected AuthenticationManager authenticationManager;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}

	@RequestMapping("/usuario")
	public String usuario(Principal principal, Model model) {
		Usuario retornoUsuario = dao.procuraUsuario(principal.getName());
		model.addAttribute("usuario", retornoUsuario);
		return "usuarioLogado";
	}

	@RequestMapping(value="/registrar",method=RequestMethod.POST)
	public String registrar(@ModelAttribute("usuario") Usuario usuario,
			RedirectAttributes redirect, HttpServletRequest request, Model model) {
		chamaLogicaParaTratarImagem(usuario, request);
		usuario.setRoles(Arrays.asList(new Role()));
		dao.salva(usuario);
		autenticaUsuarioEConfiguraSessao(usuario, request);
		model.addAttribute("usuario", usuario);
		return "usuarioLogado";

	}
	
	private void autenticaUsuarioEConfiguraSessao(Usuario usuario,
			HttpServletRequest request) {
		String username = usuario.getEmail();
		String password = usuario.getSenha();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager
				.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

	}

	private void chamaLogicaParaTratarImagem(Usuario usuario,
			HttpServletRequest request) {
		usuario.setNomeImagem(usuario.getImagem().getOriginalFilename());
		File imageFile = new File(request.getServletContext().getRealPath(
				"/image"), usuario.getNomeImagem());
		try {
			usuario.getImagem().transferTo(imageFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
