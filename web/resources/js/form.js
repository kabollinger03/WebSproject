function swap() {
  var x = document.getElementById("login-form");
    x.style.display = "none";
  var y = document.getElementById("forgot-pw-form");
  	y.style.display = "block";
}

function swap_back(){
	var x = document.getElementById("login-form");
	  x.style.display = "block";
	var y = document.getElementById("forgot-pw-form");
		y.style.display = "none";
}
