package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cookie {
	
	private Image image; // ��Ű �̹���
	
	// ��Ű�� ��ǥ�� ���� ����
	private int x = 160;
	private int y = 0;
	private int width = 80;
	private int height = 120;
	
	// ��Ű�� ���� (0�� ����, 255�� ���� ������)
	private int alpha = 255;
	
	// ��Ű�� ü��
	private int health = 1000;
	
	// ��Ű�� ���� (�Ŵ�ȭ�� ����ȭ �̱���)
	private int big = 0; // �Ŵ�ȭ ���� �ð�
	private int fast = 0; // ����ȭ ���� �ð�
	private int countJump = 0; // ���� Ƚ��
	private boolean invincible = false; // ���� ����
	private boolean fall = false; // ���� ����
	private boolean jump = false; // ���� ����
	
	public Cookie(Image image){ // �̹����� �־�����
		this.image = image;
	}
}

