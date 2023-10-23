const apiUrl = "http://localhost:8081/api/v1/staff"

$(document).ready(function () {
    $("#date-header-search-staff").on("change", function () {
        var selectedDate = new Date($(this).val());
        var currentDate = new Date();
        selectedDate.setHours(0, 0, 0, 0);
        currentDate.setHours(0, 0, 0, 0);

        if (selectedDate < currentDate) {
            alert("Vui lòng chọn ngày hiện tại hoặc trước ngày hiện tại.");
        } else {
            let year = selectedDate.getFullYear();
            let month = selectedDate.getMonth() + 1;
            let day = selectedDate.getDate();

            let formattedDate = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "01";
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
                    data.forEach(sanBong => {
                        const contentSan = $("<div class='content-san'></div>");
                        const blank = $("<div class=''></div>");
                        const tenSan = `<div class='ten-san mt-4'><h4 class='text-dark'>${sanBong.tenSanBong}</h4></div>`;
                        const bodySan = $("<div class='body-san row'></div>");

                        sanBong.loadCaResponses.forEach(ca => {
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
                                            <label
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
                                    class="myCheckbox"
                                />
                            </div>
                            <!-- Card body -->
                            <div class="card-body m-1">
                                <div class="row">
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="far fa-calendar-check fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">${ca.date}</label>
                                    </div>
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-clock fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">
                                            ${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}
                                        </label>
                                    </div>
                                    <div class="col-md-12 mt-1">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">${ca.gia} VND</label>
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
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        }
    });
});

window.onload = function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/load-san-bong",
        success: function (responseData) {
            const menu1 = $("#menu_1");
            let data = responseData;
            data.forEach(sanBong => {
                const contentSan = $("<div class='content-san'></div>");
                const blank = $("<div class=''></div>");
                const tenSan = `<div class='ten-san mt-4'><h4 class='text-dark'>${sanBong.tenSanBong}</h4></div>`;
                const bodySan = $("<div class='body-san row'></div>");

                sanBong.loadCaResponses.forEach(ca => {
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
                                            <label
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
                                    id="myCheckbox"
                                />
                            </div>
                            <!-- Card body -->
                            <div class="card-body m-1">
                                <div class="row">
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="far fa-calendar-check fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">${ca.date}</label>
                                    </div>
                                    <div class="col-md-6">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-clock fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">
                                            ${ca.thoiGianBatDau} - ${ca.thoiGianKetthuc}
                                        </label>
                                    </div>
                                    <div class="col-md-12 mt-1">
                                        <span class="badge bg-dark badge-icon">
                                            <i class="fas fa-dollar-sign fa-lg icon-content"></i>
                                        </span>
                                        <label style="color: black; font-size: 18px">${ca.gia} VND</label>
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
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}