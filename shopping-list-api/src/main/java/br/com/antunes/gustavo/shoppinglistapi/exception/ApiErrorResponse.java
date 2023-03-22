package br.com.antunes.gustavo.shoppinglistapi.exception;

public class ApiErrorResponse {
    private String status;
    private String timestamp;
    private String message;

    public ApiErrorResponse(String status, String timestamp, String message) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
}
