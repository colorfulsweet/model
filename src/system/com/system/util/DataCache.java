package com.system.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.system.service.IHibernateDao;
/**
 * 数据库数据的高速缓存器
 * 使用软引用构成的集合实现
 * 在根据执行查询的时候,就把查询到的对象放入到缓存当中
 * 当该数据发生改变(被修改或删除),就从集合中移除该对象
 * 由于内存的限制,当集合中的数据过多时
 * 软引用对象会被垃圾回收器回收,避免内存溢出的情况
 * @author 41882
 *
 */
@Repository
public class DataCache {
	private Hashtable<String,DataRef> dataRefs = new Hashtable<String,DataRef>();
	private ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
	
	@Autowired(required=true)
	private IHibernateDao<Object,String> hibernateDao;
	/**
	 * 用于创建实例对象软引用的类
	 * @author 41882
	 *
	 */
	private class DataRef extends SoftReference<Object> {
		public DataRef(Object obj,ReferenceQueue<Object> queue){
			super(obj,queue);
			try{
				Method method = obj.getClass().getMethod("getId");
				//缓存中的标识是该类名称与ID的组合
				this._key = obj.getClass().getSimpleName()+(String) method.invoke(obj);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		private String _key;
	}
	/**
	 * 从缓存区获取一个对象(如果缓存区没有该对象,则执行查询获得该对象)
	 * @param <T>
	 * @param id
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> clz,String id){
		T obj = null;
		//检查缓存中是否有该实例的软引用
		if(dataRefs.containsKey(clz.getSimpleName() + id)){
			//如果有则直接从缓存中取得
			DataRef ref = dataRefs.get(clz.getSimpleName() + id);
			obj = (T) ref.get();
		}
		if(obj == null){
			//如果没有则构建该实例
			obj = (T) hibernateDao.get(clz, id);
			this.cacheData(obj);//将该实例加入到缓存区
		}
		return obj;
	}
	/**
	 * 从缓存区当中移除一个对象
	 * (通常在该对象被修改或删除的时候,就从缓存区移除该对象)
	 * @param obj
	 */
	public void removeObject(Object obj){
		try {
			Method method = obj.getClass().getMethod("getId");
			String id = (String) method.invoke(obj);
			dataRefs.remove(obj.getClass().getSimpleName() + id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 从缓存区当中根据ID和类型移除多个对象
	 * @param clz
	 * @param ids
	 */
	public void removeObject(Class<?> clz,String[] ids){
		if(ids != null && ids.length>0){
			for(String id : ids){
				dataRefs.remove(clz.getSimpleName() + id);
			}
		}
	}
	/**
	 * 清空缓存区
	 */
	public void clearCache(){
		dataRefs.clear();
	}
	/**
	 * 缓存数据
	 * @param obj 需要执行缓存的对象
	 */
	public void cacheData(Object obj) {
		cleanQueue();
		DataRef ref = new DataRef(obj,queue);
		try{
			Method method = obj.getClass().getMethod("getId");
			dataRefs.put(obj.getClass().getSimpleName() + method.invoke(obj), ref);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 清除已经被回收的软引用对象
	 */
	private void cleanQueue(){
		DataRef ref = null;
		while((ref=(DataRef) queue.poll()) != null) {
			dataRefs.remove(ref._key);
		}
	}
}
