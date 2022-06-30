package fr.bouget.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

/**
 * Pour changer par rapport aux divers TP,
 * les annotations sont placées sur les méthodes getXXX()
 * @author pbouget
 *
 */
@Entity
@Table(name="CLIENT")
@SecondaryTable(name="ADRESSE", pkJoinColumns={@PrimaryKeyJoinColumn(name="ID_CLIENT")})
public class Client implements Serializable {
	public static final long serialVersionUID=987654378L;

	private int id;
	private String nom;
	private String prenom;
	private String voie;
	private String complement;
	private String codePostal;
	private String ville;
	private String pays;

	public Client(){
		super();
	}

	public Client(String nom, String prenom, String voie,String complement,String codePostal,String ville,String pays){
		this.nom=nom;
		this.prenom=prenom;
		this.voie=voie;
		this.complement=complement;
		this.codePostal=codePostal;
		this.ville=ville;
		this.pays=pays;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 50, nullable = false)
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(length = 50, nullable = false)
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(length = 32, name="VOIE", table="ADRESSE")
	public String getVoie() {
		return voie;
	}

	public void setVoie(String voie) {
		this.voie = voie;
	}

	@Column(length = 32, name="COMPLT", table="ADRESSE")
	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Column(length = 5, name="CP", table="ADRESSE")
	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	@Column(length=45, name="VILLE", table="ADRESSE")
	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Column(length=50, name="PAYS", table="ADRESSE")
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("id : "+id+"\n");
		sb.append("nom= : "+nom+" prenom="+prenom+"\n");
		sb.append("Adresse :  voie = "+voie+" complement = "+complement+" codePostal = "+codePostal+" ville = "+ville+" pays= "+pays+"\n");
		return sb.toString();
	}

}
