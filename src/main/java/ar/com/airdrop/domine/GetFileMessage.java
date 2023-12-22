package ar.com.airdrop.domine;
/**
 * Martin Lequerica. 18 Mar 2006
 * 
 * Programa de ejemplo de como transmitir un fichero por un socket.
 * Esta es el mensaje que contiene los cachos de fichero que se van enviando
 * 
 */


import java.io.Serializable;

/**
 * Mensaje que contiene parte del fichero que se esto transmitiendo.
 * 
 * @author Javier Abellon
 *
 */
public class GetFileMessage implements Serializable
{
    /** Nombre del fichero que se transmite. Por defecto "" */
    public String fileName ="";

    /** Si este es el ultimo mensaje del fichero en cuestion o hay mas despuus */
    public boolean lastMessage =true;

    /** Cuantos bytes son vilidos en el array de bytes */
    public int validBytes =0;

    /** Array con bytes leidos del fichero */
    public byte[] fileContent = new byte[MAX_LENGHT];
    
    /** Numero maximo de bytes que se enviain en cada mensaje */
    public final static int MAX_LENGHT =10;
}
