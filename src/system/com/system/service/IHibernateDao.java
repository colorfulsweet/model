package com.system.service;

import java.io.Serializable;
import java.util.List;

import com.system.tags.Page;

public interface IHibernateDao <T, PK extends Serializable> {
	/**
	 * 新增(保存)
	 * @param item
	 */
	public void save(T item);
	/**
	 * 更新
	 * @param item
	 */
	public void update(T item);
	/**
	 * 保存或更新
	 * @param item
	 */
	public void saveOrUpdate(T item);
	/**
	 * 删除
	 * @param item
	 */
	public void del(T item);
	/**
	 * 查询全集
	 * @param cls
	 * @return
	 */
	public List<T> dir(Class<?> cls);
	/**
	 * 分页查询
	 * @param cls
	 * @param page
	 * @return
	 */
	public List<T> dir(Class<?> cls,Page page);
	/**
	 * 获取单个实体类
	 * @param id
	 * @return
	 */
	public T get(Class<?> cls,PK id);
	/**
	 * 使用HQL语句执行查询
	 * @param hql HQL查询语句
	 * @param params HQL语句当中的参数值
	 * @return
	 */
	public List<?> excuteQuery(String hql,Object[] params);
	/**
	 * 使用原生SQL语句执行查询
	 * @param sql SQL查询语句
	 * @param params SQL语句当中的参数值
	 * @return
	 */
	public List<?> excuteSQLQuery(String sql,Object[] params);
}
