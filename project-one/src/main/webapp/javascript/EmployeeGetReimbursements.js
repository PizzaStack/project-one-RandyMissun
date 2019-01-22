function reimbursements() {
	let e = new XMLHttpRequest();
	e.open('POST', 'EmployeeReimbursementsServlet', true);
	e.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			let table = document.getElementById("reimbursements");
			let reimbursements = e.responseText.split("\n");
			for (let i = 0; i < reimbursements.length - 1; i++) {
				let row = document.createElement("tr");
				let r = reimbursements[i].split(",");

				let id = document.createElement("td");
				let idText = document.createTextNode(r[0]);
				id.appendChild(idText);
				row.appendChild(id);
				
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
				
				document.getElementById("reimbursements").appendChild(row);
			}
		}
	};
	e.send();
}