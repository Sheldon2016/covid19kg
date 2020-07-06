import java.util.ArrayList;

public class Motif {
	
	ArrayList<Integer>motif[] = null;
	ArrayList<Integer>motifLabels = null;
	
	void getM1(){
		motif = new ArrayList[3];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motifLabels = new ArrayList();
		motifLabels.add(4);
		motifLabels.add(4);
		motifLabels.add(7);
	}
	
	void getM2() {
		motif = new ArrayList[3];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		//motif[0].add(3);
		motif[1] = new ArrayList();
		motif[1].add(0);
		//motif[1].add(2);
		//motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		//motif[2].add(1);
		//motif[3] = new ArrayList();
		//motif[3].add(0);
		//motif[3].add(1);
		
		motifLabels = new ArrayList();
		motifLabels.add(0);
		//motifLabels.add(-1);
		motifLabels.add(4);
		motifLabels.add(1); 

	}
	
	void getM3() {
		motif = new ArrayList[4];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[0].add(3);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(2);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(1);
		motif[3] = new ArrayList();
		motif[3].add(0);
		motif[3].add(1);
		
		motifLabels = new ArrayList();
		motifLabels.add(2);
		motifLabels.add(5);
		motifLabels.add(4);
		motifLabels.add(1); 
	}
	
	void getM4() {
		motif = new ArrayList[3];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		//motif[0].add(3);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(2);
		//motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(1);
		//motif[2].add(3);
		//motif[3] = new ArrayList();
		//motif[3].add(0);
		//motif[3].add(1);
		//motif[3].add(2);
		
		motifLabels = new ArrayList();
		motifLabels.add(5);
		motifLabels.add(5);
		motifLabels.add(5);
		//motifLabels.add(0); 
	}
	
	void getM5() {
		motif = new ArrayList[3];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		//motif[0].add(3);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(2);
		//motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(1);
		//motif[2].add(3);
		//motif[3] = new ArrayList();
		//motif[3].add(0);
		//motif[3].add(1);
		//motif[3].add(2);
		
		motifLabels = new ArrayList();
		motifLabels.add(2);
		motifLabels.add(2);
		motifLabels.add(2);
		//motifLabels.add(0); 
	}
	
	void getM6() {
		motif = new ArrayList[4];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[0].add(3);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(2);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(1);
		motif[3] = new ArrayList();
		motif[3].add(0);
		motif[3].add(1);
		
		motifLabels = new ArrayList();
		motifLabels.add(2);
		motifLabels.add(2);
		motifLabels.add(4);
		motifLabels.add(0); 
	}

}
