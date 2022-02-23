import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    FileWriter fileWrite;

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

    public void writePos(String st) throws IOException{
        fileWrite.flush();
        System.out.println("DEBUG: Scrivo su file la posizione...");
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.fileWrite.write("["+now+"] Arrivato in stazione di "+st+"\n");
        InputUtils.enterToContinue();
    }

    //TODO: Funzione che fa scendere tutti quelli che devono scendere
        //Ritorna un ArrayList di Ticket che contiene i ticket scesi.
    

    public Train(ArrayList<String> stations, int max, File f) throws IOException {
        this.file = f;
        this.fileWrite = new FileWriter(f);
        this.seats = new ArrayList<>(Arrays.asList(new Ticket[max]));
        this.stations = stations;
    }
}
