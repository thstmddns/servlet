package w20230614;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/remove.do")
public class L06JDBCStudentRemove extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String numStr=req.getParameter("num");
		int num=0;
		try {
			num=Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			resp.sendError(400, "num을 꼭 보내야 삭제할 수 있습니다.");
			return;
		}
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "c##smart01";
		String pw = "oracle01";
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int remove=0;
		try {
			String sql="DELETE FROM STUDENT WHERE NUM=?";
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, pw);
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			remove=pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.sendError(500, "서버오류가 발생");
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		if(remove>0) {
			resp.sendRedirect("./list.do");
		}
		else {
			resp.sendRedirect("./modify.do?num="+numStr);
		}
	}
}
