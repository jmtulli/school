package br.tulli.jm.view.systemconfig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import br.tulli.jm.dao.GroupDAO;
import br.tulli.jm.dao.UserDAO;
import br.tulli.jm.model.Group;
import br.tulli.jm.model.UserGroupTo;
import br.tulli.jm.util.Cryptography;
import br.tulli.jm.util.Util;

public class EditUserAndGroupView extends JDialog {
  private int userId;
  private UserGroupView parent;
  private String operation;
  private JComboBox<Group> cmbGroups;
  private JTextField txtUserName;
  private JPasswordField pswField;
  private JPasswordField pswFieldRepeat;

  public EditUserAndGroupView(Frame frame, Boolean modal, String operation, UserGroupTo data, UserGroupView parent) {
    super(frame, modal);
    this.parent = parent;
    this.operation = operation;
    createComponents();
    clean();
    fillGroups();
    if (data != null) {
      fillUserData(data);
    }
    this.setLocationRelativeTo(parent);

  }

  private void createComponents() {
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 600, 300);
    setSize(300, 325);
    setResizable(false);

    JPanel panelAction = new JPanel();
    panelAction.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    getContentPane().add(panelAction, BorderLayout.SOUTH);
    panelAction.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

    JButton btnSave = new JButton("Save");
    btnSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveUser();
      }
    });
    panelAction.add(btnSave);

    JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    panelAction.add(btnCancel);

    JPanel panelData = new JPanel();
    panelData.setBorder(new BevelBorder(BevelBorder.RAISED));
    getContentPane().add(panelData, BorderLayout.CENTER);
    panelData.setLayout(null);

    JLabel lblUserName = new JLabel("User name");
    lblUserName.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblUserName.setBounds(2, 6, 127, 48);
    lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
    panelData.add(lblUserName);

    JLabel lblUserGroup = new JLabel("User Group");
    lblUserGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblUserGroup.setBounds(2, 179, 127, 48);
    lblUserGroup.setHorizontalAlignment(SwingConstants.RIGHT);
    panelData.add(lblUserGroup);

    cmbGroups = new JComboBox<>();
    cmbGroups.setBounds(145, 190, 120, 28);
    panelData.add(cmbGroups);

    JLabel lblPassword = new JLabel("Password");
    lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPassword.setBounds(2, 65, 127, 48);
    panelData.add(lblPassword);

    JLabel lblRepeatPassword = new JLabel("Repeat password");
    lblRepeatPassword.setHorizontalAlignment(SwingConstants.RIGHT);
    lblRepeatPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
    lblRepeatPassword.setBounds(2, 124, 127, 48);
    panelData.add(lblRepeatPassword);

    txtUserName = new JTextField();
    txtUserName.setBackground(new Color(0, 255, 255));
    txtUserName.setBounds(145, 17, 120, 28);
    panelData.add(txtUserName);
    txtUserName.setColumns(10);
    txtUserName.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        e.getComponent().setBackground(Color.GREEN);
        txtUserName.selectAll();
      }

      @Override
      public void focusLost(FocusEvent e) {
        e.getComponent().setBackground(new Color(0, 255, 255));
      }
    });

    pswField = new JPasswordField();
    pswField.setBackground(new Color(0, 255, 255));
    pswField.setBounds(145, 76, 120, 28);
    panelData.add(pswField);
    pswField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        e.getComponent().setBackground(Color.GREEN);
        pswField.selectAll();
      }

      @Override
      public void focusLost(FocusEvent e) {
        e.getComponent().setBackground(new Color(0, 255, 255));
      }
    });

    pswFieldRepeat = new JPasswordField();
    pswFieldRepeat.setBackground(Color.CYAN);
    pswFieldRepeat.setBounds(145, 135, 120, 28);
    panelData.add(pswFieldRepeat);
    pswFieldRepeat.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        e.getComponent().setBackground(Color.GREEN);
        pswFieldRepeat.selectAll();
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (pswField.getPassword() != null && pswFieldRepeat.getPassword() != null && Arrays.equals(pswField.getPassword(), pswFieldRepeat.getPassword())) {
          e.getComponent().setBackground(new Color(0, 255, 255));
        } else {
          e.getComponent().setBackground(Color.RED);
        }
      }
    });
  }

  private void clean() {
    cmbGroups.removeAll();
    txtUserName.setText(null);
    pswField.setText(null);
    pswFieldRepeat.setText(null);
  }

  private void fillGroups() {
    try {
      for (Group g : new GroupDAO().findAllGroups()) {
        cmbGroups.addItem(g);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void fillUserData(UserGroupTo data) {
    txtUserName.setText(data.getUserName());
    pswField.setText(Cryptography.decrypt(data.getPassword()));
    pswFieldRepeat.setText(Cryptography.decrypt(data.getPassword()));
    cmbGroups.getModel().setSelectedItem(data.getGroup());
    userId = data.getUserId();
  }

  private void saveUser() {
    if (isValidUser()) {
      UserDAO dao = new UserDAO();
      if ("NEW".equals(operation)) {
        try {
          dao.insertNewUser(txtUserName.getText(), Cryptography.encrypt(new String(pswField.getPassword())), ((Group) cmbGroups.getSelectedItem()).getGroupId());
          Util.showInformationMessage("User added!");
          dispose();
          parent.refreshScreen();
        } catch (SQLException e) {
          Util.showErrorMessage("Error while creating new user");
        }
      } else if ("EDIT".equals(operation)) {
        try {
          dao.updateUser(txtUserName.getText(), Cryptography.encrypt(new String(pswField.getPassword())), ((Group) cmbGroups.getSelectedItem()).getGroupId(), userId);
          Util.showInformationMessage("User updated!");
          this.dispose();
          parent.refreshScreen();
        } catch (SQLException e) {
          Util.showErrorMessage("Error while updating the user");
        }
      }
    } else {
      Util.showErrorMessage("Invalid data. Please correct.");
    }
  }

  private boolean isValidUser() {
    return txtUserName.getText() != null && pswField.getPassword() != null && pswFieldRepeat.getPassword() != null && Arrays.equals(pswField.getPassword(), pswFieldRepeat.getPassword());
  }

}
