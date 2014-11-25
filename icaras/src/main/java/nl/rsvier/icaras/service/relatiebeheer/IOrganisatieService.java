package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;

public interface IOrganisatieService {

	public void save(Organisatie organisatie);

	public void delete(Organisatie organisatie);

	public void update(Organisatie organisatie);

	public Organisatie getById(int id);

	public Organisatie getByIdMetAdressenEnNfaLijst(int id);

	public Organisatie getByIdMetRollen(int id);

	public Organisatie getByIdCompleet(int id);

	public List<Organisatie> getAll();

	public List<Organisatie> getAllMetAdressenEnNfaLijst();

	public List<Organisatie> getAllMetRollen();

	public List<Organisatie> getAllCompleet();

	public List<Organisatie> getAllMetBedrijfsrol();

	public List<Organisatie> getAllAanTeBiedenOrganisaties(Persoon persoon);

}
