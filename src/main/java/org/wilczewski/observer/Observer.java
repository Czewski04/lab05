package org.wilczewski.observer;

public interface Observer {
    void joinedToQ1(int carId);
    void joinedToQ2(int carId);
    void enteredToStand(int carId, int state, int fromQueue);
    void leavedStand(int carId, int state);
    void washedStageStart(int carId, int state, String name);
    void washedStageEnd(int carId, int state, String name);
}
