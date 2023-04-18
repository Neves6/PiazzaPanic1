package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.gamemechanisms.Tray;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.people.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(GdxTestRunner.class)
public class TestTray {

    @Test
    public void testAddToTrayDoNothing(){
        Tray testTray = new Tray();
        Stack<String> inventory1 = new Stack<>();
        Stack<String> inventory3 = new Stack<>();
        inventory3.push("ruined burger");
        Stack<String> inventory4 = new Stack<>();
        inventory4.push("hamburger");
        Chef testChef1 = new Chef("Jeff", 5,5,5,5,5,false,inventory1, 1);
        Chef testChef3 = new Chef("Jeffff", 5,5,5,5,5,false,inventory3, 1);
        Chef testChef4 = new Chef("Jefffff", 5,5,5,5,5,false,inventory4, 1);


        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();

        DeliveryStaff testStaff = new DeliveryStaff(x, y);
        Queue<Customer> customers = new LinkedList<>();
        Money testMoney = new Money();

        testTray.putOnTray("cheese");
        testTray.addToTray(testChef1,testStaff,customers, testMoney);
        assertTrue(testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));
        testTray.addToTray(testChef3,testStaff,customers, testMoney);
        assertTrue(testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));
        testTray.addToTray(testChef4,testStaff,customers, testMoney);
        assertTrue(testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));

    }
}
