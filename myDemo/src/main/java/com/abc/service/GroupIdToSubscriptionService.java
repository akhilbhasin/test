package com.abc.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc.dao.GroupIdToSubscriptionsSQLImpl;
import com.abc.dao.model.GroupIdToSubscription;
import com.googlecode.genericdao.search.Search;

@Service
public class GroupIdToSubscriptionService {

	@Autowired
	GroupIdToSubscriptionsSQLImpl sqlDao;

	@Transactional
	public void addSubscription(String groupId, String topic) {
		GroupIdToSubscription subscription = new GroupIdToSubscription();
		subscription.setGroupId(groupId);
		subscription.setSubName(topic);
		sqlDao.save(subscription);
	}

	@Transactional
	public List<String> getSubscriptions(String groupId) {
		Search s = new Search(GroupIdToSubscription.class);
		s.addFilterEqual("groupId", groupId);
		return sqlDao.getEntities(s).stream().map(entity -> entity.getSubName()).collect(Collectors.toList());
	}

	@Transactional
	public void removeSubscription(String groupId, String topic) {
		GroupIdToSubscription subscription = new GroupIdToSubscription();
		subscription.setGroupId(groupId);
		subscription.setSubName(topic);
		Search s = new Search(GroupIdToSubscription.class);
		s.addFilterEqual("groupId", groupId);
		s.addFilterEqual("subName", topic);
		List<GroupIdToSubscription> l = sqlDao.getEntities(s);
		List<Integer> l1 = l.stream().map(sub -> sub.getId()).collect(Collectors.toList());
		sqlDao.removeByIds(l1.toArray(new Serializable[0]));
	}

}
