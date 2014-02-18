package candoit.pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Receiver extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		
		boolean isGet = true;
		String url = "";
		StringBuffer data = new StringBuffer();
		BufferedReader bufferR;
		bufferR = req.getReader();
		
		EsReader esr = EsReader.getInstance();
		
		StringBuffer requestURL = req.getRequestURL();
	    String queryString = req.getQueryString();
		if (queryString == null) {
			url = requestURL.toString();
	    } else {
	    	url = requestURL.append('?').append(queryString).toString();
	    }
		url = url.replaceAll("candoitsoft.kr:9600","localhost:9200");
//		url = url.replaceAll("localhost:8080/Candlay","localhost:9200");
		System.out.println("url : "+url);
		String message = "";
		while((message = bufferR.readLine()) != null){
			System.out.println(message);
			data.append(message + "\n");
			isGet = false;
		}
		
		PrintWriter out = res.getWriter();
		if(isGet){
			out.print(esr.getEsGet(url));
		}else {
			out.print(esr.getEsPost(url, data.toString()));
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		this.doGet(req, res);
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		out.println("PUT 명령은 허가된 사용자만 가능합니다.");
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		out.println("DELETE 명령은 허가된 사용자만 가능합니다.");
	}
}
