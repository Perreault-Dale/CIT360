/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.collections;

import byui.cit360.Handler;
import java.util.*;
import byui.cit360.collections.model.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dale
 */
public class ByuiCit360Collections implements byui.cit360.Handler {
    
    public void ByuiCit360Collections() {
        
    }
    
    public static void settest(List<Employee> el) throws IOException {
        
        // convert the List to a Set
        Set<Employee> es = new HashSet<>(el);
        
        // add a new employee into the set - NOTE no choice of position
        Employee ne = new Employee("Patty","Morales",124412,new Date(118,1,8));
        if (es.add(ne)) { System.out.println("Employee Added: " + ne.getFirstName() + " " + ne.getLastName()); }
        
        // walk through the set
        for (Employee e : es) {
            Date cd = new Date(112,1,1);
            System.out.print("Employee: " + e.toString());
            if (e.getHireDate().compareTo(cd) < 0) { System.out.println(" --- Old Timer ---"); }
            else                                   { System.out.println(""); }
        }
        
        // Use our set to create a queue
        queuetest(es);
        
        // Use the set to create a map
        maptest(es);
        
        try {
            // Now let's save to MySQL
            byui.cit360.hibernate.Hibernate.saveRecords(es);
            // Now to query from MySQL and print to screen
            byui.cit360.hibernate.Hibernate.getRecords();
        } catch (Exception ex) {
            Logger.getLogger(ByuiCit360Collections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listtest(Employee[] ea) throws IOException {
        
        // convert the array into a List
        List<Employee> e1 = Arrays.asList(ea);
        List<Employee> el = new ArrayList(e1);
        
        // add a new employee to the end of the list
        Employee ne = new Employee("Graham","Wilson",124400,new Date(118,1,22));
        if (el.add(ne)) { System.out.println("Employee Added: " + ne.getFirstName() + " " + ne.getLastName()); }
        
        // add another employee, this time to the middle of the list
        Employee re = new Employee("Luke","Bonham",123421,new Date(111,8,15));
        el.add((el.size()/2),re);
        
        //walk through the list
        ListIterator<Employee> li = el.listIterator();
        while (li.hasNext()) {
            Date cd = new Date(112,1,1);
            int ni = li.nextIndex();
            Employee e = li.next();
            System.out.print("Index: " + ni + ", Employee: " + e.toString());
            if (e.getHireDate().compareTo(cd) < 0) { System.out.println(" --- Old Timer ---"); }
            else                                   { System.out.println(""); }
        }
        
        // search for and remove an employee
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter a Last Name: ");
        String name = reader.nextLine();
        boolean found = false;
        while (li.hasPrevious() || !found) {
            Employee e = li.previous();
            if (e.getLastName().equals(name)) {
                System.out.print("Employee Found: ");
                System.out.println("Name: " + e.getFirstName() + " " + e.getLastName());
                li.remove();
                System.out.println("Employee removed.");
                found = true;
            }
        }
        System.out.println("Number of employees: " + el.size());
        
        // move on to the next collection test
        settest(el);
    }
    
    public static void maptest(Set<Employee> es) throws IOException {
        
        // Create the map, then populate it
        // Note that we cannot directly create the map from another collection
        Map<Integer,Employee> em = new HashMap<>();
        for (Employee e : es) {
            em.put(e.getEmpNumber(),e);
            if (em.containsValue(e)) {
            } else {
                System.out.println("Map add for " + e.getFirstName() + " " + e.getLastName()
                        + " Failed: Duplicate Employee Number.");
            }
        }
        
        // Print the employee numbers
        Set<Integer> ens = em.keySet();
        System.out.println(ens.toString());
        
        // Now select an employee by number, then print the employee
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter an Employee Number: ");
        String number = reader1.readLine();
        Employee e = em.get(Integer.valueOf(number));
        System.out.println(e.toString());
        byui.cit360.json.json.buildJSON(e);
        
        // Add a new employee from JSON
        Employee ne = byui.cit360.json.json.employeeFromJSON();
        em.put(ne.getEmpNumber(),ne);
        System.out.println("New Employee Added: " + ne.getFirstName() + " " + ne.getLastName());
        
        // use the HashMap to create a TreeMap
        treetest(em);
    }
    
    public static void treetest(Map<Integer,Employee> em) {
        
        // create the TreeMap
        Map<Integer,Employee> etm = new TreeMap<>(em);
        
        // Print the employee numbers - note the order
        Set<Integer> ens = em.keySet();
        Set<Integer> ets = new TreeSet<>(ens);
        System.out.println(ens.toString());
        System.out.println(ets.toString());
        
        // Walk through the TreeMap and print the elements
        for (Map.Entry<Integer,Employee> eme : etm.entrySet()) {
            Integer key = eme.getKey();
            Employee e = eme.getValue();
            String out = key + "\t" + e.getFirstName() + "\t" + e.getLastName()
                             + "\t" + e.getHireDate();
            System.out.println(out);
        }
    }
    
    public static void queuetest(Set<Employee> es) {
        
        // Create a PriorityQueue from the set
        Queue<Employee> eq = new PriorityQueue<>(es);
        
        // Create an ArrayDeque to use later
        Deque<Employee> ed = new ArrayDeque<>(eq);
        
        // Add a new employee
        Employee ne = new Employee("Renee","Evans",123654,new Date(117,11,13));
        if (eq.offer(ne)) { System.out.println("New Employee added: " + ne.getLastName() + ", " + ne.getFirstName()); }
        
        // Empty the queue, and demonstrate its sort order
        while (!(eq.isEmpty())) {
            Employee e = eq.poll();
            System.out.println("Employee: " + e.toString());
        }
        
        // Add another employee, this time as the first item in the ArrayDeque
        Employee nw = new Employee("Emma","James",126487,new Date(117,11,27));
        if (ed.offerFirst(nw)) { System.out.println("New Employee added: " + nw.getLastName() + ", " + nw.getFirstName()); }
        
        // Empty the deque, last-to-first
        // Note that the new employee will not appear in alphaetical order, but at the end
        while (!(ed.isEmpty())) {
            Employee e = ed.pollLast();
            System.out.println("Employee: " + e.toString());
        }
    }
    
    @Override
    public void execute() {
        // create array of employees
        Employee[] workers = new Employee[5];
        workers[0] = new Employee("John","Dangers",123456,new Date(112,12,26));
        workers[1] = new Employee("Jack","Dangers",123457,new Date(112,12,26));
        workers[2] = new Employee("Edward","Olsen",123458,new Date(110,6,13));
        workers[3] = new Employee("Jane","Waters",123459,new Date(114,10,16));
        workers[4] = new Employee("Nancy","Rogers",123460,new Date(113,12,3));
        
        try {
            // use the array to create a list
            listtest(workers);
        } catch (IOException ex) {
            Logger.getLogger(ByuiCit360Collections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        // Setup menu of choices
        AppController ac = new AppController();
        ac.fillMap();
        String choice = "";
        String menu = "CIT360 Menu\r\n"
                    + "===========\r\n\n"
                    + "list - Test Collections, Hibernate\r\n"
                    + "del  - Test Hibernate with SQL\r\n"
                    + "url  - Test HTTPConnection, JSON\r\n"
                    + "thread - Test threading\r\n"
                    + "exit - leave the program\r\n\n"
                    + "Choose the test to run: ";
        
        // Accept and verify user input
        while(!choice.equals("exit")) {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(menu);
            choice = reader1.readLine();
            Set<String> hs = ac.commands.keySet();
            if (hs.contains(choice)) {
                ac.runCommand(choice);
            } else {
                System.out.println("Invalid Choice.");
            }
        }
    }
    
}
