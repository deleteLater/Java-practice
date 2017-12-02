package per.test;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import javax.swing.*;

public class Calculator {
	public static MyLabel l_result = new MyLabel("Result:");
	public static MyLabel l_euqation = new MyLabel("Equation:");
	public static double result = 0;
	public static void main(String args[]) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Calculator();
	}
	public static void calculate(String equation) {
		if(equation.isEmpty()) {
			return ;
		}
		//calculate when equations ends with operator
		char lastCh = equation.charAt(equation.length()-1);
		if( Character.isDigit(lastCh) || lastCh == '.' ) {
			return ;
		}
		result = 0;
		String str_num_temp = new String();
		String str_nums = new String();
		String str_chs = new String();
		char []equas = equation.toCharArray();
		for(int i = 0; i < equas.length; i++) {
			if(Character.isDigit(equas[i]) || equas[i] == '.') {
				str_num_temp += equas[i];
			}else {
				str_nums += str_num_temp;
				str_nums += ',';	//nums are seperated by ','
				str_num_temp = "";
				str_chs += equas[i];
			}
		}
		String nums[] = str_nums.split(",");
		result += Double.valueOf(nums[0]);
		for(int i = 0; i < str_chs.length()-1;) {
			switch (str_chs.toCharArray()[i]) {
			case '+':
				result += Double.valueOf(nums[++i]);
				break;
			case '-':
				result -= Double.valueOf(nums[++i]);
				break;
			case '*':
				result *= Double.valueOf(nums[++i]);
				break;
			case '/':
				result /= Double.valueOf(nums[++i]);
				break;
			}
		}
	}
	public Calculator() {
			init();
	}
	public void init(){
		MyFrame frame = new MyFrame("Calculator");
		frame.setSize(405,542);	//Win10 Calculator's Size
		frame.setLocation(300, 200);
		frame.setLocationRelativeTo(null);
		
        JPanel panle = new JPanel();
        panle.setBackground(Color.GRAY);
        panle.setFont(new Font("Arial", Font.BOLD, 20));
        panle.setPreferredSize(new Dimension(100,100));
        panle.setLayout(new BorderLayout());
        l_euqation.setHorizontalAlignment(SwingConstants.LEFT);
        l_result.setHorizontalAlignment(SwingConstants.RIGHT);
        panle.add(l_euqation,BorderLayout.NORTH);
        panle.add(l_result,BorderLayout.CENTER);
        JPanel panle_2 = new JPanel();
        panle_2.setLayout(new GridLayout(6 , 4 , 5, 5));
        panle_2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        //first row
        panle_2.add(new MyButton("%"));
        panle_2.add(new MyButton("√"));
        panle_2.add(new MyButton("x²"));
        panle_2.add(new MyButton("1/x"));
        //second row
        panle_2.add(new MyButton("CE"));
        panle_2.add(new MyButton("C"));
        panle_2.add(new MyButton("←"));
        panle_2.add(new MyButton("/"));
        //third row
        for(int i = 7; i <= 9 ; i++) {
        	panle_2.add(new MyButton(i+""));
        }
        panle_2.add(new MyButton("*"));
        //forth row
        for(int i = 4; i <= 6 ; i++) {
        	panle_2.add(new MyButton(i+""));
        }
        panle_2.add(new MyButton("-"));
        //fifth row
        for(int i = 1; i <= 3 ; i++) {
        	panle_2.add(new MyButton(i+""));
        }
        panle_2.add(new MyButton("+"));
        //six row
        panle_2.add(new MyButton("±"));
        panle_2.add(new MyButton(0+""));
        panle_2.add(new MyButton("."));
        panle_2.add(new MyButton("="));
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panle,BorderLayout.NORTH);
        frame.getContentPane().add(panle_2,BorderLayout.CENTER);
        frame.setVisible(true);
	}
}


class MyButton extends JButton{
	private String command;
	private MyLabel l_result = Calculator.l_result;
	private MyLabel l_equation = Calculator.l_euqation;
	private static final long serialVersionUID = -4881016763072413392L;
	public void changeColor() {
		if(command.length() == 1 && Character.isDigit(command.toCharArray()[0])) {
			this.setBackground(Color.WHITE);
		}else {
			this.setBackground(Color.LIGHT_GRAY);
		}
	} //it can be used to change && setColor of the button

	public MyButton(String command) {
		super(command);
		this.command = command;
		this.setFont(new Font("Microsoft YaHei", Font.BOLD,16));
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent e) {
				changeColor();
			}
			public void mouseClicked(MouseEvent e) {
				MyButton bt = (MyButton)e.getComponent();
				String str = bt.getCommand();
				if(str.equals("C")) {
					l_equation.updateMessage("Equation:");
					l_result.updateMessage("Result:0.0");
					Calculator.result = 0;
					return ;
				}else if(str.equals("←") || str.equals("CE")) {
					l_equation.removeLast();
					Calculator.calculate(l_equation.getContext().split(":")[1]);
					l_result.updateResult();
					return ;
				}else if(str.equals("%")) {
					if(Calculator.result == 0) {
						return ;
					}else {
						Calculator.result /= 100;
						l_result.updateResult();
						return ;
					}
				}else if(str.equals("√")) {
					if(Calculator.result == 0) {
						return ;
					}else {
						Calculator.result = Math.sqrt(Calculator.result);
						l_result.updateResult();
						return ;
					}
				}else if(str.equals("x²")) {
					if(Calculator.result == 0) {
						return ;
					}else {
						Calculator.result = Math.pow(Calculator.result, 2);
						l_result.updateResult();
						return ;
					}
				}else if(str.equals("1/x")) {
					if(Calculator.result == 0) {
						return ;
					}else {
						Calculator.result = 1/Calculator.result;
						l_result.updateResult();
						return ;
					}
				}else if(str.equals("±")) {
					if(Calculator.result == 0) {
						return ;
					}else {
						Calculator.result = -Calculator.result;
						l_result.updateResult();
						return ;
					}
				}else {
					l_equation.appendContext(str);
					String curEquation = l_equation.getContext().split(":")[1];
					Pattern p = Pattern.compile(".*=.");
					if(curEquation.indexOf('=')!=curEquation.length()-1 && p.matcher(curEquation).matches()) {
						l_equation.updateMessage("Equation:");
						l_result.updateMessage("Result:0.0");
						return ;
					}else {
						Calculator.calculate(curEquation);
						l_result.updateResult();
					}
				}
			}
		});
		changeColor();
	}
	public String getCommand() {
		return command;
	}
}

class MyLabel extends JLabel{
	private static final long serialVersionUID = -1492359209978836710L;
	private String str_context;
	public MyLabel(String text) {
		super(text);
		str_context = text;
		this.setFont(new Font("Arial", Font.BOLD,16));
	}
	public void updateResult() {
		str_context = "Result:"+Calculator.result;
		this.setText(str_context);
	}
	public void updateMessage(String msg) {
		str_context = msg;
		this.setText(str_context);
	}
	public void appendContext(String context) {
		String new_str = str_context+context;
		this.setText(new_str);
		str_context = new_str;
	}
	public String getContext() {
		return str_context;
	}
	public void removeLast() {
		str_context = str_context.substring(0, str_context.length()-1);
		this.setText(str_context);
	}
}

class MyFrame extends JFrame{
	private static final long serialVersionUID = -4367247892391765625L;
	public MyFrame(String name){
		super(name);
		super.setResizable(false);	//Cannot Resize it!!
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
