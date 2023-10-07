package org.academic;

public class Autoavaliacao {
    private float autoNota;
    private String comentario;

    public Autoavaliacao(String comentario, float autoNota) {
        this.comentario = comentario;
        this.autoNota = autoNota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public float getAutoNota() {
        return autoNota;
    }

    public void setAutoNota(float autoNota) {
        this.autoNota = autoNota;
    }
 
}
