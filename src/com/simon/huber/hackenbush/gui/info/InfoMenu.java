package com.simon.huber.hackenbush.gui.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Erstellt und bef�llt das Info-Men�
 * @author Simon Huber
 *
 */
public class InfoMenu extends JMenu {

    private static final long serialVersionUID = 1L;

    public InfoMenu() {
	super("Infos");
	createMenu();
    }
    
    /**
     * Bef�llt das neue Men�
     */
    private void createMenu() {
	JMenuItem about = new JMenuItem("Programminfos");
	about.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		new ProgrammInfo();
	    }
	});
	add(about);
    }

}
