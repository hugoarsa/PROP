package Presentation;

import Exceptions.BackendException;

public class main {

    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (new Runnable() {
            public void run()  {
                try {
                    PresentationCtrl.INSTANCE.abrirVistaPrincipal();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
