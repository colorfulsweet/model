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
	private Hashtable<String,Hashtable<String,DataRef>> dataRefs;//缓存区
	private ReferenceQueue<Object> queue;//引用队列
	/**
	 * 该缓存区包含两层结构,第一层是以对象类型名称为键的哈希表
	 * 这个哈希表的值是第二层哈希表结构
	 * 其中以该对象的ID作为键,以软引用对象为值
	 */
	public DataCache(){
		dataRefs = new Hashtable<String,Hashtable<String,DataRef>>();
		queue = new ReferenceQueue<Object>();
	}
	/**
	 * 用于缓存的软引用
	 * @author Sookie
	 */
	private class DataRef extends SoftReference<Object> {
		public DataRef(Object obj,ReferenceQueue<Object> queue){
			super(obj,queue);
			this.key = obj.getClass().getName();
			this.id = (String) ReflectUtils.getItemField(obj, "id");
		}
		private String key;
		private String id;
	}
	/**
	 * 从缓存区根据类型和ID获取一个对象
	 * 如果缓存区没有该对象,则返回null
	 * @param clz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> clz,String id){
		Hashtable<String,DataRef> cacheTable = dataRefs.get(clz.getName());
		if(cacheTable == null){
			return null;
		} 
		DataRef ref = cacheTable.get(id);
		if(ref != null){
			return  (T) ref.get();
		} else {
			return null;
		}
	}
	/**
	 * 将对象的软引用实例加入到缓存区
	 * @param obj 强引用对象
	 */
	public void cacheData(Object obj){
		cleanQueue();
		Hashtable<String,DataRef> cacheTable = null;
		cacheTable = dataRefs.get(obj.getClass().getName());
		if(cacheTable == null) {
			cacheTable = new Hashtable<String,DataRef>();
			dataRefs.put(obj.getClass().getName(), cacheTable);
		}
		String id = (String) ReflectUtils.getItemField(obj, "id");
		DataRef ref = new DataRef(obj,queue);
		cacheTable.put(id, ref);
	}
	/**
	 * 从缓存区当中移除一个对象
	 * (通常在该对象被修改或删除的时候,就从缓存区移除该对象)
	 * @param obj
	 */
	public void removeObject(Object obj){
		String id = (String) ReflectUtils.getItemField(obj, "id");
		Hashtable<String, DataRef> cacheTable = dataRefs.get(obj.getClass().getName());
		if(cacheTable != null){
			cacheTable.remove(id);
		}
	}
	/**
	 * 从缓存区当中根据ID和类型移除多个对象
	 * @param clz
	 * @param ids
	 */
	public void removeAllObjects(Class<?> clz,String... ids){
		Hashtable<String, DataRef> cacheTable = dataRefs.get(clz.getName());
		if(ids != null && ids.length>0){
			for(String id : ids){
				cacheTable.remove(id);
			}
		}
	}
	/**
	 * 清空整个缓存区
	 */
	public void clearCache(){
		dataRefs.clear();
	}
	/**
	 * 清除已经被回收的软引用对象
	 * (软引用对象被回收以后会加入到引用队列)
	 */
	private void cleanQueue(){
		DataRef ref = null;
		Hashtable<String, DataRef> cacheTable = null;
		while((ref = (DataRef) queue.poll()) != null) {
			cacheTable = dataRefs.get(ref.key);
			if(cacheTable != null){
				cacheTable.remove(ref.id);
			}
		}
	}
}
