const form = document.getElementById('register-form');
    const nombre = document.getElementById('nombre');
    const apellido = document.getElementById('apellido');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const confirmarPassword = document.getElementById('confirmPassword');

    const nombreErr = document.getElementById('nombre-error');
    const apellidoErr = document.getElementById('apellido-error');
    const emailErr = document.getElementById('email-error');
    const passErr = document.getElementById('password-error');
    const confirmErr = document.getElementById('confirm-error');

    form.addEventListener('submit', async (e) => {
      e.preventDefault();

      // limpiar errores
      [nombreErr, apellidoErr, emailErr, passErr, confirmErr].forEach(err => err.textContent = '');

      let valid = true;

      if (!nombre.value.trim()) {
        nombreErr.textContent = 'El nombre es obligatorio.';
        valid = false;
      }

      if (!apellido.value.trim()) {
        apellidoErr.textContent = 'El apellido es obligatorio.';
        valid = false;
      }

      if (!email.value.trim()) {
        emailErr.textContent = 'El correo es obligatorio.';
        valid = false;
      }else if (!/^\S+@\S+\.\S+$/.test(email.value)) {
        emailErr.textContent = 'Introduce un correo válido.';
        valid = false;
    }

      if (!password.value.trim()) {
        passErr.textContent = 'La contraseña es obligatoria.';
        valid = false;
      } else if (password.value.length < 6) {
        passErr.textContent = 'Debe tener al menos 6 caracteres.';
        valid = false;
      }

      if (confirmarPassword.value !== password.value) {
        confirmErr.textContent = 'Las contraseñas no coinciden.';
        valid = false;
      }

      if (!valid) return;

      const data = {
        nombre: nombre.value.trim(),
        apellido: apellido.value.trim(),
        email: email.value.trim(),
        password: password.value,
        confirmarPassword: confirmarPassword.value
      };

      try {
        const response = await fetch('http://localhost:8080/api/usuarios/registro', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        });

        const result = await response.json();
        alert(result.mensaje);
        if (response.ok) {
          window.location.href = "http://127.0.0.1:5500/Frontend/Login.html";
        }
      } catch (error) {
        console.error('Error al registrar:', error);
      }
    });