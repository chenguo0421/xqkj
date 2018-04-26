package com.cg.wx.brush.qa;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import cn.com.cg.core.c3p0.util.C3P0Util;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class QueryQuestion extends ActionSupport{
	private Map<String,Object> dataMap;  
	private String key = "QueryQuestion key"; 
	private String pageSize;
	private String sql="select * from question where isAnswer=1";
	public String getPageSize() {
		return pageSize;
	}



	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}



	public String queryQALists() throws IOException{
		dataMap = new HashMap<String, Object>();  
		if (pageSize==null||"".equals(pageSize)) {
			pageSize="3";
		}
		ResultSet res = C3P0Util.query(sql,null);
		List<HashMap<String, String>> map = getHashMap(res);
		if (map!=null&&map.size()>0) {
			if (Integer.parseInt(pageSize)<map.size()) {
				map = map.subList(0,Integer.parseInt(pageSize));
			}
		}
		
		if (map==null||map.size()<=0) {
			dataMap.put("resultCode",-1);
			dataMap.put("result", null);
		}else {
			dataMap.put("resultCode",100);
			dataMap.put("result", map);
		}
		return SUCCESS;
	}
	
	

	private ArrayList<HashMap<String, String>> getHashMap(ResultSet res) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> mapList=new ArrayList<HashMap<String,String>>();
		try {
			while (res.next()) {
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("question",res.getString("question"));
				map.put("answer",res.getString("answer"));
				mapList.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}



	public Map<String, Object> getDataMap() {  
		return dataMap;  
	}  
	//设置key属性不作为json的内容返回  
    @JSON(serialize=false)  
    public String getKey() {  
        return key;  
    }


}
