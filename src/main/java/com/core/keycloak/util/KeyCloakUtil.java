package com.core.keycloak.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import model.KeyCloakConfig;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

/**
 * Created by totvs on 01/09/2016.
 */
public class KeyCloakUtil {
    private static KeyCloakConfig keyCloakConfig;

    /**@since 02/09/2016
     * @author Diorge Jorge
     * Made by hand "getAcessToken"
     * @param user system user as {@link String}
     * @param  password system_user_password {@link String}
     * @return returns a token using the keycloak api as "bearer" + token"
     * @throws Exception
     */
    public static String getToken(String user,String password) throws Exception {
        keyCloakConfig=propertiesLoader();
        URL url = UriBuilder.fromUri(keyCloakConfig.getUrl_servidor())
                .path("realms").path(keyCloakConfig.getRealm()).path("protocol/openid-connect/token").build().toURL();
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("grant_type", keyCloakConfig.getGrant_type());
        params.put("client_id", keyCloakConfig.getClient_id());
        params.put("username", user);
        params.put("password", password);
        params.put("client_secret", keyCloakConfig.getClient_secret());
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        while ((line = reader.readLine()) != null) {
            JsonElement jelem = gson.fromJson(line, JsonElement.class);
            json = jelem.getAsJsonObject();
        }
        return "bearer " + json.get("access_token").toString().replace("\"", "");

    }

    /**@since 02/09/2016
     * @author Diorge Jorge
     * Returns a {@link KeyCloakConfig} dto reading the app.properties
     * @return {@link KeyCloakConfig} dto reading the app.properties
     */
    public static KeyCloakConfig propertiesLoader() {
        InputStream input = KeyCloakUtil.class.getClassLoader().getResourceAsStream("app.properties");

        Properties prop = new Properties();
        KeyCloakConfig keyCloakConfig = new KeyCloakConfig();
        try {
            prop.load(input);

            keyCloakConfig.setGrant_type(prop.get("grant_type").toString());
            keyCloakConfig.setClient_id(prop.get("client_id").toString());
            keyCloakConfig.setClient_secret(prop.get("client_secret").toString());
            keyCloakConfig.setUrl_servidor(prop.get("urlServidor").toString());
            keyCloakConfig.setRealm(prop.get("realm").toString());
            return keyCloakConfig;
        } catch (IOException e) {
            return null;
        }
    }

    /**@since 02/09/2016
     * @author Diorge Jorge
     * Returns a Bearer token using the embededd keycloak api
     * @param user system user {@link String}
     * @param password system user password {@link String}
     * @return returns a token using the keycloak api as "bearer" + token"     */
    public static String getTokenByApi(String user,String password) {
        Keycloak keycloak = Keycloak.getInstance(
                propertiesLoader().getUrl_servidor(),
                propertiesLoader().getRealm(),
                user,
                password,
                propertiesLoader().getClient_id(),
                propertiesLoader().getClient_secret());
        return "bearer " + keycloak.tokenManager().getAccessTokenString();
    }


}
