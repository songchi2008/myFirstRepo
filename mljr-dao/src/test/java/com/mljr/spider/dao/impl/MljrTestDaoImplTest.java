package com.mljr.spider.dao.impl;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.MljrTestDo;

/**
 * @author ckex created 2016-11-07 14:00:45:045
 * @explain -
 */
public class MljrTestDaoImplTest extends AbstractIMljrDaoTest {

	@Test
	public void testShow() {
		logger.warn(" test " + mljrTestDao.toString());
		MljrTestDo test = new MljrTestDo();
		test.setName("mljr");
		test.setGmtCreate(new Date());
		test.setGmtModified(new Date());
		test = mljrTestDao.create(test);
		logger.warn(" SUCCESS . . . " + test.toString());
	}

	// @Before
	public void testSetup() {
		// MljrTestDo record = new MljrTestDo();
		// record.setID(ID);
		// record.setSellerId(sellerId);
		// record = mljrTestDao.create(record);
	}

	// @After
	public void testTeardown() {
		// mljrTestDao.delete(ID,sellerId);
	}

	// @Test
	public void testLoad() {
		// MljrTestDo load = mljrTestDao.load(ID,sellerId);
		// Assert.assertNotNull(load);
		// Assert.assertEquals("feiyingtest", load.getXxxx());
		// logger.debug(load.toString());
	}

	// @Test
	public void testUpdate() {
		// MljrTestDo record = new MljrTestDo();
		// record.setID(ID);
		// record.setSellerId(sellerId);
		// record.setXxxx("hellofeiying");
		// mljrTestDao.update(record);

		// MljrTestDo load = mljrTestDao.load(ID,sellerId);
		// Assert.assertNotNull(load);
		// Assert.assertEquals("hellofeiying", load.getXxxx());
		// logger.debug(load.toString());
	}

	// @Test
	public void testListByPage() {
		// common.page.util.PageQuery pageQuery = new
		// common.page.util.PageQuery(0,10);
		// Integer count = null;
		// common.page.util.PageList<MljrTestDo> result =
		// mljrTestDao.listByPage(sellerId,pageQuery,count);
		// Assert.assertNotNull(result);
		// for (MljrTestDo bean : result) {
		// logger.debug(bean.toString());
		// }
	}

}
