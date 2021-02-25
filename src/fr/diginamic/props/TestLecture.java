package fr.diginamic.props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TestLecture {

	public static void main(String[] args) {
		
		ResourceBundle configFile = ResourceBundle.getBundle("database"); // Ne pas écrire ".properties"
		String driverClass	= configFile.getString("db.driver");
		String dbUrl 		= configFile.getString("db.url");
		String dbUserName 	= configFile.getString("db.user.name");
		String dbUserPwd 	= configFile.getString("db.user.pwd");
		
		// Chargement du Driver de la DB MySQL (on peut faire de même avec MariaDB)
		try {
			Class.forName(driverClass);
			// Class.forName() va charger la classe dont le nom est passée en paramètre
			// Les blocs statiques de celles-ci vont s'exécuter
			// notamment pour le driver, la méthode DriverManager.registerDriver(new Driver());
			// qui nous permet de charger le pilote nécessaire à la connexion à la DB
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
		
			// Affichage et stockage de tous les abonnés et du nombre de lignes retournées
			List<Abonne> listeAbonnes = new ArrayList<>();
			System.out.println("Liste des abonnés : ");
			ResultSet curseur = statement.executeQuery("SELECT * FROM abonne ORDER BY date_naissance DESC");
			while(curseur.next()) {
				
				Integer id 					= curseur.getInt("id");
				String 	nom 				= curseur.getString("nom");
				String 	prenom 				= curseur.getString("prenom");
				Date 	date_naissance 		= curseur.getDate("date_naissance");
				String 	adresse 			= curseur.getString("adresse");
				String 	code_postal 		= curseur.getString("code_postal");
				String 	ville 				= curseur.getString("ville");
				Date 	date_inscription 	= curseur.getDate("date_inscription");
				Date 	date_fin_abo 		= curseur.getDate("date_fin_abo");
				
				// Creation d'une instance Abonne pour chaque ligne retournée par la requête
				Abonne abo = new Abonne(id, prenom, nom, date_naissance, adresse, 
						code_postal, ville, date_inscription, date_fin_abo);
				
				// Stockage de l'abonné créé dans ma liste d'abonnés
				listeAbonnes.add(abo);
			}
			
			// Affichage de tous les Abonne de ma liste
			for (Abonne abo : listeAbonnes) {
				System.out.println(abo);
			}
			
			curseur.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Echec de la requête SQL : " + e.getMessage());
		}
	}
}
