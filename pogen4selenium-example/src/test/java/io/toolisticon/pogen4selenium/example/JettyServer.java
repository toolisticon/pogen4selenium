package io.toolisticon.pogen4selenium.example;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHandler;

public class JettyServer {

	 private Server server;

     public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9090);
        server.setConnectors(new Connector[] {connector});
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
         
        ServletHandler servletHandler = new ServletHandler();
        context.setHandler(servletHandler);
        
        servletHandler.addServletWithMapping(TestPage.class, "/start");
        servletHandler.addServletWithMapping(LinkedPageA.class, "/linkA");
        servletHandler.addServletWithMapping(LinkedPageB.class, "/linkB");
        
        
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
     
     public static class LinkedPageA extends HttpServlet {

 		private static final long serialVersionUID = 547644172712833066L;
 		
 		final String content;
      	
      	public LinkedPageA (){
      		String tmpContent = null;
      		try {
      			tmpContent = new String(getClass().getResourceAsStream("/LinkedPageA.html").readAllBytes());
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
     
     public static class LinkedPageB extends HttpServlet {

  		private static final long serialVersionUID = 547644172712833066L;
  		
  		final String content;
       	
       	public LinkedPageB (){
       		String tmpContent = null;
       		try {
       			tmpContent = new String(getClass().getResourceAsStream("/LinkedPageB.html").readAllBytes());
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
