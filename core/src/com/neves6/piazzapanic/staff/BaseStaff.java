package com.neves6.piazzapanic.staff;

import java.util.ArrayList;
import java.util.Arrays;

/** All staff need some form of animation which
 * can be handled from the base class.
 */
public class BaseStaff {
    Long time = 0L;
    ArrayList<Integer> xSequence;
    ArrayList<Integer> ySequence;
    int counter;
    boolean collect;

    /**
     * Constructor method that takes 2 arrays which must be of the
     * same length. Each index represent the nth value in the sequence of
     * movement which must take place.
     * @param xSequence List of x coordinates in order.
     * @param ySequence List of y coordinates in order.
     */
    public BaseStaff(ArrayList<Integer> xSequence, ArrayList<Integer> ySequence){
        if (xSequence.size() != ySequence.size()){
            throw new IllegalArgumentException();
        }
        this.xSequence = xSequence;
        this.ySequence = ySequence;
        // To count what frame we are up to.
        this.counter = -1;
    }

    /***
     * Gets the coordinate based upon the time since the last movement (to
     * ensure smoothness), as well as whether you have reached the end.
     * Once the end is reached, reset the collect flag so that no transition takes
     * place anymore.
     * @return A pair of coordinates in the format [x, y].
     */
    public ArrayList<Integer> getCoordInSeq(){
        if (counter + 1 >= xSequence.size()){
            counter = 0;
            this.collect = false;
        } else if (System.currentTimeMillis() - time > 500){
            counter ++;
            time = System.currentTimeMillis();
        }
        return new ArrayList<>(Arrays.asList(xSequence.get(counter), ySequence.get(counter)));
    }

    /**
     * Getter method.
     * @return Whether the inventory needs updating.
     */
    public boolean getCollect() {
        return this.collect;
    }
}
