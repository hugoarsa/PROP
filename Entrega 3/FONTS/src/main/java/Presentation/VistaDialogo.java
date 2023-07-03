package Presentation;

import Domain.Pair;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VistaDialogo extends JDialog {
    public static VistaDialogo INSTANCE = new VistaDialogo();

    private VistaDialogo() {}

    /**
     * Muestra una vista de diálogo con tres opciones a hacer en caso de conflicto entre archivos: sobreescribir,
     * guardar con otro título/autor o cancelar.
     * @return option
     */
    public static Integer excConflictoArc() {
        Integer option = JOptionPane.showOptionDialog(null, "Ya existe el archivo\n ¿Qué quiere hacer?", "Archivo ya existente", JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.WARNING_MESSAGE, null, new Object[] {"Sobreescribir", "Guardar con otro título/autor", "Cancelar"}, "Cancelar");
        return option;
    }

    /**
     * Devuelve el path seleccionado del directorio donde se quiere exportar
     * @return path del directorio seleccionado
     */
    public static String vistaEquipoExportar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    /**
     * Devuelve el path del archivo(s) que se quiere importar.
     * @return path del archivo seleccionado
     */
    public static String vistaEquipoImportar() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos validos (.prop, .txt, .xml)", "prop", "txt", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    /**
     * Muestra una vista con el mensaje de error 'mensaje'.
     * @param mensaje
     */
    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje);
    }

    /**
     * Muestra una vista la cual solicita el input de una nueva expresión booleana
     * @return expresion
     */
    public static String vistaNuevaExpresion() {
        String expresion = JOptionPane.showInputDialog(null, "Introduce la nueva expresión", "Nueva Expresión", JOptionPane.QUESTION_MESSAGE);
        return expresion;
    }

    /**
     * Muestra una vista la cual permite la edición de la expresión booleana seleccionada
     * @param expresion
     * @return expresión modificada
     */
    public static String vistaEditarExpresion(String expresion) {
        JTextField tfExp = new JTextField(expresion);
        int change = JOptionPane.showConfirmDialog(null, tfExp, "Introduce los cambios", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(change == JOptionPane.OK_OPTION) {
            return tfExp.getText();
        }
        return "";
    }

    /**
     * Muestra una vista ofreciendo una selección de distintos formatos a seleccionar
     * @return formato seleccionado
     */
    public static String vistaCambiarFormato() {
        Object obj = JOptionPane.showInputDialog(null, "Seleccione un formato: ", "Selector de formato", JOptionPane.QUESTION_MESSAGE, null, new Object[] { "xml", "txt", "prop" }, "xml");
        return obj.toString();
    }

    /**
     * Muestra dos vistas las cuales solicitan una contraseña de confirmación y preguntan por la confirmación de borrado.
     * Si se selecciona un "cancelar" devuelve un String vacío.
     * @return option
     */
    public static String vistaBorrarPerfil() {
        JPasswordField pf = new JPasswordField();
        Integer contra = JOptionPane.showConfirmDialog(null, pf, "Introduce la contraseña para borrar el perfil: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(contra == JOptionPane.OK_OPTION) {
            Integer option = JOptionPane.showOptionDialog(null, "¿Seguro que quiere borrar el perfil?", "Borrar perfil", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, new Object[] {"Borrar", "Cancelar"}, "Cancelar");
            if(option == JOptionPane.OK_OPTION) return new String(pf.getPassword());
        }
        return "";
    }

    /**
     * Muestra dos vistas las cuales solicitan una contraseña de confirmación y la nueva contraseña.
     * Si se selecciona un "cancelar" devuelve un Pair de Strings vacíos.
     * @return contraseña original y la nueva contraseña
     */
    public static Pair<String,String> vistaCambiarContrasena() {
        JPasswordField pf = new JPasswordField();
        JPasswordField pfc = new JPasswordField();
        int contra = JOptionPane.showConfirmDialog(null, pf, "Introduce la contraseña: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(contra == JOptionPane.OK_OPTION) {
            int new_contra = JOptionPane.showConfirmDialog(null, pfc, "Introduce la nueva contraseña: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(new_contra == JOptionPane.OK_OPTION) {
                return new Pair<String,String>(new String(pf.getPassword()), new String(pfc.getPassword()));
            }
        }
        return new Pair<>("","");
    }

    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (new Runnable() {
            public void run() {
                vistaEditarExpresion("patata");
            }
        });
    }

}
