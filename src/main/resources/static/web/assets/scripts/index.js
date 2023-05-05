const { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: ''
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
        }
    }
}).mount("#app")