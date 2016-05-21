package com.abc.producer;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class ZKProxy {
	
	private ZkClient zkClient;
	 String zookeeperConnect = "localhost:2181";
     int sessionTimeoutMs = 10 * 1000;
     int connectionTimeoutMs = 8 * 1000;
	
	public ZKProxy(){
		ZkClient zkClient = new ZkClient(
	            zookeeperConnect,
	            sessionTimeoutMs,
	            connectionTimeoutMs,
	            ZKStringSerializer$.MODULE$);
	}
	
	public void createTopic(String topic,int partitions, int replication){
		boolean isSecureKafkaCluster = false;
	    ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperConnect), isSecureKafkaCluster);
	    
		
	}

}
