package Clase_08_II.model;

import Clase_08_II.utils.NivelGravedad;
import Clase_08_II.utils.TipoEmergencia;

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
