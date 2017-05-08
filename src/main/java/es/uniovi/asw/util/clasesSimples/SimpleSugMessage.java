package es.uniovi.asw.util.clasesSimples;

import es.uniovi.asw.model.Sugerencia;

public class SimpleSugMessage{
	private long id=0;
	private String autor;
	private String titulo;
	private int pos;
	private int neg;
	private String estado;
	
	public SimpleSugMessage(Sugerencia sug){
		id=sug.getId();
		autor = sug.getCitizen().getUsuario();
		titulo = sug.getTitulo();
		pos=sug.getPosVotes();
		neg = sug.getNegVotes();
		estado= String.valueOf(sug.getEstado());
	}
	

	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getNeg() {
		return neg;
	}
	public void setNeg(int neg) {
		this.neg = neg;
	}
}
