package w20230614;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/modify.do")
public class L04JDBCStudentModify extends HttpServlet{
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String driver = "oracle.jdbc.driver.OracleDriver";
	private final String user = "c##smart01";
	private final String pw = "oracle01";
	private final String sqll = "SELECT * FROM STUDENT WHERE num=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String numStr = req.getParameter("num");
		int num = 0;
		try {
			num=Integer.parseInt(numStr);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(400,"num 파라미터가 없으면 동작하는 페이지가 없습니다.");
			return;
		}

		String name=null, phone=null, address=null,birthday=null;
		List list=new ArrayList<>();
		int cnt = 0;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pw);
			PreparedStatement pstmt = conn.prepareStatement(sqll);
			pstmt.setInt(1,  num);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				++cnt;
				num = rs.getInt("num");
				name=rs.getString("name");
				phone=rs.getString("phone");
				address=rs.getString("address");
				birthday=rs.getString("birthday");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.sendError(500,"db 중 문제가 발생했습니다. 다시 시도하세요!");
			return;
		}
		
		
		
		String html = "<h1>학생 수정 양식</h1>";
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html;charset=UTF-8");
		if (cnt==0) {
			html+="<h2>해당 레코드는 존재하지(삭제된 레코드) 않습니다.</h2>";
			
		}
		else {
			html+="<p><form action='./modify.do' method='post'>";
			html+="<p><label>num : <input name='num' value='"+num+"' readonly /></label></p>";	
			html+="<p><label>name : <input name='name' value='"+name+"'/></label></p>";	
			html+="<p><label>phone : <input name='phone' value='"+phone+"'/></label></p>";	
			html+="<p><label>address : <input name='address' value='"+address+"'/></label></p>";	
			html+="<p><label>birthday : <input name='birthday' value='"+birthday+"'/></label></p>";	
			html+="<p><button style='margin-right:20px;'>수정 양식 제출</button>"
					+"<a href='./remove.do?num="+num+"'>삭제</a>"
					+"</p>";
			html+="</form>";	
		}
		out.print(html);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String numStr = req.getParameter("num");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String birthday = req.getParameter("birthday");
		int modify=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql="UPDATE STUDENT SET name=?, phone=?, address=?, birthday =? WHERE num=?";
			conn=DriverManager.getConnection(url,user,pw);
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,  name);
			pstmt.setString(2,  phone);
			pstmt.setString(3,  address);
			pstmt.setString(4,  birthday);
			int num=Integer.parseInt(numStr);
			pstmt.setInt(5,  num);
			modify = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(500, "학생 데이터 수정시 db오류 발생 다시 시도하세요!");
			return;
		}
//		if(modify>0) {
//			resp.getWriter().append("<h1>수정 성공</h1>");
//		}else {
//			resp.getWriter().append("<h1>수정 실패! 이미 삭제된 레코드 입니다.</h1>");
//		}
		if(modify>0) {
			resp.sendRedirect("./detail.do?num="+numStr);
		}
		else {
			resp.sendRedirect("./detail.do?num="+numStr);
		}
	}
}
