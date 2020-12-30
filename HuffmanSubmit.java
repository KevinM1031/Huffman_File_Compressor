
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;



/**
 * This class creates a window for displaying the Huffman tree.
 * Implements JFrame class.
 * 
 * @author Kyounghan Min
 *
 */
class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Panel P = new Panel(null, "Tree is Empty");
	
	// class contructor
	Frame() {
		setSize(1300,900);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(P);
		repaint();
		revalidate();
	}
	
	/**
	 * Updates the Huffman trees and frame title to be displayed.
	 * 
	 * @param forest list of Huffman tree roots to display
	 * @param title title for the display
	 */
	public void updateContent(List<Node> forest, String title) {
		P.forest = forest;
		P.title = title;
	}
	
	/**
	 * Updates the window but not the contents of the 
	 * Huffman trees and frame title themselves.
	 */
	public void update() {
		P.repaint();
		P.revalidate();
		repaint();
		revalidate();
	}
	
	/**
	 * Shows UI components.
	 */
	public void showComponents() {
		P.showComponents();
	}
	
	/**
	 * Hides UI components.
	 */
	public void hideComponents() {
		P.hideComponents();
	}
}

/**
 * This class initializes and assign functions regarding the UI 
 * components and Huffman tree display in the window. 
 * Implements JPanel class.
 * 
 * @author Kyounghan Min
 */
class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// display options
	private static int FONT_SIZE = 7; // data font size
	private static boolean SHOW_LEAF_FREQ = false; // display data frequency in leaves
	private static boolean HIDE_INTERNAL_NODES = false; // hide internal nodes
	private static boolean TRANSLUCENT_BRANCHES = false; // translucent branches
	private static boolean BINARY_DISPLAY = false; // data display in binary
	
	// panel dimensions
	private static final int DEFAULT_WIDTH = 1300, DEFAULT_HEIGHT = 900;
	
	// font presets
	private final Font bigFont = new Font("Monospace", Font.BOLD, 18); // font for title
	private Font smallFont = new Font("Monospace", Font.BOLD, FONT_SIZE); // font for data display
	
	// UI components
	private static JCheckBox showLeafFreq, hideInternalNodes, translucentBranches, binaryDisplay;
	private static JSlider fontSize, fps;
	private static JLabel fsLabel, fpsLabel;
	
	// miscellaneous variables
	protected List<Node> forest;
	protected String title;
	
	// class constructor
	Panel(Node[] forest, String title) {
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		this.forest = new ArrayList<Node>();
		this.title = title;
		setLayout(null);
		
		// initialize all UI components
		// checkbox: show data frequencies in leaves - on/off
		showLeafFreq = new JCheckBox("Show frequencies of leaves");
		showLeafFreq.setBounds(750,20,260,20);
		add(showLeafFreq);
		
		// checkbox: hide internal nodes - on/off
		hideInternalNodes = new JCheckBox("Hide internal nodes");
		hideInternalNodes.setBounds(750,40,260,20);
		add(hideInternalNodes);
		
		// checkbox: set branches and leaf borders as translucent - on/off
		translucentBranches = new JCheckBox("Translucent branches");
		translucentBranches.setBounds(750,60,260,20);
		add(translucentBranches);
		
		// checkbox: display data as binary - on/off
		binaryDisplay = new JCheckBox("Display data in binary");
		binaryDisplay.setBounds(750,80,260,20);
		add(binaryDisplay);
		
		// slider: font size
		fontSize = new JSlider();
		fontSize.setBounds(1040,20,240,40);
		fontSize.setValue(FONT_SIZE);
		fontSize.setMaximum(20);
		fontSize.setPaintTicks(true);
		fontSize.setPaintLabels(true);
		fontSize.setMajorTickSpacing(5);
		fontSize.setMinorTickSpacing(1);
		fontSize.setPaintTrack(false);
		add(fontSize);
		
		// slider: FPS
		fps = new JSlider();
		fps.setBounds(1040,60,240,40);
		fps.setValue(1000/HuffmanSubmit.DELAY);
		fps.setMinimum(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);
		fps.setMajorTickSpacing(20);
		fps.setMinorTickSpacing(5);
		fps.setPaintTrack(false);
		fps.setMaximum(101);
		add(fps);
		
		// label: font size slider
		fsLabel = new JLabel("Font:");
		fsLabel.setBounds(1000,20,40,40);
		add(fsLabel);
		
		// label: FPS slider
		fpsLabel = new JLabel("FPS:");
		fpsLabel.setBounds(1000,60,40,40);
		add(fpsLabel);
	}
	
	/**
	 * Sets the visibility of all UI components as true.
	 */
	public void showComponents() {
		showLeafFreq.setVisible(true);
		hideInternalNodes.setVisible(true);
		translucentBranches.setVisible(true);
		binaryDisplay.setVisible(true);
		fontSize.setVisible(true);
		fps.setVisible(true);
		fsLabel.setVisible(true);
		fpsLabel.setVisible(true);
	}
	
	/**
	 * Sets the visibility of all UI components as false.
	 */
	public void hideComponents() {
		showLeafFreq.setVisible(false);
		hideInternalNodes.setVisible(false);
		translucentBranches.setVisible(false);
		binaryDisplay.setVisible(false);
		fontSize.setVisible(false);
		fps.setVisible(false);
		fsLabel.setVisible(false);
		fpsLabel.setVisible(false);
	}
	
	/**
	 * Basic overriding paint method.
	 * Called automatically when panel is updated.
	 * Creates graphic images.
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.lightGray);
		g.fillRect(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		g.setColor(Color.white);
		g.fillRect(20,120,1260,660);
		g.setColor(Color.black);
		g.drawRect(20,120,1260,660);
		
		// Draw trie
		drawForest(forest,DEFAULT_WIDTH/2,150,g);
		
		// Create label indicating which command is being run
		String caption = title;
		g.setFont(bigFont);
		g.drawString(caption, 50, 50);
	}
	
	/**
	 * Draws multiple Huffman trees next to one another.
	 * 
	 * @param forest list of Huffman tree roots
	 * @param x x-coordinate of the center tree root
	 * @param y y-coordinate of the center tree root
	 * @param g Graphics
	 */
	private void drawForest(List<Node> forest, int x, int y, Graphics g) {
		if(forest == null) return;
		
		// antialiasing for smooth graphics
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		// drawing all trees in the forest
		Node current;
		int newX;
		for(int i = 0; i < forest.size(); i++) {
			
			// updating and receiving new display options
			SHOW_LEAF_FREQ = showLeafFreq.isSelected();
			HIDE_INTERNAL_NODES = hideInternalNodes.isSelected();
			TRANSLUCENT_BRANCHES = translucentBranches.isSelected();
			BINARY_DISPLAY = binaryDisplay.isSelected();
			FONT_SIZE = fontSize.getValue();
			HuffmanSubmit.DELAY = 1000/fps.getValue();
			smallFont = new Font("Monospace", Font.BOLD, FONT_SIZE);
			
			// drawing individual tree
			current = forest.get(i);
			newX = x + (int) (x * (-forest.size()/2+i)/(double)forest.size());
			drawTree(current, newX, y, g);
		}
	}
	
	/**
	 * Draws a single Huffman tree.
	 * 
	 * @param current root of the Huffman tree
	 * @param x x-coordinate of the root
	 * @param y y-coordinate of the root
	 * @param g Graphics
	 */
	private void drawTree(Node current, int x, int y, Graphics g) {
		
		// if current node is a leaf
		// draw leaf
		if(current.isLeaf()) {
			drawLeaf((char) Integer.parseInt(current.data,2) + "", current.freq + "", x, y, g);
			
		// if current node is not a leaf (is an internal node)
		} else {
			
			// draw left branch if exists
			if(current.left != null) {
				if(TRANSLUCENT_BRANCHES) g.setColor(new Color(0,0,0,63));
				if(y > 270) {
					g.drawLine(x, y, x-7, y+25);
					drawTree(current.left, x-7, y+25, g);
				} else {
					g.drawLine(x, y, (int) (x-225*Math.pow(1.75, -Math.abs(120-y)/20)), y+25);
					drawTree(current.left, (int) (x-225*Math.pow(1.75, -Math.abs(120-y)/20)), y+25, g);
				}
			}
			
			// draw right branch if exists
			if(current.right != null) {
				if(TRANSLUCENT_BRANCHES) g.setColor(new Color(0,0,0,63));
				if(y > 270) {
					g.drawLine(x, y, x+7, y+25);
					drawTree(current.right, x+7, y+25, g);
				} else {
					g.drawLine(x, y, (int) (x+225*Math.pow(1.75, -Math.abs(120-y)/20)), y+25);
					drawTree(current.right, (int) (x+225*Math.pow(1.75, -Math.abs(120-y)/20)), y+25, g);
				}
			}
			
			// draw internal node
			if(!HIDE_INTERNAL_NODES) drawInternalNode(current.freq + "", x, y, g);
		}
	}
	
	/**
	 * Draws a single leaf of a Huffman tree.
	 * 
	 * @param data leaf data
	 * @param freq data frequency
	 * @param x x-coordinate of the leaf
	 * @param y y-coordinate of the leaf
	 * @param g Graphics
	 */
	private void drawLeaf(String data, String freq, int x, int y, Graphics g) {
		
		// setting font and getting string dimensions of the current data
		g.setFont(smallFont);
		if(BINARY_DISPLAY) data = Integer.toBinaryString(data.charAt(0));
		String text = data;
		if(SHOW_LEAF_FREQ) text += ":" + freq;
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
					
		// draw node
		g.setColor(Color.green);
		if(TRANSLUCENT_BRANCHES) g.setColor(new Color(0,255,0,63));
		g.fillRect(x-width/2-1, y-height/2-1, width+2, height+2);
		g.setColor(Color.black);
		if(TRANSLUCENT_BRANCHES) g.setColor(new Color(0,0,0,63));
		g.drawRect(x-width/2-1, y-height/2-1, width+2, height+2);
		g.setColor(Color.black);
		g.drawString(text, x-width/2, y+height/2);
	}
	
	/**
	 * Draws a single internal node of a Huffman tree.
	 * 
	 * @param freq data frequency
	 * @param x x-coordinate of the internal node
	 * @param y y-coordinate of the internal node
	 * @param g Graphics
	 */
	private void drawInternalNode(String freq, int x, int y, Graphics g) {
		
		// setting font and getting string dimensions of the current data
		g.setFont(smallFont);
		int width = g.getFontMetrics().stringWidth(freq);
		int height = g.getFontMetrics().getHeight();
		
		// draw node
		g.setColor(Color.yellow);
		if(TRANSLUCENT_BRANCHES) g.setColor(new Color(255,255,0,63));
		g.fillRect(x-width/2-1, y-height/2-1, width+2, height+2);
		g.setColor(Color.black);
		if(TRANSLUCENT_BRANCHES) g.setColor(new Color(0,0,0,63));
		g.drawRect(x-width/2-1, y-height/2-1, width+2, height+2);
		g.setColor(Color.black);
		g.drawString(freq, x-width/2, y+height/2);
	}
}



/**
 * This class deals with the node for Huffman tree, along with some
 * basic functions.
 * 
 * @author Kyounghan Min
 */
class Node {
	String data;
	int freq;
	Node left, right;
	
	// class constructor for generating a leaf
	Node(String data, int freq) {
		this.data = data;
		this.freq = freq;
	}
	
	// class constructor for generating an internal node
	Node(int freq, Node left, Node right) {
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Checks and returns whether the node is a leaf or not.
	 * 
	 * @return true if the node is a leaf; otherwise false
	 */
	boolean isLeaf() {
		if(left == null && right == null) return true;
		return false;
	}
}



/**
 * This class encodes/compresses and decodes/expands a give file with
 * encode() and decode() methods.
 * Implements Huffman class.
 * 
 * @author Kyounghan Min
 */
public class HuffmanSubmit implements Huffman {
	
	// general final variables
	private static final int BIT = 8; // number of bits in one byte
	private static final int LIMIT = 256; // largest code value in extended ASCII codes 
	private static final boolean DISPLAY = true; // on/off tree display
	
	// miscellaneous variables
	protected static int DELAY = 100; // ms delay between each frame of animated tree generation display
	private static Frame F; // JFrame for tree display
	
	/**
	 * Returns a Huffman data tree using the provided frequencies of characters.
	 * 
	 * @param freqArr array containing the frequences of each characters
	 * @return root node of the generated Huffman tree
	 */
	private Node buildTree(int[] freqArr, String title) {
		
		// creating a list containing all roots
		List<Node> rootList = new ArrayList<Node>();
		for(int i = 0; i < freqArr.length; i++)
			if(freqArr[i] > 0) rootList.add(new Node(Integer.toBinaryString(i),freqArr[i]));
		
		// special case: if there is only one type of data, simply return that data as a node
		if(rootList.size() <= 1) return rootList.get(0);
		
		// build tree
		int min1, min2, curr, iter = 0;
		while(rootList.size() > 1) {
			min1 = 0;
			min2 = 1;
			iter += 1;
			
			// find two roots with smallest frequencies
			for(int i = 2; i < rootList.size(); i++) {
				curr = rootList.get(i).freq;
				if(curr < rootList.get(min1).freq) {
					if(rootList.get(min2).freq > rootList.get(min1).freq) min2 = min1;
					min1 = i;
				} else if(curr < rootList.get(min2).freq) {
					min2 = i;
				}
			}
									
			// merge the two roots
			int freq1 = rootList.get(min1).freq, freq2 = rootList.get(min2).freq;
			if(freq2 < freq1)
				rootList.set(min1, new Node(freq1+freq2, rootList.get(min2), rootList.get(min1)));
			else
				rootList.set(min1, new Node(freq1+freq2, rootList.get(min1), rootList.get(min2)));
			rootList.remove(min2);
			
			updateDisplay(rootList, "[" + title + "] Running iteration #" + iter);
		}
		
		// finalize display and return
		endDisplay();
		if(rootList.size() <= 1) delay(1000);
		return rootList.get(0);
	}
	
	/**
	 * Returns a string array containing the Huffman encryption codes for all characters, 
	 * which are generated using the provided Huffman tree. The indexes of the returning
	 * array represents the extended ASCII decimal code, and the element in that index
	 * represents its encryption code.
	 * 
	 * @param tree root of the Huffman tree
	 * @return string array of encryption code
	 */
	private String[] getCode(Node tree) {
		if(tree.isLeaf()) {
			String[] arr = new String[LIMIT];
			arr[Integer.parseInt(tree.data,2)] = "0";
			return arr;
		} else return getCodeRecursive(tree, new String[LIMIT], "");
	}
	
	/**
	 * The recursive algorithm for getCode method.
	 * 
	 * @param tree root of the Huffman tree
	 * @param codeList incomplete string array of encryption code
	 * @param code encryption code
	 * @return finished string array of encryption code
	 */
	private String[] getCodeRecursive(Node tree, String[] codeList, String code) {
		if(tree.isLeaf()) codeList[Integer.parseInt(tree.data,2)] = code;
		if(tree.left != null) getCodeRecursive(tree.left, codeList, code+"0");
		if(tree.right != null) getCodeRecursive(tree.right, codeList, code+"1");
		return codeList;
	}
	
	/**
	 * Implements the Huffman algorithm to encrypt and potentially compress
	 * the file.
	 * 
	 * @param inputFile name of the file to compress
	 * @param outputFile name assignment for the compressed file
	 * @param tree Huffman tree generated using the original frequency file
	 */
	private void compress(String inputFile, String outputFile, Node tree) {
		String[] codedFreq = getCode(tree);

		BinaryIn bi = new BinaryIn(inputFile);
		BinaryOut bo = new BinaryOut(outputFile);
		String bin;
		char c;
		
		while(!bi.isEmpty()) {
			c = bi.readChar();
			bin = codedFreq[c];
						
			for(int i = 0; i < bin.length(); i++) {
				if(bin.charAt(i) == '0') bo.write(false);
				else bo.write(true);
			}
		}
		bo.flush();
	}
	
	/**
	 * Implements the Huffman algorithm to decrypt and potentially expand
	 * the file.
	 * 
	 * @param inputFile name of the file to expand
	 * @param outputFile name assignment for the expanded file
	 * @param tree Huffman tree generated using the original frequency file
	 */
	private void expand(String inputFile, String outputFile, Node tree) {
		BinaryIn bi = new BinaryIn(inputFile);
		BinaryOut bo = new BinaryOut(outputFile);
		boolean bit;
		Node curr = tree;
		
		// reading the entire encrypted data by iterating with its Huffman tree
		while(!bi.isEmpty()) {
			
			// if current node is a leaf
			// write original binary data
			if(curr.isLeaf()) {
				for(int i = curr.data.length(); i < BIT; i++) bo.write(false);
				
				for(char b : curr.data.toCharArray()) {
					if(b == '0') bo.write(false);
					else bo.write(true);
				}
				curr = tree;
			
			// if current node is not a leaf (is an internal node)
			// read and move further down the tree
			} else {
				bit = bi.readBoolean();
				if(!bit && curr.left != null) curr = curr.left;
				else if(curr.right != null) curr = curr.right;
			}
		}
		bo.flush();
	}
	
	/**
	 * Creates a separate large window, which will be used to display the tree.
	 * 
	 * @param tree the Huffman tree to display
	 */
	private void openDisplay() {
		if(!DISPLAY || F != null) return;
		
		F = new Frame();
	}
	
	/**
	 * Displays the Huffman tree at any stage of its generation, followed by a
	 * set delay: DELAY. Using this method may cause the runtime to increase 
	 * significantly
	 */
	private void updateDisplay(List<Node> forest, String title) {
		if(!DISPLAY) return;
		
		delay();
		F.showComponents();
		F.updateContent(forest, title);
		F.repaint();
		F.revalidate();
	}
	
	/**
	 * Called at the end of tree generation animation. Simply hides the UI
	 * components.
	 */
	private void endDisplay() {
		F.hideComponents();
	}
	
	/**
	 * Triggers a delay of DELAY milliseconds. Used for delays between each
	 * animation frame.
	 */
	private void delay() {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Triggers a delay of given amount of milliseconds.
	 * 
	 * @param delay delay in milliseconds
	 */
	private void delay(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Implements the Huffman encoding algorithm.
	 * 
	 * @param inputFile name of the file to encode
	 * @param outputFile name assignment for the encoded file
	 * @param freqFile name assignment for the frequency file
	 */
	public void encode(String inputFile, String outputFile, String freqFile) {
		
		// get data from file
		BinaryIn bi = new BinaryIn(inputFile);
		int[] rawFreq = new int[LIMIT];
		while(!bi.isEmpty()) rawFreq[bi.readChar()] += 1;

		// write frequency file
		BinaryOut bo = new BinaryOut(freqFile);
		for(int i = 0; i < rawFreq.length; i++)
			bo.write(Integer.toBinaryString(i) + ":" + rawFreq[i] + "\n");
		bo.flush();
		
		// build huffman tree
		openDisplay();
		Node tree = buildTree(rawFreq, "Encode");
		
		// write compressed file
		compress(inputFile, outputFile, tree);
	}
	
	/**
	 * Implements the Huffman decoding algorithm.
	 * 
	 * @param inputFile name of the file to decode
	 * @param outputFile name assignment for the decoded file
	 * @param freqFile name of the frequency file
	 */
	public void decode(String inputFile, String outputFile, String freqFile) {
		
		// get frequency file
		int[] rawFreq = new int[LIMIT];
		try {
			BufferedReader br = new BufferedReader(new FileReader(freqFile));
			int n = 0;
			String line;
			
			while(true) {
				line = br.readLine();
				if(line == null) break;
				rawFreq[n] = Integer.parseInt(line.substring(line.indexOf(":")+1));
				n++;
			}
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// build huffman tree
		openDisplay();
		Node tree = buildTree(rawFreq, "Decode");
		
		// write expanded file
		expand(inputFile, outputFile, tree);
	}
	
	/**
	 * main method for testruns
	 * 
	 * args format:
	 * --encode (file to encode) (encoded file name) (frequency table file name)
	 * OR
	 * --decode (file to decode) (decoded file name) (frequency table file)
	 * 
	 * Example:
	 * --encode alice30.txt alice30.enc freq.txt
	 * --decode alice30.en alice30.txt freq.txt
	 */
	public static void main(String[] args) {
		Huffman huffman = new HuffmanSubmit();
		
		if(args[0].equals("--encode")) {
			huffman.encode(args[1], args[2], args[3]);
			
		} else if(args[0].equals("--decode")) {
			huffman.decode(args[1], args[2], args[3]);
		}
	}
}

