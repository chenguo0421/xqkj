package com.cg.wx.brush.qa;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.omg.CORBA.Context;

import cn.com.cg.core.c3p0.util.C3P0Util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import freemarker.ext.servlet.HttpRequestHashModel;

@SuppressWarnings("serial")
public class QAQueryAction extends ActionSupport implements ModelDriven<Question>{

	private Map<String,Object> dataMap;  
	private String key = "QAQueryAction key"; 
	private Question q=new Question();
	


	public String queryQALists() throws IOException{
//		q.toString();
		C3P0Util.update("insert into question(question,answer,isAnswer) values (?,?,?) ", new Object[]{q.getQuestion(),q.getAnswer(),0});
		return SUCCESS;
	}
	
	

	public Map<String, Object> getDataMap() {  
		return dataMap;  
	}  
	//设置key属性不作为json的内容返回  
    @JSON(serialize=false)  
    public String getKey() {  
        return key;  
    }


	@Override
	public Question getModel() {
		// TODO Auto-generated method stub
		return q;
	}  
	
	
}	
