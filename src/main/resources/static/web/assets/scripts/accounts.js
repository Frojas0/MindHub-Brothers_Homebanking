const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/1';
const app = createApp({
    data() {
        return {
            data: [],
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data
                console.log(this.data)
            })
            .catch(err => console.log(err))
    },
})
app.mount('#vueApp')