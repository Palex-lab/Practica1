import java.io.*;
import java.lang.String;

public class EditableBufferedReader extends BufferedReader {

    private Line linea;

    public EditableBufferedReader(InputStreamReader in){
        super(in);
        this.linea = new Line();
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
        if(entrada == Dictionary.ESC){
            entrada = super.read();
            if(entrada == Dictionary.CORCHETE){
                switch(entrada) {
                    case Dictionary.DERECHA:
                        return Dictionary.FLECHA_DERECHA;
                    case Dictionary.IZQUIERDA:
                        return Dictionary.FLECHA_IZQUIERDA;
                    case Dictionary.HOME:
                        return Dictionary.TECLA_HOME;
                    case Dictionary.END:
                        return Dictionary.TECLA_FIN;
                    case Dictionary.INSERT:
                        return Dictionary.TECLA_INSERT;
                    case Dictionary.DELETE: 
                        entrada = super.read();
                        if(entrada == Dictionary.TILDE){
                            return Dictionary.TECLA_DELETE;
                        }
                        return -1;
                    default:
                        return -1;
                }
            }
        }       
        else if(entrada == Dictionary.BACKSPACE) {
            return Dictionary.BACKSPACE;  
        }   
        else {
            return entrada;
        }
        return -1;
    } 

    public String readLine() throws IOException {
        this.setRaw();
        int entrada = 0;
        entrada = this.read();
        while(entrada!= Dictionary.ENTER){
            switch(entrada){
                case Dictionary.FLECHA_DERECHA:
                    linea.mover_derecha();
                    //mover hacia la derecha
                    break;
                case Dictionary.FLECHA_IZQUIERDA:
                    linea.mover_izquierda();
                    //mover hacia la izquierda
                    break;
                case Dictionary.TECLA_HOME:
                    linea.home();
                    //ir al principio
                    break;
                case Dictionary.TECLA_FIN:
                    linea.end();
                    //ir al final
                    break;
                case Dictionary.TECLA_INSERT:
                    linea.insertarMode();
                    //insertar
                    break;
                case Dictionary.TECLA_DELETE:
                    linea.delete();
                    //borrar
                    break;
                case Dictionary.BACKSPACE:
                    linea.backspace();
                default: //otro caracter
                char temp = (char) entrada;
                linea.insertar_caracter(temp);
                System.out.print(temp);
                //linea.insertar_caracter((char) entrada);
                    //añadirlo...

                    break;
            }
            entrada = super.read(); 
        }
        //String str = linea.toString();
        String str = linea.getBuffer().toString();
        this.unsetRaw(); 
        return str;
    }

    

}