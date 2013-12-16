package com.simon.huber.hackenbush.gui.info;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.simon.huber.hackenbush.HackenbushFenster;
import com.simon.huber.hackenbush.HauptFenster;
import com.simon.huber.hackenbush.resources.Settings;

/**
 * Hilfe Popup f�r das Hauptfenster
 * @author Simon Huber
 *
 */
public class HauptfensterInfo extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ContentPane
     */
    private JPanel anzeige = new JPanel();
    
    /**
     * Settings damit checkBox "nicht mehr anzeigen" funktioniert
     */
    private Settings settings;
    private HauptFenster fenster;

    public HauptfensterInfo(Settings settings, HauptFenster f) {
	this.settings = settings;
	fenster = f;
	setAlwaysOnTop(true);
	setLayout(new BorderLayout());
	setModalityType(ModalityType.APPLICATION_MODAL);
	setTitle("Hauptfensterinfo");
	setSize(500, 500);
	setLocationByPlatform(true);
	addContent();
	setVisible(true);
    }
    
    /**
     * F�gt Text hinzu
     */
    private void addContent() {
	setContentPane(anzeige);
	
	JLabel content = new JLabel();
	content.setText("<html>\n" +
			"<h2>Zoom</h2>\n" +
			"<p>Durch das Feld zus�tliche H�he \n" +
			"kann man aus der Figur herauszoomen und somit <br>\n" +
			"weitere Kanten einf�gen.</p>\n" +
			"<h2>Starten eines Spiels</h2>\n" +
			"<ol>\n" +
			"<li>W�hlen Sie das Men� \"Spiel\" <br>" +
			"geh�ren soll.</li> \n" +
			"<li>W�hlen Sie nun den Men�punkt \"Spiel starten\" </li> \n" +
			"</ol>" +
			"<h2>Tastenzuordnungen</h2> \n" +
			"Beachten Sie, dass die Figur den Fokus hat!" +
			"<ul> \n" +
			"<li>ENTF: L�schen einer makierten Kante (wenn man im <br>" +
			"gestarteten Spiel berechtigt ist zu ziehen)</li> \n" +
			"<li>N: Neuberechnen der Kantenwerte</li> \n" +
			"<li>Die normalen Tastenk�rzel, die im Men� angegeben sind.</li> \n" +
			"</ul> \n" +
			"</html>");
	anzeige.add(content, BorderLayout.NORTH);
	
	
	JCheckBox checkbox = new JCheckBox("Nicht mehr anzeigen");
	checkbox.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JCheckBox item = (JCheckBox) arg0.getSource();
		settings.setShowHauptfensterInfo(!item.isSelected());
		fenster.rebuildEinstellungsMenu();
	    }
	});
	anzeige.add(checkbox, BorderLayout.CENTER);
	
	
	JButton button = new JButton("Schlie�en");
	button.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		dispose();
	    }
	});
	anzeige.add(button, BorderLayout.AFTER_LAST_LINE);
    }
    
}
