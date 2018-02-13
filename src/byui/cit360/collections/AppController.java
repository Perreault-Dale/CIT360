/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.collections;
import byui.cit360.Handler;
import java.util.HashMap;

/**
 *
 * @author Dale
 */
public class AppController {
    public static HashMap<String,Handler> commands = new HashMap<>();
    
    public void AppController(){
    }
    
    public static void fillMap() {
        commands.put("list", new ByuiCit360Collections());
        commands.put("url", new byui.cit360.url.url());
        commands.put("exit", new exit());
    }
    
    public static void runCommand(String cmd) {
        Handler handle = commands.get(cmd);
        handle.execute();
    }
}
