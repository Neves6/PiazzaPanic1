package com.neves6.piazzapanic.tests.stafftests;

import static org.junit.Assert.*;

import com.neves6.piazzapanic.staff.IngredientsStaff;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestIngredientStaff {
  IngredientsStaff test = new IngredientsStaff(new ArrayList<>(), new ArrayList<>());

  @Test
  public void testSaladStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("salad");
    assertSame("For a salad, staff should give out lettuce first.", "lettuce", test.collectItem());
    assertSame("For a salad, staff should give out tomato second.", "tomato", test.collectItem());
    assertSame("For a salad, staff should give out onion third.", "onion", test.collectItem());
  }

  @Test
  public void testPotatoStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("jacket potato");
    assertSame(
        "For a jacket potato, staff should give out a potato first", "potato", test.collectItem());
    assertSame(
        "For a jacket potato, staff should give out beans second", "beans", test.collectItem());
  }

  @Test
  public void testPizzaStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("pizza");
    assertSame("For a pizza, staff should give out dough first.", "dough", test.collectItem());
    Assert.assertSame(
        "For a pizza, staff should give out tomato seconds.", "tomato", test.collectItem());

    assertSame("For a pizza, staff should give out cheese third", "cheese", test.collectItem());
  }

  @Test
  public void testBurgerStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("hamburger");
    assertSame("For a burger, staff should give out a bun first.", "bun", test.collectItem());
    assertSame("For a burger, staff should give out meat first.", "meat", test.collectItem());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSaladStack() {
    // Generate stack for an invalid recipe.
    test.setCurrentRecipe("beer");
    test.generateStack();
  }

  @Test
  public void testEmptyStack() {
    test.setGenerate(true);
    assertNull("If no ingredients need to be given out, it must return null.", test.collectItem());
  }
}
