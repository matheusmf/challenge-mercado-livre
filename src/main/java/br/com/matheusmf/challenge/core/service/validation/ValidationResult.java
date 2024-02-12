package br.com.matheusmf.challenge.core.service.validation;

public class ValidationResult {
    private final boolean isValid;
    private final String errorMsg;
    
    public ValidationResult(boolean isValid, String erroMsg) {
    	this.isValid = isValid;
    	this.errorMsg = erroMsg;
    }

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(String errorMsg) {
        return new ValidationResult(false, errorMsg);
    }

    public boolean notValid() {
        return !isValid;
    }
    
    public String getErrorMsg() {
    	return this.errorMsg;
    }
}
