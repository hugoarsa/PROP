package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {

    // Componentes de la interficie gráfica
    private JPanel panelContenidos = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelDatos = new JPanel();
    private JButton botonLogIn = new JButton("Log in");
    private JButton botonSignUp = new JButton("Sign up");
    private JTextField tfUsername = new JTextField();
    private JPasswordField pfContrasena = new JPasswordField();
    private JLabel tituloVista = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
    private JLabel lUsername = new JLabel("Username", SwingConstants.LEFT);
    private JLabel lContrasena = new JLabel("Contraseña",  SwingConstants.LEFT);
    private JLabel lError = new JLabel("Username o Contraseña incorrectos", SwingConstants.CENTER);

    // Constructor y métodos públicos
    public VistaPrincipal() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelDatos();
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
        // Layout
        panelContenidos.setLayout(new GridLayout(4, 1, 5, 5));
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        tituloVista.setPreferredSize(new Dimension(200, 100));
        tituloVista.setFont(new Font("Iniciar Sesión", 1, 28));
        tituloVista.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        panelContenidos.add(tituloVista);
        panelContenidos.add(panelDatos);
        lError.setVisible(false);
        lError.setForeground(Color.RED);
        panelContenidos.add(lError);
        panelContenidos.add(panelBotones);
    }

    private void inicializar_panelDatos() {
        // Layout
        panelDatos.setLayout(new GridLayout(4,2,5,5));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(0,20,0,50));
        // Componentes
        panelDatos.add(lUsername);
        panelDatos.add(new JLabel(" "));
        panelDatos.add(tfUsername);
        panelDatos.add(new JLabel(" "));
        panelDatos.add(lContrasena);
        panelDatos.add(new JLabel(" "));
        panelDatos.add(pfContrasena);
    }

    private void inicializar_panelBotones() {
        //Layout
        panelBotones.setLayout(new GridLayout(2, 3, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10,0,40,0));
        // Componentes
        panelBotones.add(new JLabel(" "));
        panelBotones.add(botonLogIn);
        panelBotones.add(new JLabel(" "));
        panelBotones.add(new JLabel(" "));
        panelBotones.add(botonSignUp);

        JRootPane rootPane = botonLogIn.getRootPane();
        rootPane.setDefaultButton(botonLogIn);
    }

    private void iniciar_sesion() {
        String username = tfUsername.getText();
        String contrasena = new String(pfContrasena.getPassword());
        if(username == "" || contrasena == "") lError.setVisible(true);
        else if(PresentationCtrl.INSTANCE.verificarContrasena(username, contrasena)) {
            hacerInvisible();
            PresentationCtrl.INSTANCE.abrirVistaMenu(username);
            tfUsername.setText("");
            pfContrasena.setText("");
        }
        else {
            lError.setVisible(true);
        }
    }

    // Asignación de listeners
    private void asignar_listenersComponentes() {
        // Listeners para los botones
        botonLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciar_sesion();
            }
        });

        botonSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                PresentationCtrl.INSTANCE.abrirVistaRegistro();
                tfUsername.setText("");
                pfContrasena.setText("");
                lError.setVisible(false);
            }
        });
    }
}
