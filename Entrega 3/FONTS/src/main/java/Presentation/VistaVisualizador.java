package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaVisualizador extends JFrame {
    // Componentes de la interficie gráfica
    private JLabel tituloVista = new JLabel("Visualizador");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JPanel panelBotones = new JPanel();

    // Contenido
    private JTextPane tpContenido = new JTextPane();

    //Botones
    private JButton botonEditar = new JButton("Editar");

    // Menus
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");

    // Resto de atributos
    private String titulo = "";
    private String autor = "";
    private String contenido = "";

    // Constructor y métodos públicos
    public VistaVisualizador() {
        inicializar_frameVista();
        inicializar_mbVista();
        inicializar_panelContenidos();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    public void hacerVisible() {
        pack();
        setVisible(true);
        tpContenido.setText(contenido);
    }

    public void setArchivo(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        contenido = MenuCtrl.INSTANCE.get_raw(titulo, autor);
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
        //Layout
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        tituloVista.setFont(new Font(null, 1, 18));
        tituloVista.setBorder((BorderFactory.createEmptyBorder(0,0,10,0)));
        panelSuperior.add(tituloVista);
        panelContenidos.add(tituloVista, BorderLayout.NORTH);
        tpContenido.setBorder(BorderFactory.createLineBorder(Color.black));
        tpContenido.setEditable(false);
        //Put the editor pane in a scroll pane.
        JScrollPane editorScrollPane = new JScrollPane(tpContenido);
        editorScrollPane.setPreferredSize(new Dimension(250, 320));
        panelContenidos.add(editorScrollPane,BorderLayout.CENTER);
        panelContenidos.add(panelBotones,BorderLayout.SOUTH);
    }

    private void inicializar_panelBotones() {
        panelBotones.setLayout(new GridLayout(1, 3, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5,100,0,0));
        panelBotones.add(new JLabel(" "));
        panelBotones.add(new JLabel(" "));
        panelBotones.add(botonEditar);
    }

    // Asignación de listeners
    private void asignar_listenersComponentes() {

        // Listeners para los botones
        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirEditor(titulo, autor);
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
}
