package ar.com.airdrop.exceptions;

public class SendThroughtSocketException extends Exception {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public SendThroughtSocketException(Exception e, String ip, String message) {
		e.printStackTrace();
		System.out.println(message + ip + " cause : " + e.getCause());
	}

}
