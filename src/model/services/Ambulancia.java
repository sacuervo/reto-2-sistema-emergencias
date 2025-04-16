package model.services;

import model.factory.Emergencia;

public class Ambulancia extends ServicioEmergenciaBase {

    public Ambulancia(String id, int personalDisponible, double combustible) {
        super(id, personalDisponible, combustible);
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Ambulacia en camino!!!");
        System.out.println("-> [Ambulancia" + getId() + "]: " + emergencia.toString());

        asignarPersonal(3);
        asignarCombustible(5.0);
    }

}
