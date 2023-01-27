cards = '<div class="card-group">';

const xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.open("GET", "http://localhost:8080/api/");

xhr.send();

xhr.onload = function () {
    obj = JSON.parse(this.responseText);
    c = 0;
    for (i = 0; i < obj.length; i++) {
        c++
        cards += '<div class="card">'
        cards += `<img class="card-img-top" src="http://localhost:8080/api/${obj[i].id}" alt="Card image cap">`
        cards += '<div class="card-body">'
        cards += `<h5 class="card-title">${obj[i].nome}</h5>`
        cards += `<p class="card-text">${obj[i].descricao}</p>`
        cards += `<p class="card-text"><small class="text-muted">${obj[i].valor}</small></p>`
        cards += `</div>`
        cards += "</div>"
        if (c == 3) {
            c = 0
            cards += `</div>`
            cards += '<div class="card-group">'
        }
    }
    cards += "</div>"
    document.getElementById("produtos").innerHTML = cards;
}

