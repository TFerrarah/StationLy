public class App {
    public static void main(String[] args) {
       //Inizialmente avevo pensato di creare la classe "Stazione"
       //Station[6] stations = {new Station("Bari"), new Station("Foggia"), new Station("Pescara"), ...}

       //Però la classe avrebbe avuto solo una proprietà: Il nome.

       //Quindi ho fatto un semplice array di Stringhe

       String[] stations = {"Bari", "Foggia", "Pescara", "Ancona", "Bologna", "Milano" };

       for (String st : stations) {
           System.out.println(st+"\n");
       }
    }
}