package de.richardlechner.wetter;

/*https://stackoverflow.com/questions/2145431/https-using-jersey-client*/


import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.http.HttpStatus;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class HalloWeltTestClient {
    public static void main(String[] args) {
        String username = "username";
            String password = "p@ssword";
        String input = "{\"userId\":\"12345\",\"name \":\"Viquar\",\"surname\":\"Khan\",\"Email\":\"Vaquar.khan@gmail.com\"}";
        try {
        //SSLContext sc = SSLContext.getInstance("SSL");//Java 6
        SSLContext sc = SSLContext.getInstance("TLSv1");//Java 8
        System.setProperty("https.protocols", "TLSv1");//Java 8


        TrustManager[] trustAllCerts = { new InsecureTrustManager() };
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HostnameVerifier allHostsValid = new InsecureHostnameVerifier();

        Client client = ClientBuilder.newBuilder().sslContext(sc).hostnameVerifier(allHostsValid).build();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder()
                    .credentialsForBasic(username, password).credentials(username, password).build();


            client.register(feature);
       
            WebTarget webtarget = client.target("https://www.wunderground.com");
            webtarget.path("/weatherstation/WXDailyHistory.asp");
            webtarget.queryParam("ID", "IOBHOHEN20");
            webtarget.queryParam("day", "14");
            webtarget.queryParam("month", "8");
            webtarget.queryParam("year", "2018");
            webtarget.queryParam("graphspan", "day");
            webtarget.queryParam("format", "1");
            //client.target("sdfsdf").queryParam(name, values)
            
            
                    //PUT request, if need uncomment it 
                    //final Response response = client
                    //.target("https://localhost:7002/VaquarKhanWeb/employee/api/v1/informations")
                    //.request().put(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);
            //GET Request
            //final Response response = client
             //       .target("https://localhost:7002/VaquarKhanWeb/employee/api/v1/informations")
             //       .request().get();

            final Response response = webtarget.request().get();


            if (response.getStatus() != HttpStatus.OK.value()) { throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus()); }
            String output = response.readEntity(String.class);
            System.out.println("Output from Server .... \n");
            System.out.println(output);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}