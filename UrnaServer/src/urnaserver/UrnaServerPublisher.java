package urnaserver;

import javax.xml.ws.Endpoint;
/**
 *
 * @author Admin
 */
public class UrnaServerPublisher {
    public static void main(String[] args)
  {
    Endpoint.publish("http://127.0.0.1:9876/urnaserver",
    new UrnaServerElectionImpl());
    
    Endpoint.publish("http://127.0.0.2:9876/urnaserver",
    new UrnaSecurityImpl());
  }
}
