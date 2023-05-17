const { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            imgs: [
                { src: "./assets/images/art1.jpg", author: "BIRMINGHAM", name: "trust" },
                { src: "./assets/images/art2.jpg", author: "BIRMINGHAM", name: "museums" },
                { src: "./assets/images/art3.jpg", author: "FLORIN", name: "preda" },
                { src: "./assets/images/art4.jpg", author: "OLGA", name: "serjantu" },
                { src: "./assets/images/art5.jpg", author: "JENE", name: "stephaniuk" },
                { src: "./assets/images/art6.jpg", author: "JONAS", name: "allert" },
                { src: "./assets/images/art7.jpg", author: "ADRIANNA", name: "geo" },
                { src: "./assets/images/art8.jpg", author: "FRANCESSCO", name: "biaco" },
                { src: "./assets/images/art9.jpg", author: "TAMARA", name: "menzi" },
                { src: "./assets/images/art10.jpg", author: "EUROPEANA", name: "unsplash" }],
            randomImages: {}
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
            // console.log(this.randomImages);
        }
    },
    mounted() {
        this.randomImg();
    }
}).mount("#app")