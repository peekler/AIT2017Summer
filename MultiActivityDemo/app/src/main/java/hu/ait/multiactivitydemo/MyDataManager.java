package hu.ait.multiactivitydemo;

public class MyDataManager {

    private MyDataManager(){
    }

    private static MyDataManager instance = null;

    public static MyDataManager getInstance() {
        if (instance == null) {
            instance = new MyDataManager();
        }

        return instance;
    }

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
