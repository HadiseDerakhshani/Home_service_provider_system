
var email = document.forms['form']['email'];
var password = document.forms['form']['password'];

var email_error = document.getElementById('email_error');
var pass_error = document.getElementById('pass_error');


function validated() {
    if (email.value.length <6) {
        email.style.border = "1px solid red";
        email.style.boxShadow = "0 0 0.5em red";

        email_error.style.display = "block";
        email.focus();
        return false;
    }
    if (password.value.length <3) {
        password.style.border = "1px solid red";
        password.style.boxShadow = "0 0 0.5em red";

        pass_error.style.display = "block";
        password.focus();
        return false;

    }
    if (email.value ==="com.gmail@test") {
        email_error.style.display = "none";
        return true;
    }
    if (password.value === "test123") {
        pass_error.style.display = "none";
        return true;
    }
    return false;
}