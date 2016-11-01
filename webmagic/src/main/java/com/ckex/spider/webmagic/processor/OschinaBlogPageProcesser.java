/**
 * OschinaBlogPageProcesser.java by Ckex.zha
 */
package com.ckex.spider.webmagic.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Ckex.zha <br/>
 *         Oct 31, 201612:08:13 PM
 */
public class OschinaBlogPageProcesser implements PageProcessor {

  private Site site = Site.me().setDomain("my.oschina.net").setRetryTimes(10).setSleepTime(60);

  public OschinaBlogPageProcesser() {
    super();
  }

  @Override
  public Site getSite() {
    return site;
  }

  @Override
  public void process(Page page) {
    List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
    page.addTargetRequests(links);
    page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1").toString());
    page.putField("content", page.getHtml().$("div.content").toString());
    page.putField("tags", page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
  }

}
