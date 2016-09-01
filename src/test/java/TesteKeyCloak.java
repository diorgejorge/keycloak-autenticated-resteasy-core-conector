import com.core.keycloak.util.KeyCloakUtil;
import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
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
    public void testOk() {

        try {

            ClientRequest request = new ClientRequest(
                    "http://localhost:8180/cobranca-service/service/teste");
            request.header("Authorization", KeyCloakUtil.getToken(KeyCloakUtil.propertiesLoader().getProperty("urlServidor"),"basic-auth"));
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 200;
            }  catch (Exception e) {

            e.printStackTrace();

        }

    }
    @Test
    public void testDenied() {

        try {

            ClientRequest request = new ClientRequest(
                    "http://localhost:8180/cobranca-service/service/teste");
            request.header("Authorization", KeyCloakUtil.getToken(KeyCloakUtil.propertiesLoader().getProperty("urlServidor"),"basic-auth")+"0");
            ClientResponse<String> response = request.get(String.class);
            assert response.getStatus() == 401;


        }  catch (Exception e) {

            e.printStackTrace();

        }

    }

}
