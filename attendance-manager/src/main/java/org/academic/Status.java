package org.academic;

public class Status {
    private boolean aprovado;
    public Status (boolean aprovado){
        this.aprovado = aprovado;
    }

    public boolean getAprovado(){
        return aprovado;
    }

    public void setAprovado(boolean aprovado){
        this.aprovado = aprovado;
    }
}
