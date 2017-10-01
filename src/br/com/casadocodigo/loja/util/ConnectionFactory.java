package br.com.casadocodigo.loja.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/alura",
					"root", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
