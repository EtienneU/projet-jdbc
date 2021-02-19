package fr.diginamic.props;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class TestConfig {

	public static void main(String[] args) {

		ResourceBundle configFile = ResourceBundle.getBundle("data"); // achtung, ne pas écrire ".properties"

//		System.out.println(configFile.getClass().getSimpleName());
		String configFimeName = configFile.getString("nom");
//		System.out.println(configFimeName);
		
		Enumeration<String> keys = configFile.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			// Affichage des clés
			System.out.println(key);
			// Affichage des valeurs
			System.out.println(configFile.getString(key));
		}
		
	}

}
