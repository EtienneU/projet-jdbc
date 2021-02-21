package fr.diginamic.props;

import java.util.Date;

public class Abonne {
	
	private Integer id;
	private String prenom;
	private String nom;
	private Date date_naissance;
	private String adresse;
	private String code_postal;
	private String ville;
	private Date date_inscription;
	private Date date_fin_abo;
	
	public Abonne(Integer id, String prenom, String nom, Date date_naissance, String adresse, String code_postal,
			String ville, Date date_inscription, Date date_fin_abo) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.date_naissance = date_naissance;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.ville = ville;
		this.date_inscription = date_inscription;
		this.date_fin_abo = date_fin_abo;
	}
	
	public String getVille() {
		return ville;
	}

	@Override
	public String toString() {
		return "id=" + id + ": " + prenom + " " + nom.toUpperCase() + ", né.e le " + date_naissance
				+ ", ville=" + ville;
	}
	
	

}
