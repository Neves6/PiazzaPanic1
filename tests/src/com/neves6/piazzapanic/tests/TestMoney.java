package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Money;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestMoney {
  Money test = new Money();

  @Test
  public void testAutoGeneration() {
    assertTrue(test.unlockMachine("auto") == false);
  }

  @Test
  public void testIncrement() {
    test.incrementBalance();
    assertTrue(test.displayBalance().equals("Balance: $100"));
  }

  @Test
  public void testInitialValue() {
    assertTrue(test.displayBalance().equals("Balance: $0"));
  }

  @Test
  public void testAddGroupAuto() {
    assertTrue(test.addGroup("auto", 1000) == false);
  }

  @Test
  public void testAddGroupValid() {
    test.addGroup("test", 100);
    assertTrue(test.isUnlocked("test") == false);
  }

  @Test
  public void testValidPurchaseBorder() {
    test.incrementBalance();
    test.addGroup("test", 100);
    assertTrue(test.unlockMachine("test"));
  }

  @Test
  public void testValidPurchase() {
    test.incrementBalance();
    test.addGroup("test", 50);
    assertTrue(test.unlockMachine("test"));
  }

  @Test
  public void testInvalidPurchase() {
    test.incrementBalance();
    test.addGroup("test", 150);
    assertFalse(test.unlockMachine("test"));
  }
}
