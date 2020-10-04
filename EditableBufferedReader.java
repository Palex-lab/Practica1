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

    public static final int FLECHA_DERECHA = Integer.MIN_VALUE + 1;
    public static final int FLECHA_IZQUIERDA = Integer.MIN_VALUE + 2;
    public static final int FLECHA_ARRIBA = Integer.MIN_VALUE + 3;
    public static final int FLECHA_ABAJO = Integer.MIN_VALUE + 4;
    public static final int TECLA_HOME = Integer.MIN_VALUE + 5;
    public static final int TECLA_FIN = Integer.MIN_VALUE + 6;
    public static final int TECLA_INSERT = Integer.MIN_VALUE + 7;
    public static final int TECLA_DELETE = Integer.MIN_VALUE + 8;
    public static final int TECLA_CARACTER = Integer.MIN_VALUE + 9;     

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
                        return FLECHA_DERECHA;
                    case IZQUIERDA:
                        return FLECHA_IZQUIERDA;
                    case ARRIBA:
                        return FLECHA_ARRIBA;
                    case ABAJO:
                        return FLECHA_ABAJO;
                    case HOME:
                        return TECLA_HOME;
                    case END:
                        return TECLA_FIN;
                    case INSERT:
                        entrada = super.read();
                        if(entrada == TILDE){
                            return TECLA_INSERT;
                        }
                        return -1;
                    case DELETE:
                        entrada = super.read();
                        if(entrada == TILDE){
                            return TECLA_DELETE;
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

    public String readLine() throws IOException {
        this.setRaw();
        Line linea = new Line();
        int entrada = 0;
        entrada = super.read();
        while(entrada!= ENTER){
            switch(entrada){
                case FLECHA_DERECHA:
                    linea.mover_derecha();
                    //mover hacia la derecha
                    break;
                case FLECHA_IZQUIERDA:
                    linea.mover_izquierda();
                    //mover hacia la izquierda
                    break;
                case TECLA_HOME:
                    linea.home();
                    //ir al principio
                    break;
                case TECLA_FIN:
                    linea.end();
                    //ir al final
                    break;
                case TECLA_INSERT:
                    linea.insertar();
                    //insertar
                    break;
                case TECLA_DELETE:
                    linea.borrar_caracter();
                    //borrar
                    break;
                default: //otro caracter
                linea.insertar_caracter((char) entrada);
                    //añadirlo...
                    break;
            }
            entrada = super.read(); //diría que se tiene que volver a leer
        }
        String str = linea.toString();
        this.unsetRaw(); 
        return str;
    }

    

}