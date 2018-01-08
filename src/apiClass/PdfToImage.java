package apiClass;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import modal.Constants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfToImage {

	public static void convert(File pdfFile, File imgFile) throws Exception {
		//Loading an existing PDF document
	      //File file = new File("D:/autocad draw/pdf drawings/Production Drawing_Example_pdf.pdf");
	      PDDocument document = PDDocument.load(pdfFile);
	       
	      //Instantiating the PDFRenderer class
	      PDFRenderer renderer = new PDFRenderer(document);
	      
	      // set resolution (in DPI) and Rendering an image from the PDF document
	      BufferedImage src = renderer.renderImageWithDPI(0, 300);
	      
	     //Rendering an image from the PDF document
		 //BufferedImage image = renderer.renderImage(0);
	      
	      
	      int srcWidth = src.getWidth();
	      int srcHeight = src.getHeight();
	      BufferedImage dest = new BufferedImage(srcHeight, srcWidth, src.getType());
	       
	      for (int row = 0; row < srcHeight-1; row++)
	    	  for (int col = srcWidth - 1; col >=0 ; col--)	    	
	    		  dest.setRGB(row,srcWidth-1-col,src.getRGB(col,row));
	      
	      
	      
	      //Writing the image to a file
	      ImageIO.write(dest, "JPG", imgFile);
	      //ImageIO.write(dest, "JPG", new File("D:/autocad draw/image drawings/Production Drawing_ExampleByCode2.jpg"));
	       
	      System.out.println("Image created");
	       
	      //Closing the document
	      document.close();
	}
}
