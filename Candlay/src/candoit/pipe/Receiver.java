package candoit.pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Receiver extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPut(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html; charset=UTF-8");

		BufferedReader bufferR;
		bufferR = req.getReader();
		
		String type = req.getContentType();
		String path = req.getContextPath();
		String pathInfo = req.getPathInfo();	// / 이하 패스 정보.
		
		StringBuffer sbf = new StringBuffer();
		String message = "";
		while((message = bufferR.readLine()) != null){
			System.out.println(message);
			sbf.append(message + "\n");
		}
		
		PrintWriter out = res.getWriter();
		out.print("pathInfo: "+ pathInfo+"\ntype : " + type + "\npath : " + path + "\n" + sbf.toString());
	}
}
