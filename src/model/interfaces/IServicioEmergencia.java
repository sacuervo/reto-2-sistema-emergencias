package model.interfaces;

import model.factory.Emergencia;

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
