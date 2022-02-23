public class Ticket {
    private String fName;
    private String lName;
    private String destination;
    private int priority;
    private double price;

    public Ticket(String destination, String fName, String lName, int priority) {
        this.destination = destination;
        this.fName = fName;
        this.lName = lName;
        this.priority = priority;
        this.price = priority == 1 ? 1.00 : 4.00;
    }

    public String getDest(){
        return this.destination;
    }

    public String getName(){
        return this.fName + " " + this.lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}