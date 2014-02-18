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
		
		String message = "";
		while((message = bufferR.readLine()) != null){
			data.append(message + "\n");
		}
		
		PrintWriter out = res.getWriter();
		out.print(esr.getEsReq(url, data.toString()));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		this.doGet(req, res);
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
	}
}
