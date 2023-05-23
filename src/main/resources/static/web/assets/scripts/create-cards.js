const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            type: '',
            color: ''
        }
    },
    methods: {
        logOut() {
            axios.post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
        },
        createCard() {
            Swal.fire({
                icon: 'warning',
                title: 'Shure?',
                text: "you are about to create a card: " + this.type + ", " + this.color,
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Create',
                denyButtonText: `Cancel`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/cards', `type=${this.type}&color=${this.color}`)
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Created',
                                timer: 3000,
                            })
                            window.location.replace('/web/cards.html')
                        })
                        .catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Creation cancelled', '', 'info')
                }
            })
        }
    }
})
app.mount('#vueApp')
// axios.post('/api/clients/current/cards', `type=${this.type}&color=${this.color}`)
//                 .then(response => {
//                     console.log('CREATED')
//                     window.location.replace('/web/cards.html')
//                 })