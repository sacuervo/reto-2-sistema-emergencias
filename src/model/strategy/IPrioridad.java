package model.strategy;

import model.factory.Emergencia;

public interface IPrioridad {
    int calcularPrioridad(Emergencia emergencia);
}
