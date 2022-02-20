import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {

    //inString, but with confirm
    public static String inConfirm(String inMsg){
        String s="";
        boolean c=false;
        try (Scanner in = new Scanner(System.in);) {
            do {
                System.out.print(inMsg);
                s = in.nextLine();
                c = yesNo("Hai inserito " + s + ". È corretto? (S/N) ");
            } while (!c);
        } catch (Exception e) {
            System.out.println("An error occured.\n"+e);
        }

        return s;
    }

    public static String inString(String inMsg){
        String s="";
        try (Scanner in = new Scanner(System.in);) {
            System.out.print(inMsg);
            s = in.nextLine();
        } catch (Exception e) {
            System.out.println("An error occured.\n"+e);
        }

        return s;
    }

    //Ritorna true se l'input = s oppure S; false se l'input = n oppure N.

    public static Boolean yesNo(String inMsg){
        //Che alla fine è in italiano quindi S/N
        boolean yn = false;
        String userInput = "";
        boolean e = false;
        try (Scanner in = new Scanner(System.in)) {
            do {
                System.out.print(inMsg);
                e=false;
                userInput = in.nextLine();
                if (userInput.equalsIgnoreCase("s") || userInput.equalsIgnoreCase("n")) {
                    yn = userInput.equalsIgnoreCase("s"); //Ritorna true/false.
                } else {
                    e=true;
                }
            } while (e);
        } catch (Exception ex) {
            System.out.println("An error occured.\n"+ex);
        }
        
        return yn;
    }

    //Chiede in input una stringa e vede se è intero.

    public static int inInt(String inMsg){
        boolean err = false;
        String userInput = "";
        int out=0;
        do {
            err=false;
            try {
                userInput = inString(inMsg);
                Integer.parseInt(userInput);
            } catch (Exception e) {
                err=true;
                System.out.println("Inserisci un numero! Riprova. ");
            }
            out = err ? 0 : Integer.parseInt(userInput);
        } while (err);

        return out;
    }

    public static int inIntConfirm(String inMsg) {
        boolean err = false;
        String userInput = "";
        int out=0;
        do {
            err=false;
            try {
                userInput = inConfirm(inMsg).trim();
                Integer.parseInt(userInput);
            } catch (Exception e) {
                err=true;
                System.out.println("Valore non valido! Riprova. ");
            }

            out = err ? 0 : Integer.parseInt(userInput);

        } while (err);

        return out;
    }

    public static int inPositiveInt(String inMsg){
        int out=0;
        do{
            out = inInt(inMsg);
            if (out < 0) {
                System.out.println("Valore non valido! Riprova.");
            }
        }while(out < 0);
        return out;
    }

    public static void enterToContinue() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Premi enter per continuare . . .");
            in.nextLine();
        } catch (Exception e) {
            System.out.println("An error occured.\n"+e);
        }
    }

    //Display a simple menu
    public static int menu(String[] options){
        boolean v = false;
        int s = 0;

        while (!v) {
            for (int i = 0; i < options.length; i++) {
                System.out.println( "[ "+(i+1)+" ] "+options[i]+"\n");
            }

            s = InputUtils.inInt("Seleziona un'opzione: ");

            v = !(s<1 || s > options.length);   
        }

        return s;
    }

    //Gets file and reads first line, returns array with strings
    public static String[] readAndTokenizeLine(File f, String token){
        String[] data = {};

        //Crea il file se non lo trova già
        try (Scanner s = new Scanner(f)){

            if (f.createNewFile()) { //il metodo createNewFile ritorna true se il file non era presente, altrimenti ritorna false se il file è gia presente
                System.out.println("File non trovato! Creando...");
            }

            //Splits the line in a string array
            if (s.hasNextLine()) {
              data = s.nextLine().split(token);  
            }
        } catch (Exception e) {
            System.out.println("File r/w error. "+e);
        }
        return data;
    }

    //Request user input for date and time using formatter
    public static LocalDateTime inDateTime(String inMsg, String formatPattern, DateTimeFormatter formatter){
        LocalDateTime dateTime = LocalDateTime.now();
        boolean v = false;

        while (!v) {     
            String userInput = inString(inMsg + " ("+ formatPattern + ")" + ": ");
            
            try {
                dateTime = LocalDateTime.parse(userInput, formatter);
                v=true;
            } catch (Exception e) {
                System.out.println("Data non valida! Riprova.");
               v = false;
            }
        }
        
        return dateTime;
    }

    //Request user input for date and time using formatter
    public static LocalDate inDate(String inMsg, String formatPattern, DateTimeFormatter formatter){
        LocalDate dateTime = LocalDate.now();
        boolean v = false;

        while (!v) {     
            String userInput = inString(inMsg + " ("+ formatPattern + ")" + ": ");
            
            try {
                dateTime = LocalDate.parse(userInput, formatter);
                v=true;
            } catch (Exception e) {
                System.out.println("Data non valida! Riprova.");
               v = false;
            }
        }
        
        return dateTime;
    }

    //Input string of a length
    public static String inStringLen(int n, String inMsg){
        boolean v = false;
        
        String s = "";       
        while (!v) {
            s = inString(inMsg);
            if (s.length() > n) {
                System.out.println("Valore non valido! Riprova.");
            } else {
                v = true;
            }
        }
        return s;
    }

    //Input string of a length with confirm
    public static String inStringLenConfirm(int n, String inMsg){
        String s = "";
        boolean c=false; // S = true | N = false
        boolean v = false;
        
        do {
            while (!v) {
                s = inString(inMsg);
                if (s.length() != n) {
                    System.out.println("Valore non valido! Riprova.");
                } else {
                    v = true;
                }
            }
            c = yesNo("Hai inserito " + s + ". È corretto? (S/N) ");
        } while (!c);

        
        
        return s;
    }
}
