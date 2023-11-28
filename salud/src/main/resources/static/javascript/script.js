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