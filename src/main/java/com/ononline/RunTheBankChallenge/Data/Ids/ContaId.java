package com.ononline.RunTheBankChallenge.Data.Ids;

import java.io.Serializable;

public class ContaId implements Serializable {
    
    private long id;
    private int agencia;
    
    public ContaId(long id, int agencia) {
        this.id = id;
        this.agencia = agencia;
    }
    
    public long getId() {
        return this.id;
    }
    
    public int getAgencia() {
        return this.agencia;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }
    
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ContaId other)) return false;
        if (!other.canEqual(this)) return false;
        if (this.getId() != other.getId()) return false;
        return this.getAgencia() == other.getAgencia();
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ContaId;
    }
    
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        result = result * PRIME + this.getAgencia();
        return result;
    }
    
    public String toString() {
        return "ContaId(id=" + this.getId() + ", agencia=" + this.getAgencia() + ")";
    }
}
