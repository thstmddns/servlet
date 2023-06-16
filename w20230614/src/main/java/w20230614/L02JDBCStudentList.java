package w20230614;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// /student/list.do path의 호출이 오면 톰캣이 LJDBCStudentList 실행 후 응답(동적페이지)
// 학생자료 주세요(get)
// 학생자료를 등록 수정 삭제 (DML post)
// 컴파일러가 검사(Override,FunctionalInterface)하거나 자동완성 (WebServlet)
// Webservlet : DD(web.xml) 배포서술자에 동적리소스가 어떤 요청이 왔을 때 어떤 동적 리소스를 호출한건지 정의하는것 
// 배포서술자 : 톰캣 서버에 개발자가 만든 웹앱을 배포할 때 이렇게 동작할거야라는 설정을 정의하는 곳 
public class L02JDBCStudentList extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "c##smart01";
		String pw = "oracle01";
		String html="";
		html+="<h>학생 리스트</h1>";
		html += "<h2><a href='./L05StudentRegister.html'>학생 등록</a></h2>";
		html+="<table>";
		html+="<tr><th>num</th><th>name</th><th>birthday</th></tr><th>상세</th>";
		
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		// 통신, 접속은 오류를 동반하기에 예외처리를 해줘야한다.
		try {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, pw);
		String sql = "SELECT * FROM STUDENT";
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			int num = rs.getInt("NUM");
			String name=rs.getString("NAME");
			String PHONE=rs.getString("PHONE");
			String ADDRESS=rs.getString("ADDRESS");
			String BIRTHDAY=rs.getString("BIRTHDAY");
			html+="<tr>";
			html+="<td>" + num + "</td>";
			html+="<td>" + PHONE + "</td>";
			html+="<td>" + ADDRESS + "</td>";
			html+="<td>" + BIRTHDAY + "</td>";
			html+="<td><a href=\"./detail.do?num="
					+ num
					+ "\">"
					+ "상세"
					+ "</a></td>";
			
		}
//		System.out.println(conn);
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		html += "</table>";
		resp.setContentType("text/html;charset utf-8;");
		resp.getWriter().append(html);
	}
}