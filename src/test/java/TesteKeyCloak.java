import com.core.keycloak.util.KeyCloakUtil;
import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by totvs on 01/09/2016.
 */
public class TesteKeyCloak {
    static final int RESPONSE_OK = 200;
    static final int DENIED = 401;

    @Test
    /**
     * Tests the service
     */
    public void testOk() throws  Exception{


            ClientRequest request = new ClientRequest(
                    KeyCloakUtil.propertiesLoader().getProject_service_url());
            request.header("Authorization", KeyCloakUtil.getToken("user","password"));
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 200;



    }
    @Test
    public void testDenied() throws Exception {

            ClientRequest request = new ClientRequest(
                    KeyCloakUtil.propertiesLoader().getProject_service_url());
            request.header("Authorization", KeyCloakUtil.getToken("user","password")+"0");
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 401;


    }

    @Test
    public void testOkApi() throws Exception {
            ClientRequest request = new ClientRequest(
                    KeyCloakUtil.propertiesLoader().getProject_service_url());
            request.header("Authorization", KeyCloakUtil.getTokenByApi("user","password"));
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 200;
    }
    @Test
    public void testDeniedApi() throws Exception {
            ClientRequest request = new ClientRequest(
                    KeyCloakUtil.propertiesLoader().getProject_service_url());
            request.header("Authorization", KeyCloakUtil.getTokenByApi("user","password")+"0");
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 401;

    }

}
