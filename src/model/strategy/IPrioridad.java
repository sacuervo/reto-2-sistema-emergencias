package model.strategy;

import model.Emergencia;

public interface IPrioridad {
    int calcularPrioridad(Emergencia emergencia);
}
