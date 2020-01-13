package br.tulli.jm.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JMenu;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import br.com.jmtulli.util.Util;
import br.com.jmtulli.view.BaseMainWindow;

public class MainWindow extends BaseMainWindow {
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		super("School Management - Main Window", "images/MiniLogo.png");
//		initComponents();
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanelButton = new javax.swing.JPanel();
		jBtnExit = new javax.swing.JButton();
		jPanelWindow = new javax.swing.JPanel();
		jMenuBar = new javax.swing.JMenuBar();
		jMenuRegister = new javax.swing.JMenu();
		jMnItmDepartment = new javax.swing.JMenuItem();
		jMnItmTeacher = new javax.swing.JMenuItem();
		jMnItmDiscipline = new javax.swing.JMenuItem();
		jMnItmCourse = new javax.swing.JMenuItem();
		jMnItmStudent = new javax.swing.JMenuItem();
		jMenuSystem = new javax.swing.JMenu();
		jMnItmUsers = new javax.swing.JMenuItem();
		jMnItmParameters = new javax.swing.JMenuItem();
		jMenuExit = new javax.swing.JMenu();
		jMnItmExit = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("School Management System");
		setName("frmMainWindow"); // NOI18N
		setResizable(false);

		jBtnExit.setText("Exit");
		jBtnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBtnExitActionPerformed(evt);
			}
		});
		jPanelButton.add(jBtnExit);

		getContentPane().add(jPanelButton, java.awt.BorderLayout.PAGE_END);

		javax.swing.GroupLayout jPanelWindowLayout = new javax.swing.GroupLayout(jPanelWindow);
		jPanelWindow.setLayout(jPanelWindowLayout);
		jPanelWindowLayout.setHorizontalGroup(jPanelWindowLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 450, Short.MAX_VALUE));
		jPanelWindowLayout.setVerticalGroup(jPanelWindowLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE));

		getContentPane().add(jPanelWindow, java.awt.BorderLayout.CENTER);

		jMenuRegister.setText("Register");

		jMnItmDepartment.setText("1 - Department");
		jMnItmDepartment.setEnabled(false);
		jMenuRegister.add(jMnItmDepartment);

		jMnItmTeacher.setText("2 - Teacher");
		jMnItmTeacher.setEnabled(false);
		jMenuRegister.add(jMnItmTeacher);

		jMnItmDiscipline.setText("3 - Discipline");
		jMnItmDiscipline.setEnabled(false);
		jMenuRegister.add(jMnItmDiscipline);

		jMnItmCourse.setText("4 - Course");
		jMnItmCourse.setEnabled(false);
		jMenuRegister.add(jMnItmCourse);

		jMnItmStudent.setText("5 - Student");
		jMnItmStudent.setEnabled(false);
		jMenuRegister.add(jMnItmStudent);

		jMenuBar.add(jMenuRegister);

		jMenuSystem.setText("System");

		jMnItmUsers.setText("Users");
		jMnItmUsers.setEnabled(false);
		jMenuSystem.add(jMnItmUsers);

		jMnItmParameters.setText("Parameters");
		jMnItmParameters.setEnabled(false);
		jMenuSystem.add(jMnItmParameters);

		jMenuBar.add(jMenuSystem);

		jMenuExit.setText("Exit");

		jMnItmExit.setText("Exit System");
		jMnItmExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMnItmExitActionPerformed(evt);
			}
		});
		jMenuExit.add(jMnItmExit);

		jMenuBar.add(jMenuExit);

		setJMenuBar(jMenuBar);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jBtnExitActionPerformed(java.awt.event.ActionEvent evt) {
		confirmExit();
	}

	private void jMnItmExitActionPerformed(java.awt.event.ActionEvent evt) {
		confirmExit();
	}

	private void confirmExit() {
		if (Util.showMessageDialog("Do you really want to quit the system?", "Confirm system exit") == 0) {
			doCloseWindow();
		}
	}

	private void configureMenuBar() {
		JMenu register = new JMenu();
		register.setText("Register");
//		JMenu manage = new JMenu();
//		manage.setText("Manage");
//		JMenu system = new JMenu();
//		system.setText("System");
		addMenuItem(register, "1");
//		addMenuItem(manage, "2");
//		addMenuItem(system, "3");
		addMenu(register, 0);
//		addMenu(manage, 1);
//		addMenu(system, 2);
	}
	
	@Override
	public void configureComponents() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100, 100, Math.round(screenSize.width * 0.8F), Math.round(screenSize.height * 0.8F));
		this.setLocationRelativeTo(null);
		configureMenuBar();
//        jPanelWindow.setBounds(this.getBounds());
//        jMnItmDepartment.setEnabled(true);
		this.setVisible(true);
	}

	@Override
	public void closeWindow() {
		System.exit(0);
	}

	// Variables declaration - do not modify
	private javax.swing.JButton jBtnExit;
	private javax.swing.JMenuBar jMenuBar;
	private javax.swing.JMenu jMenuExit;
	private javax.swing.JMenu jMenuRegister;
	private javax.swing.JMenu jMenuSystem;
	private javax.swing.JMenuItem jMnItmCourse;
	private javax.swing.JMenuItem jMnItmDepartment;
	private javax.swing.JMenuItem jMnItmDiscipline;
	private javax.swing.JMenuItem jMnItmExit;
	private javax.swing.JMenuItem jMnItmParameters;
	private javax.swing.JMenuItem jMnItmStudent;
	private javax.swing.JMenuItem jMnItmTeacher;
	private javax.swing.JMenuItem jMnItmUsers;
	private javax.swing.JPanel jPanelButton;
	private javax.swing.JPanel jPanelWindow;
	// End of variables declaration

}
