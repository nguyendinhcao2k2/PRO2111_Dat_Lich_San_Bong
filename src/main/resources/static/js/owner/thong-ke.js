var array = [];
var arrayCaInDay = [0, 0, 0, 0];
var thangInNam = [];
var caInDay = ["00:01:00 - 06:00:00", "06:01:00 - 12:00:00", "12:01:00 - 18:00:00", "18:01:00 - 00:00:00"];
var arrayDoThue = [];
var arraySoLuongDoThue = [];
var arrayNuocUong = [];
var arraySoLuongNuocUong = [];
thangInNams();
$(document).ready(() => {
    yearpick();
    $(".dateNam").val(new Date().getFullYear());
    var url = "http://localhost:8081/api/v1/admin/thong-ke";
    $(".dateNam").change((event) => {
        url = "http://localhost:8081/api/v1/admin/thong-ke?year=" + parseInt(event.target.value);
        array = [];
        arrayMonth();
        callAPIThongKeNam(url);
    })
    $(".dateNgay").change((event) => {
        url = "http://localhost:8081/api/v1/admin/thong-ke?date=" + (event.target.value);
        arrayCaInDay = [0, 0, 0, 0];
        callAPIThongKeNam(url);
    });

    var thongTin = 'Doanh thu trong';
    $(".nameTK").text(thongTin + ' năm');
    callAPIThongKeNam(url);
    $(".optionChonTK").change((event) => {
        if (parseInt(event.target.value) == 1) {
            $(".thongKeTheoNam").prop("hidden", false);
            $(".thongKeTheoNgay").prop("hidden", true);
            $(".dateNam").prop("hidden", false);
            $(".dateNgay").prop("hidden", true);
            thongKePriceFollowYear(array);
            $(".yearpicker").val(new Date().getFullYear());
            $(".nameTK").text(thongTin + ' năm');
        } else {
            $(".thongKeTheoNam").prop("hidden", true);
            $(".thongKeTheoNgay").prop("hidden", false);
            $(".dateNam").prop("hidden", true);
            $(".dateNgay").prop("hidden", false);
            thongKePriceFollowDay(arrayCaInDay);
            const input = document.querySelector(".dateNgay");
            input.valueAsDate = new Date();
            $(".nameTK").text(thongTin + ' ngày');
        }
    });


    //do thue start
    clickThongKe();
    var url = "http://localhost:8081/api/v1/admin/thong-ke/do-thue";
    callApiDoThue(url);
    $(".doThueNam").val(new Date().getFullYear());
    $(".doThueNam").change((event) => {
        url = "http://localhost:8081/api/v1/admin/thong-ke/do-thue?year=" + (event.target.value);
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue(url);
    });

    // thang
    $(".doThueThang").change((event) => {
        var year = parseInt(event.target.value.split("-")[0]);
        var month = parseInt(event.target.value.split("-")[1]);
        url = "http://localhost:8081/api/v1/admin/thong-ke/do-thue/thang?year=" + year + "&month=" + month;
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue(url);
    });
    //ngay
    $(".doThueNgay").change((event) => {
        var year = parseInt(event.target.value.split("-")[0]);
        var month = parseInt(event.target.value.split("-")[1]);
        var day = parseInt(event.target.value.split("-")[2]);
        url = "http://localhost:8081/api/v1/admin/thong-ke/do-thue/ngay?year=" + year + "&month=" + month + "&day=" + day;
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue(url);
    });
    //do thue end

    //nuoc uong start
    checkHiddenDoThu();
    var urlNU = "http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong";
    callApiNuocUong(urlNU);
    $(".nuocUongNam").val(new Date().getFullYear());
    $(".nuocUongNam").change((event) => {
        urlNU = "http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong?year=" + (event.target.value);
        arrayNuocUong = [];
        arraySoLuongNuocUong = [];
        callApiNuocUong(urlNU);
    });
    // thang
    $(".nuocUongThang").change((event) => {
        var year = parseInt(event.target.value.split("-")[0]);
        var month = parseInt(event.target.value.split("-")[1]);
        urlNU = "http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong/thang?year=" + year + "&month=" + month;
        arrayNuocUong = [];
        arraySoLuongNuocUong = [];
        callApiNuocUong(urlNU);
    });
    //ngay
    $(".nuocUongNgay").change((event) => {
        var year = parseInt(event.target.value.split("-")[0]);
        var month = parseInt(event.target.value.split("-")[1]);
        var day = parseInt(event.target.value.split("-")[2]);
        urlNU = "http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong/ngay?year=" + year + "&month=" + month + "&day=" + day;
        arrayNuocUong = [];
        arraySoLuongNuocUong = [];
        callApiNuocUong(urlNU);
    });
    //nuoc uong end
});


function yearpick() {
    $('.yearpicker').yearpicker({

        // Initial Year
        year: null,

        // Start Year
        startYear: null,

        // End Year
        endYear: null,

        // Element tag
        itemTag: 'li',

        // Default CSS classes
        selectedClass: 'selected',
        disabledClass: 'disabled',
        hideClass: 'hide',

        // Custom template
        template: `<div class="yearpicker-container">
              <div class="yearpicker-header">
                  <div class="yearpicker-prev" data-view="yearpicker-prev">&lsaquo;</div>
                  <div class="yearpicker-current" data-view="yearpicker-current">SelectedYear</div>
                  <div class="yearpicker-next" data-view="yearpicker-next">&rsaquo;</div>
              </div>
              <div class="yearpicker-body">
                  <ul class="yearpicker-year" data-view="years">
                  </ul>
              </div>
          </div>
  `,

    });
};

function arrayMonth() {
    for (let i = 1; i <= 12; i++) {
        array.push(0);
    }
};

function callAPIThongKeNam(url) {
    var sumYear = 0;
    var sumDay = 0;
    var totalPriceNow = 0;
    $.ajax({
        type: "GET",
        url: url,
        success: (response) => {
            console.log(response)
            response.content.thongKeTheoNamAdminResponses.forEach((elem) => {
                checkThang(elem.monthName, elem.totalPrice === null ? 0 : elem.totalPrice);
                sumYear += elem.totalPrice === null ? 0 : elem.totalPrice;
            });
            tongDoanhThuCanam(".sumYear", sumYear);
            response.content.thongKeTheoCaAdminResponses.forEach((items) => {
                checkCaInDay(items.caName, items.totalPrice === null ? 0 : items.totalPrice);
                sumDay += items.totalPrice === null ? 0 : items.totalPrice;
            });
            response.content.thongKeNgayHomNay.forEach((items) => {
                totalPriceNow += items.totalPrice === null ? 0 : items.totalPrice;
            });
            $(".doanhThuNgayHomNay").text(curenlyNumber(totalPriceNow));

            thongKePriceFollowYear(array);
            thongKePriceFollowDay(arrayCaInDay);
            tongDoanhThuCanam(".sumDay", sumDay);
        },
        error: (error) => {
            console.log(error);
        }
    });
};


function curenlyNumber(number) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
};

function tongDoanhThuCanam(valueClass, price) {
    $(valueClass).text("Tổng doanh thu: " + curenlyNumber(price))
    return;
};


function checkThang(valueThang, valuePrice) {
    arrayMonth();
    if (valueThang === 1) {
        array[0] = valuePrice;
    } else if (valueThang === 2) {
        array[1] = valuePrice;
    } else if (valueThang === 3) {
        array[2] = valuePrice;
    } else if (valueThang === 4) {
        array[3] = valuePrice;
    } else if (valueThang === 5) {
        array[4] = valuePrice;
    } else if (valueThang === 6) {
        array[5] = valuePrice;
    } else if (valueThang === 7) {
        array[6] = valuePrice;
    } else if (valueThang === 8) {
        array[7] = valuePrice;
    } else if (valueThang === 9) {
        array[8] = valuePrice;
    } else if (valueThang === 10) {
        array[9] = valuePrice;
    } else if (valueThang === 11) {
        array[10] = valuePrice;
    } else {
        array[11] = valuePrice;
    }
};

function thangInNams() {
    for (let i = 1; i <= 12; i++) {
        thangInNam.push("Tháng " + i);
    }
};

function checkCaInDay(valueCa, valuePrice) {
    if (valueCa === 'ca1') {
        arrayCaInDay[0] = valuePrice;
    } else if (valueCa === 'ca2') {
        arrayCaInDay[1] = valuePrice;
    } else if (valueCa === 'ca3') {
        arrayCaInDay[2] = valuePrice;
    } else {
        arrayCaInDay[3] = valuePrice;
    }
};

var app = new Vue({
    el: "#app",
    data: {
        totalPriceList: []
    }
});


// thống kê theo nam
function thongKePriceFollowYear(data) {
    const ctx1 = document.getElementById("myAreaChart");
    const existingChart = Chart.getChart("myAreaChart");
    if (existingChart) {
        existingChart.destroy();
    }
    new Chart(ctx1, {
        type: "line",
        data: {
            labels: thangInNam,
            datasets: [
                {
                    label: "Doanh thu của tháng",
                    data: data,
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
};

// thống kê theo ngay
function thongKePriceFollowDay(data) {
    const ctx1 = document.getElementById("thongKeNgay");
    const existingChart = Chart.getChart("thongKeNgay");
    if (existingChart) {
        existingChart.destroy();
    }
    new Chart(ctx1, {
        type: "line",
        data: {
            labels: caInDay,
            datasets: [
                {
                    label: "Doanh thu trong ca",
                    data: data,
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}

// thong ke do thue theo nam start
function callApiDoThue(url) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            var doanhThu = 0;
            response.content.forEach((items) => {
                arrayDoThue.push(items.tenDichVu);
                arraySoLuongDoThue.push(items.soLuongDichVuDaDung);
                doanhThu += items.tongTienThu;
            });
            thongKeDoThue(arraySoLuongDoThue, arrayDoThue);
            $(".doanhThuDoThue").text("Doanh thu: " + curenlyNumber(doanhThu));
        },
        error: function (error) {
            console.log(error);
        }
    })
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

function parseLocalDate(dateString) {
    const [year, month, day] = dateString.split('-').map(Number);
    return {year, month, day};
}

function clickThongKe() {
    $(".thong-ke-do-thue").change(function (event) {
        checkHidden(parseInt(event.target.value));
    })
};

function checkHidden(number) {
    if (number === 1) {
        $(".doThueNam").prop("hidden", false);
        $(".doThueThang").prop("hidden", true);
        $(".doThueNgay").prop("hidden", true);
        $(".doThueNam").val(new Date().getFullYear());
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue("http://localhost:8081/api/v1/admin/thong-ke/do-thue");
        return;
    }
    if (number === 2) {
        $(".doThueNam").prop("hidden", true);
        $(".doThueThang").prop("hidden", false);
        $(".doThueNgay").prop("hidden", true);
        ganValueInputMonth(".doThueThang");
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue("http://localhost:8081/api/v1/admin/thong-ke/do-thue/thang");
        return;
    }
    if (number === 3) {
        $(".doThueNam").prop("hidden", true);
        $(".doThueThang").prop("hidden", true);
        $(".doThueNgay").prop("hidden", false);
        ganValueInputDate(".doThueNgay");
        arrayDoThue = [];
        arraySoLuongDoThue = [];
        callApiDoThue("http://localhost:8081/api/v1/admin/thong-ke/do-thue/ngay");
        return;
    }
}

//chart
function thongKeDoThue(soLuong, data) {
    const existingChart = Chart.getChart("chartDoThue");
    if (existingChart) {
        existingChart.destroy();
    }
    const ctx = document.getElementById("chartDoThue");
    new Chart(ctx, {
        type: "doughnut",
        data: {
            labels: data,
            datasets: [
                {
                    label: "Số lượng",
                    data: soLuong,
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
};
// thong ke do thue theo nam end
//
//
//
// thong ke nuoc uon start

function callApiNuocUong(url) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            var doanhThu = 0;
            response.content.forEach((items) => {
                arrayNuocUong.push(items.tenDichVu);
                arraySoLuongNuocUong.push(items.soLuongDichVuDaDung);
                doanhThu += items.tongTienThu;
            });
            thongKeNuocUong(arraySoLuongNuocUong, arrayNuocUong);
            $(".doanhThuNuocUong").text("Doanh thu: " + curenlyNumber(doanhThu));
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function checkHiddenDoThu() {
    $(".thong-ke-nuoc-uong").change((event) => {
        var number = parseInt(event.target.value);
        if (number === 1) {
            $(".nuocUongNam").prop("hidden", false);
            $(".nuocUongThang").prop("hidden", true);
            $(".nuocUongNgay").prop("hidden", true);
            $(".nuocUongNam").val(new Date().getFullYear());
            arrayNuocUong = [];
            arraySoLuongNuocUong = [];
            callApiNuocUong("http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong");
            return;
        }
        if (number === 2) {
            $(".nuocUongNam").prop("hidden", true);
            $(".nuocUongThang").prop("hidden", false);
            $(".nuocUongNgay").prop("hidden", true);
            ganValueInputMonth(".nuocUongThang");
            arrayNuocUong = [];
            arraySoLuongNuocUong = [];
            callApiNuocUong("http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong/thang");
            return;
        }
        if (number === 3) {
            $(".nuocUongNam").prop("hidden", true);
            $(".nuocUongThang").prop("hidden", true);
            $(".nuocUongNgay").prop("hidden", false);
            ganValueInputDate(".nuocUongNgay");
            arrayNuocUong = [];
            arraySoLuongNuocUong = [];
            callApiNuocUong("http://localhost:8081/api/v1/admin/thong-ke/nuoc-uong/ngay");
            return;
        }
    });

}

function thongKeNuocUong(soLuong, data) {
    const ctx3 = document.getElementById("chartNuocUong");
    const existingChart = Chart.getChart("chartNuocUong");
    if (existingChart) {
        existingChart.destroy();
    }
    new Chart(ctx3, {
        type: "doughnut",
        data: {
            labels: data,
            datasets: [
                {
                    label: "Số lượng",
                    data: soLuong,
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}

// thong ke nuoc uon end


