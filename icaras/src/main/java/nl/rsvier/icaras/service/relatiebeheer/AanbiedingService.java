package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.dao.arbeidsmarkt.IAanbiedingDao;
@Service("IAanbiedingService")
@Transactional
public class AanbiedingService implements IAanbiedingService {

	@Autowired
	private IAanbiedingDao aanbiedingDao;
	@Override
	public Aanbieding getById(int id) {
		return aanbiedingDao.getById(id);
	}

	@Override
	public List<Aanbieding> getAll() {
		return aanbiedingDao.getAll();
	}

}
