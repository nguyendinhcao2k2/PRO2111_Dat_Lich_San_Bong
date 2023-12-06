let urlNgay = "http://localhost:8081/api/v1/admin/thong-ke/ngay";
let urlTuan = "http://localhost:8081/api/v1/admin/thong-ke/tuan";
let urlThang = "http://localhost:8081/api/v1/admin/thong-ke/thang";
let urlNam = "http://localhost:8081/api/v1/admin/thong-ke/nam";
$(document).ready(() => {
    callApiListThongKeNgay(urlNgay);
    callApiListThongKeTuan(urlTuan);
    callApiListThongKeThang(urlThang);
    callApiListThongKeNam(urlNam);
    $(".inputTKNam").val(new Date().getFullYear());
    ganValueInputMonth(".inputTKThang");
    ganValueInputDate(".inputTKNgay");
    var ngayHienTai = new Date();

    // Chuyển đổi ngày thành chuỗi 'YYYY-Www'
    var chuoiNgay = ngayHienTai.getFullYear() + '-W' + ('0' + ngayHienTai.getWeek()).slice(-2);

    // Gán giá trị cho trường input
    $(".inputTKTuan").val(chuoiNgay);

    findByNgay();
    findByTuan();
    findByMonth();
    findByYear();

});
// Thêm một phương thức mới để lấy số tuần trong năm
Date.prototype.getWeek = function () {
    var target = new Date(this.valueOf());
    var dayNr = (this.getDay() + 6) % 7;
    target.setDate(target.getDate() - dayNr + 3);
    var firstThursday = target.valueOf();
    target.setMonth(0, 1);
    if (target.getDay() !== 4) {
        target.setMonth(0, 1 + ((4 - target.getDay()) + 7) % 7);
    }
    return 1 + Math.ceil((firstThursday - target) / 604800000);
};

function callApiListThongKeNgay(url) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: url,
        success: function (response) {
            var thongKeNgay = response.content.thongKeNgay;
            for (var key in thongKeNgay) {
                listTK.thongKeNgay[key] = thongKeNgay[key];
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function callApiListThongKeTuan(url) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: url,
        success: function (response) {
            var thongKeTuan = response.content.thongKeTuan;
            for (var key in thongKeTuan) {
                listTK.thongKeTuan[key] = thongKeTuan[key];
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function callApiListThongKeThang(url) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: url,
        success: function (response) {
            var thongKeThang = response.content.thongKeThang;
            for (var key in thongKeThang) {
                listTK.thongKeThang[key] = thongKeThang[key];
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function callApiListThongKeNam(url) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: url,
        success: function (response) {
            var thongKeNam = response.content.thongKeNam;
            for (var key in thongKeNam) {
                listTK.thongKeNam[key] = thongKeNam[key];
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function filerByValue(className, parameters) {
    $(className).change((event) => {
        if (event.target.value == null || event.target.value == "") {
            callApiListThongKe(url);
            return;
        }
        url = "http://localhost:8081/api/v1/admin/thong-ke/list?" + parameters + "=" + event.target.value;
        callApiListThongKe(url);
    });
}

function findByNgay() {
    $(".inputTKNgay").change((event) => {
        var url = urlNgay + "?paramNgay=" + event.target.value;
        callApiListThongKeNgay(url);
    })
};


function findByTuan() {
    $(".inputTKTuan").change((event) => {
        let year = event.target.value.split("-W")[0];
        let week = event.target.value.split("-W")[1];
        let yearWeek = year + week;
        var url = urlTuan + "?paramTuan=" + yearWeek;
        callApiListThongKeTuan(url);
    })
}

function findByMonth() {
    $(".inputTKThang").change((event) => {
        console.log(event.target.value);
        var url = urlThang + "?paramMonth=" + event.target.value + "-01" + "&thangThanhToan=" + event.target.value.split("-")[1] + "&namThanhToan=" + event.target.value.split("-")[0];
        callApiListThongKeThang(url);
    })
}

function findByYear() {
    $(".inputTKNam").change((event) => {
        if (event.target.value == null || event.target.value == "") {
            callApiListThongKeNam(urlNam);
            return;
        }
        var url = urlNam + "?paramYear=" + event.target.value;
        callApiListThongKeNam(url);
    })
}

function taoObjectThongKe() {
    return {
        tongDoanhThu: 0,
        tongSanDatOnline: 0,
        tongSoHuyLich: 0,
        tongSoLuotChuyenLich: 0,
        tongSoLuotDa: 0,
        tongSoLuotSanDatNho: 0,
        tongSoTienMat: 0,
        tongTienPhatSinhKhiGiaoCa: 0
    }
};

function ganValueInputMonth(className) {
    const inputThang = document.querySelector(className);
    const currentDate = new Date();
    const thang = currentDate.getMonth() + 1;
    const nam = currentDate.getFullYear();
    const dateValue = `${nam}-${thang.toString().padStart(2, '0')}`;
    inputThang.value = dateValue;
}

function ganValueInputDate(className) {
    const inputNgay = document.querySelector(className);
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Tháng là số từ 1 đến 12
    const day = currentDate.getDate().toString().padStart(2, '0');
    inputNgay.value = `${year}-${month}-${day}`;
};

//vue js
var listTK = new Vue({
    el: "#thongKeList",
    data: {
        thongKeNgay: taoObjectThongKe(),
        thongKeTuan: taoObjectThongKe(),
        thongKeThang: taoObjectThongKe(),
        thongKeNam: taoObjectThongKe(),
    },
    methods: {
        currenNumberListTK(number) {
            return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
        }
    }
})