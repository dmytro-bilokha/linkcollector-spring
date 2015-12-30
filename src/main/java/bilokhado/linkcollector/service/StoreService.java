package bilokhado.linkcollector.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bilokhado.linkcollector.entity.SearchQuery;

@Component
public class StoreService {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void store(String searchQuery) {
		em.persist(new SearchQuery(searchQuery.hashCode()));
	}
}
