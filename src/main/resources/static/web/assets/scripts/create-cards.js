const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/current';
const app = createApp({
    data() {
        return {
            type: '',
            color: ''
        }
    },
    methods: {
        logOut() {
            axios
                .post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
        },
        createCard() {
            axios
                .post('/api/clients/current/cards', `type=${this.type}&color=${this.color}`)
                .then(response => {
                    console.log('CREATED')
                    window.location.replace('/web/cards.html')
                })
        }
    }
})
app.mount('#vueApp')