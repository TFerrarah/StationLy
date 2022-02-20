import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class NoFreeSeats extends Exception {
    public NoFreeSeats(String err){
        super(err);
    }
}

public class Train {
    private ArrayList<Ticket> seats;
    private ArrayList<String> stations;
    private File file;

    public int getFreeSeats() {
        // Collections.frequency returns how many times the obj in second argument is
        // preset in arrayList
        return Collections.frequency(seats, null);
    }

    public void addTicket(Ticket t) throws NoFreeSeats{
        if (getFreeSeats() == 0) {
            System.out.println("Non ci sono posti disponibili.");
            //Throw exception
            throw new NoFreeSeats("errore nella richiesta di posto");
        }
        // Add ticket to first empty (null) position
        seats.set(seats.indexOf(null), t);
    }

    public ArrayList<Ticket> getTickets() {
        return seats;
    }

    public Train(ArrayList<String> stations, int max, File f) {
        this.file = f;
        this.seats = new ArrayList<>(Arrays.asList(new Ticket[max]));
        this.stations = stations;
    }
}
