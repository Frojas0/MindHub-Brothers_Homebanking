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
                .catch(error => {
                    console.error(error);
                    this.error = '*ERROR: Wrong e-mail or password';
                    this.$forceUpdate();
                })
        }
    }
}).mount("#app")