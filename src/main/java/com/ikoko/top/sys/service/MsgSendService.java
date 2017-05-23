package com.ikoko.top.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.sys.dao.MsgSendDao;
import com.ikoko.top.sys.entity.MsgSend;

/**
* 消息发送表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MsgSendService extends CrudService<MsgSendDao, MsgSend> {

}
