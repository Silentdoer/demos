package me.silentdoer.kafkausage;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * TODO 所谓Drools（Esper）的功能其实就是将告警规则从代码中解耦，
 * 所谓告警规则其实可以回想一下自己写的智慧用电系统，当三项电流的A项大于什么值且B项大于什么值做 a操作，
 * 当B项电流小于什么值做b操作，当A项电流大于什么值且持续时间达到多少分钟做什么操作；
 * 这些就是规则，以前是硬编码在代码里的；但是如果客户说要改一下持续分钟的数值，或者增加一种情况，那么
 * 就得去改源码，然后又要打包发布；
 * 而用了Drools（规则引擎，Drools Fusion是CEP引擎，即复合事件处理，将多个单一事件组合命中成一个规则处理）后
 * 就可以通过fact（传入的对象，它是三项电流检测器检测到的数据然后由代码组合[可以由专门的微服务进行数据组合格式化，如format服务]
 * 成一个对象，包括A项当前电流值及持续时间等等）
 * 然后drools将fact insert到处理器里，处理器去匹配rules，
 * 匹配出rules后按优先级进行执行，比如设置fact中result的值为email,sms，以及内容；然后接下来的代码
 * 就是根据result判断需要进行什么行为【发送短信和邮件，及邮件的内容】
 *
 * @author wlq
 * @version 1.0.0
 * @since 19-9-22 下午4:49
 */
public class ProducerEntrance {

	public static void main(String[] args) {
		Properties properties = new Properties();
		//zookeeper服务器集群地址，用逗号隔开
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		//自定义producer拦截器
		//properties.put("interceptor.classes", "com.lt.kafka.producer.MyProducerInterceptor");
		//自定义消息路由规则（消息发送到哪一个Partition中）
		//properties.put("partitioner.class", "com.lt.kafka.producer.MyPartition");

		Producer<String, String> producer = null;
		try {
			producer = new KafkaProducer<String, String>(properties);
			for (int i = 40; i < 50; i++) {
				String msg = "This is Message:" + i;

				/*
				 * kafkaproducer中会同时调用自己的callback的onCompletion方法和producerIntercepter的onAcknowledgement方法。
				 * 关键源码：Callback interceptCallback = this.interceptors == null
				 * callback : new InterceptorCallback<>(callback,
				 * this.interceptors, tp);
				 */
				// 这里能够自动创建Topic，这里可以指定key也可以不指定(RocketMQ貌似是一定会生成key的，但是kafka则不一定，consumer可能获取到的key是null）
				producer.send(new ProducerRecord<String, String>("TOPIC1", msg), new Callback() {
					@Override
					public void onCompletion(RecordMetadata recordMetadata, Exception e) {
						// 输出的是发送的数据在kafka里的元数据，比如存储在哪个Topic里，哪个partition，哪个offset；
						System.out.println(recordMetadata.toString());
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (producer != null)
				producer.close();
		}
	}
}