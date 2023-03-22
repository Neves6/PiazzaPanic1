package com.neves6.piazzapanic.tests;

import static org.junit.Assert.*;

import com.neves6.piazzapanic.gamemechanisms.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestMoney {
  Money test = new Money();

  @Test
  public void testAutoGeneration() {

    assertFalse("By default, the auto unlock id should be unlocked.", test.unlockMachine("auto"));
  }

  @Test
  public void testIncrement() {
    test.incrementBalance();

    Assert.assertEquals(
        "Increment balance should add 100 to the balance and should be displayed in the format"
            + " 'Balance: $x'",
        "Balance: $100",
        test.displayBalance());
  }

  @Test
  public void testInitialValue() {
    assertEquals(
        "Initial balance should be 0 and display in the format 'Balance: $0'",
        "Balance: $0",
        test.displayBalance());
  }

  @Test
  public void testAddGroupAuto() {
    assertFalse("Any pre-existing groups should not be overridden", test.addGroup("auto", 1000));
  }

  @Test
  public void testAddGroupValid() {
    test.addGroup("test", 100);
    assertFalse("By default, any new groups added should be locked.", test.isUnlocked("test"));
  }

  @Test
  public void testValidPurchaseBorder() {
    test.incrementBalance();
    test.addGroup("test", 100);
    assertTrue(
        "Machine should be unlocked if the user has enough money even it leads to them having a 0"
            + " balance.",
        test.unlockMachine("test"));
  }

  @Test
  public void testValidPurchase() {
    test.incrementBalance();
    test.addGroup("test", 50);
    assertTrue(
        "Machine should be unlocked if the user has enough money", test.unlockMachine("test"));
  }

  @Test
  public void testInvalidPurchase() {
    test.incrementBalance();
    test.addGroup("test", 150);
    assertFalse(
        "Machine should not be unlocked if there is not enough money", test.unlockMachine("test"));
  }
}
