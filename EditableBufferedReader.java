import java.io.*;
import java.lang.String;

public class EditableBufferedReader extends BufferedReader {

    public static final int ESC = 27;
    public static final int BACKSPACE = 127;
    public static final int DERECHA = 67;
    public static final int IZQUIERDA = 68;
    public static final int ARRIBA = 65;
    public static final int ABAJO = 66;
    public static final int HOME = 72;
    public static final int END = 70;
    public static final int INSERT = 50;
    public static final int DELETE = 51;
    public static final int CORCHETE = 91;
    public static final int TILDE = 126; //Símbolo ~
    public static final int ENTER = 13;

    public static final int SECUENCIA_DERECHA = Integer.MIN_VALUE + 1;
    public static final int SECUENCIA_IZQUIERDA = Integer.MIN_VALUE + 2;
    public static final int SECUENCIA_ARRIBA = Integer.MIN_VALUE + 3;
    public static final int SECUENCIA_ABAJO = Integer.MIN_VALUE + 4;
    public static final int SECUENCIA_HOME = Integer.MIN_VALUE + 5;
    public static final int SECUENCIA_FIN = Integer.MIN_VALUE + 6;
    public static final int SECUENCIA_INSERT = Integer.MIN_VALUE + 7;
    public static final int SECUENCIA_DELETE = Integer.MIN_VALUE + 8;
    public static final int SECUENCIA_CARACTER = Integer.MIN_VALUE + 9;     

    public EditableBufferedReader(InputStreamReader in){
        super(in);
    }
    
    public void setRaw(){
        try{
            String[] cmd = {"/bin/sh","-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e) { System.out.println("Error setRaw()");} 
    }

    public void unsetRaw(){
        try{
            String[] cmd = {"/bin/sh","-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e) { System.out.println("Error unsetRaw()");} 
    }

    public int read() throws IOException {
        int entrada = 0;
        entrada = super.read();
        if(entrada == ESC){
            entrada = super.read();
            if(entrada == CORCHETE){
                switch(entrada) {
                    case DERECHA:
                        return SECUENCIA_DERECHA;
                    case IZQUIERDA:
                        return SECUENCIA_IZQUIERDA;
                    case ARRIBA:
                        return SECUENCIA_ARRIBA;
                    case ABAJO:
                        return SECUENCIA_ABAJO;
                    case HOME:
                        return SECUENCIA_HOME;
                    case END:
                        return SECUENCIA_FIN;
                    case INSERT:
                        entrada = super.read();
                        if(entrada == TILDE){
                            return SECUENCIA_INSERT;
                        }
                        return -1;
                    case DELETE:
                        entrada = super.read();
                        if(entrada == TILDE){
                            return SECUENCIA_DELETE;
                        }
                        return -1;
                    default:
                        return -1;
                }
            }
        } else if(entrada == BACKSPACE) {
            return BACKSPACE;
        } else {
            return entrada;
        }
        return -1;
    }

}