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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class Firewall {

	/**
	 * @param args
	 */
	    // global constants and variables
	    private static final int PORT = 80;     // server details
	    private static final String HOST = "http://192.168.1.254";
	    private static final String PacketFilterUrl="/cgi-bin/packetfilter.ha";
	    private static final String LoginUrl="/cgi-bin/login.ha";
	    
	    
        private static final String deviceCode = "3287024592";
        
        
        private static final String LOGIN="Login";
        private static final String PACKETFILTER="Packet Filter";
        
        HashMap<String,String> PacketFilterInputsAdd=new HashMap<String,String>();
        HashMap<String,String> PacketFilterInputsChange=new HashMap<String,String>();
        HashMap<String,String> PacketFilterInputsDelete=new HashMap<String,String>();
        HashMap<String,String> LoginInputsAdd=new HashMap<String,String>();
        LinkedList<Page> PacketFilterActionsQueue=new LinkedList<Page>();
		
    //    private static final name="pass" value="drop" />
        String errormessage="";
        String title="";        
    
	    private Document cachedPage;
	    
		// TODO Auto-generated method stub
		public static void main(String[] args) {
			try{
				new Firewall();
			}catch(Exception e){
				e.printStackTrace();
			}
		}


		public Firewall ()  throws Exception{
			
			//String urltogo=PacketFilterUrl;
			//PacketFilterInputsAdd.put("nonce","");
			PacketFilterInputsAdd.put("enfilter","on");
			PacketFilterInputsAdd.put("pass","drop");
			PacketFilterInputsAdd.put("sourceip","0.0.0.0-255.255.255.255");
			PacketFilterInputsAdd.put("destip","192.168.1.72");
			PacketFilterInputsAdd.put("sourceport","");
			PacketFilterInputsAdd.put("destport","");
			PacketFilterInputsAdd.put("proto","udp");
			
			
			LoginInputsAdd.put("password",deviceCode);
			
			PacketFilterPageDelete PacketFilterPageDelete=new PacketFilterPageDelete(HOST+PacketFilterUrl, PacketFilterInputsAdd);
			PacketFilterActionsQueue.add(PacketFilterPageDelete);
			
			/*
			Elements els=doc.select("input");
			for (Element el: els){
				PacketFilterInputsAdd.put(el.attr("name"),el.attr("value"));
				System.out.println(el.attr("name")+" : "+el.attr("value"));
			}
			*/

			while(!PacketFilterActionsQueue.isEmpty()){
				cachedPage=PacketFilterActionsQueue.pop()
				.run(this);
			}
	    }
		
		public Document execute(String urltogo, HashMap<String,String> options) throws IOException{
			if (null!=cachedPage){
				
				String nonce=cachedPage.select("input[name*=nonce]").attr("value");
				options.put("nonce",nonce);
			}
			
			System.out.println(urltogo);
			System.out.println(options.toString());
			
			Document doc=Jsoup.connect(urltogo)
			.data(options)
			.post();

			

			String action=doc.select("form").attr("action");
			System.out.print(action);
			
			Elements rules=doc.select("table.grid tr");
			
			HashMap<Integer,LinkedList<String>> rulesMap=new HashMap<Integer,LinkedList<String>>();
			int i=0;
			for (Element el:rules){
				i++;
				LinkedList<String> list=new LinkedList<String>();
				String str="";
				Elements td=el.select("td");
				for (Element e:td){
					if (e.select("input").hasAttr("type")){
						list.add(e.select("input").attr("name"));
					}else{
						list.add(e.html().toString());
					}
					str=str+e.html().toString();
				}
				rulesMap.put(i, list);
				System.out.println(str);
			}

			
						
			//System.out.print(doc.html().toString());
			
			title=doc.select("title").text();
			
			switch(title){
				
			case LOGIN:PacketFilterActionsQueue.addFirst(new LoginPage(HOST+LoginUrl, LoginInputsAdd));
				break;
			case PACKETFILTER: 
				errormessage=doc.select("div#error-message-text").text();
				System.out.println(errormessage);
				if (errormessage.length()==0)
				{if (rules.isEmpty()){
					
					PacketFilterActionsQueue.addFirst(new PacketFilterPageAdd(HOST+PacketFilterUrl, PacketFilterInputsAdd));
				}else{
					if (PacketFilterActionsQueue.isEmpty()){
						String str="";
						HashMap<String,String> temp=new HashMap<String,String>();
						for (Element el: rules){
							//int i1=0;
							Elements td=el.select("td");
							if (!td.isEmpty()){
							for (Element e:td){
								//++i1;
								if (e.select("input").hasAttr("type")){
									str=e.select("input").attr("name").toString();
								}else{
									str=e.html().toString();
								}
								

								
							 if (str.contains("Remove")){
								 temp.put(str,"Delete");
								 }
							}
							 
							 
						
							PacketFilterActionsQueue.add(new PacketFilterPageDelete(HOST+PacketFilterUrl,temp));
							break;
						}
							}
					}
						
				}
					
				}
					break;
			}
			
			cachedPage=doc;
			return doc;
		}
		
}
