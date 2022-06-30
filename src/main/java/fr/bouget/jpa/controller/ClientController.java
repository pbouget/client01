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
