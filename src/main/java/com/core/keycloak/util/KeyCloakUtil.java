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
import java.net.URI;
import java.util.Properties;

/**
 * Created by totvs on 01/09/2016.
 */
public class KeyCloakUtil {
    static JsonObject json = new JsonObject();
    public static String getToken(String servidorRealmsUrl,String realm) throws Exception{
        URL url = UriBuilder.fromUri(servidorRealmsUrl).path(realm).path("/protocol/openid-connect/token").build().toURL();
        Map<String,Object> params = new LinkedHashMap<String,Object>();
        params.put("grant_type", "password");
        params.put("client_id", "basic-auth-service");
        params.put("username", "user");
        params.put("password", "password");
        params.put("client_secret", "password");
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        while ((line = reader.readLine()) != null) {
            JsonElement jelem = gson.fromJson(line, JsonElement.class);
            json = jelem.getAsJsonObject();
        }
        return  "bearer "+json.get("access_token").toString().replace("\"","");

    }
     public static Properties propertiesLoader(){
         InputStream input = KeyCloakUtil.class.getClassLoader().getResourceAsStream("app.properties");

         Properties prop = new Properties();
         try {
             prop.load(input);

             return prop;
         } catch (IOException e) {
             return null;
         }
    }

}
