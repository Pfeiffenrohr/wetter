package de.richardlechner.wetterTest;


import java.net.URI;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.*;

import de.richardlechner.wetter.HalloWeltService;

public class HalloWeltServiceTest
{
   @Test
   public void testRESTfulWebService()
   {
      String baseUrl        = "http://localhost:4434";
      String webContextPath = "/helloworld";
      String name           = "MeinName";

      // Testserver:
      HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create( baseUrl ), new ResourceConfig( HalloWeltService.class ) );

      try {
         // Testclient:
         Client c = ClientBuilder.newClient();
         WebTarget target = c.target( baseUrl );

         // Pruefungen:
         String txt = target.path( webContextPath ).queryParam( "name", name ).request( MediaType.TEXT_PLAIN ).get( String.class );
         String htm = target.path( webContextPath ).queryParam( "name", name ).request( MediaType.TEXT_HTML ).get( String.class );
         Assert.assertEquals( "Plain-Text: Hallo MeinName", txt );
         Assert.assertEquals( "<html><title>HelloWorld</title><body><h2>Html: Hallo MeinName</h2></body></html>", htm );

      } finally {
         // Testserver beenden:
         server.shutdown();
      }
   }
}