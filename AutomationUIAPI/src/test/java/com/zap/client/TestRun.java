package com.zap.client;
import org.zaproxy.clientapi.core.ClientApi;

public class TestRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientApi api=new ClientApi(null,0);
		//api.ascan();
		
		
	}

	
}

//public void clear() throws ProxyException {
//    try {
//        clientApi.ascan.removeAllScans(null); <-------------------HERE
//        clientApi.core.newSession(apiKey,"","");
//    } catch (ClientApiException e) {
//        e.printStackTrace();
//        throw new ProxyException(e);
//    }
//}

//
//public void connectToZAPProxy() {
//
//    String host = zapConf.getZapHost();
//    String port = zapConf.getZapPort();
//
//    API api = new API();
//    api.setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(host,
//Integer.parseInt(port))));
//
//    String failMsg ="connectToZAPProxy(): Failed to connect to ZAP on Host: " + host +" Port: " + port+ ". Make sure ZAP has been started on configured host:portspeifed in this error message.";
//    try {
//        if (scanningProxy == null) {
//            scanningProxy = new ZAProxyScanner(host, Integer.parseInt(port), zapConf.getZapAPIKey());
//            scanningProxy.clear(); // Start a new session
//
//            System.out.println("connectToZAPProxy(): scanningProxy.getLastScannerScanId()= " + scanningProxy.getLastScannerScanId());
//
//            zapSpider = (Spider) scanningProxy;
//
//            System.out.println("connectToZAPProxy(): scanningProxy.getLastSpiderScanId()= " + zapSpider.getLastSpiderScanId());
//
//            zapConf.setZapStarted(true);
//            Utilities.logInfoMessage("Created client to ZAP API");
//        } else {
//            Utilities
//                    .logInfoMessage("connectToZAPProxy(): ZAP connection is alreadyestablished.  "
//                            + "Therefore reusing existing connection.");
//        }
//
//    } catch (ProxyException e) {
//        if (zapConf.getZapForce()
//                && !zapForceStartedAlready
//                && (zapConf.getZapHost().toLowerCase().equals("localhost") || zapConf.getZapHost().toLowerCase()
//                        .equals(LOCAL_IP))) {
//            Utilities.logWarningMessage("connectToZAPProxy(): Forcing local ZAPinstacne to start at Host: " + host
//                    + " Port: " + port);
//            forceZAPConfiguredToStart();
//        } else {
//            Utilities.logSevereMessageThenFail(failMsg + "Stacktrace: " + Utilities.convertStackTraceToString(e));
//        }
//
//    } catch (IllegalArgumentException e) {
//        Utilities.logSevereMessageThenFail(failMsg + "Stacktrace: " + Utilities.convertStackTraceToString(e));
//        fail(failMsg);
//    }
//
//}

