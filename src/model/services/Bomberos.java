package Clase_08_II.model.services;

import Clase_08_II.model.Emergencia;

public class Bomberos extends ServicioEmergenciaBase {

    public Bomberos(String id, int personalDisponible, double combustible) {
        super(id, personalDisponible, combustible);
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Bomberos en camino!!!");
        System.out.println("-> [Bomberos" + getId() + "]: " + emergencia.toString());

        asignarPersonal(5);
        asignarCombustible(10);
    }

}
