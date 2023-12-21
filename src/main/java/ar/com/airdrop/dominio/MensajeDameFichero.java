package ar.com.airdrop.dominio;

import java.io.Serializable;

/**
 * Mensaje para pedir un fichero.
 * @author Martin Lequerica
 *
 */
public class MensajeDameFichero implements Serializable
{
    /** path completo del fichero que se pide */
    public String nombreFichero;
}
