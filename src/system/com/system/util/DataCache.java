package com.system.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import org.springframework.stereotype.Repository;
/**
 * 数据库数据的高速缓存器
 * 使用软引用构成的集合实现
 * 在根据执行查询的时候,就把查询到的对象放入到缓存当中
 * 当该数据被修改,就把该对象重新添加到集合当中
 * 当该数据被删除,就从集合中移除该对象
 * 由于内存的限制,当集合中的数据过多时
 * 软引用对象会被垃圾回收器回收,避免内存溢出的情况
 * @author 41882
 *
 */
@Repository
public class DataCache {
	private Hashtable<String,DataRef> dataRefs;//缓存区
	private ReferenceQueue<Object> queue;//引用队列
	
	public DataCache() {
		dataRefs = new Hashtable<String,DataRef>();
		queue = new ReferenceQueue<Object>();
	}
	/**
	 * 用于创建实例对象软引用的类
	 * @author 41882
	 *
	 */
	private class DataRef extends SoftReference<Object> {
		public DataRef(Object obj,ReferenceQueue<Object> queue){
			super(obj,queue);
			String id = (String) ReflectUtils.getItemField(obj, "id");
			//缓存中的标识是该类名称与ID的组合
			this._key = obj.getClass().getSimpleName()+id;
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
		//从缓存中获取该实例的软引用
		DataRef ref = dataRefs.get(clz.getSimpleName() + id);
		if(ref == null){
			return null;
		} else {
			//由软引用获取强引用
			//如果该软引用对象已被回收,返回null
			return (T) ref.get();
		}
	}
	/**
	 * 从缓存区当中移除一个对象
	 * (通常在该对象被修改或删除的时候,就从缓存区移除该对象)
	 * @param obj
	 */
	public void removeObject(Object obj){
		String id = (String) ReflectUtils.getItemField(obj, "id");
		dataRefs.remove(obj.getClass().getSimpleName() + id);
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
		dataRefs.put(obj.getClass().getSimpleName() + ReflectUtils.getItemField(obj, "id"), ref);
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
