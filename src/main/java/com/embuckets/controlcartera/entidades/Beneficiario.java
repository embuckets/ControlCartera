/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.util.Objects;

/**
 * beneficiario es de poliza de vida
 *
 * @author emilio
 */
public class Beneficiario {

    private Cliente cliente;
    private PolizaVida polizaVida;

    public Beneficiario(Cliente cliente, PolizaVida polizaVida) {
        this.cliente = cliente;
        this.polizaVida = polizaVida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PolizaVida getPolizaVida() {
        return polizaVida;
    }

    public void setPolizaVida(PolizaVida polizaVida) {
        this.polizaVida = polizaVida;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.cliente);
        hash = 29 * hash + Objects.hashCode(this.polizaVida);
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
        final Beneficiario other = (Beneficiario) obj;
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.polizaVida, other.polizaVida)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ " + cliente.toString() + ", " + polizaVida.toString() + " ]";
    }

}
