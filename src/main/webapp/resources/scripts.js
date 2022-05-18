function loadContent(root, suffix, currentPage) {

    let objects = JSON.parse(Get(root + "/rest/players" + suffix).responseText);
    let playersCount = Get(root + "/rest/players/count" + suffix).responseText;
    document.getElementById("count").innerText = "Игроков найдено: " + playersCount;
    let table = document.getElementById("mainTable");
    table.innerHTML = "";
    let limit = document.getElementById("limit").value;
    createPaging(limit, playersCount, currentPage);

    for (let i = 0; i < objects.length; i++) {

        let tr = document.createElement("tr");
        let th = document.createElement("th");
        th.setAttribute("scope", "row");

        th.appendChild(document.createTextNode((parseInt(currentPage) - 1) * limit + i + 1));
        tr.appendChild(th);
        let td0 = document.createElement("td");
        td0.setAttribute("class", "text-center");

        td0.appendChild(document.createTextNode(objects[i].id));
        tr.appendChild(td0);
        let td1 = document.createElement("td");

        td1.appendChild(document.createTextNode(objects[i].name));
        tr.appendChild(td1);
        let td2 = document.createElement("td");
        td2.setAttribute("class", "text-center");

        td2.appendChild(document.createTextNode(objects[i].age));
        tr.appendChild(td2);
        let td3 = document.createElement("td");
        td3.setAttribute("class", "text-center");

        td3.appendChild(document.createTextNode(objects[i].sex));
        tr.appendChild(td3);
        let td4 = document.createElement("td");
        td4.setAttribute("class", "text-center");

        td4.appendChild(document.createTextNode(objects[i].classicRating));
        tr.appendChild(td4);
        let td5 = document.createElement("td");
        td5.appendChild(document.createTextNode(objects[i].rapidRating));
        td5.setAttribute("class", "text-center");

        tr.appendChild(td5);
        let td6 = document.createElement("td");
        td6.appendChild(document.createTextNode(objects[i].blitzRating));
        td6.setAttribute("class", "text-center");

        tr.appendChild(td6);
        table.appendChild(tr);
    }
    // window.scrollTo(500, 100);
}

function Get(requestUrl) {
    let Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("GET", requestUrl, false);
    Httpreq.send(null);
    if (Httpreq.status === 400) {
        $('#error-text').text("Bad request to GET " + requestUrl);
        $('#myModal').modal('show');
    }
    if (Httpreq.status === 404) {
        $('#error-text').text("Not found GET " + requestUrl);
        $('#myModal').modal('show');
    }
    return Httpreq;
}
function processReset(contextPath, number) {
    document.getElementById("myFilter").reset();
    processSearch(contextPath, number);
}

function processSearch(root, currentPage) {
    let name = document.getElementById("inputName").value;
    let ageStart = document.getElementById("ageStart").value;
    let ageEnd = document.getElementById("ageEnd").value;
    let order = document.getElementById("order").value;
    let sex = null;
    let limit = document.getElementById("limit").value;
    if (document.getElementById("inlineRadio2").checked) {
        sex = "m";
    } else if (document.getElementById("inlineRadio3").checked) {
        sex = "f";
    }
    let suffix = "?";
    if (name !== "") {
        suffix += "name=" + name;
    }
    if (sex !== null) {
        suffix += "&sex=" + sex;
    }
    if (ageStart !== "") {
        suffix += "&ageStart=" + ageStart;
    }
    if (ageEnd !== "") {
        suffix += "&ageEnd=" + ageEnd;
    }

    suffix += "&pageNumber=" + (+currentPage - 1);
    suffix += "&pageSize=" + +limit;

    console.log(limit);

    suffix += "&order=" + order.toUpperCase();
    loadContent(root, suffix, currentPage);
}

function createPaging(playersInPage, playersSummary, currentPage) {
    let paggingBar = document.getElementById("pagging-bar");
    paggingBar.innerHTML = "";
    let pagesCount = playersSummary / playersInPage;
    if (pagesCount > 1) {

        for (let i = 0; i < pagesCount; i++) {
            let li = document.createElement("li");
            if (i === currentPage - 1) {
                li.setAttribute("class", "page-item disabled");
            } else {
                li.setAttribute("class", "page-item");
            }
            let a = document.createElement("a");
            a.setAttribute("class", "page-link");
            a.setAttribute("href", "#");
            let root = document.getElementById("root").getAttribute("about");
            a.setAttribute("onclick", "processSearch('" + root + "', " + (i + 1) + ")");
            a.appendChild(document.createTextNode(i + 1));
            li.appendChild(a);
            paggingBar.appendChild(li);
        }
    }
}