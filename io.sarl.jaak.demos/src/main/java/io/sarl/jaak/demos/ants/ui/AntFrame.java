package io.sarl.jaak.demos.ants.ui;

import io.sarl.jaak.kernel.JaakController;
import io.sarl.jaak.kernel.JaakEvent;
import io.sarl.jaak.kernel.JaakListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.arakhne.afc.vmutil.locale.Locale;

/**
 * Graphic User Interface for the ant demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class AntFrame extends JFrame implements JaakListener {

	private static final long serialVersionUID = 530029308607649235L;

	private final JaakController controller;
	
	/**
	 * @param panel is the panel which is able to display the ant colony.
	 * @param width - the preferred width of the frame (in number of cells).
	 * @param height - the preferred width of the frame (in number of cells).
	 * @param controller - the controller of the simulation.
	 */
	public AntFrame(AntPanel panel, int width, int height, JaakController controller) {
		this.controller = controller;
		
		setTitle(Locale.getString(AntFrame.class, "TITLE_0")); //$NON-NLS-1$
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		addWindowListener(new Closer());

		JScrollPane scrollPane = new JScrollPane(panel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setPreferredSize(new Dimension(50+AntPanel.CELL_SIZE*width, 50+AntPanel.CELL_SIZE*height));

		pack();
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class Closer extends WindowAdapter {
		/**
		 */
		public Closer() {
			//
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowClosing(WindowEvent event) {
			AntFrame.this.controller.stopSimulation();
		}
	}

	@Override
	public void environmentStateChanged(JaakEvent event) {
		int count = event.getEnvironment().getTurtleCount();
		String title;
		switch(count) {
		case 0:
			title = Locale.getString(AntFrame.class, "TITLE_0"); //$NON-NLS-1$
			break;
		case 1:
			title = Locale.getString(AntFrame.class, "TITLE_1", Integer.valueOf(count)); //$NON-NLS-1$
			break;
		default:
			title = Locale.getString(AntFrame.class, "TITLE_n", Integer.valueOf(count)); //$NON-NLS-1$
			break;
		}
		setTitle(title);
	}

	@Override
	public void simulationStopped(JaakEvent event) {
		setTitle(Locale.getString(AntFrame.class, "TITLE_0")); //$NON-NLS-1$
	}

	@Override
	public void simulationStarted(JaakEvent event) {
		int count = event.getEnvironment().getTurtleCount();
		String title;
		switch(count) {
		case 0:
			title = Locale.getString(AntFrame.class, "TITLE_0"); //$NON-NLS-1$
			break;
		case 1:
			title = Locale.getString(AntFrame.class, "TITLE_1", Integer.valueOf(count)); //$NON-NLS-1$
			break;
		default:
			title = Locale.getString(AntFrame.class, "TITLE_n", Integer.valueOf(count)); //$NON-NLS-1$
			break;
		}
		setTitle(title);
	}

}
