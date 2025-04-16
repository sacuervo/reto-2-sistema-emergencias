package utils;

public class EmergenciaPriorizada {
    private final int id;
    private final TipoEmergencia tipo;
    private final int prioridad;

    public EmergenciaPriorizada(int id, TipoEmergencia tipo, int prioridad) {
        this.id = id;
        this.tipo = tipo;
        this.prioridad = prioridad;
    }

    public int getId() {
        return id;
    }

    public TipoEmergencia getTipo() {
        return tipo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public String toString() {
        return "EmergenciaPriorizada{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", prioridad=" + prioridad +
                '}';
    }
}