package Presentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.*;
import java.util.Set;

@SuppressWarnings("unchecked")
public class VistaExpBooleana extends JFrame {
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private DefaultListModel modelo = new DefaultListModel<>();
    private JList lExpresiones = new JList<>(modelo);
    private JScrollPane scroll = new JScrollPane(lExpresiones);
    private JLabel tituloVista = new JLabel("Gestor de Expresiones Booleanas");
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");
    private JMenu mAnadir = new JMenu("Nueva Expresion");
    private JPopupMenu pmOpcionesExpresion = new JPopupMenu();
    private JMenuItem miBorrar = new JMenuItem("Borrar");
    private JMenuItem miEditar = new JMenuItem("Editar");
    private JMenuItem miUsar = new JMenuItem("Usar");

    private String mError = "No hay expresiones";

    public VistaExpBooleana() {
        inicializar_frameVista();
        inicializar_menus();
        inicializar_lista();
        inicializar_panelSuperior();
        inicializar_panelContenidos();
        asignar_listeners();
    }

    public void hacerVisible() {
        pack();
        setVisible(true);
    }

    private void hacerInvisible() {
        setVisible(false);
    }

    private void inicializar_frameVista() {
        setTitle("Gestor Archivos");
        setMinimumSize(new Dimension(700,500));
        setPreferredSize( getMinimumSize());
        setResizable(true);
        // Posición y operaciones por defecto
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Panel
        setContentPane(panelContenidos);
    }

    private void inicializar_menus() {
        mbVista.add(mMenu);
        mbVista.add(mAnadir);
        setJMenuBar(mbVista);

        pmOpcionesExpresion.add(miEditar);
        pmOpcionesExpresion.add(miBorrar);
        pmOpcionesExpresion.add(miUsar);
    }

    private void inicializar_lista() {
        actualizarModelo();
        lExpresiones.setLayoutOrientation(JList.VERTICAL);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(lExpresiones);
    }

    private void inicializar_panelSuperior() {
        panelSuperior.setLayout(new GridLayout(1,1,5,5));
        tituloVista.setFont(new Font(null, 1, 20));
        panelSuperior.add(tituloVista);
    }

    private  void inicializar_panelContenidos() {
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelContenidos.add(panelSuperior, BorderLayout.NORTH);
        panelContenidos.add(scroll, BorderLayout.CENTER);
    }

    private void asignar_listeners() {
        //LISTENERS
        mMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        mAnadir.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                    if(modelo.getElementAt(0).toString().equals(mError)) modelo.remove(0);
                    String expresion = VistaDialogo.INSTANCE.vistaNuevaExpresion();
                    if(!expresion.equals("")) {
                        MenuCtrl.INSTANCE.anadirExpresion(expresion);
                        actualizarModelo();
                    }
            }

            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        lExpresiones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pmOpcionesExpresion.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        miEditar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expresion = modelo.getElementAt(lExpresiones.getSelectedIndex()).toString();
                String expresion_nueva = VistaDialogo.INSTANCE.vistaEditarExpresion(expresion);
                if(!expresion_nueva.equals("")) {
                    MenuCtrl.INSTANCE.borrarExpresion(expresion);
                    MenuCtrl.INSTANCE.anadirExpresion(expresion_nueva);
                    actualizarModelo();
                }
            }
        });

        miBorrar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!modelo.getElementAt(lExpresiones.getSelectedIndex()).toString().equals(mError)) {
                    MenuCtrl.INSTANCE.borrarExpresion(modelo.get(lExpresiones.getSelectedIndex()).toString());
                    modelo.remove(lExpresiones.getSelectedIndex());
                }
                if(modelo.isEmpty()) modelo.add(0, mError);
            }
        });

        miUsar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenuBool(modelo.getElementAt(lExpresiones.getSelectedIndex()).toString());
            }
        });
    }

    /**
     * Actualiza el contenido de la lista de expresiones booleanas
     */
    private void actualizarModelo() {
        modelo.removeAllElements();
        Set<String> lista = MenuCtrl.INSTANCE.listarExpresiones();
        if(lista.isEmpty()) modelo.add(0, mError);
        else modelo.addAll(lista);
    }

}
