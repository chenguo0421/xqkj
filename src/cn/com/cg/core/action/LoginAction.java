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
	        // dataMap�е����ݽ��ᱻStruts2ת����JSON�ַ�������������Ҫ��������е�����  
	        dataMap = new HashMap<String, Object>();  
//	        Student user = new Student();  
//	        user.setName("����");  
//	        user.setIdCard("123456");
	    	ArrayList<HashMap<String, Object>> map = DBHelper.getHashMap();
	    	if (map==null) {
	    		dataMap.put("success", false);
	    		System.out.println("��ѯʧ��");
			}else {
				dataMap.put("list",map);  
		        // ����һ���Ƿ�����ɹ��ı�ʶ  
		        dataMap.put("success", true);  
		        System.out.println("��ѯ�ɹ�");
			}
	    	
	        // ���ؽ��  
	        return SUCCESS;  
	    }  
	  
	    public Map<String, Object> getDataMap() {  
	        return dataMap;  
	    }  
	  
	    //����key���Բ���Ϊjson�����ݷ���  
	    @JSON(serialize=false)  
	    public String getKey() {  
	        return key;  
	    }  
	
}
