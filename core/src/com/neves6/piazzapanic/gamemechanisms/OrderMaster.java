package com.neves6.piazzapanic.gamemechanisms;

import static java.util.Arrays.asList;

import com.neves6.piazzapanic.people.Customer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public final class OrderMaster {
  static final ArrayList<String> RECIPES =
      new ArrayList<>(asList("salad", "hamburger", "jacket potato", "pizza"));
  /** Utility constructor. SHOULD NOT BE INITIALIZED! */
  public OrderMaster() {}

  /**
   * Checks if time allowed to complete any customer orders has elapsed Removes customers whose
   * order expired and removes a reputation point
   */
  public static float checkOrderExpired(
      Queue<Customer> customers,
      float totalTimerDisplay,
      int difficulty,
      int reputationPoints,
      int customersServed,
      float lastRepPointLost) {
    int timeAllowed = Math.max(110 - 15 * (customersServed / 5), 90) - (10 * difficulty);
    for (int i = 0; i < customers.size(); i++) {
      if (customers.peek().getTimeArrived() + timeAllowed < totalTimerDisplay) {
        customers.poll();
        reputationPoints -= 1;
        lastRepPointLost = totalTimerDisplay;
      }
    }
    return lastRepPointLost;
  }

  /**
   * Creates 1-3 customers, initially skewed towards 1 but favours 3 as the total number of
   * customers served increases Will occasionally 0.5s stalls to vary customer arrival times
   */
  public static float createCustomers(
      Queue<Customer> customers,
      float lastCustomer,
      int customersServed,
      int customersGenerated,
      int maxCustomers,
      int difficulty,
      float totalTimer) {
    float waitTime = (float) Math.max(2.5 - 0.5 * (customersServed / 5F), 0.5);
    if (lastCustomer + waitTime <= totalTimer) {
      /*
      Random chance to stall next customer's arrival to vary customer arrival times
      Stalls scaled by difficulty to make harder difficulties slightly faster paced
      Chance to stall and time stalled by difficulty:
          Easy    30%   1s
          Medium  20%   0.75s
          Hard    10%   0.5s
      */

      float randomFloat = ThreadLocalRandom.current().nextFloat();
      if (customers.size() != 0 && randomFloat > (0.9 - 0.1 * (3 - difficulty))) {
        lastCustomer += 0.5 + 0.25 * (3 - difficulty);
      } else {
        int partySize = generatePartySize(customersServed);
        for (int i = 0; i < partySize; i++) {
          // Max number of customers in the queue starts at 5, increases by 1 every 5 served
          // Queue caps at 10 customers
          if (customers.size() < Math.min(5 + (customersServed / 5), 10)
              && (maxCustomers == -1 || (maxCustomers > 0 && customersGenerated < maxCustomers))) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, 4);
            customers.add(
                new Customer(
                    "Customer" + (customers.size() + 1),
                    -1,
                    -1,
                    RECIPES.get(randomInt),
                    totalTimer));
            customersGenerated += 1;
          } else {
            break;
          }
        }
        lastCustomer = totalTimer;
      }
    }
    return lastCustomer;
  }

  /**
   * Randomly generates a value 1 to 3 dependent on the number of customers served to be used as
   * group sizes Initially biased towards 1 but gradually shifts in favour of 3
   *
   * @return integer value 1 to 3
   */
  private static int generatePartySize(int customersServed) {
    /*
    Creates a random party size of 1-3 customers
    The more customers that have been served, the more likely a larger group becomes
    Probabilities of group size for each interval of customers served:
        0-4:    1 = 80%,    2 = 20%,    3 = 0%
        5-9:    1 = 40%,    2 = 40%,    3 = 20%
        10-14:  1 = 0%,     2 = 60%,    3 = 40%
        15-19:  1 = 0%,     2 = 40%,    3 = 60%
        20-24:  1 = 0%,     2 = 20%,    3 = 80%
        25+:    1 = 0%,     2 = 0%,     3 = 100%
    */
    float randomFloat = ThreadLocalRandom.current().nextFloat();
    int partySize;
    if (randomFloat <= (0.8 - 0.4 * (customersServed / 5F))) {
      partySize = 1;
    } else if (randomFloat > (0.8 - 0.4 * (customersServed / 5F))
        && randomFloat <= (1 - 0.2 * (customersServed / 5F))) {
      partySize = 2;
    } else {
      partySize = 3;
    }
    return partySize;
  }
}
