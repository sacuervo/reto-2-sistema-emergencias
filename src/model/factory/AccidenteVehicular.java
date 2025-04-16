package model.factory;

import utils.NivelGravedad;
import utils.TipoEmergencia;

public class AccidenteVehicular extends Emergencia {

    public AccidenteVehicular(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
    }

}
