import java.io.*;
import java.util.ArrayList;;

public class Line {
    ArrayList<Character> buffer;
    int cursor;

    public Line(){
        this.buffer = new ArrayList<>();
        this.cursor = 0;
    }

    public void mover_derecha(){
        this.cursor++;
    }

    public void mover_izquierda(){
        this.cursor--;
    }

    public void home(){
        this.cursor = 0;
    }

    public void end(){
        this.cursor = buffer.size();
    }

    public void insertar(){
        System.out.print("Insertar");
    }

    public void insertar_caracter(Character c){
        this.buffer.add(cursor, c);
        this.cursor++;
    }

    public void borrar_caracter(){
        this.buffer.remove(cursor);
    }

    public String toString(){
        String str = "";
        for(Character c : buffer){
            str = str + c;
        }    
        return str;    
    }

}
