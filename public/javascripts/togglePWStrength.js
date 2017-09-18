document.getElementById("password-strength-meter").style.display = 'none';

document.getElementById("password").onkeypress =
function() {toggleStrength()};

document.getElementById("password").onblur =
function() {toggleStrength()};

function toggleStrength() {
  var formElement = document.getElementById("password");
  var pwElement = document.getElementById("password-strength-meter");
  if (formElement.value == '')
  {
    pwElement.style.display = 'none';
  } else {
    pwElement.style.removeProperty('display');
  }
}

