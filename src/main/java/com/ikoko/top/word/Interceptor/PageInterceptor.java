package com.ikoko.top.word.Interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikoko.top.word.dto.Page;  

@Intercepts({
     @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),  
     @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})  
public class PageInterceptor implements Interceptor {  
  
    private static final String SELECT_ID="page";
    
    private Logger log = LoggerFactory.getLogger(PageInterceptor.class);


    //插件运行的代码，它将代替原有的方法
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	log.info("PageInterceptor -- intercept");
        
        if (invocation.getTarget() instanceof StatementHandler) {  
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();  
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);  
            MappedStatement mappedStatement=(MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            String selectId=mappedStatement.getId();
            
            if(selectId.toLowerCase().endsWith(SELECT_ID)){//拦截以page结尾的selectId 
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");  
                // 分页参数作为参数对象parameterObject的一个属性  
                String sql = boundSql.getSql();
                Page co=(Page)(boundSql.getParameterObject());
                
                // 重写sql  
                String countSql=concatCountSql(sql);
                String pageSql=concatPageSql(sql,co);
                
                log.info("重写的 count  sql        :"+countSql);
                log.info("重写的 select sql        :"+pageSql);
                
                Connection connection = (Connection) invocation.getArgs()[0];  
                
                PreparedStatement countStmt = null;  
                ResultSet rs = null;  
                int totalCount = 0;  
                try { 
                    countStmt = connection.prepareStatement(countSql);  
                    rs = countStmt.executeQuery();  
                    if (rs.next()) {  
                        totalCount = rs.getInt(1);  
                    } 
                    
                } catch (SQLException e) {  
                	log.error("Ignore this exception"+e);  
                } finally {  
                    try {  
                        rs.close();  
                        countStmt.close();  
                    } catch (SQLException e) {  
                        log.error("Ignore this exception"+ e);  
                    }  
                }  
                
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);            
              
                //绑定count
                co.setCount(totalCount);
            }
        } 
        
        return invocation.proceed();
    }
    
    /**
     * 拦截类型StatementHandler 
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
    }
    
    @Override
    public void setProperties(Properties properties) {
        
    }  
    
    
    public String concatCountSql(String sql){
        StringBuffer sb=new StringBuffer("select count(*) from ");
        sql=sql.toLowerCase();
        
        if(sql.lastIndexOf("order")>sql.lastIndexOf(")")){
            sb.append(sql.substring(sql.indexOf("from")+4, sql.lastIndexOf("order")));
        }else{
            sb.append(sql.substring(sql.indexOf("from")+4));
        }
        return sb.toString();
    }
    
    public String concatPageSql(String sql,Page co){
        StringBuffer sb=new StringBuffer();
        sb.append(sql);
        sb.append(" limit ").append(co.getPagebegin()).append(" , ").append(co.getPagesize());
        return sb.toString();
    }
    
    public void setPageCount(){
        
    }
    
}