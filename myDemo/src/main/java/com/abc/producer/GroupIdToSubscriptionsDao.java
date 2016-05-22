package com.abc.producer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class GroupIdToSubscriptionsDao {
	private static final ConcurrentHashMap<String, List<String>> m = new ConcurrentHashMap<>();

	public List<String> getSubscriptionsForGroupId(final String groupId) {
		if (m.containsKey(groupId))
			return m.get(groupId);
		return new ArrayList<>();
	}

	public void addSubscriptionToGroupId(final String groupId, final String topic) {
		if (m.containsKey(groupId)) {
			List<String> l = m.get(groupId);
			ArrayList<String> l1 = new ArrayList<>(l);
			l1.add(topic);
			m.put(groupId, l1);
		} else {
			m.put(groupId, Arrays.asList(topic));
		}
	}

	public void unsubscribeTopic(final String groupId, final String topic) {
		if(m.containsKey(groupId)){
			List<String> l = m.get(groupId);
			ArrayList<String> l1 = new ArrayList<>(l);
			l1.remove(topic);
			m.put(groupId, l1);
		}else{
			throw new IllegalArgumentException("invalid groupId");
		}
	}
}
