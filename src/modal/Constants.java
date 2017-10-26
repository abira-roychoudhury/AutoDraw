package modal;

public class Constants {
	
	
	
	//Inside servlet Processing2
	public static final String imgFile = "image";
	public static final String logFile = "Log_File.txt";
	public static final String jpg = "jpg";
	public static final String compress = "compress";
	public static final String jsonFileOfStateCity = "list_of_cities_and_state.json";
	
	
	public static final String dot = ".";
	public static final String colon = ":";
	public static final String equal = "=";
	public static final String doubleQuote = "\"";
	public static final String newLine = "\n";
	public static final String eof = "EOF";
	public static final String space = " ";
	
	
	
	
	//request attributes parameter
	public static final String fileType = "fileType";
	public static final String uploadImage = "Upload Image";
	public static final String imagePreprocessing = "Image Preprocessing";
	public static final String base64conversion = "Base 64 conversion";
	public static final String visionAPICall = "Vision API Call";
	public static final String imgBase64 = "imgBase64";
	public static final String document = "document";
	public static final String templating = "Templating";
	public static final String displaydocument = "displaydocument";
	public static final String coordinates = "coordinates";
	public static final String jsonCoord = "jsonCoord";
	public static final String description = "Description";
	public static final String contentType = "text/plain";
	
	//error message
	public static final String fileTypeMismatch1 = "The Image file uploaded and the File Type selected does not match";
	public static final String fileTypeMismatchAadharCard ="OR The aadhar number could not be detected properly from the image uploaded";
	public static final String pushOCRDataError = "There has been an error while submitting. Please try again";
	public static final String pushOCRDataSuccess = "The data was successfully submitted";
	
	//compress image dimensions
	public static final String compressWidth = "compressWidth";
	public static final String compressHeight = "compressHeight";
	
	
	//Vision API response JSON Keys
	public static class VisionResponse{
		public static final String body = "body";
		public static final String responses = "responses";
		public static final String textAnnotations = "textAnnotations";
		public static final String description = "description";	
		public static final String boundingPoly = "boundingPoly";
		public static final String vertices = "vertices";
		public static final String x = "x";
		public static final String y = "y";
	}
	
		
	//Vision API request constants
	public static class VisionRequest{
		public static final String visionApiUrl = "https://vision.googleapis.com/v1/images:annotate?";
		public static final String visionApiKey = "key=AIzaSyC3IyBtatcC3pWGseGIfS3kfxqSpnaUUNA";
		public static final String content = "content";
		public static final String image = "image";
		public static final String type = "type";
		public static final String maxResults = "maxResults";
		public static final String textDetection = "TEXT_DETECTION";
		public static final String documentTextDetection = "DOCUMENT_TEXT_DETECTION";	
		public static final String requests = "requests";
		public static final String features = "features";
		public static final String requestMethod = "POST";
		public static final String contentType = "Content-Type";
		public static final String contentTypeValue = "application/json";
		
	} 
	
	//Proxy settings
	public static final String proxyHost = "ptproxy.persistent.co.in"; 
	public static final String proxyPort = "8080";

	//date format
	public static final String dateFormatSlash = "dd/MM/yyyy";
	public static final String dateFormatDashed = "dd-MM-yyyy";
	
	//gender
	public static final String male = "MALE";
	public static final String female = "FEMALE";
	
	public static final String birth = "Birth";
	public static final String year = "Year";
	public static final String date = "Date";
	public static final String dob = "DOB";	
	public static final String india = "INDIA";
	public static final String govt = "GOVTOF";
	public static final String department = "DEPARTMENT";
	public static final String ITdepartment = "INCOME TAX DEPARTMENT";
	
	public static class DisplayImageParameters{		
		public static final int minimumImageSize = 4000000;     //minimum image size
		public static final int maximumImageWidth = 1500;       //maximum image width in pixel
		public static final double scaledImageWidth = 1000.0;        //width of image after down scaling
	}
	

	//Pan Card Constants
	public static class PanCard{
		public static final String panCard = "PanCard";
		public static final String name = "Name";
		public static final String firstName = "First Name";
		public static final String lastName = "Last Name";
		public static final String middleName = "Middle Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String panNumber = "Pan Number";
		public static final String pan = "PERMANENT ACCOUNT NUMBER";
		public static final String father = "Father";
		public static final String panAbb = "PAN";
		public static final String tax = "Tax";
		public static final String income = "Income";
	}
	//Aadhar Card Constants
	public static class AadharCardPage1{
		public static final String aadharCard = "AadharCardPage";
		public static final String name = "Name";
		public static final String firstName = "First Name";
		public static final String lastName = "Last Name";
		public static final String middleName = "Middle Name";
		public static final String year = "Year of Birth";
		public static final String dob = "DOB";
		public static final String gender = "Gender";
		public static final String aadharNumber = "Aadhar Number";
		public static final String government = "Government";
		public static final String address = "Address";
		public static final String father = "Father";
		public static final String unique = "Unique";
		public static final String authority = "Authority";
		public static final String identification = "Identification";
		public static final String qrUid = "uid";
		public static final String qrName = "name";
		public static final String qrFather = "father";
		public static final String qrGender = "gender";
		public static final String qrYob = "yob";
		public static final String qrDob = "dob";
		public static final String qrCo = "co";
		public static final String qrHouse = "house";
		public static final String qrStreet = "street";
		public static final String qrLm = "lm";
		public static final String qrLoc = "loc";
		public static final String qrVtc = "vtc";
		public static final String qrPo = "po";
		public static final String qrDist = "dist";
		public static final String qrSubdist = "subdist";
		public static final String qrState = "state";
		public static final String qrPc = "pc";
		
		
	}
	//Driving License Constants
	public static class DrivingLicense{
		public static final String drivingLicense = "DrivingLicense";
		public static final String name = "Name";
		public static final String middleName = "S/D/W of";
		public static final String address = "Address";
		public static final String dob = "DOB";
		public static final String pin = "PIN";
		public static final String bloodGroup = "Blood Group";
		public static final String add = "add";
		public static final String of = "of";
		public static final String driving = "Driving";
	}
	//Voter Card Constants
	public static class VoterCard{
		public static final String voterCard = "VoterCard";
		public static final String electorName = "Elector's Name";
		public static final String firstName = "First Name";
		public static final String lastName = "Last Name";
		public static final String middleName = "Middle Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String sex = "Sex";
		public static final String voterId = "Voter ID";
		public static final String commission = "Commission";
		public static final String card = "Card";
		public static final String elector = "Elector";
		public static final String name = "Name";
		public static final String father = "Father";
		public static final String husband = "Husband";
		public static final String mother = "Mother";
		public static final String age = "Age";
		public static final String years = "Years";
		public static final String address = "Address";
		public static final String address1 = "Addess";
		public static final String address2 = "Adress";
		public static final String address3 = "Addres";
		public static final String address4 = "Adcress";
		public static final String address5 = "ddress";
	}
	//ITR Form Constants
	public static class ITReturn{
		public static final String iTReturn = "ITReturn";
		public static final String itr = "ITR";
		public static final String name = "Name";
		public static final String firstName = "First Name";
		public static final String lastName = "Last Name";
		public static final String middleName = "Middle Name";
		public static final String of = "Of";
		public static final String deduction1 = "Deductions";
		public static final String deduction2 = "Dedactions";
		public static final String verification = "Verification";
		public static final String ward[] = {"Designation","Ward","Circle","AO"};
		public static final String original = "Original";
		public static final String revised = "Revised";
		public static final String eFiling[] = {"E-filing","Acknowledgement"};
		public static final String date[] = {"Date","DD-","-YYYY"};
		public static final String closingBracket = ")";
		public static final String status[] = {"Status","States"}; 
		public static final String grossIncome[] = {"Gross","Total","Income"};
		public static final String notRequiredRows[] = {"Name","PAN","Flat","Door","Block","Premises",
														"Building","Village","Road","Street","Post","Office",
														"Form","No","which","has","been","electronically","transmitted",
														"Town","City","District","Area","Locality","State","ZipCode","Aadhar","Enrollment"
														};
		public static final String statusType[] = {"Individual","HUF","Other than individual/HUF","ERI","External Agency","Tax Professional",
													"TDS User"};
		public static final String pan = "PAN";
		public static final String aadharNumber = "Aadhar Number";
		public static final String assessmentYear = "Assessment Year";
		public static final String address = "Address";
		public static final String designation = "Designation of AO (Ward/Circle)";
		public static final String orgRev = "Original or Revised";
		public static final String eFilingAck = "E-filling Acknowledgement No";
		public static final String eFilingDate = "Date(DD-MM-YYYY)";
		public static final String grossTotalIncome = "Gross Total Income";		
	}
	
	public static class Address{
		public static final String address = "Address";
		public static final String city = "City";
		public static final String state = "State";
		public static final String zipCode = "Zip Code";
	}
	
	//TRY all combinations of INDIA
	public static final String india1 = "INDIA";
	public static final String india2 = "INDLA";
	public static final String india3 = "LNDIA";
	public static final String india4 = "LNDLA";
	public static final String india5 = "INOIA";
	public static final String india6 = "INOLA";
	public static final String india7 = "LNOIA";
	public static final String india8 = "LNOLA";
	
	public static final String originalJson = "originalJson";
	public static final String correctedJson = "correctedJson";
	
	
	public static class SFRequest{
		public static final String requestMethod = "POST";
		public static final String contentType = "Content-Type";
		public static final String contentTypeValue = "application/json";
		public static final String authorization = "Authorization";
		public static final String bearer = "Bearer ";
		
		
		public static final String batchRequests = "batchRequests";
		public static final String richInput = "richInput";
		public static final String url = "url";
		public static final String urlValueToReceiveAccessToken = "v39.0/sobjects/EKYCStaging__c/";
		public static final String method = "method";
		public static final String methodType = "PATCH";
		public static final String accessTokenKey = "access_token";
		
		
		public static final String hasErrorKey = "hasErrors";
		
		public static final String AadharNo__c = "AadharNo__c";
		public static final String AadharNoOld__c = "AadharNoOld__c";
		public static final String Address__c = "Address__c";
		public static final String AddressOld__c = "AddressOld__c";
		public static final String AssessmentYear__c = "AssessmentYear__c";
		public static final String AssessmentYearOld__c = "AssessmentYearOld__c";
		public static final String City__c = "City__c";
		public static final String CityOld__c = "CityOld__c";
		public static final String Date__c = "Date__c";
		public static final String DateOld__c = "DateOld__c";
		public static final String DesignationofAO__c = "DesignationofAO__c";
		public static final String DesignationofAOOld__c = "DesignationofAOOld__c";
		public static final String DOBOrAge__c = "DOBOrAge__c";
		public static final String DOBOrAgeOld__c = "DOBOrAgeOld__c";
		public static final String EfillingAcknowledgementNo__c = "EfillingAcknowledgementNo__c";
		public static final String EfillingAcknowledgementNoOld__c = "EfillingAcknowledgementNoOld__c";
		public static final String FatherName__c = "FatherName__c";
		public static final String FatherNameOld__c = "FatherNameOld__c";
		public static final String FirstName__c = "FirstName__c";
		public static final String FirstNameOld__c = "FirstNameOld__c";
		public static final String FullName__c = "FullName__c";
		public static final String FullNameOld__c = "FullNameOld__c";
		public static final String Gender__c = "Gender__c";
		public static final String GenderOld__c = "GenderOld__c";
		public static final String GrossTotalIncome__c = "GrossTotalIncome__c";
		public static final String GrossTotalIncomeOld__c = "GrossTotalIncomeOld__c";
		public static final String LastName__c = "LastName__c";
		public static final String LastNameOld__c = "LastNameOld__c";
		public static final String MiddleName__c = "MiddleName__c";
		public static final String MiddleNameOld__c = "MiddleNameOld__c";
		public static final String OriginalOrRevised__c = "OriginalOrRevised__c";
		public static final String OriginalOrRevisedOld__c = "OriginalOrRevisedOld__c";
		public static final String PANNo__c = "PANNo__c";
		public static final String PANNoOld__c = "PANNoOld__c";
		public static final String State__c = "State__c";
		public static final String StateOld__c = "StateOld__c";
		public static final String Status__c = "Status__c";
		public static final String StatusOld__c = "StatusOld__c";
		public static final String VoterCardNo__c = "VoterCardNo__c";
		public static final String VoterCardNoOld__c = "VoterCardNoOld__c";
		public static final String ZipCodeOld__c = "ZipCodeOld__c";
		public static final String ZipCode__c = "ZipCode__c";
		
		public static final String AdvanceTax__c = "AdvanceTax__c";
		public static final String AdvanceTaxOld__c = "AdvanceTaxOld__c";
		public static final String CurrentYearLoss__c = "CurrentYearLoss__c";
		public static final String CurrentYearLossOld__c = "CurrentYearLossOld__c";
		public static final String DeductionUnderChapterVI__c = "DeductionUnderChapterVI__c";
		public static final String DeductionUnderChapterVIOld__c = "DeductionUnderChapterVIOld__c";
		public static final String ExemptIncome__c = "ExemptIncome__c";
		public static final String ExemptIncomeOld__c = "ExemptIncomeOld__c";
		public static final String FormNoWhichHasBeenTransmitted__c = "FormNoWhichHasBeenTransmitted__c";
		public static final String FormNoWhichHasBeenTransmittedOld__c = "FormNoWhichHasBeenTransmittedOld__c";
		public static final String InterestPayable__c = "InterestPayable__c";
		public static final String InterestPayableOld__c = "InterestPayableOld__c";
		public static final String NetTaxPayable__c = "NetTaxPayable__c";
		public static final String NetTaxPayableOld__c = "NetTaxPayableOld__c";
		public static final String Place__c = "Place__c";
		public static final String PlaceOld__c = "PlaceOld__c";
		public static final String Refund__c = "Refund__c";
		public static final String RefundOld__c = "RefundOld__c";
		public static final String SelfAsssessmentTax__c = "SelfAsssessmentTax__c";
		public static final String SelfAsssessmentTaxOld__c = "SelfAsssessmentTaxOld__c";
		public static final String TaxPayable__c = "TaxPayable__c";
		public static final String TaxPayableOld__c = "TaxPayableOld__c";
		public static final String TCS__c = "TCS__c";
		public static final String TCSOld__c = "TCSOld__c";
		public static final String TDC__c = "TDC__c";
		public static final String TDCOld__c = "TDCOld__c";
		public static final String TotalIncome__c = "TotalIncome__c";
		public static final String TotalIncomeOld__c = "TotalIncomeOld__c";
		public static final String TotalTaxAndInterestPayable__c = "TotalTaxAndInterestPayable__c";
		public static final String TotalTaxAndInterestPayableOld__c = "TotalTaxAndInterestPayableOld__c";
		public static final String TotalTaxesPaid__c = "TotalTaxesPaid__c";
		public static final String TotalTaxesPaidOld__c = "TotalTaxesPaidOld__c";
		
		
			
	}
	
}
