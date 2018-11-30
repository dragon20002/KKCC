
package kr.ac.hansung.cse.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Token;

@Repository
//@EnableTransactionManagement
@Transactional
public class TokenDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<String> getTokenValues() {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<String> query = session.createQuery("select token.value from Token token");
		return query.getResultList();
	}

	public void insertToken(Token token) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(token);
		session.flush();
	}

}
