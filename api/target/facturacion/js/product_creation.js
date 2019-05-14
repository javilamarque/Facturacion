function createProduct() {
    let product = {
        name : document.getElementById("name").value,
        description : document.getElementById("description").value,
        price : parseFloat(document.getElementById("price").value)     
    };

    let xhr = new XMLHttpRequest();
    let url = "api/product";
    
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200)  { //el status 200 significa que esta todo OK, como en postman
            var response = JSON.parse(xhr.responseText);
            console.log(response);
        }
    };

    xhr.send(JSON.stringify(product));
    alert('Producto Creado!!');
    document.getElementById("name").value = '';
    document.getElementById("description").value = '';
    document.getElementById("price").value
}

function goHome() {
    window.location.replace("index.html");
}