package apiClass;

import java.util.Arrays;
import java.util.LinkedHashMap;

import modal.Constants;


public class DocumentTemplating {

	public LinkedHashMap<String, String> parseContent(String descriptionStr) {
		LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
		
		//String compactionVariation[] = {"Compacting","Compactian","Compaction","onpaction","ompaction"};
		
		String splitDesc[] = descriptionStr.split("\\n");
		int partno=0, processname=0,partname=0,customerPartNo=0,date=0,mixno=0,press=0;
		String extract = "";
		System.out.println("*******************************************************");
		for(int i=0; i<splitDesc.length;i++)
		{
			System.out.println(splitDesc[i]);
			//partno extraction
			if(splitDesc[i].contains("DRG") && partno==0){
				try{
					extract = splitDesc[i].substring(splitDesc[i].indexOf("DRG")+5).trim();
				}catch (StringIndexOutOfBoundsException e) {
					extract = splitDesc[++i].trim();
				}
				if(extract.length()<5)
					extract = splitDesc[++i].trim();
				displayDocument.put("Part No", extract);
				partno++;
			}
					
			if(splitDesc[i].contains("DRS")  && partno==0){
				try{
					extract = splitDesc[i].substring(splitDesc[i].indexOf("DRS")+5).trim();
				}catch (StringIndexOutOfBoundsException e) {
					extract = splitDesc[++i].trim();
				}
				if(extract.length()<5)
					extract = splitDesc[++i].trim();
				displayDocument.put("Part No", extract);
				partno++;
			}
			if(splitDesc[i].contains("INA") && displayDocument.get("Part No").length()<5){
				extract = splitDesc[i].trim();
				displayDocument.put("Part No", extract);
			}
			
			//revision date extraction
			if(splitDesc[i].matches("\\d\\d.\\d\\d.\\d\\d\\d\\d") && date==0) {
				displayDocument.put("Release Date", splitDesc[i]);
				date++;
			}
			
			//process name extraction
			else if(splitDesc[i].contains("PROCESS") && processname==0){
				extract = splitDesc[++i];
				if(splitDesc[++i].contains("INSPECTION"))
					extract = extract + " " + "INSPECTION";
				displayDocument.put("Process Name", extract);
				processname++;				
			}
			
			//Customer Part Number extraction
			else if(splitDesc[i].contains("CUSTOMER") && customerPartNo==0){
				while(Arrays.stream(Constants.customerPartNo).parallel().anyMatch(splitDesc[i]::contains)) {
					i++;
				}
				int space = splitDesc[i].indexOf(" ");
				if(space<0)
					extract = splitDesc[i].trim();
				else
					extract = splitDesc[i].substring(0,space).trim();
				displayDocument.put("Customer Part No", extract);
				customerPartNo++;
			}
			
			//Part name extraction
			else if(splitDesc[i].contains("PART") && splitDesc[i].contains("NAME") && partname==0){
				while(Arrays.stream(Constants.partName).parallel().anyMatch(splitDesc[i]::contains)) {
					i++;
				}
				extract = splitDesc[i].trim();
				displayDocument.put("Part Name", extract);
				partname++;
			}
			
			//Mix number extraction
			else if(splitDesc[i].contains("MIX") && splitDesc[++i].contains("NUMBER") && mixno==0){
				extract = splitDesc[++i].trim();
				displayDocument.put("Mix Number", extract);
				mixno++;
			}
			
			//Mix number extraction
			else if(splitDesc[i].contains("PRESS") && press==0){
				extract = splitDesc[++i].trim();
				displayDocument.put("Press", extract);
				press++;
			}
				
		}		
		return displayDocument;
	}
	

}
