/**
 * Main.java by Ckex.zha
 */
package com.ckex.spider.webmagic.main;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import com.ckex.spider.webmagic.processor.OschinaBlog;
import com.ckex.spider.webmagic.processor.OschinaBlogPageProcesser;

/**
 * @author Ckex.zha <br/>
 *         Oct 31, 201612:06:11 PM
 */
public class Main {

  public Main() {
    super();
  }

  public static void main(String[] args) {
    Spider.create(new OschinaBlogPageProcesser()).addUrl("http://my.oschina.net/flashsword/blog").addPipeline(new ConsolePipeline()).run();
    // OOSpider.create(Site.me(), new ConsolePageModelPipeline(),
    // OschinaBlog.class).addUrl("http://my.oschina.net/flashsword/blog").run();
  }

}
