package algo;

import java.io.IOException;
import java.util.ArrayList;

public class MotifMatching {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		covid19kg kg = new covid19kg("C:\\Users\\Sheldon\\Documents\\GitHub\\covid19kg\\data\\HPO\\");
		Motif mf = new Motif();
		mf.getM7();
		
		ArrayList<ArrayList<Integer>>motifIns = match(kg,mf);
		motifIns2String(motifIns, mf, kg);
	}
	
	private static void motifIns2String(ArrayList<ArrayList<Integer>>motifIns, Motif mf, covid19kg kg) {
		// output the matching results
		System.out.println("Matching results for motif of "+mf.motif.length+" nodes ("+mf.motifLabelKinds.size()+" labels and "+mf.edgeNum+" edges): "+motifIns.size()+" motif instances.");
		for(int i=0;i<mf.motifLabels.size();i++) {
			System.out.print(kg.nodes[mf.motifLabels.get(i)]+"\t");
		}
		System.out.println();
		for(int i=0;i<motifIns.size();i++) {
			System.out.println(output(motifIns.get(i),mf.motifLabels, kg));
		}
	}
	
	private static String output(ArrayList<Integer> ins, ArrayList<Integer> motifLabels, covid19kg kg) {
		String s = "";
		for(int i=0;i<ins.size();i++) {
			int id = ins.get(i);
			int label = motifLabels.get(i);
			s+= kg.nodeNID[label].get(id)+"\t";
		}
		return s;
	}

	private static ArrayList<ArrayList<Integer>> match(covid19kg kg, Motif mf) {
		if(mf.motif.length == 1) {
			//match a single node
			ArrayList<ArrayList<Integer>> res = new ArrayList();
			int labelID = mf.motifLabels.get(0);
			for(int i=0;i<kg.nodeNID[labelID].size();i++) {
				ArrayList<Integer>ins = new ArrayList();
				ins.add(kg.nodeNID[labelID].get(i));
				res.add(ins);
			}
			return res;
		}
		
		if(mf.motif.length == 2) {
			//match an edge 
			ArrayList<ArrayList<Integer>> res = new ArrayList();
			ArrayList<Integer>subgraph[] = kg.edge[mf.motifLabels.get(0)][mf.motifLabels.get(1)];
			if(subgraph==null) {noinstancewarn();return res;}
			for(int i=0;i<subgraph.length;i++) {
				if(subgraph[i]==null||subgraph[i].size()==0)continue;
				for(int j=0;j<subgraph[i].size();j++) {
					int nei = subgraph[i].get(j);
					if(mf.motifLabelKinds.size()==1&&nei<i)
						//overcounting risk: otherwise edge i-nei will be counted twice, e.g., nei-i
						continue;
					ArrayList<Integer>ins = new ArrayList();
					ins.add(i);
					ins.add(nei);
					res.add(ins);
				}
			}
			return res;
		}
		
		if(mf.motif.length == 3) {
			//match a triangle
			//ArrayList<ArrayList<Integer>> res = new ArrayList();
			//
			return match3nodes(kg, mf);
		}
		
		if(mf.motif.length == 4)
			return match4nodes(kg, mf);
		
		if(mf.motif.length == 5)
			return match5nodes(kg, mf);
		
		return null;
	}

	private static ArrayList<ArrayList<Integer>> match3nodes(covid19kg kg, Motif mf) {
		// match a triangle or a 2-path
		ArrayList<ArrayList<Integer>> res = new ArrayList();
		if(mf.edgeNum<2) {
			System.out.println("The 3-node pattern graph is not connected!");
			return res;
		}
		if(mf.edgeNum==2) {
			//the pattern graph is a 2-path
			int seed = -1; //start searching from the middle node whose degree is 2
			int seedID = -1;
			for(int i=0;i<mf.motif.length;i++) {
				if(mf.motif[i].size()==2) {
					seed = i;
					seedID = i;
					break;
				}				
			}
			int seedLabel = mf.motifLabels.get(seed);
			int seedNei1 = mf.motif[seed].get(0);
			int seedNeiLabel1 = mf.motifLabels.get(seedNei1);
			int seedNei2 = mf.motif[seed].get(1);
			int seedNeiLabel2 = mf.motifLabels.get(seedNei2);
			
			if(mf.motifLabelKinds.size()==3) {
				//no overcounting risk for both 2-path and triangle
				return match3nodes3labels(seed,seedID,seedLabel,seedNei1,seedNeiLabel1,seedNei2,seedNeiLabel2,mf.edgeNum,kg,res);
			}
			if(mf.motifLabelKinds.size()==1) {
				//find a three combination from one subgraph
				ArrayList<Integer>[] subgraph = kg.edge[seedLabel][seedLabel];
				//for each seed node, pick two neighbors and check if there is edge between them
				return match3nodes1label(subgraph,mf.edgeNum,res);
			}
			if(mf.motifLabelKinds.size()==2) {
				//part of the motif instances: labelB-labelA-labelB
				int singleLabel=-1;
				for(int i=0;i<mf.motifLabelNodes.size();i++) {
					if(mf.motifLabelNodes.get(i).size()==1) {
						singleLabel = mf.motifLabelKinds.get(i);
						break;
					}
				}
				if(seedLabel == singleLabel)
					return match3nodes2labels(mf, kg, res);
				else
					return match3nodes3labels(seed,seedID,seedLabel,seedNei1,seedNeiLabel1,seedNei2,seedNeiLabel2,mf.edgeNum,kg,res);
			}
		}else {
			//the pattern graph is a triangle
			int seed = 0; //start searching from the middle node whose degree is 2
			int seedID = 0;
			int seedLabel = mf.motifLabels.get(seed);
			int seedNei1 = 1;
			int seedNeiLabel1 = mf.motifLabels.get(seedNei1);
			int seedNei2 = 2;
			int seedNeiLabel2 = mf.motifLabels.get(seedNei2);
			
			if(mf.motifLabelKinds.size()==3) {
				//no overcounting risk for both 2-path and triangle
				return match3nodes3labels(seed,seedID,seedLabel,seedNei1,seedNeiLabel1,seedNei2,seedNeiLabel2,mf.edgeNum,kg,res);
			}
			if(mf.motifLabelKinds.size()==1) {
				//find a three combination from one subgraph
				ArrayList<Integer>[] subgraph = kg.edge[seedLabel][seedLabel];
				//for each seed node, pick two neighbors and check if there is edge between them
				return match3nodes1label(subgraph,mf.edgeNum,res);
			}
			if(mf.motifLabelKinds.size()==2) {
				return match3nodes2labels(mf, kg, res);
			}
			
		}
		
		return res;
	}
	
	private static ArrayList<ArrayList<Integer>> match3nodes2labels(Motif mf, covid19kg kg,
			ArrayList<ArrayList<Integer>> res) {
		int singleLabel=-1, singleLabelID = -1, singleLabelIDGroup = -1;
		for(int i=0;i<mf.motifLabelNodes.size();i++) {
			if(mf.motifLabelNodes.get(i).size()==1) {
				singleLabel = mf.motifLabelKinds.get(i);
				singleLabelID = mf.motifLabelNodes.get(i).get(0);
				singleLabelIDGroup = i;
				break;
			}
		}
		int multiLabelID1 = mf.motif[singleLabelID].get(0);
		int multiLabelID2 = mf.motif[singleLabelID].get(1);;
		int multiLabel= mf.motifLabelKinds.get(1-singleLabelIDGroup);
		
		ArrayList<Integer>[] subgraph = kg.edge[singleLabel][multiLabel];
		ArrayList<Integer>[] subgraph2 = kg.edge[multiLabel][multiLabel];
		if(subgraph==null||subgraph2==null) {
			noinstancewarn();
			return res;
		}
		//for each seed node, pick two neighbors and check if there is edge between them
		for(int i=0;i<subgraph.length;i++) {
			if(subgraph[i]==null||subgraph[i].size()<2)continue;
			//seed = i, nei1 = subgraph1[i].get(j), nei2 = subgraph2[i].get(k)
			for(int j=0;j<subgraph[i].size();j++) {
				int nei1 = subgraph[i].get(j);
				for(int k=j+1;k<subgraph[i].size();k++) {
					int nei2 = subgraph[i].get(k);
					if(mf.edgeNum==2&&subgraph2[nei1]!=null&&subgraph2[nei1].contains(nei2))
						//it is a triangle rather than 2-path
						continue;
					if(mf.edgeNum==3&&(subgraph2[nei1]==null||!subgraph2[nei1].contains(nei2)))
						//it is a 2-path rather than triangle 
						continue;
					ArrayList<Integer>ins = new ArrayList();
					//the order of the instance should follow the pattern graph: seedID - seedNei1 - seedNei2
					ins.add(i);
					ins.add(nei1);
					ins.add(nei2);
					ins = reorder(ins, singleLabelID, multiLabelID1, multiLabelID2);
					res.add(ins);
				}
			}
		}
		return res;
	}

	private static void noinstancewarn() {
		System.out.println("The motif is not supported in the current knowledge graph.");
	}

	private static ArrayList<ArrayList<Integer>> match3nodes1label(ArrayList<Integer>[] subgraph, int edgeNum,
			ArrayList<ArrayList<Integer>> res) {
		if(subgraph==null) {
			noinstancewarn();
			return res;
		}
		for(int i=0;i<subgraph.length;i++) {
			if(subgraph[i]==null||subgraph[i].size()<2)continue;
			//seed = i, nei1 = subgraph1[i].get(j), nei2 = subgraph2[i].get(k)
			for(int j=0;j<subgraph[i].size();j++) {
				int nei1 = subgraph[i].get(j);
				for(int k=j+1;k<subgraph[i].size();k++) {
					int nei2 = subgraph[i].get(k);
					if(edgeNum==2&&subgraph[nei1].contains(nei2))
						//it is a triangle rather than 2-path
						continue;
					if(edgeNum==3&&!subgraph[nei1].contains(nei2))
						//it is a 2-path rather than triangle 
						continue;
					if(edgeNum==3&&(nei1<i||nei2<i))
						//avoid overcounting triangles: i<nei1<nei2
						continue;
					ArrayList<Integer>ins = new ArrayList();
					//the order of the instance should follow the pattern graph: seedID - seedNei1 - seedNei2
					ins.add(i);
					ins.add(nei1);
					ins.add(nei2);
					res.add(ins);
				}
			}
		}
		return res;
	}

	private static ArrayList<ArrayList<Integer>> match3nodes3labels(int seed, int seedID, int seedLabel, int seedNei1,
			int seedNeiLabel1, int seedNei2, int seedNeiLabel2, int edgeNum, covid19kg kg,
			ArrayList<ArrayList<Integer>> res) {
		ArrayList<Integer>[] subgraph1 = kg.edge[seedLabel][seedNeiLabel1];
		ArrayList<Integer>[] subgraph2 = kg.edge[seedLabel][seedNeiLabel2];
		if(subgraph1==null||subgraph2==null) {
			noinstancewarn();
			return res;
		}
		//for each seed node, pick a neighbor from subgraph1 and a neighbor from subgraph2
		for(int i=0;i<subgraph1.length;i++) {
			if(subgraph1[i]==null||subgraph2[i]==null||subgraph1[i].size()==0||subgraph2[i].size()==0)continue;
			//seed = i, nei1 = subgraph1[i].get(j), nei2 = subgraph2[i].get(k)
			for(int j=0;j<subgraph1[i].size();j++) {
				int nei1 = subgraph1[i].get(j);
				for(int k=0;k<subgraph2[i].size();k++) {
					int nei2 = subgraph2[i].get(k);
					if(edgeNum==3&&(kg.edge[seedNeiLabel1][seedNeiLabel2]==null||kg.edge[seedNeiLabel1][seedNeiLabel2][nei1]==null))
						//cannot form a triangle
						continue;
					if(edgeNum==3&&!kg.edge[seedNeiLabel1][seedNeiLabel2][nei1].contains(nei2))
						//it is a 2-path rather than triangle 
						continue;
					if(kg.edge[seedNeiLabel1][seedNeiLabel2]!=null&&kg.edge[seedNeiLabel1][seedNeiLabel2][nei1]!=null) 
						if(edgeNum==2&&kg.edge[seedNeiLabel1][seedNeiLabel2][nei1].contains(nei2))
							//it is a triangle rather than 2-path
							continue;

					
					ArrayList<Integer>ins = new ArrayList();
					//the order of the instance should follow the pattern graph: seedID - seedNei1 - seedNei2
					ins.add(i);
					ins.add(nei1);
					ins.add(nei2);
					if(edgeNum==2)
						ins = reorder(ins, seedID, seedNei1, seedNei2);
					res.add(ins);
				}
			}
		}
		return res;
	}

	private static ArrayList<Integer> reorder(ArrayList<Integer> ins, int seedID, int seedNei1, int seedNei2) {
		ArrayList<Integer>order = new ArrayList();
		order.add(seedID);order.add(seedNei1);order.add(seedNei2);
		ins = sort(ins, order);
		return ins;
	}
	private static ArrayList<Integer> reorder(ArrayList<Integer> ins, int seedID, int seedNei1, int seedNei2, int seedNei3) {
		ArrayList<Integer>order = new ArrayList();
		order.add(seedID);order.add(seedNei1);order.add(seedNei2);order.add(seedNei3);
		ins = sort(ins, order);
		return ins;
	}
	private static ArrayList<Integer> sort(ArrayList<Integer> ins, ArrayList<Integer> order) {
		for(int i=0;i<order.size();i++) {
			for(int j=i+1;j<order.size();j++) {
				if(order.get(i)>order.get(j)) {
					int tem = order.get(i);
					order.set(i, order.get(j));
					order.set(j, tem);
					tem = ins.get(i);
					ins.set(i, ins.get(j));
					ins.set(j, tem);
				}
			}
		}
		return ins;
	}

	private static ArrayList<ArrayList<Integer>> match4nodes(covid19kg kg, Motif mf) {
		// match 4-node pattern graphs
<<<<<<< HEAD
		ArrayList<ArrayList<Integer>>res = new ArrayList();
		if(mf.edgeNum==4) {
			//a tailed rectangle or a rectangle: check the degree vector
			if(mf.degreeVec.get(0)==2) {
				//it is a rectangle
				if(mf.motifLabelKinds.size()==2) {
					//three pattern graphs with 2 kinds of labels
					if(mf.motifLabelNodes.get(0).size()==2) {
						//for AABB and ABAB
						int label0 = mf.motifLabels.get(0), id0 = 0;
						int label1 = mf.motifLabels.get(mf.motif[0].get(0)), id1 = mf.motif[0].get(0);
						int label2 = mf.motifLabels.get(mf.motif[0].get(1)), id2 = mf.motif[0].get(1);
						
						if(label1!=label2) {
							//for AABB
							if(label2==label0) {
								//switch label1 and label2 if label2=label0
								label2 = label1;
								label1 = label0;
								int tem = id2;
								id2 = id1;
								id1 = tem;
							}
							int id3 = mf.motif[id1].get(0);
							if(id3==0)
								id3 = mf.motif[id1].get(1);
							//here label3 = label2
								
							ArrayList<Integer>subgraphAA[] = kg.edge[label0][label1];
							ArrayList<Integer>subgraphAB[] = kg.edge[label0][label2];
							ArrayList<Integer>subgraphBB[] = kg.edge[label2][label2];
							for(int i=0;i<subgraphAA.length;i++) {
								if(kg.nodeNID[label0].get(i)==483)
									System.out.println();
								if(subgraphAA[i]==null)continue;
								for(int j=0;j<subgraphAA[i].size();j++) {
									int nei1 = subgraphAA[i].get(j);
									if(nei1<i)
										continue;//avoid duplicates, please consider why we need this constraint here
									if(subgraphAB[i]==null)continue;
									for(int k=0;k<subgraphAB[i].size();k++) {
										int nei2 = subgraphAB[i].get(k);
										if(subgraphAB[nei1]!=null&&subgraphAB[nei1].contains(nei2))
											continue;//no edge between nei1 and nei2
										if(subgraphAB[nei1]==null)continue;
										for(int p=0;p<subgraphAB[nei1].size();p++) {
											int nei3 = subgraphAB[nei1].get(p);
											if(subgraphAB[i]!=null&&subgraphAB[i].contains(nei3))
												continue;//no edge between i and nei3
											if(subgraphBB[nei3]!=null&&subgraphBB[nei3].contains(nei2)) {
												ArrayList<Integer>ins = new ArrayList();
												ins.add(i);
												ins.add(nei1);
												ins.add(nei2);
												ins.add(nei3);
												//the order of the instance should follow the pattern graph: mf.motifLabels
												ins = reorder(ins, 0, id1, id2, id3);
												res.add(ins);
											}
										}
									}
								}
							}
						}else {
							//for ABAB
							//write code here
						}
						
					}else {
						//for AAAB
						//write code here
					}
					
				}
				if(mf.motifLabelKinds.size()==1) {
					//write code here
				}
				if(mf.motifLabelKinds.size()==3) {
					//write code here
				}
				if(mf.motifLabelKinds.size()==4) {
					//write code here
				}
				
			}else {
				//it is a tailed triangle
				//write code here
			}
			
		}
		
		return res;
=======
		ArrayList<ArrayList<Integer>> res = new ArrayList();
		if (mf.edgeNum < 3) {
			System.out.println("The 4-node pattern graph is not connected!");
			return res;
		}
		if (mf.edgeNum == 3) {
			
		}
		else if (mf.edgeNum == 4) {
			//check if pattern graph is 3-path or square
			int seed = -1;
			int seedID = -1;
			for (int i = 0; i < mf.motif.length; i++) {
				if (mf.motif[i].size() == 3) {
					//pattern graph is a 3-path
					seed = i;
					seedID = i;
					break;
				}
			}
			if (seed == -1) {
				//pattern graph is a square
				seed = 0;
				seedID = 0;
			}
			
			if (mf.motifLabelKinds.size() == 2) {
				return match4nodes2labels(seed, seedID, kg, mf, res);
			}
			
			
		}
		else if (mf.edgeNum == 5) {
			
		}
		else {
			
		}
		
		return res;
	}
	
	private static ArrayList<ArrayList<Integer>> match4nodes2labels(int seed, int seedID, covid19kg kg, Motif mf, ArrayList<ArrayList<Integer>> res) {
		int seedLabel = mf.motifLabels.get(seed);
		int seedNei1 = mf.motif[seed].get(0);
		int seedNei1Label = mf.motifLabels.get(seedNei1);
		int seedNei2 = mf.motif[seed].get(1);
		int seedNei2Label = mf.motifLabels.get(seedNei2);
		
		
		return res;
>>>>>>> db7f65d... match4nodes2labels first attempt
	}
	
	private static ArrayList<ArrayList<Integer>> match5nodes(covid19kg kg, Motif mf) {
		// match 5-node pattern graphs
		return null;
	}

}
