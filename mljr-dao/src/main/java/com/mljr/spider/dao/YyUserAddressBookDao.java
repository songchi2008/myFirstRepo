package com.mljr.spider.dao;

import java.lang.Integer;
import com.mljr.spider.model.YyUserAddressBookDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
public interface YyUserAddressBookDao {

    YyUserAddressBookDo load(Long id);

    boolean delete(Long id);

    YyUserAddressBookDo create(YyUserAddressBookDo record);

    boolean update(YyUserAddressBookDo record);

    PageList<YyUserAddressBookDo>  listByPage(PageQuery pageQuery, Integer count);
}