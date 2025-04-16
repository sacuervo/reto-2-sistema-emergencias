package model;

import utils.NivelGravedad;
import utils.TipoEmergencia;

public class Incendio extends Emergencia {

    public Incendio(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
    }

}
