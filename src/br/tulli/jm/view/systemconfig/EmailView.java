package br.tulli.jm.view.systemconfig;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import br.tulli.jm.dao.UserDAO;
import br.tulli.jm.model.User;
import br.tulli.jm.util.BlockingGlassPane;
import br.tulli.jm.util.Cryptography;
import br.tulli.jm.util.Util;

public class EmailView extends JInternalFrame {
  private JTextField txtEmail;
  private JTextField txtSubject;
  private JTextField txtContent;
  private JLabel lblEmail;
  private JLabel lblSubject;
  private JLabel lblContent;
  private JButton btnCancel;
  private JButton btnSend;
  private UserDAO dao = new UserDAO();
  private BlockingGlassPane glassPane;

  public EmailView(BlockingGlassPane blockingGlassPane) {
    super("Send e-mail");
    this.glassPane = blockingGlassPane;
    createComponents();
  }

  private void createComponents() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    try {
      this.setFrameIcon(new ImageIcon(Util.getImage("images/Email.png")));
    } catch (Exception e) {
      e.printStackTrace();
    }
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] {20, 66, 100, 100, 20};
    gridBagLayout.rowHeights = new int[] {20, 30, 30, 112, 34, 23, 20};
    gridBagLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
    gridBagLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    getContentPane().setLayout(gridBagLayout);

    lblEmail = new JLabel("E-mail");
    GridBagConstraints gbc_lblEmail = new GridBagConstraints();
    gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
    gbc_lblEmail.gridx = 1;
    gbc_lblEmail.gridy = 1;
    getContentPane().add(lblEmail, gbc_lblEmail);

    txtEmail = new JTextField();
    GridBagConstraints gbc_txtEmail = new GridBagConstraints();
    gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
    gbc_txtEmail.gridwidth = 2;
    gbc_txtEmail.gridx = 2;
    gbc_txtEmail.gridy = 1;
    getContentPane().add(txtEmail, gbc_txtEmail);
    txtEmail.setColumns(10);

    lblSubject = new JLabel("Subject");
    GridBagConstraints gbc_lblSubject = new GridBagConstraints();
    gbc_lblSubject.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblSubject.insets = new Insets(0, 0, 5, 5);
    gbc_lblSubject.gridx = 1;
    gbc_lblSubject.gridy = 2;
    getContentPane().add(lblSubject, gbc_lblSubject);

    txtSubject = new JTextField();
    GridBagConstraints gbc_txtSubject = new GridBagConstraints();
    gbc_txtSubject.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtSubject.insets = new Insets(0, 0, 5, 5);
    gbc_txtSubject.gridwidth = 2;
    gbc_txtSubject.gridx = 2;
    gbc_txtSubject.gridy = 2;
    getContentPane().add(txtSubject, gbc_txtSubject);
    txtSubject.setColumns(10);

    lblContent = new JLabel("Content");
    GridBagConstraints gbc_lblContent = new GridBagConstraints();
    gbc_lblContent.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblContent.insets = new Insets(0, 0, 5, 5);
    gbc_lblContent.gridx = 1;
    gbc_lblContent.gridy = 3;
    getContentPane().add(lblContent, gbc_lblContent);

    txtContent = new JTextField();
    GridBagConstraints gbc_txtContent = new GridBagConstraints();
    gbc_txtContent.fill = GridBagConstraints.BOTH;
    gbc_txtContent.insets = new Insets(0, 0, 5, 5);
    gbc_txtContent.gridwidth = 2;
    gbc_txtContent.gridx = 2;
    gbc_txtContent.gridy = 3;
    getContentPane().add(txtContent, gbc_txtContent);
    txtContent.setColumns(10);

    btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    GridBagConstraints gbc_btnCancel = new GridBagConstraints();
    gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
    gbc_btnCancel.gridx = 2;
    gbc_btnCancel.gridy = 5;
    getContentPane().add(btnCancel, gbc_btnCancel);
    btnSend = new JButton("Send");
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (isFieldValid()) {
          blockScreen();
          sendEmail(0.1);
        }
      }
    });
    GridBagConstraints gbc_btnSend = new GridBagConstraints();
    gbc_btnSend.insets = new Insets(0, 0, 0, 5);
    gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnSend.gridx = 3;
    gbc_btnSend.gridy = 5;
    getContentPane().add(btnSend, gbc_btnSend);

    JPanel panel = new JPanel();
    panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    GridBagConstraints gbc_panel = new GridBagConstraints();
    gbc_panel.gridwidth = 5;
    gbc_panel.gridheight = 7;
    gbc_panel.insets = new Insets(0, 0, 5, 5);
    gbc_panel.fill = GridBagConstraints.BOTH;
    gbc_panel.gridx = 0;
    gbc_panel.gridy = 0;
    getContentPane().add(panel, gbc_panel);
  }

  private void blockScreen() {
    Component[] components = getContentPane().getComponents();
    for (Component com : components) {
      com.setEnabled(false);
    }
    glassPane.showBlockingGlass();
  }

  private void unblockScreen() {
    glassPane.close();
    Component[] components = getContentPane().getComponents();
    for (Component com : components) {
      com.setEnabled(true);
    }
  }

  private void sendEmail(double delaySeconds) {
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        Properties properties = configureProperties();
        Session session = getSession(properties);
        try {
          sendMessage(session);
          glassPane.close();
          Util.showInformationMessage("Message sent.");
          doDefaultCloseAction();
        } catch (Exception e) {
          unblockScreen();
          Util.showErrorMessage("Error sending e-mail.\n" + e.getMessage());
          e.printStackTrace();
        }
      }
    };
    Timer timer = new Timer();
    timer.schedule(task, (long) delaySeconds * 1000);
  }

  private boolean isFieldValid() {
    return (!txtEmail.getText().trim().isEmpty() && !txtSubject.getText().trim().isEmpty() && !txtContent.getText().trim().isEmpty());
  }

  private Properties configureProperties() {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.socketFactory.port", "465");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    return properties;
  }

  private Session getSession(Properties properties) {
    Authenticator authenticator = null;
    try {
      User mailUser = dao.findEMailUser();
      authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(Cryptography.decrypt(mailUser.getName()), Cryptography.decrypt(mailUser.getPassword()));
        }
      };
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Session.getInstance(properties, authenticator);
  }

  private void sendMessage(Session session) throws Exception {
    InternetAddress address = new InternetAddress(txtEmail.getText());
    Message message = new MimeMessage(session);
    message.setRecipient(Message.RecipientType.TO, address);
    message.setSubject(txtSubject.getText());
    message.setText(txtContent.getText());
    Transport.send(message);
  }

}
