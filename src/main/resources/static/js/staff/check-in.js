const apiUrl = "http://localhost:8081/api/v1/staff";

function search() {
    let param = $('#inputSearch').val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/list-check-in?param=" + param,
        success: function (responseData) {
            console.log(responseData)
            $('#tbody').empty();
            let trRow = ``;
            responseData.forEach((ck, index) => {
                trRow += `<tr>
                                <td>${ck.stt}</td>
                                <td>${ck.hoTen}</td>
                                <td>${ck.soDienThoai}</td>
                                <td>${ck.tenSanBong}</td>
                                <td>${ck.tenCa}</td>
                                <td>${formatCurrencyVND(ck.tienCoc)}</td>
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
        },
        error: function (e) {
            alert("Có lỗi !!")
        }
    })
}

function checkInLichDat(param) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiUrl + "/check-in/" + param,
        success: function (responseData) {
            alert(responseData.content)
        },
        error: function (e) {
            console.log(e)
            alert("Có lỗi !!")
        }
    })
}

function formatCurrencyVND(amount) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    return formatter.format(amount);
}
