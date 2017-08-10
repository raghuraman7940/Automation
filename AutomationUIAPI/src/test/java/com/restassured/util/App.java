package com.restassured.util;

//import static com.restassured.jira.DriverScript.CONFIG;
//import static com.restassured.bdd.CommonSteps.CONFIG;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static io.restassured.matcher.RestAssuredMatchers.matchesDtd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.pearson.util.Xls_Reader;
import com.restassured.util.APIConstants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App 

{
	
	static ValidationUtils vjson;
	
	// properties
	public static Properties CONFIG;
	
	static CloseableHttpClient httpclient;
	static HttpClientContext context;
  	static Xls_Reader current_TestCase_xls=null;
  	static Xls_Reader XlsxSuite_xls=null;
  	static ProxySpecification proxySpecification;
  	public static String ApiDescription = "";
  	public static String APIStatus="";
  	static Reporting Rep;

  	
  	public App() throws IOException
  	{
  		
  		// Initialize configuration file
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
		
  	 	//Create instance
  			Rep=new Reporting();
  			vjson=new ValidationUtils();
  			
  		//Proxy host details
	    HttpHost proxy = new HttpHost(CONFIG.getProperty("PROXY_HOST"),Integer.parseInt((CONFIG.getProperty("PROXY_PORT"))));
	    DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
	    CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    credsProvider.setCredentials(
	    		new AuthScope(CONFIG.getProperty("PROXY_HOST"), Integer.parseInt((CONFIG.getProperty("PROXY_PORT"))), AuthScope.ANY_HOST, "ntlm"), 
	    		new NTCredentials(CONFIG.getProperty("PROXY_USERNAME"), CONFIG.getProperty("PROXY_PASSWORD"), "", CONFIG.getProperty("PROXY_DOMAIN")));
	    context = HttpClientContext.create();
	    //Target host details
	    
	    HttpHost targetHost = new HttpHost(CONFIG.getProperty("TARGET_HOST"), Integer.parseInt((CONFIG.getProperty("TARGET_PORT"))), CONFIG.getProperty("TARGET_TYPE"));
	    credsProvider.setCredentials(
	            new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
	            new UsernamePasswordCredentials(CONFIG.getProperty("TARGET_USERNAME"), CONFIG.getProperty("TARGET_PASSWORD")));
	    
	    // Create AuthCache instance
	    AuthCache authCache = new BasicAuthCache();
	    // Generate BASIC scheme object and add it to the local auth cache
	    BasicScheme basicAuth = new BasicScheme();
	    authCache.put(targetHost, basicAuth);
	    context.setCredentialsProvider(credsProvider);
	    context.setAuthCache(authCache);    
	    httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRoutePlanner(routePlanner).build();
	    proxySpecification=new ProxySpecification(CONFIG.getProperty("PROXY_HOST"),Integer.parseInt((CONFIG.getProperty("PROXY_PORT"))),CONFIG.getProperty("PROXY_TYPE")).withAuth(CONFIG.getProperty("PROXY_USERNAME"), CONFIG.getProperty("PROXY_PASSWORD"));
	    
  	}
   
	public static void main( String[] args ) throws AbstractMethodError, Exception 
	{
   
		App test=new App();
		    try {
		    	//test.ReadExcel_Data();//Main driver function
				Rep.GeneratehtmlReport();//Generate report
		    	
		    } catch (Exception e) {
		        e.printStackTrace();
		    }	
		    
    }
	
    
    /***************************************************************************************
	 *  Function name 		: Get_Method
	 *  Reuse Function 		:  
	 *  Description 		: GET Method API WebServices
	/****************************************************************************************/         
    public HttpResponse HttpGet_Method(String apiurl,String HKeys, String HValues) throws ClientProtocolException, IOException
	{
	
		HttpResponse resp=null;

		
		
		try{
			 HttpGet httpget = new HttpGet(apiurl);
			if ((HKeys.length()>0)&(HValues.length()>0))
			{
		        String[] ArKeys = HKeys.split(APIConstants.DATA_SPLIT);
		        String[] ArValues = HValues.split(APIConstants.DATA_SPLIT);
		        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
		        {
		        	httpget.setHeader(ArKeys[i],ArValues[i]);
		        }
			}
	        resp = httpclient.execute(httpget, context);
	        int httpCode = resp.getStatusLine().getStatusCode();
	        VerifyResponseStatus(httpCode,200);
	   	 	return resp;
		}
		catch(Exception e){
			 e.printStackTrace();
			 return resp;
		}
	}
    
    /***************************************************************************************
	 *  Function name 		: Get_Method
	 *  Reuse Function 		:  
	 *  Description 		: GET Method API WebServices
	/****************************************************************************************/         

    public Response Get_Method(String apiurl,String HKeys, String HValues)
    {    
    	int StatusCode=0;
    	Response GetResp = null;
    	//System.out.println("Get call"+apiurl+HKeys+HValues);
    	try
    	{
    		Map<String, String> header = GetKeyValuePair(HKeys,HValues);
	    	GetResp=given()   
	    			//.relaxedHTTPSValidation()
	        .proxy(proxySpecification)
	        .headers(header)
			.get(apiurl);
	    	StatusCode=GetResp.getStatusCode();
	    	//VerifyResponseStatus(GetResp,200);
    	}
    	catch(Exception e)
    	{
    		return GetResp;
    	}
    	return GetResp;
   }
    
	/***************************************************************************************
	 *  Function name 		: Post_Method
	 *  Reuse Function 		:  
	 *  Description 		: Post Method API WebServices
	/****************************************************************************************/         

    public Response Post_Method(String apiurl,String HKeys, String HValues,String PKeys, String PValues){    
    	int StatusCode;
    	StatusCode=1;
    	Response PostResp = null;
    	try
    	{
	    	//Get input key value parameter
    		Map<String, String> BodyParameter = GetKeyValuePair(PKeys,PValues);
    		Map<String, String> header = GetKeyValuePair(HKeys,HValues);
    		//apiurl=apiurl.trim();
//    		System.out.println("apiurl :"+apiurl);
//    		 System.out.println("BodyParameter :"+BodyParameter);
//    		 System.out.println("header :"+header);
//    
    		
	        PostResp=given()   
	        .proxy(proxySpecification)		
	    //	.contentType("application/x-www-form-urlencoded")
	        .headers(header)
			.body(BodyParameter)
			.when()
			.post(apiurl);
	        
	    	StatusCode=PostResp.getStatusCode();
	    	//VerifyResponseStatus(PostResp,200);
    	}
      	catch(Exception e)
    	{
    		return PostResp;
    	}
      	return PostResp;
    	
   }
    
	/***************************************************************************************
	 *  Function name 		: Post_Method
	 *  Reuse Function 		: NA
	 *  Description 		: Post Method API WebServices using HttpPost
	/****************************************************************************************/         
    public HttpResponse HTTPPost_Method(String apiurl,String HKeys, String HValues,String PKeys,String PValues) throws ClientProtocolException, IOException
	{
		HttpResponse resp=null;
		try{
			HttpPost httppost = new HttpPost(apiurl);
			if ((HKeys.length()>0)&(HValues.length()>0))
			{
		        String[] ArKeys = HKeys.split(APIConstants.DATA_SPLIT);
		        String[] ArValues = HValues.split(APIConstants.DATA_SPLIT);
		        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
		        {
		        	httppost.setHeader(ArKeys[i],ArValues[i]);
		        }
			}
			ArrayList<NameValuePair> postParameters=GetNameValuePair(PKeys,PValues);
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
	        resp = httpclient.execute(httppost,context);
	        int httpCode = resp.getStatusLine().getStatusCode();
	        VerifyResponseStatus(httpCode,200);
	        
		}
		catch(Exception e){
			 e.printStackTrace();
			 return resp;
		}
   	 return resp;

	}
	
	/***************************************************************************************
	 *  Function name 		: Delete_Method
	 *  Reuse Function 		: ReadExcel_Data
	 *  Description 		: Delete Method API WebServices  
	/****************************************************************************************/ 
    public Response Delete_Method(String apiurl,String HKeys, String HValues,String PKeys, String PValues){    
    	int StatusCode;
    	StatusCode=1;
    	
    	//Map<String, Object>  jsonAsMap = new HashMap();
    	
    	Map<String, String> BodyParameter = GetKeyValuePair(PKeys,PValues);
    	Map<String, String> header = GetKeyValuePair(HKeys,HValues);
    	Response res=given()
    	.proxy(proxySpecification)	
    	//.contentType("application/json;charset=UTF-8")
    	.headers(header)
		.body(BodyParameter)
		.when()
		.delete(apiurl);
		 StatusCode=res.getStatusCode();
    	//System.out.println("Device is sucessfully deleted and reponse is "+StatusCode);
    	return res;
   }
	/***************************************************************************************
	 *  Function name 		: Put_Method
	 *  Reuse Function 		: 
	 *  Description 		: Update Method API WebServices 
	/****************************************************************************************/ 
    public Response Put_Method(String apiurl,String HKeys, String HValues,String PKeys, String PValues){    
    	int StatusCode;
    	StatusCode=1;
    	
    
    	//Map<String, Object>  jsonAsMap = new HashMap();
    	
    	Map<String, String> BodyParameter = GetKeyValuePair(PKeys,PValues);
    	Map<String, String> header = GetKeyValuePair(HKeys,HValues);
    	Response res=given()
    	.proxy(proxySpecification)	
    	.headers(header)
    	//.contentType("application/json;charset=UTF-8")
		.body(BodyParameter)
		.when()
		.put(apiurl);
    	//StatusCode=res.getStatusCode();
    	return res;
   }
    
    
	/***************************************************************************************
	 *  Function name 		: HttpPut_Method
	 *  Reuse Function 		: 
	 *  Description 		: Update Method API WebServices 
	/****************************************************************************************/ 
	public HttpResponse HttpPut_Method(String apiurl,String HKeys, String HValues,String PKeys,String PValues) throws ClientProtocolException, IOException
	{
		HttpResponse resp=null;
		try{
			 HttpPut httpput = new HttpPut(apiurl);
			 
				if ((HKeys.length()>0)&(HValues.length()>0))
				{
			        String[] ArKeys = HKeys.split(APIConstants.DATA_SPLIT);
			        String[] ArValues = HValues.split(APIConstants.DATA_SPLIT);
			        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
			        {
			        	httpput.setHeader(ArKeys[i],ArValues[i]);
			        }
				}
				if ((PKeys.length()>0)&(PValues.length()>0))
				{
					ArrayList<NameValuePair> postParameters= GetNameValuePair(PKeys,PValues); 
			        httpput.setEntity(new UrlEncodedFormEntity(postParameters));
				}
	        resp = httpclient.execute(httpput,context);
	        int httpCode = resp.getStatusLine().getStatusCode();
	        VerifyResponseStatus(httpCode,200);
		}
		catch(Exception e){
			 e.printStackTrace();
			 return resp;
		}
   	 return resp;

	}
	
	/***************************************************************************************
	 *  Function name 		: Delete_Method
	 *  Reuse Function 		: 
	 *  Description 		: delete Method API WebServices 
	/****************************************************************************************/ 
	public HttpResponse HttpDelete_Method(String apiurl,String HKeys, String HValues,String PKeys,String PValues) throws ClientProtocolException, IOException
	{
		HttpResponse resp=null;
		try{
			 HttpDelete httpdelete = new HttpDelete(apiurl);
			 
				if ((HKeys.length()>0)&(HValues.length()>0))
				{
			        String[] ArKeys = HKeys.split(APIConstants.DATA_SPLIT);
			        String[] ArValues = HValues.split(APIConstants.DATA_SPLIT);
			        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
			        {
			        	httpdelete.setHeader(ArKeys[i],ArValues[i]);
			        }
				}
				
			 if ((PKeys.length()>0)&(PValues.length()>0))
			{
				 ArrayList<NameValuePair> postParameters= GetNameValuePair(PKeys,PValues); 
			     ((HttpResponse) httpdelete).setEntity(new UrlEncodedFormEntity(postParameters));
			}
	        resp = httpclient.execute(httpdelete,context);
	        int httpCode = resp.getStatusLine().getStatusCode();
	        VerifyResponseStatus(httpCode,200);
		}
		catch(Exception e){
			 e.printStackTrace();
			 return resp;
		}
   	 return resp;

	}
    
	
	
	/***************************************************************************************
	 *  Function name 		: ImplSwitchingMode
	 *  Reuse Function 		:  GetOutkeyValueFromResponse
	 *  Description 		: Depends upon response switch which test need to be executed
	/****************************************************************************************/    
	  public void ImplSwitchingMode(String Filename, String Sheetname, Response res,String SwitchingMode) 
	  {
		String switchValues=null;
		if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
		{
			
			Xls_Reader current_TestCase_xls1=null;
	    	//Constants.TEST_STEPS_SHEET="Agtech";
	        current_TestCase_xls1=new Xls_Reader(Filename);
	        
			//System.out.println("SwitchingMode: "+SwitchingMode);
			if((SwitchingMode.length()>0)&&(!SwitchingMode.equalsIgnoreCase("null")))
			{
		  	String[] switching = SwitchingMode.split("\\|");					        										           
		  	if ((switching.length>0))
		  	{				
		  		switchValues=GetOutkeyValueFromResponse(res,switching[0]);
				for(int i=1;i<switching.length ;i++)
				{								    			
					String[] tc=switching[i].split(":");									    			
					if (switchValues.equalsIgnoreCase(tc[0]))
					{		
						int rownum2=current_TestCase_xls.getCellRowNum(Sheetname, APIConstants.TCID,tc[1]);
						current_TestCase_xls.setCellData(Sheetname,APIConstants.TESTRUNMODE, rownum2, "Yes");
					}	
				}
			}
	    }
		}
	  }
		
	/***************************************************************************************
	 *  Function name 		: GetOutkeyValueFromResponse
	 *  Reuse Function 		:  GetResponseContentType
	 *  Description 		: Get output key value from the response
	/****************************************************************************************/    
	public String GetOutkeyValueFromResponse(Response res,String Key) 
	{
	   	String repOutputKey=null;
	   	String repContentType=null;
	    	try
	   		 {  
	    			repContentType=GetResponseContentType(res);
		    		if ((repContentType.contains("json"))|(repContentType.contains("JSON")))
		    		{
		    			repOutputKey=res.then().extract().jsonPath().getString(Key);
			    	
		    		}
		    		else if ((repContentType.contains("xml"))|(repContentType.contains("XML")))
		    		{
		    			repOutputKey=res.then().extract().xmlPath().getString(Key);
		    		}	
		    		else if ((repContentType.contains("html"))|(repContentType.contains("HTML")))
		    		{
		    			repOutputKey=res.then().extract().htmlPath().getString(Key);
		    		}
		    		
		    	   	if ((repOutputKey.contains("["))&(repOutputKey.contains("]")))
		            {
		    	   		repOutputKey=repOutputKey.replace("[", "");
		    	   		repOutputKey=repOutputKey.replace("]", "");
		            }
		    	
		    		
	    	}
	    	catch (AssertionError e)
	   		 {     		
	   			 System.out.println("Err Catch GetOutkeyValue :"+e.getMessage());
	   			 return repOutputKey;
	   		 }
	    	return repOutputKey;
	    	
	    }

	/*************************************************************************************************
	 *  Function name 		: GetJsonValueFromResponse
	 *  Reuse Function 		: 
	 *  Description 		:  Get the Value of  key from responsestring
	/**************************************************************************************************/
	 public String GetJsonValueFromResponse(String ResponseString,String Key)
	    {   	
	    	String ReString;
	    	ReString=null;
	    	ReString="Not Found";
		    try 
		        {
		    	if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
		    	{
			    	JsonPath jsonResponse = new JsonPath(ResponseString);
				    ReString= jsonResponse.getString(Key);			 		 
					System.out.println("Json Values for output key "+Key+" is "+ReString);
		    	}
		    	else
		    	{
		    		ReString="";
		    	}
		        } 
		     catch (Exception e) 
		        {
		        	e.printStackTrace();
		        }        	      
	     	return ReString;
	      }    
	
	 
		/*************************************************************************************************
		 *  Function name 		: GetJsonValueFromResponse2
		 *  Reuse Function 		: 
		 *  Description 		: Get the Value of  key from external json file
		/**************************************************************************************************/
		 public String GetJsonValueFromResponse2(String FileName,String FileType,String Key)
		    {   	
		    	String ReString;
		    	ReString=null;
		    	ReString="Not Found";
			    File file = new File(System.getProperty("user.dir")+"//src//test//resources//Response/"+FileName+"."+FileType);
			    try 
			        {
			    	
			    	JsonPath jsonResponse = new JsonPath(new FileReader(file));
				    ReString= jsonResponse.getString(Key);			 		 
					System.out.println("Json Values for output key "+Key+" is "+ReString);

			        } 
			     catch (FileNotFoundException e) 
			        {
			        	e.printStackTrace();
			        } 
			     catch (IOException e) 
			        {
			        	e.printStackTrace();
			        }        	      
		     	return ReString;
		      }    
		
		/**************************************************************************
		 *  Function name 		: ExpectedValueValidation
		 *  Reuse Function 		: 
		 *  Description 		: Get Method with validation
		 	/**********************************************************************/
	    public boolean ExpectedValueValidation(String apiurl,String Key,String ExpectedValue) 
	    {
	    		try
	    		 {    
	    			get(apiurl).then().assertThat().body(Key, equalTo(ExpectedValue));
	    			return true;
	    		 }
	    		 catch (AssertionError e)
	    		 {     			
	    			 return false;
	    		 }
	    }
	    
		/**************************************************************************
		 *  Function name 		: Rep_InputValues
		 *  Reuse Function 		: 
		 *  Description 		: Provide paired key values from  excel input key values    
		 	/**********************************************************************/    
	    public String Rep_InputValues(String PKeys,String PValues) 
		{
			String RetnString="";
				if ((PKeys.length()>0)&(PValues.length()>0))
				{
		
			        String[] ArKeys = PKeys.split(APIConstants.DATA_SPLIT);
			        String[] ArValues = PValues.split(APIConstants.DATA_SPLIT);
			
			        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
			        {
			        	RetnString=RetnString+"\n "+ArKeys[i]+" : "+ArValues[i];
			        	
			        }
				}
				else
				{
					RetnString="NA";
				}
				return RetnString;
			}
	    
	    
    /**************************************************************************
	 *  Function name 		: GetKeyValuePair
	 *  Reuse Function 		: 
	 *  Description 		: Provide paired key values from  excel input key values    
	 /**********************************************************************/    
	    public ArrayList<NameValuePair> GetNameValuePair(String PKeys,String PValues) 
		{
			//Map<String, String>  mKeyValue=new HashMap<>();
			
			ArrayList<NameValuePair> mKeyValue = null;

	        
				if ((PKeys.length()>0)&(PValues.length()>0))
				{
					mKeyValue = new ArrayList<NameValuePair>();
			        String[] ArKeys = PKeys.split(APIConstants.DATA_SPLIT);
			        String[] ArValues = PValues.split(APIConstants.DATA_SPLIT);
			        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
			        {
			        	mKeyValue.add(new BasicNameValuePair(ArKeys[i], ArValues[i]));
			        }
				
 
				}
				return mKeyValue;
			}
	    
	    
    /**************************************************************************
	 *  Function name 		: GetKeyValuePair
	 *  Reuse Function 		: 
	 *  Description 		: Provide paired key values from  excel input key values    
	 /**********************************************************************/    
	    public Map<String, String> GetKeyValuePair(String PKeys,String PValues) 
		{
			Map<String, String>  mKeyValue=new HashMap<>();
				if ((PKeys.length()>0)&(PValues.length()>0))
				{
			        
					//jsonAsMap = new HashMap<>();
			        String[] ArKeys = PKeys.split(APIConstants.DATA_SPLIT);
			        String[] ArValues = PValues.split(APIConstants.DATA_SPLIT);
			        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
			        {
			        	mKeyValue.put(ArKeys[i], ArValues[i]);
			        	
			        }
 
				}
				return mKeyValue;
			}
	    
	    /**************************************************************************
		 *  Function name 		: ResponseExpectedValidation
		 *  Reuse Function 		: 
		 *  Description 		: Validate response with expected key value pair    
		 /**********************************************************************/    
	    public boolean ResponseExpectedValidation(Response res, String PKeys,String PValues)
	    {
	    	 boolean Validation= false;
	    	 String ExpecKeytoFind=null;
	    	 String repKeyExist=null;
	    	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
	    	 {
		    	try
		   		 {  
			    	if ((PKeys.length()>0)&(PValues.length()>0)&(!PKeys.equalsIgnoreCase("null"))&(!PValues.equalsIgnoreCase("null")))
					{
			    		
				        String[] ArKeys = PKeys.split(APIConstants.DATA_SPLIT);
				        String[] ArValues = PValues.split(APIConstants.DATA_SPLIT);
				        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
				        {
				        	ExpecKeytoFind=ArKeys[i];
				        
				        	//Check whether key is present
				        	repKeyExist=res.then().extract().path(ArKeys[i]);
				        	if(repKeyExist!=null)
				        	{	
						        res.then().assertThat().body(ArKeys[i], equalTo(ArValues[i]));
						    	Validation= true;
				        	}
				        	else
				        	{
				        		ApiDescription=ApiDescription+"\n  Key not found : "+ArKeys[i];
					   			APIStatus=APIConstants.KEYWORD_FAIL;
					   			System.out.println("Err Catch ResponseExpectedValidation :"+ApiDescription);
					   			return false;
				        	}
				        }
	
					}
			    	else
			    	{
			    		 Validation= true;
			    		 //ApiDescription=ApiDescription+"\n  Expected Values not provided";
			    		 return Validation;
			    	}
		   		 }
		   		 catch (AssertionError e)
		   		 {     		
		   			System.out.println("Err Catch ResponseExpectedValidation :"+e.getMessage());
		   			ApiDescription=ApiDescription+"\n  Mismatched Actual Values:"+e.getMessage();
		   			APIStatus=APIConstants.KEYWORD_FAIL;
		   			return false;
		   		 }
		    	catch (ClassCastException e)
		   		 {     		
		    		System.out.println("Err Catch ResponseExpectedValidation :"+e.getMessage());
		   			ApiDescription=ApiDescription+"\n  Key not found  :"+ExpecKeytoFind;
		   			APIStatus=APIConstants.KEYWORD_FAIL;
		   			return false;
		   		 }
			    APIStatus=APIConstants.KEYWORD_PASS;
				ApiDescription=ApiDescription+"\n  Expected Values matched with actual values";
		    	}

	    	return Validation;
	    	
	    }
	    
	    
	    /**************************************************************************
		 *  Function name 		: ResponseExpectedValidation
		 *  Reuse Function 		: 
		 *  Description 		: Validate response with expected key value pair    
		 /**********************************************************************/    
	    public boolean ResponseValidation_hasitem(Response res, String PKeys,String PValues)
	    {
	    	 boolean Validation= false;
	    	 String ExpecKeytoFind=null;
	    	 String repKeyExist=null;
	    	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
	    	 {
		    	try
		   		 {  
			    	if ((PKeys.length()>0)&(PValues.length()>0)&(!PKeys.equalsIgnoreCase("null"))&(!PValues.equalsIgnoreCase("null")))
					{
			    		
				        String[] ArKeys = PKeys.split(APIConstants.DATA_SPLIT);
				        String[] ArValues = PValues.split(APIConstants.DATA_SPLIT);
				        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
				        {
				        	ExpecKeytoFind=ArKeys[i];
				        	//Check whether key is present
				        	repKeyExist=res.then().extract().path(ArKeys[i]);
				        	if(repKeyExist!=null)
				        	{	
						        res.then().assertThat().body(ArKeys[i], hasItem(ArValues[i]));
						    	Validation= true;
				        	}
				        	else
				        	{
				        		ApiDescription=ApiDescription+"\n  Key not found : "+ArKeys[i];
					   			APIStatus=APIConstants.KEYWORD_FAIL;
					   			System.out.println("Err Catch ResponseExpectedValidation :"+ApiDescription);
					   			return false;
				        	}
				        }
	
					}
			    	else
			    	{
			    		 Validation= true;
			    		 //ApiDescription=ApiDescription+"\n  Expected Values not provided";
			    		 return Validation;
			    	}
		   		 }
		   		 catch (AssertionError e)
		   		 {     		
		   			System.out.println("Err Catch ResponseExpectedValidation :"+e.getMessage());
		   			ApiDescription=ApiDescription+"\n  Mismatched Actual Values:"+e.getMessage();
		   			APIStatus=APIConstants.KEYWORD_FAIL;
		   			return false;
		   		 }
		    	catch (ClassCastException e)
		   		 {     		
		    		System.out.println("Err Catch ResponseExpectedValidation :"+e.getMessage());
		   			ApiDescription=ApiDescription+"\n  Key not found :"+ExpecKeytoFind;
		   			APIStatus=APIConstants.KEYWORD_FAIL;
		   			return false;
		   		 }
		    	
		    	}
		    APIStatus=APIConstants.KEYWORD_PASS;
			ApiDescription=ApiDescription+"\n  Expected Values matched with actual values";
	    	return Validation;
	    	
	    }
	    
	    /***************************************************************************************
		 *  Function name 		: GetOutkeyValueFromResponse
		 *  Reuse Function 		:  GetResponseContentType
		 *  Description 		: Get output key value from the response
		/****************************************************************************************/    
		public static String VerifyPathFromResponse(Response res,String Key) 
		{
		   	String bResPathValid=null;
	
		    	try
		   		 {  
		    		bResPathValid=res.then().extract().path(Key);
			    	System.out.println("Path exist : "+Key+" : "+bResPathValid);
		    	}
		    	catch (AssertionError e)
		   		 {     		
		   			 System.out.println("Err Catch VerifyPathFromResponse :"+e.getMessage());
		   			 return bResPathValid;
		   		 }
		    	catch (ClassCastException e)
		   		 {     		
		   			 System.out.println("Err Catch VerifyPathFromResponse :"+e.getMessage());
		   			 return bResPathValid;
		   		 }
		    	return bResPathValid;
		    	
		    }
	    
	    /**************************************************************************
		 *  Function name 		: GetResponseTime
		 *  Reuse Function 		: 
		 *  Description 		: Get response time from response    
		 /**********************************************************************/  
	    public long GetResponseTime(Response res)
	    {
	    	String respinms="";
	    	long repTime=0;
		   	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
	    	 {
		    	try
		   		 {  
				    repTime=res.then().extract().response().getTime();
				    System.out.println("Resp Time "+repTime);
				    respinms=repTime+" ms";
				   // ApiDescription=ApiDescription+"\n  Response Time:"+repTime +" ms";
				 }
		    	catch (AssertionError e)
		   		 {     		
		   			
		   			 System.out.println("Err Catch GetResponseTime :"+e.getMessage());
		   			 ApiDescription=ApiDescription+"\n  Invalid Response Time: "+repTime;
		   			 return repTime;
		   		 }
		    	//ApiDescription=ApiDescription+"\n  Expected Values matched with actual values";
	    	 }
	    	return repTime;
	    	
	    }
	    
	    
	    /**************************************************************************
		 *  Function name 		: GetResponseTimeinMS
		 *  Reuse Function 		: 
		 *  Description 		: Get response time in ms from response    
		 /**********************************************************************/  
	    public String GetResponseTimeinMS(Response res)
	    {
	    	String respinms="";
	    	long repTime=0;
		   	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
	    	 {
		    	try
		   		 {  
				    repTime=res.then().extract().response().getTime();
				    System.out.println("Resp Time "+repTime);
				    respinms=repTime+" ms";
				   // ApiDescription=ApiDescription+"\n  Response Time:"+repTime +" ms";
				 }
		    	catch (AssertionError e)
		   		 {     		
		   			
		   			 System.out.println("Err Catch GetResponseTime :"+e.getMessage());
		   			 ApiDescription=ApiDescription+"\n  Invalid Response Time: "+repTime;
		   			 return respinms;
		   		 }
		    	//ApiDescription=ApiDescription+"\n  Expected Values matched with actual values";
	    	 }
	    	return respinms;
	    	
	    }
	    
	    /**************************************************************************
		 *  Function name 		: GetResponseContentType
		 *  Reuse Function 		: 
		 *  Description 		: Get content type from response    
		 /**********************************************************************/  
	    public static String GetResponseContentType(Response res)
	    {
	    	String repContentType=null;
	    	String RespContent=null;
	    	try
	   		 {  
	    		repContentType=res.then().extract().response().contentType();
			    //System.out.println("Resp Content Type "+repContentType);
	    		
	      		if ((repContentType.contains("json"))|(repContentType.contains("JSON")))
	    		{
	      			RespContent="json";
	    		}
	    		else if ((repContentType.contains("xml"))|(repContentType.contains("XML")))
	    		{
	    			RespContent="xml";
	    		}	
	    		else if ((repContentType.contains("html"))|(repContentType.contains("HTML")))
	    		{
	    			RespContent="html";
	    		}
	    		else
	    		{
	    			RespContent="txt";
	    		}
	  
			 }
	    	catch (AssertionError e)
	   		 {     		
	   			
	   			 //System.out.println(e.getMessage());
	   			 //System.out.println(e.toString());
	   			 System.out.println("Err Catch GetResponseContentType :"+e.getMessage());
	   			 ApiDescription=ApiDescription+"\n  Invalid Content Type:"+repContentType;
	   			 return RespContent;
	   		 }
	    	return RespContent;
	    	
	    }
	    
	    
    /********************************************************************************
	 *  Function name 		: ValidataSchema
	 *  Reuse Function 		: 
	 *  Description 		: Validate response with schema from JSON, XML-XSD/DTD
	 /*******************************************************************************/  
	public String ValidataSchema(Response res,String SchemaFilepath,String ResponseFilePath) throws ProcessingException, IOException
	{
	   	String repContentType=null;
	    File SchmFile = new File(SchemaFilepath);
	   	 if ((APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))&(SchmFile.exists()))
    	 {
	    	try
	   		 {  
	    			repContentType=GetResponseContentType(res);
		    		if ((repContentType.contains("json"))|(repContentType.contains("JSON")))
		    		{
		    			ApiDescription=ApiDescription+"\n  Json Schema Validation : ";
		    			//res.then().assertThat().body(matchesJsonSchemaInClasspath(""));
		    			ValidateJSONSchema(SchemaFilepath,ResponseFilePath);
		    		}
		    		else if ((repContentType.contains("xml"))|(repContentType.contains("XML")))
		    		{
		    			String ext =FilenameUtils.getExtension(SchemaFilepath);
		    			if (ext.equalsIgnoreCase("xsd"))
		    			{
		    				ApiDescription=ApiDescription+"\n  XML Schema Valitation with XSD :";
		    				res.then().assertThat().body(matchesXsd(SchmFile));
		    				APIStatus=APIConstants.KEYWORD_PASS;
		    				ApiDescription=ApiDescription+"\n Passed ";
		    			}
		    			else if (ext.equalsIgnoreCase("dtd"))
		    			{
		    				ApiDescription=ApiDescription+"\n  XML Schema Valitation with Dtd :";
		    				res.then().assertThat().body(matchesDtd(SchmFile));
		    				APIStatus=APIConstants.KEYWORD_PASS;
		    				ApiDescription=ApiDescription+"\n Passed ";
		    			}
		    		else if ((repContentType.contains("html"))|(repContentType.contains("HTML")))
		    		{
		    			
		    		}
	    	}
	    		
			 }
	    	catch (AssertionError e)
	   		 {     		
	   			
	   			 System.out.println("Err Catch ValidataSchema :"+e.getMessage());
	   			 APIStatus=APIConstants.KEYWORD_FAIL;
	   			 ApiDescription=ApiDescription+"\n Faileded :";
	   			 return repContentType;
	   		 }
	    	
    	 }
	    	return repContentType;
	    	
	    }
	   
    /********************************************************************************
	 *  Function name 		: ValidateJSONSchema
	 *  Reuse Function 		: 
	 *  Description 		: Validate response string with schema from JSON
	 /*******************************************************************************/  
	    public static boolean ValidateJSONSchema(String JsonSchemaFile,String JsonValueFile) throws ProcessingException, IOException
	    {
	  	  boolean isvalid=false;
	  	  String PrcMsg=null;
	  	  vjson=new ValidationUtils();
	   	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
	   	 {
	  		  File sFile = new File(JsonSchemaFile);
	  		  File jFile = new File(JsonValueFile);
	  		  if ((sFile.exists())&(jFile.exists())) 
	  		  {
	  			  isvalid=vjson.isJsonValid(sFile, jFile);
	  			  if (!isvalid)
	  			  {
	  				PrcMsg=vjson.validateJson(sFile,jFile);
	  				System.out.println("Json Schema Process Msg :"+PrcMsg);
	  				APIStatus=APIConstants.KEYWORD_FAIL;
	  				ApiDescription=ApiDescription+"\n  Json Schema Faileded Process Msg: \n "+PrcMsg;
	  			  }
	  			  else
	  			  {
	  				  APIStatus=APIConstants.KEYWORD_PASS;
	  				  ApiDescription=ApiDescription+"\n Passed ";
	  			  }
	  			}
	  		  else
	  		  {
	  			  return true;
	  			  
	  		  }
	   	 }
	   	
	  	  return isvalid;
	    }
	    
    /********************************************************************************
	 *  Function name 		: VerifyResponseStatus
	 *  Reuse Function 		: 
	 *  Description 		: Validate expected status from response
	 /*******************************************************************************/  
	    public int VerifyResponseStatus(Response res,int expStatusCode)
	    {
	    	int RespStatus=0;
	    	try
	   		 { 
			    
			    res.then().assertThat().statusCode(expStatusCode);
			    RespStatus=res.getStatusCode();
			    System.out.println("Resp Status :"+RespStatus);
			    
			 }
	    	catch (AssertionError e)
	   		 {     		
	    		 System.out.println("Err Catch ResponseStatus :"+e.getMessage());
	   			 APIStatus=APIConstants.KEYWORD_FAIL;
	   			 ApiDescription="The following response details: \n Invalid Response Code:"+RespStatus+"\n"+e.getMessage();
	   			 return RespStatus;
	   		 }
	    	APIStatus=APIConstants.KEYWORD_PASS;
		    ApiDescription="The following response details: \n Response Code: "+RespStatus;
		   
	    	return RespStatus;
	    }
	    
	    /********************************************************************************
		 *  Function name 		: VerifyResponseStatus
		 *  Reuse Function 		: 
		 *  Description 		: Validate expected status from Actual
		 /*******************************************************************************/  
	    
	    public static void VerifyResponseStatus(int ActualStatusCode,int expStatusCode)
	    {
	    
	     
	    		if (ActualStatusCode==expStatusCode)
	    		{
	    			APIStatus=APIConstants.KEYWORD_PASS;
	    		    ApiDescription="Valid Response Code: "+ActualStatusCode;
	    		}
	    	else
	   		 {     		
	   			APIStatus=APIConstants.KEYWORD_FAIL;
	   			System.out.println("Resp Status "+ActualStatusCode);
	   			ApiDescription="Invalid Response status:"+ActualStatusCode;
	   		 }
	
	    }
	    
		/***************************************************************************************
		 *  Function name 		: VerifyExpectedValues
		 *  Reuse Function 		: 
		 *  Description 		: Split the GetOutputKey and Expected values using delimiter |. 
		/****************************************************************************************/         
	    public void VerifyExpectedValues( Xls_Reader current_TestCase_xls,int rowNum,String url,String OpKey, String Expected) 
	    {
	    	boolean SetValue3,SetValue4;
	       	String joined = null;
	       	if (Expected.length() > 0 )
	       	{
		    	String[] GetOutputKey = OpKey.split("\\|");
		    	String[] Actual = Expected.split("\\|");
		    	String[] descrition = new String[GetOutputKey.length];
		    	String Status=null;
		    	
		    	boolean TestFlag=true; 	
		    	boolean[] result = new boolean[GetOutputKey.length];
				for(int i=0;i<GetOutputKey.length;i++)
				{	
				 	result[i]= ExpectedValueValidation(url, GetOutputKey[i], Actual[i]);
					if (result[i] == true)
						descrition[i]= "Validation match " + GetOutputKey[i]+":" +Actual[i];				
					else{
						TestFlag=false;
						descrition[i]= "Validation not match " + GetOutputKey[i]+":" +Actual[i];
						}
				  } 			
					joined = String.join("|", descrition);			
					if (TestFlag==true)
					 Status =APIConstants.KEYWORD_PASS; 
					else
					 {Status=APIConstants.KEYWORD_FAIL;}
					
					 SetValue3=current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.ResponseDescription, rowNum, joined);
					 SetValue4=current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.RESULT, rowNum, Status);
					 System.out.println(" Result:"+SetValue3+SetValue4);
	      	}
			}
	    
	
    /*************************************************************************************************
	 *  Function name 		: GetReferenceValue
	 *  Reuse Function 		: 
	 *  Description 		: Get the reference value from the URL column  
	/**************************************************************************************************/   
  
	public static String GetReferenceValue(String Filename, String SheetName, String XString)
    {
    	String OpValue,OpStatus;
    	String ReTurnString=XString;
    	int strlen=XString.length();
    	Xls_Reader current_TestCase_xls1=null;
    	//Constants.TEST_STEPS_SHEET="Agtech";
        current_TestCase_xls1=new Xls_Reader(Filename);
    	if ((XString.contains("{"))&(XString.contains("}")))
        {
    		String Url1 = XString.substring(0,XString.indexOf("{"));
    		String RefString = XString.substring(XString.indexOf("{")+1, XString.indexOf("}"));
    		//System.out.println("Ref String : "+RefString);
    		String[] ArTcCol = RefString.split(APIConstants.DATA_SPLIT);
    		String RefTCid=ArTcCol[0];
    		//System.out.println(RefTCid);
    		String[] A1tTCs=RefTCid.split(APIConstants.KEYWORD_REF);
    		String TCID=A1tTCs[1];
    		String TcColm=ArTcCol[1];
    		String Urllast = XString.substring(XString.indexOf("}")+1,strlen);
        	int ValueRow= current_TestCase_xls1.getCellRowNum(SheetName, APIConstants.TCID, TCID);
        	OpStatus=current_TestCase_xls1.getCellData(SheetName, APIConstants.RESULT, ValueRow);
        	//System.out.println(" OpStatus:"+OpStatus);
        	if (OpStatus.equalsIgnoreCase("Pass"))
        	{
        		//System.out.println(" Ref Colomn Name:"+TcColm);
               	OpValue=current_TestCase_xls1.getCellData(SheetName, TcColm, ValueRow);
               //	System.out.println(" OpValue:"+OpValue);
            	System.out.println("Reference Testcase ID : "+TCID+" Coloumn of "+TcColm+" is "+OpValue);
            	if (OpValue.length()>0)
            	{
	        		
	        		ReTurnString=Url1+OpValue+Urllast;
	        		//System.out.println("Return URL with Ref:"+ReTurnString);
            	}
            	else
            	{
            		ReTurnString=Url1+"Ref not Found"+Urllast;
            		APIStatus=APIConstants.KEYWORD_SKIP;
        		    ApiDescription="Skipped due to Value not exist of dependent Test case  : " +TCID;
            		
            	}
        	}
        	else
        	{
        		ReTurnString=Url1+"Ref not Found"+Urllast;
        		APIStatus=APIConstants.KEYWORD_SKIP;
    		    ApiDescription="Skipped due to failed status of dependent Test case  : " +TCID;
        	}
 
    		 
          }
       
    	return ReTurnString;
    }
	
	/*************************************************************************************************
	 *  Function name 		: StoreRespose
	 *  Reuse Function 		: 
	 *  Description 		: Store the Web services Response to local src/res/Response path
	/**************************************************************************************************/
	
    public String StoreRespose(Xls_Reader current_TestCase_xls,String Filename, String FileType,String Value,int rowNum)throws FileNotFoundException
    {
    	boolean SetValue2;
    	String RespPath=null;
    	File file = new File(System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"Response//"+Filename+"."+FileType);
		try (FileOutputStream fop = new FileOutputStream(file)) 
		{
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = Value.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			URL myUrl = file.toURI().toURL();
			RespPath=file.getAbsolutePath();
			System.out.println("Response stored Path : "+RespPath);	
			current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.Response, rowNum,myUrl.toString());
			return RespPath;
			
		}
     catch (IOException e) {e.printStackTrace();}
		return RespPath;
    }
    
	/*************************************************************************************************
	 *  Function name 		: GetValuefromXMLStream
	 *  Reuse Function 		: 
	 *  Description 		: Read the XML response and get field value 
	/**************************************************************************************************/    
    public static String GetValuefromXMLStream(String FileName,String FileType,String Key)
    {
    	String ReString;
    	ReString=null;
    	ReString="Not Found"; 
    	      try {				
		    	  	System.out.println("XML Value for : "+FileName+" : " +FileType);	
				  	File fXmlFile = new File(System.getProperty("user.dir")+"//src//res//Response//"+FileName+"."+FileType);
				  	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				  	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();				 
				  	Document doc = dBuilder.parse(fXmlFile);
				  	doc.getDocumentElement().normalize();  				  						  	
				  	String roolElement=doc.getDocumentElement().getNodeName().toString();			  
				  	
				    NodeList nList = doc.getElementsByTagName(roolElement);			  					  					  	
				  	for (int temp = 0; temp < nList.getLength(); temp++)
				  	{				
				  		Node nNode = nList.item(temp);	
				  		
				  		if (nNode.getNodeType() == Node.ELEMENT_NODE) {				
				  			Element eElement = (Element) nNode;	
				  			ReString=eElement.getElementsByTagName(Key.trim()).item(0).getTextContent();			  						  						
				  			}
				  	}
				  } 
		      catch (Exception e) {e.printStackTrace();}
    	      return ReString;
     }


	/*************************************************************************************************
	 *  Function name 		: GetHttpRespString
	 *  Reuse Function 		: 
	 *  Description 		: Get Body string from HttpResponse
	/**************************************************************************************************/ 
	
	public static String GetHttpRespString(HttpResponse respString) 
	{
		String responseString=null;
		try{
	        HttpEntity entity = respString.getEntity();
	        responseString = EntityUtils.toString(entity, "UTF-8");
	        //System.out.println("Response Stringsss :"+responseString);
		}
		catch(Exception e){
			 e.printStackTrace();
			 return responseString;
		}
		return responseString;
	}
	
	/*************************************************************************************************
	 *  Function name 		: GetRespString
	 *  Reuse Function 		: 
	 *  Description 		: Get Body string from Response
	/**************************************************************************************************/ 
	public String GetRespString(Response respString) 
	{
		String responseString=null;
		try{
	       
	        responseString = respString.asString();
	        //System.out.println("Response Stringsss :"+responseString);
		}
		catch(Exception e){
			 e.printStackTrace();
			 return responseString;
		}
		return responseString;
	}
	
	/*************************************************************************************************
	 *  Function name 		: GetJsonKeyValue
	 *  Reuse Function 		: 
	 *  Description 		: Get value for key from Response string
	/**************************************************************************************************/ 
	public static String GetJsonKeyValue(String JsonSSString,String Key)
    {   	
    	String ReString;
    	ReString=null;
    	ReString="Not Found";
	    try 
	      {
	    	JsonPath jsonResponse = new JsonPath(JsonSSString);
	    	ReString= jsonResponse.getString(Key);			 
	      } 
	    catch (Exception e) 
	      {
	    	e.printStackTrace();
	       }        	      
     	return ReString;
      } 
	
	public static void deletfile(String FilePath)
	{
		try {
			
			File files= new File(FilePath);
			if (files.isFile())
			{
			boolean bool=files.delete();
			System.out.println("File "+files.getName()+" deleted: "+bool);
			}
		} catch (Exception x) {
		    // File permission problems are caught here.
		    System.out.println(x);
		}
	}
	

	/*************************************************************************************************
	 *  Function name 		: StoreReponseToExternal
	 *  Reuse Function 		: 
	 *  Description 		: Store the response string to local path
	/**************************************************************************************************/ 
	 public void StoreReponseToExternal(String Filename,String Value)throws FileNotFoundException
	    {
	    
		 	String TempPath=null;
		 	
		 	
		 	//System.out.println(Filename);
		 	Boolean result;
		 	File folder = new File(TempPath);
	        //ResponseBuilder response = Response.ok((Object) file);
		 	if (!folder.exists()) {
		 		 try{
		 			folder.getParentFile().mkdirs();
		 			//folder.mkdir();
		 	        result = true;
		 	    } 
		 	    catch(SecurityException se){
		 	        //handle it
		 	    }
		 	}
		 	if (result=true)
		 	{
		    	File file = new File(TempPath);
				try (FileOutputStream fop = new FileOutputStream(file)) 
				{
					// if file doesn't exists, then create it
					file.getParentFile().mkdirs();
					if (!file.exists()) {
						file.createNewFile();
					}
					// get the content in bytes
					byte[] contentInBytes = Value.getBytes();
					fop.write(contentInBytes);
					fop.flush();
					fop.close();
				}
		     catch (IOException e) {e.printStackTrace();}
	    }
	    }


	}
	
    
	
