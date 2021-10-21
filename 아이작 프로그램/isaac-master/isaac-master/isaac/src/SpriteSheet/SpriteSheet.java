package SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

@Data

// 이클래스 자체가 스프리트 이미지를 가져와 내가 사용할 이미지를 imgObject에 담는다.
public class SpriteSheet extends JLabel {
	private final static String TAG = "SpriteSheet : ";	
    private BufferedImage imgSprite;	// 객체 이미지, 스프리트시트 담는다.
    private String url;			// 이미지 url-> 경로
    private String gubun;		// 구분 (이 스프리트가 하는 일 또는 구분)
    private int xPos, yPos;		// 이미지 위치 x, y 좌표 이미지를 처음에 (x, y, width, height ) 중에서 x, y 를 의미
    private int width, height;	// 이미지 가로 세로 길이 (x, y, width, height ) 중에서 width, height 를 의미
	private ImageIcon imgObject;	// 스프리트 이미지에서 사용할 부분을 잘라서 가지고 온 이미지

	public SpriteSheet() {}

	public SpriteSheet(String url, String gubun, int xPos, int yPos, int width, int height) {
		this.url = url;
		this.gubun = gubun;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		loadSpriteImage(url);	// imgSprite에 값을 초기화한다.
	} 
	public void loadSpriteImage(String url) {
		try {
			imgSprite = ImageIO.read(new File("images/" + url));
		} catch (Exception e) {
			System.out.println(TAG + "이미지 로드 실패");
		}	
	}
	
	public BufferedImage getObjectImage() {
		return imgSprite.getSubimage(xPos, yPos, width, height);
	}
	
	// 자른 이미지를 화면에 그림.
    public void drawObject(int x, int y) {
    	imgObject = new ImageIcon(getObjectImage());
		setIcon(imgObject);
		setSize(width, height);	// 라벨에 대한 사이즈
		setLocation(x, y);
//		System.out.println(TAG + gubun + "그려짐");
    	
    }



	
}
