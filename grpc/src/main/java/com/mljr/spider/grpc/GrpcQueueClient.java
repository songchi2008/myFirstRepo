/**
 * GrpcQueueClient.java by Ckex.zha
 */
package com.mljr.spider.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ckex.zha <br/>
 *         Nov 16, 20169:43:38 AM
 */
public class GrpcQueueClient {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  private final ManagedChannel channel;
  private final QueueServerGrpc.QueueServerBlockingStub blockingStub;


  public GrpcQueueClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  GrpcQueueClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = QueueServerGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public String pullMsg(String queueId) {
    QueueRequest request = QueueRequest.newBuilder().setName(queueId).build();
    return blockingStub.pullMsg(request).getMsg();
  }

}
