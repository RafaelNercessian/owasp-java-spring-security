package br.com.casadocodigo.loja.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="ROLE")
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NAME")
	private String name="ROLE_USER";
	
	@Override
	public String getAuthority() {
		return name;
	}

}
