package Memento;

/**
 * Created by Kesem Even Hen on 26/05/2019.
 * Caretaker class of Memento DP.
 */
public class Caretaker {
    private final CityMemento[] statesList = new CityMemento[3];
    private static int size = 0;

    /**
     * the object that keeps track of multiple memento.
     */
    public Caretaker(){
        for (int i = 0; i < 3; ++i)
            statesList[i] = null;
    }

    public void addMemento(CityMemento m, int index) {
        if(statesList[index] == null) {
            statesList[index] = m;
            ++size;
        }
    }

    public void removeMemento(int index) {
        if (statesList[index] != null) {
            statesList[index] = null;
            --size;
        }
    }
    public CityMemento getMemento(int index) {
        return statesList[index];
    }

    public boolean notFull(){return size < 3;}

    public boolean notEmpty(){return size != 0;}
}

