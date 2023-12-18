const apiUrl = "http://localhost:8081/api/v1/staff";

window.onload = function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/load-hoa-don-check-in",
        success: function (responseData) {
            if (responseData.length === 0) {
                let tbody = $('#tbody');
                tbody.empty();
                tbody.append(`<td colspan="9" class="alert alert-danger noContent" role="alert">
                                    <h1 class="" style="font-size: 20px">Không có dữ liệu</h1>
                               </td>`)
            } else {
                $('#tbody').empty();
                let trRow = ``;
                responseData.forEach((ck, index) => {
                    trRow += `<tr>
                                <td>${index + 1}</td>
                                <td>${ck.hoTen}</td>
                                <td>${ck.soDienThoai}</td>
                                <td>${ck.tenSanBong}</td>
                                <td>${ck.tenCa}</td>
                                <td>${formatCurrencyVND(ck.tienSan)}</td>
                                <td>${ck.ngayDenSan}</td>
                                <td>${ck.thoiGianBatDau} - ${ck.thoiGianKetThuc}</td>                       
                                <td>
                                    <div class="d-flex flex-column">
                                        <button onclick="checkInLichDat('${ck.idHDSanCa}')" class="btn btn-success  btn-sm " type="button">
                                           Thanh toán
                                        </button>
                                    </div>
                                </td>
                            </tr>`
                });
                $('#tbody').append(trRow);
            }
        },
        error: function (e) {
            alert("Có lỗi !!")
        }
    })
}

function search() {
    let param = $('#inputSearch').val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/list-check-in?param=" + param,
        success: function (responseData) {
            if (responseData.length === 0) {
                let tbody = $('#tbody');
                tbody.empty();
                tbody.append(`<div style="width: 100%"  class="alert alert-danger noContent" role="alert">
                                    <h1 class="" style="font-size: 20px">Không có dữ liệu</h1>
                               </div>`)
            } else {
                $('#tbody').empty();
                let trRow = ``;
                responseData.forEach((ck, index) => {
                    trRow += `<tr>
                                <td>${index + 1}</td>
                                <td>${ck.hoTen}</td>
                                <td>${ck.soDienThoai}</td>
                                <td>${ck.tenSanBong}</td>
                                <td>${ck.tenCa}</td>
                                <td>${formatCurrencyVND(ck.tienSan)}</td>
                                <td>${ck.ngayDenSan}</td>
                                <td>${ck.thoiGianBatDau} - ${ck.thoiGianKetThuc}</td>                       
                                <td>
                                    <div class="d-flex flex-column">
                                        <button onclick="checkInLichDat('${ck.idHDSanCa}')" class="btn btn-success  btn-sm " type="button">
                                            Check in
                                        </button>
                                    </div>
                                </td>
                            </tr>`
                });
                $('#tbody').append(trRow);
            }
        },
        error: function (e) {
            alert("Có lỗi !!")
        }
    })

}

function checkInLichDat(param) {
    Swal.fire({
        title: "Bạn có chắc chắn check in sân ca này  không ?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Xác nhận',
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: apiUrl + "/check-in/" + param,
                success: function (responseData) {
                    alert(responseData.content)
                    window.location.href = "/api/v1/staff/check-in";
                },
                error: function (e) {
                    alert(e.content)
                }
            })
        }
    });
}

function formatCurrencyVND(amount) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    return formatter.format(amount);
}


function showHoaDonCheckIn() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/show-hd-check-in",
        success: function (responseData) {
            if (responseData.length === 0) {
                let idTable = $('#idtb');
                idTable.empty();
                idTable.append(`<td colspan="11" class="alert alert-danger noContent" role="alert">
                                    <h1 class="" style="font-size: 20px">Không có dữ liệu</h1>
                               </td>`);
            } else {
                $('#idtb').empty();
                let trRow = ``;
                responseData.forEach((ck, index) => {
                    trRow += `<tr>
                                <td>${index + 1}</td>
                                <td>${ck.hoTen}</td>
                                <td>${ck.soDienThoai}</td>
                                <td>${ck.tenSanBong}</td>
                                <td>${ck.tenCa}</td>
                                <td>${formatCurrencyVND(ck.tienSan)}</td>
                                <td>${ck.ngayDenSan}</td>
                                <td>${ck.thoiGianBatDau} - ${ck.thoiGianKetThuc}</td>      
                                <td>${ck.thoiGianCheckIn}</td>
                                <td>Đã check in</td>             
                                <td>
                                    <div class="d-flex flex-column">
                                        <a href="http://localhost:8081/api/v1/staff/thanh-toan/thanh-toan-hoa-don/${ck.idHDSanCa}" class="btn btn-success  btn-sm " type="button">
                                            Thanh toán
                                        </a>
                                    </div>
                                </td>
                            </tr>`;
                });
                $('#idtb').append(trRow);
            }
        },
        error: function (e) {
            createAndShowToast('bg-danger', 'Thông báo check in', "Có lỗi !!");
        }
    });
}
