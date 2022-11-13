package org.zjl.staff.service;

import org.zjl.staff.entity.Wages;
import java.util.List;

/**
 * (Wages)表服务接口
 * @since 2022-11-12 16:38:24
 */
public interface WagesService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Wages queryById(Integer id);

    /**
     *查询
     * @param wages 筛选条件
     * @return 查询结果
     */
    List<Wages> queryAll(Wages wages);

    /**
     * 新增数据
     *
     * @param wages 实例对象
     * @return 实例对象
     */
    Wages insert(Wages wages);

    /**
     * 修改数据
     *
     * @param wages 实例对象
     * @return 实例对象
     */
    void update(Wages wages);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    void deleteById(Integer id);
    void insertQuick(String settlementTime,String sourceTime);
    void export(Wages wages);

    Wages getByName(String name);
}
