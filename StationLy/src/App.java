import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class NoFreeSeats extends Exception {
    public NoFreeSeats(String errorMessage) {
        super(errorMessage);
    }
}

public class App {

    // Display train ascii art
    public static void trainArt() {
        System.out.println(
                "                   o  o  O  O\n             ,_____  ____    O\n             | PMD \\_|[]|_'__Y\n             |_______|__|_|__|}\n=============oo--oo==oo--OOO'\\\\====================");
    }

    // clear console
    public static void clrscrn() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        trainArt();
        System.out.println("              Station.ly version 1.2");
    }

    public static void main(String[] args) throws Exception { // throws IOException, NoFreeSeats
        // Inizialmente avevo pensato di creare la classe "Stazione"
        // Station[6] stations = {new Station("Bari"), new Station("Foggia"), new
        // Station("Pescara"), ...}

        // Però la classe avrebbe avuto solo una proprietà: Il nome.

        // Quindi ho fatto un semplice array di Stringhe

        ArrayList<String> stations = new ArrayList<>(
                Arrays.asList("Bari", "Foggia", "Pescara", "Ancona", "Bologna", "Milano"));

        // Condizione per l'uscita dal programma (o l'esplosione del treno 😏)

        boolean exit = false;

        // Max seats for a train
        final int MAX_SEATS = 4;

        // Get date and time as string for logfile
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));

        // returns 20022022131754

        // Initialize directories
        File testFile = new File("./StationLy/logs");
        testFile.mkdirs();

        // Initialize train obj
        Train trenoBellissimo = new Train(stations, MAX_SEATS, new File("./StationLy/logs/log_" + dateTime + ".txt"));


        String oldPos = "";
        do {
            for (String st : stations) {
                // Salva posizione per evitare reiterazioni inutili
                if (oldPos.equals(st)) {
                    continue; // salta iterazione del foreach se la stazione viene ripetuta più volte
                }

                oldPos = st;

                // Titolone supremo
                clrscrn();
                System.out.println("Ti trovi a " + st);

                // Definizione delle opzioni del menù

                // Opzione 1 - E il viaggio continua... | Mostra sempre

                // Opzione 2 - Crea un nuovo ticket | Mostra solo se getFreeSeats() > 0

                // Opzione 3 - Ferma il treno e termina il programma | Mostra solo se st = Bari
                // o Milano

                ArrayList<String> options = new ArrayList<>(Arrays.asList("Prossima fermata."));

                // Solo se ci sono posti liberi
                if (trenoBellissimo.getFreeSeats() > 0) {
                    options.add("Crea nuovo ticket");
                }

                // Solo se sei ad un capolinea (Prima o ultima stazione) e il file di log non è vuoto
                if ((st.equals(stations.get(0)) || st.equals(stations.get(stations.size() - 1))) && !trenoBellissimo.hasJustStarted()) {
                    // Chiedi di fermare il trambo
                    options.add("Fine della corsa.");
                }

                // Optioni inizializzate con successo.

                // Scrittura su file

                // Scrivi la posizione corrente
                trenoBellissimo.writePos(st);
                System.out.println("Scritto nel file "+st);

                // Controlla chi deve scendere
                trenoBellissimo.drop(st);

                // Mostra il menu

                int sel = InputUtils.menuArrayList(options);

                switch (sel) {
                    case 1:
                        // manda avanti il treno
                        break;
                    case 2:
                        // Il case 2 può essere sia "Crea un nuovo ticket", che "Fine della corsa."
                        // è stato creato questo controllo un po' hardcoded per evitare scelte non
                        // corrispondenti
                        if (options.get(1).equals("Crea nuovo ticket")) {
                            // Chiedere se inserire nuovo biglietto

                            // Condizione per chiedere se inserire nuovo ticket
                            boolean addNew = true;

                            while (trenoBellissimo.getFreeSeats() > 0 && addNew) {
                                System.out
                                        .println("Ci sono ancora " + trenoBellissimo.getFreeSeats() + " posti liberi");
                                if (InputUtils.yesNo("Vuoi creare un nuovo biglietto? (S/N)")) {
                                    // Crea un nuovo biglietto

                                    // Chiedi nome
                                    String name = InputUtils.inString("Nome: ");
                                    // Chiedi cognome
                                    String sname = InputUtils.inString("Cognome: ");
                                    // Chiedi destinazione
                                    boolean destValid = true;
                                    boolean sameDest = true;
                                    String dest = "";
                                    do {
                                        dest = InputUtils.inString("Destinazione: ");

                                        // Capitalize user input
                                        // Capitalize first char
                                        dest = dest.substring(0, 1).toUpperCase()
                                                + dest.toLowerCase().substring(1, dest.length());

                                        // Find dest in existing destinations
                                        destValid = stations.contains(dest);
                                        if (!destValid) {
                                            System.out.println("Destinazione non valida. Riprova\n");
                                        }

                                        // check if destination entered is equal to current station
                                        sameDest = !dest.equals(st);
                                        if (!sameDest) {
                                            System.out.println("Bro sei già a destinazione. Riprova");
                                        }

                                        if (dest.equalsIgnoreCase("napoli")) {
                                            System.out.println(
                                                    "Non sarebbe il massimo far derubare i passeggieri.....evitiamo");
                                        }

                                        if (dest.equalsIgnoreCase("caserta")) {
                                            System.out.println(
                                                    "C'è un luogo e un tempo per bruciare le scuole, \nma non è questo il tempo o il luogo!");
                                        }
                                    } while (!destValid || !sameDest);

                                    // Chiedi priority
                                    String[] priorities = { "Economy", "Business" };
                                    System.out.println("Priorità:");
                                    int priority = InputUtils.menu(priorities);

                                    // Tutti i dati sono stati recuperati

                                    // Aggiungi il nuovo ticker
                                    trenoBellissimo.addTicket(new Ticket(dest, name, sname, priority));
                                } else {
                                    addNew = false;
                                }

                            }
                        } else {
                            System.out.println("Attenzione!\nStai per terminare l'esecuzione del programma.");
                            exit = InputUtils.yesNo("Vuoi davvero farlo? (S/N)");
                            System.out.println(exit ? "Fermo il treno e salvo i dati...\nNon spegnere il computer."
                                    : "Annullato!");
                        }
                        break;

                    case 3:
                        System.out.println("Attenzione!\nStai per terminare l'esecuzione del programma.");
                        exit = InputUtils.yesNo("Vuoi davvero farlo? (S/N)");
                        System.out.println(
                                exit ? "Fermo il treno e salvo i dati...\nNon spegnere il computer." : "Annullato!");
                        break;
                    default:
                        System.out.println("E tu che ci fai qui?");
                        break;
                }
            }
            Collections.reverse(stations);
        } while (!exit);

        clrscrn();
        trenoBellissimo.fileWrite.write("mammt");
        System.out.println("padania liberata");
    }
}