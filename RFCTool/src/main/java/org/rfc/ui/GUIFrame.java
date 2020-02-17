package org.rfc.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.rfc.controller.DefaultController;
import java.awt.CardLayout;

public class GUIFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	public static final String SCREEN_LOAD_PLANT_DATA="screen_1";
	public static final String SCREEN_RUN_SETTINGS="screen_2";
	public static final String SCREEN_EXEC_MONITOR="screen_3";
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataSelectionPanel dataSelectionPanel;
	private DefaultController controller=null;
	private RunSettingsPanel runSettingsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIFrame frame = new GUIFrame(new DefaultController());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIFrame(DefaultController controller) {
		this.controller=controller;
		initialize();
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SAP RFC Tool 1.0");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		contentPane.add(getDataSelectionPanel(),SCREEN_LOAD_PLANT_DATA);
		contentPane.add(getRunSettingsPanel(),SCREEN_RUN_SETTINGS);
	}

	private DataSelectionPanel getDataSelectionPanel() {
		if (dataSelectionPanel == null) {
			dataSelectionPanel = new DataSelectionPanel(this.controller,this);
		}
		return dataSelectionPanel;
	}
	private RunSettingsPanel getRunSettingsPanel() {
		if (runSettingsPanel == null) {
			runSettingsPanel = new RunSettingsPanel(this.controller,this);
		}
		return runSettingsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(dataSelectionPanel.getBtnNext())) {
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_RUN_SETTINGS);
		}
		else if(e.getSource().equals(runSettingsPanel.getBtnBack())){
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_LOAD_PLANT_DATA);
		}
		else if(e.getSource().equals(runSettingsPanel.getBtnStart())) {
			
		}
		
	}
}
