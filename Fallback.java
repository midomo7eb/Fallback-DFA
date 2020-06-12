
import java.util.ArrayList;
import java.util.Stack;

//T11_37_4158_Ahmed_Hossam
public class Main {
	static FallbackDfa test;
	public static void main(String[] args) {

		FallbackDfa x = new FallbackDfa("0,4,1,000;1,1,2,001;2,3,2,010;3,1,4,011;4,4,0,100#2,4");
//		FallbackDfa x = new FallbackDfa("0,1,1,000;1,4,2,001;2,2,3,010;3,4,0,011;4,2,4,100#4");
		test = x;
		Run("0110");
	}
	public static void Run(String literal) {
		String[] splitted = literal.split("");
		Stack<FDfaState> stack = new Stack<FDfaState>();
		int initialNum= 0;
		boolean stackAcc = false;
		
		FDfaState currentState = test.states.get(initialNum);
		stack.push(currentState);
		for(int i = 0;i<splitted.length;i++) {
			if(splitted[i].equals("0")) {
				initialNum = Integer.parseInt(currentState.transition0);
				currentState = test.states.get(initialNum);

			}
				
			if(splitted[i].equals("1")) {
				initialNum = Integer.parseInt(currentState.transition1);
				currentState = test.states.get(initialNum);
				}
			if(currentState.isAccepted) {
				stackAcc = true;
			}
			stack.push(currentState);
			
		}
		FDfaState popped = null;
		if(stackAcc) {
			while(!stack.isEmpty()) {
				 popped = stack.pop();
				if(popped.isAccepted) {
					break;
					
				}
			}
			System.out.println(popped.print);
			String next = literal.substring(stack.size());
			if(next.length()>0)
			Run(next);
					}
		else {
			System.out.println(stack.peek().print);
		}
//		System.out.println(currentState);
	}
static class FDfaState {
	String transition0;
	String transition1;
	boolean isAccepted = false;
	int stateNum;
	String print;
	public FDfaState(int stateNum,String tran0,String tran1,boolean isAccepted,String print) {
		this.transition0 = tran0;
		this.transition1 = tran1;
		this.isAccepted = isAccepted;
		this.stateNum = stateNum;
		this.print = print;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stateNum + " "+transition0+" "+transition1+" "+isAccepted+" "+print;
	}
}
	static class FallbackDfa{
	ArrayList<FDfaState> states;
	String[] language;
	ArrayList<FDfaState> acceptingStates;
	FDfaState q0;
	public FallbackDfa(String input) {
		this.states = new ArrayList<FDfaState>();
		this.acceptingStates = new ArrayList<FDfaState>();
		String[] sepStates = input.split(";");
		String[] sepAccept = sepStates[sepStates.length-1].split("#");
		createStates(sepStates,sepAccept[1]);
	}
	public void createStates(String[] states,String accept){
		boolean acceptState = false;
		for(int i = 0;i<states.length;i++) {
			String[] nums = states[i].split(",");
			String[] sepAccept = accept.split(",");
			for(int j = 0;j<sepAccept.length;j++) {
				if(nums[0].equals(sepAccept[j])) {
					acceptState = true;
					break;
				}
			}
			
			String[] splitLast = nums[3].split("#");
			FDfaState x = new FDfaState(Integer.parseInt(nums[0]),nums[1],nums[2],acceptState,splitLast[0]);
			if(acceptState) {
				acceptingStates.add(x);
			}
			acceptState = false;
			if(nums[0].equals("0")) {
				this.q0 = x;
			}
			this.states.add(x);
		}
//		for(int i = 0;i<this.states.size();i++) {
//			System.out.println(this.states.get(i));
//		}
	}
	
}
}






