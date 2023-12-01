$(document).ready(function () {
    $("#email, #password").keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();


            iniciarSesion();
        }
    });

    $("#loginButton").click(function () {
        iniciarSesion();
    });

    // Función que maneja el inicio de sesión
    function iniciarSesion() {
        var formData = {
            email: $("#email").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            url: "/login",
            data: formData,
            success: function (response) {
                enviarFormulario();
                $("#loginModal").modal("hide");
                $("#containerMensaje").addClass("d-none");
            },
            error: function (error) {
                console.error(error.responseText);
                $("#mensajeError").text("Email o Contraseña inválidos!");
                $("#containerMensaje").removeClass("d-none");
            }
        });
    }
});

function enviarFormulario() {
    var formulario = document.getElementById("formLogin");
    formulario.submit();
}

function validarNombre() {
    var nombreInput = document.getElementById('nombreUsuario');
    var nombreValidationFeedback = document.getElementById('nombreValidationFeedback');
    var nombre = nombreInput.value;

    if (/[\d]/.test(nombre) || nombre.length < 3) {
        nombreInput.classList.add('is-invalid');
        nombreInput.classList.remove('is-valid');
        nombreValidationFeedback.style.display = 'block';
        return false;
    }

    nombreInput.classList.remove('is-invalid');
    nombreInput.classList.add('is-valid');
    nombreValidationFeedback.style.display = 'none';
    return true;
}

function validarEmail() {
    var emailInputs = document.getElementsByClassName('email');
    var emailValidationFeedback = document.getElementById('emailValidationFeedback');

    for (var i = 0; i < emailInputs.length; i++) {
        var emailInput = emailInputs[i];
        var email = emailInput.value;

        if (!/\S+@\S+\.\S+/.test(email)) {
            emailInput.classList.add('is-invalid');
            emailInput.classList.remove('is-valid');
            emailValidationFeedback.style.display = 'block';
            return false;
        }

        emailInput.classList.remove('is-invalid');
        emailInput.classList.add('is-valid');
        emailValidationFeedback.style.display = 'none';
    }

    return true;
}

function validarContrasena() {
    var contrasenaInputs = document.getElementsByClassName('password');
    var contrasenaValidationFeedback = document.getElementById('passwordValidationFeedback');

    for (var i = 0; i < contrasenaInputs.length; i++) {
        var contrasenaInput = contrasenaInputs[i];
        var contrasena = contrasenaInput.value;

        if (contrasena.length < 6) {
            contrasenaInput.classList.add('is-invalid');
            contrasenaInput.classList.remove('is-valid');
            contrasenaValidationFeedback.innerHTML = 'La contraseña debe tener al menos 6 caracteres.';
            contrasenaValidationFeedback.style.display = 'block';
            return false;
        }

        contrasenaInput.classList.remove('is-invalid');
        contrasenaInput.classList.add('is-valid');
        contrasenaValidationFeedback.style.display = 'none';
    }

    return true;
}

function validarConfirmacionContrasena() {
    var contrasenaInputs = document.getElementsByClassName('password');
    var confirmacionContrasenaInputs = document.getElementsByClassName('password2');
    var confirmacionContrasenaValidationFeedback = document.getElementById('confirmPasswordValidationFeedback');

    for (var i = 0; i < contrasenaInputs.length; i++) {
        var contrasenaInput = contrasenaInputs[i];
        var confirmacionContrasenaInput = confirmacionContrasenaInputs[i];
        var contrasena = contrasenaInput.value;
        var confirmacionContrasena = confirmacionContrasenaInput.value;

        if (contrasena !== confirmacionContrasena) {
            confirmacionContrasenaInput.classList.add('is-invalid');
            confirmacionContrasenaInput.classList.remove('is-valid');
            confirmacionContrasenaValidationFeedback.innerHTML = 'Las contraseñas no coinciden.';
            confirmacionContrasenaValidationFeedback.style.display = 'block';
            return false;
        }

        confirmacionContrasenaInput.classList.remove('is-invalid');
        confirmacionContrasenaInput.classList.add('is-valid');
        confirmacionContrasenaValidationFeedback.style.display = 'none';
    }

    return true;
}

/*function validarTelefono() {
 var telefonoInput = document.getElementById('telefono');
 var telefonoHelp = document.getElementById('telefonoHelp');
 var telefono = telefonoInput.value;
 var telefonoRegex = /^\d{5}$/;
 
 if (!telefonoRegex.test(telefono)) {
 telefonoInput.classList.add('is-invalid');
 telefonoInput.classList.remove('is-valid');
 telefonoHelp.innerHTML = 'Ingresa un número de teléfono válido.';
 telefonoHelp.style.display = 'block';
 return false;
 }
 
 telefonoInput.classList.remove('is-invalid');
 telefonoInput.classList.add('is-valid');
 telefonoHelp.style.display = 'none';
 return true;
 }*/

document.getElementById('nombreUsuario').addEventListener('input', validarNombre);
document.getElementsByClassName('email')[0].addEventListener('input', validarEmail);
document.getElementsByClassName('password')[0].addEventListener('input', validarContrasena);
document.getElementsByClassName('password2')[0].addEventListener('input', validarConfirmacionContrasena);
/*document.getElementById('telefono').addEventListener('input', validarTelefono)*/

function validarFormulario(event) {
    var nombreValido = validarNombre();
    var emailValido = validarEmail();
    var contrasenaValida = validarContrasena();
    var confirmacionContrasenaValida = validarConfirmacionContrasena();
    /*var telefonoValido = validarTelefono();*/

    if (!(nombreValido && emailValido && contrasenaValida && confirmacionContrasenaValida/* && telefonoValido*/)) {
        event.preventDefault();
    }
}
document.getElementById('formularioRegistro').addEventListener('submit', validarFormulario);

