package model.factory;

import utils.NivelGravedad;
import utils.TipoEmergencia;

public class FactoryEmergencias {

    public static Emergencia crearEmergencia(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad,
            int tiempoRespuesta) {
        switch (tipo) {
            case ROBO:
                return new Robo(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
            case ACCIDENTE_VEHICULAR:
                return new AccidenteVehicular(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
            case INCENDIO:
                return new Incendio(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
            default:
                return null;
        }
    }
}
