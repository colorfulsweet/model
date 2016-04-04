package com.system.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.model.User;
import com.system.service.dao.IHibernateDao;
import com.system.util.DataCache;
import com.system.util.ReflectUtils;
import com.system.util.SpringUtils;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private IHibernateDao<User,String> hibernateDao;
	
	@Autowired
	private DataCache dataCache;
	
	@Autowired
	private ServletContext context;
	
	private DiskFileItemFactory factory;
	/**
	 * 创建/修改 用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/save.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveUser(User user){
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		if(user!=null && user.getId()!=null && user.getId().length()!=0){
			//修改
			User destUser = hibernateDao.get(User.class, user.getId());
			try {
				ReflectUtils.transferFields(user, destUser);
			} catch (Exception e) {
				e.printStackTrace();
				return SystemMessage.getMessage("failed");
			}
			hibernateDao.update(destUser);
		} else {
			//新增
			user.setCreateTime(new Date());
			hibernateDao.save(user);
		}
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/delete.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delUser(User user){
		hibernateDao.del(user);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 批量删除用户
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteUsers.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delUsers(@RequestParam(value="userId")String[] ids){
		if(ids.length == 0){
			return SystemMessage.getMessage("failed");
		}
		StringBuilder hql = new StringBuilder("delete User u where u.id in (");
		for(int i=0 ; i<ids.length ; ++i){
			hql.append("?,");
		}
		hql.setLength(hql.length() - 1);
		hql.append(")");
		hibernateDao.excuteUpdate(hql.toString(), Arrays.asList(ids).toArray());
		dataCache.removeAllObjects(User.class, ids);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 上传头像
	 * @param request
	 * @return "success"
	 */
	@RequestMapping(value="/uploadIcon.html",method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String uploadIcon(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		if(factory == null){
			factory = SpringUtils.getBean(DiskFileItemFactory.class);
		}
		SessionFactory sessionFactory = SpringUtils.getSpringMVCBean(context, "sessionFactory");
		Session session = sessionFactory.openSession();
		LobHelper lobHelper = session.getLobHelper();
		// 创建解析类的实例
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 文件大小限制1M
		sfu.setFileSizeMax(1024L * 1024L);
		// 每个表单域中数据会封装到一个对应的FileItem对象上
		try {
			List<FileItem> items = sfu.parseRequest(request);
			// 区分表单域
			for (FileItem item : items) {
				// isFormField为true，表示这不是文件上传表单域
				if (!item.isFormField()) {
					InputStream input = item.getInputStream();
					user.setIcon(lobHelper.createBlob(input,0));
					hibernateDao.update(user);
					input.close();
				}
			}
			return SystemMessage.getMessage("uploadSuccess");
		} catch (FileUploadException | IOException e) {
			e.printStackTrace();
			return SystemMessage.getMessage("uploadFailed");
		} finally {
			session.close();
		}
	}
	/**
	 * 获取当前用户的头像
	 * @param session
	 * @param response
	 */
	@RequestMapping(value="/getIcon.html",method=RequestMethod.GET)
	public void getUserIcon(HttpSession session,HttpServletResponse response){
		byte[] buf = new byte[2048];
		try {
			User user = (User) session.getAttribute("user");
			Blob icon = user.getIcon();
			if(icon != null){
				InputStream input = icon.getBinaryStream();
				BufferedInputStream bufferInput = new BufferedInputStream(input);
				OutputStream output = response.getOutputStream();
				BufferedOutputStream bufferOutput = new BufferedOutputStream(output);
				int len = 0;
				while((len=bufferInput.read(buf)) != -1){
					bufferOutput.write(buf,0,len);
				}
				bufferOutput.flush();
				bufferOutput.close();
				bufferInput.close();
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
