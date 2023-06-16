package w20230614;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class System2{
	
}
@WebServlet("/hello.do")
public class L01HelloServlet extends HttpServlet{
	int a = 0;
	int b = 0;
	String s = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException{
		
		String aStr = req.getParameter("a");
		String bStr = req.getParameter("b");
		
		int a = 0;b = 0;
		try {
			a = Integer.parseInt(aStr);
			b = Integer.parseInt(bStr);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
//		int a = (aStr!=null)?Integer.parseInt(aStr):0;
//		int b = (bStr!=null)?Integer.parseInt(bStr):0;
		
		resp.setContentType("text/html;charset=UTF-8");
		String htmlStr = "<h1>안녕 동적리소스 서블릿!</h1>";
		htmlStr += "<h2>axb=" + (a*b)+ "</h2>";
		PrintWriter out = resp.getWriter();
		out.append(htmlStr);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 해당 리소스를  post방식으로 요청하면 톰캣서버가 해당 리소스의 doPost를 실행
		// 톰캣이 요청 정보를 HttpServletRequest req에 참조해서 doPost를 실행
		// url은 무조건 데이터를 문자열로 전달.
		String aStr = req.getParameter("a");
		String bStr = req.getParameter("b");
		
		// resp.getWriter()에 문자열을 담아 놓으면 doPost 실행이 끝나고 응답 리소스로 해당 문자열을 사용한다.
		// 만약 문자열의 형식을 지정하면 해당 문자열을 그 형식의 문서로 전달(default: text/html)
		resp.setContentType("text/html;charset=UTF-8;");
		resp.getWriter()
			.append("<h1>hello.do doPost()</h1>")
			.append("<h2>post 방식은 양식을 제출합니다.</h2>")
			.append("<p>a+b="+(aStr+ bStr)+"</p>");
	}
}
