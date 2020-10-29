import java.io.*;
import java.lang.String;

public class EditableBufferedReader extends BufferedReader { //Controller

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
        int numleido = -1; //Iniciamos con un -1 ya que este entero no se corresponde con ningún carácter
        try {
        int entrada = super.read();
        if(entrada != Dictionary.ESC){ //Leemos primero los carácteres 'normales' y el backspace ya que son directos
            if(entrada == Dictionary.BACKSPACE){ // y entraría directamente en esta condición. 
                return Dictionary.TECLA_BACKSPACE;
            } else {
                numleido = entrada;
            } 
        } else {                        //Si no es así, como en el caso de las flechas vendríamos hasta aquí.
            entrada = super.read();     //Todos estos carácteres tienen en común que se concatenan ^[ y [
            entrada = super.read();
            switch(entrada) {           // Y los podemos diferenciar por el último 'símbolo'. Véase las constantes en 'Dictionary'
                case Dictionary.SUPRIMIR: // Delete es un caso especial, ya que después de este último 'símbolo' diferencial
                    int numleido2 = super.read(); //incorpora un ~ que lo comprobamos con numleido2
                    if (numleido2 == Dictionary.TILDE){
                        numleido = Dictionary.TECLA_SUPRIMIR;
                    }
                    break;
                case Dictionary.INSERT: //En mi caso, con MacOs, el INSERT será la flecha hacia arriba
                    return Dictionary.TECLA_INSERT; 
                case Dictionary.DERECHA:
                    return Dictionary.FLECHA_DERECHA;
                case Dictionary.IZQUIERDA:
                    return Dictionary.FLECHA_IZQUIERDA;
                case Dictionary.HOME:
                    return Dictionary.TECLA_HOME;
                case Dictionary.END:
                    return Dictionary.TECLA_FIN;
                default:
                    System.err.print("Invalid input:)");
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
            switch(entrada){ //En este switch simplemente indicamos la orden que ha de seguir el programa por las 
                case Dictionary.TECLA_BACKSPACE: //diferentes teclas leídas, con sus correspondientes limitaciones.
                    if(linea.getPosCursor() > 0) {
                        linea.backspace();
                    }
                    break;
                case Dictionary.TECLA_SUPRIMIR:
                    if(linea.getPosCursor() < linea.getLength()){
                        linea.delete();
                    }
                    break;
                case Dictionary.TECLA_HOME:
                    linea.home();
                    break;
                case Dictionary.TECLA_FIN:
                    linea.end();
                    break;
                case Dictionary.FLECHA_DERECHA:
                    if(linea.getPosCursor() < linea.getLength()){
                        linea.mover_derecha();
                    }
                    break;
                case Dictionary.FLECHA_IZQUIERDA:
                    if(linea.getPosCursor() != 0){
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