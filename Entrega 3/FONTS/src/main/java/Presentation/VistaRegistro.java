package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaRegistro extends JFrame {

    // Componentes de la interficie gráfica
    private JPanel panelContenidos = new JPanel();
    private JPanel panelDatos1 = new JPanel();
    private JPanel panelDatos2 = new JPanel();
    private JPanel panelDatos3 = new JPanel();
    private JPanel panelDatos4 = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JTextField tfUsername = new JTextField();
    private JTextField tfNombre = new JTextField();
    private JButton botonSignUp = new JButton("Sign up");
    private JButton botonCancelar = new JButton("Cancelar");
    private JPasswordField pfContrasena = new JPasswordField();
    private JPasswordField pfContrasena2 = new JPasswordField();
    private JLabel tituloVista = new JLabel("Registro", SwingConstants.CENTER);
    private JLabel lUsername = new JLabel("Username", SwingConstants.LEFT);
    private JLabel lNombre = new JLabel("Nombre", SwingConstants.LEFT);
    private JLabel lContrasena = new JLabel("Contraseña", SwingConstants.LEFT);
    private JLabel lContrasena2 = new JLabel("Repite Contraseña", SwingConstants.LEFT);

    // Constructor y métodos públicos
    public VistaRegistro() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelDatos1();
        inicializar_panelDatos2();
        inicializar_panelDatos3();
        inicializar_panelDatos4();
        inicializar_panelBotones();
        asignar_listenersComponentes();
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

    private void inicializar_panelContenidos() {
        //Layout
        panelContenidos.setLayout(new GridLayout(7, 1, 5, 5));
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        tituloVista.setFont(new Font(null, 1, 28));
        tituloVista.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        panelContenidos.add(tituloVista);
        panelContenidos.add(panelDatos1);
        panelContenidos.add(panelDatos2);
        panelContenidos.add(panelDatos3);
        panelContenidos.add(panelDatos4);
        panelContenidos.add(panelBotones);
    }

    private void inicializar_panelDatos1() {
        //Layout
        panelDatos1.setLayout(new GridLayout(2, 2, 5, 5));
        panelDatos1.setBorder(BorderFactory.createEmptyBorder(0,20,0,50));
        // Componentes
        panelDatos1.add(lUsername);
        panelDatos1.add(new JLabel(" "));
        panelDatos1.add(tfUsername);
    }
    private void inicializar_panelDatos2() {
        //Layout
        panelDatos2.setLayout(new GridLayout(2, 2, 5, 5));
        panelDatos2.setBorder(BorderFactory.createEmptyBorder(0,20,0,50));
        // Componentes
        panelDatos2.add(lNombre);
        panelDatos2.add(new JLabel(" "));
        panelDatos2.add(tfNombre);
    }

    private void inicializar_panelDatos3() {
        //Layout
        panelDatos3.setLayout(new GridLayout(2, 2, 5, 5));
        panelDatos3.setBorder(BorderFactory.createEmptyBorder(0,20,0,50));
        // Componentes
        panelDatos3.add(lContrasena);
        panelDatos3.add(new JLabel(" "));
        panelDatos3.add(pfContrasena);
    }

    private void inicializar_panelDatos4() {
        //Layout
        panelDatos4.setLayout(new GridLayout(2, 2, 5, 5));
        panelDatos4.setBorder(BorderFactory.createEmptyBorder(0,20,0,50));
        // Componentes
        panelDatos4.add(lContrasena2);
        panelDatos4.add(new JLabel(" "));
        panelDatos4.add(pfContrasena2);
    }

    private void inicializar_panelBotones() {
        //Layout
        panelBotones.setLayout(new GridLayout(1, 3, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0,50,30,50));
        // Componentes
        panelBotones.add(new JLabel(" "));
        panelBotones.add(botonSignUp);
        panelBotones.add(botonCancelar);
    }

    // Asignación de listeners
    private void asignar_listenersComponentes() {

        // Listeners para los botones
        botonSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String nombre = tfNombre.getText();
                String contrasena = new String(pfContrasena.getPassword());
                String contrasena2 = new String(pfContrasena2.getPassword());
                Boolean creado = PresentationCtrl.INSTANCE.nuevoPerfil(username, contrasena, contrasena2, nombre);
                if(creado) {
                    PresentationCtrl.INSTANCE.abrirVistaPrincipal();
                    hacerInvisible();
                }
                resetCampos();
            }
        });
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                PresentationCtrl.INSTANCE.abrirVistaPrincipal();
                resetCampos();
            }
        });
    }

    private void resetCampos() {
        tfUsername.setText("");
        tfNombre.setText("");
        pfContrasena.setText("");
        pfContrasena2.setText("");
    }
}
