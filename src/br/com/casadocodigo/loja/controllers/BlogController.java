package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.BlogDao;
import br.com.casadocodigo.loja.models.BlogPost;

@Controller
@Transactional
public class BlogController {

	@Autowired
	private BlogDao dao;

	@RequestMapping("/blog")
	public String blog(Model model) {
		BlogPost blogPost = new BlogPost();
		model.addAttribute(blogPost);
		List<BlogPost> mensagens = dao.buscaMensagens();
		model.addAttribute("lista", mensagens);
		return "blog";
	}

	@RequestMapping(value = "/enviaMensagem", method = RequestMethod.POST)
	public String enviaMensagem(
			@ModelAttribute(value = "blog") BlogPost blog,
			RedirectAttributes redirect, Model model) {
		dao.salvaBlogPost(blog);
		return "redirect:/blog";
	}

}
