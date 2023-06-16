package w20230614;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//@WebServlet("/student/detail.do")
public class L03JDBCStudentDetail extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String numStr = req.getParameter("num");
		int num = 0;
//		?num=1000 OR 1 == 1;DROP TABLE STUDENT
		try {
			num = Integer.parseInt(numStr);
		}catch (NumberFormatException e) {
			resp.sendError(400);
			return;
		}
		String sql = "SELECT * FROM STUDENT WHERE num=?";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "c##smart01";
		String pw = "oracle01";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		PrintWriter out = resp.getWriter();
		String html="";
		html += "<h1>학생 상세 정보</h1>";
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,pw);
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,  num);
//			pstmt.setInt(2,  numStr);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			html += "<p>num:"+rs.getInt("num")+"</p>";
			html += "<p>name:"+rs.getString(2)+"</p>";
			html += "<p>phone:"+rs.getString(3)+"</p>";
			html += "<p>address:"+rs.getString(4)+"</p>";
			html += "<p>birthday:"+rs.getString(5)+"</p>";			
			html += "<a href='./modify.do?num="
					+ rs.getInt("num")
					+ "'>수정(상세와 같은데 수정 양식이 존재)</a>";	
			
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	
		resp.setContentType("text/html;charset=UTF-8");
		out.print(html);
	}
	
}