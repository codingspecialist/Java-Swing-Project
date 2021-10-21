package jumpKing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// �ȼ�üũ Ŭ�����Դϴ�.
// �÷��̾��� ��ġ������ ���� �Ǵ����ݴϴ�
public class PixelCheck implements Runnable {
	private int playerX, playerY, playerY2, playerX3, playerY3, playerX4, playerY4, playerX5,playerY5;
	private int red1, green1, blue1, alpha1;// RGB,���� ���� //������ �Ⱦ� ����� �־���°�
	private int red2, green2, blue2 ;// RGB ����
	private int red3, green3, blue3 ;// RGB ����
	private int red4, green4, blue4 ;// RGB ����
	private int red5, green5, blue5 ;// RGB ����
	

	private BufferedImage image; // �̹���
	private Player player; // �÷��̾� ���ؽ�Ʈ ���
	private JumpKingApp jumpKingApp; // ����� ���ؽ�Ʈ ���

	public PixelCheck(Player player, JumpKingApp jumpKingApp) {
		this.player = player; //�÷��̾� ���ؽ�Ʈ ���
		this.jumpKingApp = jumpKingApp; // ����� ���ؽ�Ʈ ���
		try {
			this.image = ImageIO.read(new File("images/1StageBW.png")); // ��׶��� �̹���
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {

			try {
				// ���ϰ��
//				int x = image.getWidth(null); // �̹��� ���� ��ü 
//				int y = image.getHeight(null); // �̹��� ���� ��ü

				playerX = player.getX() + 25; // ���ٴ� �ν���ǥ
				playerY = player.getY() + 60;//

				playerY2 = player.getY() + 55; // ���ٴ� �ν���ǥ2

				playerX3 = player.getX(); // ���� �ν���ǥ
				playerY3 = player.getY() + 40; 

				playerX4 = player.getX() + 55; // ���� �ν���ǥ
				playerY4 = player.getY() + 40;
				
				playerX5 = player.getX() + 25; // �Ӹ� �ν���ǥ
				playerY5 = player.getY();
				

				Color color1 = new Color(image.getRGB(playerX, playerY));// �ش���ǥ �÷����
				Color color2 = new Color(image.getRGB(playerX, playerY2)); // ���� ���� �÷���ǥ //������ �Ǻ�
				Color color3 = new Color(image.getRGB(playerX3, playerY3));// ���� ���� ���� // �Ķ��� �Ǻ�
				Color color4 = new Color(image.getRGB(playerX4, playerY4));// ���� ���� ���� // �Ķ��� �Ǻ�
				Color color5 = new Color(image.getRGB(playerX5, playerY4));// ���� ���� ���� // �Ķ��� �Ǻ�
				

				// alpha = color.getAlpha(); // ���� ����

				red1 = color1.getRed(); // ������ ����
				green1 = color1.getGreen();// �ʷϻ� ����
				blue1 = color1.getBlue(); // �Ķ��� ����

				red2 = color2.getRed(); // ������ ����
				green2 = color2.getGreen();// �ʷϻ� ����
				blue2 = color2.getBlue(); // �Ķ��� ����

				red3 = color3.getRed(); // ������ ����
				green3 = color3.getGreen();// �ʷϻ� ����
				blue3 = color3.getBlue(); // �Ķ��� ����

				red4 = color4.getRed(); // ������ ����
				green4 = color4.getGreen();// �ʷϻ� ����
				blue4 = color4.getBlue(); // �Ķ��� ����
				
				red5 = color5.getRed(); // ������ ����
				green5 = color5.getGreen();// �ʷϻ� ����
				blue5 = color5.getBlue(); // �Ķ��� ����


//				System.out.println(" X ��ǥ : " +playerX + " Y ��ǥ : " + playerY); //���� �÷��̾��� ��ġ ���
//				System.out.println("���� : "+red+" ���� : "+ green + "�Ķ� : " + blue); // �������
				
				
				if (red1 == 255 && green1 == 255 && blue1 == 255) { //���ٴ��� �ƴϸ� [���] �Ʒ��� ������
					player.setMoveLockLeft(false);
					player.setMoveLockRight(false);
					player.Gravity = true;
//					System.out.println("���");

				} else if (red1 == 0 && green1 == 0 && blue1 == 0) { // ���ٴ� IF��
					if (red2 == 0 && green2 == 0 && blue2 == 0) {// �����̸� ���ٴ����� �÷��ִ� IF��
						player.setPlayerY(player.getY() - 1);
//						System.out.println("�����̴�");	
					} else {//���ٴ��̸� �������� �ʰ�, ��,�� �̵������ϰ� Lock ����
						player.Gravity = false;
						player.setMoveLockLeft(false);
						player.setMoveLockRight(false);
						
//						System.out.println("������");	
					}
				} 
				
				if((red3 == 0 && green3 == 0 && blue3 == 255)&&(red4 == 0 && green4 == 0 && blue4 == 255)){
					// �Ķ��������� ���� �÷���
					player.setPlayerY(player.getPlayerY()+1);
				}else if (red3 == 0 && green3 == 0 && blue3 == 255) { //������ �Ķ����̸� ���̻� ����
//					System.out.println("�۷��̴� ����");
					player.setMoveLockLeft(true); //�����̵� ���
					player.setJumpRight(2); //����������� 2����
					player.setJumpGauge(player.getJumpGauge()-30); //���������� 30����
					player.setJumpGaugeDown(player.getJumpGaugeDown()-30); // �����������ٿ� 30����
				}else	if (red4 == 0 && green4 == 0 && blue4 == 255) {//�������� �Ķ����̸� ���̻� ����
//					System.out.println("�۷��̴� ������");
					player.setMoveLockRight(true);
					player.setJumpLeft(2);
					player.setJumpGauge(player.getJumpGauge()-30);
					player.setJumpGaugeDown(player.getJumpGaugeDown()-30);
				} 
								
				 if (red5 == 0 && green5 == 0 && blue5 == 255) {// �Ӹ��κ��� �Ķ����̸� �ϰ��ϵ��� ���� 
//					System.out.println("�Ӹ�@��");
					player.setJumpGauge(0);
				}
			
				if (player.getY() < 0) { //õ���� ������ �������������� �Ѿ
					if (player.getStageCount() == 1) { // ��������ī��Ʈ�� 1�϶� �߻�
						player.setPlayerY(500); // Y�� 500���� ����
						player.setStageCount(2); // �������� ī��Ʈ2 �κ���
						jumpKingApp.setImgCount(2); // ����� �������� 2�� ����
						jumpKingApp.setSize(1080, 607); // ��� ������ ����
						
						this.image = ImageIO.read(new File("images/2StageBW.png")); // ������ 2���������� ����
					} else if (player.getStageCount() == 2) { // �������� ī��Ʈ�� 2�϶� �߻� ���� ����
						player.setPlayerY(500);
						player.setStageCount(3);
						jumpKingApp.setImgCount(3);
						jumpKingApp.creatPrincess(); //���� ����
						jumpKingApp.setSize(1080, 607);
						this.image = ImageIO.read(new File("images/3StageBW.png"));
					}
				} else if (player.getY() > 535) {// �Ʒ��� �϶��ϸ� �������� ���� �������� ��ȯ
					if (player.getStageCount() == 2) { // ��������ī���� 2�϶� �߻�
						player.setPlayerY(0); // Y�� 10���� ����
						player.setStageCount(1); // �������� ī����1
						jumpKingApp.setImgCount(1);// ����� �������� 1�� �ٲ�
						jumpKingApp.setSize(1080, 607); // ��� ������ ����
						
						this.image = ImageIO.read(new File("images/1StageBW.png"));// ������ ��������1�� ����
					} else if (player.getStageCount() == 3) {// �������� ī���� 3�϶��߻� ���� ����
						player.setPlayerY(0);
						player.setStageCount(2);
						jumpKingApp.setImgCount(2);
						jumpKingApp.removePrincess();// ���� ����
						jumpKingApp.setSize(1080, 607);
						this.image = ImageIO.read(new File("images/2StageBW.png"));
					}
				}
				Thread.sleep(1);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
