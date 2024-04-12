package com.example.elimatsim;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a GIANT singleton that has relevant MoMo sandbox trial code.
 * All getters are at the bottom of the code. I will write some Java doc explaining each method.
 * Builder or Factory design pattern may be applicable for HttpConnections at the start of the API calls.
 */
public class MoMoAPI {

    private static MoMoAPI instance;
    private final String xReferenceID;
    private final String primaryAPIKey;
    /*
     *  AUTHORIZATION is xReferenceID:API key generated from
     */
    private final String AUTHORIZATION;
    /*
     *  Access Token is later used to access other API calls.
     */
    private String ACCESS_TOKEN;

    private MoMoAPI() {
        UUID uuid = UUID.randomUUID();
        xReferenceID = uuid.toString();
        primaryAPIKey = "2b6ecd81af2a4e999557cc9192e2dd00";
        apiUserCreate();
        if (!apiKeyCreate().isEmpty()) {
            String apiKey = keyExtractor(apiKeyCreate());
            AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString((getXReferenceID() +
                    ":" + apiKey).getBytes());
            ACCESS_TOKEN = tokenExtractor(createAccessToken());
        } else {
            Log.i("AUTH", "AUTHORIZATION NOT SET");
            AUTHORIZATION = "";
        }

    }

    /**
     * Given an HTTP response, extract api key alone
     *
     * @param input String formatted like so;
     *              HTTP/1.1 201 Created
     *              content-length: 45
     *              content-type: application/json;charset=utf-8
     *              date: Fri, 12 Apr 2024 01:29:49 GMT
     *              vary: Origin
     *              x-xss-protection: 1; mode=block
     *              {
     *              "apiKey": "e21aca34f1d8478d8e1a3bb33a2c1a3e"
     *              }
     * @return e21aca34f1d8478d8e1a3bb33a2c1a3e
     */
    private String keyExtractor(String input) {
        String regex = "\"apiKey\"\\s*:\\s*\"([a-fA-F0-9]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Check if the matcher finds a match
        if (matcher.find()) {
            return matcher.group(1);
        }
        Log.i("Key Extractor:", "API Key not found.");
        return "";
    }

    private String tokenExtractor(String input){
        String regex = "\"access_token\"\\s*:\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Check if the matcher finds a match
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    /**
     * This method creates a user for the sandbox.
     */
    private void apiUserCreate() {
        try {
            Log.i("API User:", "Creating API User");
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
            connection.getOutputStream().write("{ providerCallbackHost: \"string\" }".getBytes());
            int status = connection.getResponseCode();
            Log.i("HTTP Status", String.valueOf(status));

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            Log.i("API User: ", "Created");
        } catch (Exception ex) {
            Log.e("exception:", "API User Create");
        }
    }

    /**
     * Used to create an API key for an API user (xReferenceID) in the sandbox target environment.
     */
    private String apiKeyCreate() {
        try {
            Log.i("API Key:", "Creating API Key");
            String urlString = "https://sandbox.momodeveloper.mtn.com/v1_0/apiuser/" + getXReferenceID() + "/apikey";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());
            connection.setRequestMethod("POST");

            int status = connection.getResponseCode();
            Log.i("HTTP Status Code:", String.valueOf(status));

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            Log.i("API Key:", "API Key created");
            return String.valueOf(content);
        } catch (Exception ex) {
            Log.e("exception:", "Failed to Create API Key");
        }
        return "";
    }

    public String createAccessToken() {
        try {
            Log.i("Create Access Token:", "Creating Access Token");
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/token/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", getAUTHORIZATION());
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());
            connection.setRequestMethod("POST");

            int status = connection.getResponseCode();
            Log.i("HTTP Status", String.valueOf(status));

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            // System.out.println(content);
            connection.disconnect();
            Log.i("Access Token", "Created");
            return String.valueOf(content);
        } catch (Exception ex) {
            Log.e("exception:", "Create Access Token");
        }
        return "";
    }

    /**
     * This operation is used to claim a consent by the account holder for the requested scopes.
     *
     * @return The string representing the result of the HTTP request.
     */
    public String bcAuthorize() {
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/v1_0/bc-authorize";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", getAUTHORIZATION());
            connection.setRequestProperty("X-Target-Environment", "sandbox");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());
            connection.setRequestMethod("POST");

            // Request body
            connection.setDoOutput(true);
            connection.getOutputStream().write("login_hint=ID:{msisdn}/MSISDN&scope={scope}&access_type={online/offline}".getBytes());
            int status = connection.getResponseCode();
            Log.i("HTTP Status Code:", String.valueOf(status));

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return String.valueOf(content);
        } catch (Exception ex) {
            Log.e("BC-Authorize", "Something's wrong");
        }
        return "";
    }

    /**
     * Get the balance of own account.
     *
     * @return A String representing the account balance.
     */
    public String getAccountBalance() {
        try {
            Log.i("Account Balance:", "Getting Account Balance");
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/v1_0/account/balance";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", getAUTHORIZATION());
            connection.setRequestProperty("X-Target-Environment", "sandbox");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", getPrimaryAPIKey());
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            Log.i("HTTP code:", String.valueOf(status));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return String.valueOf(content);
        } catch (Exception ex) {
            Log.e("Get Account Balance:", "Try Failed");
        }
        return "";
    }

    private String getXReferenceID() {
        return xReferenceID;
    }

    private String getPrimaryAPIKey() {
        return primaryAPIKey;
    }

    private String getAUTHORIZATION() {
        return AUTHORIZATION;
    }

    public static MoMoAPI getInstance() {
        if (instance == null) instance = new MoMoAPI();
        return instance;
    }
}
