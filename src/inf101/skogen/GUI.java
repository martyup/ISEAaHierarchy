package inf101.skogen;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame {
	/**
	 * Katalog hvor GUI-en leter etter bilder (relativt til hvor klassefilene
	 * ligger)
	 */
	public static String bildeSti = "images/";

	private static final long serialVersionUID = -8702692606797266435L;

	public GUI(ISimulator spill, Collection<ITing> muligeTing, int bredde,
			int høyde) {
		add(new GUIPanel(spill, muligeTing, bredde, høyde));
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

/**
 * GUI for 101-meterskogen
 * 
 * @author anya
 *
 */
class GUIPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	Random random = new Random();

	private int bredde, høyde;
	/**
	 * Her ligger ligger verden
	 */
	private Painter hovedPanel;
	/**
	 * Viser tid og poeng nederst
	 */
	private JPanel statusPanel;
	/**
	 * 'Nytt spill' knapp og størrelsesvalg
	 */
	private JPanel kontrollPanel;

	private JPanel toolPanel;

	/**
	 * Kontrollknapper
	 */
	private JButton ny, pause, avslutt, neste;

	private JCheckBox visId, visStørrelse, visSyn, visInfo, visBilde;

	private JLabel status;
	/**
	 * For å finne X og Y til knappen som ble trykket
	 */

	private Collection<ITing> tingListe;
	private Collection<ITing> muligeTing;
	private Map<Object, ITing> tingMap;

	/**
	 * Vekker oss hvert halve sekund
	 */
	private javax.swing.Timer timer;

	private int steg = 0;

	private ISimulator spill;

	/**
	 * Oppretter en ny spill-GUI
	 * 
	 * Anbefalt bredde og høyde er 101.
	 * 
	 * Tingene i muligeTing bør ikke endres -- disse er kun til intern bruk av
	 * GUI-klassen. Alle konkrete implementasjoner av ITing bør ha en
	 * representant i denne samlingen
	 * 
	 * @param spill
	 *            Spillet som skal kontrolleres
	 * @param muligeTing
	 *            En samling av ting som brukeren kan lage ved å trykke på
	 *            knapper
	 * @param bredde
	 *            Spillets bredde
	 * @param høyde
	 *            Spillets høyde
	 */
	public GUIPanel(ISimulator spill, Collection<ITing> muligeTing, int bredde,
			int høyde) {

		this.spill = spill;
		this.bredde = bredde;
		this.høyde = høyde;
		this.muligeTing = muligeTing;

		timer = new javax.swing.Timer(100, this); // vekk oss hvert 100
													// millisekund

		kontrollPanel = new JPanel();
		kontrollPanel.setLayout(new BoxLayout(kontrollPanel,
				BoxLayout.PAGE_AXIS));
		hovedPanel = new Painter();
		statusPanel = new JPanel();
		toolPanel = new JPanel();
		toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		toolPanel.setAlignmentX(CENTER_ALIGNMENT);
		setLayout(new BorderLayout());
		add(kontrollPanel, BorderLayout.WEST);
		add(hovedPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);

		ny = new JButton("Omstart");
		ny.addActionListener(this);
		ny.setMaximumSize(new Dimension(Short.MAX_VALUE,
				ny.getMaximumSize().height));
		ny.setAlignmentX(CENTER_ALIGNMENT);
		pause = new JButton("Pause");
		pause.addActionListener(this);
		pause.setMaximumSize(new Dimension(Short.MAX_VALUE, pause
				.getMaximumSize().height));
		pause.setAlignmentX(CENTER_ALIGNMENT);
		neste = new JButton("Neste");
		neste.addActionListener(this);
		neste.setMaximumSize(new Dimension(Short.MAX_VALUE, neste
				.getMaximumSize().height));
		neste.setAlignmentX(CENTER_ALIGNMENT);
		avslutt = new JButton("Avslutt");
		avslutt.addActionListener(this);
		avslutt.setMaximumSize(new Dimension(Short.MAX_VALUE, avslutt
				.getMaximumSize().height));
		avslutt.setAlignmentX(CENTER_ALIGNMENT);
		kontrollPanel.add(ny);
		kontrollPanel.add(pause);
		kontrollPanel.add(neste);
		kontrollPanel.add(avslutt);

		JPanel boxpanel = new JPanel();
		boxpanel.setLayout(new BoxLayout(boxpanel, BoxLayout.PAGE_AXIS));
		boxpanel.setAlignmentX(CENTER_ALIGNMENT);
		visId = new JCheckBox("ID", hovedPanel.visId);
		visId.addActionListener(this);
		boxpanel.add(visId);
		visStørrelse = new JCheckBox("Størrelse", hovedPanel.visStørrelse);
		visStørrelse.addActionListener(this);
		boxpanel.add(visStørrelse);
		visSyn = new JCheckBox("Syn", hovedPanel.visSyn);
		visSyn.addActionListener(this);
		boxpanel.add(visSyn);
		visInfo = new JCheckBox("Info", hovedPanel.visInfo);
		visInfo.addActionListener(this);
		boxpanel.add(visInfo);
		visBilde = new JCheckBox("Bilde", hovedPanel.visBilde);
		visBilde.addActionListener(this);
		boxpanel.add(visBilde);

		kontrollPanel.add(boxpanel);

		tingMap = new HashMap<Object, ITing>();
		int buttonWidth = 40;
		for (ITing t : muligeTing) {
			JButton b;
			if (t.hentIkonNavn() != null)
				b = new JButton(new ImageIcon(ImageCache.finnBilde(
						t.hentIkonNavn(), 32)));
			else if (t.hentBildeNavn(0) != null)
				b = new JButton(new ImageIcon(ImageCache.finnBilde(
						t.hentBildeNavn(0), 32)));
			else
				b = new JButton(t.hentNavn());
			b.setMargin(new Insets(0, 0, 0, 0));
			b.addActionListener(this);
			buttonWidth = b.getPreferredSize().width;
			tingMap.put(b, t);
			toolPanel.add(b);
		}
		// System.out.println(buttonWidth);
		toolPanel.setPreferredSize(new Dimension(buttonWidth * 2 + 4, toolPanel
				.getPreferredSize().height));
		toolPanel.setMaximumSize(new Dimension(buttonWidth * 2 + 4,
				Short.MAX_VALUE));
		kontrollPanel.add(toolPanel);

		status = new JLabel("Ok");
		statusPanel.add(status);

		tingListe = new ArrayList<ITing>();

		timer.start();

	}

	/**
	 * Denne blir kalt av Java hver gang brukeren trykker på en knapp, eller
	 * hver gang timer-signalet avfyres.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pause) {
			if (timer.isRunning()) {
				pause.setText("Fortsett");
				timer.stop();
			} else {
				pause.setText("Pause");
				timer.start();
			}
		} else if (e.getSource() == ny) {
			steg = 0;
			spill.startPåNytt();
		} else if (e.getSource() == visId)
			hovedPanel.visId = ((JCheckBox) e.getSource()).isSelected();
		else if (e.getSource() == visStørrelse)
			hovedPanel.visStørrelse = ((JCheckBox) e.getSource()).isSelected();
		else if (e.getSource() == visSyn)
			hovedPanel.visSyn = ((JCheckBox) e.getSource()).isSelected();
		else if (e.getSource() == visInfo)
			hovedPanel.visInfo = ((JCheckBox) e.getSource()).isSelected();
		else if (e.getSource() == visBilde)
			hovedPanel.visBilde = ((JCheckBox) e.getSource()).isSelected();
		else if (e.getSource() == avslutt) {
			System.exit(0);
		} else if (e.getSource() == neste || e.getSource() == timer) {
			if (e.getSource() == neste) {
				pause.setText("Fortsett");
				timer.stop();
			} else
				timer.restart();
			spill.nyttSteg(steg);
			tingListe = spill.alleTing();
			status.setText(spill.status());
			repaint();
			steg++;
			Toolkit.getDefaultToolkit().sync();
		} else if (tingMap.containsKey(e.getSource())) {
			ITing t = tingMap.get(e.getSource());
			t = t.lagNy(random.nextInt(bredde), random.nextInt(høyde));
			spill.leggTil(t);

			//
			// System.out.println("Plankton");
			// tingListe = spill.alleTing();
			// status.setText(spill.status());
			// repaint();
			//
		}
	}

	/**
	 * Selve spillområdet
	 * 
	 * @author anya
	 *
	 */
	class Painter extends JComponent {
		private static final long serialVersionUID = -942947300803441395L;
		List<Shape> shapes;
		Path2D.Double node;
		double scale;
		public boolean visId = false, visStørrelse = false, visSyn = false,
				visInfo = false, visBilde = true;

		public Painter() {
			super();
			this.scale = 7;
			this.setMinimumSize(new Dimension(bredde + 20, høyde + 20));
			this.setPreferredSize(new Dimension(i(bredde) + 20, i(høyde) + 20));
		}

		/**
		 * Denne kalles hver gang vi skal oppdatere skjermbildet, og er
		 * ansvarlig for å tegne alt på skjermen.
		 * 
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		public void paint(Graphics graphics) {
			super.paint(graphics);
			Graphics2D g = (Graphics2D) graphics;
			// sett skaleringen basert på vinduets nåværende størrelse
			scale = getWidth() / bredde;

			// bakgrunnsbildet
			if (spill.bakgrunn() != null) {
				Image bg = ImageCache.finnBilde(spill.bakgrunn(),
						i(bredde) + 20);
				if (bg != null)
					g.drawImage(bg, 0, 0, null);
			}
			// g.setColor(Color.GRAY);
			// g.drawRect(10, 10, i(bredde), i(høyde));
			// tegn alle tingene
			for (ITing ting : tingListe) {
				if (ting.erIBruk()) {
					int x = i(ting.hentX()) + 10;
					int y = i(ting.hentY()) + 10;
					int s = i(ting.hentStørrelse());

					if (visBilde && s >= 1) {
						String bilde = ting.hentBildeNavn(steg);
						if (bilde != null) {
							Image img = ImageCache.finnBilde(bilde, s * 2);
							if (img != null)
								if (ting.erLevende())
									g.drawImage(img, x - s, y - s, null);
								else
									g.drawImage(img, x - s, y + s,
											img.getWidth(null),
											-img.getHeight(null), null);
						}
					}

					if (visStørrelse) {
						if (ting.erLevende())
							g.setColor(Color.WHITE);
						else
							g.setColor(Color.BLACK);
						g.drawOval(x - s, y - s, s * 2, s * 2);
					}

					if (visSyn && ting instanceof IAktiv && ting.erLevende()) {

						g.setColor(Color.ORANGE);
						int syn = i(((IAktiv) ting).synsRadius());
						g.drawOval(x - syn, y - syn, syn * 2, syn * 2);
					}

					if (visId) {
						char[] id = { ting.hentId() };

						g.setColor(Color.BLACK);
						g.drawChars(id, 0, 1, x - s, y - s);
					}

					if (visInfo) {
						g.setColor(Color.BLACK);
						g.drawString(ting.hentInfo(), x - s, y + s + 10);
					}
				}
			}
		}

		/**
		 * Konverter spill-koordinater til piksler
		 * 
		 * @param d
		 *            Doubleverdi i spill-koordinatsystemet
		 * @return Heltallsverdi i piksel-koordinatsystemet
		 */
		private int i(double d) {
			return (int) Math.round(d * scale);
		}
	}

}

/**
 * Klasse som ordner med å laste inn og skalere bilder. Ferdige bilder blir
 * mellomlagret til fremtidig bruk.
 * 
 * @author anya
 *
 */
class ImageCache {
	private static HashMap<String, Image> images = new HashMap<String, Image>();

	/**
	 * @param lyd
	 *            Navnet på billedfilen (leter i <code>GUI.bildeSti</code>
	 *            underkatalogen)
	 * @param størrelse
	 *            Bredde som bildet skal skaleres til
	 */
	public static Image finnBilde(String bilde, int størrelse) {
		String key = bilde;
		if (størrelse != 0)
			key = key + ":" + størrelse;
		if (!images.containsKey(key)) {
			Image img;
			if (!images.containsKey(bilde)) {
				try {
					URL url = ImageCache.class
							.getResource(GUI.bildeSti + bilde);
					if (url == null)
						throw new IOException("Fant ikke filen: '"
								+ GUI.bildeSti + bilde + "'");
					img = ImageIO.read(url);
					images.put(bilde, img);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else
				img = images.get(bilde);

			if (størrelse != 0) {
				img = img.getScaledInstance(størrelse, -1,
						Image.SCALE_AREA_AVERAGING);
				/*
				 * int w = img.getWidth(null); int h = img.getHeight(null); int
				 * newW = størrelse; int newH =
				 * (int)Math.round((double)h*(((double)newW)/(double)w));
				 * BufferedImage img2 = new BufferedImage(newW, newH,
				 * BufferedImage.TYPE_INT_ARGB); Graphics2D g2 =
				 * img2.createGraphics();
				 * g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				 * RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				 * g2.drawImage(img, 0, 0, newW, newH, null); g2.dispose(); img
				 * = img2;
				 */
				images.put(key, img);
			}
			return img;

		}

		return images.get(key);
	}
}
