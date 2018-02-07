/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.url;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Dale
 */
public class url {
    static Integer acctNum = null;
    static String userName = null;
    static String apiKey = null;
    static String apiToken = null;
    
    public static void getToken() {
        // Get input from user
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter User Name: ");
        try {
            userName = reader1.readLine();
        } catch (IOException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Enter API Key: ");
        try {
            apiKey = reader1.readLine();
        } catch (IOException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Create inner JSON with credentials
        JSONObject creds = new JSONObject();
        creds.put("username",userName);
        creds.put("apiKey",apiKey);
        
        // Create another inner JSON 
        JSONObject authType = new JSONObject();
        authType.put("RAX-KSKEY:apiKeyCredentials", creds);
        
        // Create final JSON entry
        JSONObject authObj = new JSONObject();
        authObj.put("auth", authType);
        JSONParser jParse = new JSONParser();
        String response = null;
        
        // Print as a test
        System.out.println(authObj);
        
        try {
            // Now create a URL connection
            URL identURL = new URL("https://identity.api.rackspacecloud.com/v2.0/tokens");
            HttpsURLConnection connect = (HttpsURLConnection) identURL.openConnection();
            connect.setRequestProperty("Content-type", "application/json");
            connect.setRequestMethod("POST");
            connect.setDoOutput(true);
            connect.setDoInput(true);
            
            // Send the JSON with credentials
            OutputStreamWriter out = new OutputStreamWriter(connect.getOutputStream());
            out.write(authObj.toString());
            out.close();
            
            // Print the response code from the server
            System.out.println("Response from server: " + connect.getResponseCode() + " " + connect.getResponseMessage());
            
            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            response = in.readLine();
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Print the response
        JSONObject outJson = null;
        try {
            outJson = (JSONObject) jParse.parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        }
        // System.out.println(outJson);
        
        // Capture the account number and API token
        JSONObject subJson = (JSONObject) outJson.get("access");
        JSONObject tokenJson = (JSONObject) subJson.get("token");
        apiToken = (String) tokenJson.get("id");
        JSONObject tenantJson = (JSONObject) tokenJson.get("tenant");
        acctNum = Integer.valueOf((String)tenantJson.get("id"));
        System.out.println("Account Number: " + acctNum);
        System.out.println("API Token: " + apiToken);
    }
    
    
}
