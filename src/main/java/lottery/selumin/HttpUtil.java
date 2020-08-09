package lottery.selumin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	public static File log = new File(Constant.LOG_PATH + LocalDate.now().toString() + "后一");

    public static JSONObject httpGet(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        ;
        return JSON.parseObject(buf.toString().trim());
    }

    public static String httpGet1(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String httpGet2(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String httpPut(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        int code = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return (buf.toString().trim()) != null ? (buf.toString().trim()) : (code + "");
    }

    public static JSONObject httpPost(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return JSON.parseObject(buf.toString().trim());
    }

    public static JSONObject httpPost(String urlStr, String postText) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        byte[] bytes = postText.getBytes("utf-8");
        OutputStream os = conn.getOutputStream();
        os.write(bytes);
        os.close();
        //
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            //
            inputLine = in.readLine();
        }
        in.close();
        return JSON.parseObject(buf.toString().trim());
    }

	public static String doGet(String url, Map<String, String> params) throws  Exception
			 {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}
		return httpGet2(uriBuilder.build().toString());
	}

    public static String httpPostApplication(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuffer buf = new StringBuffer();
        String inputLine = in.readLine();
        while (inputLine != null) {
            buf.append(inputLine).append("\r\n");
            inputLine = in.readLine();
        }
        in.close();
        //
        return buf.toString().trim();
    }

    public static String RestfulGet(String url) {
        String returnString = "";
        try {
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpConnection.setRequestProperty("contentType", "utf-8");
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException(
                        "HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream()), "utf-8"));
            String output = responseBuffer.readLine();
            // System.out.println("Output from Server: \n");
            StringBuffer buf = new StringBuffer();
            while (output != null) {
                buf.append(output).append("\r\n");
                output = responseBuffer.readLine();
            }
            responseBuffer.close();
            httpConnection.disconnect();
            returnString = buf.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static String RestfulPost(String url, String input) {
        String result = "";
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Charsert", "UTF-8");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream()), "UTF-8"));
            String output;
            System.out.println("Output from Server:\n");
            StringBuffer buf = new StringBuffer();
            while ((output = responseBuffer.readLine()) != null) {
                buf.append(output).append("\r\n");
            }
            httpConnection.disconnect();
            responseBuffer.close();
            result = buf.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

}