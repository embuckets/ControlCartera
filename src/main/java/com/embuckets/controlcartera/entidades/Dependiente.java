/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.util.Objects;

/**
 * polizaGMM
 *
 * @author emilio
 */
public class Dependiente {

    private Cliente cliente;
    private PolizaGmm polizaGmm;

    public Dependiente(Cliente cliente, PolizaGmm polizaGmm) {
        this.cliente = cliente;
        this.polizaGmm = polizaGmm;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PolizaGmm getPolizaGmm() {
        return polizaGmm;
    }

    public void setPolizaGmm(PolizaGmm polizaGmm) {
        this.polizaGmm = polizaGmm;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.cliente);
        hash = 53 * hash + Objects.hashCode(this.polizaGmm);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dependiente other = (Dependiente) obj;
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.polizaGmm, other.polizaGmm)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ " + cliente.toString() + ", " + polizaGmm.toString() + " ]";
    }
}
