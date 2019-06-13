package com.wl4g.devops.umc.store.opentsdb.client.http.callback;

import com.wl4g.devops.umc.store.opentsdb.client.bean.request.Point;
import com.wl4g.devops.umc.store.opentsdb.client.bean.response.DetailResult;
import com.wl4g.devops.umc.store.opentsdb.client.common.Json;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 异步写入回调
 *
 * @Author: jinyao
 * @Description:
 * @CreateDate: 2019/2/23 下午9:58
 * @Version: 1.0
 */
public class BatchPutHttpResponseCallback implements FutureCallback<HttpResponse> {

    final private Logger log = LoggerFactory.getLogger(getClass());

    private BatchPutCallBack callBack;

    private List<Point> points;

    public BatchPutHttpResponseCallback() {
    }

    public BatchPutHttpResponseCallback(BatchPutCallBack callBack, List<Point> points) {
        this.callBack = callBack;
        this.points = points;
    }

    @Override
    public void completed(HttpResponse response) {
        if (callBack != null) {
            // 无论成功失败，response body的格式始终是DetailResult的形式
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    String content = EntityUtils.toString(entity, Charset.defaultCharset());
                    DetailResult detailResult = Json.readValue(content, DetailResult.class);
                    if (detailResult.getFailed() == 0) {
                        log.debug("批量添加错误数量为0，全部成功");
                        this.callBack.response(points, detailResult);
                    } else {
                        log.debug("批量添加出现错误，错误个数:{}", detailResult.getFailed());
                        this.callBack.responseError(points, detailResult);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void failed(Exception e) {
        if (callBack != null) {
            log.error("批量添加请求失败,error:{}", e.getMessage());
            this.callBack.failed(points, e);
        }
    }

    @Override
    public void cancelled() {

    }

    public interface BatchPutCallBack {

        /***
         * 在请求完成并且response code成功时回调
         * @param points 数据点
         * @param result 请求结果
         */
        void response(List<Point> points, DetailResult result);

        /***
         * 在response code失败时回调
         * @param points 数据点
         * @param result 请求结果
         */
        void responseError(List<Point> points, DetailResult result);

        /***
         * 在发生错误是回调
         * @param points 数据点
         * @param e 异常
         */
        void failed(List<Point> points, Exception e);

    }

}
