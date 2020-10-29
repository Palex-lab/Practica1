import java.io.*;

class TestReadLine {
    public static void main(String[] args) {
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        //int i = 0; //llegir
        try {
            str = in.readLine(); 
            in.close(); //NEW
            //i = in.read();
        } catch (IOException e) { e.printStackTrace();}
        System.out.println("\nYour line is: " + str); //i
    }
}

