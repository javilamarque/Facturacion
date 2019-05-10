function createProduct() {
    let product = {
        name : document.getElementById("name").Value,
        description : document.getElementById("description").Value,
        price : parseFloat(document.getElementById("price")).value     
    };

    let xhr = new XMLHttpRequest();
    let url = "api/product";
    xhr,open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "aplication/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200)  {//el status 200 significa que esta todo OK, como en postman
            var response = JSON.parse(xhr.responseText);
            console.log(response);
        }
    };

    alert(JSON.stringify(product));
}

function goHome() {
    window.location.replace("index.html");
}