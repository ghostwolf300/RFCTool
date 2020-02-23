package org.rfc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JPanel;

import org.rfc.controller.DefaultController;
import org.rfc.dto.UserFunction;
import org.rfc.model.UserFunctionModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FunctionSelectionPanel extends JPanel implements IView,ActionListener,ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnNext;
	private JScrollPane scrollPane;
	private JList<UserFunction> list;
	private DefaultController controller=null;
	private ActionListener cardManager=null;
	
	/**
	 * Create the panel.
	 */
	public FunctionSelectionPanel(DefaultController controller,ActionListener cardManager) {
		this.cardManager=cardManager;
		this.controller=controller;
		this.controller.addView(this);
		initialize();
		this.controller.loadFunctions();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		add(getScrollPane(), "cell 0 0,grow");
		add(getBtnNext(), "cell 0 1");
	}

	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.setEnabled(false);
			btnNext.addActionListener(cardManager);
		}
		return btnNext;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<UserFunction> getList() {
		if (list == null) {
			list = new JList<UserFunction>(new FunctionListModel());
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.addListSelectionListener(this);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(UserFunctionModel.Property.FUNCTIONS.toString())) {
			List<UserFunction> functions=(List<UserFunction>) pce.getNewValue();
			FunctionListModel m=(FunctionListModel) list.getModel();
			System.out.println("Adding functions: "+m.getSize());
			m.setFunctions(functions);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false) {
			if(list.getSelectedIndex()==-1) {
				btnNext.setEnabled(false);
			}
			else {
				UserFunction userFunction=list.getSelectedValue();
				controller.selectUserFunction(userFunction);
				btnNext.setEnabled(true);
			}
		}
		
	}
}
