package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import model.factory.AccidenteVehicular;
import model.factory.Emergencia;
import model.factory.Incendio;
import model.factory.Robo;
import model.interfaces.IServicioEmergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.services.Ambulancia;
import model.services.Bomberos;
import model.services.Policia;
import model.strategy.IPrioridad;
import model.strategy.StrategyPrioridadCercania;
import model.strategy.StrategyPrioridadGravedad;
import utils.TipoEmergencia;

public class SistemaEmergencias implements SujetoEmergencias {

    private static SistemaEmergencias instance;
    private List<Emergencia> listaEmergencias;
    private List<IServicioEmergencia> listaRecursos;
    private List<ObserverEmergencias> observadores;

    private IPrioridad strategyPrioridad;

    private int emergenciasAtendidas;
    private long tiempoTotalAtencion;

    private SistemaEmergencias() {
        strategyPrioridad = new StrategyPrioridadGravedad();
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        emergenciasAtendidas = 0;
        tiempoTotalAtencion = 0;
    }

    public static SistemaEmergencias getInstance() {
        if (instance == null) {
            instance = new SistemaEmergencias();
        }
        return instance;
    }

    @Override
    public void agregarObserver(ObserverEmergencias observerEmergencias) {
        observadores.add(observerEmergencias);
    }

    @Override
    public void eliminarObserver(ObserverEmergencias observerEmergencias) {
        observadores.remove(observerEmergencias);
    }

    @Override
    public void notificarEmergencias(Emergencia emergencia) {
        for (ObserverEmergencias observerEmergencias : observadores) {
            observerEmergencias.onNuevasEmergencias(emergencia);
        }
    }

    public void registrarRecurso(IServicioEmergencia recurso) {
        listaRecursos.add(recurso);
    }

    public void mostrarEstadoRecursos() {
        System.out.println("\n=== ESTADO ACTUAL DE RECURSOS ===");
        for (IServicioEmergencia r : listaRecursos) {
            System.out.println(r.toString());
        }
    }

    // Ejemplo de uso de Lambda para filtrar recursos disponibles
    public List<IServicioEmergencia> filtrarRecursosDisponibles() {
        return listaRecursos.stream()
                .filter(r -> r.estaDisponible())
                .collect(Collectors.toList());
    }

    public void registrarNuevaEmergencia(Emergencia e) {
        listaEmergencias.add(e);
        notificarEmergencias(e);
    }

    public List<Emergencia> getEmergenciasPendientes() {
        return listaEmergencias.stream()
                .filter(e -> !e.isAtendida())
                .collect(Collectors.toList());
    }

    public void asignarRecursosAEmergencia(Emergencia emergencia) {
        // Buscamos recursos disponibles
        List<IServicioEmergencia> disponibles = filtrarRecursosDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay recursos disponibles para esta emergencia.");
            return;
        }
        System.out.println("-> Asignando recursos automáticamente...");

        if (emergencia instanceof Incendio) {
            for (IServicioEmergencia r : disponibles) {
                if (r instanceof Bomberos) {
                    r.atenderEmergencia(emergencia);
                    break;
                }
            }
        } else if (emergencia instanceof AccidenteVehicular) {
            for (IServicioEmergencia r : disponibles) {
                if (r instanceof Ambulancia) {
                    r.atenderEmergencia(emergencia);
                    break;
                }
            }
        } else if (emergencia instanceof Robo) {
            for (IServicioEmergencia r : disponibles) {
                if (r instanceof Policia) {
                    r.atenderEmergencia(emergencia);
                    break;
                }
            }
        }
    }

    /* ----------------------- PRIORIZACIÓN DE EMERGENCIAS ---------------------- */

    /**
     * Regresa una lista de hashmaps con información de los tipos de emergencias
     * pendientes.
     * 
     * Cada hashmap contiene las llaves: id (entero, comienza desde 1), nombre
     * (String) y tipo (TipoEmergencia).
     * 
     * @return a {@code List<HashMap<String, Object>>} Lista que almacena
     *         información de los tipos de emergencias pendientes.
     */
    public List<HashMap<String, Object>> getTiposEmergenciasPendientes() {
        List<TipoEmergencia> tiposUnicos = getEmergenciasPendientes().stream()
                .map(Emergencia::getTipo)
                .distinct()
                .collect(Collectors.toList());

        List<HashMap<String, Object>> resultado = new ArrayList<>();
        int id = 1;
        for (TipoEmergencia tipo : tiposUnicos) {
            HashMap<String, Object> mapa = new HashMap<>();
            mapa.put("id", id++);
            mapa.put("nombre", tipo.name());
            mapa.put("tipo", tipo);
            resultado.add(mapa);
        }
        return resultado;
    }

    /**
     * Filtra las emergencias del método recibido y las ordena por prioridad. La
     * prioridad es la suma del cálculo de la estrategia gravedad y la estrategia
     * cercanía.
     *
     * @param tipo Tipo de emergencia que se quiere filtrar
     * @return Una lista de emergencias pendientes por atender del mismo tipo
     *         ordenadas por prioridad
     */
    public List<Emergencia> getEmergenciasPriorizadasPorTipo(TipoEmergencia tipo) {
        StrategyPrioridadGravedad gravedad = new StrategyPrioridadGravedad();
        StrategyPrioridadCercania cercania = new StrategyPrioridadCercania();

        return getEmergenciasPendientes().stream()
                .filter(e -> e.getTipo() == tipo)
                .sorted(Comparator.comparingInt(e -> gravedad.calcularPrioridad(e) + cercania.calcularPrioridad(e)))
                .collect(Collectors.toList());
    }

    /**
     * Prioriza las emergencias pendientes por ubicación utilizando la estrategia
     * de cercanía.
     *
     * @return Una lista de emergencias pendientes ordenadas por cercanía.
     */
    public List<Emergencia> priorizarEmergenciasPorUbicacion() {
        setEstrategiaPrioridad(new StrategyPrioridadCercania());
        return getEmergenciasPendientes().stream()
                .sorted(Comparator.comparingInt(strategyPrioridad::calcularPrioridad))
                .collect(Collectors.toList());
    }

    /**
     * Prioriza las emergencias pendientes por gravedad utilizando la estrategia
     * de gravedad.
     *
     * @return Una lista de emergencias pendientes ordenadas por gravedad.
     */
    public List<Emergencia> priorizarEmergenciasPorGravedad() {
        setEstrategiaPrioridad(new StrategyPrioridadGravedad());
        return getEmergenciasPendientes().stream()
                .sorted(Comparator.comparingInt(strategyPrioridad::calcularPrioridad))
                .collect(Collectors.toList());
    }

    /* -------------------------------------------------------------------------- */

    public void atenderEmergencia(Emergencia e) {
        if (e.isAtendida()) {
            System.out.println("Esta emergencia ya fue atendida.");
            return;
        }

        e.iniciarAtencion();

        // Muchachos esto es para simular el tiempo que tarda en atenderse una
        // emergencia
        // puede colocar cualquier cantidad de milisegundos 1000 milisegundos es 1
        // segundo
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        e.finalizarAtencion();
        System.out.println("Emergencia atendida: " + e.toString());

        emergenciasAtendidas++;
        tiempoTotalAtencion += e.getTiempoRespuesta();
    }

    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADÍSTICAS DEL DÍA ===");
        System.out.println("Emergencias atendidas: " + emergenciasAtendidas);

        long promedioMs = 0;
        if (emergenciasAtendidas > 0) {
            promedioMs = tiempoTotalAtencion / emergenciasAtendidas;
        }
        double promedioSeg = promedioMs / 1000.0;
        System.out.println("Tiempo promedio de respuesta: " + promedioSeg + " seg.");

        long noAtendidas = listaEmergencias.stream()
                .filter(e -> !e.isAtendida())
                .count();
        System.out.println("Emergencias no atendidas: " + noAtendidas);
    }

    public void finalizarJornada() {
        mostrarEstadisticas();
        System.out.println("Guardando registro del día (simulado)...");
        // Lógica para guardarlo en BD o archivo
        System.out.println("Sistema preparado para siguiente ciclo.");
    }

    public void setEstrategiaPrioridad(IPrioridad nuevaEstrategia) {
        strategyPrioridad = nuevaEstrategia;
    }

}
