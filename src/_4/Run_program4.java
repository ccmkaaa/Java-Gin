package _4;

import javax.swing.SwingUtilities;

import _4.Frame.TextCustom;

public class Run_program4 {
    public static void runProgram4() {
        SwingUtilities.invokeLater(() -> {
            TextCustom example = new TextCustom();
            example.setVisible(true);
        });
    }
}
