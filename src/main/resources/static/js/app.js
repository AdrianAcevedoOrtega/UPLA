// Configuración de la API
const API_BASE_URL = window.location.origin;

// Elementos del DOM
const reservaForm = document.getElementById('reservaForm');
const reservasList = document.getElementById('reservasList');
const messageDiv = document.getElementById('message');
const pageInput = document.getElementById('page');
const sizeInput = document.getElementById('size');

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    loadReservas();
    setupForm();
});

// Configurar el formulario
function setupForm() {
    reservaForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        await crearReserva();
    });
}

// Mostrar mensajes al usuario
function showMessage(message, type = 'success') {
    messageDiv.textContent = message;
    messageDiv.className = `message ${type}`;
    messageDiv.style.display = 'block';

    // Ocultar el mensaje después de 5 segundos
    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 5000);
}

// Crear una nueva reserva
async function crearReserva() {
    const formData = new FormData(reservaForm);
    const reservaData = {
        id_cliente: parseInt(formData.get('id_cliente')),
        id_apartamento: parseInt(formData.get('id_apartamento')),
        f_entrada: new Date(formData.get('f_entrada')).toISOString(),
        f_salida: new Date(formData.get('f_salida')).toISOString()
    };

    try {
        const response = await fetch(`${API_BASE_URL}/api/reservas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reservaData)
        });

        if (response.ok) {
            const nuevaReserva = await response.json();
            showMessage(`✅ Reserva creada exitosamente! ID: ${nuevaReserva.id_reserva}`, 'success');
            reservaForm.reset();
            loadReservas(); // Recargar la lista
        } else {
            const error = await response.json();
            showMessage(`❌ Error: ${error.message || 'Error al crear la reserva'}`, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showMessage('❌ Error de conexión con el servidor', 'error');
    }
}

// Cargar reservas desde la API
async function loadReservas() {
    const page = pageInput.value;
    const size = sizeInput.value;

    // Mostrar loading
    reservasList.innerHTML = '<div class="loading">Cargando reservas...</div>';

    try {
        const response = await fetch(`${API_BASE_URL}/api/reservas?page=${page}&size=${size}`);

        if (response.ok) {
            const pageData = await response.json();
            displayReservas(pageData.content);
        } else {
            reservasList.innerHTML = '<p>Error al cargar las reservas</p>';
        }
    } catch (error) {
        console.error('Error:', error);
        reservasList.innerHTML = '<p>Error de conexión con el servidor</p>';
    }
}

// Mostrar las reservas en el DOM
function displayReservas(reservas) {
    if (reservas.length === 0) {
        reservasList.innerHTML = '<p>No hay reservas para mostrar</p>';
        return;
    }

    reservasList.innerHTML = '';

    reservas.forEach(reserva => {
        const reservaCard = document.createElement('div');
        reservaCard.className = 'reserva-card';

        const fechaEntrada = new Date(reserva.f_entrada).toLocaleDateString('es-ES');
        const fechaSalida = new Date(reserva.f_salida).toLocaleDateString('es-ES');

        reservaCard.innerHTML = `
            <h3>Reserva #${reserva.id_reserva}</h3>
            <p><strong>Cliente:</strong> ${reserva.nombreCliente}</p>
            <p><strong>Apartamento:</strong> ${reserva.direccionApartamento}</p>
            <p><strong>Entrada:</strong> ${fechaEntrada}</p>
            <p><strong>Salida:</strong> ${fechaSalida}</p>
            <p class="precio"><strong>Precio Total:</strong> ${reserva.precio ? reserva.precio.toFixed(2) + '€' : 'N/A'}</p>
            <div class="acciones">
                <button class="btn btn-danger" onclick="eliminarReserva('${reserva.id_reserva}')">
                    🗑️ Eliminar
                </button>
            </div>
        `;

        reservasList.appendChild(reservaCard);
    });
}

// Eliminar una reserva
async function eliminarReserva(id) {
    if (!confirm('¿Estás seguro de que quieres eliminar esta reserva?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/reservas/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('✅ Reserva eliminada exitosamente', 'success');
            loadReservas(); // Recargar la lista
        } else {
            showMessage('❌ Error al eliminar la reserva', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showMessage('❌ Error de conexión con el servidor', 'error');
    }
}

// Función de utilidad para formatear fechas
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

// Función de utilidad para formatear precios
function formatPrice(price) {
    return new Intl.NumberFormat('es-ES', {
        style: 'currency',
        currency: 'EUR'
    }).format(price);
}
