package it.welld.patternrecognition.PatternRecognition;

public class BindingException extends Exception {

	private static final long serialVersionUID = -6793291651574351713L;

	private String message;

	public BindingException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
