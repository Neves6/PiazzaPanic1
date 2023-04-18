package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.neves6.piazzapanic.people.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * things to test :) constructor validity x coord is set y coord is set erroneous coords throws
 * error move right move left move down move up order is set textures exist
 */
@RunWith(GdxTestRunner.class)
public class TestCustomer {
  @Test
  public void testCustomerConstructorValid() {
    int x = 6;
    int y = 9;
    String name = "39";
    String order = "chronological";
    float time = 100;
    Customer testCustomer = new Customer(name, x, y, order, time);
    assertTrue(
        "Customer Constructor Valid :D",
        testCustomer.getxCoord() == x
            && testCustomer.getxCoord() == 6
            && testCustomer.getyCoord() == y
            && testCustomer.getyCoord() == 9
            && testCustomer.getName().equals(name)
            && testCustomer.getName().equals("39")
            && testCustomer.getOrder().equals(order)
            && testCustomer.getOrder().equals("chronological")
            && testCustomer.getTimeArrived() == time);
  }


  @Test
  public void testGetTxUp() {
    int x = 6;
    int y = 9;
    String name = "39";
    String order = "chronological";
    float time = 100;
    Customer testCustomer = new Customer(name, x, y, order, time);
    Texture txUp = new Texture("people/cust1up.png");
    assertEquals("Customer up facing texture is not correct", txUp.toString(), testCustomer.getTxUp().toString());
  }

  @Test
  public void testGetTxLeft() {
    int x = 6;
    int y = 9;
    String name = "39";
    String order = "chronological";
    float time = 100;
    Customer testCustomer = new Customer(name, x, y, order, time);
    Texture txLeft = new Texture("people/cust1left.png");
    assertEquals("Customer left facing texture is not correct", txLeft.toString(), testCustomer.getTxLeft().toString());
  }
}
