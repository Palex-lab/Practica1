import java.util.Observable;
import java.util.Observer;

public class Console implements Observer { //View

    @Override
    public void update(Observable o, Object arg) {
        String[] tecleado = (String[]) arg;
        String key = tecleado[1];
        switch(tecleado[0]){
            case "InsertarCar":
                System.out.print(key);
                break;
            case "Reemplazar_caracter":

            case "Backspace":

            case "Mover_derecha":
                System.out.print(key);
                break;
            case "Mover_izquierda":
                System.out.print(key);
                break;
            case "Mover_home":
                System.out.print(key);
                break;
            case "Mover_end":
                System.out.print(key);
                break;
            case "Delete":
                System.out.print(key);
                break;
            default:
                System.err.println("Error");;
            break;
        }
    }

    
}
