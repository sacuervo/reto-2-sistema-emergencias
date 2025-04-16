package Clase_08_II.model.interfaces;

import Clase_08_II.model.Emergencia;

public interface IServicioEmergencia {

    String getId();

    int getPersonalDisponible();

    double getCombustible();

    boolean estaDisponible();

    void asignarPersonal(int cantidad);

    void liberarPersonal(int cantidad);

    void asignarCombustible(double cantidad);

    void tanquearCombustible(double cantidad);

    void atenderEmergencia(Emergencia emergencia);

}
