let login = new XMLHttpRequest();
login.open('POST', 'SessionServlet');
login.onreadystatechange = function() {
	if (this.readyState === 4 && this.status === 200) {
		let response = login.responseText.split(",");
		let id = document.getElementById("currentId");
		if (id != null) {
			id.innerText = response[0];
		}
		let type = document.getElementById("currentType");
		if (type != null) {
			type.innerText = response[1];
		}
		let user = document.getElementById("currentUsername");
		if (user != null) {
			user.innerText = response[2];
		}
		let pass = document.getElementById("currentPassword");
		if (pass != null) {
			pass.innerText = response[3];
		}
	}
};
login.send();
