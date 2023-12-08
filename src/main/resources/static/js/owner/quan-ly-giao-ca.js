$(document).ready(function () {
    getData();
    sortByThoiGianNhanCa();
    ganValueInputDate(".timeNC");
    $(".exportExcelGC").click(()=>{
        window.location.href="http://localhost:8081/api/v1/admin/giao-ca/export";
    });
    function getData() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/api/v1/admin/giao-ca/owners",
            success: function (data) {
                if (data.content.data.length == 0) {
                    $(".noContent").prop("hidden", false);
                } else {
                    $(".readTable").prop("hidden", false);
                    readData(data);
                    app.lengthListSearch = 0;
                }
            },
            error: (error) => {
                console.log(error);
            },
        });
    }

    //check box giao ca co tien
    $(".checkBoxKiemTra").change((event) => {
        locByCheckBox(event, "Không có dữ liệu mong muốn!");
    });

    function locByCheckBox(event, message) {
        if (parseInt(event.target.value) === 1) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/giao-ca/rut-tien",
                success: function (data) {
                    console.log(data.content.data);
                    if (data.content.data.length == 0) {
                        alert(message);
                        createAndShowToast("bg-warning","Thông báo","Không có dữ liệu!")
                    } else {
                        readData(data);
                        if (parseInt(event.target.value) === 1) {
                            app.lengthListSearch = 2;
                        }
                    }
                },
                error: (error) => {
                    console.log(error);
                },
            });
        } else {
            getData();
        }
    }
    var sortName = null;
    function sortByThoiGianNhanCa() {
        $(".thoiGianNhanCaTangDan").click(() => {
            $(".thoiGianNhanCaGiamDan").prop("hidden", false);
            $(".thoiGianNhanCaTangDan").prop("hidden", true);
            orderBy("asc");
            sortName = "asc";
        });
        $(".thoiGianNhanCaGiamDan").click(() => {
            $(".thoiGianNhanCaGiamDan").prop("hidden", true);
            $(".thoiGianNhanCaTangDan").prop("hidden", false);
            //   getData();
            orderBy("desc");
            sortName = "desc";
        });
    }
    function orderBy(value) {
        var url = "http://localhost:8081/api/v1/admin/giao-ca/owners?sort=" + value;
        if (app.lengthListSearch === 0) {
            url;
        } else if (app.lengthListSearch === 1) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/search?sort=" +
                value +
                "&name=" +
                nameSearch;
        } else if (app.lengthListSearch == 2) {
            url = "http://localhost:8081/api/v1/admin/giao-ca/rut-tien?sort=" + value;
        } else if (app.lengthListSearch == 3) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/by-time?sort=" +
                value +
                "&time=" +
                timeSearch;
        }

        $.ajax({
            type: "GET",
            url: url,
            success: function (data) {
                readData(data);
                console.log(data);
            },
            error: (error) => {
                console.log(error);
            },
        });
    }
    //next
    $(".nextPage").click(() => {
        if (parseInt(app.displaycurrentPage) == parseInt(app.totalPages)) {
            return;
        } else {
            $(".noContent").hide();
            pagingTione(app.currentPage + 1);
        }
    });
    //previoust
    $(".previousPage").click(() => {
        if (parseInt(app.displaycurrentPage) == 1) {
            return;
        } else {
            pagingTione(app.currentPage - 1);
        }
    });

    function pagingTione(value) {
        var url =
            "http://localhost:8081/api/v1/admin/giao-ca/owners?page=" +
            value +
            "&sort=" +
            sortName;
        if (app.lengthListSearch === 0) {
            url;
        } else if (app.lengthListSearch === 1) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/search?page=" +
                value +
                "&name=" +
                nameSearch +
                "&sort=" +
                sortName;
        } else if (app.lengthListSearch == 2) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/rut-tien?page=" +
                value +
                "&sort=" +
                sortName;
        } else if (app.lengthListSearch == 3) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/by-time?page=" +
                value +
                "&time=" +
                timeSearch +
                "&sort=" +
                sortName;
        } else if (app.lengthListSearch == 4) {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/sort-by-nhan-ca-asc?page=" +
                value;
        } else {
            url =
                "http://localhost:8081/api/v1/admin/giao-ca/sort-by-nhan-ca-desc?page=" +
                value;
        }
        $.ajax({
            type: "GET",
            url: url,
            success: function (data) {
                readData(data);
            },
            error: (error) => {
                console.log(error);
            },
        });
    }

    //search by ngay nhan ca
    var timeSearch = null;
    $(".timeNC").change((event) => {
        timeSearch = event.target.value;
        $.ajax({
            type: "GET",
            url:
                "http://localhost:8081/api/v1/admin/giao-ca/by-time?time=" + timeSearch,
            success: function (data) {
                if (data.content.data.length == 0) {
                    createAndShowToast("bg-warning","Thông báo","Không có dữ liệu!")
                } else {
                    readData(data);
                    app.lengthListSearch = 3;
                }
            },
            error: (error) => {
                console.log(error);
            },
        });
    });

    //search by name
    $(".searchName").on("input", function () {
        timKiemByName($(this).val());
    });
    var nameSearch = null;

    function timKiemByName(data) {
        nameSearch = data;
        $.ajax({
            type: "GET",
            url:
                "http://localhost:8081/api/v1/admin/giao-ca/search?name=" + nameSearch,
            success: function (data) {
                if (data.content.data.length == 0) {
                    getData();
                } else {
                    readData(data);
                    app.lengthListSearch = 1;
                }
            },
            error: (error) => {
                console.log(error);
            },
        });
    }
});

function ganValueInputDate(className) {
    const inputNgay = document.querySelector(className);
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Tháng là số từ 1 đến 12
    const day = currentDate.getDate().toString().padStart(2, '0');
    inputNgay.value = `${year}-${month}-${day}`;
};

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
            id: item.id,
            displayNameNhanVienTrongCa: item.displayNameNhanVienTrongCa,
            displayNameNhanVienCaTiepTheo: item.displayNameNhanVienCaTiepTheo,
            thoiGianNhanCa: formatDate(item.thoiGianNhanCa),
            thoiGianKetCa: formatDate(item.thoiGianKetCa),
            tienBanDau: curenlyNumber(item.tienBanDau),
            tongTienTrongCa: curenlyNumber(item.tongTienTrongCa),
            tienPhatSinh: curenlyNumber(item.tienPhatSinh),
            ghiChuPhatSinh: item.ghiChuPhatSinh,
            tongTienMatRut: curenlyNumber(item.tongTienMatRut),
            thoiGianReset:
                item.thoiGianReset == null ? "Null" : formatDate(item.thoiGianReset),
            tongTienMat: curenlyNumber(item.tongTienMat),
            tongTienKhac: curenlyNumber(item.tongTienKhac),
        };
    });
}

function formatDate(date) {
    return moment(date).format("HH:mm:ss DD-MM-YYYY");
}

function curenlyNumber(number) {
    return number.toLocaleString("vi-VN");
}

function formatDateTimeStamps(date) {
    return moment(date, "DD-MM-YYYY").format("YYYY-MM-DD");
}

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
    methods: {
        findGiaoCaByid(items) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/giao-ca/by-id/" + items,
                success: function (data) {
                    $(".tenNVTC").val(data.content.displayNameNhanVienTrongCa);
                    $(".tenNVCTT").val(data.content.displayNameNhanVienCaTiepTheo);
                    $(".thoiGianNhanCaCT").val(formatDate(data.content.thoiGianNhanCa));
                    $(".thoiGianKetCaCT").val(formatDate(data.content.thoiGianKetCa));
                    $(".tienBanDauCT").val(curenlyNumber(data.content.tienBanDau));
                    $(".tienPhatSinhCT").val(curenlyNumber(data.content.tienPhatSinh));
                    $(".tongTienMatTrongCaCT").val(
                        curenlyNumber(data.content.tongTienMat)
                    );
                    $(".tongTienChuyenKhoanCT").val(
                        curenlyNumber(data.content.tongTienKhac)
                    );
                    $(".tongTienTrongCaCT").val(
                        curenlyNumber(data.content.tongTienTrongCa)
                    );
                    $(".tongTienMatRutCT").val(
                        curenlyNumber(data.content.tongTienMatRut)
                    );
                    $(".ghiChuPhatSinhCT").val(data.content.ghiChuPhatSinh);
                },
                error: (error) => {
                    console.log(error);
                },
            });
        },
    },
    computed: {},
});
