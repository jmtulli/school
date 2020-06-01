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
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import br.tulli.jm.dao.ConnectSchool;
import br.tulli.jm.model.User;
import br.tulli.jm.util.BlockingGlassPane;
import br.tulli.jm.util.DateTimeUtil;
import br.tulli.jm.util.Util;
import br.tulli.jm.util.Util.LookAndFeelTypes;
import br.tulli.jm.view.systemconfig.EmailView;
import br.tulli.jm.view.systemconfig.UserGroupView;

public class MainWindow extends JFrame {
  private User user;
  private Timer timer = new Timer();
  private TimerTask timerTask;
  private JDesktopPane jPanelMainPanel;
  private JLabel jLabelDateTime;
  private JMenuBar jMenuBarMainMenu;
  private JPanel jPanelAlarmPanel;
  private BlockingGlassPane glass;
  private JInternalFrame openedWindow = null;

  public MainWindow(User user) {
    super("School Management - User " + user.getName());
    this.user = user;
    createComponents();
    configureComponents();
    configureWindow();
  }

  private void createComponents() {
    setResizable(false);
    Container contentPane = this.getContentPane();
    jPanelAlarmPanel = new javax.swing.JPanel();
    jLabelDateTime = new javax.swing.JLabel();
    jPanelMainPanel = new javax.swing.JDesktopPane();
    jMenuBarMainMenu = new javax.swing.JMenuBar();

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    setJMenuBar(jMenuBarMainMenu);

    jPanelMainPanel.setBackground(new java.awt.Color(240, 240, 240));
    jPanelMainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));
    jPanelMainPanel.setLayout(new BorderLayout());

    jPanelAlarmPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
    jPanelAlarmPanel.setLayout(new BorderLayout());
    jLabelDateTime.setText("System Time");
    jLabelDateTime.setSize(10, 19);
    jPanelAlarmPanel.add(jLabelDateTime, BorderLayout.EAST);
    jPanelMainPanel.add(jPanelAlarmPanel, BorderLayout.SOUTH);

    contentPane.add(jPanelMainPanel);

    timerTask = new TimerTask() {
      @Override
      public void run() {
        updateTime();
      }
    };
    timer.schedule(timerTask, DateTimeUtil.getCurrentTime(), 10 * 1000);
  }

  private void configureComponents() {
    this.setLocationRelativeTo(null);
    configureMenuBar();
    this.setVisible(true);
  }

  private void configureWindow() {
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
    glass = new BlockingGlassPane(getSize());
    setGlassPane(glass);
  }

  public void setWindowIcon(String iconPath) {
    ImageIcon windowIcon;
    try {
      windowIcon = new ImageIcon(Util.getImage(iconPath));
      this.setIconImage(windowIcon.getImage());
    } catch (IOException e) {
      // FIXME add log
      System.err.println("Error when setting window icon. Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void jMnItmSendEmailActionPerformed(ActionEvent e) {
    EmailView window = new EmailView(this.getBlockingGlass());
    openWindow(window);
  }

  private void jMnItmUserProfilesActionPerformed(ActionEvent e) {
    UserGroupView window = new UserGroupView(user);
    openWindow(window);
  }

  private void jMnItmExitActionPerformed(ActionEvent evt) {
    confirmExit();
  }

  private void openWindow(JInternalFrame window) {
    window.setClosable(false);
    window.setIconifiable(false);
    window.setMaximizable(false);
    jPanelMainPanel.add(window);
    window.setVisible(true);
    if (openedWindow != null) {
      openedWindow.dispose();
    }
    openedWindow = window;
  }

  private void updateTime() {
    jLabelDateTime.setText(DateTimeUtil.dateTimeFormat() + " ");
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
    addMenuItem(system, "Send e-mail", true, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jMnItmSendEmailActionPerformed(e);
      }
    });
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

  public void closeWindow() {
    timer.cancel();
    timerTask.cancel();
    ConnectSchool.closeConnection();
    System.exit(0);
  }

  private void confirmExit() {
    if (Util.showMessageDialog("Do you really want to quit the system?", "Confirm system exit") == 0) {
      ConnectSchool.closeConnection();
      closeWindow();
    }
  }

  public BlockingGlassPane getBlockingGlass() {
    return glass;
  }
}
