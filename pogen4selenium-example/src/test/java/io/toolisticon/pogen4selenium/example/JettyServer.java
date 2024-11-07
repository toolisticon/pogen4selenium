package io.toolisticon.pogen4selenium.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class JettyServer {

	 private Server server;

     public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9090);
        server.setConnectors(new Connector[] {connector});
        
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
        
        servletHandler.addServletWithMapping(TestPage.class, "/start");
        
        server.start();
    }
     
     public static class TestPage extends HttpServlet {

		private static final long serialVersionUID = 547644172712833066L;
		
		final String content;
     	
     	public TestPage (){
     		String tmpContent = null;
     		try {
     			tmpContent = new String(getClass().getResourceAsStream("/TestPage.html").readAllBytes());
     		} catch (IOException e) {
     			e.printStackTrace();
     			tmpContent = e.toString();
     		}
     		content = tmpContent;
     	}
     	


	    protected void doGet(
	      HttpServletRequest request, 
	      HttpServletResponse response)
	      throws ServletException, IOException {
	 
	        response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.getWriter().println(content);
	    }
	    
	    
	    
	}
     
    public void stop() throws Exception {
    	server.stop();
    }
	
}
