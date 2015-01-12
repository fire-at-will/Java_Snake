package com.willtaylor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    public JLabel scoreLabel;

    private Control control;

    public GameOverDialog(Control control) {

        this.control = control;

        setContentPane(contentPane);
        setModal(false);
        getRootPane().setDefaultButton(buttonOK);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        scoreLabel.setText("Your Score: " + control.score);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setSize(new Dimension(300, 200));

        setLocationRelativeTo(control.screen);

        setVisible(true);
    }

    private void onOK() {

        dispose();

        control.resetGame();
    }
}
