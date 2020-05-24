package br.tulli.jm.view.systemconfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

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
import javax.swing.JTextField;

import br.tulli.jm.util.Cryptography;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EmailView extends JInternalFrame {
  private JTextField txtEmail;
  private JTextField txtSubject;
  private JTextField txtContent;
  private JLabel jLblLoading;
  private JButton btnCancel;
  private JButton btnSend;
  private String userMail = "f25c21adea4a729649b9432fa3bcf1e801c373837740f52ba92501821b710af4";
  private String password = "52ec1b80b98e2c29502f3bcdce6393b3";

  public EmailView() {
    super("Send e-mail");
    initComponents();
  }

  private void initComponents() {
    setBounds(100, 100, 800, 553);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] {66, 100, 100, 89};
    gridBagLayout.rowHeights = new int[] {30, 30, 112, 34, 23};
    gridBagLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0};
    gridBagLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
    getContentPane().setLayout(gridBagLayout);

    JLabel lblEmail = new JLabel("E-mail");
    GridBagConstraints gbc_lblEmail = new GridBagConstraints();
    gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
    gbc_lblEmail.gridx = 0;
    gbc_lblEmail.gridy = 0;
    getContentPane().add(lblEmail, gbc_lblEmail);

    txtEmail = new JTextField();
    GridBagConstraints gbc_txtEmail = new GridBagConstraints();
    gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
    gbc_txtEmail.gridwidth = 2;
    gbc_txtEmail.gridx = 1;
    gbc_txtEmail.gridy = 0;
    getContentPane().add(txtEmail, gbc_txtEmail);
    txtEmail.setColumns(10);

    JLabel lblSubject = new JLabel("Subject");
    GridBagConstraints gbc_lblSubject = new GridBagConstraints();
    gbc_lblSubject.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblSubject.insets = new Insets(0, 0, 5, 5);
    gbc_lblSubject.gridx = 0;
    gbc_lblSubject.gridy = 1;
    getContentPane().add(lblSubject, gbc_lblSubject);

    txtSubject = new JTextField();
    GridBagConstraints gbc_txtSubject = new GridBagConstraints();
    gbc_txtSubject.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtSubject.insets = new Insets(0, 0, 5, 5);
    gbc_txtSubject.gridwidth = 2;
    gbc_txtSubject.gridx = 1;
    gbc_txtSubject.gridy = 1;
    getContentPane().add(txtSubject, gbc_txtSubject);
    txtSubject.setColumns(10);

    JLabel lblContent = new JLabel("Content");
    GridBagConstraints gbc_lblContent = new GridBagConstraints();
    gbc_lblContent.fill = GridBagConstraints.HORIZONTAL;
    gbc_lblContent.insets = new Insets(0, 0, 5, 5);
    gbc_lblContent.gridx = 0;
    gbc_lblContent.gridy = 2;
    getContentPane().add(lblContent, gbc_lblContent);

    txtContent = new JTextField();
    GridBagConstraints gbc_txtContent = new GridBagConstraints();
    gbc_txtContent.fill = GridBagConstraints.BOTH;
    gbc_txtContent.insets = new Insets(0, 0, 5, 5);
    gbc_txtContent.gridwidth = 2;
    gbc_txtContent.gridx = 1;
    gbc_txtContent.gridy = 2;
    getContentPane().add(txtContent, gbc_txtContent);
    txtContent.setColumns(10);

    btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images/LoadingLogin.gif"));
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    jLblLoading = new JLabel();
    GridBagConstraints gbc_jLblLoading = new GridBagConstraints();
    gbc_jLblLoading.insets = new Insets(0, 0, 0, 5);
    gbc_jLblLoading.gridheight = 2;
    gbc_jLblLoading.gridx = 2;
    gbc_jLblLoading.gridy = 3;
    getContentPane().add(jLblLoading, gbc_jLblLoading);
    jLblLoading.setIcon(imageIcon);
    imageIcon.setImageObserver(jLblLoading);
    jLblLoading.setVisible(false);
    GridBagConstraints gbc_btnCancel = new GridBagConstraints();
    gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
    gbc_btnCancel.gridx = 1;
    gbc_btnCancel.gridy = 4;
    getContentPane().add(btnCancel, gbc_btnCancel);

    btnSend = new JButton("Send");
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sendEmail();
      }
    });
    GridBagConstraints gbc_btnSend = new GridBagConstraints();
    gbc_btnSend.insets = new Insets(0, 0, 0, 5);
    gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnSend.gridx = 2;
    gbc_btnSend.gridy = 4;
    getContentPane().add(btnSend, gbc_btnSend);
  }

  private void sendEmail() {
    jLblLoading.setVisible(true);
    btnCancel.setEnabled(false);
    btnSend.setEnabled(false);
    if (isFieldValid()) {
      Properties properties = configureProperties();
      Session session = getSession(properties);
      sendMessage(session);
      jLblLoading.setVisible(false);
      btnCancel.setEnabled(true);
      btnSend.setEnabled(true);
      this.doDefaultCloseAction();
    }
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
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(Cryptography.decrypt(userMail), Cryptography.decrypt(password));
      }
    };
    return Session.getInstance(properties, authenticator);
  }

  private void sendMessage(Session session) {
    try {
      InternetAddress address = new InternetAddress(txtEmail.getText());
      Message message = new MimeMessage(session);
      message.setRecipient(Message.RecipientType.TO, address);
      message.setSubject(txtSubject.getText());
      message.setText(txtContent.getText());
      Transport.send(message);
    } catch (Exception e) {
      jLblLoading.setVisible(false);
      btnCancel.setEnabled(true);
      btnSend.setEnabled(true);
      e.printStackTrace();
    }
  }

}
