package br.tulli.jm.util;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BlockingGlassPane extends JPanel {
  private Integer timeOut = 60;
  private Timer timer = new Timer();
  private TemporizadorTimeOut timerTask;

  public BlockingGlassPane(Dimension size) {
    setSize(size);
    setLayout(null);
    setOpaque(false);
    addMouseListener(new MouseAdapter() {});
    try {
      JLabel icon = new JLabel();
      ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images/LoadingLogin.gif"));
      icon.setIcon(imageIcon);
      imageIcon.setImageObserver(icon);
      icon.setBounds(getWidth() / 2 - 55, getHeight() / 2 - 55, 55, 55);
      add(icon);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setColor(new Color(0, 0, 0, 0));
    g2.fillRect(0, 0, getWidth(), getHeight());
    g2.dispose();
  }

  public void close() {
    if (timerTask != null) {
      timerTask.blockingGlassPane.setVisible(false);
      timerTask.cancel();
    }
  }

  public void showBlockingGlass() {
    if (!this.isVisible()) {
      this.timerTask = new TemporizadorTimeOut(this);
      this.timer.schedule(timerTask, 0, this.timeOut * 1000);
    }
  }

  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
  }

  class TemporizadorTimeOut extends TimerTask {

    private BlockingGlassPane blockingGlassPane;

    public TemporizadorTimeOut(BlockingGlassPane glassPane) {
      this.blockingGlassPane = glassPane;
    }

    @Override
    public void run() {
      if (blockingGlassPane != null) {
        if (!blockingGlassPane.isVisible()) {
          blockingGlassPane.setVisible(true);
        } else {
          blockingGlassPane.setVisible(false);
          JOptionPane.showMessageDialog(null, "No answer received from server.", "Timeout", JOptionPane.ERROR_MESSAGE);
          this.cancel();
        }
      }
    }
  }


}
