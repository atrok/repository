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

	public void add(String str) {
		if (str.length()!=0){
		++i;
		switch (i) {

		case 1:
			setAction(str);
			break;
		case 2:
			setSourceIP(str);
			break;
		case 3:
			setDestIP(str);
			break;
		case 4:
			setSourcePort(str);
			break;
		case 5:
			setDestPort(str);
			break;
		case 6:
			setEdit(str);
			break;
		case 7:
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
		return Action + " " + SourceIP + " " + DestIP + " " + Prot + " "
				+ SourcePort + " " + DestPort+" "+Edit+" "+Remove;
	}
	
	public HashMap<String,String> getMap(){
		return map;
		
	}

}
