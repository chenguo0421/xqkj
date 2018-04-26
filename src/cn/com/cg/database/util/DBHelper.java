package cn.com.cg.database.util;



import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//连接数据类
public class DBHelper {
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1:3306/";
	private final static String USER = "root";
	private final static String PASSWORD = "232232a";
	private final static String DATABASENAME = "mydatabase";
	private static Connection conn = null;
	private static PreparedStatement ps;
	private static ResultSet rs;
	
	public static Connection getConn() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL + DATABASENAME, USER,
					PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接失败");
		}
		return conn;
	}

	public static void close() {
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Connection conn = getConn();
		try {
			
			
			ps=conn.prepareStatement("select * from city");
			rs=ps.executeQuery();
			while(rs.next()){
				String name=rs.getString("name");
				String pass=rs.getString("address");  
				System.out.println(name+","+pass);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<HashMap<String, Object>> getHashMap(){
		
		ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		Connection conn = getConn();
		try {
			ps=conn.prepareStatement("select * from city");
			rs=ps.executeQuery();
			while(rs.next()){
				HashMap<String, Object> map=new HashMap<String, Object>();
				String name=rs.getString("name");
				String pass=rs.getString("address");  
				System.out.println(name+","+pass);
				map.put("conmunity",name);
				map.put("address",pass);
				list.add(map);
			}
			close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
}
