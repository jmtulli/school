package br.tulli.jm.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Util {

  public static int showMessageDialog(String message, String title) {
    return showMessageDialog(message, title, 0);
  }

  public static int showMessageDialog(String message, String title, int defaultSelection) {
    Object[] options = {"Yes", "No"};
    return JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[defaultSelection]);
  }

  public static void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void showInformationMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }

  public static Image getImage(String image) throws IOException {
    InputStream in = Util.class.getClassLoader().getResourceAsStream(image);
    if (in != null) {
      return ImageIO.read(in);
    }
    return null;
  }

  public static void defineLookAndFeel(LookAndFeelTypes look) {
    try {
      UIManager.setLookAndFeel(look.getValue());
    } catch (Exception ex) {
    }
  }

  public enum LookAndFeelTypes {
    /**
     * Nimbus
     */
    NIMBUS("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"),
    /**
     * Windows
     */
    WINDOWS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
    /**
     * Metal
     */
    METAL("javax.swing.plaf.metal.MetalLookAndFeel");

    private final String valor;

    LookAndFeelTypes(String valor) {
      this.valor = valor;
    }

    public String getValue() {
      return valor;
    }
  }
}
