package com.mljr.spider.dao;

import java.lang.Integer;
import com.mljr.spider.model.MljrTestDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * @author ckex created 2016-11-07 14:00:45:045
 * @explain -
 */
public interface MljrTestDao {

    MljrTestDo load(Integer ID);

    boolean delete(Integer ID);

    MljrTestDo create(MljrTestDo record);

    boolean update(MljrTestDo record);

    PageList<MljrTestDo>  listByPage(PageQuery pageQuery, Integer count);
}