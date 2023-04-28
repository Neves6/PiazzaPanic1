package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.powerups.DoubleMoney;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestDoubleMoney {
  DoubleMoney testDoubleMoney = new DoubleMoney(1000L, "test");
  Money testMoney = new Money();

  @Test
  public void testApplyInactive() {
    testDoubleMoney.applyPowerUp(testMoney);
    assertTrue(
        "Amount should not change if the power-up is inactive",
        testMoney.displayBalance().equals("Balance: $0.0"));
  }

  @Test
  public void testApplyActive() {
    testDoubleMoney.acquirePowerUp();
    testDoubleMoney.applyPowerUp(testMoney);
    assertTrue(
        "Amount should not change if the power-up is inactive",
        testMoney.displayBalance().equals("Balance: $100.0"));
  }
}
