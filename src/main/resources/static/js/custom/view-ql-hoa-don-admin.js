var table = document.getElementById("tableId");

for (var i = 1; i < table.rows.length; i++) {
    var row = table.rows[i];
    row.addEventListener("click", function() {
        var id = this.querySelector(".hidden").textContent;

        if (id) {
            var currentURL = 'http://localhost:8081/api/v1/admin/thanh-toan-hoa-don';
            var newURL = currentURL + '/' + id;
            window.location.href = newURL;
        }
    });
}