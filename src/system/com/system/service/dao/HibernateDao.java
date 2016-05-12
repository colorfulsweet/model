package com.system.service.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.util.StringHelper;
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
	private static Logger log = Logger.getLogger(HibernateDao.class);
	
	@Autowired(required=true)
	private DataCache dataCache;
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
		if(criteria != null && !criteria.isEmpty()){
			criteriaHandle(cls, cta, criteria);
		}
		List<T> result = cta.list();
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void dir(Class<?> cls, Page page, Map<String,Object> criteria){
		//创建查询
		Criteria cta = this.getSessionFactory().getCurrentSession().createCriteria(cls);
		if(criteria != null && !criteria.isEmpty()){
			criteriaHandle(cls, cta, criteria);
		}
		//聚合函数查询记录总数
		cta.setProjection(Projections.rowCount());
		long count = (Long) cta.uniqueResult();
		//查询当前页记录内容
		cta.setProjection(null);
		List<T> result = cta.setFirstResult(page.getRowStart())
				.setMaxResults(page.getPageSize())
				.list();
		page.setRowCount(count);
		page.setResult(result);
	}
	/**
	 * 将查询条件添加到Criteria当中
	 * @param cls 表示实体类的Class对象
	 * @param cta 查询基准
	 * @param criteria 查询条件的字段名与字段值的Map集合
	 */
	private void criteriaHandle(Class<?> cls, Criteria cta, Map<String,Object> criteria){
		//获取实体类当中所有的属性列表
		Field[] fields = cls.getDeclaredFields();
		Map<String,String> fieldMap = new HashMap<String,String>();
		for(Field field : fields) {
			fieldMap.put(field.getName(), field.getType().getName());
		}
		//获取查询条件的列表
		Set<Entry<String, Object>> entries = criteria.entrySet();
		for(Entry<String, Object> entry : entries){
			//将查询条件加入到Criteria
			Criterion criterion = null;
			String key = entry.getKey();
			String value = (String) entry.getValue();
			if(StringHelper.isEmpty(value)){
				continue;
			}
			String fieldName = null;
			//针对日期时间数据采集字段名称
			byte dateFlag = 0;
			if(key.contains("Start")){
				fieldName = key.substring(0, key.indexOf("Start"));
				dateFlag = -1;
			} else if(key.contains("End")){
				fieldName = key.substring(0, key.indexOf("End"));
				dateFlag = 1;
			} else {
				fieldName = key;
			}
			if(!fieldMap.containsKey(fieldName)){
				continue;
			}
			try {
				switch(fieldMap.get(fieldName)){
				case "java.lang.String" : 
					//对于字符串类型, 使用模糊查询
					criterion = Restrictions.ilike(fieldName, "%"+value+"%");
					break;
				case "java.sql.Timestamp" :
				case "java.util.Date" :
					//对于日期时间类型的, 使用起止时间进行查询
					if(dateFlag > 0){
						criterion = Restrictions.lt(fieldName, dateFormat.parse(value));
					} else if(dateFlag < 0){
						criterion = Restrictions.gt(fieldName, dateFormat.parse(value));
					}
					break;
				case "java.lang.Boolean" :
					criterion = Restrictions.eq(fieldName, Boolean.parseBoolean(value));
					break;
				default : log.warn("未知的数据类型!--" + fieldMap.get(fieldName));
				}
				if(criterion != null){
					cta.add(criterion);
				}
			} catch (ParseException e) {
				log.error("解析查询条件出错!", e);
			}
		}
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
	public int excuteUpdate(String hql, String[] paramNames, Collection<?>... params) {
		if(paramNames.length != params.length){
			throw new IllegalArgumentException("参数名称数量与参数值的数量不同");
		}
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		for(short index=0 ; index<paramNames.length ; index++){
			query.setParameterList(paramNames[index], params[index]);
		}
		int lines = query.executeUpdate();
		return lines;
	}
}