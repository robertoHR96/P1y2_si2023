
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Mensaje {
    // Estructura del mensaje
}

class Canal {
    private BlockingQueue<Mensaje> queue = new LinkedBlockingQueue<>();

    // Enviar un mensaje al canal
    public void enviarMensaje(Mensaje mensaje) throws InterruptedException {
        queue.put(mensaje);
    }

    // Recibir un mensaje del canal
    public Mensaje recibirMensaje() throws InterruptedException {
        return queue.take();
    }
}

class ProcesoP extends Thread {
    private Canal canal;

    public ProcesoP(Canal canal) {
        this.canal = canal;
    }

    @Override
    public void run() {
        Mensaje mensaje = new Mensaje();

        try {
            // Enviar el mensaje al canal de manera síncrona
            canal.enviarMensaje(mensaje);
            // Realizar otras operaciones si es necesario
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProcesoQ extends Thread {
    private Canal canal;

    public ProcesoQ(Canal canal) {
        this.canal = canal;
    }

    @Override
    public void run() {
        try {
            // Recibir un mensaje del canal de manera síncrona
            Mensaje mensajeRecibido = canal.recibirMensaje();
            // Realizar operaciones con el mensaje recibido
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        Canal canal = new Canal();

        // Crear y comenzar los procesos P y Q
        ProcesoP procesoP = new ProcesoP(canal);
        ProcesoQ procesoQ = new ProcesoQ(canal);

        procesoP.start();
        procesoQ.start();
    }
}
