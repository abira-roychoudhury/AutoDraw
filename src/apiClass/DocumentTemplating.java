package apiClass;

import java.util.LinkedHashMap;


public class DocumentTemplating {

	public LinkedHashMap<String, String> parseContent(String descriptionStr) {
		LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
		
		//String compactionVariation[] = {"Compacting","Compactian","Compaction","onpaction","ompaction"};
		
		String splitDesc[] = descriptionStr.split("\\n");
		int partno=0, processname=0,burr=0,usl=0,mid=0,lsl=0,partname=0;
		String key="",value="",density1="",density2="";
		int colon,space;
		System.out.println("*******************************************************");
		for(int i=0; i<splitDesc.length;i++)
		{
			System.out.println(splitDesc[i]);
			//partno extraction
			if(splitDesc[i].contains("porite") && partno==0){
				displayDocument.put("Part No", splitDesc[i].substring(7));
				partno++;
			}
			
			//process name extraction
			else if(splitDesc[i].contains("Process") && processname==0){
				displayDocument.put("Process Name", splitDesc[i+3]);
				processname++;				
			}
			
			//compacting burr control extraction
			else if(splitDesc[i].contains("control") && splitDesc[i].contains("") && burr==0){
				key = "burr";
				value = splitDesc[i].substring(splitDesc[i].indexOf(":")+1);
				displayDocument.put(key,value);
				burr++;				
			}
			
			//USL extraction
			else if(splitDesc[i].contains("USL") && usl==0){
				colon = splitDesc[i].indexOf(":");
				value = splitDesc[i].substring(colon+2,colon+6);
				displayDocument.put("USL", value);
				density1 = splitDesc[i+1];
				usl++;
			}
			
			//MID extraction
			else if(splitDesc[i].contains("MID") && mid==0){
				colon = splitDesc[i].indexOf(":");
				value = splitDesc[i].substring(colon+2,colon+6);
				displayDocument.put("MID", value);
				mid++;
			}
			
			//LSL extraction
			else if(splitDesc[i].contains("LSL") && lsl==0){
				colon = splitDesc[i].indexOf(":");
				value = splitDesc[i].substring(colon+2,colon+6);
				displayDocument.put("LSL", value);
				density2 = splitDesc[i+1];;
				displayDocument.put("density", density1+"~"+density2);
				lsl++;
			}
			
			//Part name extraction
			else if(splitDesc[i].contains("PART") && splitDesc[i].contains("NAME") && partname==0){
				displayDocument.put("Part Name", splitDesc[i+1]);
				partname++;
			}
				
		}		
		return displayDocument;
	}
	

}
