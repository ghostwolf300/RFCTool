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
	
	public static final String SCREEN_FUNCTION_SELECTION="screen_1";
	public static final String SCREEN_DATA_SELECTION="screen_2";
	public static final String SCREEN_RUN_SETTINGS="screen_3";
	public static final String SCREEN_RUN_MONITOR="screen_4";
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataSelectionPanel dataSelectionPanel;
	private DefaultController controller=null;
	private RunSettingsPanel runSettingsPanel;
	private RunMonitorPanel runMonitorPanel;
	private FunctionSelectionPanel functionSelectionPanel;

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
		contentPane.add(getFunctionSelectionPanel(),SCREEN_FUNCTION_SELECTION);
		contentPane.add(getDataSelectionPanel(),SCREEN_DATA_SELECTION);
		contentPane.add(getRunSettingsPanel(),SCREEN_RUN_SETTINGS);
		contentPane.add(getRunPanel(), SCREEN_RUN_MONITOR);
	}

	private DataSelectionPanel getDataSelectionPanel() {
		if (dataSelectionPanel == null) {
			dataSelectionPanel = new DataSelectionPanel(controller,this);
		}
		return dataSelectionPanel;
	}
	private RunSettingsPanel getRunSettingsPanel() {
		if (runSettingsPanel == null) {
			runSettingsPanel = new RunSettingsPanel(controller,this);
		}
		return runSettingsPanel;
	}
	private RunMonitorPanel getRunPanel() {
		if (runMonitorPanel == null) {
			runMonitorPanel = new RunMonitorPanel(controller,this);
		}
		return runMonitorPanel;
	}
	private FunctionSelectionPanel getFunctionSelectionPanel() {
		if (functionSelectionPanel == null) {
			functionSelectionPanel = new FunctionSelectionPanel(controller,this);
		}
		return functionSelectionPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(functionSelectionPanel.getBtnNext())) {
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_DATA_SELECTION);
		}
		else if(e.getSource().equals(dataSelectionPanel.getBtnNext())) {
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_RUN_SETTINGS);
		}
		else if(e.getSource().equals(runSettingsPanel.getBtnBack())){
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_DATA_SELECTION);
		}
		else if(e.getSource().equals(runSettingsPanel.getBtnNext())) {
			CardLayout cards=(CardLayout) contentPane.getLayout();
			cards.show(contentPane,SCREEN_RUN_MONITOR);
		}
		
	}
	
}
