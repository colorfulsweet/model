package com.system.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.system.tags.Page;
import com.system.util.DataCache;
import com.system.util.ReflectUtils;

/**
 * 公共的增删改查方法
 * @author 41882
 *
 */
@Repository
public class HibernateDao<T, PK extends Serializable> extends HibernateDaoSupport
			implements IHibernateDao<T, PK>{
	
	@Autowired(required=true)
	private DataCache dataCache;
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void save(T item) {
		this.getSession().save(item);
		dataCache.cacheData(item);
	}
	@Override
	public void update(T item) {
		this.getSession().update(item);
		dataCache.cacheData(item);
	}
	@Override
	public void saveOrUpdate(T item) {
		this.getSession().saveOrUpdate(item);
		dataCache.cacheData(item);
	}
	@Override
	public void del(T item) {
		this.getSession().delete(item);
		dataCache.removeObject(item);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void del(T item,boolean cascade){
		if(!cascade){
			this.del(item);
			return;
		}
		Session session = this.getSession();
		item = (T) session.get(item.getClass(),
				ReflectUtils.getItemField(item, "id").toString());
		session.delete(item);
		dataCache.removeObject(item);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> dir(Class<?> cls) {
		List<T> result = this.getSession().createCriteria(cls).list();
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void dir(Class<?> cls,Page page){
		//聚合函数查询记录总数
		Criteria criteria = this.getSession().createCriteria(cls)
						.setProjection(Projections.rowCount());
		int count = (Integer) criteria.uniqueResult();
		//查询当前页记录内容
		criteria.setProjection(null);
		List<T> result = criteria.setFirstResult(page.getRowStart())
				.setMaxResults(page.getPageSize())
				.list();
		page.setRowCount(count);
		page.setResult(result);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T get(Class<?> cls,PK id) {
		//首先从缓存区进行查找
		T item = (T) dataCache.getObject(cls, id.toString());
		if(item != null){
			return item;
		}
		//如果缓存区当中未找到,则进行数据库查询
		item = (T) this.getSession().get(cls,id);
		dataCache.cacheData(item);
		return item;
	}
	@Override
	public List<?> excuteQuery(String hql,Object... params){
		Query query = this.getSession().createQuery(hql);
		if(params != null){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		List<?> result = query.list();
		return result;
	}
	@Override
	public List<?> excuteSQLQuery(String sql,Object... params){
		Query query = this.getSession().createSQLQuery(sql);
		if(params != null){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		List<?> result = query.list();
		return result;
	}
	
	@Override
	public List<?> excuteQueryName(String queryName, Object... params) {
		Query query = this.getSession().getNamedQuery(queryName);
		if(params != null){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		List<?> result = query.list();
		return result;
	}
	@Override
	public int excuteUpdate(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		if(params != null){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		int lines = query.executeUpdate();
		return lines;
	}
}