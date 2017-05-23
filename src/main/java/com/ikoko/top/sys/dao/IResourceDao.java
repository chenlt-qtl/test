package com.ikoko.top.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * 资源Dao接口
 * 
 * @author cc
 */
@MyBatisDao
public interface IResourceDao extends ICrudDao<Resource> {

	/**
	 * 获取资源排序值
	 * 
	 * @param id
	 * @return
	 */
	public int getResourceSort(@Param("id") String id);

	/**
	 * 获取权限标识
	 * @param resourceIds
	 * @return
	 */
	public List<Resource> getResources(@Param("resourceIds") Set<String> resourceIds);

	/**
	 * 查询是否存在子节点
	 * @param resource
	 * @return
	 */
	public int findNext(Resource resource);

}
