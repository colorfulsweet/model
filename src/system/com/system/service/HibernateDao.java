package com.system.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.system.tags.Page;
import com.system.util.DataCache;
import com.system.util.HibernateUtils;
import com.system.util.ReflectUtils;

/**
 * 公共的增删改查方法
 * @author 41882
 *
 */
@Repository
public class HibernateDao<T, PK extends Serializable> 
			implements IHibernateDao<T, PK>{
	
	@Autowired(required=true)
	private HibernateUtils hibernateUtils;
	
	@Autowired(required=true)
	private DataCache dataCache;
	
	@Override
	public void save(T item) {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			session.save(item);
			session.getTransaction().commit();
			dataCache.cacheData(item);
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		
	}
	@Override
	public void update(T item) {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			session.update(item);
			session.getTransaction().commit();
			dataCache.cacheData(item);
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
	}
	@Override
	public void saveOrUpdate(T item) {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			session.saveOrUpdate(item);
			session.getTransaction().commit();
			dataCache.cacheData(item);
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
	}
	@Override
	public void del(T item) {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			session.delete(item);
			session.getTransaction().commit();
			dataCache.removeObject(item);
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void del(T item,boolean cascade){
		if(!cascade){
			this.del(item);
			return;
		}
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			item = (T) session.get(item.getClass(),
					ReflectUtils.getItemField(item, "id").toString());
			session.delete(item);
			session.getTransaction().commit();
			dataCache.removeObject(item);
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			hibernateUtils.closeSession(session);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> dir(Class<?> cls) {
		Session session = null;
		List<T> result = null;
		try{
			session = hibernateUtils.getSession();
			result = session.createCriteria(cls).list();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void dir(Class<?> cls,Page page){
		Session session = null;
		List<T> result = null;
		int count = 0;
		try{
			session = hibernateUtils.getSession();
			//聚合函数查询记录总数
			Criteria criteria = session.createCriteria(cls)
							.setProjection(Projections.rowCount());
			count = (Integer) criteria.uniqueResult();
			//查询当前页记录内容
			criteria.setProjection(null);
			result = criteria.setFirstResult(page.getRowStart())
					.setMaxResults(page.getPageSize())
					.list();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		page.setRowCount(count);
		page.setResult(result);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T get(Class<?> cls,PK id) {
		Session session = null;
		T item = null;
		try{
			session = hibernateUtils.getSession();
			item = (T) session.get(cls,id);
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return item;
	}
	@Override
	public List<?> excuteQuery(String hql,Object... params){
		Session session = null;
		List<?> result = null;
		try{
			session = hibernateUtils.getSession();
			Query query = session.createQuery(hql);
			if(params != null){
				for(int index=0 ; index<params.length ; index++){
					query.setParameter(index, params[index]);
				}
			}
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return result;
	}
	@Override
	public List<?> excuteSQLQuery(String sql,Object... params){
		Session session = null;
		List<?> result = null;
		try{
			session = hibernateUtils.getSession();
			Query query = session.createSQLQuery(sql);
			if(params != null){
				for(int index=0 ; index<params.length ; index++){
					query.setParameter(index, params[index]);
				}
			}
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return result;
	}
	
	@Override
	public List<?> excuteQueryName(String queryName, Object... params) {
		Session session = null;
		List<?> result = null;
		try{
			session = hibernateUtils.getSession();
			Query query = session.getNamedQuery(queryName);
			if(params != null){
				for(int index=0 ; index<params.length ; index++){
					query.setParameter(index, params[index]);
				}
			}
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return result;
	}
	@Override
	public int excuteUpdate(String hql, Object... params) {
		Session session = null;
		int lines = 0;
		try{
			session = hibernateUtils.getSession();
			Query query = session.createQuery(hql);
			if(params != null){
				for(int index=0 ; index<params.length ; index++){
					query.setParameter(index, params[index]);
				}
			}
			lines = query.executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return lines;
	}
}