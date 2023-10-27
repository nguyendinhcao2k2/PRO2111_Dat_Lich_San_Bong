var array = [];
var arrayCaInDay = [0, 0, 0, 0];
var thangInNam = [];
var caInDay = ["00:01:00 - 06:00:00", "06:01:00 - 12:00:00", "12:01:00 - 18:00:00", "18:01:00 - 00:00:00"];
thangInNams();
$(document).ready(() => {
    yearpick();
    $(".yearpicker").val(new Date().getFullYear());
    var url = "http://localhost:8081/api/v1/admin/thong-ke";
    $(".yearpicker").change((event) => {
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
    $.ajax({
        type: "GET",
        url: url,
        success: (response) => {
            console.log(response)
            response.content.thongKeTheoNamAdminResponses.forEach((elem) => {
                checkThang(elem.monthName, elem.totalPrice);
            });
            response.content.thongKeTheoCaAdminResponses.forEach((items) => {
                checkCaInDay(items.caName, items.totalPrice === null ? 0 : items.totalPrice);
            });
            thongKePriceFollowYear(array);
            thongKePriceFollowDay(arrayCaInDay);
        },
        error: (error) => {
            console.log(error);
        }
    });
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

const ctx = document.getElementById("myChart");

new Chart(ctx, {
    type: "bar",
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [
            {
                label: "# of Votes",
                data: [12, 19, 3, 5, 2, 3],
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


const ctx2 = document.getElementById("myPieChart");

new Chart(ctx2, {
    type: "doughnut",
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [
            {
                label: "# of Votes",
                data: [12, 19, 3, 5, 2, 3],
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
myArea;
const ctx3 = document.getElementById("myArea");

new Chart(ctx3, {
    type: "bar",
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [
            {
                label: "# of Votes",
                data: [12, 19, 3, 5, 2, 3],
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
