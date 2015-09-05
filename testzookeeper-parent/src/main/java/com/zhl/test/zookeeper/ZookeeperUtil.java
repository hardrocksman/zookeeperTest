package com.zhl.test.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperUtil {
	
	/**
	 * 创建连接
	 * @param host
	 * @return
	 * @throws IOException
	 */
	public static ZooKeeper connection(String host) throws IOException {
        //ZooKeeper zk = new ZooKeeper(host, 60000, null);
        //return zk;
		
		return new ZooKeeper(host, 60000, new Watcher() {
            public void process(WatchedEvent event) {
            }
        });
    }
	
	public static void initQueue(ZooKeeper zk) throws KeeperException, InterruptedException {
        if (zk.exists("/queue-fifo", false) == null) {
            System.out.println("create /queue-fifo task-queue-fifo");
            zk.create("/queue-fifo", "task-queue-fifo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            System.out.println("/queue-fifo is exist!");
        }
    }
	
	/**
	 * 生产者
	 * @param zk
	 * @param x
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public static void produce(ZooKeeper zk, int x) throws KeeperException, InterruptedException {
        System.out.println("create /queue-fifo/x" + x + " x" + x);
        zk.create("/queue-fifo/x" + x, ("x" + x).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	
	/**
	 * 消费者
	 * @param zk
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	 public static void cosume(ZooKeeper zk) throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren("/queue-fifo", true);
        if (list.size() > 0) {
            long min = Long.MAX_VALUE;
            for (String num : list) {
                if (min > Long.parseLong(num.substring(1))) {
                    min = Long.parseLong(num.substring(1));
                }
            }
            System.out.println("delete /queue/x" + min);
            zk.delete("/queue-fifo/x" + min, 0);
        } else {
            System.out.println("No node to cosume");
        }
	 }
}
