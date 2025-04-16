package Clase_08_II.model.services;

import Clase_08_II.model.Emergencia;
import Clase_08_II.model.interfaces.IServicioEmergencia;

public abstract class ServicioEmergenciaBase implements IServicioEmergencia {

    private String id;
    private int personalDisponible;
    private double combustible;

    public ServicioEmergenciaBase(String id, int personalDisponible, double combustible) {
        this.id = id;
        this.personalDisponible = personalDisponible;
        this.combustible = combustible;
    }

    public String getId() {
        return id;
    }

    public int getPersonalDisponible() {
        return personalDisponible;
    }

    public double getCombustible() {
        return combustible;
    }

    @Override
    public boolean estaDisponible() {
        return personalDisponible > 0 && combustible > 0;
    }

    @Override
    public void asignarPersonal(int cantidad) {
        if (cantidad <= personalDisponible) {
            personalDisponible -= cantidad;
        } else {
            System.out.println("No hay suficiente personal disponible en el servicio " + id);
        }
    }

    @Override
    public void liberarPersonal(int cantidad) {
        personalDisponible += cantidad;
    }

    @Override
    public void asignarCombustible(double cantidad) {
        combustible = Math.max(0, combustible - cantidad);
    }

    @Override
    public void tanquearCombustible(double cantidad) {
        combustible += cantidad;
    }

    public abstract void atenderEmergencia(Emergencia emergencia);

    @Override
    public String toString() {
        return "ServicioEmergenciaBase [id=" + id + ", personalDisponible=" + personalDisponible + ", combustible="
                + combustible + "]";
    }

}
