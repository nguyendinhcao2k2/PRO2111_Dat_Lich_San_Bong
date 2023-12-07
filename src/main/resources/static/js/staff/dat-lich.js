const apiUrl = "http://localhost:8081/api/v1/staff";
let date;
let reloadDate;

function reloadSanBong() {
    let dataObject = {
        date: reloadDate,
        sanBong: 'all'
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: apiUrl + "/search-san-bong",
        data: JSON.stringify(dataObject),
        success: function (responseData) {
            if ($(".content-san").length !== 0) {
                $(".content-san").remove();
            }
            const wrapDiv = $(`<div class='wrap-div'></div>`);
            let emptyField = 0;
            let waitingField = 0;
            let waitingForPayField = 0;
            let workingField = 0;
            let outOfTimeField = 0;
            let allField = 0;
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
                        emptyField++;
                    } else if (ca.trangThai === 0) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-secondary badge-status">Đang chờ nhận sân</span>
                                      </div>`
                        waitingField++;
                    } else if (ca.trangThai === 1) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-success badge-status">Đang hoạt động</span>
                                      </div>`
                        workingField++;
                    } else if (ca.trangThai === 2) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-info badge-status">Chờ thanh toán</span>
                                      </div>`
                        waitingForPayField++;
                    } else if (ca.trangThai === 3) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ</span>
                                      </div>`
                        outOfTimeField++;
                    } else if (ca.trangThai === 4) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ đặt</span>
                                      </div>`
                        outOfTimeField++;
                    }
                    allField++;

                    const card = `<div class="col-md-2 mb-2">
                        <div
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 95%; border-radius: 0px"
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
                                        ${setSelectBox(ca.trangThai,ca.idHoaDonSanCa)}
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px"
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
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                             <span class="badge bg-primary badge-icon">
                                                <i class="far fa-calendar-check fa-lg icon-content"></i>
                                            </span>
                                             <label id="label-date" style="color: black; font-size: 18px; margin-left: 5px">${ca.date}</label>
                                        </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                         <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-clock fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-thoi-gian" style="color: black; font-size: 18px; margin-left: 5px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                         </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-gia" style="color: black; font-size: 18px; margin-left: 5px">${formatCurrencyVND(ca.gia)}</label>
                                        </div>
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
                wrapDiv.append(contentSan)
                menu1.append(contentSan);
            });
            setSelectedCheckBox(date);
            $("#modal-filter").modal('hide');
            $('#all-san-bong').text(allField)
            $('#empty-san-bong').text(emptyField)
            $('#waiting-san-bong').text(waitingField)
            $('#wait-for-pay').text(waitingForPayField)
            $('#is-active').text(workingField)
            $('#out-of-time').text(outOfTimeField)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
};

function filterSanBong() {
    let currentDate = new Date();
    let formattedDate = '';

    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt !== null) {
        genDataTable(tt)
    }
    let selectedValue = $("#select-san-st").val();
    let selectedDateText = $('#date-header-search-staff').val();
    let selectedDate = new Date(selectedDateText);
    if (isNaN(selectedDate.getDate())) {
        formattedDate = 'none';
    } else {
        selectedDate.setHours(0, 0, 0, 0);
        currentDate.setHours(0, 0, 0, 0);
        if (selectedDate < currentDate) {
            alert("Vui lòng chọn ngày hiện tại hoặc trước ngày hiện tại.");
            return;
        }
        let year = selectedDate.getFullYear();
        let month = selectedDate.getMonth() + 1;
        let day = selectedDate.getDate();

        if (day < 10) {
            day = '0' + day;
        }

        if (month < 10) {
            month = '0' + month;
        }
        formattedDate = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "01";
        reloadDate = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "01";
        date = year + "-" + month + "-" + day;
    }

    let dataObject = {
        date: formattedDate,
        sanBong: selectedValue
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: apiUrl + "/search-san-bong",
        data: JSON.stringify(dataObject),
        success: function (responseData) {
            let emptyField = 0;
            let waitingField = 0;
            let waitingForPayField = 0;
            let workingField = 0;
            let outOfTimeField = 0;
            let allField = 0;
            if ($(".content-san").length !== 0) {
                $(".content-san").remove();
            }
            const menu1 = $("#menu_1");
            const wrapDiv = $(`<div class='wrap-div'></div>`);
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
                        emptyField++;
                    } else if (ca.trangThai === 0) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-secondary badge-status">Đang chờ nhận sân</span>
                                      </div>`
                        waitingField++;
                    } else if (ca.trangThai === 1) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-success badge-status">Đang hoạt động</span>
                                      </div>`
                        workingField++;
                    } else if (ca.trangThai === 2) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-info badge-status">Chờ thanh toán</span>
                                      </div>`
                        waitingForPayField++;
                    } else if (ca.trangThai === 3) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ</span>
                                      </div>`
                        outOfTimeField++;
                    } else if (ca.trangThai === 4) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ đặt</span>
                                      </div>`
                        outOfTimeField++;
                    }
                    allField++;

                    const card = `<div class="col-md-2 mb-2">
                        <div2
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 95%; border-radius: 0px"
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
                                        ${setSelectBox(ca.trangThai,ca.idHoaDonSanCa)}
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px"
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
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                             <span class="badge bg-primary badge-icon">
                                                <i class="far fa-calendar-check fa-lg icon-content"></i>
                                            </span>
                                             <label id="label-date" style="color: black; font-size: 18px; margin-left: 5px">${ca.date}</label>
                                        </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                          <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-clock fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-thoi-gian" style="color: black; font-size: 18px; margin-left: 5px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                         </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                       <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-gia" style="color: black; font-size: 18px; margin-left: 5px">${formatCurrencyVND(ca.gia)}</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Trạng thái -->
                            <div class="card-footer border-0" style="background-color: #ffff">
                                ${trangThai}
                            </div>
                        </div2>
                    </div>`;

                    bodySan.append(card);
                });

                blank.append(tenSan);
                blank.append(bodySan);
                contentSan.append(blank);
                wrapDiv.append(contentSan);
                menu1.append(wrapDiv);
            });
            setSelectedCheckBox(date);
            $("#modal-filter").modal('hide');
            $('#all-san-bong').text(allField)
            $('#empty-san-bong').text(emptyField)
            $('#waiting-san-bong').text(waitingField)
            $('#wait-for-pay').text(waitingForPayField)
            $('#is-active').text(workingField)
            $('#out-of-time').text(outOfTimeField)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

};

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
    date = year + "-" + month + "-" + day;
    reloadDate = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "01";
    let tt = JSON.parse(localStorage.getItem("thongTin"));
    if (tt !== null) {
        genDataTable(tt)
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/load-san-bong",
        success: function (responseData) {
            let emptyField = 0;
            let waitingField = 0;
            let waitingForPayField = 0;
            let workingField = 0;
            let outOfTimeField = 0;
            let allField = 0;
            const selectBox = $('#select-san-st').empty();
            const menu1 = $("#menu_1");
            const wrapDiv = $(`<div class='wrap-div'></div>`);
            let option = `<option value="all">Tất Cả</option>`;
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
                        emptyField++;
                    } else if (ca.trangThai === 0) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-secondary badge-status">Đang chờ nhận sân</span>
                                      </div>`
                        waitingField++;
                    } else if (ca.trangThai === 1) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-success badge-status">Đang hoạt động</span>
                                      </div>`
                        workingField++;
                    } else if (ca.trangThai === 2) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-info badge-status">Chờ thanh toán</span>
                                      </div>`
                        waitingForPayField++;
                    } else if (ca.trangThai === 3) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ</span>
                                      </div>`
                        outOfTimeField++;
                    } else if (ca.trangThai === 4) {
                        trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ đặt</span>
                                      </div>`
                        outOfTimeField++;
                    }
                    allField++;
                    const card = `<div class="col-md-2 mb-2">
                        <div
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 95%; border-radius: 0px"
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
                                         ${setSelectBox(ca.trangThai,ca.idHoaDonSanCa)}
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px"
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
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                             <span class="badge bg-primary badge-icon ">
                                                <i class="far fa-calendar-check fa-lg icon-content"></i>
                                            </span>
                                             <label id="label-date" style="color: black; font-size: 18px; margin-left: 5px">${ca.date}</label>
                                        </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                         <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-clock fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-thoi-gian" style="color: black; font-size: 18px; margin-left: 5px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                         </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-gia" style="color: black; font-size: 18px; margin-left: 5px">${formatCurrencyVND(ca.gia)}</label>
                                        </div>
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
                option += `<option value="${sanBong.idSanBong}">${sanBong.tenSanBong}</option>`
                blank.append(tenSan);
                blank.append(bodySan);
                contentSan.append(blank);
                wrapDiv.append(contentSan)
                menu1.append(wrapDiv);
            });
            selectBox.append(option);
            setSelectedCheckBox(year + "-" + month + "-" + day);
            $('#all-san-bong').text(allField)
            $('#empty-san-bong').text(emptyField)
            $('#waiting-san-bong').text(waitingField)
            $('#wait-for-pay').text(waitingForPayField)
            $('#is-active').text(workingField)
            $('#out-of-time').text(outOfTimeField)
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
            price: convertCurrencyStringToNumber(gia),
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
        newRow.append('<td>' + formatCurrencyVND(row.price) + '</td>');
        newRow.append(`<td><button onclick="deleteRow('${tt[i].cbId}-${tt[i].ngay}')" class="btn btn-primary">Xóa</button></td>`);

        // Thêm hàng mới vào tbody
        $('#idTable').append(newRow);
    }
}

function setSelectedCheckBox(date) {
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

function removeSelectedCheckBox(tt) {
    if (tt.ngay === date.trim()) {
        let checkBox = document.getElementById(tt.cbId);
        checkBox.checked = false;
    }
}

function deleteRow(id) {
    let tt = JSON.parse(localStorage.getItem("thongTin"));
    for (let i = 0; i < tt.length; i++) {
        if (tt[i].cbId + "-" + tt[i].ngay === id) {
            removeSelectedCheckBox(tt[i]);
            tt.splice(i, 1);
            genDataTable(tt);
            localStorage.setItem("thongTin", JSON.stringify(tt));
            break;
        }
    }
}

function datSan() {
    let hoTen = $("#hoTenInput").val();
    let emailSend = $("#emailInput").val();
    let sdt = $("#soDienThoaiInput").val();
    let ghiChuSend = $("#ghiChuInput").val();

    //Error field
    let hoTenError = $("#errorHoten");
    let emailError = $("#errorEmail");
    let soDienThoaiError = $("#errorSoDienThoai");

    let check = true;

    if (hoTen === '') {
        hoTenError.text("Họ tên không được để trống");
        check = false;
    } else if (hoTen.length < 6) {
        hoTenError.text("Họ tên phải trên 5 ký tự");
        check = false;
    } else {
        hoTenError.text("");
    }

    if (emailSend === '') {
        emailError.text("Email không được để trống");
        check = false;
    } else if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailSend)) {
        emailError.text("Email không đúng định dạng");
        check = false;
    } else {
        emailError.text("");
    }

    if (sdt === '') {
        soDienThoaiError.text("Số điện thoại không được để trống");
        check = false;
    } else if (!/^[0]\d{9}$/.test(sdt)) {
        soDienThoaiError.text("Số điện thoại phải đúng định dạng");
        check = false;
    } else {
        soDienThoaiError.text("");
    }

    if (check) {
        let tt = JSON.parse(localStorage.getItem("thongTin"));
        if (tt.length === 0 || tt === null) {
            alert("Vui lòng chọn sân bóng");
        } else {
            let thongTin = JSON.parse(localStorage.getItem("thongTin"));
            let dataSend = {
                hoVaTen: hoTen,
                soDienThoai: sdt,
                email: emailSend,
                ghiChu: ghiChuSend,
                thongTinLichDatRequests: thongTin
            }
            $.ajax({
                url: apiUrl + "/dat-lich",
                type: "POST",
                data: JSON.stringify(dataSend),
                contentType: "application/json",
                success: function (data) {
                    localStorage.setItem("thongTin", JSON.stringify([]));
                    $('#modalInfo').modal('hide');
                    $('#idTable').empty();
                    reloadSanBong();
                },
                error: function (error) {
                    alert(error.responseJSON.message);
                    $('#modalInfo').modal('hide');
                }
            });
        }
    }
}

function openModalDanhSachCho() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/show-danh-sach-cho",
        success: function (responseData) {
            $('#tableXacNhan').empty();
            let tr = ``;
            responseData.forEach((dt) => {
                let dataRow = `<tr>
                            <td>${dt.stt}</td>
                            <td>${dt.tenNguoiDat}</td>
                            <td>${dt.soDienThoaiNguoiDat}</td>
                            <td>${dt.email}</td>
                            <td>${dt.ngay}</td>
                            <td>${formatCurrencyVND(dt.tongTien)} VND</td>
                            <td>${formatCurrencyVND(dt.tienCoc)} VND</td>
                            <td>${dt.maTienCoc}</td>
                            <td>
                                 <div class="d-flex flex-column">
                                      <button
                                             onclick="confirmHoaDon('${dt.idHoaDon}')"
                                             class="btn btn-success btn-sm mt-2"
                                             type="button"
                                      >
                                                       Xác nhận
                                      </button>
                                       <button
                                             onclick="detailDanhSach('${dt.idHoaDon}')"
                                             class="btn btn-primary btn-sm mt-2"
                                             type="button"
                                             data-bs-toggle="modal"
                                             data-bs-target="#modal-chi-tiet-danh-sach"
                                      >
                                                       Xem chi tiết
                                      </button>
                                      <button
                                             onclick="deleteHoaDon('${dt.idHoaDon}')"
                                             class="btn btn-outline-danger btn-sm mt-2"
                                             type="button"
                                      >
                                                          Hủy
                                      </button>
                                 </div>
                            </td>                       
                       </tr>`;
                tr += dataRow;
            });
            $('#tableXacNhan').append(tr);
        },
        error: function (e) {
            alert("Có lỗi !!")
        }
    })
    $('#modal-danh-sach-dat-san').modal('show')
}

function deleteHoaDon(idHoaDon) {
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: apiUrl + "/delete-hoa-don?idHoaDon=" + idHoaDon,
        success: function () {
            openModalDanhSachCho()
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function detailDanhSach(idHoaDon) {
    // $('#modal-danh-sach-dat-san').modal('hide')
    $('#modal-chi-tiet-danh-sach').modal('show')
}

function closeModalChiTiet() {
    $('#modal-chi-tiet-danh-sach').modal('hide')
    $('#modal-danh-sach-dat-san').modal('show')
}

function searchDanhSachCho() {
    let text = $('#input-search-danh-sach-cho').val();

    let formData = {
        textString: text,
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: apiUrl + "/filter-lich-dat",
        data: JSON.stringify(formData),
        success: function (responseData) {
            $('#tableXacNhan').empty();
            let tr = ``;
            responseData.forEach((dt) => {
                let dataRow = `<tr>
                            <td>${dt.stt}</td>
                            <td>${dt.tenNguoiDat}</td>
                            <td>${dt.soDienThoaiNguoiDat}</td>
                            <td>${dt.email}</td>
                            <td>${dt.ngay}</td>
                            <td>${formatCurrencyVND(dt.tongTien)} VND</td>
                            <td>${dt.tienCoc} VND</td>
                            <td>${dt.maTienCoc}</td>
                            <td>
                                 <div class="d-flex flex-column">
                                      <button
                                             onclick="confirmHoaDon('${dt.idHoaDon}')"
                                             class="btn btn-success btn-sm mt-2"
                                             type="button"
                                      >
                                                       Xác nhận
                                      </button>
                                       <button
                                             onclick="detailDanhSach('${dt.idHoaDon}')"
                                             class="btn btn-primary btn-sm mt-2"
                                             type="button"
                                             data-bs-toggle="modal"
                                             data-bs-target="#modal-chi-tiet-danh-sach"
                                      >
                                                       Xem chi tiết
                                      </button>
                                      <button
                                             onclick="deleteHoaDon('${dt.idHoaDon}')"
                                             class="btn btn-outline-danger btn-sm mt-2"
                                             type="button"
                                      >
                                                          Hủy
                                      </button>
                                 </div>
                            </td>                       
                       </tr>`;
                tr += dataRow;
            });
            $('#tableXacNhan').append(tr);
        },
        error: function (e) {
            alert("Có lỗi !!")
        }
    })
}

function filterTrangThai(param) {
    if (param === 'all') {
        reloadSanBong();
    } else {
        callApiFilter(param)
    }
}

function callApiFilter(param) {
    let dataObject = {
        date: reloadDate,
        sanBong: 'all'
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: apiUrl + "/search-san-bong",
        data: JSON.stringify(dataObject),
        success: function (responseData) {
            if ($(".content-san").length !== 0) {
                $(".content-san").remove();
            }
            let check = 0;
            const wrapDiv = $(`<div class='wrap-div'></div>`);
            const menu1 = $("#menu_1");
            let data = responseData;
            data.forEach((sanBong, index) => {
                const contentSan = $(`<div class='content-san'></div>`);
                const blank = $(`<div id="san-content-${index}"></div>`);
                const tenSan = `<div class='ten-san mt-4'><h4 class='text-dark'>${sanBong.tenSanBong}</h4></div>`;
                const bodySan = $("<div class='body-san row'></div>");

                sanBong.loadCaResponses.forEach((ca, i) => {
                    if (ca.trangThai === param) {
                        check++;
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
                        } else if (ca.trangThai === 4) {
                            trangThai = `<div class="card-footer border-0" style="background-color: #ffff">
                                        <span class="badge rounded-pill bg-danger badge-status">Quá giờ đặt</span>
                                      </div>`
                        }
                        const card = `<div class="col-md-2 mb-2">
                        <div
                            class="card card-san"
                            id="content-san-${index}-card-san-${i}"
                            style="width: 95%; border-radius: 0px"
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
                                        ${setSelectBox(ca.trangThai,ca.idHoaDonSanCa)}
                                    </div>
                                </div>
                                <div
                                    class="card-subtitle d-flex justify-content-end"
                                >
                                    <label
                                        style="color: black; font-size: 18px"
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
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                             <span class="badge bg-primary badge-icon">
                                                <i class="far fa-calendar-check fa-lg icon-content"></i>
                                            </span>
                                             <label id="label-date" style="color: black; font-size: 18px; margin-left: 5px">${ca.date}</label>
                                        </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                         <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-clock fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-thoi-gian" style="color: black; font-size: 18px; margin-left: 5px">${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}</label>
                                         </div>
                                    </div>
                                    <div class="col-md-12 mt-2">
                                        <div style="display: flex; align-items: center">
                                            <span class="badge bg-primary badge-icon">
                                                <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                            </span>
                                            <label id="label-gia" style="color: black; font-size: 18px; margin-left: 5px">${formatCurrencyVND(ca.gia)}</label>
                                        </div>
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
                    }
                });

                if (check > 0) {
                    blank.append(tenSan);
                    blank.append(bodySan);
                    contentSan.append(blank);
                    wrapDiv.append(contentSan)
                    menu1.append(contentSan);
                }
                check = 0;
            });
            setSelectedCheckBox(date);
            $("#modal-filter").modal('hide');

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function setSelectBox(trangThai, idHoaDonSanCa) {
    if (trangThai === 0) {
        return `<ul
                                            class="dropdown-menu dropdown-menu-end"
                                        >
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Check In
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Hủy
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item">
                                                    Đổi Sân
                                                </a>
                                            </li>
                                        </ul>`
    } else if (trangThai === 1) {
        return `<ul
                                            class="dropdown-menu dropdown-menu-end"
                                        >
                                            <li>
                                                <a href="http://localhost:8081/api/v1/staff/thanh-toan/thanh-toan-hoa-don/${idHoaDonSanCa}" class="dropdown-item">
                                                    Thanh toán
                                                </a>
                                            </li>
                                        </ul>`
    }
    return ``;
}

function formatCurrencyVND(amount) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    return formatter.format(amount);
}

function convertCurrencyStringToNumber(currencyString) {
    // Remove non-numeric characters and convert to a number
    const numericValue = parseInt(currencyString.replace(/[^\d]/g, ''), 10);

    return numericValue;
}