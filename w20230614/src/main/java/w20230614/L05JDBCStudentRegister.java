package w20230614;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/register.do")
public class L05JDBCStudentRegister extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String numStr = req.getParameter("num");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String birthday = req.getParameter("birthday");

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "c##smart01";
		String pw = "oracle01";
		int register=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			String sql="INSERT INTO STUDENT (NUM,NAME,PHONE,ADDRESS, BIRTHDAY) VALUES(?,?,?,?,?)";
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(numStr));
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			pstmt.setString(4, address);
			pstmt.setString(5, birthday);
			register=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
//			resp.sendError(500, "num를 꼭 입력하거나 중복될 수 없습니다. or db나 서버에 오류가 발생했습니다.");
//			return;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (register>0) {
			resp.sendRedirect("./list.do");
		}else {// 등록은 실패오류가 발생하지 0을 잘 반환하지 않는다. 
			resp.sendRedirect("./L05StudentRegister.html");
			
		}
	}
}
