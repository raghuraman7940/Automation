package com.zap.client;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.clientapi.core.ClientApiMain;
import org.zaproxy.clientapi.gen.Spider;

public class ZapTools {
	
	String ZAP_LOCATION = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\";
	String JAVA_LOCATION = "C:\\Program Files (x86)\\Common Files\\Oracle\\Java\\javapath\\java.exe";
	//String ZAP_LOCATION = "D:\\";
	String SAVE_SESSION_DIRECTORY = "ZAPSessions\\";
	
//	String zapExecutableLocation = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\ZAP.exe";
//	String zapWorkingDirectory = "C:\\Program Files\\OWASP\\Zed Attack Proxy";
//	
	public boolean startZAP() {
		try {
			String[] command = { this.JAVA_LOCATION, "-jar", this.ZAP_LOCATION+"zap-2.7.0.jar","-daemon"};
			ProcessBuilder proc = new ProcessBuilder(command);
			proc.directory(new File(this.ZAP_LOCATION));
			Process p = proc.start();
			//p.waitFor();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			OutputStreamWriter oStream = new OutputStreamWriter(
					p.getOutputStream());
			oStream.write("process where name='ZAP.exe'");
			oStream.flush();
			oStream.close();
			String line;
			while ((line = input.readLine()) != null) {
				//kludge to tell when ZAP is started and ready
				if (line.contains("INFO") && line.contains("org.zaproxy.zap.DaemonBootstrap") && line.contains("ZAP is now listening")) {
					input.close();
					break;
				}
			}
			System.out.println("ZAP has started successfully.");
			return true;
		} catch (Exception ex) {
			System.out.println("ZAP was unable to start.");
			ex.printStackTrace();
			return false;
		}
	}
	
	public void stopZAP(String zapaddr, int zapport,String zapapikey) {
		ClientApiMain.main(new String[] { "stop", "zapaddr=" + zapaddr,	"zapport=" + zapport,"apikey="+zapapikey });
	}

	public void startSession(String zapaddr, int zapport,String zapapikey) {
		ClientApiMain.main(new String[] { "newSession", "zapaddr=" + zapaddr, "zapport=" + zapport,"apikey="+zapapikey });
		System.out.println( "session started" );
		System.out.println("Session started successfully.");
	}

	public void saveSession(ClientApi api, String fileName) {
		try {
			String path = this.SAVE_SESSION_DIRECTORY + fileName + ".session";
			api.core.saveSession( path, "true");
			System.out.println( "Session save successful (" + path + ")." );
		} catch (ClientApiException ex) {
			System.out.println( "Error saving session." );
			ex.printStackTrace();
		}
	}

	public boolean ascan(ClientApi api, String ZAP_URI_PORT) throws InterruptedException {
		try {
			 int progress;
			   String scanid;
			System.out.println("Active scan starting...");
			//api.ascan.scan(ZAP_URI_PORT, null, null);
			 ApiResponse resp =api.ascan.scan(ZAP_URI_PORT, "True", "False", null, null, null);
			 //The scan now returns a scan id to support concurrent scanning
	         scanid = ((ApiResponseElement) resp).getValue();
            // Poll the status until it completes
            while (true) {
	                Thread.sleep(5000);
	                progress =
	                        Integer.parseInt(
	                                ((ApiResponseElement) api.ascan.status(scanid)).getValue());
	                System.out.println("Active Scan progress : " + progress + "%");
	                if (progress >= 100) {
	                    break;
	                }
	            }
	            System.out.println("Active Scan complete");

	            System.out.println("Alerts:");
	            System.out.println(new String(api.core.xmlreport()));
//	            
//			//kludge to see when scan is done - Currently am not sure how to work with the ApiRepsonse Object
//			while (api.ascan.status().toString(0).contains("100") == false) {
//				System.out.println("active scan progress: "	+ api.ascan.status().toString(0));
//				try {
//					Thread.sleep(15000); //basically printing status every 15 seconds
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			//System.out.println("progress: " + api.ascan.status().toString(0));
	            
			return true;
		} catch (ClientApiException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean spider(ClientApi api, String ZAP_URI_PORT) throws InterruptedException {
		try{
			System.out.println("Spider scan starting...");
			
			  
            ApiResponse resp = api.spider.scan(ZAP_URI_PORT, null, null, null, null);
            String scanid;
            int progress;

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();

            // Poll the status until it completes
            while (true) {
                Thread.sleep(1000);
                progress =
                        Integer.parseInt(
                                ((ApiResponseElement) api.spider.status(scanid)).getValue());
                System.out.println("Spider progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            System.out.println("Spider complete");

//            
//			Spider spider = new Spider( api );
//			spider.scan( ZAP_URI_PORT );
//			//kludge to see when spider has completed - currently am not sure how to use the ApiResponse Object
//			while (spider.status().toString(0).contains("100") == false) {
//				System.out.println("progress: "	+ spider.status().toString(0));
//				try {
//					Thread.sleep(5000); //basically printing status every 5 seconds
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			System.out.println("progress: " + spider.status().toString(0));
//			
			return true;
		} catch(ClientApiException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public String checkErrors(ClientApi api) {
		String errors = "";
		List<Alert> ignoreAlerts = new ArrayList<>(2);
		//ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null, Risk.Low, Reliability.Warning, null, null) {});
		//ignoreAlerts.add(new Alert(null, null, Risk.Low, Reliability.Warning, null, null));
		//ignoreAlerts.add(new Alert(null, null, Risk.Informational, Reliability.Warning, null, null));
		try {
			System.out.println("Checking Alerts...");
			api.checkAlerts(ignoreAlerts, null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			errors = ex.getMessage();
		}
		return errors;
	}
}