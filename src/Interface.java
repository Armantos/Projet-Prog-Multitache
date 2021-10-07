import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.net.*;
import java.io.*;

import Serveur.*;
import Composants.*;
import Client.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;

public class Interface extends javax.swing.JFrame {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface window = new Interface();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Interface() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnCrerUnClient = new JButton("Lancer le serveur");
        btnCrerUnClient.setBounds(144, 113, 150, 29);
        btnCrerUnClient.setBackground(Color.RED);
        btnCrerUnClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "Serveur lancé");

                ChatServer te = new ChatServer();
                te.main(null);
                // ChatServer.main(null);


            }
        });
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(btnCrerUnClient);

        JButton btnCrerUnClient_1 = new JButton("Connexion client");
        btnCrerUnClient_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ChatClient.main(null);
                } catch (UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.out.println("Fail");
                }

            }
        });
        btnCrerUnClient_1.setBounds(161, 167, 117, 29);
        frame.getContentPane().add(btnCrerUnClient_1);

        JLabel lblBienvenueSurNotre = new JLabel("Bienvenue sur le Client/Serveur");
        lblBienvenueSurNotre.setForeground(Color.RED);
        lblBienvenueSurNotre.setFont(new Font("Apple SD Gothic Neo", Font.BOLD | Font.ITALIC, 18));
        lblBienvenueSurNotre.setBounds(70, 33, 322, 23);
        frame.getContentPane().add(lblBienvenueSurNotre);

        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnFermer.setBounds(161, 232, 117, 29);
        frame.getContentPane().add(btnFermer);
    }
}
