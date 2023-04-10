const { createApp } = Vue;
const url = 'http://localhost:8080/rest/clients';
const app = createApp({
    data() {
        return {
            data: [],
            firstName: "",
            lastName: "",
            eMail: "",
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data._embedded.clients
                console.log(this.data)
            })
            .catch(err => console.log(err))
    },
    methods: {
        // axios.post(url[, data[, config]])
        addClient() {
            axios({
                method: 'post',
                url: url,
                data: {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    eMail: this.eMail,
                }
            })
        }
    }
})
app.mount('#vueApp')
