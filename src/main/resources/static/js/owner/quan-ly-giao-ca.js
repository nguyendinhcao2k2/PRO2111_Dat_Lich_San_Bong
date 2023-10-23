// $(function () {
//     $("#datepicker")
//         .datepicker({
//             autoclose: true,
//             todayHighlight: true,
//         })
//         .datepicker("update", new Date());
// });
// $(function () {
//     $("#datepicker2")
//         .datepicker({
//             autoclose: true,
//             todayHighlight: true,
//         })
//         .datepicker("update", new Date());
// });
$(document).ready(function () {
    getData();

    function getData() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/api/v1/staff/giao-ca/owners",
            success: function (data) {
                console.log(data.content.data)
                if (data.content.data.length == 0) {
                    $(".noContent").removeClass("hidden");
                } else {
                    $(".readTable").removeClass("hide");
                    readData(data);
                }
            },
            error: (error) => {
                console.log(error)
            }
        });
    };

    //check box giao ca co tien
    $(".checkBoxKiemTra").change((event) => {
        if (event.target.checked) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/staff/giao-ca/rut-tien",
                success: function (data) {
                    console.log(data.content.data)
                    if (data.content.data.length == 0) {
                        $(".noContent").removeClass("hidden");
                    } else {
                        $(".readTable").removeClass("hide");
                        readData(data);
                        app.lengthListSearch = 2;
                    }
                },
                error: (error) => {
                    console.log(error)
                }
            });
        } else {
            getData();
        }
    });

    //next
    $(".nextPage").click(() => {
        if (parseInt(app.displaycurrentPage) == parseInt(app.totalPages)) {
            return;
        } else {
            $(".noContent").hide();
            pagingTione((app.currentPage + 1));
        }
    });
    //previoust
    $(".previousPage").click(() => {
        if (parseInt(app.displaycurrentPage) == 1) {
            return;
        } else {
            pagingTione((app.currentPage - 1))
        }
    });

    function pagingTione(value) {
        var url = "http://localhost:8081/api/v1/staff/giao-ca/owners?page=" + value;
        if (app.lengthListSearch === 1) {
            url = "http://localhost:8081/api/v1/staff/giao-ca/search?page=" + value + "&name=" + nameSearch;
        } else if (app.lengthListSearch == 2) {
            url = "http://localhost:8081/api/v1/staff/giao-ca/rut-tien?page=" + value;
        } else if (app.lengthListSearch == 3) {
            url = "http://localhost:8081/api/v1/staff/giao-ca/by-time?page=" + value + "&time=" + timeSearch;
        }
        $.ajax({
            type: "GET",
            url: url,
            success: function (data) {
                readData(data);
            },
            error: (error) => {
                console.log(error)
            }
        });
    }

    //search by ngay nhan ca
    var timeSearch = null;
    $(".timeNC").change((event) => {
        timeSearch = event.target.value;
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/api/v1/staff/giao-ca/by-time?time=" + timeSearch,
            success: function (data) {
                if (data.content.data.length == 0) {
                    alert("Không có dữ liệu!")
                } else {
                    readData(data);
                    app.lengthListSearch = 3;
                }

            },
            error: (error) => {
                console.log(error)
            }
        });
    });
    //search by name
    var nameSearch = null;
    $(".searchIcon").click(() => {
        nameSearch = $("#search").val();
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/api/v1/staff/giao-ca/search?name=" + ($("#search").val()),
            success: function (data) {
                if (data.content.data.length == 0) {
                    alert("Không có dữ liệu!")
                } else {
                    readData(data);
                    app.lengthListSearch = 1;
                }

            },
            error: (error) => {
                console.log(error)
            }
        });
    });
});

// base read data
function readData(data) {
    app.giaoCaOwner = formatData(data.content.data);
    app.totalPages = data.content.totalPages;
    app.currentPage = parseInt(data.content.currentPage);
    app.pageSize = data.content.pageSize;
    app.displaycurrentPage = parseInt(data.content.currentPage) + 1;
}

//format data
function formatData(data) {
    return data.map((item) => {
        return {
            displayNameNhanVienTrongCa: item.displayNameNhanVienTrongCa,
            displayNameNhanVienCaTiepTheo: item.displayNameNhanVienCaTiepTheo,
            thoiGianNhanCa: formatDate(item.thoiGianNhanCa),
            thoiGianKetCa: formatDate(item.thoiGianKetCa),
            tienBanDau: curenlyNumber(item.tienBanDau),
            tongTienTrongCa: curenlyNumber(item.tongTienTrongCa),
            tienPhatSinh: curenlyNumber(item.tienPhatSinh),
            ghiChuPhatSinh: item.ghiChuPhatSinh,
            tongTienMatRut: curenlyNumber(item.tongTienMatRut),
            thoiGianReset: item.thoiGianReset == null ? "Null" : formatDate(item.thoiGianReset),
            tongTienMat: curenlyNumber(item.tongTienMat),
            tongTienKhac: curenlyNumber(item.tongTienKhac),
        }
    })
};

function formatDate(date) {
    return moment(date).format("HH:mm:ss DD-MM-YYYY");
};

function curenlyNumber(number) {
    return number.toLocaleString("vi-VN");
};

function formatDateTimeStamps(date) {
    return moment(date, "DD-MM-YYYY").format("YYYY-MM-DD");
};

var app = new Vue({
    el: "#app",
    data: {
        giaoCaOwner: null,
        totalPages: 0,
        currentPage: 0,
        pageSize: 0,
        displaycurrentPage: 0,
        isDisabled: true,
        lengthListSearch: 0,
    },
    methods: {},
    computed: {}
});
