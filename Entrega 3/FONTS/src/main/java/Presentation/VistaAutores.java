package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class VistaAutores extends JFrame {
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JLabel tituloVista = new JLabel("Autores");
    private JPanel panelBuscar = new JPanel();
    private JTextField tfBuscador = new JTextField();
    private JButton botonBuscar = new JButton("Buscar");
    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tAutores = new JTable(modelo) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JScrollPane scroll = new JScrollPane(tAutores);
    private JMenu mMenu = new JMenu("Menu");
    private JMenuBar mbVista = new JMenuBar();
    private String buscado = "";


    public VistaAutores() {
        inicializar_vista();
        inicializar_lista();
        inicializar_panelBuscar();
        inicializar_panelSuperior();
        inicializar_panelContenidos();
        asignarListeners();
    }

    public void hacerVisible() {
        pack();
        setVisible(true);
    }

    private void hacerInvisible() {
        setVisible(false);
    }

    private void inicializar_vista() {
        setTitle("Gestor Archivos");
        setMinimumSize(new Dimension(700,500));
        setPreferredSize(getMinimumSize());
        setResizable(true);
        // Posicion y operaciones por defecto
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Panel
        setContentPane(panelContenidos);
    }

    private void inicializar_lista() {
        modelo.addColumn("Autor");
        actualizarModelo(MenuCtrl.INSTANCE.listarAutores(""));
        tAutores.setAutoCreateRowSorter(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(tAutores);
    }

    private void inicializar_panelBuscar() {
        panelBuscar.setLayout(new BorderLayout());
        panelBuscar.add(tfBuscador, BorderLayout.CENTER);
        panelBuscar.add(botonBuscar, BorderLayout.EAST);
    }

    private void inicializar_panelSuperior() {
        panelSuperior.setLayout(new GridLayout(2,1,5,5));
        tituloVista.setFont(new Font(null, 1, 20));
        panelSuperior.add(tituloVista);
        panelSuperior.add(panelBuscar);
        mbVista.add(mMenu);
        setJMenuBar(mbVista);
    }

    private void inicializar_panelContenidos() {
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelContenidos.add(panelSuperior, BorderLayout.NORTH);
        panelContenidos.add(scroll, BorderLayout.CENTER);
    }

    private void asignarListeners() {
        mMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                buscado = "";
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirMenu();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        tfBuscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscado = tfBuscador.getText();
                actualizarModelo(MenuCtrl.INSTANCE.listarAutores(buscado));
            }
        });

        botonBuscar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscado = tfBuscador.getText();
                System.out.println(buscado);
                actualizarModelo(MenuCtrl.INSTANCE.listarAutores(buscado));
            }
        });
    }

    /**
     * Actualiza el contenido de la tabla de autores.
     * @param listado
     */
    private void actualizarModelo(Set<String> listado) {
        DefaultTableModel modeloAux = new DefaultTableModel();
        modeloAux.addColumn("Autor");
        for (String entry : listado) {
            modeloAux.addRow(new Object[]{entry});
        }
        modelo = modeloAux;
        tAutores.setModel(modelo);
    }
}
