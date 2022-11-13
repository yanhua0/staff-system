package org.zjl.staff.dao;

import org.zjl.staff.entity.Wages;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * (Wages)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-12 16:38:24
 */
public interface WagesDao extends Mapper<Wages>, InsertListMapper<Wages> {

}

