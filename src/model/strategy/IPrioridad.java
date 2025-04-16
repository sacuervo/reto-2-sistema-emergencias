package Clase_08_II.model.strategy;

import Clase_08_II.model.Emergencia;

public interface IPrioridad {
    int calcularPrioridad(Emergencia emergencia);
}
