package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tools.Extend;

/**
 * This calss is used to call https apis
 * 
 * @author Administrator
 */
public class Http extends HttpEntityEnclosingRequestBase {
	protected static int statusCode = -1;
	private static String serviceURL = "";
	private static String token = "";
	private static ArrayList<org.apache.http.message.BasicHeader> headerList = new ArrayList<org.apache.http.message.BasicHeader>();
	List<BasicNameValuePair> formParameters = new ArrayList<BasicNameValuePair>();
	public static final String METHOD_NAME = "DELETE";
	CloseableHttpResponse response = null;
	CloseableHttpClient httpclient = null;

	public String getMethod() {
		return METHOD_NAME;
	}

	public Http(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	public Http(final URI uri) {
		super();
		setURI(uri);
	}

	public Http() {
		super();
	}
	
	/**
	 * Usage: | setBaseUrl| baseUrl|
	 * @param baseUrl
	 */

	public static void setBaseUrl(String baseUrl) {
		serviceURL = baseUrl;
		Extend.verificationMessage("Setting service URL: " + serviceURL);
	}
	
	/**
	 * Usage: | setServiceURL| url|
	 * @param url
	 * @throws Exception
	 */

	public static void setServiceURL(String url) throws Exception {
		if (serviceURL.isEmpty()) {
			serviceURL = url;
		} else if (!serviceURL.endsWith("/") && !serviceURL.startsWith("/")) {
			serviceURL = serviceURL + "/" + url;
		} else {
			serviceURL = serviceURL + url;
		}
		Extend.verificationMessage("Setting service URL: " + serviceURL);
	}

	/**
	 * Get Status code
	 * Usage: | getStatusCode|
	 * @return
	 */
	public int getStatusCode() {
		// Log.Message(String.valueOf(this.statusCode), LogLevel.INFO);
		return this.statusCode;
	}

	/**
	 * This method adds the header values for rest api request
	 * 
	 * Usage: | addHeader| headername| headervalue|
	 * 
	 * @param name
	 *            - name of the attribute to be set
	 * @param value
	 *            - value of the attribute to be set
	 * @return nothing
	 * @throws Exception
	 */
	public void addHeader(String headername, String headervalue) {
		org.apache.http.message.BasicHeader header = new BasicHeader(
				headername, headervalue);
		headerList.add(header);
	}

	/**
	 * Set authorization token
	 * Usage: | setAuthorizationToken| authorizationToken|
	 * @param authorizationToken
	 */
	public void setAuthorizationToken(String authorizationToken) {
		token = authorizationToken;
	}

	public void addFormParameter(String name, String value) {
		formParameters.add(new BasicNameValuePair(name, value));
	}

	
	/**
	 * To get header value
	 * Usage: | getHeader| name|
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		String headerValue = "";
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			System.out.println("Header Name:" + header.getName() + " Header value: "
					+ header.getValue());
			
			if (header.getName().contains(name)) {
				headerValue = header.getValue();
				break;
			}
		}
		return headerValue;
	}

	/**
	 * Sends http get request to provided URL & returns the response.
	 * Usage: | getRequest| url|
	 * @param url
	 *            - URL to send get request.
	 * @return
	 * @throws Exception
	 */
	public static String getRequest(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		Extend.info("Sending 'GET' request to URL : " + url);
		Extend.info("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	/**
	 * Wait for the specified seconds
	 * 
	 * @param interget
	 *            value
	 * @throws InterruptedException
	 */
	public static void wait(int i) {
		Extend.info("Waiting for time [Http.java] : " + i + " seconds ");
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			Extend.error(e.getMessage());
		}
	}

	
	/**
	 * This method accepts input json and perform HTTP DELETE operation using
	 * the message body. It returns the output response.
	 * Usage: | deleteRequestWithBody| inputJson |
	 * @param inputJsonString
	 * @return result - output response.
	 * @throws Exception
	 */
	public String deleteRequestWithBody(String inputJsonString)
			throws Exception {
		Extend.info("Getting service path:" + serviceURL);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		Http httpDelete = new Http(serviceURL);
		StringEntity input = new StringEntity(inputJsonString,
				ContentType.APPLICATION_JSON);
		if (!token.isEmpty()) {
			Extend.info("Setting authorization token:" + token);
			httpDelete.setHeader("Authorization", token);
		}
		if (headerList != null) {
			for (org.apache.http.message.BasicHeader header : headerList) {
				httpDelete.setHeader(header);
			}
		}
		httpDelete.setEntity(input);
		CloseableHttpResponse response = httpclient.execute(httpDelete);
		String result = "";
		this.statusCode = response.getStatusLine().getStatusCode();
		if (response.getEntity() != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output = "";
			while ((output = br.readLine()) != null) {
				result = result + output;
			}
		}
		Extend.info("Status code for response is - " + this.statusCode);
		Extend.info("Returning result -  " + result);
		return result;
	}

	/**
	 * This method will use the httpsclient (buildHttpsClient) created to ignore
	 * the ssl handshakes & provide GET request functionality.
	 * Usage: | getRequestForHttps|
	 * @return jsonResponse
	 * @throws Exception
	 */
	public String getRequestForHttps() throws Exception {
		Extend.info("Getting service path:" + serviceURL);
		CloseableHttpClient httpclient = buildHttpsClient();
		HttpGet request = new HttpGet(serviceURL);
		CloseableHttpResponse response = httpclient.execute(request);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String result = "";
		String output = "";
		while ((output = br.readLine()) != null) {
			result = result + output;
		}
		this.statusCode = response.getStatusLine().getStatusCode();
		Extend.info("Status code for response is - " + this.statusCode);
		Extend.info("Returning result -  " + result);
		return result;
	}

	/**
	 * This method will create a httpsClient which will ignore certificate & it
	 * will not cause SSLHandshakeException while requesting to a https url.
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private CloseableHttpClient buildHttpsClient() {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				// @Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					return true;
				}
			});
			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build(),
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	/**
	 * This method gets the response for request types such as get, post, put,
	 * delete and head *
	 * Usage: | getResponse| mediaType| requestType| inputJson |
	 * @param mediaType
	 *            - Application/Json or
	 * @param requestType
	 *            - get, post, put, delete,head
	 * @param jsonString
	 *            - input string in Json format
	 * @param mediaType
	 * @param requestType
	 * @param inputString
	 * @return
	 * @throws Exception
	 */
	public String getResponse(String mediaType, String requestType,
			String inputString) throws Exception {
		if (null == httpclient) {
			httpclient = buildHttpsClient();
		}

		switch (requestType.toLowerCase()) {
		case "get":
			HttpGet getRequest = new HttpGet(Http.serviceURL);
			getRequest.setHeader("Content-Type", mediaType);

			if (!headerList.isEmpty()) {
				for (org.apache.http.message.BasicHeader header : headerList) {
					getRequest.setHeader(header);
				}
			}
			
			response = httpclient.execute(getRequest);
			break;

		case "put":
			HttpPut putRequest = new HttpPut(Http.serviceURL);
			putRequest.setHeader("Content-Type", mediaType);
			if (!inputString.isEmpty()) {
				StringEntity input = new StringEntity(inputString);
				putRequest.setEntity(input);
			}

			if (!headerList.isEmpty()) {
				for (org.apache.http.message.BasicHeader header : headerList) {
					putRequest.setHeader(header);
				}
			}
			Thread.sleep(2000);
			response = httpclient.execute(putRequest);
			//Extend.info(response.toString()); //commenting this line to avoid auth token visibility in reports
			break;

		case "post":
			HttpPost postRequest = new HttpPost(Http.serviceURL);
			if (!headerList.isEmpty()) {
				for (org.apache.http.message.BasicHeader header : headerList) {
					postRequest.setHeader(header);
				}
			}
			if (this.formParameters.isEmpty()) {
				postRequest.setHeader("Content-Type", mediaType);
				StringEntity input = new StringEntity(inputString);
				postRequest.setEntity(input);
			} else {
				postRequest.setEntity(new UrlEncodedFormEntity(
						this.formParameters, "UTF-8"));
			}
			response = httpclient.execute(postRequest);
			break;
		case "delete":
			HttpDelete deleteRequest = new HttpDelete(Http.serviceURL);
			deleteRequest.setHeader("Content-Type", mediaType);

			response = httpclient.execute(deleteRequest);
			break;
		}
		
		
		this.statusCode = response.getStatusLine().getStatusCode();
		
		
		
		String result = "";
		String output = "";
		
		if(this.statusCode == 204) {
			result = "No Content";
		} 
		
		else {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			
			while ((output = br.readLine()) != null) {
				result = result + output;
			}
			

		}
		  
		
		  //Closing http request
		  Extend.info("Request => "+inputString);
		  Extend.info("Response => "+result);
	      return result;
	}
	
	/** 
	 * To get the Trimmed json from the given String
     * Usage: | getJsonTrim| inputString|
	 * @param String 
	 * @return returns string data
	 */
	public static String getJsonTrim(String inputString) {
		return inputString.trim();
	}
	
	/**
	 * To upload the file via API
     * Usage: | getJsonTrim| inputString|
	 * @param filepath
	 * @param attachableId
	 * @param clientId
	 * @param attachableType
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(String filepath, String attachableId, String clientId, String attachableType)
			throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("attachable_id", attachableId).addFormDataPart("client_id", clientId)
				.addFormDataPart("attachable_type", attachableType).addFormDataPart("attachment", filepath,
						RequestBody.create(MediaType.parse("application/octet-stream"), new File(filepath)))
				.build();
		Request request = new Request.Builder().url(Http.serviceURL).method("POST", body)
				.addHeader("Authorization", headerList.get(headerList.size()-1).getValue()).build();
		Response response = client.newCall(request).execute();
		statusCode = response.code();

		return response.body().string();
	}
	
	public static void main (String[] args) throws Exception {		
		setBaseUrl("https://consignee.pandostaging.in/api/login");
		Http http = new Http();
		System.out.println(http.getResponse("Application/Json", "put", "{\"data\":{\"email\":\"pandoconsignee+1202109061241rG@gmail.com\",\"password\":\"12345\"}}"));
		String token = http.getHeader("x-auth-token");
		System.out.println(token);
		setBaseUrl("https://consignee.pandostaging.in/api/attachment");
		http.addHeader("Authorization",token);
		System.out.println(uploadFile("C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\testAttachment\\images\\lr1202109061241rG.png", "6135bf65e96e92004d465527", "600e60da8627813585251244", "order"));
	}
}
