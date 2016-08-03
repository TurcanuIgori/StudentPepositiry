/**
 * 
 */

function textStatus(){	
	var text = getText(document.getElementById("textStatus"));
		alert(text);		
}

function resetForm(element){
	for (var i=0; i<=element.length; i++)
	{
		element.remove(i);
	}
}
function resetFormProf(element){
	for (var i=0; i<=element.length; i++)
	{
		element.remove(i);
	}
}

function reset() {
	document.getElementById("image").setAttribute("src", "Resources/img.png");
	document.getElementById("addStudent").reset();  	
	document.getElementById("female").removeAttribute("checked");
	document.getElementById("male").removeAttribute("checked");
	document.getElementById("noneGroup").setAttribute("selected", "selected");
	document.getElementById("nonephoneType1").setAttribute("selected", "selected");
	for(var i=2; i <= 4; i++){
		if(document.getElementById("selectPhoneType" + i)){
			document.getElementById("selectPhoneType" + i).remove();
			document.getElementById("addPhone" + i).remove();
			document.getElementById("TextBoxDiv" + i).remove();
			counter--;
		}
	}
};

    

//CHECK ALL SUDENTS
function checkAll(source){
		checkboxes=document.getElementsByName('check');
		for(var i=0, n=checkboxes.length; i<n; i++){
			checkboxes[i].checked=source.checked;	
		}		
	};

//ADD / DELETE PHONE / PHONE TYPE
   $(document).ready(function(){

	     $("#removeInput").click(function () {
	    if(counter==2){
	          alert("No more textbox to remove");
	          return false;
	      }   
	    counter--;
	    var i=2;

	        $("#TextBoxDiv" + counter).remove();

	     });

	   
	  });
   var counter = 2;
   function addPhoneDiv(){
	   if(counter > 4){
           alert("Only 4 textboxes allow");
           return false;
   }   
	  
	  var div = document.createElement('div');  
	  div.id = "TextBoxDiv" + counter;
	  var select = document.createElement("Select");
	  select.setAttribute("name", "selectPhoneType" + counter);
	  select.setAttribute("id", "selectPhoneType" + counter);
	  select.setAttribute("form", "addStudent");
	  var none = document.createElement("option");			
	  none.value = "0";
	  none.text = "PhoneType";					
	  select.add(none);
	  var opt1 = document.createElement("option");			
	  opt1.value = "1";
	  opt1.text = "Mobile";
	  opt1.id = "Mobile" + counter;
	  opt1.name = "Mobile" + counter;
	  select.add(opt1);
	  var opt2 = document.createElement("option");			
	  opt2.value = "2";
	  opt2.text = "Stationery";		
	  opt2.id = "Stationery" + counter;
	  opt2.name = "Stationery" + counter;
	  select.add(opt2);
	  div.appendChild(select);
	  var input = document.createElement("input");
	  input.setAttribute("type", "text");
	  input.setAttribute("form", "addStudent");
	  input.setAttribute("id", "addPhone" + counter);
	  input.setAttribute("name", "addPhone" + counter);
	  input.setAttribute("placeholder", "(123)45-67-89");
	  input.setAttribute("onkeyup", "mask('addPhone" + counter +"', '(000)00-00-00', event);");	  
	  div.appendChild(input);
	  document.getElementById("TextBoxesGroup").appendChild(div);
	  counter++;
	  
  }   
//For Data Field
   $(function() {
	    $( "#startDob" ).datepicker();
	  });
	$(function() {
	    $( "#endDob" ).datepicker();
	  });
	$(function() {
	    $( "#startDate" ).datepicker();
	  });
	$(function() {
	    $( "#endDate" ).datepicker();
	  });
	$(function() {
	    $( "#dob" ).datepicker();
	  });
//Validate
function checkAddStudentForm(form){
	//if input is empty
	if(form.firstName.value == ""){
		alert("Error: Name is empty!");
		form.firstName.focus();
		return false;
	}

	var re = /^[\w ]+$/;
	
	
	//If input contains inaccessible symbols 
	if(!re.test(form.firstName.value)){
		alert("Error: Name contais invalid symbols!");
		form.firstName.focus();
		return false;
	}	
	if(form.lastName.value == ""){
		alert("Error: Last Name is empty!");
		form.lastName.focus();
		return false;
	}
	
	
	//If input contains inaccessible symbols 
	if(!re.test(form.lastName.value)){
		alert("Error: Last Name contais invalid symbols!");
		form.lastName.focus();
		return false;
	}
	
	
	
	
	
	if(form.dob.value == ""){
		alert("Error: Data is empty!");
		form.dob.focus();
		return false;
	}
	if(form.Country.value == ""){
		alert("Error: Country is empty!");
		form.Country.focus();
		return false;
	}
	
	//If input contains inaccessible symbols 
	if(!re.test(form.Country.value)){
		alert("Error: Country contais invalid symbols!");
		form.Country.focus();
		return false;
	}
	if(form.dob.value == ""){
		alert("Error: Data is empty!");
		form.dob.focus();
		return false;
	}
	if(form.City.value == ""){
		alert("Error: City is empty!");
		form.City.focus();
		return false;
	}
	
	//If input contains inaccessible symbols 
	if(!re.test(form.City.value)){
		alert("Error: City contais invalid symbols!");
		form.City.focus();
		return false;
	}
	if(form.adressLine.value == ""){
		alert("Error: Adress Line is empty!");
		form.adressLine.focus();
		return false;
	}
	
	//If input contains inaccessible symbols 
	if(!re.test(form.adressLine.value)){
		alert("Error: Adress Line contais invalid symbols!");
		form.adressLine.focus();
		return false;
	}
	if(form.addPhone.value == ""){
		alert("Error: Phone is empty!");
		form.addPhone.focus();
		return false;
	}
	

}
function checkAbonametForm(form){
	if(form.startDate.value == ""){
		alert("Error: Data is empty!");
		form.startDate.focus();
		return false;
	}
	if(form.endDate.value == ""){
		alert("Error: Data is empty!");
		form.endDate.focus();
		return false;
	}
}
function checkMarkForm(form){
	if(form.mark.value == ""){
		alert("Error: Mark is empty!");
		form.mark.focus();
		return false;
	}
	return true;
};
//template phone
function mask(inputName, mask, evt) {
	mask='(000)00-00-00';
    try {
      var text = document.getElementById(inputName);
      var value = text.value;

      // If user pressed DEL or BACK SPACE, clean the value
      try {
        var e = (evt.which) ? evt.which : event.keyCode;
        if ( e == 46 || e == 8 ) {
          text.value = "";
          return;
        }
      } catch (e1) {} 

      var literalPattern=/[0\*]/;
      var numberPattern=/[0-9]/;
      var newValue = "";
      for (var vId = 0, mId = 0 ; mId < mask.length ; ) {
        if (mId >= value.length)
          break;
        // Number expected but got a different value, store only the valid portion
        if (mask[mId] == '0' && value[vId].match(numberPattern) == null) {
          break;
        }
        // Found a literal
        while (mask[mId].match(literalPattern) == null) {
          if (value[vId] == mask[mId])
            break;
        newValue += mask[mId++];
      }
      newValue += value[vId++];
      mId++;
    }
    text.value = newValue;
  } catch(e) {}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
