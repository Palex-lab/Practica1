import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.IOException;

public class EditableBufferedReader extends BufferedReader {
    public EditableBufferedReader(InputStreamReader in){
        super(in);
    }

    public void setRaw(){
    try{ 
        String[] cmd = {"/bin/sh", "-c","stty -echo raw </dev/tty"};
        Runtime.getRuntime().exec(cmd);
    } catch(IOException e){
        System.out.println("Error setting Raw mode");
    }
    }

    public void unsetRaw(){
        try{ 
            String[] cmd = {"/bin/sh", "-c","stty -echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e){
            System.out.println("Error setting Cooked mode");
        }
        }

//    public void read(){
//        try{ 
//            Scanner sc = new Scanner(System.in);
//            String nextCar = sc.next();
//            if(nextCar)
//        } catch(IOException e){
//            System.out.println("Error reading character");
//       }
//        }
}