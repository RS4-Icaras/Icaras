package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.dao.relatiebeheer.IOrganisatieDao;
@Service("IOrganisatieService")
@Transactional
public class OrganisatieService implements IOrganisatieService {

	@Autowired
	private IOrganisatieDao organisatieDao;
	
	@Override
	public void save(Organisatie organisatie) {
		organisatieDao.save(organisatie);
	}

	@Override
	public void delete(Organisatie organisatie) {
		// TODO do some for loop magic
		organisatieDao.delete(organisatie);
	}

	@Override
	public void update(Organisatie organisatie) {
		organisatieDao.update(organisatie);
	}

	@Override
	public Organisatie getById(int id) {
		return organisatieDao.getById(id);
	}

	@Override
	public Organisatie getByIdMetAdressenEnNfaLijst(int id) {
		return organisatieDao.getByIdMetAdressenEnNfaLijst(id);
	}

	@Override
	public Organisatie getByIdMetRollen(int id) {
		return organisatieDao.getByIdMetRollen(id);
	}

	@Override
	public Organisatie getByIdCompleet(int id) {
		return organisatieDao.getByIdCompleet(id);
	}

	@Override
	public List<Organisatie> getAll() {
		return organisatieDao.getAll();
	}

	@Override
	public List<Organisatie> getAllMetAdressenEnNfaLijst() {
		return organisatieDao.getAllMetAdressenenNfaLijst();
	}

	@Override
	public List<Organisatie> getAllMetRollen() {
		return organisatieDao.getAllMetRollen();
	}

	@Override
	public List<Organisatie> getAllCompleet() {
		return organisatieDao.getAllCompleet();
	}
	
	public List<Organisatie> getAllMetBedrijfsrol() {
		return organisatieDao.getAllMetBedrijfsrol();
	}

}
