package com.sucursal.model;

public class ErrorMsj {
	
	private long code;

	private String titulo;

	private String mensaje;
	
	public ErrorMsj(int value, String message) {
		this.code = value;
		this.mensaje = message;
	}

	public ErrorMsj(int value, String titulo, String message ) {
		this.code = value;
		this.mensaje = message;
		this.titulo=titulo;
	}
	
	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
