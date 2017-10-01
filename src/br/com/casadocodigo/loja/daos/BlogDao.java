package br.com.casadocodigo.loja.daos;

import java.util.List;

import br.com.casadocodigo.loja.models.BlogPost;

public interface BlogDao {
	
	public void salvaBlogPost(BlogPost post);
	public List<BlogPost> buscaMensagens();
}
