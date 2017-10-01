package br.com.casadocodigo.loja.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.util.ConnectionFactory;

@Repository
public class UsuarioDaoImpl implements UsuarioDao, UserDetailsService {

	// Será usado na primeira aula, depois mudaremos para o EntityManager
	Connection connection = new ConnectionFactory().getConnection();

	// Será usado mais adiante
	@PersistenceContext
	private EntityManager manager;

	public void salva(Usuario usuario) {
		String query = "insert into usuarios (email,senha,nome,nomeImagem) values ('"
				+ usuario.getEmail()
				+ "','"
				+ usuario.getSenha()
				+ "','"
				+ usuario.getNome()
				+ "','"
				+ usuario.getNomeImagem() + "');";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Usuario procuraUsuario(String email) {
		String query = "SELECT * FROM usuarios WHERE email=" + "'"
				+ email + "';";
		try {
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			Usuario usuarioRetorno = new Usuario();
			while (results.next()) {
				usuarioRetorno.setEmail(results.getString("email"));
				usuarioRetorno.setSenha(results.getString("senha"));
				usuarioRetorno.setNomeImagem(results.getString("nomeImagem"));
				usuarioRetorno.setNome(results.getString("nome"));
			}
			if (usuarioRetorno.getEmail() == null
					&& usuarioRetorno.getSenha() == null) {
				return null;
			} else {
				return usuarioRetorno;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean verificaSeUsuarioEhAdmin(Usuario usuario) {
		TypedQuery<Usuario> query = manager
				.createQuery(
						"select u from Usuario u where u.email =:email and u.senha =:senha",
						Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		Usuario retornoUsuario = query.getResultList().stream().findFirst()
				.orElse(null);
		if (retornoUsuario != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		String jpql = "select u from Usuario u where u.email = :email";
		List<Usuario> users = manager.createQuery(jpql, Usuario.class)
				.setParameter("email", username).getResultList();
		if(!users.isEmpty()){
			return users.get(0);
		}
		return null;
	}
}
