package com.appdynamics.sample.resource;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mark.prichard on 8/25/16.
 */
public class ControllerRESTCall {
    private final URL baseUrl;
    private final String account;
    private final String username;
    private final String password;

    public ControllerRESTCall(URL baseUrl, String account, String username, String password) {
        this.baseUrl = baseUrl;
        this.account = account;
        this.username = username;
        this.password = password;
    }

    // Make GET request to baseUrl + path
    public String getRESTResponse(String path){
        return getDataFromServer(path);
    }

    private String getDataFromServer(String path) throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(baseUrl + path);
            URLConnection urlConnection = setUsernamePassword(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private URLConnection setUsernamePassword(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String basicAuthString = username + "@" + account + ":" + password;
        String basicAuthStringBase64 = new String(Base64.encodeBase64(basicAuthString.getBytes()));
        urlConnection.setRequestProperty("Authorization", "Basic " + basicAuthStringBase64);
        return urlConnection;
    }
}
