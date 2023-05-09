const { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: ''
        }
    },
    methods: {
        signUp() {
            Swal.fire({
                title: 'ACEPTAS LOS TERMINOS Y CONDICIONES',
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Si',
                denyButtonText: `No`,
            }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                        .then(response => {
                            axios.post('/api/login', `email=${this.email}&password=${this.password}`)
                                .then(response => {
                                    window.location.replace('./accounts.html');
                                })
                        }).catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Cancelado', '', 'info')
                }
            });
        }
    }
}).mount("#app")