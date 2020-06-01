package br.tulli.jm.view.login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.CommunicationException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.tulli.jm.dao.ConnectSchool;
import br.tulli.jm.dao.UserDAO;
import br.tulli.jm.model.User;
import br.tulli.jm.util.Cryptography;
import br.tulli.jm.util.Util;
import br.tulli.jm.util.Util.LookAndFeelTypes;
import br.tulli.jm.view.main.MainWindow;

public class LoginWindow extends javax.swing.JDialog {
  private static final long serialVersionUID = 1L;
  private User user = null;
  private JPanel panelData;
  private JPanel panelButtons;
  private JButton jBtnLoginCancel;
  private JButton jBtnLoginOk;
  private JLabel jLblLoading;
  private JLabel jLblUser;
  private JLabel jLblPassword;
  private JTextField jTxtUser;
  private JPasswordField jPswPassword;
  private Connection connection = null;

  public LoginWindow() {
    super();
    initComponents();
    configureComponents();

    // autologin
    jTxtUser.setText("a");
    jPswPassword.setText("a");
    jBtnLoginOk.doClick();
  }

  private void initComponents() {
    panelData = new JPanel();
    panelButtons = new JPanel();
    jBtnLoginOk = new javax.swing.JButton();
    jBtnLoginCancel = new javax.swing.JButton();
    jLblLoading = new JLabel();
    jLblUser = new JLabel();
    jLblPassword = new JLabel();
    jTxtUser = new JTextField();
    jPswPassword = new JPasswordField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("System login");
    setResizable(false);
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));

    panelData.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    panelData.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
    panelData.setPreferredSize(new Dimension(314, 96));

    panelButtons.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
    panelButtons.setPreferredSize(new Dimension(314, 45));

    jLblUser.setText("Username:");
    jLblUser.setFont(new java.awt.Font("Arial", 1, 12));
    jLblUser.setPreferredSize(new Dimension(70, 25));
    jLblPassword.setText("Password:");
    jLblPassword.setFont(new java.awt.Font("Arial", 1, 12));
    jLblPassword.setPreferredSize(new Dimension(70, 25));
    jLblLoading.setPreferredSize(new Dimension(20, 20));
    jTxtUser.setPreferredSize(new Dimension(176, 25));
    jPswPassword.setPreferredSize(new Dimension(176, 25));
    panelData.add(jLblUser);
    panelData.add(jTxtUser);
    panelData.add(jLblLoading);
    panelData.add(jLblPassword);
    panelData.add(jPswPassword);
    getContentPane().add(panelData);

    jBtnLoginOk.setText("Ok");
    jBtnLoginOk.setFont(new java.awt.Font("Arial", 1, 12));
    jBtnLoginOk.setPreferredSize(new Dimension(100, 30));
    jBtnLoginOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBtnLoginOkActionPerformed(evt);
      }
    });
    panelButtons.add(jBtnLoginOk);

    jBtnLoginCancel.setText("Cancel");
    jBtnLoginCancel.setFont(new java.awt.Font("Arial", 1, 12));
    jBtnLoginCancel.setPreferredSize(new Dimension(100, 30));
    jBtnLoginCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBtnLoginCancelActionPerformed(evt);
      }
    });
    panelButtons.add(jBtnLoginCancel);

    getContentPane().add(panelButtons);

    getRootPane().setDefaultButton(jBtnLoginOk);

    pack();
  }

  private void configureComponents() {
    this.setResizable(false);
    this.setSize(324, 173);
    this.setLocationRelativeTo(null);
    Util.defineLookAndFeel(LookAndFeelTypes.METAL);
    try {
      ImageIcon windowIcon = new ImageIcon(Util.getImage("images/Login.png"));
      this.setIconImage(windowIcon.getImage());
      ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images/LoadingLogin.gif"));

      this.jLblLoading.setIcon(imageIcon);
      imageIcon.setImageObserver(jLblLoading);
      this.jLblLoading.setVisible(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.setVisible(true);
  }

  private void jBtnLoginOkActionPerformed(java.awt.event.ActionEvent evt) {

    // String password = new String(jPswPassword.getPassword());
    // String crip = Cryptography.encrypt(password);
    // String decrip = Cryptography.decrypt(crip);
    // System.out.println("senha: [" + password + "]");
    // System.out.println("criptografada: [" + crip + "]");
    // System.out.println("descriptografada: [" + decrip + "]");

    this.jLblLoading.setVisible(true);
    try {
      getDBConnection();
    } catch (CommunicationException e) {
      Util.showErrorMessage(e.getMessage());
      System.exit(ERROR);
    }
    try {
      if (isValidUser()) {
        new MainWindow(user);
        this.setVisible(false);
        this.dispose();
      } else {
        Util.showErrorMessage("Invalid user/password!");
        this.jLblLoading.setVisible(false);
      }
    } catch (SQLException e) {
      Util.showErrorMessage(e.getMessage());
      System.exit(ERROR);
    }
  }

  private void jBtnLoginCancelActionPerformed(java.awt.event.ActionEvent evt) {
    System.exit(0);
  }

  public static void main(String[] args) {
    new LoginWindow();
  }

  private void getDBConnection() throws CommunicationException {
    connection = ConnectSchool.getConnection();
    if (connection == null) {
      throw new CommunicationException("Error when connecting to database.");
    }
  }

  private boolean isValidUser() throws SQLException {
    UserDAO dao = new UserDAO();
    try {
      user = dao.findUserByName(jTxtUser.getText());
      if (user != null && user.getPassword() != null && !user.getPassword().isEmpty()) {
        return user.getPassword().equals(Cryptography.encrypt(new String(jPswPassword.getPassword())));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("Error finding user in the database.");
    }
    return false;
  }

}
