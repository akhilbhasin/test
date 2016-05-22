package com.abc.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;
@Component
public class ProducerProxy {
	private static final Logger log = LoggerFactory.getLogger(ProducerProxy.class);
	private final KafkaProducer<String, String> kafkaProducer;

	public ProducerProxy(final KafkaProducer<String, String> producer) {
		this.kafkaProducer = producer;
	}
	
	public ProducerProxy(){
		try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            this.kafkaProducer = new KafkaProducer<>(properties);
		} catch (IOException e) {
			throw new RuntimeException("unable to create KafkaProducer",e);		
		}
	}

	public void sendMessage(final String topic, final String message) {
		try {
			ProducerRecord<String, String> producerRecordRequest = new ProducerRecord<String, String>(topic, message);
			Future<RecordMetadata> record = kafkaProducer.send(producerRecordRequest);
			log.warn("record has been added:"+record.get());
		} catch (Exception e) {
			throw new RuntimeException("unable to post to topic:" + topic + " , message:" + message);
		} finally {
			//kafkaProducer.close();
		}

	}

}
