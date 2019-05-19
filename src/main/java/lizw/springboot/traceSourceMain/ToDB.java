package lizw.springboot.traceSourceMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lizw.springboot.entity.TraceAnalysisEntity;

public class ToDB {

    private ToDB(){
		
	}

	private static ToDB toDB = new ToDB();

	public static ToDB getInstance() {
		return toDB;
	}
	
	String url = "jdbc:mysql://192.168.1.62:3306/test?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
	String user = "hostinfo";
	String password = "hostinfo123";
	public int save(TraceAnalysisEntity traceAnalysisEntity) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);//设置手动
			
			//ps = conn.prepareStatement("  insert into trace values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ");
			ps = conn.prepareStatement("  INSERT INTO trace_table (hop,A_ip,A_country,A_province,A_city,A_operator,A_p,B_ip,B_country,B_province,B_city,B_operator,B_p,hop_sign) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ");
			
			ps.setInt(1, traceAnalysisEntity.getHop());
			ps.setString(2, traceAnalysisEntity.getA_ip());
			ps.setString(3, traceAnalysisEntity.getA_country());
			ps.setString(4, traceAnalysisEntity.getA_province());
			ps.setString(5, traceAnalysisEntity.getA_city());			
			ps.setString(6, traceAnalysisEntity.getA_operator());
			ps.setString(7, traceAnalysisEntity.getA_p());
			ps.setString(8, traceAnalysisEntity.getB_ip());
			ps.setString(9, traceAnalysisEntity.getB_country());
			ps.setString(10, traceAnalysisEntity.getB_province());
			ps.setString(11, traceAnalysisEntity.getB_city());			
			ps.setString(12, traceAnalysisEntity.getB_operator());
			ps.setString(13, traceAnalysisEntity.getB_p());
			ps.setInt(14, traceAnalysisEntity.getHop_sign());
			
			
			//执行
			ps.executeUpdate();
			//ps.executeBatch();
			
			//提交
			conn.commit();
			
		} catch (ClassNotFoundException e) {
			//conn.rollback();
			e.printStackTrace();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
		
	}
	

}
