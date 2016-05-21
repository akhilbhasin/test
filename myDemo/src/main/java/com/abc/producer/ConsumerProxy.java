package com.abc.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;


public class ConsumerProxy {
	
	private static Logger log = LoggerFactory.getLogger(ConsumerProxy.class);
	private final KafkaConsumer<String, String> kafkaConsumer;
	
	public ConsumerProxy(final String groupId) {
		try (InputStream props = Resources.getResource("consumer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            if (properties.getProperty("group.id") == null) {
                properties.setProperty("group.id", groupId);
            }
            this.kafkaConsumer = new KafkaConsumer<>(properties);
        } catch (IOException e) {
        	throw new RuntimeException("unable to create KafkaConsumer for groupId"+groupId);
		}
		
	}
	
	public List<String> getMessages(final String topic){
		kafkaConsumer.subscribe(Arrays.asList(topic));
		log.warn("zzzz topic: "+topic);
		ConsumerRecords<String, String> records = kafkaConsumer.poll(5000);
		log.warn("zzzz records: "+records.toString());
		log.warn("zzzz records: "+records.isEmpty());
		log.warn("zzzz count: "+records.count());
		List<String> messages = new ArrayList<>();
		for (ConsumerRecord<String, String> record : records){
			messages.add(record.value());
		}
		return messages;		
	}

}
