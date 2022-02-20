public class App {

    public static void trainArt() {
        System.out.println("            Welcome to Station.ly");
        System.out.println(
                "                   o  o  O  O\n             ,_____  ____    O\n             | PMD \\_|[]|_'__Y\n             |_______|__|_|__|}\n=============oo--oo==oo--OOO'\\\\====================");
    }


    public static void main(String[] args) {
        // Inizialmente avevo pensato di creare la classe "Stazione"
        // Station[6] stations = {new Station("Bari"), new Station("Foggia"), new
        // Station("Pescara"), ...}

        // Però la classe avrebbe avuto solo una proprietà: Il nome.

        // Quindi ho fatto un semplice array di Stringhe
        
        String[] stations = { "Bari", "Foggia", "Pescara", "Ancona", "Bologna", "Milano" };

        // Condizione per l'uscita dal programma (o l'esplosione del treno 😏)

        boolean exit = false;

        // Max seats for a train
        final int MAX_SEATS = 10;
        
        // Initialize train obj
        trainArt();
        Train trenoBellissimo = new Train(stations, MAX_SEATS);
        // do {
        //     for (String st : stations) {
        //         System.out.println("Ti trovi a " + st);
        //     }
        // } while (!exit);

    }
}