package Presentation;

import Domain.Pair;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class VistaMenu extends JFrame {

    // Componentes de la interficie gráfica
    private JLabel tituloVista = new JLabel("Menu");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JPanel panelOrden = new JPanel();
    private JPanel panelBuscar = new JPanel();

    //Botones de ordenacion
    private JLabel lOrden = new JLabel("Tipo Búsqueda: ");
    private JRadioButton rbTipoBusTitulo = new JRadioButton("por Titulo");
    private JRadioButton rbTipoBusAutor = new JRadioButton("por Autor");
    private JRadioButton rbTipoBusBool = new JRadioButton("por Bool");

    //PopupMenus
    private JPopupMenu pmOpcionesArchivo = new JPopupMenu();
    private JMenuItem miEliminar = new JMenuItem("Eliminar");
    private JMenuItem miEditar = new JMenuItem("Editar");
    private JMenuItem miExportar = new JMenuItem("Exportar");
    private JMenuItem miVer = new JMenuItem("Ver");

    //Tabla Archivos
    private JTextField tfBuscador = new JTextField();
    private JButton botonBuscar = new JButton("Buscar");
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
    private JMenu mArchivo = new JMenu("Archivo");
    private JMenu mBuscar = new JMenu("Buscar");
    private JMenu mPerfil = new JMenu("Perfil");
    private JMenuItem miComparar = new JMenuItem("Comparar");
    private JMenuItem miQuery = new JMenuItem("Query");
    private JMenuItem miImportar = new JMenuItem("Importar");
    private JMenuItem miExpresionBooleana = new JMenuItem("Gestor ExpBooleana");
    private JMenuItem miAutores = new JMenuItem("Autores");
    private JMenuItem miCerrar = new JMenuItem("Cerrar");
    private JMenuItem miNuevo = new JMenuItem("Nuevo Archivo");


    // Resto de atributos
    private String buscado = "";

    Pair<String, String> mErrorVacio = new Pair<String,String>("No se han encontrado resultados", "");

    public VistaMenu() {
        inicializar_frameVista();
        inicializar_mbVista();
        inicializar_pmOpcionesArchivo();
        inicializar_panelOrden();
        inicializar_panelBuscar();
        inicializar_panelSuperior();
        inicializar_panelContenidos();
        asignar_listenersComponentes();
    }

    public void hacerVisible() {
        rbTipoBusTitulo.setSelected(true);
        rbTipoBusAutor.setSelected(false);
        rbTipoBusBool.setSelected(false);
        actualizarLista();
        pack();
        setVisible(true);
    }

    /**
     * Abre el menú con los resultados de la búsqueda por la expresion booleana 'expresion'
     * @param expresion
     */
    public void abrirMenuBool(String expresion) {
        rbTipoBusTitulo.setSelected(false);
        rbTipoBusAutor.setSelected(false);
        rbTipoBusBool.setSelected(true);
        buscado = expresion;
        tfBuscador.setText(expresion);
        actualizarLista();
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
        //Panel
        setContentPane(panelContenidos);
    }

    private void inicializar_mbVista() {
        mArchivo.add(miImportar);
        mArchivo.add(miExpresionBooleana);
        mArchivo.add(miNuevo);
        mBuscar.add(miComparar);
        mBuscar.add(miQuery);
        mBuscar.add(miAutores);
        mbVista.add(mArchivo);
        mbVista.add(mBuscar);
        mbVista.add(mPerfil);
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
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(tArchivos);
        //
        panelContenidos.add(scroll, BorderLayout.CENTER);
    }

    private void inicializar_panelSuperior() {
        panelSuperior.setLayout(new GridLayout(3,1,5,5));
        tituloVista.setFont(new Font(null, 1, 20));
        panelSuperior.add(tituloVista);
        panelSuperior.add(panelOrden);
        panelSuperior.add(panelBuscar);
    }

    private void inicializar_panelOrden() {
        ButtonGroup group = new ButtonGroup();
        group.add(rbTipoBusTitulo);
        group.add(rbTipoBusAutor);
        group.add(rbTipoBusBool);
        rbTipoBusTitulo.setSelected(true);
        rbTipoBusAutor.setSelected(false);
        rbTipoBusBool.setSelected(false);
        panelOrden.setLayout(new GridLayout(1,4,5,5));
        panelOrden.setBorder(BorderFactory.createEmptyBorder(0,0,0,120));
        panelOrden.add(lOrden);
        panelOrden.add(rbTipoBusTitulo);
        panelOrden.add(rbTipoBusAutor);
        panelOrden.add(rbTipoBusBool);
    }

    private void inicializar_panelBuscar() {
        panelBuscar.setLayout(new BorderLayout());
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        panelBuscar.add(tfBuscador, BorderLayout.CENTER);
        panelBuscar.add(botonBuscar, BorderLayout.EAST);
    }

    private void asignar_listenersComponentes() {
        tfBuscador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscado = tfBuscador.getText();
                actualizarLista();
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscado = tfBuscador.getText();
                actualizarLista();
            }
        });

        tArchivos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pmOpcionesArchivo.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        //OPCIONES DEL POPUPMENU
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
                MenuCtrl.INSTANCE.abrirEditor(tArchivos.getValueAt(fila, 0).toString(), tArchivos.getValueAt(fila, 1).toString());
            }
        });

        miVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tArchivos.getSelectedRow();
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirVisualizador(tArchivos.getValueAt(fila, 0).toString(), tArchivos.getValueAt(fila, 1).toString());
            }
        });

        miNuevo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuCtrl.INSTANCE.abrirCreadorArchivo();
                hacerInvisible();
            }
        });

        miExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = VistaDialogo.INSTANCE.vistaEquipoExportar();
                if(path != "") {
                    int[] seleccion = tArchivos.getSelectedRows();
                    for(int i = seleccion.length - 1; i>=0; i--) {
                        Vector<String> sol = modelo.getDataVector().elementAt(seleccion[i]);
                        System.out.println(sol.firstElement() + " " + sol.lastElement() + " PATH: " + path);
                        MenuCtrl.INSTANCE.exportarArchivo(sol.firstElement(), sol.lastElement(), path);
                    }
                }
            }
        });

        //OPCIONES DEL MENU ARCHIVO
        miImportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = VistaDialogo.INSTANCE.vistaEquipoImportar();
                if(path != "") {
                    MenuCtrl.INSTANCE.importarArchivo(path);
                    actualizarLista();
                }
            }
        });

        miExpresionBooleana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirGestorExpresion();
            }
        });

        //OPCIONES DEL MENU BUSCAR
        miQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirVistaQuery();
            }
        });

        miComparar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirComparador();
            }
        });

        miAutores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.abrirAutores();
            }
        });

        //OPCIONES DEL MENU PERFIL
        mPerfil.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                MenuCtrl.INSTANCE.abrirPerfil();
                hacerInvisible();
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        miCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerInvisible();
                MenuCtrl.INSTANCE.cerrar();
            }
        });

        scroll.addComponentListener(new ComponentAdapter() {
        });
    }

    private void actualizarLista() {
        ArrayList<Pair<String,String>> listado = new ArrayList<Pair<String,String>>();
        if(rbTipoBusTitulo.isSelected()) {
            listado.addAll(MenuCtrl.INSTANCE.listarPorTitulo(buscado));
        }
        else if(rbTipoBusAutor.isSelected()) {
            listado.addAll(MenuCtrl.INSTANCE.listarPorAutor(buscado));
        }
        else {
            listado.addAll(MenuCtrl.INSTANCE.listarPorBool(buscado));
        }
        actualizarModelo(listado);
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
}


