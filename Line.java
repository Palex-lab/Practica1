import java.io.*;
import java.util.ArrayList;
import java.util.Observable;;

public class Line extends Observable{ //Model
    StringBuilder buffer; //Unithread. Más sencillo. StringBuffer multithread
    int cursor; //Entero declarado para conocer la posición del cursor
    Boolean insert; //False: modo sobre-escritura; True: Inserción normal
    private String[] tecleado; //Acción del teclado

    public Line(){
        this.cursor = 0;
        insert = true;
        this.buffer = new StringBuilder(100);
        this.tecleado = new String[2];
    }

    public StringBuilder getLine() {
        return this.buffer;
    }
    
    public int getLength(){
        return this.buffer.length();
    }

    public int getPosCursor(){
        return this.cursor;
    }

    public Boolean getMode(){
        return this.insert;
    }

    public void insertarMode(){
        this.insert = !this.insert;
    }

    public void insertar_caracter(Character c){ //If insert == true entramos en el bucle. Véase readLine()
        this.buffer = this.buffer.insert(this.cursor,c);
        tecleado[0] = "InsertarCar";
        tecleado[1] = Dictionary.ANSI_INSERT + c;
        this.cursor++;
        this.setChanged();
        this.notifyObservers(tecleado);
    }

    public void reemplazar_caracter(char c){ //If insert == false entramos en el bucle. Véase readLine()
        this.buffer.replace(this.cursor, this.cursor + 1, "" + c); 
        this.cursor++;
        tecleado[0] = "Reemplazar_caracter";
        tecleado[1] = "" + c;
        this.setChanged();
        this.notifyObservers(tecleado);
    }

    public void backspace(){
        this.buffer.deleteCharAt(this.cursor-1);
        this.cursor--;
        tecleado[0] = "Backspace";
        tecleado[1] = Dictionary.ANSI_BACkSPACE;
        this.setChanged();
        this.notifyObservers(tecleado);
    }
    
    public void mover_derecha(){
        if(this.cursor < this.buffer.length()){
            this.cursor++;
            tecleado[0] = "Mover_derecha";
            tecleado[1] = Dictionary.ANSI_DERECHA;
            this.setChanged();
            this.notifyObservers(tecleado);
        }
    }

    public void mover_izquierda(){
        if(this.cursor!=0){
            this.cursor--;
            tecleado[0] = "Mover_izquierda";
            tecleado[1] = Dictionary.ANSI_IZQUIERDA;
            this.setChanged();
            this.notifyObservers(tecleado);
        }
    }

    public void home(){
        this.cursor = 0;
        tecleado[0] = "Mover_home";
        tecleado[1] = Dictionary.ANSI_HOME;
        this.setChanged();
        this.notifyObservers(tecleado);
    }

    public void end(){
        int mover = this.getLength() - this.cursor;
        this.cursor = this.getLength();
        tecleado[0] = "Mover_end";
        tecleado[1] = "\033[" + mover + "C";
        this.setChanged();
        this.notifyObservers(tecleado);
    }

    public void delete(){
        if(this.cursor < this.buffer.length()){
            this.buffer.deleteCharAt(this.cursor);
            tecleado[0] = "Delete";
            tecleado[1] = Dictionary.ANSI_DELETE;
            this.setChanged();
            this.notifyObservers(tecleado);
        }
    }

}
