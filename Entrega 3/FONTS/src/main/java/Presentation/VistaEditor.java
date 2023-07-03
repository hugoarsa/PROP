package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;

public class VistaEditor extends JFrame {

    // Componentes de la interficie gráfica
    private JLabel tituloVista = new JLabel("Editor");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelSuperior = new JPanel();
    private JPanel panelBotones = new JPanel();

    // Panel Superior
    private JTextField tfTitulo = new JTextField();
    private JTextField tfAutor = new JTextField();
    private JLabel lTitulo = new JLabel("Titulo:", SwingConstants.RIGHT);
    private JLabel lAutor = new JLabel("Autor:", SwingConstants.RIGHT);
    private JLabel lGuardado = new JLabel("Guardado", SwingConstants.RIGHT);

    // Panel Contenido
    private JTextArea taContenido = new JTextArea();
    private JLabel lContenido = new JLabel("Contenido:", SwingConstants.LEFT);

    // Botones
    private JButton botonGuardar = new JButton("Guardar");
    private JButton botonCancelar = new JButton("Cancelar");

    // Menus
    private JMenuBar mbVista = new JMenuBar();
    private JMenu mMenu = new JMenu("Menu");
    private JMenu mCambiarFormato = new JMenu("Cambiar Formato");

    // Resto de atributos
    private String titulo = "";
    private String autor = "";
    private String contenido = "";

    // Constructor y métodos públicos
    public VistaEditor() {
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
        tfTitulo.setText(titulo);
        tfAutor.setText(autor);
        taContenido.setText(contenido);
    }

    public void setArchivo(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.contenido = MenuCtrl.INSTANCE.consultaArchivo(titulo, autor);
    }

    private void hacerInvisible() {
        setVisible(false);
    }

    private void inicializar_mbVista() {
        mbVista.add(mMenu);
        mbVista.add(mCambiarFormato);
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
        //Put the editor pane in a scroll pane.
        JScrollPane editorScrollPane = new JScrollPane(taContenido);
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
        panelSuperior.add(new JLabel(" "));
        lGuardado.setVisible(false);
        panelSuperior.add(lGuardado);
        panelSuperior.add(lTitulo);
        tfTitulo.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSuperior.add(tfTitulo);
        panelSuperior.add(lAutor);
        tfAutor.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSuperior.add(tfAutor);
        panelSuperior.add(lContenido);
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
                String titulo_ant = titulo;
                String autor_ant = autor;
                titulo = tfTitulo.getText();
                autor = tfAutor.getText();
                contenido = taContenido.getText();
                MenuCtrl.INSTANCE.editarArchivo(titulo_ant, autor_ant, titulo, autor, contenido);
                lGuardado.setVisible(true);
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfTitulo.setText(titulo);
                tfAutor.setText(autor);
                taContenido.setText(contenido);
            }
        });

        mCambiarFormato.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String formato = VistaDialogo.INSTANCE.vistaCambiarFormato();
                MenuCtrl.INSTANCE.cambiarFormato(titulo,autor,formato);
                contenido = MenuCtrl.INSTANCE.consultaArchivo(titulo,autor);
                taContenido.setText(contenido);
            }

            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
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



        // Listeners para los campos de texto
        tfTitulo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {}
            @Override
            public void keyReleased(KeyEvent arg0) {}
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (taContenido.getText() != contenido) lGuardado.setVisible(false);
            }
        });

        tfAutor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {}
            @Override
            public void keyReleased(KeyEvent arg0) {}
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (taContenido.getText() != contenido) lGuardado.setVisible(false);
            }
        });

        taContenido.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {}
            @Override
            public void keyReleased(KeyEvent arg0) {}
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (taContenido.getText() != contenido) lGuardado.setVisible(false);
            }
        });
    }
}