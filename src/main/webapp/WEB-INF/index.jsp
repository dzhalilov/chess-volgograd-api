<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Рейтинг Волгоградских шахматистов</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/chess.png" sizes="32x32">
    <meta id="root" about="${pageContext.request.contextPath}">
    <link href="${pageContext.request.contextPath}/resources/bootstrap-4.3.1-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap-4.3.1-dist/js/jq.js" rel="stylesheet">
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap-4.3.1-dist/js/jq.js">
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/bootstrap-4.3.1-dist/js/bootstrap.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts.js">
    </script>

</head>
<body onload="loadContent('${pageContext.request.contextPath}','', 1);">
<div class="container">
    <form  id="myFilter" style="background-color: #E9ECEF; padding: 20px; border-radius: 10px; margin-top: 10px">
    <h4>Фильтры</h4>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputName">Имя спортсмена</label>
                <input type="text" class="form-control" id="inputName" placeholder="Имя">
            </div>
            <div class="form-group col-md-6">
                <label>Год рождения</label>
                <div class="form-row">
                    <div class="form-group col-md-3">
                        <input type="number" min="1950" max="2100" step="1" class="form-control" id="ageStart"
                               placeholder="от">
                    </div>
                    <div class="form-group col-md-3">
                        <input type="number" min="1950" max="2100" step="1" class="form-control" id="ageEnd"
                               placeholder="по">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-6">
                <label>Категория</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1"
                           value="null" checked="checked">
                    <label class="form-check-label" for="inlineRadio1">Любая</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2"
                           value="false">
                    <label class="form-check-label" for="inlineRadio2">Мальчики</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3"
                           value="true">
                    <label class="form-check-label" for="inlineRadio3">Девочки</label>
                </div>
            </div>
        </div>
        <button type="button" onclick="processSearch('${pageContext.request.contextPath}',1)" class="btn btn-primary">
            <i class='fas fa-chess-pawn'></i>
            Отфильтровать
        </button>
        <button type="button" onclick="processReset('${pageContext.request.contextPath}',1)" class="btn btn-danger">
            Сбросить фильтр
        </button>
        <h5 id="count" style="float: right; margin-right: 20px"></h5>
    </form>
    <h1 style="text-align: center; margin-top: 50px; margin-bottom: 30px">
        <i class='fas fa-chess-queen' style='color: red'></i> Шахматисты Волгоградской области
        <i class='fas fa-chess-queen' style='color: red'></i> </h1>

    <label style="float: left ; margin-right: 10px" for="order">Сортировка: </label>
    <select style="float: left" onchange="processSearch('${pageContext.request.contextPath}', 1)"
            style="margin-left: 5px" id="order"
            class="form-control-sm">
        <option>Id</option>
        <option selected>Classic</option>
        <option>Rapid</option>
        <option>Blitz</option>
    </select>
    <select style="float: right" onchange="processSearch('${pageContext.request.contextPath}', 1)"
            style="margin-left: 5px" id="limit"
            class="form-control-sm">
        <option>20</option>
        <option selected>50</option>
        <option>100</option>
    </select>
    <label style="float: right; margin-right: 10px" for="limit">Шахматистов на странице: </label>

    <br>

    <table style="margin-top: 10px" class="table">
        <thead class="table-bordered thead-dark">
        <tr>
            <th class="text-center" scope="col">№</th>
            <th class="text-center" scope="col">id РШФ</th>
            <th class="text-center" scope="col">Имя</th>
            <th class="text-center" scope="col">Год рождения</th>
            <th class="text-center" scope="col">Пол</th>
            <th class="text-center" scope="col">Рейтинг РШФ классика</th>
            <th class="text-center" scope="col">Рейтинг РШФ рапид</th>
            <th class="text-center" scope="col">Рейтинг РШФ блиц</th>
        </tr>
        </thead>
        <tbody id="mainTable">
        </tbody>
    </table>

    <div>
        <ul id="pagging-bar" class="pagination pagination-sm justify-content-center">

        </ul>
    </div>
</div>
</body>
</html>