package view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.UserDAO;
import model.UserDTO;

public class Signup extends JFrame{
	
	private JLabel lblTitle;
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel lblPwVerify;
	private Font ftTitle;
	private Font ftSignup;
	private JTextField txtFieldId;
	private JPasswordField pwFieldPw; 
	private JPasswordField pwFieldPwVerify; 
	private JButton btnSignup;
	private JButton btnCancel;
	
	public Signup() {
		this.setTitle("SPEED C( )DER - Signup");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		this.getContentPane().setLayout(null);
		this.getContentPane().add(getTitleLabel());

		this.getContentPane().add(getIdLabel());
		this.getContentPane().add(getIdTextField());
		
		this.getContentPane().add(getPwLabel());
		this.getContentPane().add(getPwField());
		
		this.getContentPane().add(getPwVerifyLabel());
		this.getContentPane().add(getPwVerifyField());
		
		this.getContentPane().add(getSignupBtn());
		this.getContentPane().add(getCancelBtn());
		
		this.locationCenter();
	}
	
	/* 라벨 */
	// 제목 문구 설정
	private JLabel getTitleLabel() {
		if(lblTitle == null	) {
			lblTitle = new JLabel();
			lblTitle.setText("SPEED C( )DER");
			lblTitle.setBounds(100, 80, 300, 50);
			lblTitle.setFont(getTitleFont());
		}
		return lblTitle;
	}
	
	// ID 라벨 설정
		private JLabel getIdLabel() {
			if(lblId == null) {
				lblId = new JLabel();
				lblId.setText("ID      :  ");
				lblId.setBounds(70, 170, 100, 40);
				lblId.setFont(getSignupFont());
			}
			return lblId;
		}
		
		// PW 라벨 설정
		private JLabel getPwLabel() {
			if(lblPw == null) {
				lblPw = new JLabel();
				lblPw.setText("PW     :   ");
				lblPw.setBounds(68, 220, 100, 40);
				lblPw.setFont(getSignupFont());
			}
			return lblPw;
		}
		
		// PW 검사 라벨 설정
		private JLabel getPwVerifyLabel() {
			if(lblPwVerify == null) {
				lblPwVerify = new JLabel();
				lblPwVerify.setText("PW 확인 : ");
				lblPwVerify.setBounds(50, 270, 100, 40);
				lblPwVerify.setFont(getSignupFont());
			}
			return lblPwVerify;
		}
	
	/* 폰트 */
	// 제목 폰트 설정
		private Font getTitleFont() {
			if(ftTitle == null) {
				ftTitle = new Font("Malgun Gothic", Font.PLAIN, 40);			
			}
			return ftTitle;
		}
	
	// 제목 폰트 설정
	private Font getSignupFont() {
		if(ftSignup == null) {
			ftSignup = new Font("Malgun Gothic", Font.PLAIN, 20);			
		}
		return ftSignup;
	}
	
	// 아이디 텍스트 필드
		private JTextField getIdTextField() {
			if(txtFieldId == null) {
				txtFieldId = new JTextField();
			}
			txtFieldId.setBounds(150, 170, 250, 40);
			return txtFieldId;
		}
		
		// 비밀먼호 패스워드 필드
		private JPasswordField getPwField() {
			if(pwFieldPw == null) {
				pwFieldPw = new JPasswordField();
			}
			pwFieldPw.setBounds(150, 220, 250, 40);
			return pwFieldPw;
		}
		
		// 비밀먼호 검사 패스워드 필드
		private JPasswordField getPwVerifyField() {
			if(pwFieldPwVerify == null) {
				pwFieldPwVerify = new JPasswordField();
			}
			pwFieldPwVerify.setBounds(150, 270, 250, 40);
			return pwFieldPwVerify;
		}
	
	/* 버튼 */
	// 회원가입 버튼
	private JButton getSignupBtn() {
		if (btnSignup == null) {
			btnSignup = new JButton();
			btnSignup.setText("회원가입");
			btnSignup.setBounds(155, 320, 110, 40);
			btnSignup.addActionListener(e -> {
				SignupFn();
			});
		}
		return btnSignup;
	}

	// 나가기 버튼
	private JButton getCancelBtn() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("취소");
			btnCancel.setBounds(285, 320, 110, 40);
			btnCancel.addActionListener(e -> {
				dispose();
				Login login = new Login();
				login.setVisible(true);
			});
		}
		return btnCancel;
	}
	
	//회원가입 버튼 기능 
	private void SignupFn() {
		
		boolean idCheck=false;	//아이디가 존재하면 true 값 가짐.
		String id = txtFieldId.getText();
		char[] pw = pwFieldPw.getPassword();
		char[] pwVerify = pwFieldPwVerify.getPassword();
		
		UserDAO signupDAO = UserDAO.getInstance();
		List<UserDTO> signups = signupDAO.getSignups();	//전체 회원 정보 가져오기 메소드

		//입력받은 아이디가 중복이거나 null일 경우 idCehck 변수 true 설정
		for(UserDTO signupDTO : signups){
			if(id.equals(signupDTO.getId())||id.equals(null)) {	
				idCheck = true;
			}
		}
		if(idCheck==true) {
			JOptionPane.showMessageDialog(Signup.this, "해당 아이디는 사용할 수 없습니다.");
		}else {	//아이디 검증 통과한 경우
			//비밀번호가 입력되었으면서 검증란과 동일한 경우 if 문, 아닌 경우 else
			if (pw.length!=0 && Arrays.equals(pw, pwVerify)) {
/*	회원가입 기능 구현 테스트 위해 주석처리함	 */
//				SignupDTO signupDTO = new SignupDTO();
//				
//				signupDTO.setId(id);
//				String strPw = new String(pw); 
//				signupDTO.setPw(strPw);
//				
//				signupDAO.insertSignup(signupDTO);
				JOptionPane.showMessageDialog(Signup.this, "회원가입이 완료되었습니다.");
				super.dispose();
				Login login = new Login();
				login.setVisible(true);
			}else if(pw.length==0){
				JOptionPane.showMessageDialog(Signup.this, "비밀번호를 공백으로 설정할 수 없습니다.");
			}else {
				JOptionPane.showMessageDialog(Signup.this, "비밀번호와 비밀번호 확인란에 입력된 문자가 동일해야합니다.");
			}
		}
//		txtFieldId.setText("");
		pwFieldPw.setText("");		
		pwFieldPwVerify.setText("");		
	}
	
	//창 중앙 정렬
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}

}
