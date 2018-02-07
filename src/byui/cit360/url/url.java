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

/**
 *
 * @author Dale
 */
public class url {
    static Integer acctNum = 123456;
    static String userName = "bossman";
    static String apiKey = "ehf9phf1jhefpfhj1epfhc1078y14r3fh";
    String apiToken;
    
    public static void getToken() {
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
        
        JSONObject response = new JSONObject();
        
        // Print as a test
        System.out.println(authObj);
        
        try {
            // Now create a URL connection
            URL identURL = new URL("https://identity.api.rackspacecloud.com/v2.0/tokens");
            URLConnection connect = identURL.openConnection();
            connect.setRequestProperty("Content-type", "application/json");
            connect.setDoOutput(true);
            
            // Send the JSON with credentials
            OutputStreamWriter out = new OutputStreamWriter(connect.getOutputStream());
            out.write(authObj.toJSONString());
            out.close();
            
            // Get the response
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    connect.getInputStream()));
            response = (JSONObject) in.lines();
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(url.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Print the response
        System.out.println(response);
    }
    
}
