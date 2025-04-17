package model.strategy;

import model.factory.Emergencia;

public class StrategyPrioridadCercania implements IPrioridad {

    private class MapaUrbano {
        public int calcularDistancia(String ubicacion) {
            switch (ubicacion.toLowerCase()) {
                case "centro":
                    return 8;
                case "norte":
                    return 2;
                case "sur":
                    return 1;
                case "este":
                    return 5;
                case "oeste":
                    return 4;
                default:
                    return 1;
            }
        }
    }

    private MapaUrbano mapaUrbano = new MapaUrbano();

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        int calcularDistancia = mapaUrbano.calcularDistancia(emergencia.getUbicacion());
        return 10 - calcularDistancia;
    }

}
