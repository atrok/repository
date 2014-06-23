package firewall;

public class URLOptions {
	private String url;
	private String data;
	
	public URLOptions(){}
	
	public void setHost(String url){
		this.url=url;
		
	}
	
	public void setData (String data){
		this.data=data;
	}
	public String getHost(){
		return url;
	}
	
	public String getData(){
		return data;
	}

}
