package firewall;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.nodes.Document;

public abstract class Page {
		protected HashMap<String,String> options;
		protected String url;	
		
		public Page(String url, HashMap options){
			this.url=url;
			this.options=options;
			
		}
		
		public String getUrl(){return url;}
		public HashMap getOptions(){return options;}
		
		public Document run(Firewall fw){
			updateOptions();
			try{
				return fw.execute(url,options);
				}catch(IOException e){
					e.printStackTrace();
					
				}
				return null;
		}
		abstract void updateOptions();
		
}

class LoginPage extends Page{
	
	public LoginPage(String url, HashMap<String,String> options) {
		super(url, options);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unused")
	void updateOptions(){};

}

class PacketFilterPageAdd extends Page{
	
	public PacketFilterPageAdd(String url, HashMap<String,String> options) {
		super(url, options);
		// TODO Auto-generated constructor stub
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put("Add", "Add");
	}
}

class PacketFilterPageDelete extends Page{
	
	public PacketFilterPageDelete(String url, HashMap<String,String> options) {
		super(url, options);
		// TODO Auto-generated constructor stub
		
		options.put("enfilter","on");
		options.put("pass","pass");
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		//options.put("Delete", "Delete");
	}
}

class PacketFilterPageChange extends Page{
	
	public PacketFilterPageChange(String url, HashMap<String,String> options) {
		super(url, options);
		// TODO Auto-generated constructor stub
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put("Change", "Change");
	}
}