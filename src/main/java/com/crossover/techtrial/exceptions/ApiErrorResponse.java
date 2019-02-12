package com.crossover.techtrial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorResponse {
	
	 private HttpStatus status;
	    //private Object data;
	    private String error;

	    public ApiErrorResponse() {
	        this(null);
	    }

	    public ApiErrorResponse(Object data) {
	       // this.data = data;
	        this.error = null;
	    }

	    public ResponseEntity<ApiErrorResponse> send(HttpStatus status) {
	        this.status = status;
	        return new ResponseEntity<ApiErrorResponse>(this, status);
	    }

	    public ResponseEntity<ApiErrorResponse> send(HttpStatus status, String error) {
	        this.status = status;
	        this.error = error;
	        return new ResponseEntity<ApiErrorResponse>(this, status);
	    }

	    /*public Object getData() {
	        return data;
	    }*/

	    public String getError() {
	        return error;
	    }

	    public HttpStatus getStatus() {
	        return this.status;
	    }
	

}
