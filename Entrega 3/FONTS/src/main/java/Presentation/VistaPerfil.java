package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Domain.Pair;

public class VistaPerfil extends JFrame {
    // Componentes de la interficie gráfica
    private JPanel panelContenidos = new JPanel();
    private JPanel panelPerfil = new JPanel();
    private JPanel panelDatos = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JTextField tfUsername = new JTextField();
    private JTextField tfNombre = new JTextField();
    private JButton botonBorrarPerfil = new JButton("Borrar Perfil");
    private JButton botonEditarPerfil = new JButton("Editar Perfil");
    private JButton botonCambiarContrasena = new JButton("Cambiar Contraseña");
    private JButton botonLogOut = new JButton("Log out");
    private JLabel tituloVista = new JLabel("Mi Perfil");
    private JLabel lUsername = new JLabel("Username", SwingConstants.LEFT);
    private JLabel lNombre = new JLabel("Nombre", SwingConstants.LEFT);
    private JButton botonOK = new JButton("Guardar cambios");
    private JButton botonCancel = new JButton("Cancelar");

    // Menus
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");

    // Resto de atributos
    private String username = "";
    private String nombre = "";

    // Constructor y métodos públicos
    public VistaPerfil() {
        inicializar_frameVista();
        inicializar_mbVista();
        inicializar_panelContenidos();
        inicializar_panelPerfil();
        inicializar_panelDatos();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    public void hacerVisible() {
        pack();
        setVisible(true);
        tfUsername.setText(username);
        tfNombre.setText(nombre);
    }

    /**
     * Llena los campos de username y nombre con los parámetros entrados
     * @param username
     * @param nombre
     */
    public void setUsuario(String username, String nombre) {
        this.username = username;
        this.nombre = nombre;
    }

    private void hacerInvisible() {
        setVisible(false);
    }

    private void inicializar_frameVista() {
        setTitle("Gestor Archivos");
        // Tamaño
        setMinimumSize(new Dimension(700,500));
        setPreferredSize(getMinimumSize());
        setResizable(true);
        // Posicion y operaciones por defecto
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Panel
        setContentPane(panelContenidos);
    }

    private void inicializar_mbVista() {
        mbVista.add(mMenu);
        setJMenuBar(mbVista);
    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        panelContenidos.add(panelPerfil, BorderLayout.NORTH);
        panelContenidos.add(panelDatos,BorderLayout.CENTER);
        panelContenidos.add(panelBotones,BorderLayout.SOUTH);
    }

    private void inicializar_panelPerfil() {
        //Botones de edicion
        botonOK.setEnabled(false);
        botonOK.setVisible(false);
        botonCancel.setEnabled(false);
        botonCancel.setVisible(false);

        // Layout
        panelPerfil.setLayout(new BorderLayout());
        panelPerfil.setBorder(BorderFactory.createEmptyBorder(20,20,40,20));
        // Componentes
        tituloVista.setPreferredSize(new Dimension(200, 30));
        tituloVista.setFont(new Font(null, 1, 28));
        panelPerfil.add(tituloVista, BorderLayout.WEST);
        panelPerfil.add(botonLogOut, BorderLayout.EAST);
    }

    private void inicializar_panelDatos() {
        // Layout
        panelDatos.setLayout(new GridLayout(5,2,5,5));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10,20,100,150));
        // Componentes
        panelDatos.add(lUsername);
        panelDatos.add(new JLabel(" "));
        tfUsername.setEditable(false);
        tfUsername.setBorder(BorderFactory.createLineBorder(Color.black));
        tfUsername.setBackground(Color.white);
        panelDatos.add(tfUsername);
        panelDatos.add(new JLabel(" "));
        panelDatos.add(new JLabel(" "));
        panelDatos.add(new JLabel(" "));
        panelDatos.add(lNombre);
        panelDatos.add(new JLabel(" "));
        tfNombre.setEditable(false);
        tfNombre.setBorder(BorderFactory.createLineBorder(Color.black));
        tfNombre.setBackground(Color.white);
        panelDatos.add(tfNombre);
    }

    private void inicializar_panelBotones() {
        panelBotones.setLayout(new GridLayout(2, 3, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0,20,40,20));
        panelBotones.add(new Label(""));
        panelBotones.add(botonOK);
        panelBotones.add(botonCancel);
        panelBotones.add(botonEditarPerfil);
        panelBotones.add(botonCambiarContrasena);
        panelBotones.add(botonBorrarPerfil);
    }

    // Asignación de listeners
    private void asignar_listenersComponentes() {
        // Listeners para los botones
        botonLogOut.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.cerrar();
            }
        });
        botonEditarPerfil.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tfNombre.setEditable(true);
                tfUsername.setEditable(true);
                botonCambiarContrasena.setVisible(false);
                botonCambiarContrasena.setEnabled(false);
                botonEditarPerfil.setVisible(false);
                botonEditarPerfil.setEnabled(false);
                botonOK.setVisible(true);
                botonOK.setEnabled(true);
                botonLogOut.setVisible(false);
                botonLogOut.setEnabled(false);
                mMenu.setVisible(false);
                mMenu.setEnabled(false);
                botonBorrarPerfil.setEnabled(false);
                botonBorrarPerfil.setVisible(false);
                botonCancel.setEnabled(true);
                botonCancel.setVisible(true);
            }
        });
        botonOK.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!MenuCtrl.INSTANCE.modificarPerfil(tfUsername.getText(), tfNombre.getText())) {
                    tfUsername.setText(username);
                    tfNombre.setText(nombre);
                }
                salirDeEdicion();
            }
        });

        botonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfUsername.setText(username);
                tfNombre.setText(nombre);
                salirDeEdicion();
            }
        });
        
        botonCambiarContrasena.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Pair<String,String> contrasenas = VistaDialogo.INSTANCE.vistaCambiarContrasena();
                MenuCtrl.INSTANCE.cambiarContrasena(username, contrasenas.first(), contrasenas.second());
            }
        });
        botonBorrarPerfil.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String contrasena = VistaDialogo.INSTANCE.vistaBorrarPerfil();
                Boolean borrado = MenuCtrl.INSTANCE.borrarPerfil(username, contrasena);
                if(borrado) {
                    hacerInvisible();
                    MenuCtrl.INSTANCE.cerrar();
                }
            }
        });

        // Listeners para las opciones de menu
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
    }

    /**
     * Sale del modo edición de la vista Perfil
     */
    private void salirDeEdicion() {
        tfNombre.setEditable(false);
        tfUsername.setEditable(false);
        username = tfUsername.getText();
        nombre = tfNombre.getText();
        botonCambiarContrasena.setVisible(true);
        botonCambiarContrasena.setEnabled(true);
        botonEditarPerfil.setEnabled(true);
        botonEditarPerfil.setVisible(true);
        botonOK.setVisible(false);
        botonOK.setEnabled(false);
        botonLogOut.setVisible(true);
        botonLogOut.setEnabled(true);
        mMenu.setVisible(true);
        mMenu.setEnabled(true);
        botonBorrarPerfil.setEnabled(true);
        botonBorrarPerfil.setVisible(true);
        botonCancel.setEnabled(false);
        botonCancel.setVisible(false);
    }
}
