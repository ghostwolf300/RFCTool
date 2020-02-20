package org.rfc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JPanel;

import org.rfc.controller.DefaultController;
import org.rfc.dto.RFCFunction;
import org.rfc.model.RFCFunctionModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class FunctionSelectionPanel extends JPanel implements IView,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultController controller=null;
	private JButton btnNext;
	private JScrollPane scrollPane;
	private JList<RFCFunction> list;
	
	/**
	 * Create the panel.
	 */
	public FunctionSelectionPanel(DefaultController controller) {
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		add(getScrollPane_1(), "cell 0 0,grow");
		add(getBtnNext(), "cell 0 1");
	}

	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
		}
		return btnNext;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<RFCFunction> getList() {
		if (list == null) {
			list = new JList<RFCFunction>();
		}
		return list;
	}
	
	@Override
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(RFCFunctionModel.P_FUNCTIONS)) {
			List<RFCFunction> functions=(List<RFCFunction>) pce.getNewValue();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
