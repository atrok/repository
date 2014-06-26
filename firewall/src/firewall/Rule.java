package firewall;

import java.util.HashMap;

public class Rule {
	private String Action;
	private String SourceIP;
	private String DestIP;
	private String Prot;
	private String SourcePort;
	private String DestPort;
	private String Edit;
	private String Remove;
	
	private HashMap<String,String> map=new HashMap<String,String>();
	private int i;

	public Rule() {
		
		setAction("")
		.setDestIP("")
		.setDestPort("")
		.setProt("")
		.setSourceIP("")
		.setSourcePort("");
		
		
	}

	public Rule setAction(String str) {
		Action = str;
		map.put("pass", str);
		return this;
	}

	public Rule setSourceIP(String str) {
		SourceIP = str;
		map.put("sourceip", str);
		return this;
	}

	public Rule setDestIP(String str) {
		DestIP = str;
		map.put("destip", str);
		return this;
	}

	public Rule setProt(String str) {
		Prot = str;
		map.put("proto", str);
		return this;
	}

	public Rule setSourcePort(String str) {
		SourcePort = str;
		map.put("sourceport",str);
		return this;
	}

	public Rule setDestPort(String str) {
		DestPort = str;
		map.put("destport", str);
		return this;
	}

	public String getAction() {
		return Action;
	}

	public String getSourceIP() {
		return SourceIP;
	}

	public String getDestIP() {
		return DestIP;
	}

	public String getProt() {
		return Prot;
	}

	public String getSourcePort() {
		return SourcePort;
	}

	public String getDestPort() {
		return DestPort;
	}

	public void add(String key,String str) {
		
		if (str.length()!=0){
		
		switch (key) {

		case "Action":
			setAction(str);
			break;
		case "Source IP":
			setSourceIP(str);
			break;
		case "Dest IP":
			setDestIP(str);
			break;
		case "Prot":
			setProt(str);
		case "Source Port":
			setSourcePort(str);
			break;
		case "Dest Port":
			setDestPort(str);
			break;
		case "Edit":
			setEdit(str);
			break;
		case "Delete":
			setRemove(str);
		default:
			break;
		}
		
		}

	}

	public String getEdit() {
		return Edit;
	}

	public Rule setEdit(String edit) {
		Edit = edit;
		map.put(edit,"Edit");
		return this;
	}

	public String getRemove() {
		return Remove;
	}

	public void setRemove(String remove) {
		Remove = remove;
		map.put(remove, "Delete");
	}

	public void dropRemove(){
		map.remove(Remove);
	}
	
	public void dropEdit(){
		map.remove(Edit);
	}
	
	public String toString() {
		
		return map.toString();
	}
	
	public HashMap<String,String> getMap(){
		return map;
		
	}
	
	public void addNewFieldToRule(String key,String value){
		map.put(key, value);
	}
	
	public String getValuebyKey(String key){
		return map.get(key);
	}
	
	public void putAll(HashMap<String,String> m){
		map.putAll(m);
	}
	
	public boolean isEmpty(){
		
		for (String str: map.values())
			if (str.length()!=0)
			return false;
		return true;
	}
}
