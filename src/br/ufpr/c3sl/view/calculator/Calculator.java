package br.ufpr.c3sl.view.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpr.c3sl.model.CalcLogic;





/*
"AC", "A",  "←", "x²",
"7",  "8",  "9", "÷",
"4",  "5",  "6", "x",
"1",  "2",  "3", "-",
".",  "0",  "±", "+", 
"",    "",  "=", "²√x"
 */		


@SuppressWarnings("serial")
public class Calculator extends JFrame{

	private static final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);

	private JTextField jtfDisplay;

	private JPanel jpMain;
	private JPanel jpRight;
	private JPanel jpCenter;
	private JPanel jpBottom;

	private CalcLogic calc;


	private boolean clearAll;
	private boolean clearDisplay = true;
	private boolean inputMode;

	private String operation;


	public Calculator() {
		setSize(200, 300);
		inicializeVariables();

		this.pack();
		this.setFocusable(true);
		this.setTitle("Calculadora");
		this.setResizable(false);
		//this.setLocationRelativeTo(JPanelCRI.getInstance());
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { cmdKeyTyped(e); }
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { }
		});
	}

	public void inicializeVariables(){
		calc = new CalcLogic();

		jpMain = new JPanel();
		jpMain.setName("jpMain");
		jpMain.setLayout(new BorderLayout(5, 5));
		jpMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(jpMain);

		jtfDisplay = new JTextField("0", 15);
		jtfDisplay.setName("jtfDisplay");
		jtfDisplay.setHorizontalAlignment(JTextField.RIGHT);
		jtfDisplay.setFont(BIGGER_FONT);
		jtfDisplay.setEditable(false);
		jtfDisplay.setFocusable(false);
		jtfDisplay.setBackground(Color.white);

		addButtons();

		jpMain.add(jtfDisplay, BorderLayout.NORTH );
		jpMain.add(jpCenter, BorderLayout.CENTER);
		jpMain.add(jpRight, BorderLayout.EAST);
		jpMain.add(jpBottom, BorderLayout.SOUTH);

	}
	
	private void addButtons(){

		jpCenter = new JPanel();
		jpCenter.setName("jpCenter");
		jpCenter.setLayout(new GridLayout(5, 3, 7, 7));

		JButton jbAC = new JButton("AC");
		jbAC.setName("AC");
		jbAC.setFont(BIGGER_FONT);
		jbAC.setFocusable(false);
		jbAC.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdAC();
			}
		});	

		JButton jbA = new JButton("C");
		jbA.setName("C");
		jbA.setFont(BIGGER_FONT);
		jbA.setFocusable(false);
		jbA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdC();
			}
		});

		JButton jbBksp = new JButton("←"); 
		jbBksp.setName("←");
		jbBksp.setFont(BIGGER_FONT);
		jbBksp.setFocusable(false);
		jbBksp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdBksp();
			}
		});

		jpCenter.add(jbAC);
		jpCenter.add(jbA);
		jpCenter.add(jbBksp);

		String numbers = "7894561230";

		for (Character number : numbers.toCharArray()) {
			JButton b = new JButton(number.toString());
			b.setFont(BIGGER_FONT);
			b.setFocusable(false);
			b.setName(number.toString());
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cmdNumbers(e.getActionCommand());
				}
			});
			jpCenter.add(b);
		}

		JButton jbPoint = new JButton("."); 
		jbPoint.setName(".");
		jbPoint.setFont(BIGGER_FONT);
		jbPoint.setFocusable(false);
		jbPoint.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdPoint();
			}
		});


		JButton jbMoreLess = new JButton("±");
		jbMoreLess.setName("±");
		jbMoreLess.setFont(BIGGER_FONT);
		jbMoreLess.setFocusable(false);
		jbMoreLess.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdMoreLess();
			}
		});

		jpCenter.add(jbPoint, 12);
		jpCenter.add(jbMoreLess);


		jpRight = new JPanel();
		jpRight.setName("jpRight");
		jpRight.setLayout(new GridLayout(5,1,7,7));

		JButton jbPow = new JButton("x²"); 
		jbPow.setName("x²");
		jbPow.setFont(BIGGER_FONT);
		jbPow.setFocusable(false);
		jbPow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdPow();
			}
		});

		JButton jbDivide = new JButton("÷");
		jbDivide.setName("/");
		jbDivide.setFont(BIGGER_FONT);
		jbDivide.setFocusable(false);
		jbDivide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("/");
			}
		});

		JButton jbMultiply = new JButton("x");
		jbMultiply.setName("x");
		jbMultiply.setFont(BIGGER_FONT);
		jbMultiply.setFocusable(false);
		jbMultiply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("x");
			}
		});

		JButton jbSubtract= new JButton("-"); 
		jbSubtract.setName("-");
		jbSubtract.setFont(BIGGER_FONT);
		jbSubtract.setFocusable(false);
		jbSubtract.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("-");
			}
		});

		JButton jbSum = new JButton("+"); 
		jbSum.setFont(BIGGER_FONT);
		jbSum.setName("+");
		jbSum.setFocusable(false);
		jbSum.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("+");
			}
		});
		
		JButton jbPowY = new JButton("<html><sub>X</sub><font size='2'>y</font>"); 
		jbPowY.setName("x^y");
		jbPowY.setFont(BIGGER_FONT);
		jbPowY.setFocusable(false);
		jbPowY.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("x^y");
			}
		});

		JButton jbSqrtY = new JButton("<html><font size='2'>y</font><sub>√x</sub>"); 
		jbSqrtY.setName("srqtY");
		jbSqrtY.setFont(BIGGER_FONT);
		jbSqrtY.setFocusable(false);
		jbSqrtY.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdOperation("sqrtY");
			}
		});
		
		jpRight.add(jbPow);
		jpRight.add(jbDivide); 
		jpRight.add(jbMultiply);
		jpRight.add(jbSubtract);
		jpRight.add(jbSum);

		jpBottom = new JPanel();
		jpBottom.setLayout(new GridBagLayout());
		jpBottom.setName("jpBottom");
		//jpButtom.setBorder(BorderFactory.createEtchedBorder());

		GridBagConstraints c = new GridBagConstraints();
		
		JButton jbEqual = new JButton("=");
		jbEqual.setName("=");
		jbEqual.setFont(BIGGER_FONT);
		jbEqual.setFocusable(false);
		jbEqual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdEqual();
			}
		});

		JButton jbRaiz = new JButton("²√x"); 
		jbRaiz.setName("²√x");
		jbRaiz.setFont(BIGGER_FONT);
		jbRaiz.setFocusable(false);
		jbRaiz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cmdRaiz();
			}
		});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 2;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 0, 6);
		jpBottom.add(jbEqual, c);

//		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		jpBottom.add(jbRaiz, c);
				
//		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 1;
//		c.weightx = 1;
//		c.weighty = 1;
		jpBottom.add(jbSqrtY, c);
		
//		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 1;
//		c.weightx = 1;
//		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		jpBottom.add(jbPowY, c);
		
	}

	private void setDisplay(String s){
		/* if (s.equals("0.0") || s.equals("-0.0"))
			jtfDisplay.setText("0");
		else
		*/
		jtfDisplay.setText(s);
	}

	private String getDisplayText(){
		return jtfDisplay.getText();
	}

	public void clear(){
		setDisplay("0");
		calc.setSetCurrentNumber(false);	
		inputMode = false;
	}

	private void displayErro(String msg){
		JOptionPane.showMessageDialog(this, msg,"Erro", JOptionPane.ERROR_MESSAGE);
		clear();	
	}

	private void cmdNumbers(String s){
		if (clearAll){
			clearAll = false;
			clear();
		}

		if (clearDisplay || getDisplayText().equals("0")){
			setDisplay(s);
			clearDisplay = false;
		}else
			setDisplay(getDisplayText()+s); 

		inputMode = true;
	}

	private void cmdOperation(String s){
		if (s.charAt(s.length()-1) == '.'){
			s.substring(0, (s.length() -1));
		}

		if (!calc.isSetCurrentNumber()){
			calc.setCurrentNumber(getDisplayText());
			calc.setSetCurrentNumber(true);	
		}else
			if (inputMode){
				processOperator();
			}

		operation = s;
		clearDisplay = true;
		clearAll = false;
		inputMode = false;
	}

	private void processOperator() {
		if (operation.equals("-"))
			setDisplay(calc.subtract(getDisplayText())+"");
		else if (operation.equals("+"))
			setDisplay(calc.sum(getDisplayText())+"");
		else if (operation.equals("x^y"))
			setDisplay(calc.powY(getDisplayText())+"");
		else if (operation.equals("sqrtY"))
			setDisplay(calc.sqrtY(getDisplayText())+"");
		else if (operation.equals("/")){
			String result = calc.divide(getDisplayText())+""; 
			if (result.endsWith("Infinity"))
				displayErro("Não é possível dividir um número por zero");
			else
				setDisplay(result);
		}
		else if (operation.equals("x"))
			setDisplay(calc.multiply(getDisplayText())+"");
	}

	private void cmdEqual(){
		if (calc.isSetCurrentNumber())
			if (inputMode){
				processOperator();
				inputMode = false;
				clearAll = true;
			}
	}

	private void cmdAC(){clear();}
	private void cmdC(){setDisplay("0");}

	private void cmdBksp(){
		if (getDisplayText().length() == 1)
			setDisplay("0");
		else
			setDisplay(getDisplayText().substring(0,(getDisplayText().length()-1)));
	}

	private void cmdPoint(){
		if (clearDisplay || getDisplayText().equals("0")){
			setDisplay("0.");
			clearDisplay = false;
		}
		
		if (!getDisplayText().contains(".")){
			setDisplay(getDisplayText()+".");
		}
	}


	private void cmdPow(){
		setDisplay(calc.pow(getDisplayText())+"");
		clearAll = true;
		inputMode = false;
	}
	
	private void cmdMoreLess(){
		if (!getDisplayText().equals("0"))
			if (getDisplayText().contains("-")){
				setDisplay(getDisplayText().replace('-', ' ').trim());
			}else
				setDisplay("-"+getDisplayText());
	}

	private void cmdRaiz(){
		String result = calc.sqrt(getDisplayText())+"";
		if (result.equals("NaN")){
			displayErro("Não existe Raiz de um número negativo");
		}else
			setDisplay(result);

		clearAll = true;
		inputMode = false;
	}

	private void cmdKeyTyped(KeyEvent e){
		char c = e.getKeyChar();
		if ( c >= '0' && c <= '9' ) {
			cmdNumbers(String.valueOf(c-48));
		}else if ( c == '+' || c == '-' ) {
			cmdOperation(c+"");
		}else if ( c == '*' ) {
			cmdOperation("x");
		}else if ( c == 'x') {
			cmdOperation("x^y");
		}else if ( c == '/') {
			cmdOperation("/");
		}else if ( c == '^' ) {
			cmdPow();
		}else if ( c == '.' || c == ',' ) {
			cmdPoint();
		}else if ( c == 10 ) {
			cmdEqual();
		}else if ( c == 8 ) {
			cmdBksp();
		}else if ( c== 27 ) {
			cmdAC();
		}
	}
	
	public static void createAndShowGUI() {
		//Create and set up the window.
		Calculator frame = new Calculator();
		frame.setVisible(true);
	}


	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Calculator.createAndShowGUI();
			}
		});
	}
}
