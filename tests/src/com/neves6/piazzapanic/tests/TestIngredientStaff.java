package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestIngredientStaff {
  IngredientsStaff test = new IngredientsStaff(new ArrayList<>(), new ArrayList<>());

  @Test
  public void testSaladStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("salad");
    assertTrue(test.collectItem() == "lettuce");
    assertTrue(test.collectItem() == "tomato");
    assertTrue(test.collectItem() == "onion");
  }

  @Test
  public void testPotatoStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("jacket potato");
    assertTrue(test.collectItem() == "potato");
    assertTrue(test.collectItem() == "beans");
  }

  @Test
  public void testPizzaStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("pizza");
    assertTrue(test.collectItem() == "dough");
    assertTrue(test.collectItem() == "tomato");
    assertTrue(test.collectItem() == "cheese");
  }

  @Test
  public void testBurgerStack() {
    test.setGenerate(true);
    test.setCurrentRecipe("hamburger");
    assertTrue(test.collectItem() == "bun");
    assertTrue(test.collectItem() == "meat");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSaladStack() {
    test.setCurrentRecipe("salad");
    test.generateStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPotatoStack() {
    test.setCurrentRecipe("jacket potato");
    test.generateStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPizzaStack() {
    test.setCurrentRecipe("pizza");
    test.generateStack();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBurgerStack() {
    test.setCurrentRecipe("hamburger");
    test.generateStack();
  }

  @Test
  public void testEmptyStack() {
    test.setGenerate(true);
    assertTrue(test.collectItem() == null);
  }
}
