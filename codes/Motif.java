package algo;

import java.util.ArrayList;

public class Motif {
	
	public ArrayList<Integer> motif[] = null;
	public ArrayList<Integer> degreeVec = null;
	public ArrayList<Integer> motifLabels = null;
	public ArrayList<Integer> motifLabelKinds = null;
	public ArrayList<ArrayList<Integer>> motifLabelNodes = null;
	public int edgeNum = 0;
	
	
	void getTailedTriangle(){
		motif = new ArrayList[4];
		motif[0] = new ArrayList();//to store neighbors of 0
		motif[0].add(1);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(2);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(1);
		motif[2].add(3);
		motif[3] = new ArrayList();
		motif[3].add(1);
		motif[3].add(2);
		motifLabels = new ArrayList();
		motifLabels.add(0);
		motifLabels.add(1);
		motifLabels.add(1);
		motifLabels.add(3);
		run();
	}
	
	void getNode(){
		motif = new ArrayList[1];
		motif[0] = new ArrayList();//to store neighbors of 0
		motifLabels = new ArrayList();
		motifLabels.add(6);
		run();
	}
	
	void getM0(){
		motif = new ArrayList[2];
		motif[0] = new ArrayList();//to store neighbors of 0
		motif[0].add(1);
		//edge(0,1) and edge(0,2)
		motif[1] = new ArrayList();
		motif[1].add(0);
		motifLabels = new ArrayList();
		motifLabels.add(6);
		motifLabels.add(7);
		
		run();
	}
	
	void getM1(){
		motif = new ArrayList[3];
		motif[0] = new ArrayList();//to store neighbors of 0
		motif[0].add(1);
		motif[0].add(2);
		//edge(0,1) and edge(0,2)
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motifLabels = new ArrayList();
		motifLabels.add(6);
		motifLabels.add(6);
		motifLabels.add(7);
		
		run();
	}
	
	void getM2() {
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
		//motif[3] = new ArrayList();
		//motif[3].add(0);
		//motif[3].add(1);
		
		motifLabels = new ArrayList();
		motifLabels.add(2);
		//motifLabels.add(-1);
		motifLabels.add(2);
		motifLabels.add(5); 

		run();
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
		run();
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
		run();
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
		run();
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
		run();
	}
	void getM7() {
		//rectangle: strain-strain-location-location
		motif = new ArrayList[4];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(3);
		motif[3] = new ArrayList();
		motif[3].add(1);
		motif[3].add(2);
		
		motifLabels = new ArrayList();
		motifLabels.add(6);
		motifLabels.add(6);
		motifLabels.add(7);
		motifLabels.add(7); 
		run();
	}
	
	void getM8() {
		//rectangle: strain-location-strain-location
		motif = new ArrayList[4];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(3);
		motif[3] = new ArrayList();
		motif[3].add(1);
		motif[3].add(2);
		
		motifLabels = new ArrayList();
		motifLabels.add(6);
		motifLabels.add(7);
		motifLabels.add(7);
		motifLabels.add(6); 
		run();
	}
	
	void getM9() {
		//rectangle: strain-location-location-location
		motif = new ArrayList[4];
		motif[0] = new ArrayList();
		motif[0].add(1);
		motif[0].add(2);
		motif[1] = new ArrayList();
		motif[1].add(0);
		motif[1].add(3);
		motif[2] = new ArrayList();
		motif[2].add(0);
		motif[2].add(3);
		motif[3] = new ArrayList();
		motif[3].add(1);
		motif[3].add(2);
		
		motifLabels = new ArrayList();
		motifLabels.add(4);
		motifLabels.add(5);
		motifLabels.add(6);
		motifLabels.add(7); 
		run();
	}
	
	public boolean run() {
		degreeVec = new ArrayList();
		for(int i=0;i<motif.length;i++){
			degreeVec.add(motif[i].size());
		}
		
		for(int i=0;i<degreeVec.size();i++) {
			for(int j=i+1;j<degreeVec.size();j++) {
				if(degreeVec.get(i)>degreeVec.get(j)) {
					int tem = degreeVec.get(i);
					degreeVec.set(i, degreeVec.get(j));
					degreeVec.set(j, tem);
				}
			}
		}
		
		//to check if the motif can be over-counted.
		motifLabelKinds = new ArrayList();
		motifLabelNodes = new ArrayList();
		
		for(int i=0;i<motif.length;i++) {
			for(int j=0;j<motif[i].size();j++) {
				int nei = motif[i].get(j);
				if(nei>i)
					edgeNum ++;
			}
		}
		
		boolean sig = false;
		for(int i=0;i<motifLabels.size();i++) {
			int l = motifLabels.get(i);
			if(motifLabelKinds.contains(l)) {				
				sig = true;
				motifLabelNodes.get(motifLabelKinds.indexOf(l)).add(i);
				}else {
					motifLabelKinds.add(l);
					ArrayList<Integer>res = new ArrayList();
					res.add(i);
					motifLabelNodes.add(res);
				}			
		}
		//rank motifLabelKinds and motifLabelNodes ASCE, according to the size of motifLabelNodes.get(i)
		for(int i=0;i<motifLabelNodes.size();i++) {
			int sizei = motifLabelNodes.get(i).size();
			for(int j=i+1;j<motifLabelNodes.size();j++) {
				int sizej = motifLabelNodes.get(j).size();
				if(sizei>sizej) {
					ArrayList<Integer>mlnTem = motifLabelNodes.get(i);
					motifLabelNodes.set(i, motifLabelNodes.get(j));
					motifLabelNodes.set(j, mlnTem);
					int mlkTem = motifLabelKinds.get(i);
					motifLabelKinds.set(i, motifLabelKinds.get(j));
					motifLabelKinds.set(j, mlkTem);
				}
			}
		}
		
		if(!sig)
			return false;
		//check if every node in the same list is in the same orbit
		//for(int i=0;i<motifLabelNodes.size();i++) {
		//	ArrayList<Integer>nodes = motifLabelNodes.get(i);
		//	if(nodes.size()<=1)continue;
			
		//}
		
		return false;
	}
	

}
