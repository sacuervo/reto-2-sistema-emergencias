package view;

import controller.SistemaEmergencias;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.factory.Emergencia;
import model.factory.FactoryEmergencias;
import model.services.Ambulancia;
import model.services.Bomberos;
import model.services.Policia;
import utils.NivelGravedad;
import utils.TipoEmergencia;

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

        List<HashMap<String, Object>> tiposEmergenciasPendientes = sistema.getTiposEmergenciasPendientes();

        /*
         * Si solo hay un tipo de emergencia pendiente, atenderla(s) emergencias
         * automáticamente
         */

        if (tiposEmergenciasPendientes.size() == 1) {
            for (Emergencia pendiente : pendientes) {
                sistema.atenderEmergencia(pendiente);
                return;
            }
        }

        /*
         * Preguntarle al usuario el tipo de emergencia que desea atender
         */

        System.out.println("\n=== ATENDER EMERGENCIA ===");
        System.out.println("Seleccione el tipo de emergencia que desea atender: ");

        for (HashMap<String, Object> tipoEmergenciaMap : tiposEmergenciasPendientes) {
            System.out.printf("%d. %s%n", tipoEmergenciaMap.get("id"), tipoEmergenciaMap.get("nombre"));
        }

        Integer seleccionIdTipo = Integer.parseInt(sc.nextLine());
        TipoEmergencia seleccionTipo = null;

        for (HashMap<String, Object> tipoEmergenciaMap : tiposEmergenciasPendientes) {
            if (tipoEmergenciaMap.get("id").equals(seleccionIdTipo)) {
                seleccionTipo = (TipoEmergencia) tipoEmergenciaMap.get("tipo");
                break;
            }
        }

        if (seleccionTipo == null) {
            System.out.println("Tipo de emergencia no encontrado.");
            return;
        }

        /* Preguntar por la estrategia de priorización que se desea usar */
        System.out.println("Seleccione la estrategia de priorización: 1. Por ubicación, 2. Por gravedad, 3. Combinado");

        String eleccionEstrategia = sc.nextLine().trim();

        /*
         * Atender emergencias de la categoría indicada una por una en orden de
         * prioridad
         */

        switch (eleccionEstrategia) {
            case "1":
                pendientes = sistema.priorizarEmergenciasPorUbicacion();
            case "2":
                pendientes = sistema.priorizarEmergenciasPorGravedad();
            case "3":
                pendientes = sistema.getEmergenciasPriorizadasPorTipo(seleccionTipo);
                break;
            default:
                System.out.println("La estrategia de priorización no es válida.");
                return;
        }

        sistema.atenderEmergencia(pendientes.get(0));

    }

}