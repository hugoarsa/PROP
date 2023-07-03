package Presentation;

import Domain.Pair;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("unchecked")
public class VistaQuery extends JFrame {
    // Componentes de la interficie gráfica
    private JLabel tituloVista = new JLabel("Comparador de Archivos");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JPanel panelOrden = new JPanel();
    private JPanel panelBuscar = new JPanel();

    // Botones de ordenacion
    private JLabel lOrden = new JLabel("Orden:");
    private JRadioButton rbtfdf  = new JRadioButton("tf-idf");
    private JRadioButton rbBm25  = new JRadioButton("Bm25");

    // Buscador
    private JPanel panelSeleccionador = new JPanel();
    private JLabel lArchivos = new JLabel("Numero Archivos: ");
    private JTextField tfNumArchivos = new JTextField();
    private JPanel panelQuery = new JPanel();
    private JLabel lQuery = new JLabel("Query: ");
    private JTextField tfQuery = new JTextField();
    private JButton botonBuscar = new JButton("Buscar");

    // Tabla archivos
    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tArchivos = new JTable(modelo){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JScrollPane scroll = new JScrollPane(tArchivos);

    // Menus
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");

    //PopupMenus
    private JPopupMenu pmOpcionesArchivo = new JPopupMenu();
    private JMenuItem miEliminar = new JMenuItem("Eliminar");
    private JMenuItem miEditar = new JMenuItem("Editar");
    private JMenuItem miExportar = new JMenuItem("Exportar");
    private JMenuItem miVer = new JMenuItem("Ver");

    // Resto de atributos
    private Integer num = 0;
    private Integer wMode = 0;
    private String query = "";

    Pair<String, String> mErrorVacio = new Pair<String,String>("No se han encontrado resultados", "");

    public VistaQuery() {
        inicializar_frameVista();
        inicializar_mbVista();
        inicializar_pmOpcionesArchivo();
        inicializar_panelOrden();
        inicializar_panelSeleccionador();
        inicializar_panelBuscar();
        inicializar_panelSuperior();
        inicializar_panelContenidos();
        asignar_listenersComponentes();
    }

    public void hacerVisible() {
        pack();
        setVisible(true);
        actualizarModelo(MenuCtrl.INSTANCE.listarPorTitulo(""));
    }

    private void hacerInvisible() {
        setVisible(false);
    }

    private void inicializar_frameVista() {
        setTitle("Gestor Archivos");
        setMinimumSize(new Dimension(700,500));
        setPreferredSize( getMinimumSize());
        setResizable(true);
        // Posicion y operaciones por defecto
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Panel
        setContentPane(panelContenidos);
    }

    private void inicializar_mbVista() {
        mbVista.add(mMenu);
        setJMenuBar(mbVista);
    }

    private void inicializar_pmOpcionesArchivo() {
        pmOpcionesArchivo.add(miEliminar);
        pmOpcionesArchivo.add(miExportar);
        pmOpcionesArchivo.add(miEditar);
        pmOpcionesArchivo.add(miVer);
    }

    private void inicializar_panelContenidos() {
        //Layout
        panelContenidos.setLayout(new BorderLayout());
        panelContenidos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Paneles
        panelContenidos.add(panelSuperior, BorderLayout.NORTH);
        //
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        tArchivos.setAutoCreateRowSorter(true);
        tArchivos.getTableHeader().setReorderingAllowed(false);
        actualizarModelo(MenuCtrl.INSTANCE.listarPorTitulo(""));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(tArchivos);
        //
        panelContenidos.add(scroll, BorderLayout.CENTER);
    }

    private void inicializar_panelSuperior() {
        panelSuperior.setLayout(new GridLayout(4,1,5,5));
        tituloVista.setFont(new Font(null, 1, 20));
        panelSuperior.add(tituloVista);
        panelSuperior.add(panelOrden);
        panelSuperior.add(panelSeleccionador);
        panelSuperior.add(panelBuscar);
    }


    private void inicializar_panelOrden() {
        ButtonGroup group = new ButtonGroup();
        group.add(rbtfdf);
        group.add(rbBm25);
        rbtfdf.setSelected(true);
        panelOrden.setLayout(new GridLayout(1,3,5,5));
        panelOrden.setBorder(BorderFactory.createEmptyBorder(0,0,0,280));
        panelOrden.add(lOrden);
        panelOrden.add(rbtfdf);
        panelOrden.add(rbBm25);
    }

    private void inicializar_panelBuscar() {
        panelQuery.setLayout(new BorderLayout());
        panelQuery.add(tfQuery, BorderLayout.CENTER);
        panelQuery.add(botonBuscar, BorderLayout.EAST);

        panelBuscar.setLayout(new GridLayout(1,2,5,5));
        panelBuscar.add(lArchivos);
        panelBuscar.add(tfNumArchivos);
    }

    private void inicializar_panelSeleccionador() {
        panelSeleccionador.setLayout(new GridLayout(1,2,5,5));
        panelSeleccionador.add(lQuery);
        panelSeleccionador.add(panelQuery);
    }

    private void asignar_listenersComponentes() {
        rbtfdf.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wMode = 0;
            }
        });

        rbBm25.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wMode = 1;
            }
        });

        tfQuery.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = tfQuery.getText();
                actualizarModelo(MenuCtrl.INSTANCE.listarPorQuery(query, num, wMode));
            }
        });

        tfNumArchivos.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num = Integer.valueOf(tfNumArchivos.getText());
                actualizarModelo(MenuCtrl.INSTANCE.listarPorQuery(query, num, wMode));
            }
        });

        botonBuscar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = tfQuery.getText();
                num = Integer.valueOf(tfNumArchivos.getText());
                actualizarModelo(MenuCtrl.INSTANCE.listarPorQuery(query, num, wMode));
            }
        });

        mMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                hacerInvisible();
                reiniciarCampos();
                MenuCtrl.INSTANCE.abrirMenu();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        tArchivos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pmOpcionesArchivo.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        miEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] seleccion = tArchivos.getSelectedRows();
                for(int i = seleccion.length - 1; i>=0; i--) {
                    Vector<String> sol = modelo.getDataVector().elementAt(seleccion[i]);
                    MenuCtrl.INSTANCE.borrarArchivo(sol.firstElement(), sol.lastElement());
                    modelo.removeRow(seleccion[i]);
                }
            }
        });

        miEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tArchivos.getSelectedRow();
                hacerInvisible();
                reiniciarCampos();
                MenuCtrl.INSTANCE.abrirEditor(tArchivos.getValueAt(fila, 0).toString(), tArchivos.getValueAt(fila, 1).toString());
            }
        });

        miVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tArchivos.getSelectedRow();
                hacerInvisible();
                reiniciarCampos();
                MenuCtrl.INSTANCE.abrirVisualizador(tArchivos.getValueAt(fila, 0).toString(), tArchivos.getValueAt(fila, 1).toString());
            }
        });

    }

    /**
     * Actualiza el contenido de la tabla de archivos.
     * @param listado
     */
    private void actualizarModelo(ArrayList<Pair<String,String>> listado) {
        DefaultTableModel modeloAux = new DefaultTableModel();
        modeloAux.addColumn("Titulo");
        modeloAux.addColumn("Autor");
        if(listado.isEmpty()) listado.add(mErrorVacio);
        for (Pair<String, String> entry : listado) {
            Object fila[] = new Object[]{entry.first(), entry.second()};
            modeloAux.addRow(fila);
        }
        modelo = modeloAux;
        tArchivos.setModel(modelo);
    }

    private void reiniciarCampos() {
        wMode = 0;
        num = 0;
        query = "";
        tfNumArchivos.setText("");
        tfQuery.setText(query);
    }
}
