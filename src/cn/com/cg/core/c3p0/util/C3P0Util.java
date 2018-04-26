package cn.com.cg.core.c3p0.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类
 * 连接池 , 该对象负责存储多个数据库连接 . 等需要使用连接的时候 会从连接池中随机调度一个使用。
 * 当连接使用完毕之后 会自动归还于连接池中 , 而不是关闭连接 
 * 数据源 : 连接池 + 管理方式
 * DBCP  apache组织的 , C3P0 另一个数据源
 * @author Administrator
 */
public class C3P0Util {
	
	//连接属性
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static int initialPoolSize;
	private static int maxPoolSize;
	//创建数据源对象
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	//静态快 
	static{
		
		try {
			
			//放到 src 目录之下
			ResourceBundle rb = ResourceBundle.getBundle("jdbc");
			driver = rb.getString("driver");
			url = rb.getString("url");
			username = rb.getString("username");
			password = rb.getString("password");
			initialPoolSize=Integer.parseInt(rb.getString("initialPoolSize"));
			maxPoolSize=Integer.parseInt(rb.getString("maxPoolSize"));
			
			
			
			//配置数据源
			cpds.setDriverClass(driver);
			cpds.setJdbcUrl(url);
			cpds.setUser(username);
			cpds.setPassword(password);
			//配置初始连接个数
			cpds.setInitialPoolSize(initialPoolSize);
			//配置最大连接个数
			cpds.setMaxPoolSize(maxPoolSize);
			//配置超时时间
			cpds.setCheckoutTimeout(30000);
			
		} catch (PropertyVetoException e) {
			System.out.println("数据源初始化失败 !");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取连接的方法
	 */
	public static Connection getConnection(){
		Connection connection = null;
		try {
			connection = cpds.getConnection();
		} catch (SQLException e) {
			System.out.println("获取连接失败 !");
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关闭方法
	 * 如果调用查询方法 , 则会有 rs 结果集对象 . 但是没有 连接和命令对象
	 * 如果是增删改 , 则没有 rs 对象 但是有 连接 和命令对象 所以需要分开关闭
	 */
	public static void closeAll(ResultSet rs){
		
		try {
			
			if( rs != null ){
				Statement st = rs.getStatement();
				Connection conn = st.getConnection();
				rs.close();
				st.close();
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAll(ResultSet rs,Statement s,Connection c){
		
		try {
			
			if( rs != null ){
				rs.close();
				rs = null;
			}
			
			if( c != null ){
				c.close();
				c = null;
			}
			
			if( s != null ){
				s.close();
				s = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用的 查询 方法
	 * 支持任何类型形式的查询代码
	 * @param sql 所执行的 SQL 语句代码
	 * @param obj 该语句代码所对应的 参数列表 , 如果没有任何参数 则显示为 null
	 * @return 查询完毕之后的结果集对象 . 但是所有元素暂时都没有关闭
	 */
	public static ResultSet query(String sql,Object[] obj){
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null ;
		
		try {
			//获取连接
			connection = C3P0Util.getConnection();
			//创建预处理命令对象
			ps = connection.prepareStatement(sql);
			if( obj != null ){
				//有参数
				for(int i=0;i<obj.length;i++){
					//赋值
					ps.setObject(i+1,obj[i]);
				}
			}
			//执行
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("查询失败 !");
			e.printStackTrace();
		}
		
		return rs;
	}
	
	/**
	 * 通用的 增删改 方法
	 * 支持任何类型形式的增删改代码
	 * @param sql 所执行的 SQL 语句代码
	 * @param obj 该语句代码所对应的 参数列表 , 如果没有任何参数 则显示为 null
	 * @return 受影响行数 . 对象已关闭
	 */
	public static int update(String sql,Object[] obj){
		
		Connection connection = null;
		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			//获取连接
			connection = C3P0Util.getConnection();
			//设置为手动事务
			connection.setAutoCommit(false);
			//创建预处理命令对象
			ps = connection.prepareStatement(sql);
			if( obj != null ){
				//有参数
				for(int i=0;i<obj.length;i++){
					//赋值
					ps.setObject(i+1,obj[i]);
				}
			}
			//执行
			rows = ps.executeUpdate();
			
			if( rows > 0 ){
				connection.commit();
			}else{
				connection.rollback();
			}
			
		} catch (SQLException e) {
			System.out.println("查询失败 !");
			e.printStackTrace();
			//回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			//关闭资源
			C3P0Util.closeAll(null,ps,connection);
		}
		
		return rows;
	}
	
}
