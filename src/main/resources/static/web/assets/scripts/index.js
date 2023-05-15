const { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            imgs: ["./assets/images/art1.jpg", "./assets/images/art2.jpg", "./assets/images/art3.jpg", "./assets/images/art4.jpg", "./assets/images/art5.jpg", "./assets/images/art6.jpg", "./assets/images/art7.jpg", "./assets/images/art8.jpg", "./assets/images/art9.jpg", "./assets/images/art10.jpg"],
            randomImages: ''
        }
    },
    methods: {
        login() {
            axios
                .post('/api/login', `email=${this.email}&password=${this.password}`, {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(response => {
                    if (this.email == "admin@admin.com") {
                        window.location.replace('/web/admin/manager.html');
                    } else {
                        window.location.replace('./accounts.html');
                    }
                })
                .catch(error => Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'invalid password or email',
                    timer: 3000,
                }))
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