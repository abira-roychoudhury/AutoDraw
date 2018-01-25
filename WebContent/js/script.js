
$(function(){
	
	var aspectRatio;
	var croppedImageBase64;
	var bubbleValue = {};
	var countOfBubble = 0;
	var imgNoBubbles;
	var imgWithBubbles;
	
	
function draw(base64) {
	var image = document.getElementById("image");		
	var imgObj = new Image();	
	imgObj.src = base64;

  	var canvas = document.createElement('canvas');  	
  	if (canvas.getContext) {
	    var ctx = canvas.getContext('2d');
	  	
	    imgObj.onload = function(){
	    	
	    	canvas.height = imgObj.height;
	    	canvas.width = imgObj.width;
	    	//console.log("executing height");

	    	image.height = (image.width / imgObj.width) * imgObj.height;
	    	
	    	aspectRatioR = image.width/image.height;
	    	aspectRatio = image.width / imgObj.width;
	    	
		  	/*console.log(image.height)
		  	console.log(image.width)
		  	console.log(imgObj.height)
		  	console.log(imgObj.width)*/
		  	
		  	ctx.drawImage(imgObj, 0, 0, imgObj.width, imgObj.height);
		  
		  	image.src = canvas.toDataURL();
		  	
		  	createImageWithoutBubbles();
	   };
  	}
}


draw(img);

function createImageWithoutBubbles(){
	
	console.log("inside createImageWithoutBubbles")
	
	var image = new Image();  
	image.src = img;
	
	imgNoBubbles = new Image();
	imgWithBubbles = new Image();
	
  	var canvas = document.createElement('canvas');
  	
	if (canvas.getContext) {
		var ctx = canvas.getContext('2d');

		image.onload = function() {
			
			imgWithBubbles.src = image.src;
	  	
	    	canvas.height = image.height;
	    	canvas.width = image.width;
	    	
		    ctx.drawImage(image, 0, 0, image.width, image.height)
		    ctx.fillStyle="white";
		    
			ctx.lineWidth = (image.width / 500 ) * 0.5;
			
			
			for(var i=0; i<numberOfBubbles; i++){
				
				var coordinate = coordinatesOfBubbles[i];
				console.log(coordinate);
				var ox = coordinate.x1;
				var oy = coordinate.y1;
				var owidth = coordinate.x2 - coordinate.x1;
				var oheight =  coordinate.y2 - coordinate.y1;
		
		   		ctx.fillRect(ox, oy, owidth,oheight);	
		   		
			}
			imgNoBubbles.src = canvas.toDataURL();
		
	  	};
		
	}   
   
}


function drawPolyies(base64){
	
	console.log("inside polyies")
	
	var image = document.getElementById("image");
	
	var img = new Image();  
	img.src = base64;
	
  	var canvas = document.createElement('canvas');
  	
	if (canvas.getContext) {
		var ctx = canvas.getContext('2d');

		img.onload = function() {
	  	
	    	canvas.height = img.height;
	    	canvas.width = img.width;

	    	/*owidth = (owidth - ox);
			oheight = (oheight - oy);*/
			
	    	var ratio = (image.width / img.width);
	    	
	   		image.height = ratio * img.height;
	    	
		    ctx.drawImage(img, 0, 0, img.width, img.height)
		  	//ctx.strokeRect(ox, oy, owidth, oheight);
			ctx.strokeStyle="red";
		    
			ctx.lineWidth = (img.width / 500 ) * 0.5;
			
			
			for(var i=0; i<numberOfBubbles; i++){
				
				var coordinate = coordinatesOfValues[i];
				console.log(coordinate);
				var ox = coordinate.x1;
				var oy = coordinate.y1;
				var owidth = coordinate.x2 - coordinate.x1;
				var oheight =  coordinate.y2 - coordinate.y1;
				/*
				ox = ox * ratio;
				oy = oy * ratio;
				owidth = owidth * ratio;
				oheight = oheight * ratio;
				*/
		   		ctx.strokeRect(ox, oy, owidth,oheight);	
		   		
		   		console.log("drawing :"+ ox +","+ oy+","+ owidth+","+oheight )
			}
		  	image.src = canvas.toDataURL();
		  	
	  	};
		
	}   
   
}


function getSelection(image, selection) {
	console.log("inside getSelection");

	if (parseInt(selection.width) > 0) {
        // Show image preview
        if (selection.x1 != 0 && selection.y1 != 0 && selection.width != 0 && selection.height != 0) {
        	var x1 = selection.x1;
        	var y1 = selection.y1;
        	var width = selection.width;
        	var height = selection.height;
        	        	
        	var canvas = document.createElement('canvas');
        	var canvasContext = canvas.getContext('2d'); 
        	
	    	
        	var newHeight = height/aspectRatio;
        	var newWidth = width/aspectRatio;
        	x1 = (x1/aspectRatio);
        	y1 = (y1/aspectRatio);
        	
        	canvas.height = newHeight;
	    	canvas.width = newWidth;
	    	
	    	image.src = img;
        	
	    	canvasContext.drawImage(imgNoBubbles, x1, y1, newWidth, newHeight, 0, 0, newWidth, newHeight);
	    	
	    	var croppedImage = document.getElementById("croppedImage");
	    	 
	    	croppedImage.height = (croppedImage.width/ newWidth) * newHeight;
	    	
	    	croppedImage.src = canvas.toDataURL();
	    	croppedImageBase64 = canvas.toDataURL();	    	
        	
        }       
    }
};

$('#image').imgAreaSelect({
    handles: true,
    onSelectEnd: getSelection,
});


$("#submit").click
(
    function()
    {
    	$('#bubbleValue').val("");
    	$("#submitLoader").show();
      	$('input, button, textarea').attr("disabled", true);
    	$(".container").addClass('haze');
    	$.ajax
        (
            {
                url:'/AutoDraw/SelectionOCR',
                data: {"croppedImageBase64":croppedImageBase64},
                type:'post',
                success:function(data){
                	console.log(data);
                	$('#bubbleValue').val(data); 
                	$("#submitLoader").hide();
                	$('input, button, textarea').attr("disabled", false);
                	$('.container').removeClass('haze');
                },
                error:function(err){
                	console.log(err)
                	$("#submitLoader").hide();
                	$('input, button, textarea').attr("disabled", false);
                	$('.container').removeClass('haze');
                	
                }
            }
        );
    }
);


$("#excel").click
(
    function()
    {  	
    	var partno = $('#partno').val();
    	var partname = $('#partname').val();
    	var processname = $('#processname').val(); 
    	var customerpartno = $('#customerpartno').val();
    	var releasedate = $('#date').val();
    	
    	//sorting the bubble value with its key
    	var sortedBubbleValue = {}
    	Object.keys(bubbleValue).sort().forEach(function(key) {
    		sortedBubbleValue[key] = bubbleValue[key];
    		});
    	var bubbleValueString = JSON.stringify(sortedBubbleValue);
    	console.log(bubbleValueString);
    	
    	$('#excelLabel').text("");
    	$("#submitLoader").show();
      	$('input, button, textarea').attr("disabled", true);
    	$(".container").addClass('haze');
    	
    	$.ajax
        (
            {
                url:'/AutoDraw/PushToExcel',
                data: {"partno":partno, "partname":partname, "processname":processname, "customerpartno":customerpartno, "bubbleValueString":bubbleValueString,"numberOfBubbles":numberOfBubbles,
                		"releasedate":releasedate},
                type:'post',
                success:function(data){
                	console.log(data);
                	$('#excelLabel').text(data); 
                	$("#submitLoader").hide();
                	$('input, button, textarea').attr("disabled", false);
                	$('.container').removeClass('haze');
                },
                error:function(err){
                	console.log(err)
                	$("#submitLoader").hide();
                	$('input, button, textarea').attr("disabled", false);
                	$('.container').removeClass('haze');
                	
                }
            }
        );
    }
); 



$('#next1').click(function(){
	$('#block1').hide();
	$('#block2').show();
	drawPolyies(img);
});

$('#next2').click(function(){
	$('#block2').hide();
	$('#block3').show();
	draw(img);
	focusOnBubble(countOfBubble);	
});


$('#skip').click(function(){
	var presentBubbleNumber = $("#bubbleNumber").val();
	bubbleValue[presentBubbleNumber] = "";
	countOfBubble = countOfBubble + 1;
	if(countOfBubble>=numberOfBubbles)
		$('#bubbleLabel').text("No More bubbles could be detected. Please select any other bubble manually.");
	else
		focusOnBubble(countOfBubble);
});

$('#save').click(function(){
	var presentBubbleNumber = $("#bubbleNumber").val();
	bubbleValue[presentBubbleNumber] = $("#bubbleValue").val();
	countOfBubble = countOfBubble + 1;
	if(countOfBubble>=numberOfBubbles)
		{
			$('#bubbleLabel').text("No More bubbles could be detected. Please select any other bubble manually.");
			console.log("bubble value ");
			console.log(bubbleValue)
		}
	else
		focusOnBubble(countOfBubble);	
});




function focusOnBubble(index){
	console.log("inside focus function")
	var coordinate = coordinatesOfValues[index];
	var ox1 = coordinate.x1*aspectRatio;
	var oy1 = coordinate.y1*aspectRatio;
	var ox2 = coordinate.x2*aspectRatio;
	var oy2 = coordinate.y2*aspectRatio;
	
	console.log()
	$('#image').imgAreaSelect({ x1: ox1, y1: oy1, x2: ox2, y2: oy2,
		handles: true,
		});
	
	var width = ox1 - ox2;
	var height = oy1 - oy2;

	var orignalox1 = coordinate.x1;
	var orignaloy1 = coordinate.y1;
	var originalWidth = coordinate.x2 - coordinate.x1;
	var originalHeight = coordinate.y2 - coordinate.y1;

	var canvas = document.createElement('canvas');
	var canvasContext = canvas.getContext('2d'); 
	
	canvas.height = originalHeight;
	canvas.width = originalWidth;
	
	canvasContext.drawImage(imgWithBubbles, orignalox1, orignaloy1, originalWidth, originalHeight, 0, 0, originalWidth, originalHeight);
	
	var croppedImage = document.getElementById("croppedImage");	 
	croppedImage.height = (croppedImage.width/ originalWidth) * originalHeight;	
	croppedImage.src = canvas.toDataURL();
	
	console.log("inside looping ##############################################");

	var canvasValues = document.createElement('canvas');
	var canvasValuesContext = canvasValues.getContext('2d'); 
	
	canvasValues.height = originalHeight;
	canvasValues.width = originalWidth;

	canvasValuesContext.drawImage(imgNoBubbles, orignalox1, orignaloy1, originalWidth, originalHeight, 0, 0, originalWidth, originalHeight);
	
	croppedImageBase64 = canvasValues.toDataURL();	
	
	console.log("inside looping ##############################################");
	
	var coordinateBubble = coordinatesOfBubbles[index];
	var orignalBubblex1 = coordinateBubble.x1;
	var orignalBubbley1 = coordinateBubble.y1;
	var originalBubbleWidth = coordinateBubble.x2 - coordinateBubble.x1;
	var originalBubbleHeight = coordinateBubble.y2 - coordinateBubble.y1;
	
	var canvasBubble = document.createElement('canvas');
	var canvasBubbleContext = canvasBubble.getContext('2d'); 
	
	canvasBubble.height = originalBubbleHeight;
	canvasBubble.width = originalBubbleWidth;
	
		
	canvasBubbleContext.drawImage(imgWithBubbles, orignalBubblex1, orignalBubbley1, originalBubbleWidth, originalBubbleHeight, 0, 0, originalBubbleWidth, originalBubbleHeight);
	croppedBubbleBase64 = canvasBubble.toDataURL();
	//console.log(croppedBubbleBase64);
	
	
	$('#bubbleValue').val("");
	$('#bubbleNumber').val("");
	$("#submitLoader").show();
  	$('input, button, textarea').attr("disabled", true);
	$(".container").addClass('haze');
	$.ajax
    (
        {
            url:'/AutoDraw/BubbleDataOCR',
            data: {"croppedImageBase64":croppedImageBase64, "croppedBubbleBase64":croppedBubbleBase64},
            type:'post',
            success:function(data){
            	console.log(data)
            	data = JSON.parse(data);
            	$('#bubbleValue').val(data.bubbleValue); 
            	$('#bubbleNumber').val(data.bubbleNumber);
            	$("#submitLoader").hide();
            	$('input, button, textarea').attr("disabled", false);
            	$('.container').removeClass('haze');
            },
            error:function(err){
            	console.log(err)
            	$("#submitLoader").hide();
            	$('input, button, textarea').attr("disabled", false);
            	$('.container').removeClass('haze');
            	
            }
        }
    );
}

});