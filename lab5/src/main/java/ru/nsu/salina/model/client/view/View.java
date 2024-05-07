package ru.nsu.salina.model.client.view;

import ru.nsu.salina.model.client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class View  extends JFrame {
    private final JTextField textField;
    private final JTextArea textArea;
    private final Client client;
    public View(Client client) {
        super("Messenger");
        this.client = client;

        setBackground(new Color(170, 170, 170));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField(40);
        textArea = new JTextArea(8, 40);
        setTextField();
        setTextArea();

        getContentPane().add(textField, "North");
        getContentPane().add(new JScrollPane(textArea), "Center");
        pack();

        textField.addActionListener(e -> {
            client.printInPW(textField.getText());
            textField.setText("");
        });
    }

    private void setTextArea() {
        textArea.setEditable(false);
        textArea.setBackground(new Color(170, 170, 170));
    }

    private void setTextField() {
        textField.setEditable(false);
        textField.setOpaque(false);
        textField.setForeground(Color.BLACK);
        textField.setSelectionColor(new Color(118, 218, 255, 100));
    }
    public String getName() {
        return JOptionPane.showInputDialog(this, "Enter your nickname", "", JOptionPane.QUESTION_MESSAGE);
    }

    public void allowWriting() {
        textField.setEditable(true);
    }

    public void broadcastMassage(BufferedReader bufferedReader) {
        String message = null;
        try {
            while ((message = bufferedReader.readLine()) != null) {
                if (message.startsWith("mes ")) {
                    textArea.append(message.substring(4) + "\n");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
