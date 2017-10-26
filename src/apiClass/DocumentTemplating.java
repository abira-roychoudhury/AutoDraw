package apiClass;

import java.util.Arrays;
import java.util.LinkedHashMap;

import modal.Constants;


public class DocumentTemplating {

	public LinkedHashMap<String, String> parseContent(String descriptionStr, String fileType, String filePath) {
		LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
		
		String compactionVariation[] = {"Compacting","Compactian","Compaction","onpaction","ompaction"};
		
		String splitDesc[] = descriptionStr.split("\\n");
		
		for(int i=0; i<splitDesc.length;i++)
		{
			if(Arrays.stream(compactionVariation).parallel().anyMatch(splitDesc[i]::contains)){
				String key = "",value = "";
				for(int j=0;j<splitDesc[i].length();j++){
					char c = splitDesc[i].charAt(j);
					if(Character.isDigit(c) || c =='.')
						value = value + c;
					else
						key = key + c;
				}
				boolean flag = true;
				while(flag){
					value = value + " ";
					i++;					
					for(int j=0;j<splitDesc[i].length();j++){
						char c = splitDesc[i].charAt(j);
						if(Character.isDigit(c) || c =='.' || c==' ')
							value = value + c;
						else{ 
							  flag = false; 
							  break;
							}
					}
				}
				i--;
				displayDocument.put(key, value);
			}
		}
		
		
		return displayDocument;
	}
	

}
