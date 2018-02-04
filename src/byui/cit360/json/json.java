/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.json;

import org.json.simple.JSONObject;
import byui.cit360.collections.model.Employee;
import java.util.Date;
import java.util.Map;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dale
 */
public class json {
    
    public static void buildJSON (Employee e) {
        JSONObject obj = new JSONObject();
        obj.put("first_name", e.getFirstName());
        obj.put("last_name", e.getLastName());
        obj.put("emp_number", e.getEmpNumber());
        obj.put("hire_date", e.getHireDate());
        System.out.println(obj);
    }
    
    public static Employee employeeFromJSON() {
        // Create a blank Employee object
        Employee e = new Employee();
        
        // Build a JSON object
        JSONObject obj = new JSONObject();
        obj.put("firstName", "Matthew");
        obj.put("lastName", "Simmons");
        obj.put("empNumber", 123532);
        obj.put("hireDate", new Date(111,4,27));
        
        // Convert the JSON to a Map
        Map<String,Object> em = obj;
        
        // Set the Employee object using the map
        e.setFirstName((String) em.get("firstName"));
        e.setLastName((String) em.get("lastName"));
        e.setEmpNumber((Integer) em.get("empNumber"));
        e.setHireDate((Date) em.get("hireDate"));
        
        //Return the new employee object
        return e;
    }
}
