package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.staff.DeliveryStaff;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestDeliveryStaff {
    DeliveryStaff test = new DeliveryStaff(new ArrayList<>(), new ArrayList<>());

    @Test
    public void testCollect(){
        test.collectItem("pizza");
        assertTrue(test.getCollect());
    }

    @Test
    public void testPickup(){
        test.collectItem("pizza");
        Stack<Object> testStack = new Stack<>();
        testStack.add("pizza");
        assertTrue(test.getItems().equals(testStack));
    }

}
