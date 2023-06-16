package j20230614;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class L02AnonymousClass {
	public static void main(String[] args) {
      JFrame f=new JFrame("버튼 이벤트 리스너 테스트");
      f.setBounds(0, 0, 300, 300);
      JButton btn=new JButton("버튼");
      f.add(btn,BorderLayout.NORTH);
      JLabel label=new JLabel("라벨");
      f.add(label);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setVisible(true);
	
//    btn.addActionListener(new ActionListener() {
//    	@Override
//    	public void actionPerformed(ActionEvent e) {
//    		label.setText("안녕");
//    	}
//    });
    // 개발자의 불만!! 이것도 너무 길고 불편해!! 함수형 언어는 함수만 매개변수로 사용하던데 너무 불편하다!!
    // 람다식 : 함수가 1개 있는 인터페이스를 마치 함수를 매개변수로 사용하는 것처럼 작성가능!
    // 익명클래스와 아주 유사하게 컴팡일러가 구현하지만 클래스를 미리 만들어진 것을 사용하기 때문에 익명클래스가 없다.
    // 함수형언어인척 하는 것 : 문법적 설탕
    btn.addActionListener((e) -> {
    	label.setText("람다식 안녕");
    });
    f.add(label);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setVisible(true);
	}
}
