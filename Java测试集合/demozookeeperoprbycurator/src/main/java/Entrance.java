import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liqi.wang
 * @since 2019/8/4 21:13
 */
public class Entrance {

	public static void main(String[] args) {

		CuratorFramework client = null;
		try {
			// 如果是集群，可以用英文逗号隔开添加多个address
			client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
					// 连接超时时间
					.connectionTimeoutMs(1000)
					// 会话超时时间
					.sessionTimeoutMs(1000)
					// 刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次(1000, 3)
					.retryPolicy(new ExponentialBackoffRetry(200, 1))
					.build();
			// start后client就可以使用了（已经建立tcp连接）
			client.start();

			// 流式写法（Stream操作），以前的做法一般是checkExists("path")来检测path这个节点是否存在
			// 但这种写法个人觉得未必就很好，因为它很多操作都需要最后加个forPath，所以所有操作的返回值类型必须一样
			// 但是实际上很多操作它们的返回的数据是大不相同的，这里的实现方式是不同的操作返回的类型都有forPath方法，
			// 但是对于不同操作返回类型的forPath方法的返回类型则又和其操作有关，比如checkExists()返回的类型是A，A的forPath返回类型是Stat
			// 而getChildren()返回的类型是B，B的forPath返回类型则是List<String>
			// /根节点是一定存在的
			Stat stat1 = client.checkExists().forPath("/");
			// 如果没有这个节点，那么stat1是null
			System.out.println(stat1.getClass());
			// acl version，这里获取的是此节点的一些元数据
			System.out.println(stat1.getAversion());

			// 更新节点数据
			/*client.setData()
					.withVersion(2) // 乐观锁，即有人读取该节点直接去更新，有人写则等待？
					.forPath("/search/business/test","0".getBytes());*/

			// Stat就是对znode元数据的一个映射，stat=null表示节点不存在
			/*Stat stat = new Stat();
			// 获取该节点的value值，同时将元数据保存到stat里
			byte[] re = client.getData().storingStatIn(stat).forPath("/search/business/test");*/

			// 删除节点
			/*client.delete()
					.guaranteed() // 保障机制，若未删除成功，只要会话有效会在后台一直尝试删除
					.deletingChildrenIfNeeded() // 若当前节点包含子节点，子节点也删除
					.withVersion(2) // 指定版本号
					.forPath("/search/business/test");*/

			// 如果创建的节点（叶子）的祖先节点不存在，则会先创建祖先节点，祖先节点默认是PERSISTENT类型（持久节点）
			// 如果节点重复创建会抛异常（这里创建的祖先节点是会先检测是否存在，不存在才创建）
			/*client.create().creatingParentsIfNeeded().forPath("/test/uuu/msf");*/

			// 创建节点
			/*client.create().creatingParentsIfNeeded() // 若创建节点的父节点不存在则先创建父节点再创建子节点
					.withMode(CreateMode.PERSISTENT) // 创建的是持久节点
					.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // 默认匿名权限,权限scheme id:'world,'anyone,:cdrwa
					.forPath("/testmm","1".getBytes());*/

			// 这种创建方式如果没有祖先节点则不会默认创建而是抛异常
			// 注意，默认创建的就是CreateMode.PERSISTENT，ZooDefs.Ids.OPEN_ACL_UNSAFE的节点
			// 不指定value的话默认是用ip作为value【只是curator是这种逻辑，其他工具包未必】（除非是默认创建的祖先节点则value是空）
			//client.create().forPath("/kkks");

			// CreateMode可能不同，但是ACL一般都是OPEN_ACL_UNSAFE）
			// 注意，如果后面没有接forPath是不会执行任何操作的，也不会报错
			// TODO 注意，这里所有的create返回的都是String，对于创建节点的zk中的绝对路径
			// TODO EPHEMERAL短暂的节点和持久的节点不同的点在于Node Metadata里的ephemeral owner的值大于0，持久节点的该值是0
			//client.create().withMode(CreateMode.EPHEMERAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/mswt8");
			// EPHEMERAL是临时节点，程序一关闭该节点就可能立刻被清除，所以这里read()一下；
			//System.in.read();

			// 创建自动加排序号后缀的子节点（即uust0000000000/uust0000000001这样的节点，指定的叶子节点名会加上十位十进制数值）
			//client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/kkks/uust");
			//System.in.read();

			// 这里得到的只是一级children，不会递归获取所有的子孙节点
			// TODO 这里的ss[0]只是一级孩子节点的名字，不包括/
			List<String> ss = client.getChildren().forPath("/");
			System.out.println(ss);

			// 监听父节点以下所有的子节点, 当子节点发生变化的时候(增删改)都会监听到
			// 为子节点添加watcher事件，第三个参数是是否缓存Data；
			// PathChildrenCache监听数据节点的增删改，nginxplus只需监听增删即可
			// TODO 这些是在Recipes包里的，curator-framework包里没有
			final PathChildrenCache childrenCache = new PathChildrenCache(client, "/test/uuu", true);
			// TODO NORMAL:异步初始化但不产生INITIALIZED事件（但产生了添加子节点的事件【但这些子节点是监听前已经存在了】）
			//  TODO , BUILD_INITIAL_CACHE:同步初始化（不产生初始化事件和监听前的节点的的添加事件）
			//   TODO , POST_INITIALIZED_EVENT:异步初始化且初始化之后会触发初始化事件和监听前已经存在子节点的添加事件
			childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
			// 这名字有点坑爹，用getCurrentChildrenData不是更好吗？，它的操作本质上还是通过之前的client实现的；
			List<ChildData> childDataList = childrenCache.getCurrentData(); // 当前数据节点的子节点数据列表
			childrenCache.getListenable().addListener(new PathChildrenCacheListener() {

				/*
				修改子节点, path:/test/uuu/eees, data:kkk
				添加子节点, path:/test/uuu/eskut, data:
				删除子节点, path:/test/uuu/ssmk, data:ss
				 */
				@Override
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
					if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
						// 由于用的是POST_INITIALIZED_EVENT，所以会触发这个事件
						System.out.println("PathChildrenCache初始化完毕");
					} else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
						// TODO 注意，如果PathChildrenCache初始化之前就已经有子节点存在，那么也是会触发添加子节点的事件的
						// event.getData()获取的不是value值而是Node的一个数据封装对象
						System.out.printf("添加子节点, path:%s, data:%s\n", event.getData().getPath(), new String(event.getData().getData(), StandardCharsets.UTF_8));
					} else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
						System.out.printf("删除子节点, path:%s, data:%s\n", event.getData().getPath(), new String(event.getData().getData(), StandardCharsets.UTF_8));
					} else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
						// TODO 经过测试这里的修改不包括给子节点添加子子节点或删除子子节点，但是包括修改Value值
						System.out.printf("修改子节点, path:%s, data:%s\n", event.getData().getPath(), new String(event.getData().getData(), StandardCharsets.UTF_8));
					}
				}
			});

			LockSupport.park();

			// Curator还支持事物操作，即一组操作要么都成功，要么都失败
			// 开启事务，这个已经过时了，新的貌似是用client.transaction()来操作，有需要的时候再看
			/*CuratorTransaction transaction = client.inTransaction();
			Collection<CuratorTransactionResult> results = transaction.create().forPath("/curator/1", "0".getBytes())
					.and().setData().forPath("/curator/2", "-1".getBytes())
					.and().delete().forPath("/curator/3")
					.and().commit();*/

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(client)) {
				CloseableUtils.closeQuietly(client);
			}
		}
	}
}
