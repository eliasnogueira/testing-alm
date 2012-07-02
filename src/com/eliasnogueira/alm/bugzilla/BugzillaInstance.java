package com.eliasnogueira.alm.bugzilla;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientException;
import org.apache.xmlrpc.client.XmlRpcSunHttpTransport;
import org.apache.xmlrpc.client.XmlRpcTransport;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;

import com.eliasnogueira.alm.util.ALMUtils;

/**
 * Classe para conectar ao Bugzilla
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 *
 */
public class BugzillaInstance {

	private static XmlRpcClient rpcClient = null;
	
	/**
	 * Efetua o login se nao existe uma instancia do Bugzilla ativa
	 */
	public static XmlRpcClient getBugzillaInstance() {
		
		if (rpcClient == null) {
			rpcClient = new XmlRpcClient();
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			final LinkedHashMap<String, String> cookies = new LinkedHashMap<String, String>();
			
			XmlRpcTransportFactory factory = new XmlRpcTransportFactory() {

	            public XmlRpcTransport getTransport() {
	                return new XmlRpcSunHttpTransport(rpcClient) {

	                    private URLConnection conn;

	                    @Override
	                    protected URLConnection newURLConnection(URL pURL) throws IOException {
	                        conn = super.newURLConnection(new URL(ALMUtils.getProperties("bugzilla.url")));
	                        return conn;
	                    }

	                    @Override
	                    protected void initHttpHeaders(XmlRpcRequest pRequest) throws XmlRpcClientException {
	                        super.initHttpHeaders(pRequest);
	                        setCookies(conn);
	                    }

	                    @Override
	                    protected void close() throws XmlRpcClientException {
	                        getCookies(conn);
	                    }

	                    private void setCookies(URLConnection pConn) {
	                        String cookieString = "";
	                        for (String cookieName : cookies.keySet()) {
	                            cookieString += "; " + cookieName + "=" + cookies.get(cookieName);
	                        }
	                        if (cookieString.length() > 2) {
	                            setRequestHeader("Cookie", cookieString.substring(2));
	                        }
	                    }

	                    private void getCookies(URLConnection pConn) {
	                        String headerName = null;
	                        for (int i = 1; (headerName = pConn.getHeaderFieldKey(i)) != null; i++) {
	                            if (headerName.equals("Set-Cookie")) {
	                                String cookie = pConn.getHeaderField(i);
	                                cookie = cookie.substring(0, cookie.indexOf(";"));
	                                String cookieName = cookie.substring(0, cookie.indexOf("="));
	                                String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
	                                cookies.put(cookieName, cookieValue);
	                            }
	                        }
	                    }
	                };
	            }
	        };

	        rpcClient.setTransportFactory(factory);
	        rpcClient.setConfig(config);
	        
			//map of the login data
			Map<String, String> loginMap = new HashMap<String, String>();
			loginMap.put("login", ALMUtils.getProperties("bugzilla.user"));
			loginMap.put("password", ALMUtils.getProperties("bugzilla.password"));
			loginMap.put("rememberlogin", "Bugzilla_remember");

			@SuppressWarnings("unused")
			Object loginResult = null;

			try {
				loginResult = rpcClient.execute("User.login", new Object[]{loginMap});
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}
		
		return rpcClient;
	}
}
