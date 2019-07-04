package com.wl4g.devops.umc.receiver;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wl4g.devops.common.bean.umc.model.proto.MetricModel;
import com.wl4g.devops.umc.store.MetricStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.wl4g.devops.common.constants.UMCDevOpsConstants.URI_METRIC;

/**
 * HTTP collection receiver
 * 
 * @author wangl.sir
 * @version v1.0 2019年6月17日
 * @since
 */
@ResponseBody
@com.wl4g.devops.umc.annotation.HttpCollectReceiver
public class HttpCollectReceiver extends AbstractCollectReceiver {


	public HttpCollectReceiver(MetricStore store) {
		super(store);
	}


	/**
	 * metrics
	 */
	@RequestMapping(URI_METRIC)
	public void statInfoReceive(@RequestBody byte[] body) {
		try {
			MetricModel.MetricAggregate aggregate = MetricModel.MetricAggregate.parseFrom(body);
			putMetrics(aggregate);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
