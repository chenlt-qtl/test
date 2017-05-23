package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.MsgReceive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 消息接收表 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface MsgReceiveDao extends ICrudDao<MsgReceive> {

    /**
     * 批量添加记录
     * @param msgReceives
     * @return
     */
    public int saveBatch(@Param("msgReceives") List<MsgReceive> msgReceives);

    /**
     * 根据消息ID删除记录
     * @param msgId
     * @return
     */
    public int deleteByMsgId(@Param("msgId") String msgId);

}
