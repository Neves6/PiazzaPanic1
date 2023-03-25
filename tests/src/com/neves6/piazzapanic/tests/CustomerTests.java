package com.neves6.piazzapanic.tests;
import com.neves6.piazzapanic.people.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
/** 
 * things to test :)
 * constructor validity
 * x coord is set
 * y coord is set
 * erroneous coords throws error
 * move right
 * move left
 * move down
 * move up
 * order is set
 * textures exist
 */
@RunWith(GdxTestRunner.class)
public class CustomerTests {
    @Test
    public void testCustomerConstructorValid(){
        int x=6;
        int y=9;
        String name="39";
        String order="chronological";
        float time = 100;
        Customer testCustomer=new Customer(name, x, y, order, time);
        assertTrue("Customer Constructor Valid :D",
        testCustomer.getxCoord() == x &&
        testCustomer.getxCoord() == 6 &&
        testCustomer.getyCoord() == y &&
        testCustomer.getyCoord() == 9 &&
        testCustomer.getName().equals(name) &&
        testCustomer.getName().equals("39") &&
        testCustomer.getOrder().equals(order) &&
        testCustomer.getOrder().equals("chronological") &&
        testCustomer.getTimeArrived() == time
        );
    }
}
