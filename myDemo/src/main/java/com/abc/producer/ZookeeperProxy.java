package com.abc.producer;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperProxy {
	
	private final ZooKeeper zk;
	
	public ZookeeperProxy(){
		try {
			zk = new ZooKeeper("localhost:2181", 10000, null);
		} catch (IOException e) {
			throw new RuntimeException("unable to create zookeeper client",e);
		}
	}
	
	public List<String> getListOfTopics(){
		try {
			return zk.getChildren("/brokers/topics", false);
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
