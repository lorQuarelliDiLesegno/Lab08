package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.dizionariograph.model.Parola;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int length) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}
			conn.close();
			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Parola> aggiungiArchiTramiteQuery(String p, String s) {
		String sql = "SELECT p2.nome " + 
				"FROM parola as p1, parola as p2 " + 
				"WHERE p1.nome = ? " + 
				"AND p1.nome != p2.nome " + 
				"AND p2.nome LIKE ? ";
		List<Parola> parole = new ArrayList<Parola>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			st.setString(2, s);
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(new Parola(res.getString("p2.nome")));
			}
			conn.close();
			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	

}
