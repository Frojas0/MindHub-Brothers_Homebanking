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
            axios
                .post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`, {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(
                    response => {
                        axios.post('/api/login', `email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                            .then(response => {
                                window.location.replace('./accounts.html');
                            })
                    })
                .catch(error => {
                    console.error(error);
                    this.error = 'Failed to register. Please try again.';
                });
        }
    }
}).mount("#app")