import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        String[] tecleado = (String[]) arg;
        String key = tecleado[1];
        switch(tecleado[0]){
            case "InsertarCar":
                System.out.print(key);
                break;
            case "Reemplazar_caracter":
                // System.out.print(key);
                // break;
            case "Backspace":
                //System.out.print(key);
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
                System.out.print("\033[" + key + "C");
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
