package com.jpii.navalbattle.io;

import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jpii.navalbattle.NavalBattle;
import com.jpii.navalbattle.data.Constants;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class BroadcastService {
	
	/* Items from client */
	private String announcementId;
	private BroadcastThread broadcastThread;
	
	/* Items from update.xml */
	private String versionCode, versionReadable, updateUrl;
	private String announcementCode, announcementTitle, announcementText, announcementUrl;
	
	public BroadcastService() {
		announcementId = NavalBattleIO.getAttribute("announcementId");
		broadcastThread = new BroadcastThread();
		
		NavalBattle.getDebugWindow().printInfo("Checking for announcements...");
		broadcastThread.run();
	}
	
	public String getVersionCode() {
		return versionCode;
	}
	
	public String getVersionReadable() {
		return versionReadable;
	}
	
	public String getUpdateUrl() {
		return updateUrl;
	}
	
	public String getAnnouncementCode() {
		return announcementCode;
	}
	
	public String getAnnouncementTitle() {
		return announcementTitle;
	}
	
	public String getAnnouncementText() {
		return announcementText;
	}
	
	public String getAnnouncementUrl() {
		return announcementUrl;
	}
	
	class BroadcastThread extends Thread {
		
		public BroadcastThread() {

		}

		@Override
		public void run() {
			parseXml();
			
			checkForUpdates();
			checkForAnnouncement();
		}
		
		private void parseXml() {			
			DOMParser parser = new DOMParser();

	        try {
	            parser.parse(new InputSource(new URL(Constants.NAVALBATTLE_UPDATE_URL).openStream()));
	            Document doc = parser.getDocument();

	            NodeList nodeList = doc.getElementsByTagName("string");
	            for (int i = 0; i < nodeList.getLength(); i++) {
	                System.out.print("Item "+(i+1));
	                
	                Node n = nodeList.item(i);
	                NamedNodeMap m = n.getAttributes();
	                Node actualNode = n.getFirstChild();
	               
	                if (actualNode != null) {
	                	if(m.getNamedItem("name").getTextContent().equals("version_code")) {
	                		versionCode = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("version_readable")) {
	                		versionReadable = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("update_url")) {
	                		updateUrl = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("announcement_code")) {
	                		announcementCode = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("announcement_title")) {
	                		announcementTitle = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("announcement_text")) {
	                		announcementText = actualNode.getNodeValue();
	                	}
	                	
	                	if(m.getNamedItem("name").getTextContent().equals("announcement_url")) {
	                		announcementUrl = actualNode.getNodeValue();
	                	}
	                }
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			
			NavalBattle.getDebugWindow().printInfo("Successfully loaded BroadcastService data");
		}
		
		private void checkForUpdates() {
			int clientVersion = Integer.parseInt(Constants.VERSION_CODE);
			int latestVersion = Integer.parseInt(versionCode);
			
			if(clientVersion < latestVersion) {
				NavalBattle.getDebugWindow().printWarning("Update found! " + versionReadable + " (" + versionCode + ")");
				NavalBattle.getDebugWindow().printWarning("Update url: " + updateUrl);
			} else {
				NavalBattle.getDebugWindow().printInfo("You are running the latest version!");
			}
		}
		
		private void checkForAnnouncement() {
			
		}
	}
}
