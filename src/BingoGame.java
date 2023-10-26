import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

//빙고 게임
public class BingoGame {
	public static void main(String[] args) throws Exception {
		new Bingo();
	}
}

class Bingo{
	JFrame frm = new JFrame("Bingo");
	JButton[][] btn = new JButton[5][5];
	int[][] bingo = new int[5][5];
	int[][] computer = new int[5][5];
    int[] noti = {6, 6};
    int C_input, H_input, C_bingo, H_bingo, a = 0, b = 0;
    int[] count = new int[2];
    
	int value=0;
	int result = 1;
	
	Calendar start = Calendar.getInstance();
	
	public Bingo() throws Exception{
		frm.setLayout(new GridLayout(5,5,5,5));
		frm.setBounds(100, 100, 300, 300);
  	 
		Set com = new LinkedHashSet();// computer bingo
		
		for(int i=0; com.size()<25; i++){//1~25 ramdom value
        	com.add((int)(Math.random()*25)+1+"");
        }
		
		Iterator it = com.iterator();
		
		for(int i=0; i<computer.length; i++){//computer input value
        	for(int j=0; j<computer.length; j++){
        		computer[i][j]=Integer.parseInt((String) it.next());
        	}
        }
		
		Set men = new LinkedHashSet();// human bingo
	       
        for(int i=0; men.size()<25; i++){//1~25 ramdom value
        	men.add((int)(Math.random()*25)+1+"");
        }
		  
        it = men.iterator();
        
        for(int i=0; i<bingo.length; i++){//bingo input value
        	for(int j=0; j<bingo.length; j++){
        		bingo[i][j]=Integer.parseInt((String) it.next());
        	}
        }
        
        C_input = computer[computer.length / 2][computer.length / 2];
        computer[computer.length / 2][computer.length / 2] = 0;

        for (int i = 0; i < bingo.length; i ++) {
            for (int j = 0; j < bingo.length; j ++) {
                if (bingo[i][j] == C_input) 
                    bingo[i][j] = 0;
            }
        }
        ///////////////////////////////////////////////////
        
		for(int i=0; i<bingo.length; i++){
			for(int j=0; j<bingo.length; j++){
				if(bingo[i][j] == 0)
					btn[i][j]= new JButton("");
				else {
					btn[i][j]= new JButton(String.valueOf(bingo[i][j]));
				}
				btn[i][j].setUI(new StyledButtonUI());
				frm.add(btn[i][j]);
		  	}
		}
		event();
		frm.setVisible(true);
	}
	
	
	
	public void inputFrame(){
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				if(bingo[i][j] == 0)
					btn[i][j].setText("");
				else {
					btn[i][j].setText(String.valueOf(bingo[i][j]));
				}
			}
		}
	}
	
	public int value() {
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				btn[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						try {
						value = Integer.valueOf(e.getActionCommand());
						}catch(Exception valuee) {}
					}
				});
			}
		}
		return value;
	}
	
	public void event() {
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				btn[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						try {
							value = Integer.valueOf(e.getActionCommand());
							cal();
							inputFrame();
						}catch(Exception ae) {}
					}
				});
			}
		}
	}
	
	public void cal() {
		
		// human input
        for (int i = 0; i < bingo.length; i ++) {
            for (int j = 0; j < bingo.length; j ++) {
                if (bingo[i][j] == value()) 
                    bingo[i][j] = 0;
            }
        }
        
        // Human 결과값 계산식
        H_bingo = 0;
        C_bingo = 0;
        
        for (int i = 0, num3 = 0, num4 = 0; i < bingo.length; i ++) {
            for (int j = 0, num1 = 0, num2 = 0; j < bingo.length; j ++) {
                if (bingo[i][j] == 0)  // 가로 빙고
                    num1 += 1;
                
                if (num1 >= 5) 
                    H_bingo += 1;
                
                if (bingo[j][i] == 0)  // 세로 빙고
                    num2 += 1;
                
                if (num2 >= 5) 
                    H_bingo += 1;
                
                if (bingo[i][j] == 0 && i == j)  // 왼쪽 대각선 빙고
                    num3 += 1;
                
                if (bingo[i][j] == 0 && j == 4 - i)  // 오른쪽 대각선 빙고
                    num4 += 1;
            }
            if (num3 >= 5) 
                H_bingo += 1;
            
            if (num4 >= 5) 
                H_bingo += 1;
        }
        
        // computer human input
        for (int i = 0; i < computer.length; i ++) {
            for (int j = 0; j < computer.length; j ++) {
                if (computer[i][j] == value()) 
                    computer[i][j] = 0;
            }
        }
        
        comcal();
        
        // 결과값 계산식
        C_bingo = 0;
        for (int i = 0, num3 = 0, num4 = 0; i < bingo.length; i ++) {
            for (int j = 0, num1 = 0, num2 = 0; j < bingo.length; j ++) {
                if (computer[i][j] == 0)  // 가로 빙고
                    num1 += 1;
                
                if (num1 >= 5) 
                    C_bingo += 1;
                
                if (computer[j][i] == 0)  // 세로 빙고
                    num2 += 1;
                
                if (num2 >= 5) 
                    C_bingo ++;
                
                if (computer[i][j] == 0 && i == j)  // 왼쪽 대각선 빙고
                    num3 += 1;
                
                if (computer[i][j] == 0 && j == 4 - i)  // 오른쪽 대각선 빙고
                    num4 += 1;
                
            }
            if (num3 >= 5) 
                C_bingo += 1;
            
            if (num4 >= 5) 
                C_bingo += 1;
            
        }
        
        // 결과값 계산식
        H_bingo = 0;
        for (int i = 0, num3 = 0, num4 = 0; i < bingo.length; i ++) {
            for (int j = 0, num1 = 0, num2 = 0; j < bingo.length; j ++) {
                if (bingo[i][j] == 0)  // 가로 빙고
                    num1 += 1;
                
                if (num1 >= 5) 
                    H_bingo += 1;
                
                if (bingo[j][i] == 0)  // 세로 빙고
                    num2 += 1;
                
                if (num2 >= 5) 
                    H_bingo += 1;
                
                if (bingo[i][j] == 0 && i == j)  // 왼쪽 대각선 빙고
                    num3 += 1;
                
                if (bingo[i][j] == 0 && j == 4 - i)  // 오른쪽 대각선 빙고
                    num4 += 1;
                
            }
            if (num3 >= 5) 
                H_bingo += 1;
            
            if (num4 >= 5) 
                H_bingo += 1;
            
        }
        
    // bingo result
        if (H_bingo >= 3) {
        	for (int i = 0; i < bingo.length; i ++) {
        		for (int j = 0; j < bingo.length; j ++) {
        			if (bingo[i][j] == 0) 
        				System.out.print("□  ");
        			else 
        				System.out.printf("%-3d", bingo[i][j]);
            
        		}
            System.out.println();
        }
    	Calendar humantime = Calendar.getInstance();
		JOptionPane.showMessageDialog( frm, "Number Bingo Your Winner!!\n"+"경과시간 : "+Math.abs(start.getTimeInMillis()-humantime.getTimeInMillis())/1000+"초");
		System.exit(result);
        System.out.println("-----------Number Bingo Your Winner!!-----------");
        } else if (C_bingo >= 3) {
        	for (int i = 0; i < computer.length; i ++) {
        		for (int j = 0; j < computer.length; j ++) {
        			if (computer[i][j] == 0) 
        				System.out.print("□  ");
        			else 
        				System.out.printf("%-3d", computer[i][j]);
                
        		}
        		System.out.println();
        	}
        Calendar humantime = Calendar.getInstance();
		JOptionPane.showMessageDialog( frm, "Number Bingo Your Loserㅠㅠ Computer Winner\n"+"경과시간 : "+Math.abs(start.getTimeInMillis()-humantime.getTimeInMillis())/1000+"초");
		System.exit(result);
        System.out.println("-----------Number Bingo Your Loserㅠㅠ Computer Winner-----------");
    
        }
	}
	public void comcal() {
		// Computer AI
        int max = 0;
        if (count[0] >= 5) 
            count[0] = 0;
        
        if (count[1] >= 5) 
            count[1] = 0;
        
        for (int i = 0; i < 5; i ++) {
            for (int j = 0, n1 = 0, n2 = 0; j < 5; j ++) {
                if (computer[i][j] == 0) // 가로 증가
                    n1 += 1;
                
                if (computer[j][i] == 0) // 세로 증가
                    n2 += 1;
                //noti[0] 가로 noti[1] 세로
                if (n1 >= 5) {
                    noti[0] = i;
                    count[0] = 0;
                }
                
                if (n2 >= 5) {
                    noti[1] = i;
                    count[1] = 0;
                }
            }
        }
        
        int c3 = 0,
        c4 = 0;
        for (int i = 0; i < computer.length; i ++) {
            for (int j = 0, c1 = 0, c2 = 0; j < computer.length; j ++) {
                if (noti[0] != i) {
                    if (computer[i][j] == 0) { // 가로
                        c1 += 1;
                        if (c1 == 4) 
                            noti[0] = i;
                        
                        if (c1 > count[0]) {
                            count[0] = c1;
                            a = i;
                        }
                    }
                }
                if (noti[1] != i) {
                    if (computer[j][i] == 0) { // 세로
                        c2 += 1;
                        if (c2 == 4) 
                            noti[1] = i;
                        
                        if (c2 > count[1]) {
                            count[1] = c2;
                            b = i;
                        }
                    }
                }
                if (count[0] < count[1]) 
                    max = 1;
                
                if (count[0] > count[1]) 
                    max = 0;
                
                if (computer[i][j] == 0 && i == j)  // 왼쪽 대각선 빙고
                    c3 ++;
                
                if (computer[i][j] == 0 && j == 4 - i)  // 오른쪽 대각선 빙고
                    c4 ++;
                
            }
        }
        for (int i = 0; i < 5; i ++) { // if(count[0]>count[1])
            if (computer[a][i] != 0 && max != 1) { // 가로
                C_input = computer[a][i];
                break;
            }
            if (computer[i][b] != 0 && max == 1) { // 세로
                C_input = computer[i][b];
                break;
            }
        }
        for (int i = 0; i < 5; i ++) {
            if (computer[i][i] != 0 && c3 >= 4) { // 왼쪽 대각선
                C_input = computer[i][i];
                break;
            }
            if (computer[i][4 - i] != 0 && c4 >= 4) { // 오른쪽 대각선
                C_input = computer[i][4 - i];
                break;
            }
        }
        for (int i = 0; i < computer.length; i ++) {
            for (int j = 0; j < computer.length; j ++) {
                if (computer[i][j] == C_input) {
                    computer[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < bingo.length; i ++) {
            for (int j = 0; j < bingo.length; j ++) {
                if (bingo[i][j] == C_input) 
                    bingo[i][j] = 0;
                
            }
        }
	}
}

class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }
    
    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Impact", Font.PLAIN, 18));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0x2dce98));
        g.fillRoundRect(0, yOffset, size.width, size.height, 50, 50);
    }
}