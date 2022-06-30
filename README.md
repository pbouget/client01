# 5 - ENTITY CLIENT (corrigé): MAPPING MULTI-TABLES avec *@SecondaryTable* (**client01**)

![client01](images/client01.png)

Terminée la picole, maintenant c'est du sérieux !
Et bientôt, les petites croisières...

Vous allez créér une entité java qui correspondra à 2 tables dans la base de données MySQL.

A FAIRE :

- Créer votre projet SpringBoot **client01** 
- Créer les 3 packages `comme dans` **pinard01**

- Créer une Entité **Client** mappée sur deux tables :

  - **Client** (id, nom(50), prenom(50))
  - **Adresse** (voie(32), complement(32), codePostal(5), ville(45), pays(50))

## Création de la classe Client

Dans le package **model**, créez la classe **Client** qui comportera les attributs suivants :

```java
    private int id;
    private String nom;
    private String prenom;
    private String voie;
    private String complement;
    private String codePostal;
    private String ville;
    private String pays;
```

- La clé primaire `id` sera auto-générée
- La table principale sera appelée `CLIENT`
- La table secondaire sera appelée `ADRESSE`
- Les propriétés **voie, complement, codePostal, ville et pays** seront mappés respectivement sur les colonnes nommées de la table Adresse :

  - VOIE
  - COMPLT
  - VILLE
  - PAYS

- Pour que la table secondaire soit créée dans la base de données, vous devez ajouter les annotations suivantes dans votre classe **Client.java** :

```java
@Table(name="CLIENT")
@SecondaryTable(name="ADRESSE", pkJoinColumns={@PrimaryKeyJoinColumn(name="ID_CLIENT")})

```

- Les champs mappés sur la table adresse devront comporter l’annotation ci-dessous que vous devez adapter :

```java
@Column(length = 32, name="VOIE", table="ADRESSE")
	public String getVoie() {
		return voie;
	}
```

- Créer votre interface **Repository** pour gérer vos clients en héritant de l'interface **JpaRepository**.

Voici le code à ajouter dans votre contrôleur :

```java
package fr.bouget.jpa.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bouget.jpa.model.Client;
import fr.bouget.jpa.repository.ClientRepository;

/**
 * @author Philippe
 *
 */
@CrossOrigin("*")
@RestController
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;
	
	@GetMapping("/accueil")
	@ResponseBody
	public String home()
	{

		Client martin=new Client("MARTINO","Jeanno","65, rue de la Republico","","78105","VERSAILLES", "FRANCE");
		martin=clientRepository.saveAndFlush(martin);

		Client dupont=new Client("DUPONTI","sophia","5, rue du Renardi","","75014","PARIS","FRANCE");
		dupont=clientRepository.saveAndFlush(dupont);

		Client durand=new Client("DURANDO","Pierrot","20, boulevard Gambetto","","78200","POISSY","FRANCE");
		durand=clientRepository.saveAndFlush(durand);

		Client madec=new Client("MODICA","Denise","29, boulevard Divin","","78400","POISSY","FRANCE");
		clientRepository.saveAndFlush(madec);

		System.out.println();
		System.out.println("Liste de tous les clients:");
		Collection<Client> liste=clientRepository.findAll();
		this.affiche(liste);

		System.out.println("MARTIN Jean habite desormais avec DUPONTI Sophia :");
		martin.setVoie(dupont.getVoie());
		martin.setComplement(dupont.getComplement());
		martin.setCodePostal(dupont.getCodePostal());
		martin.setVille(dupont.getVille());
		martin.setPays(dupont.getPays());
		clientRepository.saveAndFlush(martin);

		System.out.println("DURANDO Pierrot est décédé :");
		clientRepository.delete(durand);

		System.out.println("Liste de tous les clients :");
		this.affiche(clientRepository.findAll());
		
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Regardez dans votre console et dans votre base de données MySQL <strong>JPA</strong></h1>");
		sb.append("<a href='http://localhost:8080/clients'>Voir la liste des clients enregistrés</a>");
		return  sb.toString();

	}
	
	@GetMapping(value = "/clients")
	public ResponseEntity<?> getAll(){
		List<Client> liste = null;
		try
		{
			liste = clientRepository.findAll();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(liste);
	}

	/**
	 * Méthode pour affichage dans la console
	 * @param liste
	 */
	private void affiche(Collection<Client> liste)
	{

		for (Client client : liste) {

			System.out.println(client);
		}

	}

}

```

- Lancez l'application Spring Boot et observez les tables générées dans votre base de données.

- Appelez la méthode **home()** dans votre navigateur pour initialiser les clients en base de données en saisissant l'url ci-dessous dans votre navigateur :
  
[http://localhost:8080/accueil](http://localhost:8080/accueil)

- Ensuite cliquez sur ce lien [http://localhost:8080/clients](http://localhost:8080/clients) pour visualiser vos clients enregistrés.

- Vous devez obtenir une structure de fichier json ressemblant à l'image ci-dessous :

![tp1-multitables.png](images/client05-resultat.png)

[Retour vers les exercices](https://pbouget.github.io/cours/framework-back/1-jpa-orm/mapping-orm.html)

[Retour vers le cours complet](https://pbouget.github.io/cours/)