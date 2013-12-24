package ar.com.airdrop.exceptions;

public class EnviarSocketException extends Exception {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public EnviarSocketException(Exception e, String ip, String mensaje) {

		
		e.printStackTrace();
		
		System.out.println(mensaje + ip + " causa : " + e.getCause());

	}

}
