package br.tulli.jm.view.systemconfig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

import br.tulli.jm.dao.UserDAO;
import br.tulli.jm.dao.UserGroupDAO;
import br.tulli.jm.model.User;
import br.tulli.jm.model.UserGroupTo;
import br.tulli.jm.util.Util;

public class UserGroupView extends JInternalFrame {
  private User user;
  private UserGroupTableModel tableModel;
  private JTable table = new JTable();
  private JScrollPane scrTable;

  public UserGroupView(User user) {
    super("User and Groups configuration");
    this.user = user;
    initComponents();
    refreshScreen();
  }

  private void initComponents() {
    Boolean canEdit = checkPermission(user);
    JPanel panelData = new JPanel(new BorderLayout());
    panelData.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    this.getContentPane().add(panelData, BorderLayout.CENTER);
    JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panelAction.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    this.getContentPane().add(panelAction, BorderLayout.SOUTH);
    try {
      this.setFrameIcon(new ImageIcon(Util.getImage("images/Edit.png")));
    } catch (Exception e) {
      e.printStackTrace();
    }
    JButton btnNewUser = new JButton("New User");
    btnNewUser.setEnabled(canEdit);
    btnNewUser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openNewUserView();
      }
    });
    panelAction.add(btnNewUser);
    JButton btnEdit = new JButton("Edit");
    btnEdit.setEnabled(canEdit);
    btnEdit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openEditView();
      }
    });
    panelAction.add(btnEdit);
    JButton btnDelete = new JButton("Delete");
    btnDelete.setEnabled(canEdit);
    btnDelete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        deleteUser();
      }
    });
    panelAction.add(btnDelete);
    JButton btnClose = new JButton("Close");
    btnClose.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doDefaultCloseAction();
        dispose();
      }
    });
    panelAction.add(btnClose);
    scrTable = new JScrollPane();
    scrTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrTable.setViewportView(table);
    panelData.add(scrTable);
  }

  private boolean checkPermission(User user) {
    UserGroupDAO dao = new UserGroupDAO();
    return dao.isUserAdmin(user.getUserId());
  }

  public void refreshScreen() {
    setUpTable(getUserGroup());
  }

  private List<UserGroupTo> getUserGroup() {
    return new UserGroupDAO().findAllUserGroup();
  }

  public void setUpTable(List<UserGroupTo> tableData) {
    tableModel = new UserGroupTableModel(tableData);
    table = new JTable(tableModel);
    table.setRowHeight(22);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ((DefaultTableCellRenderer) (table.getTableHeader().getDefaultRenderer())).setHorizontalAlignment(SwingConstants.CENTER);
    tableModel.fireTableDataChanged();
    scrTable.setViewportView(table);
  }

  public void openEditView() {
    if (table.getSelectedRow() >= 0) {
      List<UserGroupTo> users = ((UserGroupTableModel) table.getModel()).getUsers();
      (new EditUserAndGroupView(null, true, "EDIT", users.get(table.getSelectedRow()), this)).setVisible(true);
    } else {
      Util.showInformationMessage("Select user to edit.");
    }
  }

  public void openNewUserView() {
    new EditUserAndGroupView(null, true, "NEW", null, this).setVisible(true);
  }

  public void deleteUser() {
    if (table.getSelectedRow() >= 0) {
      if (Util.showMessageDialog("Do you really want to delete the selected user?", "Confirm delete user") == 0) {
        List<UserGroupTo> users = ((UserGroupTableModel) table.getModel()).getUsers();
        Integer userId = users.get(table.getSelectedRow()).getUserId();
        UserDAO dao = new UserDAO();
        if (!dao.deleteUser(userId)) {
          Util.showErrorMessage("Error while deleting the user");
        }
      }
      refreshScreen();
    } else {
      Util.showInformationMessage("Select user to delete.");
    }
  }

}
