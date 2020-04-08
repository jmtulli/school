package br.tulli.jm.view.systemconfig;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.tulli.jm.model.UserGroupTo;

public class UserGroupTableModel extends AbstractTableModel {

  public static final int COLUMN_USER_NAME = 0;
  public static final int COLUMN_GROUP_NAME = 1;
  public static final int TOTAL_COLUMNS = 2;

  private List<UserGroupTo> users;
  private String[] columns = {"User name", "Group"};

  public UserGroupTableModel(List<UserGroupTo> users) {
    this.users = users;
  }

  @Override
  public int getRowCount() {
    return getUsers().size();
  }

  @Override
  public int getColumnCount() {
    return TOTAL_COLUMNS;
  }

  @Override
  public String getColumnName(int column) {
    return columns[column];
  }

  @Override
  public Class getColumnClass(int columnIndex) {
    return Object.class;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    UserGroupTo user = users.get(rowIndex);
    switch (columnIndex) {
      case COLUMN_USER_NAME:
        return user.getUserName();
      case COLUMN_GROUP_NAME:
        return user.getGroup().getName();
    }
    return null;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  public List<UserGroupTo> getUsers() {
    return users;
  }

}
