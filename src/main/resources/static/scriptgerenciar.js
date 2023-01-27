function carregar() {
    tabela = '';

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("GET", "http://localhost:8080/api/");

    xhr.send();

    xhr.onload = function () {
        obj = JSON.parse(this.responseText);
        c = 0;
        for (i = 0; i < obj.length; i++) {
            tabela += `<tr>`
            tabela += `<td style="cursor : pointer;" scope="row"><i onclick=apagar(${obj[i].id}) style="width: 15px;" class="fa fa-trash" aria-hidden="true"></i></td>`
            tabela += `<td>${obj[i].nome}</td>`
            tabela += `<td>${obj[i].valor}</td>`
            tabela += `<td><button onclick=editar(${obj[i].id},${obj[i].nome},${obj[i].descricao},${obj[i].valor}) type="button" class="btn btn-secondary">Editar</button></td>`
            tabela += `</tr>`
        }
        document.getElementById("produtos").innerHTML = tabela;
    }
}
carregar()
function apagar(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            const xhr = new XMLHttpRequest();
            xhr.withCredentials = true;

            xhr.open("DELETE", "http://localhost:8080/api/" + id);

            xhr.send();

            xhr.onload = function () {
                if (this.status == 200) {
                    carregar()
                    Swal.fire(
                        'Deleted!',
                        'Your file has been deleted.',
                        'success'
                    )
                }
            }
        }
    })
}
function adicionar(){
    $('#addmodal').modal('show')
}
function fecha(id){
    $('#'+id).modal('toggle')
}
$(function(){
    $('#precomask').maskMoney({
      prefix:'R$ ',
      allowNegative: true,
      thousands:'.', decimal:',',
      affixesStay: true});
})