package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.gamemechanisms.Tray;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestTray {

  @Test
  public void testAddToTrayDoNothing() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    Stack<String> inventory3 = new Stack<>();
    inventory3.push("ruined burger");
    Stack<String> inventory4 = new Stack<>();
    inventory4.push("hamburger");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);
    Chef testChef3 = new Chef("Jeffff", 5, 5, 5, 5, 5, false, inventory3, 1);
    Chef testChef4 = new Chef("Jefffff", 5, 5, 5, 5, 5, false, inventory4, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    Money testMoney = new Money();

    testTray.putOnTray("cheese");
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue(testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));
    testTray.addToTray(testChef3, testStaff, customers, testMoney);
    assertTrue(testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));
    testTray.addToTray(testChef4, testStaff, customers, testMoney);

    assertTrue(
        "Tray stays unaltered",
        testTray.getList().equals(new ArrayList<>(Arrays.asList("cheese"))));
  }

  @Test
  public void testAddToTrayContentAdd() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("tomato");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue(
        "add chefs inventory to the contents",
        testTray.getList().equals(new ArrayList<>(Arrays.asList("tomato"))));
  }

  @Test
  public void testAddToTrayRuined() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    inventory1.add("lettuce");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue(
        "creates ruined recipe due to too many ingredients",
        testChef1.getInventory().pop().equals("ruined hamburger"));
    assertTrue(testTray.getList().isEmpty());
  }

  @Test
  public void testAddToInventoryComplete() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue("creates recipe", testChef1.getInventory().pop().equals("hamburger"));
    assertTrue("test empty", testTray.getList().isEmpty());
  }

  @Test
  public void testAddToTrayStaff() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testMoney.incrementBalance();
    testMoney.unlockMachine("server-staff");

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);

    assertTrue(testChef1.getInventory().isEmpty());
  }

  @Test
  public void testAddToTrayContentAdd() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("tomato");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue("add chefs inventory to the contents", testTray.getList().equals(new ArrayList<>(Arrays.asList("tomato"))));
    }

  @Test
  public void testAddToTrayRuined() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    inventory1.add("lettuce");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue("creates ruined recipe due to too many ingredients", testChef1.getInventory().pop().equals("ruined hamburger"));
    assertTrue(testTray.getList().isEmpty());

  }

  @Test
  public void testAddToInventoryComplete() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 0f);

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue("creates recipe", testChef1.getInventory().pop().equals("hamburger"));
    assertTrue("test empty", testTray.getList().isEmpty());
  }

  @Test
  public void testAddToTrayStaff() {
    Tray testTray = new Tray();
    Stack<String> inventory1 = new Stack<>();
    inventory1.add("burger");
    inventory1.add("toasted bun");
    Chef testChef1 = new Chef("Jeff", 5, 5, 5, 5, 5, false, inventory1, 1);

    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    DeliveryStaff testStaff = new DeliveryStaff(x, y);
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("bob", 2, 2, "hamburger", 1));
    Money testMoney = new Money();
    testMoney.addGroup("server-staff", 100f);

    testMoney.incrementBalance();
    testMoney.unlockMachine("server-staff");

    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    testTray.addToTray(testChef1, testStaff, customers, testMoney);
    assertTrue("test empty", testTray.getList().isEmpty());
    Queue<Customer> customers1 = new LinkedList<>();
    testChef1.addToInventory("tomato");
    testTray.addToTray(testChef1, testStaff, customers1, testMoney);
    assertTrue("add chefs inventory to the contents", testTray.getList().equals(new ArrayList<>(Arrays.asList("tomato"))));

  }
}