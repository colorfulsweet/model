package com.system.service.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.system.tags.Page;
import com.system.util.DataCache;
import com.system.util.ReflectUtils;

/**
 * 公共的增删改查方法
 * @author 41882
 *
 */
@Transactional
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
		this.getSessionFactory().getCurrentSession().save(item);
		dataCache.cacheData(item);
	}
	@Override
	public void update(T item) {
		this.getSessionFactory().getCurrentSession().update(item);
		dataCache.cacheData(item);
	}
	@Override
	public void saveOrUpdate(T item) {
		this.getSessionFactory().getCurrentSession().saveOrUpdate(item);
		dataCache.cacheData(item);
	}
	@Override
	public void del(T item) {
		this.getSessionFactory().getCurrentSession().delete(item);
		dataCache.removeObject(item);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void del(T item,boolean cascade){
		if(!cascade){
			this.del(item);
			return;
		}
		Session session = this.getSessionFactory().getCurrentSession();
		item = (T) session.get(item.getClass(),
				ReflectUtils.getItemField(item, "id").toString());
		session.delete(item);
		dataCache.removeObject(item);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> dir(Class<?> cls, Map<String,Object> criteria) {
		Criteria cta = this.getSessionFactory().getCurrentSession().createCriteria(cls);
		Set<Entry<String, Object>> entries = criteria.entrySet();
		if(criteria != null && !criteria.isEmpty()){
			for(Entry<String, Object> entry : entries){
				//将查询条件加入到Criteria
				if(entry.getValue() instanceof String){
					Criterion criterion = Restrictions.ilike(entry.getKey(), '%'+(String)entry.getValue()+'%');
					cta.add(criterion);
				}
			}
		}
		List<T> result = cta.list();
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void dir(Class<?> cls, Page page, Map<String,Object> criteria){
		//聚合函数查询记录总数
		Criteria cta = this.getSessionFactory().getCurrentSession().createCriteria(cls)
						.setProjection(Projections.rowCount());
		//获取查询条件的列表
		Set<Entry<String, Object>> entries = criteria.entrySet();
		if(criteria != null && !criteria.isEmpty()){
			for(Entry<String, Object> entry : entries){
				//将查询条件加入到Criteria
				Criterion criterion = null;
				if(entry.getValue() instanceof String){
					criterion = Restrictions.ilike(entry.getKey(), '%'+(String)entry.getValue()+'%');
					cta.add(criterion);
				}
			}
		}
		long count = (Long) cta.uniqueResult();
		//查询当前页记录内容
		cta.setProjection(null);
		List<T> result = cta.setFirstResult(page.getRowStart())
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
		item = (T) this.getSessionFactory().getCurrentSession().get(cls,id);
		dataCache.cacheData(item);
		return item;
	}
	@Override
	public List<?> excuteQuery(String hql,Object... params){
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
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
		Query query = this.getSessionFactory().getCurrentSession().getNamedQuery(queryName);
		if(params!=null && params.length!=0){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		List<?> result = query.list();
		return result;
	}
	@Override
	public int excuteUpdate(String hql, Object... params) {
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		if(params != null){
			for(int index=0 ; index<params.length ; index++){
				query.setParameter(index, params[index]);
			}
		}
		int lines = query.executeUpdate();
		return lines;
	}

	@Override
	public int excuteUpdate(String hql, String paramName, List<?> params) {
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameterList(paramName, params);
		int lines = query.executeUpdate();
		return lines;
	}
}