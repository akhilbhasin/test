package com.abc.producer;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class ZKProxy {
	
	private ZkClient zkClient;
	 String zookeeperConnect = "localhost:2181";
     int sessionTimeoutMs = 10 * 1000;
     int connectionTimeoutMs = 8 * 1000;
	
	private void init(){
		zkClient = new ZkClient(
	            zookeeperConnect,
	            sessionTimeoutMs,
	            connectionTimeoutMs,
	            ZKStringSerializer$.MODULE$);
	}
	
	public void createTopic(String topic,int partitions, int replication){
		init();
		boolean isSecureKafkaCluster = false;
	    ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperConnect), isSecureKafkaCluster);
	    Properties topicConfig = new Properties(); // add per-topic configurations settings here
	    AdminUtils.createTopic(zkUtils, topic, partitions, replication, topicConfig);
	    zkClient.close();		
	}

}
