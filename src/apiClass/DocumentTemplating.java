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
		
		for(int i=0; i<splitDesc.length;i++)
		{
			//partno extraction
			if(splitDesc[i].contains("porite") && partno==0){
				displayDocument.put("Part No", "I"+splitDesc[i].substring(8));
				partno++;
			}
			
			//process name extraction
			else if(splitDesc[i].contains("Process") && processname==0){
				i+=2;
				displayDocument.put("Process Name", splitDesc[i]);
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
				space = splitDesc[i].lastIndexOf(" ");
				value = splitDesc[i].substring(space+1);
				density1 = value;
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
				space = splitDesc[i].lastIndexOf(" ");
				value = splitDesc[i].substring(space+1);
				density2 = value;
				displayDocument.put("density", density1+"~"+density2);
				lsl++;
			}
			
			//Part name extraction
			else if(splitDesc[i].contains("PART") && splitDesc[i].contains("NAME") && partname==0){
				i++;
				displayDocument.put("Part Name", splitDesc[i]);
				partname++;
			}
				
		}		
		return displayDocument;
	}
	

}
