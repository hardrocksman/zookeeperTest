package com.zhl.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class TestZooKeeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host1 = "192.168.1.6:2181";
		ZookeeperUtil zkUtil = new ZookeeperUtil();
		try {
			ZooKeeper zk = zkUtil.connection(host1);
			zkUtil.initQueue(zk);
			zkUtil.produce(zk, 1);
			zkUtil.produce(zk, 2);
			zkUtil.cosume(zk);
			zkUtil.cosume(zk);
			zkUtil.cosume(zk);
	        zk.close();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
