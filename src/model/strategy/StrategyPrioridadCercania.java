package Clase_08_II.model.strategy;

import Clase_08_II.model.Emergencia;

public class StrategyPrioridadCercania implements IPrioridad {

    private class MapaUrbano {
        public int calcularDistancia(String ubicacion) {
            switch (ubicacion.toLowerCase()) {
                case "centro":
                    return 2;
                case "norte":
                    return 8;
                case "sur":
                    return 10;
                case "este":
                    return 5;
                case "oeste":
                    return 6;
                default:
                    return 10;
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
