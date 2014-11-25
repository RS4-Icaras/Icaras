package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Persoon;

public interface IPersoonService {

	public void save(Persoon persoon);

	public void delete(Persoon persoon);

	public void update(Persoon persoon);

	public Persoon getById(int id);

	public Persoon getByIdMetAdressenEnNfaLijst(int id);

	public Persoon getByIdMetRollen(int id);

	public Persoon getByIdCompleet(int id);

	public List<Persoon> getAll();

	public List<Persoon> getAllMetAdressenEnNfaLijst();

	public List<Persoon> getAllMetRollen();

	public List<Persoon> getAllCompleet();

	public List<Persoon> getAllMetKandidaat();

	public List<Persoon> getAllMetWerknemerEnKandidaat();

	public List<Persoon> getAllMetContactPersoon();
}
