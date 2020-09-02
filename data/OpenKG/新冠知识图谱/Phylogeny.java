package covid19;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import net.sf.json.JSON;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;

public class Phylogeny {
	
	static HashSet<String> nodeLabelSet;
	static HashSet<String> edgeLabelSet;
	static HashSet<String> allNodesSet;
	static HashMap<String,String> edgeStringLabelMap;
	
	public static void main(String[] args) throws  Exception{
		
		nodeLabelSet = new HashSet<String>();
		edgeLabelSet = new HashSet<String>();
		allNodesSet = new HashSet<String>();//store all the nodes
		edgeStringLabelMap = new HashMap<String,String>();//(edgeString, edgeLabel) ("100_200",edgeLabel)
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true); 
//		HashMap<String, String> LabelMap= getNodeLabels();//C1 - Virus
		
		//scan for label of all nodes
		HashMap<String, String> allNodesWithLabel = allNodes();
		//scan all edges
		getRelationships(allNodesWithLabel);
        
		HashMap<String,Integer> nodeLabelMap = new HashMap<>();
		HashMap<String,Integer> edgeLabelMap = new HashMap<>();
		HashMap<String,Integer> allNodesMap = new HashMap<>();
		
		Iterator nodeLabelIt = nodeLabelSet.iterator();
		int nodeLabelCount = 0;
		while (nodeLabelIt.hasNext()) {
			nodeLabelMap.put((String) nodeLabelIt.next(), nodeLabelCount);
			nodeLabelCount ++;
		}
		
		Iterator edgeLabelIt = edgeLabelSet.iterator();
		int edgeLabelCount = 0;
		while (edgeLabelIt.hasNext()) {
			edgeLabelMap.put((String) edgeLabelIt.next(), edgeLabelCount);
			edgeLabelCount ++;
		}
		
		Iterator allNodesIt = allNodesSet.iterator();
		int nodeCount = 0;
		while (allNodesIt.hasNext()) {
			allNodesMap.put((String) allNodesIt.next(), nodeCount);
			nodeCount ++;
		}
		allNodesMap = (HashMap<String, Integer>) sortByValueAscending(allNodesMap);
//		System.out.println("nodeLabelMap:"+nodeLabelMap);
//		System.out.println("edgeLabelMap:"+edgeLabelMap);
//		System.out.println("allNodesMap:"+allNodesMap);
		
		System.out.println("Start write graph files!");
		writeGraphFiles(allNodesMap, edgeStringLabelMap, nodeLabelMap, edgeLabelMap, allNodesWithLabel);
		writeLabelFiles(nodeLabelMap, edgeLabelMap);
		
		//write mapping relationships
		writeMappingRelation(allNodesMap);
        
    }
	
	public static void writeMappingRelation(HashMap<String,Integer> allNodesMap){
		System.out.println(allNodesMap.size());
		HashMap<Integer,String> reMap = new HashMap<Integer,String>();
		
		FileWriter fw;
		try {
			String fName = "mappings.lg";
			fw = new FileWriter(fName);
			fw.write("t # " + ":\n");
			System.out.println("-------------write mappings-------------");
			for(Entry<String,Integer> entry:allNodesMap.entrySet()){
				String nodeString = entry.getKey();
				int nodeId = entry.getValue();
				String out = nodeId + " = " + nodeString + "\n";
				fw.write(out);
				reMap.put(nodeId, nodeString);
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(reMap.size());
	}
	
	public static HashMap<String, String> allNodes(){
		String path = "/Users/zengjian/iParkWSFiles/projects/PatKG_SixOne/Covid_19_Data_Process/"
				+ "data/phylogeny_edges.json";
		String s = readJsonFile(path);
		JSONObject jobj = JSON.parseObject(s);
		JSONArray graph = jobj.getJSONArray("@graph");
		HashMap<String, String> allNodesWithLabel = new HashMap<String,String>();
		for(int i = 0 ; i < graph.size();i++){
			JSONObject obj = (JSONObject)graph.get(i);
			String nodeIdxA = (String)obj.get("@id");
			if(allNodesWithLabel.containsKey(nodeIdxA)) 
				continue;
			String nodeLabel = (String)obj.get("@type");
			allNodesWithLabel.put(nodeIdxA, nodeLabel);
		}
		return allNodesWithLabel;
	}
	
	public static void getRelationships(HashMap<String, String> allNodesWithLabel) {
		String path = "/Users/zengjian/iParkWSFiles/projects/PatKG_SixOne/Covid_19_Data_Process/"
				+ "data/phylogeny_edges.json";
		String s = readJsonFile(path);
		JSONObject jobj = JSON.parseObject(s);
		JSONArray graph = jobj.getJSONArray("@graph");
		for (int i = 0; i < graph.size(); i++) {
			JSONObject obj = (JSONObject) graph.get(i);
			String nodeIdxA = (String) obj.get("@id");
//			System.out.println(obj.toString());
			Iterator iter = obj.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				
//				JSONArray value = obj.getJSONArray(key);
				if(entry.getValue() == null) continue;
				String value = entry.getValue().toString();
				if(value.length() < 48) continue;
				String tmp = value.substring(0, 47);
				if (tmp.equals("http://www.openkg.cn/COVID-19/research/resource") && !key.equals("@id")) {
					String nodeIdxB = value;
					String edgeLabel = key;
					String labelA = allNodesWithLabel.get(nodeIdxA);
					String labelB = allNodesWithLabel.get(nodeIdxB);
					if (labelA == null || labelB == null)
						continue;
					if (!allNodesSet.contains(nodeIdxA))
						allNodesSet.add(nodeIdxA);
					if (!allNodesSet.contains(nodeIdxB))
						allNodesSet.add(nodeIdxB);
					if (!nodeLabelSet.contains(labelA))
						nodeLabelSet.add(labelA);
					if (!nodeLabelSet.contains(labelB))
						nodeLabelSet.add(labelB);
					if (!edgeLabelSet.contains(edgeLabel))
						edgeLabelSet.add(edgeLabel);
					String AB = nodeIdxA + "#" + nodeIdxB;
					edgeStringLabelMap.put(AB, edgeLabel);
				}
				//end edge scan
			}
		}

	}
	
	public static HashMap<String, String> getNodeLabels(){
		String path = "/Users/zengjian/iParkWSFiles/projects/PatKG_SixOne/Covid_19_Data_Process/data/phylogeny_nodesLabel.json";
		String s = readJsonFile(path);
		JSONObject jobj = JSON.parseObject(s);
		JSONArray graph = jobj.getJSONArray("@graph");
		//get node labels
		HashMap<String, String> nodeLabelmap = new HashMap<String, String>();
		for (int i = 0 ; i < graph.size();i++){
			JSONObject obj = (JSONObject)graph.get(i);
			String nodeLabelId = (String)obj.get("@id");
			String nodetType = (String)obj.get("type");
			String nodeLabel = "";
			JSONArray subObj = obj.getJSONArray("label");
			for(int j = 0;j<subObj.size();j++){
				JSONObject nodeLabelObj = (JSONObject)subObj.get(j);
				String lan = (String) nodeLabelObj.get("@language");
				if(lan.equals("en")){//English
					nodeLabel = (String) nodeLabelObj.get("@value");
				}
			}
			nodeLabelmap.put(nodeLabelId, nodeLabel);
		}
		return nodeLabelmap;
	}
	
	/**
     * read json file
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAscending(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				int compare = (o1.getValue()).compareTo(o2.getValue());
				return compare;
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
    
    
    public static void writeGraphFiles(Map<String, Integer> allNodesMap, HashMap<String, String> edgeStringLabelMap,
			HashMap<String,Integer> nodeLabelMap, HashMap<String,Integer> edgeLabelMap, HashMap<String,String> typeMap) {

		FileWriter fw;
		try {
			String fName = "phylogeny.lg";
			fw = new FileWriter(fName);
			fw.write("t # " + ":\n");
			System.out.println("-------------write nodes-------------");
			for (Entry<String, Integer> entry : allNodesMap.entrySet()) {
				String nodeStringIdx = entry.getKey();
				int nodeIdx = entry.getValue();
				Integer nodeLabelIdx = nodeLabelMap.get(typeMap.get(nodeStringIdx));
				String out = "v " + nodeIdx + " " + nodeLabelIdx + "\n";
				fw.write(out);
			}
			System.out.println("-------------write edges-------------");
			for (Entry<String, String> entry : edgeStringLabelMap.entrySet()) {
				String AB = entry.getKey();
		        String[] strArray = AB.split("#");
		        String nodeA = strArray[0];
		        String nodeB = strArray[1];
				Integer edgeLabelIdx = edgeLabelMap.get(entry.getValue());
				String out = "e " + allNodesMap.get(nodeA) + " " + allNodesMap.get(nodeB) + " " + edgeLabelIdx + "\n";
				fw.write(out);
			}

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void writeLabelFiles(HashMap<String,Integer> nodeLabelMap, HashMap<String,Integer> edgeLabelMap) {

		// nodes
		FileWriter fw;
		try {
			String fName = "Phy_Label.txt";
			fw = new FileWriter(fName);
			fw.write("Labels correspondence " + ":\n");
			fw.write("-------------------nodeLabels-------------------" + "\n");
			
			
			for(Entry<String,Integer> entry:nodeLabelMap.entrySet()) {
				fw.write(entry.getValue()+ ":");
				fw.write(entry.getKey()+ "\n");
			}
			
			fw.write("-------------------edgeLabels-------------------" + "\n");
			for(Entry<String,Integer> entry:edgeLabelMap.entrySet()) {
				fw.write(entry.getValue()+ ":");
				fw.write(entry.getKey()+ "\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
