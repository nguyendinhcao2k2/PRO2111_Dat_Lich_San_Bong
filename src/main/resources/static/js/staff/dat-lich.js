const apiUrl = "http://localhost:8081/api/v1/staff"

window.onload = function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/load-san-bong",
        success: function (responseData) {
            console.log(responseData)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}