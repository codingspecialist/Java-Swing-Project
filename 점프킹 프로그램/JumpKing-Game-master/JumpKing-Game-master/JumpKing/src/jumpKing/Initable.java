package jumpKing;

// 인잇에이블 입니다.
// 클래스 생성시 편하게 관리하기위해 사용했습니다.
// 인터페이스로 강제성을 부여합니다.
public interface Initable {
void init(); // new
void setting(); // JFrame 기본 셋팅
void batch(); // 화면 구성
void listener(); // 리스너 
}
