package Clase_08_II.model;

import Clase_08_II.utils.NivelGravedad;
import Clase_08_II.utils.TipoEmergencia;

public class Incendio extends Emergencia {

    public Incendio(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super(tipo, ubicacion, nivelGravedad, tiempoRespuesta);
    }

}
