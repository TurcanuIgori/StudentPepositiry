var request = null;
function createRequest(){
	try{
		request=new XMLHttpRequest();		
	}catch(trymicrosoft){
		try{
			request=new ActiveXObject("Msxml2.XMLHTTP");
		} catch(othermicrosoft){
			try{
				request=new ActiveXObject("Microsoft.XMLHTTP");
			}catch(failed){
				request=null;
			}
		}
	}
}
function getAbonamentInfo(id){	
	createRequest();
	if(request==null){
		alert("Error creating request!");
	}		
	var url = "/list?action=LIST&id=" + id;		
	request.open("GET", url, true);	
	request.onreadystatechange=updateAbonament;		
	request.send(null);
}
function updateAbonament(){	
	if(request.readyState == 4){			
		if(request.status == 200){
			var jsonData=eval('(' + request.responseText + ')');			
			replaceText(document.getElementById("studentName"), jsonData.firstName + " " + jsonData.lastName  + " - Student Abonament");	
			var startDate = new Date(jsonData.abonament.startDate);
			var endDate = new Date(jsonData.abonament.endDate);
			document.getElementById("startDate").value=(startDate.getMonth() + 1) + '/' + startDate.getDate()+ '/' +  startDate.getFullYear();
			document.getElementById("endDate").value=(endDate.getMonth() + 1) + '/' + endDate.getDate()+ '/' +  endDate.getFullYear();
			document.getElementById("abonament_id").value=jsonData.abonament.abonament_id;
			document.getElementById(jsonData.abonament.status).setAttribute("selected", "selected");		
		}
	}	
}
function getMarkInfo(id) {
	createRequest();
	if (request == null) {
		alert("Error creating request!");
	}	
	var url = "/list?action=GET_MARK&id=" + id;
	request.open("GET", url, true);
	request.onreadystatechange = updateMark;	
	request.send(null);
}

function updateMark(){
	resetForm(document.getElementById("discipline"));
	resetFormProf(document.getElementById("professor"));
	if(request.readyState == 4){		
		if(request.status == 200){				
			var jsonData=eval('(' + request.responseText + ')');			
			replaceText(document.getElementById("studentNameMark"), "Add Mark to student " + jsonData.firstName + " " + jsonData.lastName + " (Group: " + jsonData.group.name + ")");
			document.getElementById("studentIdMark").value=jsonData.student_id;
			var none = document.createElement("option");			
			none.value = "0";
			none.text = "(none)";					
			document.getElementById("discipline").add(none);			
			for (var i = 0; i < jsonData.disciplineList.length; i++){
				var opt = document.createElement("option");			
				opt.value = jsonData.disciplineList[i].discipline_id;
				opt.text = jsonData.disciplineList[i].title;					
				document.getElementById("discipline").add(opt);
			}
			
		}
	}
}
function getProfessorsInfo() {
	createRequest();
	var id = document.getElementById("discipline").value;	
	if (request == null) {
		alert("Error creating request!");
	}	
	var url = "/list?action=GET_PROFESSORS&id=" + id;
	request.open("GET", url, true);
	request.onreadystatechange = updateProfessors;
	request.send(null);
}

function updateProfessors(){	
	resetFormProf(document.getElementById("professor"));
	if(request.readyState == 4){		
		if(request.status == 200){				
			var jsonData=eval('(' + request.responseText + ')');			
			var none = document.createElement("option");			
			none.value = "0";
			none.text = "(none)";					
			document.getElementById("professor").add(none);
			
			for (var i = 0; i < jsonData.length; i++){
				var opt = document.createElement("option");			
				opt.value = jsonData[i].professor_id;
				opt.text = jsonData[i].firstName + " " + jsonData[i].lastName;					
				document.getElementById("professor").add(opt);
			}
			
		}
	}
}
function getStudentInfo(id){
	createRequest();
	if (request == null) {
		alert("Error creating request!");
	}
	reset();
	var url = "/list?action=GET_STUDENT&id=" + id;
	request.open("GET", url, true);
	request.onreadystatechange = updateStudent;
	request.send(null);
}
function updateStudent(){
	if(request.readyState == 4){			
		if(request.status == 200){			
			var jsonData=eval('(' + request.responseText + ')');			
			document.getElementById("firstName").value=jsonData.firstName;
			document.getElementById("lastName").value=jsonData.lastName;
			document.getElementById("idStudent").value=jsonData.student_id;
			var dob = new Date(jsonData.dob);
			document.getElementById("dob").value=(dob.getMonth() + 1) + '/' + dob.getDate()+ '/' +  dob.getFullYear();;
			document.getElementById("image").setAttribute("src", "/picture?id=" + jsonData.student_id);
			
			if(jsonData.genger.trim() == "M"){
				document.getElementById("male").setAttribute("checked", "checked");
			}
			if(jsonData.genger.trim() == "F"){
				document.getElementById("female").setAttribute("checked", "checked");
			}
			document.getElementById("country").value=jsonData.adress.country;
			document.getElementById("city").value=jsonData.adress.city;
			document.getElementById("adressLine").value=jsonData.adress.street;
			document.getElementById(jsonData.group.name).setAttribute("selected", "selected");
			
			for(var i = 0; i < jsonData.phoneList.length; i++){					
				document.getElementById(jsonData.phoneList[i].phoneType.name + (i+1)).setAttribute("selected", "selected");
				document.getElementById("addPhone" + (i+1)).value=jsonData.phoneList[i].number;
				if(i < (jsonData.phoneList.length-1)){
					
					addPhoneDiv();
				}
			}
		}
	}
}
function replaceText(el, text){
	if(el != null){
		clearText(el);
		var newNode = document.createTextNode(text);
		el.appendChild(newNode);
	}
}
function clearText(el){
	if(el != null){
		if(el.childNodes){
			for(var i = 0; i < el.childNodes.length; i++){
				var childNode = el.childNodes[i];
				el.removeChild(childNode);
			}
		}
	}
}
function getText(el){
	var text="";
	if(el != null){
		if(el.childNodes){
			for(var i=0; i < el.childNodes[i]; i++){
				var childNode = el.childNodes[i];
				if(childNode.nodeValue != null){
					text = text + childNode.nodeValue;
				}
			}
		}
	}
	return text;
}
					

