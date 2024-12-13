package org.wilczewski.wash;

import org.wilczewski.threads.CarThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CarWash {
    ArrayList<WashStand> washStandsList;
    Queue<CarThread> queue1;
    Queue<CarThread> queue2;
    int queue1Size = 3;
    int queue2Size = 3;
    int washStands = 4;

    public CarWash() {
        washStandsList = new ArrayList<>();
        for(int i = 0; i < washStands; i++){
            washStandsList.add(new WashStand());
            if(i==0){
                washStandsList.getLast().getWashesList().add(new Wash("water"));
                washStandsList.getLast().getWashesList().add(new Wash("soap"));
            }
            else if(i==washStands-1){
                washStandsList.getLast().getWashesList().add(washStandsList.get(i-1).getWashesList().get(2));
                washStandsList.getLast().getWashesList().add(washStandsList.get(i-1).getWashesList().get(3));
            }
            else if(i==1){
                washStandsList.getLast().getWashesList().add(washStandsList.get(0).getWashesList().get(0));
                washStandsList.getLast().getWashesList().add(washStandsList.get(0).getWashesList().get(1));
                washStandsList.getLast().getWashesList().add(new Wash("water"));
                washStandsList.getLast().getWashesList().add(new Wash("soap"));
            }
            else {
                washStandsList.getLast().getWashesList().add(washStandsList.get(i-1).getWashesList().get(2));
                washStandsList.getLast().getWashesList().add(washStandsList.get(i-1).getWashesList().get(3));
                washStandsList.getLast().getWashesList().add(new Wash("water"));
                washStandsList.getLast().getWashesList().add(new Wash("soap"));
            }
        }
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public ArrayList<WashStand> getWashStandsList() {
        return washStandsList;
    }

    public void setWashStandsList(ArrayList<WashStand> washStandsList) {
        this.washStandsList = washStandsList;
    }

    public Queue<CarThread> getQueue1() {
        return queue1;
    }

    public void addToQueue1(CarThread car) {
        this.queue1.add(car);
    }

    public void removeFromQueue1() {
        this.queue1.remove();
    }

    public Queue<CarThread> getQueue2() {
        return queue2;
    }

    public void addToQueue2(CarThread car) {
        this.queue2.add(car);
    }

    public void removeFromQueue2() {
        this.queue2.remove();
    }

    public int getQueue1Size() {
        return queue1Size;
    }

    public void setQueue1Size(int queue1Size) {
        this.queue1Size = queue1Size;
    }

    public int getQueue2Size() {
        return queue2Size;
    }

    public void setQueue2Size(int queue2Size) {
        this.queue2Size = queue2Size;
    }

}
