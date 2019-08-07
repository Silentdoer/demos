import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 2019/8/7 14:54
 */
public class ZkClientEntrance {

	public static void main(String[] args) {
		ZkClient client = new ZkClient("localhost:2181", 1000, 1000);
		boolean exists = client.exists("/test/uuu");
		System.out.println(exists);

		// TODO 这里也是持续性的监听/test/uuu的子节点的变更，和Curator不一样的是这里不会将监听前已经存在的节点也作为事件通知
		// 而且这里的事件通知没有Curator那么智能，它只能给出当前所有的子节点，由程序和上一份变更进行比较看发生了什么变更（增，删等）
		client.subscribeChildChanges("/test/uuu", new ZkConcreteServiceChangeListener());

		System.out.println(Thread.currentThread().getId());  // 1
		// 阻塞主线程，它不会影响事件通知，因为不在同一个线程里进行（Socket监听是在其他线程里进行的）；
		LockSupport.park();
	}

	public static class ZkConcreteServiceChangeListener implements IZkChildListener {

		@Override
		public void handleChildChange(String s, List<String> list) throws Exception {
			// 输出Prefix---/test/uuu###[ssmk, sfj]，ssmk,sfj是/test/uuu的子节点，这里只会通知subscribe之后的变更（这个好
			// Curator也可以，用同步初始化即可）
			// 但是这里的不够智能，无法给出哪个节点被删除，哪个节点被增加；
			System.out.println(Thread.currentThread().getId());  // 11
			System.out.println("Prefix---" + s + "###" + list);
		}
	}
}
