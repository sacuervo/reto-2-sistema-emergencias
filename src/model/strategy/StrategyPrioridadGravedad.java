package model.strategy;

import model.factory.Emergencia;

public class StrategyPrioridadGravedad implements IPrioridad {

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        switch (emergencia.getNivelGravedad()) {
            case ALTO:
                return 3;
            case MEDIO:
                return 2;
            case BAJO:
                return 1;
            default:
                return 1;
        }
    }

}
