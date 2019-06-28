package Logic;

public class pro {
 public void intialize () {
	MasonMethod masonMethod = new MasonMethod();
	masonMethod.setSFG(Data.segmentsGains);
	Data.forwardPaths = masonMethod.getForwardPaths();
	Data.loops = masonMethod.getLoops();
	Data.nonTouchingloops = masonMethod.getNonTouchingLoops();
	Data.loopsGain = masonMethod.getLoopGains();
	Data.forwardPathsGain = masonMethod.getForwardPathGains();
	Data.nonTouchingloopsGain = masonMethod.getNonTouchingLoopGains();
	
	//ResultView result = new ResultView();
	//result.setVisible(true);
}
int[] delta;

public StringBuilder getdata () {
	Data data = new Data();
	String [] temp = data.forwardPaths;
	StringBuilder answer = new StringBuilder();
	for (int i = 0; i < temp.length; i++) {
		answer.append((i + 1) + ") " + temp[i] + "   ");
	}
	return answer;
	
}
 public StringBuilder getLoop() {
		Data data = new Data();
		String [] temp = data.loops;
		StringBuilder answer = new StringBuilder();
		for (int i = 0; i < temp.length; i++) {
			answer.append((i + 1) + ") " + temp[i]+ "   "  );
		}
		return answer;
		
	}
 public StringBuilder getNonTouchingLoop() {
		Data data = new Data();
		String [] temp = data.nonTouchingloops;
		StringBuilder answer = new StringBuilder();
		for (int i = 0; i < temp.length; i++) {
			answer.append((i + 1) + ") " + temp[i] + "   " );
		}
		return answer;
	}

 public StringBuilder getDelta() {
	 Data data = new Data();
	 String[] forward = data.forwardPaths;
	 String[] loops = data.loops;
	 int flag=0;
	 delta =  new int[forward.length];
	 StringBuilder answer = new StringBuilder();
	 for (int k=0 ; k<forward.length;k++) {
		 int ans=0;
		 for (int i=0 ; i<loops.length;i++) {
			 String [] temp = loops[i].split(" ");
			 flag =0;
			 for (int j=0; j <temp.length && flag ==0;j++) {
				 if (forward[k].contains(temp[j])) {
					 flag =1;
				 }
			 }
			 if (flag ==0) {
				 int mul=1;
				 mul=(int) (mul*data.loopsGain[i]);
				 ans=ans+mul;
			 }
		 }
		 ans =1-ans; 
		 delta[k]=ans;
	 }
		for (int i = 0; i < delta.length; i++) {
			answer.append((i + 1) + ") " + delta[i]+"  "  );
		}
	return answer;
	 
 }
 public int getBigDelta () {
	 Data data = new Data();
	 int loopGain=0;
	 int nonTouchingGain=0;
	 int answer=0;
	 for (int i=0;i<data.loopsGain.length;i++) {
		 loopGain=(int) (loopGain+data.loopsGain[i]);
	 }
	
	 for (int i=0;i<data.nonTouchingloops.length;i++) {
		 String[] temp = data.nonTouchingloops[i].split(",");
		 if (temp.length%2==0) {
			 nonTouchingGain=(int) (nonTouchingGain+data.nonTouchingloopsGain[i]);
		 }
		 else {
			 nonTouchingGain=(int) (nonTouchingGain-data.nonTouchingloopsGain[i]);
		 }
	 }
	 answer = 1-loopGain+nonTouchingGain;
	return answer;
	 
 }
 public double getOverAllTF() {
	 Data data = new Data();
	 int bigDelta = getBigDelta();
	double ans =0;
	 for (int i=0;i<data.forwardPathsGain.length;i++) {
		 ans=ans+(data.forwardPathsGain[i]*delta[i]);
	 }
	
	return ans/bigDelta;
	 
 }
}
