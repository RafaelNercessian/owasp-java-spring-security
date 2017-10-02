package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao, UserDetailsService {

	@PersistenceContext
	private EntityManager manager;

	public void salva(Usuario usuario) {
		manager.persist(usuario);
	}

	public Usuario procuraUsuario(String email) {
		TypedQuery<Usuario> query = manager.createQuery(
				"select u from Usuario u where u.email =:email", Usuario.class)
				.setParameter("email", email);
		Usuario usuarioRetornado = query.getResultList().stream().findFirst()
				.orElse(null);
		return usuarioRetornado;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Usuario usuario = manager
				.createQuery("select u from Usuario u where u.email =:email",
						Usuario.class).setParameter("email", username)
				.getResultList().stream().findFirst().orElse(null);
		return usuario;

	}
}
