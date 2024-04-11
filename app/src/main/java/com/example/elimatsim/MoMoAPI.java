package com.example.elimatsim;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * This class is a GIANT singleton that has relevant MoMo sandbox trial code.
 * All getters are at the bottom of the code. I will write some Java doc explaining each method.
 * Builder or Factory design pattern may be applicable.
 */
public class MoMoAPI {
    private static MoMoAPI instance;
    private final String xReferenceID;
    private final String primaryAPIKey;
    private final String AUTHORIZATION;

    private MoMoAPI() {
        UUID uuid = UUID.randomUUID();
        xReferenceID = uuid.toString();
        primaryAPIKey = "2b6ecd81af2a4e999557cc9192e2dd00";
        apiUserCreate();
        if (!getAPICredentials().isEmpty()) {
            AUTHORIZATION = getAPICredentials();
        } else {
            Log.i("AUTH", "AUTHORIZATION NOT SET");
            AUTHORIZATION = "";
        }
    }

    private void apiUserCreate() {
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/v1_0/apiuser";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("X-Reference-Id", getXReferenceID());

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());

            connection.setRequestMethod("POST");

            // Request body
            connection.setDoOutput(true);
            connection
                    .getOutputStream()
                    .write(
                            "{ providerCallbackHost: \"string\" }".getBytes()
                    );

            int status = connection.getResponseCode();
            Log.i("HTTP Status", String.valueOf(status));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);

            connection.disconnect();
            Log.i("API User: ", "Created");
        } catch (Exception ex) {
            Log.e("exception:", Objects.requireNonNull(ex.getMessage()));
        }
    }

    private String getAPICredentials() {
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/v1_0/apiuser/XXXXX";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "2b6ecd81af2a4e999557cc9192e2dd00");

            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Log.i("API Credentials", "Credentials fetched.");

            connection.disconnect();
            return content.toString();
        } catch (Exception ex) {
            Log.e("exception:", "Failed to Get API Credentials.");
        }
        return "";
    }


    public String createAccessToken() {
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/token/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", getAUTHORIZATION());

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());

            connection.setRequestMethod("POST");

            int status = connection.getResponseCode();
            Log.i("HTTP Status", String.valueOf(status));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            // System.out.println(content);
            connection.disconnect();
            return content.toString();
        } catch (Exception ex) {
            Log.e("exception:", "Failed to connect");
        }
        return "Failed to connect";
    }

    private String getXReferenceID() {
        return xReferenceID;
    }

    private String getPrimaryAPIKey() {
        return primaryAPIKey;
    }

    public String getAUTHORIZATION() {
        return AUTHORIZATION;
    }

    public static MoMoAPI getInstance() {
        if (instance == null)
            instance = new MoMoAPI();
        return instance;
    }
}
