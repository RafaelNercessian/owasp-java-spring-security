package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.Usuario;

public interface UsuarioDao {
	public void salva(Usuario usuario);
	public Usuario procuraUsuario(String email);
	public boolean verificaSeUsuarioEhAdmin(Usuario usuario);
}
