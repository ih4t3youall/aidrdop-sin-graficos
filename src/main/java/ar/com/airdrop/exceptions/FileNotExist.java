package ar.com.airdrop.exceptions;

public class FileNotExist extends Exception {

	public FileNotExist(String e){
		System.err.println(e);
	}
}
