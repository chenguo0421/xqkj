package cn.com.cg.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import cn.com.cg.database.util.DBHelper;
import cn.com.cg.entity.Student;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class LoginAction extends ActionSupport{
//	@Override
//	public String execute() throws Exception {
//		return "success";
//	}
	public String aa() throws Exception {
//		ActionContext.getContext().put("aaa","bbbb");
	
		
		Student stu=new Student();
		stu.setIdCard("123");
		stu.setName("zhangsan");
		ActionContext.getContext().getValueStack().push(stu);
		
		
		return "success";
	}
	
	
	 private Map<String,Object> dataMap;  
	    private String key = "Just see see";  
	      
	    public String json() {  
	        // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据  
	        dataMap = new HashMap<String, Object>();  
//	        Student user = new Student();  
//	        user.setName("张三");  
//	        user.setIdCard("123456");
	    	ArrayList<HashMap<String, Object>> map = DBHelper.getHashMap();
	    	if (map==null) {
	    		dataMap.put("success", false);
	    		System.out.println("查询失败");
			}else {
				dataMap.put("list",map);  
		        // 放入一个是否操作成功的标识  
		        dataMap.put("success", true);  
		        System.out.println("查询成功");
			}
	    	
	        // 返回结果  
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
	
}
