package candoit.pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EsReader {

	private static EsReader instance;

	private EsReader() {
	}

	public static EsReader getInstance() {
		if (instance == null) {
			instance = new EsReader();
		}
		return instance;
	}
	
	/**
	 * POST 형식으로 Elasticsearch 명령 및 결과 전달.
	 * @param urls
	 * @param data
	 * @return
	 */
	public String getEsPost(String urls, String data) {
		StringBuffer res = new StringBuffer();
		try {

			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				res.append(output);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(res.toString());
		return res.toString();
	}

	/**
	 * GET 형식으로 Elasticsearch 명령 및 결과 전달.
	 * @param urls
	 * @return
	 */
	public String getEsGet(String urls, String method) {
		StringBuffer res = new StringBuffer();
		try {
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				res.append(output);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(res.toString());
		return res.toString();
	}
}
