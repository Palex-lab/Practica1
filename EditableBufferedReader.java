import java.io.*;
import java.lang.String;

public class EditableBufferedReader extends BufferedReader {

    private Line linea;
    private Console console;

    public EditableBufferedReader(Reader in){
        super(in);
        this.linea = new Line();
        this.console = new Console();
        this.linea.addObserver(this.console);
    }
    
    public void setRaw() {
        try{
            String[] cmd = {"/bin/sh","-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(Exception e) { System.out.println("Error setRaw()");} 
    }

    public void unsetRaw(){
        try{
            String[] cmd = {"/bin/sh","-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(Exception e) { System.out.println("Error unsetRaw()");} 
    }
    
    public int read() {
        int numleido = -1;
        try {
        int entrada = super.read();
        if(entrada != Dictionary.ESC){
            if(entrada == Dictionary.BACKSPACE){
                return Dictionary.TECLA_BACKSPACE;
            } else {
                numleido = entrada;
            } 
        } else {
            entrada = super.read();
            entrada = super.read();
            switch(entrada) {
                case Dictionary.DELETE:
                    int numleido2 = super.read();
                    if (numleido2 == Dictionary.TILDE){
                        numleido = Dictionary.TECLA_DELETE;
                    }
                    break;
                case Dictionary.INSERT:
                    int numleido3 = super.read();
                    if (numleido3 == Dictionary.TILDE){
                        numleido = Dictionary.TECLA_INSERT;
                    }
                    break;
                case Dictionary.DERECHA:
                    return Dictionary.FLECHA_DERECHA;
                case Dictionary.IZQUIERDA:
                    return Dictionary.FLECHA_IZQUIERDA;
                case Dictionary.HOME:
                    return Dictionary.TECLA_HOME;
                case Dictionary.END:
                    return Dictionary.TECLA_FIN;
                default:
                    System.err.println("Invalid input:)");
                    break;
            }
         }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numleido;
    } 

    public String readLine() throws IOException {
        this.setRaw();
        int entrada = this.read();
        while(entrada != Dictionary.ENTER_1 && entrada != Dictionary.ENTER_2){ //Nueva línea y retorno de carro
            switch(entrada){
                case Dictionary.TECLA_BACKSPACE:
                    if(linea.getPos() > 0) {
                        linea.backspace();
                    }
                    break;
                case Dictionary.TECLA_DELETE:
                    if(linea.getPos() < linea.getLength()){
                        linea.delete();
                    }
                    break;
                case Dictionary.TECLA_HOME:
                    linea.home();
                    break;
                case Dictionary.TECLA_FIN: //AIXO NO ESTAVA AL DE L'ALEXIS -> APAREIX el "ߖ"
                    linea.end();
                    break;
                case Dictionary.FLECHA_DERECHA:
                    if(linea.getPos() < linea.getLength()){
                        linea.mover_derecha();
                    }
                    break;
                case Dictionary.FLECHA_IZQUIERDA:
                    if(linea.getPos() != 0){
                        linea.mover_izquierda();
                    }
                    break;
                case Dictionary.TECLA_INSERT:
                    linea.insertarMode();
                    break;
                default:
                    if(linea.getMode()){
                        linea.insertar_caracter((char) entrada);
                    } else {
                        linea.reemplazar_caracter((char) entrada);
                    }
                    break;
            }
            entrada = this.read();
        }
        this.unsetRaw();
        return linea.getLine().toString();
    }

    

}