package com.mljr.spider.dao.impl;

import java.lang.Integer;
import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.mljr.spider.dao.AbstractBasicDao;

import com.mljr.spider.dao.MljrTestDao;
import com.mljr.spider.model.MljrTestDo;
import common.search.util.SearchMap;
import common.page.util.PageQuery;
import common.page.util.PageList;
import common.page.util.Paginator;

/**
 * @author ckex created 2016-11-07 14:00:45:045
 * @explain -
 */
@Repository("mljrTestDao")
public class MljrTestDaoImpl extends AbstractBasicDao implements MljrTestDao {

	@Override
	public MljrTestDo load(Integer ID) {
		SearchMap map = new SearchMap();
		map.add("ID", ID);
		return (MljrTestDo) getSqlSessionTemplate().selectOne("Mapper.mljr_test.load", map);
	}

	@Override
	public boolean delete(Integer ID) {
		SearchMap map = new SearchMap();
		map.add("ID", ID);
		return getSqlSessionTemplate().delete("Mapper.mljr_test.delete", map) > 0;
	}

	@Override
	public MljrTestDo create(MljrTestDo record) {
		getSqlSessionTemplate().insert("Mapper.mljr_test.create", record);
		return record;
	}

	@Override
	public boolean update(MljrTestDo record) {
		return getSqlSessionTemplate().update("Mapper.mljr_test.update", record) > 0;
	}

	@Override
	public PageList<MljrTestDo> listByPage(PageQuery pageQuery, Integer count) {
		SearchMap map = new SearchMap();
		map.add("startIndex", pageQuery.getStartIndex());
		map.add("pageSize", pageQuery.getPageSize());
		if (count == null || count.intValue() == 0) {
			count = (Integer) getSqlSessionTemplate().selectOne("Mapper.mljr_test.listByPageCount", map);
		}
		List<MljrTestDo> list = Collections.emptyList();
		if (count != null && count.intValue() > 0) {
			list = getSqlSessionTemplate().selectList("Mapper.mljr_test.listByPage", map);
		}
		Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
		paginator.setPage(pageQuery.getPageNum());
		PageList<MljrTestDo> result = new PageList<MljrTestDo>(list);
		result.setPaginator(paginator);
		return result;
	}
}
