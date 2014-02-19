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
		
		String url = "";
		StringBuffer data = new StringBuffer();
		BufferedReader bufferR;
		bufferR = req.getReader();

		EsReader esr = EsReader.getInstance();

		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString == null) {
			url = requestURL.toString().toLowerCase();
		} else {
			url = requestURL.append('?').append(queryString).toString();
		}
		
		url = url.replaceAll("candoitsoft.kr:9600", "localhost:9200");
		
		String message = "";
		while ((message = bufferR.readLine()) != null) {
			data.append(message);
		}

		PrintWriter out = res.getWriter();
		out.print(esr.getEsGet(url, req.getMethod()));
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		String url = "";
		StringBuffer data = new StringBuffer();
		BufferedReader bufferR;
		bufferR = req.getReader();

		EsReader esr = EsReader.getInstance();

		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString == null) {
			url = requestURL.toString().toLowerCase();
		} else {
			url = requestURL.append('?').append(queryString).toString();
		}
		
		url = url.replaceAll("candoitsoft.kr:9600", "localhost:9200");
		
		String message = "";
		while ((message = bufferR.readLine()) != null) {
			data.append(message);
		}
		
		PrintWriter out = res.getWriter();
		out.print(esr.getEsPost(url, data.toString()));
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String url = "";
		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString == null) {
			url = requestURL.toString();
		} else {
			url = requestURL.append('?').append(queryString).toString();
		}
		System.out.println("url (" + req.getMethod() + "): " + url);

		res.setContentType("application/json; charset=UTF-8");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Content-Length");
		
		PrintWriter out = res.getWriter();
		
		if (req.getMethod().equals("PUT")) {
			out.println("PUT 명령은 허가된 사용자만 가능합니다.");
		} else if (req.getMethod().equals("DELETE")) {
			out.println("DELETE 명령은 허가된 사용자만 가능합니다.");
		} else if (req.getMethod().equals("POST")) {
			this.doPost(req, res);
		} else {
			this.doGet(req, res);
		} 
	}

}
