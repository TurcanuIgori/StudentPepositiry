<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Resources/style.css">
<script src="Resources/scripts.js" type="text/javascript"></script>
<script src="Resources/ajax.js" type="text/javascript"> </script>
<title>University</title>
</head>
<body>

	<h1>University</h1>
	<hr />
	<h2>List Students</h2>
	<div class="listDiv">
		<form action="/list" method="POST" id="searchForm">
			<input type="hidden" name="action" value="SEARCH" form="searchForm">
			<table align="left" class="table1">
				<tr>
					<td>Name:</td>
					<td><input type="text" name="partialName"
						placeholder="Partial Name" form="searchForm"></td>
					<td>Date of birth:</td>
					<td><input type="text" id="startDob" name="startDob" form="searchForm">
					</td>
					<td><input type="text" id="endDob" name="endDob" form="searchForm"></td>
				</tr>
				<tr>
					<td>Adress:</td>
					<td><input type="text" name="partialAdress"
						placeholder="Partial Adress" form="searchForm"></td>
					<td>Discipline:</td>
					<td><Select form="searchForm" name="discipline">
							<option value="0">(none)</option>
							<c:forEach items="${listDisciplines}" var="discipline">
								<option value="${discipline.discipline_id}">${discipline.title}</option>
							</c:forEach>
					</Select></td>
					<td><input type="text" name="disciplineAverage"
						placeholder="Discipline Average" form="searchForm"></td>
				</tr>
				<tr>
					<td>Group:</td>
					<td><Select name="group" form="searchForm">
							<option value="0">(none)</option>
							<c:forEach items="${listGroups}" var="group">
								<option value="${group.group_id}">${group.name}</option>
							</c:forEach>
					</Select></td>
					<td>Total Average:</td>
					<td><input type="text" name="totalAverage"
						placeholder="Total Average" form="searchForm"></td>
					<td></td>
				</tr>
				<tr>
					<td>Gender:</td>
					<td><input type="radio" name="gender" value="M">
						Male <input type="radio" name="gender" value="F">
						Female <input type="radio" name="gender" checked="checked"
						value="A"> All</td>
					<td></td>
					<td></td>
					<td><input type="submit" name="search" value="Search" form="searchForm">
						<input type="reset" name="reset" value="Reset" form="searchForm"></td>
				</tr>
			</table>
		</form>
		
		<table align="left" class="table1" id="tableList">
			<tr id="firstLine">
				<td><input type="checkbox" onClick="checkAll(this)"
					name="allStudents"></td>
				<td>Picture</td>
				<td>Name</td>
				<td>Birth Day</td>
				<td>Gender</td>
				<td>Adress</td>
				<td>Phone</td>
				<td>Library Abonament</td>
				<td>Marks</td>
				<td>Action</td>
			</tr>
			<c:forEach items="${listStudents}" var="student">
				<tr>
					<td><input type="checkbox" name="check"
						value="${student.student_id}" form="delete"></td>
					<td><img src="/picture?id=${student.student_id}" width="100">
					</td>
					<td>${student.student_id}${student.firstName}
						${student.lastName}</td>
					<td>${student.dob}</td>
					<td>${student.genger}</td>
					<td>${student.adress.country}<a>, or.</a>${student.adress.city}<a>,
							str.</a>${student.adress.street}</td>
					<td><c:forEach items="${student.phoneList}" var="phone">
							${phone.phoneType.name}: ${phone.number}<br />
						</c:forEach></td>
					<td>
						<!--	Window Abonament -->
						<div name="buttonAbonament" class="link">
							<a id="abonamentInfo" href="#addNewAbonament"
								onClick="getAbonamentInfo(${student.student_id})">${student.abonament.status}</a>
						</div> <br> <br> <c:if
							test="${student.abonament.status == 'Active'}">
							From: ${student.abonament.startDate}<br> To: ${student.abonament.endDate}		
						</c:if>
					</td>
					<td><c:forEach items="${student.mark}" var="mark">
							${mark.discipline.title}: ${mark.mark}<br />
						</c:forEach></td>
					<td>
						<!-- 	Window Add / Edit -->
						<div class="link">
							<a href="#addnewedit"  onClick="getStudentInfo(${student.student_id})">Edit</a>
						</div> <!-- 	Window Add Mark -->
						<div class="link">
							<a href="#addmark" onClick="getMarkInfo(${student.student_id})">Add Mark</a>							
						</div>
						
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td><div class="link">
						<a href="#addnewedit" onClick="reset();">Add New</a>
					</div></td>
				<td>
					<div class="link">
						<form action="/list" method="Post" id="delete">
							<input type="submit" name="delete1" value="Delete"> <input
								type="hidden" name="action" value="DELETE">
						</form>
					</div>
				</td>
				<td colspan="7"><span id="textStatus" onClick="textStatus();">${textStatus}</span></td>
				<td><div class="link">
				
						<a href="/list?action=DOWNLOAD_PDF">Download</a>
					</div></td>
			</tr>
		</table>

		
		<!--	Window Abonament -->
		<div id="addNewAbonament" name="addNewAbonament" class="modalDialog">
			<div>
				<a href="#close" title="Close" class="close">X</a><br /> 
				
				<form action="/list" method="POST" id="abonament"
					onSubmit="return checkAbonamentForm(this);">
					<input type="hidden" name="action" value="ADD_ABONAMENT" form="abonament">
					<table>
					<tr><div id="studentName"><div></tr>
						<tr>
							<td>Status:</td>
							<td><Select name="status" form="abonament">
									<option value="None" id="None" name="None">None</option>
									<option value="Active" id="Active" name="Active">Active</option>
									<option value="Suspended" id="Suspended" name="Suspended">Suspended</option>
							</Select></td>
						</tr>
						<tr>
						<input type="hidden" id="abonament_id" name="abonament_id" form="abonament">
							<td>Start Date:</td>
							<td><input type="text" id="startDate" name="startDate" form="abonament">								
						</tr>
						<tr>
							<td>End Date:</td>
							<td><input type="text" id="endDate" name="endDate" form="abonament">
							</td>
						</tr>
					</table>
					<input type="submit" name="addAbonament" value="Save" form="abonament">
				</form>
			</div>
		</div>
		<!-- 	Window Add Mark -->
		<div id="addmark" class="modalDialog">
			<div>
				<a href="#close" title="Close" class="close">X</a><br />				
				<form action="/list?action=ADD_MARK" method="POST" onSubmit="return checkMarkForm(this);" id="addMark">
				<input type="hidden" name="studentIdMark" id="studentIdMark" form="addMark">
					<table>
					<tr><div id="studentNameMark"></div></tr>
						<tr>
							<td>Discipline:</td>
							<td><Select onClick="getProfessorsInfo()" id="discipline" name="discipline" form="addMark">																		
							</Select>							
						</tr>
						<tr>
							<td>Proffessor:</td>
							<td><Select id="professor" name="professor" form="addMark">									
							</Select></td>							
						</tr>
						<tr>
							<td>Mark:</td>
							<td><input type="text" name="mark" placeholder="Mark"  form="addMark">
							</td>
						</tr>
					</table>
					<input type="submit" name="addMark" value="Save" form="addMark">
				</form>
			</div>
		</div>
		<!-- 	Window Add / Edit -->
		<form action="/list" id="addStudent" method="POST" enctype="multipart/form-data" onSubmit="return checkAddStudentForm(this);">
			<input type="hidden" name="action" value="ADD" form="addStudent">
			<input type="hidden" name="idStudent" id="idStudent" form="addStudent">
			<div id="addnewedit" class="modalDialog">
				<div>
					<a href="#close" title="Close" class="close">X</a>
					<h2>Add / Edit Student</h2>
					<table>
						<tr>
							<td>First Name:</td>
							<td><input type="text" id="firstName" name="firstName"
								placeholder="First Name" form="addStudent"></td>
							<td></td>
							<td>Group:</td>
							<td><Select name="group" form="addStudent">
									<option id="noneGroup" value="0">(none)</option>
									<c:forEach items="${listGroups}" var="group">
										<option id="${group.name}" value="${group.group_id}">${group.name}</option>
									</c:forEach>
							</Select></td>
						</tr>
						<tr>
							<td>Last Name:</td>
							<td><input type="text" form="addStudent" id="lastName" name="lastName"
								placeholder="Last Name"></td>
							<td></td>
							<td rowspan="5" colspan="2"><div id="addImage">
									<img form="addStudent" src="Resources/img.png" id="image">
								</div></td>
							<td></td>
						</tr>
						<tr>
							<td>Date of Birth:</td>
							<td><input type="text" form="addStudent" id="dob" name="dob"></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Gender:</td>
							<td><input type="radio" form="addStudent" name="gender" id="male" value="M">
								Male <input type="radio" form="addStudent"  id="female" name="gender" value="F"> Female
							</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Country:</td>
							<td><input type="text" form="addStudent" name="Country" id="country"
								placeholder="Country"></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>City</td>
							<td><input type="text" form="addStudent" name="City" id="city"
								placeholder="City"></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Adress Line:</td>
							<td><input type="text" form="addStudent" id="adressLine" name="adressLine"
								placeholder="Adress Line"></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Phone(s):</td>

							<td>
								<div id='TextBoxesGroup'>
									<div id="TextBoxDiv1">
										<select name="selectPhoneType1" id="selectPhoneType1" form="addStudent">
											<option id="nonephoneType1" value="0">PhoneType</option>
											
												<option id="Mobile1" value="1" selected="selected">Mobile</option>
											
												<option id="Stationery1" value="2">Stationery</option>
											
										</select>
										
										
										
										<input type="text" form="addStudent" id="addPhone1"
											onkeyup="mask('addPhone1', '(000)00-00-00', event);"
											name="addPhone1" placeholder="(123)45-67-89"><br>
									</div>
								</div>
							</td>
							<td><input type='button' form="addStudent" value='+' id='addInput'onClick="addPhoneDiv()"> <input
								type='button' value='- ' form="addStudent" id='removeInput'></td>
							<td></td>
							<td><input type="file" form="addStudent" name="picture" id="picture"
								accept="image/*"
								onchange="$('#image')[0].src=window.URL.createObjectURL(this.files[0])"
								width="200px" form="addStudent"></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td><input type="submit" form="addStudent" name="Add" value="Save"></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
