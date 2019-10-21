package me.silentdoer.kafkausage;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-9-22 下午5:53
 */
public class ConsumerEntrance {

	public static void main(String[] args) {
		Properties props = new Properties();
		//props.put("bootstrap.servers", "172.16.0.218:9092,172.16.0.219:9092,172.16.0.217:9092");
		props.put("bootstrap.servers", "localhost:9092");
		// 也会自动创建group？？【貌似是的】，TODO 为consumer设置分组
		props.put("group.id", "managers");
		// 消费了消息自动提交消费成功的ack
		props.put("enable.auto.commit", "true");
		//想要读取之前的数据，必须加上【貌似没用】
		//props.put("auto.offset.reset", "earliest");
		/* 自动确认offset的时间间隔 TODO 能够自动调整offset从而读取在subscribe之前产生的数据 */
		// TODO 这里的逻辑应该是服务端会保存Message被哪些group消费了，当consumer请求kafka服务调整offset时
		// TODO kafka会根据记录找出那些没有被此group消费的消息的offset【或者是没有被任何group消费的】返回给consumer
		props.put("auto.commit.interval.ms", "1000");
		/*
		 * 一旦consumer和kakfa集群建立连接，
		 * consumer会以心跳的方式来高速集群自己还活着，
		 * 如果session.timeout.ms 内心跳未到达服务器，服务器认为心跳丢失，会做rebalence
		 */
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//配置自定义的拦截器，可以在拦截器中引入第三方插件实现日志记录等功能。
		//props.put("interceptor.classes", "com.lt.kafka.consumer.MyConsumerInterceptor");

		@SuppressWarnings("resource")
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		try {
			/* 消费者订阅的topic, 可同时订阅多个 */
			consumer.subscribe(Arrays.asList("TOPIC1"));
			// 由于是poll的形式，所以需要自己去轮询【这样做是可以根据consumer机器的性能进行一定的轮询速度控制】
			while (true) {
				//轮询数据。如果缓冲区中没有数据，轮询等待的时间为毫秒。如果0，立即返回缓冲区中可用的任何记录，则返回空
				// 100毫秒内轮询不到数据则返回空数组
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.partition() + record.topic());
					System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
									  record.value());
					consumer.commitAsync();
					// 如果没有开启自动提交enable.auto.commit需要手动提交，可以同步或异步
					//consumer.commitSync();
					/*Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
					offsets.put(new TopicPartition(record.topic(), record.partition())
							, new OffsetAndMetadata(record.offset() + 1, "not meteData"));
					// 回调函数可以为null，就是提交消费确认给kafka服务后，kafka服务会通知consumer说：我知道你已经消费了某些Message
					// 这里可以一次性提交多条消息的消费，这里是提交的一条
					consumer.commitAsync(offsets, new OffsetCommitCallback() {
						@Override
						public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
							if (Objects.nonNull(exception)) {
								System.out.println("kafka服务确定某条消息消费成功产生异常");
							}
							System.out.println("服务端已经知道消费了某些消息，即Topic-partition-offset共同唯一确定一条消息");
						}
					});*/
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			consumer.close();
		}
	}
}