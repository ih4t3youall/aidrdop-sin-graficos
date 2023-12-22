package ar.com.airdrop.domine;

import java.io.Serializable;

/**
 * Mensaje para pedir un fichero.
 * @author Martin Lequerica
 *
 */
public class GiveMeFile implements Serializable
{
    /** path completo del fichero que se pide */
    public String fileName;
}
