/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.conf.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * author geekcattle
 * date 2017/3/22 0022 下午 15:32
 */
@Component
public class RedisSessionDAO extends EnterpriseCacheSessionDAO  {
    private static Logger logger = Logger.getLogger(RedisSessionDAO.class);
    @Value("${server.session-timeout}")
    private long expireTime;
    
	@Autowired(required=true)
    private RedisTemplate<Object, Object> redisTemplate;
    private static String prefix = "jypx-session:";
    @Override
    protected Serializable doCreate(Session session) {
    	Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:"+ session.getId());
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session,getExpireTime(), TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
    	logger.debug("获取session:"+sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
    	super.doUpdate(session);
        logger.debug("获取session:"+session.getId());
        String key = prefix + session.getId().toString();
        //if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        //}
        redisTemplate.expire(key, getExpireTime(), TimeUnit.SECONDS);        
    }

    @Override
    public void delete(Session session) {
    	logger.debug("删除session:"+ session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }
    
    @Override
    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        sessionIdGenerator = new JavaUuidSessionIdGenerator();
        logger.debug("SessionID "+ sessionIdGenerator);
        super.setSessionIdGenerator(sessionIdGenerator);
    }
    
    public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
    
    public long getExpireTime() {  
        return expireTime*60*1000;  
    } 
}
