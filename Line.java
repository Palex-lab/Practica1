import java.io.*;
import java.util.ArrayList;
import java.util.Observable;;

public class Line extends Observable{
    //ArrayList<Character> buffer;
    StringBuilder buff;
    int cursor;
    Boolean insert; //False: modo sobre-escritura; True: Inserción normal

    public Line(){
        //this.buffer = new ArrayList<>();
        this.cursor = 0;
        insert = false;
        buff = new StringBuilder(100);
    }

    public int getPos(){
        return this.cursor;
    }

    public StringBuilder getBuffer(){
        return this.buff;
    }

    public void backspace(){
        this.buff.deleteCharAt(this.cursor-1);
        this.cursor--;
        /*if(this.cursor > 0){
            this.buffer.remove(cursor-1);
            this.mover_izquierda();
        }*/
    }
    public void mover_derecha(){
        if(this.cursor < this.buff.length()){
            this.cursor++;
            this.setChanged();
            this.notifyObservers(Dictionary.FLECHA_DERECHA);
        }
    }

    public void mover_izquierda(){
        if(this.cursor!=0){
            this.cursor--;
            this.setChanged();
            this.notifyObservers(Dictionary.FLECHA_IZQUIERDA);
        }
    }

    public void home(){
        this.cursor = 0;
    }

    public void end(){
        this.cursor = this.buff.length();
        //this.cursor = this.buffer.size();
    }

    public void insertarMode(){
        this.insert = !this.insert;
    }

    public void insertar_caracter(Character c){
        this.buff.insert(cursor,c);
        this.cursor++;
        /*if(this.insert){
            if(this.cursor < this.buffer.size()){

            }
        }*/
        //this.buffer.add(cursor, c);
        //this.cursor++;
    }

    public void delete(){
        if(this.cursor < this.buff.length()){
            this.buff.deleteCharAt(cursor);
        }
        //this.buffer.remove(cursor);
    }

    /*public String toString(){
        String str = "";
        for(Character c : buffer){
            str = str + c;
        }    
        return str;    
    }*/

}
