/**
 * BaiduMobileFileProcessorTests.java by Ckex.zha
 */
package com.mljr.spider.processor;

import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Ckex.zha <br/>
 *         Nov 17, 20162:03:32 PM
 */
public class BaiduMobileFileProcessorTests {

  private static final String URL = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=%s&rsv_spt=1&oq=%s";

  public BaiduMobileFileProcessorTests() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    String url = String.format(URL, "03178117334", "03178117334");
    Pipeline pipeline = new FilePipeline();
    Spider spider = Spider.create(new BaiduMobileProcessor()).addUrl(url).addPipeline(pipeline);
    spider.addPipeline(new ConsolePipeline());
    TimeUnit.SECONDS.sleep(1);
    spider.start();
  }

}
