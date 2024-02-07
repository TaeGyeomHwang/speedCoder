package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import model.ScoreDAO;
import model.ScoreDTO;

public class MyInfoGraph extends JFrame{

	private JLabel lblTitle;
	private JLabel lblWordTableTitle;
	private JLabel lblBlockTableTitle;
	
	private JTable tblWord;
	private JTable tblBlock;
	
	private JLabel lblWordAvgHit;
	private JLabel lblWordAvgAcc;
	private JLabel lblBlockAvgHit;
	private JLabel lblBlockAvgAcc;
	
	private JPanel pnlGraphics;
	
	private Font fontTitle;
	private Font fontNormal;
	private Font fontAcc;
	
	private String loginedID = "code123";
	
	public MyInfoGraph() {
		this.setTitle("SPEED C( )DER - MyInfo");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.getContentPane().setLayout(null);
		this.getContentPane().add(getTitleLabel());
		this.getContentPane().add(getWordTableTitleLabel());
		this.getContentPane().add(getBlockTableTitleLabel());
//		this.getContentPane().add(getWordTable());
//		this.getContentPane().add(getBlockTable());
		this.getContentPane().add(getPnlGraphics());
//		this.getContentPane().add(getWordAvgAccLabel());		
//		this.getContentPane().add(getWordAvgHitLabel());		
//		this.getContentPane().add(getBlockAvgAccLabel());		
//		this.getContentPane().add(getBlockAvgHitLabel());		
		
		this.locationCenter();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				Main main = new Main();
				main.setVisible(true);
			}
		});
	
	}


	/* 2D 그래프 */
	// 그래프 GUI
	private JPanel getPnlGraphics() {
        if (pnlGraphics == null) {
            pnlGraphics = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawFrame(g);
                    drawMainLine(g);
                    drawRod(g, 90, 380, "word");
                }
            };
            pnlGraphics.setBounds(0, 180, 1200, 420);
        }
        return pnlGraphics;
    }
	
	// 그래프 설정
    private void drawFrame(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(50, 0, 420, 420);
        g.drawRect(520, 0, 420, 420);
    }
    
    private void drawMainLine(Graphics g) {
    	g.setColor(Color.RED);
    	g.drawLine(90, 380, 430, 380);
    	g.drawLine(90, 30, 90, 380);
    	g.drawLine(560, 380, 900, 380);
    	g.drawLine(560, 30, 560, 380);
    }
    
    private void drawRod(Graphics g, int xPosition, int yPosition, int weight, int height, String kind) {
    	
    	g.setColor(Color.BLUE);
    	
    	ScoreDAO scoreDAO = ScoreDAO.getInstance();
		List<ScoreDTO> scores = scoreDAO.getScoreByIdDesc(Login.getLoginedId(), kind);
		Object[][] rowData = new Object[10][3];
		
		int row=0;
		for(ScoreDTO scoreDTO : scores) {
			rowData[row][0] = row+1;
			rowData[row][1] = scoreDTO.getSpeed();
			rowData[row][2] = scoreDTO.getAcc();
			row++;
		}
		
//		g.fillRect(xPosition, yPosition-height, weight, height);

    }
	
	/* 라벨 */
	// 제목 라벨 설정
	private JLabel getTitleLabel() {
		if(lblTitle == null	) {
			lblTitle = new JLabel();
			lblTitle.setText("SPEED C( )DER");
			lblTitle.setBounds(360, 70, 300, 50);
			lblTitle.setFont(getTitleFont());
		}
		return lblTitle;
	}
	
	// 단어 연습 순위표 제목 라벨 설정
	private JLabel getWordTableTitleLabel() {
		if(lblWordTableTitle == null) {
			lblWordTableTitle = new JLabel();
			lblWordTableTitle.setText("단어 연습");
			lblWordTableTitle.setBounds(200, 130, 150, 40);
			lblWordTableTitle.setFont(getNormalFont());
		}
		return lblWordTableTitle;
	}
	
	// 블록 연습 순위표 제목 라벨 설정
	private JLabel getBlockTableTitleLabel() {
		if(lblBlockTableTitle == null) {
			lblBlockTableTitle = new JLabel();
			lblBlockTableTitle.setText("블록 연습");
			lblBlockTableTitle.setBounds(680, 130, 150, 40);
			lblBlockTableTitle.setFont(getNormalFont());
		}
		return lblBlockTableTitle;
	}
	
	
	
	/* 테이블 */
	// 단어 연습 순위표 테이블 설정
	private JTable getWordTable() {
		if(tblWord == null) {
			String[] columnNames = {"번호", "타수", "정확도(%)"};

			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			List<ScoreDTO> scores = scoreDAO.getScoreByIdDesc(Login.getLoginedId(), "word");
			Object[][] rowData = new Object[10][3];
			
			int row=0;
			for(ScoreDTO scoreDTO : scores) {
				rowData[row][0] = row+1;
				rowData[row][1] = scoreDTO.getSpeed();
				rowData[row][2] = scoreDTO.getAcc();
				row++;
			}
			tblWord = new JTable(rowData, columnNames);
			tblWord.setBounds(50, 220, 150, 160);
			
			//테이블 헤더 생성
			JTableHeader header = tblWord.getTableHeader();
	        header.setBounds(
	        		tblWord.getBounds().x,
	        		tblWord.getBounds().y - header.getPreferredSize().height,
	        		tblWord.getBounds().width,
	        		header.getPreferredSize().height
	        		);
	        
	        tblWord.getColumn("번호").setPreferredWidth(50);
	        tblWord.getColumn("타수").setPreferredWidth(100);
	        tblWord.getColumn("정확도(%)").setPreferredWidth(150);
	        this.getContentPane().add(header);
	        
	        //열 내용 가운데 정렬
	        for (int i = 0; i < tblWord.getColumnCount(); i++) {
	        	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        	tblWord.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
		}
		return tblWord;
	}
	
	// 블록 연습 순위표 테이블 설정
	private JTable getBlockTable() {
		if(tblBlock == null) {
			String[] columnNames = {"번호", "타수", "정확도(%)"};
			
			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			List<ScoreDTO> scores = scoreDAO.getScoreByIdDesc(Login.getLoginedId(), "block");
			Object[][] rowData = new Object[10][3];
			
			int row=0;
			for(ScoreDTO scoreDTO : scores) {
				rowData[row][0] = row+1;
				rowData[row][1] = scoreDTO.getSpeed();
				rowData[row][2] = scoreDTO.getAcc();
				row++;
			}
			tblBlock = new JTable(rowData, columnNames);
			tblBlock.setBounds(480, 220, 150, 160);
			
			//테이블 헤더 생성
			JTableHeader header = tblBlock.getTableHeader();
	        header.setBounds(
	        		tblBlock.getBounds().x,
	        		tblBlock.getBounds().y - header.getPreferredSize().height,
	        		tblBlock.getBounds().width,
	        		header.getPreferredSize().height
	        		);
	        
	        tblBlock.getColumn("번호").setPreferredWidth(50);
	        tblBlock.getColumn("타수").setPreferredWidth(100);
	        tblBlock.getColumn("정확도(%)").setPreferredWidth(150);
	        this.getContentPane().add(header);
	        
	        //열 내용 가운데 정렬
	        for (int i = 0; i < tblBlock.getColumnCount(); i++) {
	        	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        	tblBlock.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
		}
		return tblBlock;
	}
	
	/* 하단 라벨 */
	// 단어 연습 평균 타수 라벨 설정
	private JLabel getWordAvgHitLabel() {
		if(lblWordAvgHit == null) {
			lblWordAvgHit = new JLabel();
			
			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			lblWordAvgHit.setText("평균 타수     :  " + scoreDAO.getSpeedAvg(Login.getLoginedId(), "word") + "타");
			lblWordAvgHit.setBounds(48, 370, 200, 40);
			lblWordAvgHit.setFont(getAccFont());
		}
		return lblWordAvgHit;
	}
	
	// 단어 연습 평균 정확도 라벨 설정
	private JLabel getWordAvgAccLabel() {
		if(lblWordAvgAcc == null) {
			lblWordAvgAcc = new JLabel();
			
			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			lblWordAvgAcc.setText("평균 정확도  :   " + scoreDAO.getAccAvg(Login.getLoginedId(), "word") + "%");
			lblWordAvgAcc.setBounds(48, 405, 200, 40);
			lblWordAvgAcc.setFont(getAccFont());
		}
		return lblWordAvgAcc;
	}
	
	// 블록 연습 평균 타수 라벨 설정
	private JLabel getBlockAvgHitLabel() {
		if(lblBlockAvgHit == null) {
			lblBlockAvgHit = new JLabel();
			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			lblBlockAvgHit.setText("평균 타수     :  " + scoreDAO.getSpeedAvg(Login.getLoginedId(), "block") + "타");
			lblBlockAvgHit.setBounds(278, 370, 200, 40);
			lblBlockAvgHit.setFont(getAccFont());
		}
		return lblBlockAvgHit;
	}
	
	// 블록 연습 평균 정확도 라벨 설정
	private JLabel getBlockAvgAccLabel() {
		if(lblBlockAvgAcc == null) {
			lblBlockAvgAcc = new JLabel();
			ScoreDAO scoreDAO = ScoreDAO.getInstance();
			lblBlockAvgAcc.setText("평균 정확도  :   " + scoreDAO.getAccAvg(Login.getLoginedId(), "block") + "%");
			lblBlockAvgAcc.setBounds(278, 405, 200, 40);
			lblBlockAvgAcc.setFont(getAccFont());
		}
		return lblBlockAvgAcc;
	}
	
	/* 폰트 */
	// 제목 폰트 설정
		private Font getTitleFont() {
			if(fontTitle == null) {
				fontTitle = new Font("Malgun Gothic", Font.PLAIN, 40);			
			}
			return fontTitle;
		}
	
	// 보통 크기 폰트 설정
	private Font getNormalFont() {
		if(fontNormal == null) {
			fontNormal = new Font("Malgun Gothic", Font.PLAIN, 25);			
		}
		return fontNormal;
	}
	
	// 평균 폰트 설정
	private Font getAccFont() {
		if(fontAcc == null) {
			fontAcc = new Font("Malgun Gothic", Font.PLAIN, 15);			
		}
		return fontAcc;
	}
	
	//창 중앙 정렬
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	// 테스트 용 임시 실행문
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MyInfoGraph myInfoGraph = new MyInfoGraph();
			myInfoGraph.setVisible(true);
		});
	}	
}