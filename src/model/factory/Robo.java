package model.factory;

import utils.NivelGravedad;
import utils.TipoEmergencia;

public class Robo extends Emergencia {

    public Robo(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
    }

}
