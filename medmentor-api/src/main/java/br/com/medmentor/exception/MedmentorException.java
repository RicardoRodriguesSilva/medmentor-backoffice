package br.com.medmentor.exception;

public class MedmentorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MedmentorException() {
		super();
	}
	
    public MedmentorException(String message) {
        super(message);
    }
	
    public MedmentorException(String message, Throwable cause) {
        super(message, cause);
    }
}
