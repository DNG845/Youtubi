/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.youtubivlc;

/**
 *
 * @author danny
 */
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private final JTextArea textArea;
    private final JLabel imageLabel;
    private final EmbeddedMediaPlayerComponent mediaPlayer;
    private boolean isPaused = false;
    private static final String IP_ADDRESS = "192.168.1.40";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.setVisible(true);
        });
    }

    public Client() {
        setTitle("Contenido");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = Color.DARK_GRAY;
        Color foregroundColor = Color.WHITE;
        Color buttonColor = new Color(60, 63, 65);
        Color buttonHoverColor = new Color(75, 78, 80);
        Color buttonPressedColor = new Color(50, 53, 55);
        Color buttonTextColor = Color.WHITE;

        JButton musicButton = createButton("Música", buttonColor,
                buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton videoButton = createButton("Videos", buttonColor,
                buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton documentButton = createButton("Documentos", 
                buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton imageButton = createButton("Imágenes",
                buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);

        musicButton.addActionListener(e -> openMusic());
        videoButton.addActionListener(e -> openVideo());
        documentButton.addActionListener(e -> openDocument());
        imageButton.addActionListener(e -> openImage());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBackground(backgroundColor);
        panel.add(musicButton);
        panel.add(videoButton);
        panel.add(documentButton);
        panel.add(imageButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(backgroundColor);
        textArea.setForeground(foregroundColor);
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));
        textArea.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(new LineBorder(Color.BLACK));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.EAST);

        mediaPlayer = new EmbeddedMediaPlayerComponent();
        add(mediaPlayer, BorderLayout.NORTH);
    }

    private JButton createButton(String text, Color background, 
            Color hoverBackground, Color pressedBackground, 
            Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.BLACK));
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(20, 40, 20, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverBackground);
            }
        });

        return button;
    }

    private void openMusic() {
        JFrame musicFrame = new JFrame("Música");
        musicFrame.setSize(1280, 720);
        musicFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musicFrame.setLocationRelativeTo(null);
        musicFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JButton mp3Button1 = createButton("Reproducir MP3-1", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton mp3Button2 = createButton("Reproducir MP3-2", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);

        mp3Button1.addActionListener(e -> openContent(IP_ADDRESS, PORT, 
                "Avispas.mp3", "src/imagenes/Avispas.jpg"));
        mp3Button2.addActionListener(e -> openContent(IP_ADDRESS, PORT, 
                "My-or.mp3", "src/imagenes/My-or.jpg"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(mp3Button1);
        panel.add(mp3Button2);

        musicFrame.add(panel);
        musicFrame.setVisible(true);
    }

    private void openVideo() {
        JFrame videoFrame = new JFrame("Videos");
        videoFrame.setSize(1280, 720);
        videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        videoFrame.setLocationRelativeTo(null);
        videoFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JButton mp4Button1 = createButton("Reproducir MP4-1", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton mp4Button2 = createButton("Reproducir MP4-2", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);

        mp4Button1.addActionListener(e -> openContent(IP_ADDRESS, PORT,
                "java.mp4", null));
        mp4Button2.addActionListener(e -> openContent(IP_ADDRESS, PORT,
                "JS.mp4", null));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(mp4Button1);
        panel.add(mp4Button2);

        videoFrame.add(panel);
        videoFrame.setVisible(true);
    }

    private void openDocument() {
        JFrame documentFrame = new JFrame("Documentos");
        documentFrame.setSize(1280, 720);
        documentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        documentFrame.setLocationRelativeTo(null);
        documentFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JButton txtButton1 = createButton("Mostrar TXT-1", new Color(60, 63, 65), 
                new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton txtButton2 = createButton("Mostrar TXT-2", new Color(60, 63, 65), 
                new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        txtButton1.addActionListener(e -> openText(IP_ADDRESS, PORT,
                "dato.txt"));
        txtButton2.addActionListener(e -> openText(IP_ADDRESS, PORT,
                "dato2.txt"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(txtButton1);
        panel.add(txtButton2);

        documentFrame.add(panel);
        documentFrame.setVisible(true);
    }

    private void openText(String ipAddress, int port, String fileName) {
    JFrame textFrame = new JFrame("Texto");
    textFrame.setSize(1280, 720);
    textFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    textFrame.setLocationRelativeTo(null);
    textFrame.getContentPane().setBackground(Color.DARK_GRAY);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setBackground(Color.DARK_GRAY);
    textArea.setForeground(Color.WHITE);
    textArea.setFont(new Font("Serif", Font.PLAIN, 16));
    
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setBackground(Color.DARK_GRAY);
    scrollPane.setBorder(null);

    JButton closeButton = createButton("Cerrar", new Color(60, 63, 65), 
            new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

    closeButton.addActionListener(e -> textFrame.dispose());

    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    controlPanel.setBackground(Color.DARK_GRAY);
    controlPanel.add(closeButton);

    textFrame.setLayout(new BorderLayout());
    textFrame.add(scrollPane, BorderLayout.CENTER);
    textFrame.add(controlPanel, BorderLayout.SOUTH);

    requestTextFile(ipAddress, port, fileName, textArea);

    textFrame.setVisible(true);
}

    private void openImage() {
        JFrame imageFrame = new JFrame("Imágenes");
        imageFrame.setSize(1280, 720);
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.setLocationRelativeTo(null);
        imageFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JButton imageButton1 = createButton("Imagen 1", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton imageButton2 = createButton("Imagen 2", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton imageButton3 = createButton("Imagen 3", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton imageButton4 = createButton("Imagen 4", 
                new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);

        imageButton1.addActionListener(e -> displayImage(
                IP_ADDRESS, PORT, "JS.png"));
        imageButton2.addActionListener(e -> displayImage(
                IP_ADDRESS, PORT, "jdk.jpg"));
        imageButton3.addActionListener(e -> displayImage(
                IP_ADDRESS, PORT, "java.jpg"));
        imageButton4.addActionListener(e -> displayImage(
                IP_ADDRESS, PORT, "mine.png"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(imageButton1);
        panel.add(imageButton2);
        panel.add(imageButton3);
        panel.add(imageButton4);

        imageFrame.add(panel);
        imageFrame.setVisible(true);
    }

    private void displayImage(String ipAddress, int port, String imageName) {
        JFrame imageDisplayFrame = new JFrame("Mostrar Imagen");
        imageDisplayFrame.setSize(1280, 720);
        imageDisplayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageDisplayFrame.setLocationRelativeTo(null);
        imageDisplayFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            Socket socket = new Socket(ipAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET /" + imageName);

            File tempFile = File.createTempFile("temp", null);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();

            ImageIcon imageIcon = new ImageIcon(tempFile.getAbsolutePath());
            imageLabel.setIcon(imageIcon);

            socket.close();
        } catch (IOException e) {
        }

        imageDisplayFrame.add(imageLabel);
        imageDisplayFrame.setVisible(true);
    }

    private void openContent(String ipAddress, 
            int port, String fileName, String imagePath) {
        JFrame contentFrame = new JFrame("Contenido");
        contentFrame.setSize(1280, 720);
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentFrame.setLocationRelativeTo(null);

        JButton backButton = createButton
        ("Regresar", new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);
        JButton localPauseButton = createButton
        ("Pausar/Reanudar", new Color(60, 63, 65), new Color(75, 78, 80), 
                new Color(50, 53, 55), Color.WHITE);

        EmbeddedMediaPlayerComponent localMediaPlayer = 
                new EmbeddedMediaPlayerComponent();

        backButton.addActionListener(e -> {
            localMediaPlayer.mediaPlayer().controls().stop();
            contentFrame.dispose();
        });

        localPauseButton.addActionListener(e -> pauseMedia(localMediaPlayer, 
                localPauseButton));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.add(backButton);
        controlPanel.add(localPauseButton);

        contentFrame.setLayout(new BorderLayout());
        contentFrame.add(controlPanel, BorderLayout.NORTH);
        contentFrame.add(localMediaPlayer, BorderLayout.CENTER);

        if (imagePath != null) {
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon imageIcon = new ImageIcon(imagePath);
            imageLabel.setIcon(imageIcon);
            contentFrame.add(imageLabel, BorderLayout.EAST);
        }

        contentFrame.setVisible(true);
        playMedia(localMediaPlayer, ipAddress, port, fileName);
    }

    private void playMedia(EmbeddedMediaPlayerComponent mediaPlayer, 
            String ipAddress, int port, String fileName) {
        this.mediaPlayer.mediaPlayer().controls().stop();

        try {
            Socket socket = new Socket(ipAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET /" + fileName);

            File tempFile = File.createTempFile("temp", null);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();

            mediaPlayer.mediaPlayer().media().play(tempFile.getAbsolutePath());

            socket.close();
        } catch (IOException e) {
        }
    }

    private void requestTextFile(String ipAddress, int port, String fileName, 
            JTextArea textArea) {
        try {
            Socket socket = new Socket(ipAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET /" + fileName);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            textArea.setText(content.toString());

            socket.close();
        } catch (IOException e) {
        }
    }

    private void pauseMedia() {
        if (isPaused) {
            mediaPlayer.mediaPlayer().controls().play();
        } else {
            mediaPlayer.mediaPlayer().controls().pause();
        }
        isPaused = !isPaused;
    }

    private void pauseMedia(EmbeddedMediaPlayerComponent mediaPlayer,
            JButton pauseButton) {
        if (isPaused) {
            mediaPlayer.mediaPlayer().controls().play();
            pauseButton.setText("Pausar");
        } else {
            mediaPlayer.mediaPlayer().controls().pause();
            pauseButton.setText("Reanudar");
        }
        isPaused = !isPaused;
    }
}