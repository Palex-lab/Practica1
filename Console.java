import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {

    private Line linea;

    public Console(Line value) {
        this.linea = value;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub

    }

    
}
