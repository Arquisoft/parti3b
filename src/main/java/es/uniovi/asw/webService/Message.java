package es.uniovi.asw.webService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uniovi.asw.model.Categoria;
import es.uniovi.asw.model.Comentario;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.VotoComentario;
import es.uniovi.asw.model.VotoSugerencia;

public class Message {
	
	public static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static String setMessage(Sugerencia sugerencia) {
        return gson.toJson(sugerencia);
    }
    
    public static String setMessage(Comentario comentario) {
    	return gson.toJson(comentario);
    }
    
    public static String setMessage(VotoComentario votoComentario) {
    	return gson.toJson(votoComentario);
    }
    
    public static String setMessage(VotoSugerencia votoSusgerencia) {
    	return gson.toJson(votoSusgerencia);
    }
    
    /**
     * Metodo que traduce de Json a clase sugerencia 
     * @param json
     * @return
     */
    public static Sugerencia getSugerenciaFromJSON(String json){
    	final Gson gson = new Gson();
        final Sugerencia sugg = gson.fromJson(json, Sugerencia.class);
        if(sugg!=null)
        	return sugg;
		return null;
    }
    /**
     * Metodo que traduce de Json a clase comentario
     * @param json
     * @return
     */
    public static Comentario getComentarioFromJSON(String json){
    	final Gson gson = new Gson();
        final Comentario comm = gson.fromJson(json, Comentario.class);
        if(comm!=null)
        	return comm;
		return null;
    }
    /**
     * Metodo que traduce de JSON a votoComentario
     * @param json
     * @return
     */
    public static VotoComentario getVotoComentarioFromJSON(String json){
    	final Gson gson = new Gson();
        final VotoComentario vComm = gson.fromJson(json, VotoComentario.class);
        if(vComm!=null)
        	return vComm;
		return null;
    }
    
    /**
     * Metodo que traduce de JSO a votoSugerencia
     * @param json
     * @return
     */
    public static VotoSugerencia getVotoSugerenciaFromJSON(String json){
    	final Gson gson = new Gson();
        final VotoSugerencia vSugg = gson.fromJson(json, VotoSugerencia.class);
        if(vSugg!=null)
        	return vSugg;
		return null;
    }
    /**
     * Metodo que traduce de JSO a Categoria
     * @param json
     * @return
     */
    public static Categoria getCategoriaFromJSON(String json){
    	final Gson gson = new Gson();
        final Categoria cat = gson.fromJson(json, Categoria.class);
        if(cat!=null)
        	return cat;
		return null;
    }

}
