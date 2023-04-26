package com.neves6.piazzapanic.tests.gamemechanisms;

import com.neves6.piazzapanic.gamemechanisms.ReputationPoints;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.gamemechanisms.OrderMaster;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestOrderMaster {
    @Test
    public void testCheckOrderExpiredTrue(){
        Queue<Customer> customers = new LinkedList<>();
        customers.add(new Customer("a", 5, 5, "test1", 1));
        customers.add(new Customer("b", 5, 5, "test2", 2));
        float currentTime = 700;
        assertEquals("Reputation point timer has not updated when an order expired", currentTime, OrderMaster.checkOrderExpired(customers, currentTime, 1, new ReputationPoints(3), 1, 5), 0.0);
    }

    @Test
    public void testCheckOrderExpiredFalse(){
        Queue<Customer> customers = new LinkedList<>();
        customers.add(new Customer("a", 5, 5, "test1", 1));
        customers.add(new Customer("b", 5, 5, "test2", 2));
        float currentTime = 5;
        assertEquals("Reputation point timer has changed but an order has not expired", 400, OrderMaster.checkOrderExpired(customers, currentTime, 1, new ReputationPoints(3), 1, 400), 0.0);
    }

    @Test
    public void testCreateCustomersValidScenario(){
        Queue<Customer> customers = new LinkedList<>();
        float lastCustomer = 0;
        lastCustomer = OrderMaster.createCustomers(customers, 2, 1, 1, 10, 1, 5);
        assertTrue("Customer has not been created", customers.size() > 0);
        assertEquals("Time of last customer creation has not been updated", 5, lastCustomer, 0.0);
    }

    @Test
    public void testCreateCustomersValidEndless(){
        Queue<Customer> customers = new LinkedList<>();
        float lastCustomer = 0;
        lastCustomer = OrderMaster.createCustomers(customers, 2, 1, 1, -1, 3, 5);
        assertTrue("Customer has not been created", customers.size() > 0);
        assertEquals("Time of last customer creation has not been updated", 5, lastCustomer, 0.0);
    }

    @Test
    public void testCreateCustomersInvalid(){
        Queue<Customer> customers = new LinkedList<>();
        for (int i = 0; i <= 10; i++){
            customers.add(new Customer("" + i, 1, 1, "test", 1));
        }
        float lastCustomer = 0;
        lastCustomer = OrderMaster.createCustomers(customers, 2, 1, 11, 20, 1, 5);
        assertTrue("Customer should not have been created", customers.size() <= 11);
        //Will return 5 if it enters the "else" of the stall clause, will return 3 if generation is stalled instead
        assertTrue("Time of last customer creation should not have been updated", lastCustomer == 5 || lastCustomer == 3);
    }
}
