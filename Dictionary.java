public abstract class Dictionary {
    
    public final static int ESC = 27;
    public final static int BACKSPACE = 127;
    public final static int DERECHA = 67;
    public final static int IZQUIERDA = 68;
    public final static int ABAJO = 66;
    public final static int HOME = 72;
    public final static int END = 70;
    public final static int INSERT = 65;
    public final static int SUPRIMIR = 51;
    public final static int CORCHETE = 91; //CSI
    public final static int TILDE = 126; //Símbolo ~
    public final static int ENTER_1 = 10;
    public final static int ENTER_2 = 13;
    public final static int PAJARITO = 94; //Símbolo ^ 

    public final static int FLECHA_DERECHA = 2001;
    public final static int FLECHA_IZQUIERDA = 2002;
    public final static int FLECHA_ARRIBA = 2003;
    public final static int FLECHA_ABAJO = 2004;
    public final static int TECLA_HOME = 2005;
    public final static int TECLA_FIN = 2006;
    public final static int TECLA_INSERT = 2007;
    public final static int TECLA_SUPRIMIR = 2008;
    public final static int TECLA_CARACTER = 2009; 
    public final static int TECLA_BACKSPACE = 2010;
    
    public final static String ANSI_DERECHA = "\033[1C";
    public final static String ANSI_IZQUIERDA = "\033[1D";
    public final static String ANSI_INSERT = "\033[1@";
    //public final static String ANSI_BACKSPACE = "\b";
    public final static String ANSI_BACkSPACE = "\033[1D" + "\033[1P";
    public final static String ANSI_DELETE = "\033[1P";
    public final static String ANSI_HOME = "\033[1G";
    public final static String ANSI_END = "\033[1F";
    //public final static String ANSI_BLANK = "\033[@";

}
