package firewall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Firewall {

	/**
	 * @param args
	 */
	    // global constants and variables
	    private static final int PORT = 80;     // server details
	    private static final String HOST = "http://192.168.1.254/cgi-bin/packetfilter.ha";
        private static final String deviceCode = "3287024592";
        
	    private Socket sock;
	    private BufferedReader in;     // i/o for the client
	    private PrintWriter out;
	    
		// TODO Auto-generated method stub
		public static void main(String[] args) throws Exception {
			getFluent();

	    }
		
		
		public static void getOldWay() throws Exception{
			
	        URL oracle = new URL(HOST);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
		}
		
		public static void postRequest() throws Exception{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("nonce", "58aa5c4d00274767cd071dd381dc479d587c3c6dddca0427"));
			formparams.add(new BasicNameValuePair("password", deviceCode));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			HttpPost httppost = new HttpPost(HOST);
			httppost.setEntity(entity);
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				 HttpEntity entity_response = response.getEntity();
				 if (entity_response != null) {
				 long len = entity_response.getContentLength();
				 if (len != -1 && len < 2048) {
				 System.out.println(EntityUtils.toString(entity));
				 } else {
				 // Stream content out
					 
					 BufferedReader in = new BufferedReader(
						        new InputStreamReader(entity_response.getContent()));

						        String inputLine;
						        while ((inputLine = in.readLine()) != null)
						            System.out.println(inputLine);
						        in.close();
				 }
				 }
				} finally {
				 response.close();
				}

			
		}
		
		public static void getRequest() throws Exception {
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(HOST);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
			 HttpEntity entity = response.getEntity();
			 if (entity != null) {
			 InputStream instream = entity.getContent();
			 try {
			 // do something useful
				 BufferedReader in = new BufferedReader(
					        new InputStreamReader(instream));
				 String inputLine;
			        while ((inputLine = in.readLine()) != null)
			            System.out.println(inputLine);
			        in.close();
			 } finally {
			 instream.close();
			 }
			 }
			} finally {
			 response.close();
			}

		}
		
		public static void getFluent() throws Exception{
			
			Document result = Request.Get(HOST)
					 .execute().handleResponse(new ResponseHandler<Document>() {
					 @SuppressWarnings("deprecation")
						public Document handleResponse(final HttpResponse response) throws IOException {
						 StatusLine statusLine = response.getStatusLine();
						 HttpEntity entity = response.getEntity();
						 if (statusLine.getStatusCode() >= 300) {
							 throw new HttpResponseException(
								 statusLine.getStatusCode(),
								 statusLine.getReasonPhrase());
						 	}
						 if (entity == null) {
							 throw new ClientProtocolException("Response contains no content");
						 	}
						 DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
						 try {
							 DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
							 ContentType contentType = ContentType.getOrDefault(entity);
							/* if (!contentType.equals(ContentType.APPLICATION_XML)) {
								 throw new ClientProtocolException("Unexpected content type:" +
										 	contentType);
							 	}
							 	*/
							 String charset = contentType.getCharset().toString();
							 if (charset == null) {
								 	charset = HTTP.DEFAULT_CONTENT_CHARSET;
							 }
							 return docBuilder.parse(entity.getContent(), charset);
						 	} catch (ParserConfigurationException ex) {
						 			throw new IllegalStateException(ex);
						 	} catch (SAXException ex) {
						 		throw new ClientProtocolException("Malformed XML document", ex);
						 	}
						 }
						 });

		}

}
