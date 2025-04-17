# Reto 2 – Sistema de Emergencias

Este proyecto es una aplicación de consola en Java para la gestión de emergencias. Permite registrar distintos tipos de incidentes (incendios, accidentes vehiculares y robos), priorizarlos según gravedad y cercanía, asignar recursos (bomberos, ambulancias, policía) y llevar estadísticas diarias.

---

## Arquitectura MVC

- **Model**  
  Clases que representan el dominio y la lógica de negocio:

  - `model.factory.Emergencia` y sus subclases (`Incendio`, `AccidenteVehicular`, `Robo`)
  - `model.services.*` (recursos de atención)
  - `model.strategy.*` (módulo de cálculo de prioridad)
  - `model.observer.*` (mecanismo de notificaciones internas)

- **View**  
  Interfaz de usuario por consola:

  - `view.Main` controla menús, recibe input del usuario, muestra resultados y llama al controlador.

- **Controller**  
  Coordina el flujo entre la vista y el modelo:
  - `controller.SistemaEmergencias` gestiona la lista de emergencias, recursos, estadísticas y delega a los patrones de diseño.

---

## Patrones de Diseño

1. **Factory**

   - `model.factory.FactoryEmergencias`: crea instancias de `Emergencia` según el tipo seleccionado (robo, incendio, accidente).

2. **Observer**

   - `model.observer.SujetoEmergencias` y `model.observer.ObserverEmergencias`: notifican a los observadores cada vez que se registra una nueva emergencia.

3. **Strategy**
   - `model.strategy.IPrioridad`: interfaz común para calcular una puntuación de prioridad.
   - `StrategyPrioridadGravedad` y `StrategyPrioridadCercania`: dos estrategias que ponderan la atención según el nivel de gravedad o la distancia al incidente.

---

## Estructura del Proyecto

```
reto-2-sistema-emergencias/
├── src/
│   ├── controller/           # Lógica de control (MVC)
│   ├── model/
│   │   ├── factory/          # Clases de emergencia y Factory
│   │   ├── services/         # Recursos de atención
│   │   ├── strategy/         # Cálculo de prioridad
│   │   └── observer/         # Observer/Subject
│   ├── utils/                # Enumeraciones y helpers
│   └── view/                 # Interfaz de consola
├── README.md
└── .gitignore
```

---

## Requisitos

- Java 8+ instalado
- Terminal (macOS/Linux), CMD o PowerShell (Windows)

---

## Compilar y Ejecutar

1. Desde la raíz del proyecto, crea un directorio para clases compiladas:

   ```bash
   mkdir -p bin
  
   ```

2. Compila todo el código fuente en `src/`

   ```bash
    javac -d bin $(find src -name "*.java")
   
   ```

3. Ejecuta la aplicación:
   ```bash
    java -cp bin view.Main
   
   ```

## Uso Básico

### 1. Registrar emergencias
   Elige tipo, ubicación, nivel de gravedad y tiempo estimado de atención.

### 2. Ver recursos
   Lista unidades de bomberos, ambulancias y policía con su disponibilidad.

### 3. Atender emergencias
   Selecciona el tipo de incidente y la estrategia de priorización (ubicación, gravedad o combinado).

### 4. Estadísticas
   Muestra número de emergencias atendidas, tiempo promedio de respuesta y pendientes.

5. Finalizar jornada
   Guarda (simulado) las estadísticas y reinicia el sistema para un nuevo ciclo.
