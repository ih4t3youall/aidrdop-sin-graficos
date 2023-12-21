package ar.com.airdrop.dominio;
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
public class MensajeTomaFichero implements Serializable
{
    /** Nombre del fichero que se transmite. Por defecto "" */
    public String nombreFichero="";

    /** Si este es el ultimo mensaje del fichero en cuestion o hay mas despuus */
    public boolean ultimoMensaje=true;

    /** Cuantos bytes son vilidos en el array de bytes */
    public int bytesValidos=0;

    /** Array con bytes leidos del fichero */
    public byte[] contenidoFichero = new byte[LONGITUD_MAXIMA];
    
    /** Numero maximo de bytes que se enviain en cada mensaje */
    public final static int LONGITUD_MAXIMA=10;
}
