package com.wl4g.devops.umc.receiver;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wl4g.devops.common.bean.umc.model.proto.MetricModel;
import com.wl4g.devops.common.bean.umc.model.proto.MetricModel.MetricAggregate;
import com.wl4g.devops.umc.store.MetricStore;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

import static com.wl4g.devops.common.constants.UMCDevOpsConstants.TOPIC_RECEIVE_PATTERN;
import static com.wl4g.devops.umc.config.UmcReceiveAutoConfiguration.BEAN_KAFKA_BATCH_FACTORY;

/**
 * KAFKA collection receiver
 *
 * @author wangl.sir
 * @version v1.0 2019年6月17日
 * @since
 */
public class KafkaCollectReceiver extends AbstractCollectReceiver {

	public KafkaCollectReceiver(MetricStore store) {
		super(store);
	}

	/**
	 * Receiving consumer messages on multiple topics
	 *
	 * @param records
	 * @param ack
	 */
	@KafkaListener(topicPattern = TOPIC_RECEIVE_PATTERN, containerFactory = BEAN_KAFKA_BATCH_FACTORY)
	public void onMultiReceive(List<ConsumerRecord<byte[], Bytes>> records, Acknowledgment ack) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Consumer records for - {}", records);
			}
			// Process
			doProcess(records, new MultiAcknowledgmentState(ack));
		} catch (Exception e) {
			log.error("", e);
		} finally {
			// Echo
			// ack.acknowledge();
		}
	}

	/**
	 * UMC agent metric processing.
	 *
	 * @param records
	 * @param state
	 */
	private void doProcess(List<ConsumerRecord<byte[], Bytes>> records, MultiAcknowledgmentState state) {
		//
		// TODO
		//
		for (ConsumerRecord<byte[], Bytes> consumerRecord : records) {
			log.info("listen kafka message" + consumerRecord.value());
			Bytes value = consumerRecord.value();

			try {
				MetricAggregate aggregate = MetricModel.MetricAggregate.parseFrom(value.get());
				System.out.println(aggregate.getMetricsList().toString());
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}

			// switch (key) {
			// case URI_PHYSICAL:
			// PhysicalStatInfo physical = JacksonUtils.parseJSON(value,
			// PhysicalStatInfo.class);
			// putPhysical(physical);
			// break;
			// case URI_VIRTUAL_DOCKER:
			// Docker docker = JacksonUtils.parseJSON(value, Docker.class);
			// putVirtualDocker(docker);
			// break;
			// case URI_REDIS:
			// RedisStatInfo redis = JacksonUtils.parseJSON(value,
			// RedisStatInfo.class);
			// putRedis(redis);
			// break;
			// case URI_ZOOKEEPER:
			// ZookeeperStatInfo zookeeper = JacksonUtils.parseJSON(value,
			// ZookeeperStatInfo.class);
			// putZookeeper(zookeeper);
			// break;
			// case URI_KAFKA:
			// KafkaStatInfo kafka = JacksonUtils.parseJSON(value,
			// KafkaStatInfo.class);
			// putKafka(kafka);
			// break;
			// default:
			// throw new UnsupportedOperationException("unsupport this type");
			// }
//			switch (key) {
//			case URI_PHYSICAL:
//				PhysicalStatInfo physical = JacksonUtils.parseJSON(value, PhysicalStatInfo.class);
//				putPhysical(physical);
//				break;
//			case URI_VIRTUAL_DOCKER:
//				Docker docker = JacksonUtils.parseJSON(value, Docker.class);
//				putVirtualDocker(docker);
//				break;
//			case URI_REDIS:
//				RedisStatInfo redis = JacksonUtils.parseJSON(value, RedisStatInfo.class);
//				putRedis(redis);
//				break;
//			case URI_ZOOKEEPER:
//				ZookeeperStatInfo zookeeper = JacksonUtils.parseJSON(value, ZookeeperStatInfo.class);
//				putZookeeper(zookeeper);
//				break;
//			case URI_KAFKA:
//				KafkaStatInfo kafka = JacksonUtils.parseJSON(value, KafkaStatInfo.class);
//				putKafka(kafka);
//				break;
//			default:
//				throw new UnsupportedOperationException("unsupport this type");
//			}
		}
		state.completed();
	}

	/**
	 * Multiple ACK completion state
	 *
	 * @author wangl.sir
	 * @version v1.0 2019年6月18日
	 * @since
	 */
	public static class MultiAcknowledgmentState {

		final private Acknowledgment ack;

		public MultiAcknowledgmentState(Acknowledgment ack) {
			super();
			this.ack = ack;
		}

		public void completed() {
			ack.acknowledge();
		}

	}

}
