package de.richardlechner.wetter;


import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class HalloWeltTestServer
{
   public static void main( String[] args ) throws IOException, InterruptedException 
   {
      String baseUrl = ( args.length > 0 ) ? args[0] : "http://localhost:4434";

      final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create( baseUrl ), new ResourceConfig( HalloWeltService.class ), false );
      Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {
         @Override
         public void run() {
            server.shutdownNow();
         }
      } ) );
      server.start();

      System.out.println( String.format( "\nGrizzly-HTTP-Server gestartet mit der URL: %s\n"
                                         + "Stoppen des Grizzly-HTTP-Servers mit:      Strg+C\n",
                                         baseUrl + HalloWeltService.webContextPath ) );

      Thread.currentThread().join();
   }
}
