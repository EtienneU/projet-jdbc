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
		
		// achtung, ne pas écrire ".properties"
		ResourceBundle configFile = ResourceBundle.getBundle("database");
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
			// Requête de création d'un abonne
			int resultInsert = statement.executeUpdate(
					"INSERT INTO abonne(prenom, nom, date_naissance, adresse, code_postal, "
					+ "ville, date_inscription, date_fin_abo) "
					+ "VALUES "
					+ "('Sophie', 'TELLIER', '1984-07-23', '43 impasse Lievin', '34000',"
					+ "'Montpellier', '2011-08-30', '2021-01-13')");
			
			System.out.println("Nombre de lignes affectées par la requête réalisée : " + resultInsert);
			
			// Requête de suppression de plusieurs abonnes
//			int resultDelete = statement.executeUpdate("DELETE FROM abonne WHERE ville = 'NICE'");
//			System.out.println("Nombre de lignes affectées par la requête réalisée : " + resultDelete);
//			
			
			// Affichage de tous les abonnés et du nombre de lignes retournées
			int count = 0;
			System.out.println("Liste des abonnés : ");
			ResultSet curseur = statement.executeQuery("SELECT * FROM abonne ORDER BY date_naissance DESC");
			while(curseur.next()) {
				count++;
				Integer id = curseur.getInt("id");
				String nom = curseur.getString("nom");
				Date date_naissance = curseur.getDate("date_naissance");
				System.out.println("id = " + id + " - " + nom.toUpperCase());
			}
			System.out.println(count + " lignes retournées");
			
			curseur.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Echec de la requête SQL : " + e.getMessage());
		}
	}
}
