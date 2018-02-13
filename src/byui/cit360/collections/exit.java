/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.collections;
import byui.cit360.Handler;

/**
 *
 * @author Dale
 */
public class exit implements Handler {
    @Override
    public void execute() {
        System.out.println("Bye!");
    }
}
