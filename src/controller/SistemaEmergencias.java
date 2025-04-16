package Clase_08_II.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Clase_08_II.model.AccidenteVehicular;
import Clase_08_II.model.Incendio;
import Clase_08_II.model.Robo;
import Clase_08_II.model.services.Ambulancia;
import Clase_08_II.model.services.Bomberos;
import Clase_08_II.model.services.Policia;
import Clase_08_II.model.strategy.IPrioridad;
import Clase_08_II.model.strategy.StrategyPrioridadGravedad;
import Clase_08_II.model.Emergencia;
import Clase_08_II.model.interfaces.IServicioEmergencia;
import Clase_08_II.model.observer.ObserverEmergencias;
import Clase_08_II.model.observer.SujetoEmergencias;

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
