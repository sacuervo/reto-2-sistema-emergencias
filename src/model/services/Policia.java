package Clase_08_II.model.services;

import Clase_08_II.model.Emergencia;

public class Policia extends ServicioEmergenciaBase {

    public Policia(String id, int personalDisponible, double combustible) {
        super(id, personalDisponible, combustible);
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Policia en camino!!!");
        System.out.println("-> [policia" + getId() + "]: " + emergencia.toString());

        asignarPersonal(2);
        asignarCombustible(3);
    }

}
