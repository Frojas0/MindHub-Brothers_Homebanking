const { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            imgs: ["./assets/images/art1.jpg", "./assets/images/art2.jpg", "./assets/images/art3.jpg", "./assets/images/art4.jpg", "./assets/images/art5.jpg", "./assets/images/art6.jpg", "./assets/images/art7.jpg", "./assets/images/art8.jpg", "./assets/images/art9.jpg", "./assets/images/art10.jpg"],
            randomImages: ''
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
        },
        randomImg() {
            const randomNum = Math.floor(Math.random() * this.imgs.length);
            this.randomImages = this.imgs[randomNum];
            console.log(this.randomImages);
        }
    },
    mounted() {
        this.randomImg();
    }
}).mount("#app")