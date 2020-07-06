import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class covid19kg {

	ArrayList<Integer>nodeNID[] = null;//to store the nodeID-nid for each node label
	ArrayList<String>nodeName[] = null;//to store the nodeID-name for each node label
	Hashtable<Integer,Integer>node[] = null;//to match nid-nodeID for each node label
	
	ArrayList<String>NodeList = null; // to store the node label list
	String[]nodes = null;
	ArrayList<Integer>[][][]edge = null;//to store the Bgraphs
	int[]label1 = null, label2 = null;// to store the source label and target label of each Bgraph
	public covid19kg(String mainDir) throws IOException {
		//String mainDir = "C:\\Users\\Sheldon\\Documents\\GitHub\\covid19kg\\data\\HPO\\";
		
		//load nodes
		String nodes2[] = {"Disease","Drug","HostProtein","HPO","Virus","VirusProtein","Strain","Location"};
		nodes = nodes2;
		NodeList = new ArrayList();
		for(int i=0;i<nodes.length;i++) {
			NodeList.add(nodes[i]);
		}
		
		node = new Hashtable[nodes.length];//nid-id
		nodeNID = new ArrayList[nodes.length];//id-nid
		nodeName = new ArrayList[nodes.length];//id-name
		loadNode(mainDir, nodes, node, nodeNID, nodeName);
		
		//load edges
		String[]edges = {"virusprotein_hostprotein", "virus_hostprotein", "drug_gene", "hpoID_geneID","virus_virusprotein","virus_disease","hpoID_diseaseID_disease","drug_virusprotein","disease_gene","hpo_virus","hostprotein_hostprotein", "location_location", "strain_strain", "strain_location"};
		int[]column1	= {1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0};
		int[]column2	= {4, 3, 2, 2, 2, 3, 1, 2, 1, 1, 1, 1, 1, 1};
		int[]label1		= {5, 4, 1, 3, 4, 4, 3, 1, 0, 3, 2, 7, 6, 6};
		int[]label2		= {2, 2, 2, 2, 5, 0, 0, 5, 2, 4, 2, 7, 6, 7};
		this.label1 = label1;
		this.label2 = label2;
		edge = new ArrayList[nodes.length][nodes.length][];//
		loadEdges(mainDir, nodes, node, nodeNID, edges, edge, column1, column2, label1, label2);
		
	}

	private String DBDeal(Integer dbid) {
		String s = "DB";
		for(int i=0;i<(5-(dbid+"").length());i++) {
			s+="0";
		}
		s+=dbid;
		return s;
	}

	private ArrayList<Integer> getNodes(int[] snid, Hashtable<Integer, Integer>[] node, int Slabel) {
		// TODO Auto-generated method stub
		ArrayList<Integer>S = new ArrayList();
		for(int i=0;i<snid.length;i++) {
			S.add(node[Slabel].get(snid[i]));
		}
		return S;
	}

	private void loadEdges(String mainDir, String[] nodes, Hashtable<Integer, Integer>[] node, ArrayList<Integer>[] nodeNID, String[] edges, ArrayList<Integer>[][][] edge, int[] column1, int[] column2,
			int[] label1, int[] label2) throws IOException {
		
		for(int file=0;file<edges.length;file++) {
			//node[file] = new Hashtable();
			//nodeNID[file] = new ArrayList();
			//nodeName[file] = new ArrayList();
			
			//if(edges[file].equals("hostprotein_hostprotein")) continue;
			BufferedReader a = new BufferedReader (new FileReader(mainDir+"edges\\"+edges[file]+".txt"));
			int labelID1 = label1[file];
			int labelID2 = label2[file];
			edge[labelID1][labelID2] = new ArrayList[nodeNID[labelID1].size()];
			edge[labelID2][labelID1] = new ArrayList[nodeNID[labelID2].size()];
			String s = a.readLine();
			while(s.charAt(0)=='#') {
				String[]tem=s.substring(1).split(",");
				System.out.println(nodes[label1[file]]+"_"+nodes[label2[file]]+"\tnode1:\t"+tem[column1[file]]+"\t"+"node2:\t"+tem[column2[file]]);
				s=a.readLine();
			}
			while(s!=null&&!s.equals("")) {
				//System.out.println(s);
				
				String[]tem=s.split(",");
				int nid1 = -1;
				int nid2 = -1;
				if(tem.length>column1[file]&&tem.length>column2[file]&&tem[column1[file]].length()>0&&tem[column2[file]].length()>0) {
					nid1 = Integer.parseInt(tem[column1[file]]);
					nid2 = Integer.parseInt(tem[column2[file]]);
				}
				if(nid1==-1||nid2==-1){
					s=a.readLine();
					continue;
				}
				
				int id1 = -1; 
				int id2 = -1; 
				
				try {
					id1 = node[label1[file]].get(nid1);
					id2 = node[label2[file]].get(nid2);
				}catch(Exception e) {
					s=a.readLine();
					continue;
				}
				
				if(edge[labelID1][labelID2][id1]==null) {
					edge[labelID1][labelID2][id1] = new ArrayList<Integer>();
					edge[labelID1][labelID2][id1].add(id2);
				}else {
					if(!edge[labelID1][labelID2][id1].contains(id2)) {
						edge[labelID1][labelID2][id1].add(id2);
					}
				}
				
				if(edge[labelID2][labelID1][id2]==null) {
					edge[labelID2][labelID1][id2] = new ArrayList<Integer>();
					edge[labelID2][labelID1][id2].add(id1);
				}else {
					if(!edge[labelID2][labelID1][id2].contains(id1)) {
						edge[labelID2][labelID1][id2].add(id1);
					}
				}
				
				s=a.readLine();
				}
			
			
			
		}
		
	}

	private void loadNode(String mainDir, String[] nodes, Hashtable<Integer, Integer>[] node, ArrayList<Integer>[] nodeNID, ArrayList<String>[] nodeName) throws IOException {
		for(int nodefile=0;nodefile<nodes.length;nodefile++) {
			node[nodefile] = new Hashtable();
			nodeNID[nodefile] = new ArrayList();
			nodeName[nodefile] = new ArrayList();
			BufferedReader a = new BufferedReader (new FileReader(mainDir+"nodes\\"+nodes[nodefile]+".txt"));
			String s = a.readLine();
			while(s.charAt(0)=='#') {
				s=a.readLine();
			}
			while(s!=null&&!s.equals("")) {
				//System.out.println(s);
				String[]tem=s.split(",");
				int nid = Integer.parseInt(tem[0]);
				if(nid==-1) {s=a.readLine();continue;}
				int id = nodeNID[nodefile].size();
				String name = "";
				if(tem.length>1)
					name = tem[1];
				node[nodefile].put(nid, id);
				nodeNID[nodefile].add(nid);
				nodeName[nodefile].add(name);
				s=a.readLine();
				}
		}
		
	}
	
}