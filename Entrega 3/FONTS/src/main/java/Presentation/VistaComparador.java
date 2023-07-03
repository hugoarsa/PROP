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
import java.util.*;
import java.util.List;

@SuppressWarnings("unchecked")
public class VistaComparador extends JFrame {
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
    private JLabel lNumArchivos = new JLabel("Numero Archivos: ");
    private JTextField tfNumeroArc = new JTextField();
    private JLabel lArchivo = new JLabel("Archivo: ");
    private DefaultComboBoxModel modeloCb = new DefaultComboBoxModel();
    private JComboBox cbBuscador = new JComboBox<>(modeloCb);
    private JButton botonBuscar = new JButton("Buscar");

    // Tabla archivos
    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tArchivos = new JTable(modelo) {
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
    private List arc = new ArrayList();
    private String[] titolAutor = new String[2];
    private Integer wMode = 0;

    Pair<String, String> mErrorVacio = new Pair<String,String>("No se han encontrado resultados", "");

    public VistaComparador() {
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
        ArrayList<Pair<String, String>> listado = MenuCtrl.INSTANCE.listarPorTitulo("");
        actualizarComboBox(listado);
        actualizarModelo(listado);
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
        panelSuperior.setLayout(new GridLayout(5,1,5,5));
        tituloVista.setFont(new Font(null, 1, 20));
        panelSuperior.add(tituloVista);
        panelSuperior.add(panelOrden);
        panelSuperior.add(panelBuscar);
        panelSuperior.add(panelSeleccionador);
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

    private void inicializar_panelSeleccionador() {
        panelSeleccionador.setLayout(new GridLayout(1,2,5,5));
        panelSeleccionador.add(lArchivo);
        actualizarComboBox(MenuCtrl.INSTANCE.listarPorTitulo(""));
        panelSeleccionador.add(cbBuscador);
    }

    private void inicializar_panelBuscar() {
        panelBuscar.setLayout(new GridLayout(1,3,5,5));
        panelBuscar.add(lNumArchivos);
        panelBuscar.add(tfNumeroArc);
        panelBuscar.add(botonBuscar);
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

        tfNumeroArc.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num = Integer.valueOf(tfNumeroArc.getText());
                if (!arc.isEmpty()) actualizarModelo(MenuCtrl.INSTANCE.listarPorSemejanza(titolAutor[0], titolAutor[1], num, wMode));
            }
        });

        botonBuscar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num = Integer.valueOf(tfNumeroArc.getText());
                if (!arc.isEmpty()) actualizarModelo(MenuCtrl.INSTANCE.listarPorSemejanza(titolAutor[0], titolAutor[1], num, wMode));
            }
        });

        cbBuscador.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arc = Arrays.asList(modeloCb.getElementAt(cbBuscador.getSelectedIndex()));
                titolAutor = arc.get(0).toString().split("    ");
            }
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
                actualizarComboBox(MenuCtrl.INSTANCE.listarPorTitulo(""));
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
        tfNumeroArc.setText("");
    }

    /**
     * Actualiza el ComboBox
     * @param listado
     */
    private void actualizarComboBox(ArrayList<Pair<String, String>> listado) {
        ArrayList<String> lista = new ArrayList<>();
        DefaultComboBoxModel modeloCbAux = new DefaultComboBoxModel();
        for (Pair<String, String> entry : listado) {
            lista.add((entry.first() + "    " + entry.second()));
        }
        modeloCbAux.addAll(lista);
        modeloCb = modeloCbAux;
        cbBuscador.setModel(modeloCb);
    }
}
