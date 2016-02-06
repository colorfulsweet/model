package com.system.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.bean.Dict;
import com.system.bean.Dict.DictClause;
import com.system.util.HibernateUtils;

@Service
public class DictService implements IDictService {

	@Autowired
	private HibernateUtils hibernateUtils;
	
	@Override
	public List<DictClause> getDictClauseList(Dict dict) {
		Session session = null;
		List<DictClause> dictClauseList = null;
		try{
			session = hibernateUtils.getSession();
			dict = (Dict) session.get(Dict.class, dict.getId());
			if(dict != null){
				dictClauseList = dict.getClauses();
				dictClauseList.remove(null);
			}
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return dictClauseList;
	}

	@Override
	public void saveDictClause(DictClause dictClause) {
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			Dict dict = (Dict) session.get(Dict.class, dictClause.getDict().getId());
			if(dict != null){
				dict.getClauses().add(dictClause);
			}
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
	}

}
