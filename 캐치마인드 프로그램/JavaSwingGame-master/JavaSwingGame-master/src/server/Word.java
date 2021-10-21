package server;


public class Word {

	public static void main(String[] args) {
		Word wd = new Word();
		for(int I=0; I<50; I++)
			System.out.println(wd.getStr());

	}
	
	int[] rNum = new int[50];
	String wordList[] = {
			"사자", "비둘기","김경호", "고양이", "강아지", "얼룩말", "카레이서", "기린", "물고기", "사과", 	
			"파인애플", "카카오나무", "상추", "나무", "가격표", "라면", "전화","탁구", "마이크", "공중전화",	
			"샴푸", "빵", "태양", "수영", "열쇠", "바지", "부메랑", "컴퓨터","소프라노", "정장",			
			"원숭이", "공부", "선생님", "다이아몬드", "그물", "창조물","호랑이", "사람", "축구", "파리",		
			"소방관", "가수", "악기", "노래", "귓속말", "동아리", "코끼리", "게릴라", "완두콩", "산책"};		
	String[] wordQuiz = new String[50];
	String[] alreadyWord = new String[50]; 
	int num = 0;
	int flag = 0;
	
	public Word() {

		// 랜덤
		for(int I=0; I<rNum.length; I++) {
			rNum[I] = (int)(Math.random()*50)+0;
			// 중복 제거
			for(int j=0; j<I; j++) {
				if(rNum[j] == rNum[I]) {
					I--;
					break;
				}
			}
			this.wordQuiz[I] = this.wordList[rNum[I]];
		}
	
		num++;
	}
	
	public String getStr() {
		String a = wordList[rNum[flag]];
		flag++;
		return a;
	}

}