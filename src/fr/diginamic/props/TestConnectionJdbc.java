package fr.diginamic.props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class TestConnectionJdbc {

	public static void main(String[] args) {
		
		ResourceBundle configFile = ResourceBundle.getBundle("database"); // achtung, ne pas écrire ".properties"
		String driverClass	= configFile.getString("db.driver");
		String dbUrl 		= configFile.getString("db.url");
		String dbUserName 	= configFile.getString("db.user.name");
		String dbUserPwd 	= configFile.getString("db.user.pwd");
		
		// Chargement du Driver de la DB MySQL (on peut faire de même avec MariaDB)
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			System.out.println("Echec de chargement du Driver");
		}

		// Demande de connection a une DB de type MySQL
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(dbUrl, dbUserName, dbUserPwd);
//			Ajouter "?serverTimezone=UTC&useLegacyDatetimeCode=false" après le nom de la base, si ça ne fonctionne pas. 
			System.out.println("Connexion ouverte : " + !connection.isClosed());
		} catch (SQLException e) {
			System.out.println("Echec de connexion à la base de données : " + e.getMessage());
		}
		
		Statement statement;
		try {
			
			statement = connection.createStatement();
//			int resultInsert = statement.executeUpdate(
//					"INSERT INTO abonne(email, password, first_name, last_name, birthdate, bio, class, "
//					+ "age, createdAt, updatedAt) "
//					+ "VALUES "
//					+ "('e@dftr', 'pwd999', 'Gladys', 'Debussy', '1989-05-16',"
//					+ "'who am I ?', '4C', 31, '2021-01-13 09:53:54', '2021-01-13 09:53:54')");
//			
//			System.out.println("Nombre de lignes affectées par la requête réalisée : " + resultInsert);
//			
//			int resultDelete = statement.executeUpdate("DELETE FROM etudiants WHERE id > 11");
//			
//			System.out.println("Nombre de lignes affectées par la requête réalisée : " + resultDelete);
//			
			System.out.println("Liste des abonnés : ");
			ResultSet curseur = statement.executeQuery("SELECT * FROM abonne ORDER BY ville DESC");
			while(curseur.next()) {
				Integer id = curseur.getInt("id");
				String nom = curseur.getString("nom");
				Date date_naissance = curseur.getDate("date_naissance");
//				System.out.println("id = " + id + " - " + lastname.toUpperCase());
			}
			
			curseur.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Echec de la requête SQL : " + e.getMessage());
		}
	}
}
