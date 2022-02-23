import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

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

    public void addTicket(Ticket t) throws NoFreeSeats, IOException{
        if (getFreeSeats() == 0) {
            System.out.println("Non ci sono posti disponibili.");
            //Throw exception
            throw new NoFreeSeats("Non ci sono posti disponibili.");
        }
        // Add ticket to first empty (null) position
        seats.set(seats.indexOf(null), t);
        
        //Log ticket
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String str = "["+now+"] Creato un biglietto: \n\tNome: "+t.getfName()+"\n\tCognome: "+t.getlName()+"\n\tPriority: Level "+t.getPriority()+"\n\tDestinazione: "+t.getDest()+"\n";
        this.fileWrite.write(str);
        fileWrite.flush();
    }

    public ArrayList<Ticket> getTickets() {
        return seats;
    }

    public void writePos(String st) throws IOException{
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.fileWrite.write("["+now+"] Arrivato in stazione di "+st+"\n");
        fileWrite.flush();
    }

    //Funzione che fa scendere tutti quelli che devono scendere
        //Ritorna un ArrayList di Ticket che contiene i ticket scesi.
    public ArrayList<Ticket> drop(String st) throws IOException{
        ArrayList<Ticket> dropped = new ArrayList<>();
        for (Ticket ticket : this.seats) {
            if (ticket !=null && ticket.getDest().equals(st)) {
                dropped.add(ticket);
            }
        }
        this.seats.removeAll(dropped);

        //Logging
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        if (!dropped.isEmpty()) {
            String str = "["+now+"] Ticket fuori dal treno: ";
            ArrayList<String> names = new ArrayList<>();
            for (Ticket ticket : dropped) {
                names.add(ticket.getName());
            }
            str = str + names.toString();
            this.fileWrite.write(str+"\n");
        }
        fileWrite.flush();
        return dropped;
    }

    public boolean hasJustStarted() throws FileNotFoundException{
        Scanner r = new Scanner(this.file);
        boolean res = !r.hasNextLine();
        r.close();
        return res;
    }

    public void end() throws IOException{
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String str = "["+now+"] padania liberata.\nStation.Ly 1.0.4\nCreated by T_Ferrarah and Luca-Landri and SuperBoyR3d\nFine del file di LOG.";
        fileWrite.write(str);
        fileWrite.flush();
    }
    

    public Train(ArrayList<String> stations, int max, File f) throws IOException {
        this.file = f;
        this.fileWrite = new FileWriter(f);
        this.seats = new ArrayList<>(Arrays.asList(new Ticket[max]));
        this.stations = stations;
    }
}
