package Clase_08_II.view;

import java.util.List;
import java.util.Scanner;

import Clase_08_II.controller.SistemaEmergencias;
import Clase_08_II.model.Emergencia;
import Clase_08_II.model.FactoryEmergencias;
import Clase_08_II.model.services.Ambulancia;
import Clase_08_II.model.services.Bomberos;
import Clase_08_II.model.services.Policia;
import Clase_08_II.utils.NivelGravedad;
import Clase_08_II.utils.TipoEmergencia;

public class Main {

    public static void main(String[] args) {

        SistemaEmergencias sistema = SistemaEmergencias.getInstance();

        inicializarRecursosDemo(sistema);

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE EMERGENCIAS ===");
            System.out.println("1. Registrar una nueva emergencia");
            System.out.println("2. Ver estado de recursos disponibles");
            System.out.println("3. Atender una emergencia");
            System.out.println("4. Mostrar estadísticas del día");
            System.out.println("5. Finalizar jornada (cerrar sistema)");
            System.out.print("Seleccione una opción: ");

            int opcion = 0;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarEmergenciaMenu(sistema, sc);
                    break;
                case 2:
                    sistema.mostrarEstadoRecursos();
                    break;
                case 3:
                    atenderEmergenciaMenu(sistema, sc);
                    break;
                case 4:
                    sistema.mostrarEstadisticas();
                    break;
                case 5:
                    System.out.println("Finalizando jornada...");
                    sistema.finalizarJornada();
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        sc.close();
    }

    private static void inicializarRecursosDemo(SistemaEmergencias sistema) {
        sistema.registrarRecurso(new Bomberos("Unidad-B1", 5, 100));
        sistema.registrarRecurso(new Bomberos("Unidad-B2", 3, 80));
        sistema.registrarRecurso(new Ambulancia("Unidad-A1", 2, 100));
        sistema.registrarRecurso(new Ambulancia("Unidad-A2", 2, 60));
        sistema.registrarRecurso(new Policia("Unidad-P1", 4, 100));
        sistema.registrarRecurso(new Policia("Unidad-P2", 2, 70));
    }

    private static void registrarEmergenciaMenu(SistemaEmergencias sistema, Scanner sc) {
        System.out.println("\n=== REGISTRAR NUEVA EMERGENCIA ===");
        System.out.println("1. Incendio");
        System.out.println("2. Accidente Vehicular");
        System.out.println("3. Robo");
        System.out.print("Seleccione el tipo: ");
        TipoEmergencia tipo = null;
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                tipo = TipoEmergencia.INCENDIO;
                break;
            case 2:
                tipo = TipoEmergencia.ACCIDENTE_VEHICULAR;
                break;
            case 3:
                tipo = TipoEmergencia.ROBO;
                break;
        }

        System.out
                .print("Ingrese ubicación (ejemplo: Norte,Sur, Centro, este, oeste): ");
        String ubicacion = sc.nextLine();

        System.out.print("Ingrese nivel de gravedad (1. bajo, 2. medio, 3. alto): ");

        NivelGravedad nivelGravedad = null;
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                nivelGravedad = NivelGravedad.BAJO;
                break;
            case 2:
                nivelGravedad = NivelGravedad.MEDIO;
                break;
            case 3:
                nivelGravedad = NivelGravedad.ALTO;
                break;
            default:
                nivelGravedad = NivelGravedad.BAJO;
                break;

        }

        System.out.print("Ingrese tiempo estimado de atención (minutos): ");
        int tiempoEstimado = Integer.parseInt(sc.nextLine());

        Emergencia nueva = FactoryEmergencias.crearEmergencia(tipo, ubicacion, nivelGravedad, tiempoEstimado);
        if (nueva == null) {
            System.out.println("Tipo de emergencia inválido.");
            return;
        }

        sistema.registrarNuevaEmergencia(nueva);
        System.out.println("Emergencia registrada: " + nueva);
    }

    private static void atenderEmergenciaMenu(SistemaEmergencias sistema, Scanner sc) {
        List<Emergencia> pendientes = sistema.getEmergenciasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay emergencias pendientes.");
            return;
        }

        System.out.println("\n=== ATENDER EMERGENCIA ===");
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).toString());
        }
        System.out.print("Seleccione el número de la emergencia a atender: ");
        int indice = Integer.parseInt(sc.nextLine()) - 1;
        if (indice < 0 || indice >= pendientes.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Emergencia emergencia = pendientes.get(indice);
        sistema.asignarRecursosAEmergencia(emergencia);
        sistema.atenderEmergencia(emergencia);
    }

}
