package br.tulli.jm.view.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import br.tulli.jm.dao.UserDAO;
import br.tulli.jm.model.User;
import br.tulli.jm.util.DateTimeUtil;
import br.tulli.jm.util.Util;
import br.tulli.jm.util.Util.LookAndFeelTypes;
import br.tulli.jm.view.systemconfig.UserGroupView;

public class MainWindow extends JFrame {

  private User user;
  private Timer timer = new Timer();
  private TimerTask timerTask;

  public MainWindow(User user) {
    super("School Management - User " + user.getName());
    this.user = user;
    initComponents();
    configureComponents();
    configureWindow();
  }

  private void initComponents() {
    Container contentPane = this.getContentPane();
    jPanelAlarmPanel = new javax.swing.JPanel();
    jLabelDateTime = new javax.swing.JLabel();
    jPanelMainPanel = new javax.swing.JDesktopPane();
    jMenuBarMainMenu = new javax.swing.JMenuBar();

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    jPanelMainPanel.setBackground(new java.awt.Color(240, 240, 240));
    jPanelMainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));
    jPanelMainPanel.setLayout(new BorderLayout());

    jPanelAlarmPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
    jPanelAlarmPanel.setLayout(new BorderLayout());

    jLabelDateTime.setText("System Time");
    jLabelDateTime.setSize(10, 19);
    jPanelAlarmPanel.add(jLabelDateTime, BorderLayout.EAST);

    setJMenuBar(jMenuBarMainMenu);
    jPanelMainPanel.add(jPanelAlarmPanel, BorderLayout.SOUTH);
    contentPane.add(jPanelMainPanel);

    pack();

    timerTask = new TimerTask() {
      @Override
      public void run() {
        updateTime();
      }
    };
    timer.schedule(timerTask, DateTimeUtil.getCurrentTime(), 10 * 1000);
  }

  public void configureComponents() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setBounds(100, 100, Math.round(screenSize.width * 0.8F), Math.round(screenSize.height * 0.8F));
    this.setLocationRelativeTo(null);
    configureMenuBar();
    Util.defineLookAndFeel(LookAndFeelTypes.NIMBUS);
    this.setVisible(true);
  }

  public void configureWindow() {
    Util.defineLookAndFeel(LookAndFeelTypes.NIMBUS);
    setWindowIcon("images/MiniLogo.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setBounds(100, 100, Math.round(screenSize.width * 0.8F), Math.round(screenSize.height * 0.8F));
    this.setLocationRelativeTo(null);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        confirmExit();
      }
    });
  }

  public void setWindowIcon(String iconPath) {
    ImageIcon windowIcon;
    try {
      windowIcon = new ImageIcon(Util.getImage(iconPath));
      this.setIconImage(windowIcon.getImage());
    } catch (IOException e) {
      // FIXME add log
      e.printStackTrace();
    }

  }

  private void jMnItmExitActionPerformed(java.awt.event.ActionEvent evt) {
    confirmExit();
  }

  private void confirmExit() {
    if (Util.showMessageDialog("Do you really want to quit the system?", "Confirm system exit") == 0) {
      new UserDAO().closeConnection();
      closeWindow();
    }
  }

  private void configureMenuBar() {
    JMenu register = new JMenu("Register");
    addMenuItem(register, "1 - Department", true);
    addMenuItem(register, "2 - Teacher", false);
    addMenuItem(register, "3 - Discipline", false);
    addMenuItem(register, "4 - Course", false);
    addMenuItem(register, "5 - Student", false);
    addMenu(register, 0);
    JMenu access = new JMenu("Access Control");
    addMenuItem(access, "Change Password", true);
    addMenu(access, 1);
    JMenu system = new JMenu("System");
    addMenuItem(system, "System Parameters", false);
    addMenuItem(system, "Users and Profiles", true, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jMnItmUserProfilesActionPerformed(e);
      }
    });
    addMenuItem(system, "Exit", true, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jMnItmExitActionPerformed(e);
      }
    });
    addMenu(system, 2);
  }

  private void updateTime() {
    jLabelDateTime.setText(DateTimeUtil.dateTimeFormat() + " ");
  }

  private void jMnItmUserProfilesActionPerformed(ActionEvent e) {
    UserGroupView window = new UserGroupView(user);
    window.setClosable(true);
    window.setIconifiable(true);
    window.setMaximizable(true);
    jPanelMainPanel.add(window);
    window.setVisible(true);
  }

  public void closeWindow() {
    timer.cancel();
    timerTask.cancel();
    System.exit(0);
  }

  public void addMenu(JMenu menu) {
    addMenu(menu, -1);
  }

  public void addMenu(JMenu menu, int position) {
    jMenuBarMainMenu.add(menu, position);
  }

  public void addMenuItem(JMenu menu, String titleItemMenu, boolean enabled) {
    JMenuItem menuItem = new JMenuItem();
    menuItem.setEnabled(enabled);
    menuItem.setVisible(true);
    menuItem.setText(titleItemMenu);
    menuItem.setFont(new java.awt.Font("Arial", 0, 12));
    menu.add(menuItem);
  }

  public void addMenuItem(JMenu menu, String titleItemMenu, boolean enabled, ActionListener listener) {
    JMenuItem menuItem = new JMenuItem();
    menuItem.setEnabled(enabled);
    menuItem.setVisible(true);
    menuItem.setText(titleItemMenu);
    menuItem.addActionListener(listener);
    menuItem.setFont(new java.awt.Font("Arial", 0, 12));
    menu.add(menuItem);
  }

  // Variables declaration
  private javax.swing.JDesktopPane jPanelMainPanel;
  private javax.swing.JLabel jLabelDateTime;
  private javax.swing.JMenuBar jMenuBarMainMenu;
  private javax.swing.JPanel jPanelAlarmPanel;
}
