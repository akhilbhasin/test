package com.abc.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abc.dao.model.GroupIdToSubscription;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

@Repository
public class GroupIdToSubscriptionsSQLImpl extends GenericDAOImpl<GroupIdToSubscription, Integer> {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public List<GroupIdToSubscription> getEntities(final Search search) {
		return this.search(search);
	}

}
