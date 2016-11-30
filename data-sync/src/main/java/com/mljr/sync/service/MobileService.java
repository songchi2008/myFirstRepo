/**
 * 
 */
package com.mljr.sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mljr.spider.dao.YyUserAddressBookDao;
import com.mljr.spider.model.YyUserAddressBookDo;
import com.sun.tools.javac.util.List;
import com.mljr.redis.RedisClient;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,下午4:08:29
 *
 */
@Service
public class MobileService {

	@Autowired
	private YyUserAddressBookDao yyUserAddressBookDao;

	private List<YyUserAddressBookDo> listYyUserAddressBook() {
		RedisClient client;
		return null;
	}
}
