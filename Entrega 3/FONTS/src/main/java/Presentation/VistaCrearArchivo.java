package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

@SuppressWarnings("unchecked")
public class VistaCrearArchivo extends JFrame {
    // Componentes de la interficie gráfica
    private JLabel tituloVista = new JLabel("Crear Archivo");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JPanel panelBotones = new JPanel();

    // Panel Superior
    private JTextField tfTitulo = new JTextField("sin titulo");
    private JTextField tfAutor = new JTextField("sin autor");
    private DefaultComboBoxModel cbModelo = new DefaultComboBoxModel<>(new Object[]{"txt", "xml", "prop"});
    private JComboBox cbFormato = new JComboBox(cbModelo);

    // Panel Contenido
    private JTextArea taContenido = new JTextArea();

    // Botones
    private JButton botonGuardar = new JButton("Guardar");
    private JButton botonCancelar = new JButton("Cancelar");

    // Menus
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");

    // Constructor y métodos públicos
    public VistaCrearArchivo() {
        inicializar_frameVista();
        inicializar_mbVista();
        inicializar_panelContenidos();
        inicializar_panelSuperior();
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

    private void inicializar_mbVista() {
        mbVista.add(mMenu);
        setJMenuBar(mbVista);
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
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        panelContenidos.add(panelSuperior, BorderLayout.NORTH);
        taContenido.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane editorScrollPane = new JScrollPane(taContenido);
        //scrollPane
        editorScrollPane.setPreferredSize(new Dimension(250, 320));
        panelContenidos.add(editorScrollPane,BorderLayout.CENTER);
        panelContenidos.add(panelBotones,BorderLayout.SOUTH);
    }

    private void inicializar_panelSuperior() {
        panelSuperior.setLayout(new GridLayout(3,4, 5, 5));
        tituloVista.setFont(new Font(null, 1, 18));
        tituloVista.setBorder((BorderFactory.createEmptyBorder(0,0,5,0)));
        panelSuperior.add(tituloVista);
        panelSuperior.add(new JLabel(" "));
        panelSuperior.add(new JLabel("Formato: ", SwingConstants.RIGHT));
        panelSuperior.add(cbFormato);
        panelSuperior.add(new JLabel("Título:", SwingConstants.RIGHT));
        tfTitulo.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSuperior.add(tfTitulo);
        panelSuperior.add(new JLabel("Autor:", SwingConstants.RIGHT));
        tfAutor.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSuperior.add(tfAutor);
        panelSuperior.add(new JLabel("Contenido:", SwingConstants.LEFT));
        panelSuperior.add(new JLabel(" "));
        panelSuperior.add(new JLabel(" "));
    }

    private void inicializar_panelBotones() {
        panelBotones.setLayout(new GridLayout(1, 3, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5,100,0,0));
        panelBotones.add(new JLabel(" "));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
    }

    private void asignar_listenersComponentes() {
        // Listeners para los botones
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tfTitulo.getText();
                String autor = tfAutor.getText();
                String contenido = taContenido.getText();
                String formato = cbModelo.getElementAt(cbFormato.getSelectedIndex()).toString();
                MenuCtrl.INSTANCE.altaArchivo(titulo, autor, formato, contenido);

                reiniciarCampos();
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenu();
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarCampos();
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenu();
            }
        });

        // Listeners para las opciones de menu
        mMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                reiniciarCampos();
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
    }

    private void reiniciarCampos() {
        tfTitulo.setText("Sin título");
        tfAutor.setText("Sin autor");
        taContenido.setText("");
    }
}
