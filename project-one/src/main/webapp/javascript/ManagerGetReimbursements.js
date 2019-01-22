let m = new XMLHttpRequest();
m.open('POST', 'ManagerReimbursementsServlet');
m.onreadystatechange = function() {
	if (this.readyState === 4 && this.status === 200) {
		let reimbursements = m.responseText.split("\n");
		for (let i = 0; i < reimbursements.length - 1; i++) {
			let row = document.createElement("tr");
			let r = reimbursements[i].split(",");

			let id = document.createElement("td");
			let idText = document.createTextNode(r[0]);
			id.appendChild(idText);
			row.appendChild(id);

			let employee = document.createElement("td");
			let employeeText = document.createTextNode(r[1]);
			employee.appendChild(employeeText);
			row.appendChild(employee);

			let description = document.createElement("td");
			let descriptionText = document.createTextNode(r[2]);
			description.appendChild(descriptionText);
			row.appendChild(description);

			let img = document.createElement("td");
			let imgReal = document.createElement("img");
			imgReal.alt = "Receipt"
			imgReal.src = r[3];
			imgReal.width = 150;
			imgReal.height = 75;
			img.appendChild(imgReal);
			row.appendChild(img);

			let status = document.createElement("td");
			let statusText = document.createTextNode(r[4]);
			status.appendChild(statusText);
			row.appendChild(status);

			if (r[4] == "Pending") {
				let managerOption = document.createElement("td");
				let approve = document.createElement("button");
				approve.innerText = "Approve";
				approve.onclick = function() {updateReimbursement(this)};
				let deny = document.createElement("button");
				deny.innerText = "Deny";
				deny.onclick = function() {updateReimbursement(this)};
				managerOption.appendChild(approve);
				managerOption.appendChild(deny);
				row.appendChild(managerOption);
			} else {
				let manager = document.createElement("td");
				let managerText = document.createTextNode(r[5]);
				manager.appendChild(managerText);
				row.appendChild(manager);
			}

			document.getElementById("reimbursements").appendChild(row);
		}
	}
};
m.send();

function updateReimbursement(button) {
	let a = new XMLHttpRequest();
	var row = button.parentNode.parentNode;
	let id = row.cells[0].innerText;
	var status = button.innerText;
	if (status === "Approve")
    	status = "Approved";
    else if (status === "Deny")
    	status = "Denied";
	a.open('POST', 'UpdateReimbursementServlet', true);
	a.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {			  
			row.cells[4].innerText = status;
			row.deleteCell(5);
		    var newCell = row.insertCell(5);
		    newCell.appendChild(document.createTextNode(document.getElementById("currentUsername").innerText));
		}
	};
	a.send(id + "," + status);
}
