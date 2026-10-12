# JavaFX_Hilos_Practica

Repositorio de la semana 9, práctica guiada: Hilos en JavaFX.

**Asignatura:** Programación de Aplicaciones de Escritorio  
**Universidad Americana (UAM) — Unidad III: Programación Concurrente**

---

## Descripción

Aplicación JavaFX que simula el procesamiento de registros empresariales usando hilos (`Thread` + `Runnable`), sin bloquear la interfaz gráfica.

---

## Funcionalidades

### Procesador de Registros (Paso a Paso de la Guía)
- Procesa 100 registros simulados con una barra de progreso.
- El hilo corre en segundo plano — la ventana permanece receptiva.
- Botones: **Iniciar**, **Detener** (con `interrupt()`), **Reiniciar**.
- Actualización de controles con `Platform.runLater()`.

### Reto Final: Monitor Empresarial (Dos Hilos Concurrentes)
- **Hilo Clientes:** 100 registros a 80ms/registro (~8 segundos).
- **Hilo Productos:** 50 registros a 150ms/registro (~7.5 segundos).
- Dos barras de progreso independientes.
- Los botones se habilitan únicamente cuando **ambos** hilos terminan.
- Cada hilo se puede detener individualmente con `interrupt()`.

---

## Estructura del Proyecto

```
PracticaHilosJavaFX/
├── src/main/java/
│   ├── module-info.java
│   └── ni/edu/uam/practicahilos/
│       ├── MainApp.java
│       ├── ProcesadorController.java   ← Guía paso a paso
│       └── MonitorController.java      ← Reto final (2 hilos)
├── src/main/resources/
│   └── ni/edu/uam/practicahilos/
│       ├── procesador-view.fxml
│       └── monitor-view.fxml
└── pom.xml
```

---

## Conceptos Clave

| Concepto | Uso |
|---|---|
| `Thread` | Representa el hilo de ejecución |
| `Runnable` | Define la tarea que corre el hilo |
| `start()` | Inicia el hilo en paralelo |
| `sleep()` | Simula tiempo de procesamiento |
| `Platform.runLater()` | Actualiza la UI desde el hilo secundario |
| `interrupt()` | Solicita detención del hilo |
| `isInterrupted()` | Verifica si el hilo fue interrumpido |
| `setDaemon(true)` | El hilo no impide que cierre la JVM |

---

## Ejecutar

```bash
mvn javafx:run
```
