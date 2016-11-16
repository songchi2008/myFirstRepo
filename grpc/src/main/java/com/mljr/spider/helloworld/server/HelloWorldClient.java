package com.mljr.spider.helloworld.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;
import com.mljr.spider.grpc.*;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class HelloWorldClient {
  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private final ManagedChannel channel;
  private final QueueServerGrpc.QueueServerBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public HelloWorldClient(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
    // needing certificates.
        .usePlaintext(true).build();
    blockingStub = QueueServerGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** Say hello to server. */
  public void greet(String name) {
    // logger.info("Will try to greet " + name + " ...");
    QueueRequest request = QueueRequest.newBuilder().setName(name).build();
    QueueResponse response;
    try {
      response = blockingStub.pullMsg(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Greeting: " + response.getMsg());
  }

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
    final HelloWorldClient client = new HelloWorldClient("localhost", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String user = "world";
      if (args.length > 0) {
        user = args[0]; /* Use the arg as the name to greet if provided */
      }
      int nThreads = 1;
      CountDownLatch latch = new CountDownLatch(nThreads);
      ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
      Stopwatch watch = Stopwatch.createStarted();
      for (int i = 0; i < nThreads; i++) {
        executorService.execute(getRannable(client, user, latch));
      }
      latch.await();
      watch.stop();
      TimeUnit.SECONDS.sleep(1);
      System.out.println("userTime:" + watch.elapsed(TimeUnit.MILLISECONDS));
    } finally {
      client.shutdown();
    }
  }

  private static Runnable getRannable(final HelloWorldClient client, final String user, final CountDownLatch latch) {
    return new Runnable() {

      @Override
      public void run() {
        try {
          for (int i = 0; i < 10; i++) {
            client.greet(user);
          }
        } finally {
          latch.countDown();
        }
      }
    };
  }
}
