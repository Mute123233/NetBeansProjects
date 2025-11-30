async function getData() {
    try {
        const request = await fetch("http://localhost:8080/atelie/allTkanNovinki");
        const data = await request.json();
        console.log(data);
        return data;
    } catch(error) {
        console.error(error);
        return [];
    }
}

document.addEventListener('DOMContentLoaded', getData());