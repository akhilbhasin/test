package com.abc.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Resources;

public class ConsumerProxy {

	private static Logger log = LoggerFactory.getLogger(ConsumerProxy.class);
	private final KafkaConsumer<String, String> kafkaConsumer;

	public ConsumerProxy(final String groupId) {
		try (InputStream props = Resources.getResource("consumer.props").openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			log.warn("zzzz group id being added:" + groupId);
			properties.setProperty("group.id", groupId);
			log.warn("zzz groupId set is:"+properties.getProperty("group.id"));
			this.kafkaConsumer = new KafkaConsumer<>(properties);
		} catch (IOException e) {
			throw new RuntimeException("unable to create KafkaConsumer for groupId" + groupId);
		}

	}

	public List<String> getMessages(final String topic) {
		try {
			kafkaConsumer.subscribe(Arrays.asList(topic));
			log.warn("zzzz topic: " + topic);
			ConsumerRecords<String, String> records = kafkaConsumer.poll(5000);
			log.warn("zzzz records: " + records.toString());
			log.warn("zzzz records: " + records.isEmpty());
			log.warn("zzzz count: " + records.count());
			List<String> messages = new ArrayList<>();
			for (ConsumerRecord<String, String> record : records) {
				messages.add(record.value());
			}
			return messages;
		} finally {
			kafkaConsumer.close();
		}
	}

	public void removeTopic(final String topic) {
		try {
			Set<String> currentSubscriptions = kafkaConsumer.subscription();
			List<String> topicsToSubscribeTo = currentSubscriptions.stream()
					.filter(subscription -> !topic.equals(subscription)).collect(Collectors.toList());
			kafkaConsumer.subscribe(topicsToSubscribeTo);
		} finally {
			kafkaConsumer.close();
		}
	}

	public void subscribeTopic(final String topic) {
		try {
			Set<String> currentSubscriptions = kafkaConsumer.subscription();
			List<String> newSubscription = currentSubscriptions.stream().collect(Collectors.toList());
			newSubscription.add(topic);			
			kafkaConsumer.subscribe(newSubscription);
			kafkaConsumer.commitSync();
		} finally {
			kafkaConsumer.close();
		}
	}

	public List<String> listTopics() {
		try {
			
			Set<String> currentSubscriptions = kafkaConsumer.subscription();
			log.warn("zzz currentSubscriptions: "+currentSubscriptions + kafkaConsumer.listTopics() + kafkaConsumer );
			return currentSubscriptions.stream().collect(Collectors.toList());
		} finally {
			kafkaConsumer.close();
		}

	}

}
