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
                title: 'QUERI REGISTRARTE?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'SI PO',
                denyButtonText: `NO PO`,
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
                    // Swal.fire('GUARDADO!', '', 'success')
                } else if (result.isDenied) {
                    Swal.fire('TE ARREPENTISTI', '', 'info')
                }
            });
        }
    }
}).mount("#app")

// newAccount() {
//     Swal.fire({
//         icon: 'warning',
//         title: 'You are creating a new Account..¿Are you sure?',
//         showCancelButton: true,
//         confirmButtonText: 'Yes, create new Account',
//         cancelButtonText: 'Cancel',
//         timer: 6000,
//     }).then((result) => {
//         if (result.isConfirmed) {
//             axios.post('/api/clients/current/accounts')
//                 .then(response => {
//                     if (response.status == "201") {
//                         this.createdAccount = true,
//                             this.loadData()
//                         Swal.fire({
//                             icon: 'success',
//                             title: 'You have a new Account!',
//                             showCancelButton: true,
//                             confirmButtonText: 'Accepted',
//                             cancelButtonText: 'Cancel',
//                             timer: 6000,
//                         })
//                     }
//                 })
//                 .catch(error => Swal.fire({
//                     icon: 'error',
//                     title: 'Error',
//                     text: error.response.data,
//                     timer: 6000,
//                 }))
//         }
//     })
// },