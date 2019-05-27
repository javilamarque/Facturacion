let xhr = new XMLHttpRequest ();
let url = 'api/product';

xhr.open("GET", url, true);

xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
        let response = JSON.parse(xhr.responseText);
        let products = response.data;

        //cabecera de la tabla
        let cabeceras = ["ID", "Nombre", "Descripcion", "Precio", "Stock"];
        let nombresCampos = ["id", "name", "descripction", "price", "stock"];

        let table = document.createElement("table");

        let tr = table.insertRow(-1);

    for (let i = 0; i <cabeceras.length; i++) {
        let th = document.createElement("th")
        th.innerHTML = cabeceras[i];
        tr.appendChild(th);
    }
    for (let i = 0; i < products.length; i++) {
        tr = table.insertRow(-1);

            for ( let j = 0; j < nombresCampos.length; j++) {
                let tabCell = tr .insertCell(-1);
                tabCell.innerHTML = products[i][nombresCampos[j]];
            }
    }
        var divContainer = document.getElementById("showData");
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
    }
};
xhr.send();