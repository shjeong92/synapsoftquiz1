import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Laddergame {

	String temp_x = "";
	String temp_y = ""; 
	Integer cur_Lv = 0;
	Integer LL = 1, RR =0; 
	Integer L_min = 0, R_min = 0;
	Integer picked_Line=0;
	
	
	ArrayList <Integer>list = new ArrayList<Integer>();
	ArrayList <Integer>left = new ArrayList<Integer>();
	ArrayList <Integer>right = new ArrayList<Integer>();
	
	public int nextLv(int pline) {
		
		if(LL < pline && pline < RR) { // 왼쪽 오른쪽 비교가 필요한경우
			

			
			for(int i=1;i<list.size();i+=2) {
				
				if(list.get(i) == pline -1 && list.get(i-1) > cur_Lv) { //왼쪽값 리스트에 넣기.
					left.add(list.get(i-1));		
				}
			
				if(list.get(i) == pline && list.get(i-1) > cur_Lv) { //오른쪽값 리스트에넣기.
						right.add(list.get(i-1));
				}			
				
			}

			if(left.size()!=0)//사다리 정보를 순서대로 입력하지 않았을경우도 돌아가기위해 정렬하기.
			{
				Collections.sort(left);
				L_min = left.get(0);
			}
			if(right.size()!=0)
			{
				Collections.sort(right);
				R_min = right.get(0);
			}
			
			
			if(left.size()!=0 && right.size()!=0) {  //비교할 Left 값과 Right값이 있는경우. 
				if(L_min>R_min) {
					picked_Line+=1;
					cur_Lv=right.get(0);
					left.clear();
					right.clear();
					System.out.println("오른쪽으로! line="+picked_Line+" L_min="+L_min +" R_min="+R_min);
					return nextLv(picked_Line); //재귀함수
				}
				else {
					picked_Line-=1;
					cur_Lv=left.get(0);
					left.clear();
					right.clear();
					System.out.println("왼쪽으로! line="+picked_Line+" L_min="+L_min +" R_min="+R_min);
					return nextLv(picked_Line); //재귀함수
				}
			}
			else if(left.size()==0 && right.size()!=0) { // Left값 없고 오른쪽길은 있을
				picked_Line+=1;
				cur_Lv=right.get(0);
				left.clear();
				right.clear();
				System.out.println("오른쪽만 갈수있을때!"+picked_Line);
				return nextLv(picked_Line); //재귀함수
			}
			else if(left.size()!=0 && right.size()==0) { // Right 값 없고 왼쪽길은 있을때
				picked_Line-=1;
				cur_Lv=left.get(0);
				left.clear();
				right.clear();
				System.out.println("왼쪽만 갈수있을때!"+picked_Line);
				return nextLv(picked_Line); //재귀함수
			}
			else //길이 없을때
				return pline;
			
		}
		
		else if(pline == LL) { //picked_Line이 제일 왼쪽일경우
			for(int i=1;i<list.size();i+=2 ) {
				if(list.get(i)==pline && list.get(i-1) > cur_Lv) {
					right.add(list.get(i-1));
				}
			}
			
			if(right.size()==0) {
				return pline;
			}
			else
			{
				cur_Lv=right.get(0);
				picked_Line=pline+1;
				right.clear();
				System.out.println("제일왼쪽에서 오른쪽으로" );
				return nextLv(picked_Line); //재귀함수
			}
		}
		else { //picked_Line이 제일 오른쪽일경우
			for(int i=1;i<list.size();i+=2 ) {
				if(list.get(i)==pline-1 && list.get(i-1) > cur_Lv) {
					left.add(list.get(i-1));
				}
			}
			
			if(left.size()==0) {
				return pline;
			}
			else
			{
				cur_Lv=left.get(0);
				picked_Line=pline-1;
				left.clear();
				System.out.println("제일오른쪽에서 왼쪽으로");
				return nextLv(picked_Line); //재귀함수
			}
		}
			
		

	}
	
	public static void main(String[] args) {
		Laddergame t1 = new Laddergame();
		Scanner sc = new Scanner(System.in); 
		Integer result = 0;
		System.out.println("x를 입력하면 종료됩니다.");
		
		while(true) { //"x"를 입력받을때까지 사다리 정보 입력받기.

			t1.temp_x = sc.next();
			
			if(t1.temp_x.equals("x")) {
				
				break;
			}
			t1.temp_y = sc.next();
			
			if(t1.RR < Integer.parseInt(t1.temp_y)+1) { //제일 오른쪽 줄번호 구하기.
				t1.RR = Integer.parseInt(t1.temp_y)+1;
			}
			t1.list.add(Integer.parseInt(t1.temp_x));
			t1.list.add(Integer.parseInt(t1.temp_y));
			
			System.out.println("입력된x="+t1.temp_x+"입력된y="+t1.temp_y);
			
			t1.temp_x="";
			t1.temp_y="";

		}
		for(int i=0;i<t1.list.size();i++) {
			System.out.println(t1.list.get(i));
		}
		
		
		System.out.println("제일오른쪽 사다리는 몇번쩨?="+t1.RR);
		System.out.println("몇번재꺼 선택할꺼?");
		while(true) {
			
			
			t1.temp_x = sc.next();
			if(t1.temp_x.equals("x")) { //입력한 값이 x 일때 종료.
				System.out.println("끝");
				break;
			}
			t1.picked_Line = Integer.parseInt(t1.temp_x);
			if(1<= t1.picked_Line && t1.picked_Line <=t1.RR) {
				System.out.println(t1.picked_Line+"번재꺼 선택함");
				result = t1.nextLv(t1.picked_Line);
				System.out.println("답"+result);
				t1.cur_Lv=0; //새로운탐색을위해 초기화
			}
			else
				System.out.println("존재하지 않는 사다리번호입니다 다시입력해주세요.");
		}	
	}
}
