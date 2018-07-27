package com.zap.client;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;



public class testfire {
    private static final String ZAP_ADDRESS = "localhost";
    private static final int ZAP_PORT = 8080;
    private static final String ZAP_API_KEY ="fn4ism7pac59tdfac434mvmpao"; // Change this if you have set the apikey in ZAP via Options / API
    public static ClientApi api;

 public static void main(String[] args) throws Exception {

	 
	 ZapTools zapa=new ZapTools();
	//zapa.startZAP();
		boolean reult=   CheckIfZAPHasStartedOrNot(ZAP_ADDRESS, ZAP_PORT);
		System.out.println("reult: "+reult);
//		
 zapa.stopZAP(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
////	 
//	 zapa.startSession(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
//	 Thread.sleep(9000);
//	 
	
	   



 //OpenZAP();
//OpenZAP2();
 //OpenZAP3();
 //OpenZAP3_2();
//api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

	
 //OpenZAP4();
 
 }
 
 private static void OpenZAP() throws IOException, InterruptedException
 {
 String zapExecutableLocation = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\ZAP.exe";
 String zapWorkingDirectory = "C:\\Program Files\\OWASP\\Zed Attack Proxy";
 Process p = Runtime.getRuntime().exec(zapExecutableLocation, null, new File(zapWorkingDirectory));
 p.waitFor();
 //CheckIfZAPHasStartedOrNot();
 System.out.println("Going to sleep for some time to simulate polling to see if ZAP has started");
 Thread.sleep(50000);
 }
 
 private static void OpenZAP2() throws IOException, InterruptedException
 {
 String zapExecutableLocation = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\ZAP.bat";
 String zapWorkingDirectory = "C:\\Program Files\\OWASP\\Zed Attack Proxy";
 String[] command = { "CMD", "/C", zapExecutableLocation };
 
 System.out.println("Command :"+command);
 ProcessBuilder pb = new ProcessBuilder(command);
 pb.directory(new File(zapWorkingDirectory).getAbsoluteFile());
 Process p = pb.start();
 //p.waitFor();
 //CheckIfZAPHasStartedOrNot();
 System.out.println("Going to sleep for some time to simulate polling to see if ZAP has started");
 Thread.sleep(50000); 
 }
 
 private static void OpenZAP3() throws IOException, InterruptedException
 {
 String zapExecutableLocation = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\ZAP.exe";
 String zapWorkingDirectory = "C:\\Program Files\\OWASP\\Zed Attack Proxy";
 ProcessBuilder pb = new ProcessBuilder(zapExecutableLocation);
 pb.directory(new File(zapWorkingDirectory).getAbsoluteFile());
 Process p = pb.start();
 p.waitFor();
 //CheckIfZAPHasStartedOrNot();
 System.out.println("Going to sleep for some time to simulate polling to see if ZAP has started");
 Thread.sleep(50000); 
 }
	private static void OpenZAP3_2() throws  IOException, InterruptedException, ClientApiException
	{
		String zapExecutableLocation = "C:\\Program Files\\OWASP\\Zed Attack Proxy\\ZAP.exe";
		String zapWorkingDirectory = "C:\\Program Files\\OWASP\\Zed Attack Proxy";
		ProcessBuilder pb = new ProcessBuilder(zapExecutableLocation);
		pb.directory(new File(zapWorkingDirectory).getAbsoluteFile());
		pb.redirectErrorStream(true);
		pb.redirectOutput(new File("OWASPZAPOutputStream.txt"));
		System.out.println("Trying to invoke the ZAP executable");
		Process p = pb.start();
		//CheckIfZAPHasStartedOrNot();
		System.out.println("Waiting for successful connection to ZAP");
		
		api.waitForSuccessfulConnectionToZap(60000, 1000);
//		waitForSuccessfulConnectionToZap(60000, 1000); // from ClientApi class of java client api
		System.out.println("Seems like we can connect to ZAP APIs now");
	}
 
 private static void OpenZAP4() throws InterruptedException
 {
// ZAPStarter zap = new ZAPStarter();
 System.out.println("Created ZAPStarter");
 //Thread t = new Thread(zap);
 System.out.println("Created Thread");
// t.start();
 System.out.println("Started Thread");
 //CheckIfZAPHasStartedOrNot();
 Thread.sleep(50000);
 System.out.println("Slept for 50 s");
 }
 
 private static boolean CheckIfZAPHasStartedOrNot(String ZAP_ADDRESS, int ZAP_PORT) throws IOException, InterruptedException {
 String zapApiUrl = "http://"+ZAP_ADDRESS+":"+ZAP_PORT;
 System.out.println("zapApiUrl :"+zapApiUrl);
 URL url = new URL(zapApiUrl);
 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
 connection.setRequestMethod("GET");
 int numberOfRetries = 0;
 boolean result=false;


 while (numberOfRetries <= 2) {
 try {
 BufferedReader in = new BufferedReader(new InputStreamReader(
 connection.getInputStream()));
 String inputLine;
 StringBuffer response = new StringBuffer();


 while ((inputLine = in.readLine()) != null) {
 response.append(inputLine);
 }
 in.close();
 System.out.println(response.toString());
 
 System.out.println("Response received from the API endpoint. ZAP should be up by now");
 result=true;
 break;
 } catch (ConnectException e) {
 System.out.println("No response received from the API endpoint. Seems like ZAP has not started yet, let's keep polling");
 if(numberOfRetries >= 2)
 {
 System.out.println("Tried " + numberOfRetries + " of times, couldn't get a response from the ZAP API endpoint");
 }
 continue;
 } finally {
 ++numberOfRetries;
 Thread.sleep(5000);
 }
 }
 return result;
 }
 
}
