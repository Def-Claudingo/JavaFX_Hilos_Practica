package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.concurrent.atomic.AtomicInteger;

public class MonitorController {

    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private Button btnReiniciar;
    private Thread hiloClientes;
    private Thread hiloProductos;

    private final AtomicInteger hilosTerminados = new AtomicInteger(0);

    @FXML private void iniciarProcesos() {

        if ((hiloClientes != null && hiloClientes.isAlive()) ||
            (hiloProductos != null && hiloProductos.isAlive())) {
            return;
        }

        hilosTerminados.set(0);

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Procesando clientes...");

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Procesando productos...");

        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        btnReiniciar.setDisable(true);

        Runnable tareaClientes = () -> {
            try {
                for (int i = 1; i <= 100; i++) {

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(80);

                    final int avance = i;

                    Platform.runLater(() -> {
                        barraClientes.setProgress(avance / 100.0);
                        lblPorcentajeClientes.setText(avance + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Clientes finalizados");
                    verificarFinalizacion();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Clientes detenidos");
                    verificarFinalizacion();
                });
            }
        };

        Runnable tareaProductos = () -> {
            try {
                for (int i = 1; i <= 50; i++) {

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(150);

                    final int avance = i;

                    Platform.runLater(() -> {
                        barraProductos.setProgress(avance / 50.0);
                        lblPorcentajeProductos.setText(avance + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Productos finalizados");
                    verificarFinalizacion();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Productos detenidos");
                    verificarFinalizacion();
                });
            }
        };

        hiloClientes = new Thread(tareaClientes);
        hiloClientes.setName("Hilo-Clientes");
        hiloClientes.setDaemon(true);

        hiloProductos = new Thread(tareaProductos);
        hiloProductos.setName("Hilo-Productos");
        hiloProductos.setDaemon(true);

        hiloClientes.start();
        hiloProductos.start();
    }

    private void verificarFinalizacion() {
        if (hilosTerminados.incrementAndGet() == 2) {
            habilitarBotones();
        }
    }

    @FXML private void detenerProcesos() {

        btnDetener.setDisable(true);

        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }

        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }

    @FXML private void reiniciarProcesos() {

        if ((hiloClientes != null && hiloClientes.isAlive()) ||
            (hiloProductos != null && hiloProductos.isAlive())) {
            return;
        }

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Esperando inicio");

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Esperando inicio");

        habilitarBotones();
    }

    private void habilitarBotones() {
        btnIniciar.setDisable(false);
        btnDetener.setDisable(true);
        btnReiniciar.setDisable(false);
    }

    public void detenerAlCerrar() {

        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }

        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }
}
