const apiUrl = "http://localhost:8081/api/v1/staff";
let date;

$(document).ready(function () {
    let currentDate = new Date();

    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt !== null) {
        genDataTable(tt)
    }
    $("#date-header-search-staff").on("change", function () {
        let selectedDate = new Date($(this).val());
        selectedDate.setHours(0, 0, 0, 0);
        currentDate.setHours(0, 0, 0, 0);

        if (selectedDate < currentDate) {
            alert("Vui lòng chọn ngày hiện tại hoặc trước ngày hiện tại.");
        } else {
            let year = selectedDate.getFullYear();
            let month = selectedDate.getMonth() + 1;
            let day = selectedDate.getDate();

            if (day < 10) {
                day = '0' + day;
            }

            if (month < 10) {
                month = '0' + month;
            }
            let formattedDate = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "01";
            date = year + "-" + month + "-" + day;
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: apiUrl + "/load-san-bong?date=" + formattedDate,
                success: function (responseData) {
                    if ($(".content-san").length !== 0) {
                        $(".content-san").remove();
                    }
                    const menu1 = $("#menu_1");
                    let data = responseData;
                    data.forEach((sanBong, index) => {
                        const contentSan = $(`<div class='content-san'></div>`);
                        const blank = $(`<div id="san-content-${index}"></div>`);
                        const tenSan = `<div class='ten-san mt-4'><h4 class='text-dark'>${sanBong.tenSanBong}</h4></div>`;
                        const bodySan = $("<div class='body-san row'></div>");

                        sanBong.loadCaResponses.forEach((ca, i) => {
                            let trangThai = ``;
                            if (ca.trangThai === null) {
                                trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-primary badge-status" >Đang trống</span>
                                      </div>`;
                            } else if (ca.trangThai === 0) {
                                trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-secondary badge-status">Đang chờ nhận sân</span>
                                      </div>`
                            } else if (ca.trangThai === 1) {
                                trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-success badge-status">Đang hoạt động</span>
                                      </div>`
                            } else if (ca.trangThai === 2) {
                                trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-info badge-status">Chờ thanh toán</span>
                                      </div>`
                            } else if (ca.trangThai === 3) {
                                trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ</span>
                                      </div>`
                            }

                            const card = `<div class="col-md-4 mb-4">
                        <div
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 100%; border-radius: 0px"
                        >
                            <!-- Header card -->
                            <div
                                class="card-header"
                                style="background: #ffff"
                            >
                                <div
                                    class="card-title d-flex justify-content-end"
                                >
                                    <div class="btn-group">
                                        <button
                                            type="button"
                                            class="btn dropdown-toggle"
                                            data-bs-toggle="dropdown"
                                            aria-expanded="false"
                                            style="box-shadow: none"
                                        >
                                            <label id="label-ca"
                                                style="color: black; font-size: 18px; font-weight: bold;"
                                            >${ca.tenCa}</label>
                                        </button>
                                        <ul
                                            class="dropdown-menu dropdown-menu-end"
                                        >
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 1
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 2
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 3
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px; font-weight: bold;"
                                    >Loại sân : ${ca.loaiSan}</label>
                                </div>
                            </div>
                            <!-- Check box -->
                            <div
                                id="checkboxContainer"
                                style="display: none; position: absolute; top: 10px; left: 10px;"
                            >
                                <input
                                    value="${ca.idResponse}"
                                    type="checkbox"
                                    style="width: 20px; height: 20px; margin-right: 5px;"
                                    id="checkbox-sb-${index}-ca-${i}"
                                    onclick="checkBoxFunction('san-content-${index}','content-san-${index}-card-san-${i}','checkbox-sb-${index}-ca-${i}')"
                                />
                            </div>
                            <!-- Card body -->
                            <div class="card-body m-1">
                                <div class="row">
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="far fa-calendar-check fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-date" style="color: black; font-size: 18px">${ca.date}</label>
                                    </div>
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-clock fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-thoi-gian" style="color: black; font-size: 18px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                    </div>
                                    <div class="col-md-12 mt-1">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-gia" style="color: black; font-size: 18px">${ca.gia} VND</label>
                                    </div>
                                </div>
                            </div>
                            <!-- Trạng thái -->
                            <div class="card-footer border-0" style="background-color: #ffff">
                                ${trangThai}
                            </div>
                        </div>
                    </div>`;

                            bodySan.append(card);
                        });

                        blank.append(tenSan);
                        blank.append(bodySan);
                        contentSan.append(blank);
                        menu1.append(contentSan);
                    });
                    setSelectedCheckBox(date);
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        }
    });
});

window.onload = function () {
    let currentDate = new Date();
    let year = currentDate.getFullYear();
    let month = currentDate.getMonth() + 1;
    let day = currentDate.getDate();
    if (day < 10) {
        day = '0' + day;
    }

    if (month < 10) {
        month = '0' + month;
    }
    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt !== null) {
        genDataTable(tt)
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/load-san-bong",
        success: function (responseData) {
            const menu1 = $("#menu_1");
            let data = responseData;
            data.forEach((sanBong, index) => {
                const contentSan = $(`<div class='content-san'></div>`);
                const blank = $(`<div id="san-content-${index}"></div>`);
                const tenSan = `<div class='ten-san mt-4'><h4 class='text-dark'>${sanBong.tenSanBong}</h4></div>`;
                const bodySan = $("<div class='body-san row'></div>");

                sanBong.loadCaResponses.forEach((ca, i) => {
                    let trangThai = ``;
                    if (ca.trangThai === null) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-primary badge-status" >Đang trống</span>
                                      </div>`;
                    } else if (ca.trangThai === 0) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-secondary badge-status">Đang chờ nhận sân</span>
                                      </div>`
                    } else if (ca.trangThai === 1) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-success badge-status">Đang hoạt động</span>
                                      </div>`
                    } else if (ca.trangThai === 2) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-info badge-status">Chờ thanh toán</span>
                                      </div>`
                    } else if (ca.trangThai === 3) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ</span>
                                      </div>`
                    }

                    const card = `<div class="col-md-4 mb-4">
                        <div
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 100%; border-radius: 0px"
                        >
                            <!-- Header card -->
                            <div
                                class="card-header"
                                style="background: #ffff"
                            >
                                <div
                                    class="card-title d-flex justify-content-end"
                                >
                                    <div class="btn-group">
                                        <button
                                            type="button"
                                            class="btn dropdown-toggle"
                                            data-bs-toggle="dropdown"
                                            aria-expanded="false"
                                            style="box-shadow: none"
                                        >
                                            <label id="label-ca"
                                                style="color: black; font-size: 18px; font-weight: bold;"
                                            >${ca.tenCa}</label>
                                        </button>
                                        <ul
                                            class="dropdown-menu dropdown-menu-end"
                                        >
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 1
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 2
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In 3
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px; font-weight: bold;"
                                    >Loại sân : ${ca.loaiSan}</label>
                                </div>
                            </div>
                            <!-- Check box -->
                            <div
                                id="checkboxContainer"
                                style="display: none; position: absolute; top: 10px; left: 10px;"
                            >
                                <input
                                    value="${ca.idResponse}"
                                    type="checkbox"
                                    style="width: 20px; height: 20px; margin-right: 5px;"
                                    id="checkbox-sb-${index}-ca-${i}"
                                    onclick="checkBoxFunction('san-content-${index}','content-san-${index}-card-san-${i}','checkbox-sb-${index}-ca-${i}')"
                                />
                            </div>
                            <!-- Card body -->
                            <div class="card-body m-1">
                                <div class="row">
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="far fa-calendar-check fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-date" style="color: black; font-size: 18px">${ca.date}</label>
                                    </div>
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-clock fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-thoi-gian" style="color: black; font-size: 18px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                    </div>
                                    <div class="col-md-12 mt-1">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                        </span>
                                        <label id="label-gia" style="color: black; font-size: 18px">${ca.gia} VND</label>
                                    </div>
                                </div>
                            </div>
                            <!-- Trạng thái -->
                            <div class="card-footer border-0" style="background-color: #ffff">
                                ${trangThai}
                            </div>
                        </div>
                    </div>`;

                    bodySan.append(card);
                });

                blank.append(tenSan);
                blank.append(bodySan);
                contentSan.append(blank);
                menu1.append(contentSan);
            });
            setSelectedCheckBox(year + "-" + month + "-" + day);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function checkBoxFunction(sanContent, cardCa, cb) {
    let checkBox = document.getElementById(cb);
    if (checkBox.checked == true) {
        let cardCaDom = document.getElementById(cardCa);
        let tenSanBong = document.getElementById(sanContent).querySelector(".text-dark").textContent;
        let tc = cardCaDom.querySelector("#label-ca").textContent;
        let date = cardCaDom.querySelector("#label-date").textContent;
        let thoiGian = cardCaDom.querySelector("#label-thoi-gian").textContent;
        let gia = cardCaDom.querySelector("#label-gia").textContent;

        let thongTinSanBong = {
            cbId: cb,
            id: checkBox.value,
            tenSan: tenSanBong,
            tenCa: tc,
            ngay: date,
            time: thoiGian,
            price: gia.split(" ")[0],
        };
        let thongTinSanBongList = JSON.parse(localStorage.getItem("thongTin"));
        if (thongTinSanBongList === null) {
            let sanBongList = [thongTinSanBong];
            localStorage.setItem("thongTin", JSON.stringify(sanBongList));
            genDataTable(sanBongList);
        } else {
            let tt = JSON.parse(localStorage.getItem("thongTin"));
            tt.push(thongTinSanBong);
            localStorage.setItem("thongTin", JSON.stringify(tt));
            genDataTable(tt);
        }
    } else {
        let tt = JSON.parse(localStorage.getItem("thongTin"));
        let thongTinSanBong = tt.filter(t => t.id === checkBox.value);
        tt.splice(thongTinSanBong, 1);
        localStorage.setItem("thongTin", JSON.stringify(tt));
        genDataTable(tt);
    }
}

function genDataTable(tt) {
    $('#idTable').empty();
    for (let i = 0; i < tt.length; i++) {
        let row = tt[i];
        let newRow = $('<tr>');
        newRow.append('<td>' + row.tenSan + '</td>');
        newRow.append('<td>' + row.ngay + '</td>');
        newRow.append('<td>' + row.tenCa + '</td>');
        newRow.append('<td>' + row.time + '</td>');
        newRow.append('<td>' + row.price + ' VND</td>');
        newRow.append(`<td><button onclick="deleteRow('${tt[i].cbId}-${tt[i].ngay}')" class="btn btn-primary">Xóa</button></td>`);

        // Thêm hàng mới vào tbody
        $('#idTable').append(newRow);
    }
}

function setSelectedCheckBox(date) {
    if (date === undefined) {
        let currentDate = new Date();
        let year = currentDate.getFullYear();
        let month = currentDate.getMonth() + 1;
        let day = currentDate.getDate();
        if (day < 10) {
            day = '0' + day;
        }

        if (month < 10) {
            month = '0' + month;
        }
        date = year + "-" + month + "-" + day
    }
    console.log(date);
    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt) {
        for (let i = 0; i < tt.length; i++) {
            if (tt[i].ngay === date.trim()) {
                let checkBox = document.getElementById(tt[i].cbId);
                checkBox.checked = true;
            }
        }
    }
}

function deleteRow(id) {
    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt) {
        for (let i = 0; i < tt.length; i++) {
            if (tt[0].cbId + "-" + tt[i].ngay === id) {
                tt.splice(i, 1);
                genDataTable(tt);
                localStorage.setItem("thongTin", JSON.stringify(tt));
                setSelectedCheckBox(date);
                break;
            }
        }
    }
}