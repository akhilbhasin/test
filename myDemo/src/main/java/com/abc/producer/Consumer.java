package com.abc.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;

@Component
public class Consumer {

	private static Logger log = LoggerFactory.getLogger(Consumer.class);
	private String groupId;

	public void scheduleConsumer(final String groupId) {
		this.groupId = groupId;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleWithFixedDelay(new ConsumerCallable(), 0, 5, TimeUnit.SECONDS);

	}

	private class ConsumerCallable implements Runnable {
		private static final String CONSUMER_OFFSET_STRING = "__consumer_offsets";
		private KafkaConsumer<String, String> kafkaConsumer;
		private ZookeeperProxy zk;

		private void init() {
			zk = new ZookeeperProxy();
			try (InputStream props = Resources.getResource("consumer.props").openStream()) {
				Properties properties = new Properties();
				properties.load(props);
				properties.setProperty("group.id", groupId);
				this.kafkaConsumer = new KafkaConsumer<>(properties);

			} catch (IOException e) {
				throw new RuntimeException("unable to create KafkaConsumer");
			}
		}

		@Override
		public void run() {
			init();
			List<String> topics = zk.getListOfTopics();
			topics = topics.stream().filter(topic -> !CONSUMER_OFFSET_STRING.equals(topic))
					.collect(Collectors.toList());
			log.warn("zzzz listOfTopic" + topics);
			try {
				kafkaConsumer.subscribe(topics);
				ConsumerRecords<String, String> records = kafkaConsumer.poll(5000);
				log.warn("zzzz records empty" + records.isEmpty());
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("topic" + record.topic() + "message:" + record.value());

				}
			}catch(Exception e){
				System.out.println(e.getStackTrace());
				log.error("zzzz",e);
			}finally {
				kafkaConsumer.close();
			}

		}
	}

}
