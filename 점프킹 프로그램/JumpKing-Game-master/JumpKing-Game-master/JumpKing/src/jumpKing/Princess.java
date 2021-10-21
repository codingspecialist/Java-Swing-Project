package jumpKing;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

// ���� Ŭ�����Դϴ�.
// �����尡 ���鼭 �̹����� ��ȯ�˴ϴ�.
@Data
public class Princess extends JLabel {
	public Princess princess = this; // �÷��̾� ���ؽ�Ʈ
	private final static String TAG = "princess"; 
	private Thread princessActivity;
	//���� �̹���
	private ImageIcon icPrincess1,icPrincess2,icPrincess3,icPrincess4,icPrincess5,icPrincess6,icPrincess7,icPrincess8;
//	private  int princessX = 180; // ĳ���� �⺻ ���� X��
//	private  int princessY = 95; // ĳƽ�� �⺻ ���� Y��
	
	private  int princessX = 600; // ĳ���� �⺻ ���� X��
    private  int princessY = 300; // ĳƽ�� �⺻ ���� Y�� 
	
	public Princess() {
		init();
		setIcon(icPrincess1);
		setSize(70, 70); // ������� ũ��
		setLocation(princessX, princessY); // �⺻ ������ġ
	}

	void init() {
		// �����̹���
		icPrincess1 = new ImageIcon("images/icPrincess1.png");
		icPrincess2 = new ImageIcon("images/icPrincess2.png");
		icPrincess3 = new ImageIcon("images/icPrincess3.png");
		icPrincess4 = new ImageIcon("images/icPrincess4.png");
		icPrincess5 = new ImageIcon("images/icPrincess5.png");
		icPrincess6 = new ImageIcon("images/icPrincess6.png");
		icPrincess7 = new ImageIcon("images/icPrincess7.png");
		icPrincess8 = new ImageIcon("images/icPrincess8.png");
		
		princessActivity = new Thread(new PrincessActivity());
		princessActivity.start();
	};
		
	class PrincessActivity implements Runnable{

		@Override
		public void run() {
			while(true) {
				try {
					setIcon(icPrincess1);
					Thread.sleep(300);
					setIcon(icPrincess2);
					Thread.sleep(300);
					setIcon(icPrincess3);
					Thread.sleep(300);
					setIcon(icPrincess4);
					Thread.sleep(300);
					setIcon(icPrincess5);
					Thread.sleep(300);
					setIcon(icPrincess6);
					Thread.sleep(300);
					setIcon(icPrincess7);
					Thread.sleep(300);
					setIcon(icPrincess8);
					Thread.sleep(300);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		
	}
	
}
