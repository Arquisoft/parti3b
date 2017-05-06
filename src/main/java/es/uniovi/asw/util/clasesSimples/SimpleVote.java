package es.uniovi.asw.util.clasesSimples;

import es.uniovi.asw.model.VotoSugerencia;

public class SimpleVote{
	 
	Long sugerenciaId;
	boolean aFavor;
	int nPositivos;
	int nNegativos;
	public SimpleVote(VotoSugerencia voto){
		sugerenciaId= voto.getSugerencia().getId();
		aFavor=voto.isAFavor();
		nPositivos= voto.getSugerencia().getPosVotes() ;
		nPositivos = voto.getSugerencia().getNegVotes();
		
		if(aFavor) nPositivos++;
		else nNegativos++;
	}

	public Long getSugerenciaId() {
		return sugerenciaId;
	}

	public void setSugerenciaId(Long sugerenciaId) {
		this.sugerenciaId = sugerenciaId;
	}

	public boolean isaFavor() {
		return aFavor;
	}

	public void setaFavor(boolean aFavor) {
		this.aFavor = aFavor;
	}

	public int getnPositivos() {
		return nPositivos;
	}

	public void setnPositivos(int nPositivos) {
		this.nPositivos = nPositivos;
	}

	public int getnNegativos() {
		return nNegativos;
	}

	public void setnNegativos(int nNegativos) {
		this.nNegativos = nNegativos;
	}
}
	
