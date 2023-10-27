// Biến lưu trữ đối tượng lịch để sử dụng trong openModal
var calendar;
// Biến để theo dõi ô cell cuối cùng được click
var lastClickedDate = null;
// Biến để theo dõi số lần click
var clickCount = 0;
//  Biến calendar để lưu tạm
var selectedDateFromFullCalendar; // Declare a global variable to store the selected date
var mobiscrollInstanceSingleSelectDate;
var mobiscrollInstanceMultiSelectDate;
var mobiscrollInstanceSelectWeek;
var mobiscrollInstanceSelectRangeDay;
// Lấy ra table của
var tableBody = document.querySelector(".click-event-fullcalendar tbody");
// Khai báo biến global để theo dõi ngày đã được hiển thị hay chưa
var currentSelectedDate = ""; // Initialize a variable to store the currently selected date
// Config FullCalendar
document.addEventListener("DOMContentLoaded", function () {

    var calendarEl = document.getElementById("calendar");
    calendar = new FullCalendar.Calendar(calendarEl, {
        selectable: true,
        headerToolbar: {
            left: "prev,next,today",
            center: "title",
            right: "dayGridMonth,timeGridWeek,timeGridDay",
        },
        // Set the locale to Vietnamese
        locale: "vi",
        views: {
            dayGridMonth: {
                // Customize the "dayGridMonth" view name
                titleFormat: { year: "numeric", month: "long" },
            },
            timeGridWeek: {
                // Customize the "timeGridWeek" view name
                titleFormat: { year: "numeric", month: "short", day: "numeric" },
            },
            timeGridDay: {
                // Customize the "timeGridDay" view name
                titleFormat: { year: "numeric", month: "long", day: "numeric" },
            },
        },
        //  Config cho click vào cell day hiển thị modal
        dateClick: function (info) {
            if (
                lastClickedDate &&
                info.date.getTime() !== lastClickedDate.getTime()
            ) {
                // Kiểm tra nếu đã click vào ô cell khác, đặt lại clickCount thành 1
                clickCount = 1;
            } else if (clickCount == 3) {
                clickCount = 1;
            } else {
                clickCount++;
            }

            if (clickCount == 1) {
                // hiện thị ca đã chọn
                handleCalendarDateClick(info.date);
                //
                var selectDateClickShowEvent = info.date;
                var eventsClickShowEvent = calendar.getEvents();

                // Lọc sự kiện cho ngày được click
                var eventsForSelectedDate = eventsClickShowEvent.filter(function (
                    event
                ) {
                    return isSameDay(event.start, selectDateClickShowEvent);
                });
                // Hiển thị các sự kiện trong bảng
                displayEventsInTable(eventsForSelectedDate);

                console.log(eventsForSelectedDate);
                //check
                console.log("hiện thị ca đã chọn -Click count: " + clickCount);
                // Click 1 lần đóng modal
                closeModal1();
            } else if (clickCount == 2) {
                selectedDateFromFullCalendar = info.date;

                var cellElement = info.dayEl; // Lấy đối tượng DOM của ô cell đã click
                var modal = document.getElementById("myModal1");

                // Lấy ngày trong tuần của ô cell (0 là Chủ Nhật, 6 là Thứ Bảy)
                var dayOfWeek = info.date.getDay();

                // Lấy kích thước và vị trí của ô cell và modal
                var cellRect = cellElement.getBoundingClientRect();
                var modalRect = modal.getBoundingClientRect();

                // LAMNP FIX MODAL RESULT LEFT ON 7, CN
                // Điều chỉnh vị trí top và left của modal
                var modalTop =
                    cellRect.top + cellRect.height / 2 - modalRect.height / 2;
                var modalLeft = cellRect.left + cellRect.width;

                // Kiểm tra xem cell có phải là Thứ 7 hoặc Chủ Nhật không
                if (dayOfWeek === 0) {
                    // Nếu là Chủ Nhật, di chuyển sang trái 2 ngày
                    modalLeft -= cellRect.width * 5;
                } else if (dayOfWeek === 6) {
                    // Nếu là Thứ Bảy, di chuyển sang trái 1 ngày
                    modalLeft -= cellRect.width * 5;
                }

                // LAMNP NODE : Nó đang ăn theo thằng thứ 7 và chủ nhật
                // Thiết lập vị trí của modal
                modal.style.top = modalTop - 15 + "px";
                modal.style.left = modalLeft - 15 + "px";

                // Cái này dùng để cố định modal khi di cuộn màn hình
                // Triển làm
                modal.style.position = "absolute";

                // Đặt kích thước mặc định cho modal
                modal.style.width = "auto";
                modal.style.maxHeight = "auto";

                // Hiển thị modal
                modal.classList.add("visible");

                // Đặt sự kiện để đóng modal khi người dùng click vào nút đóng (X)
                document
                    .getElementById("closeModal1")
                    .addEventListener("click", function () {
                        modal.classList.remove("visible");
                    });
                console.log(" hiện modal chọn ca-Click count: " + clickCount);
            } else {
                // Click 3 lần đóng modal
                closeModal1();
                // Check
                console.log("đóng modal -Click count: " + clickCount);
            }
            lastClickedDate = info.date;
        },
    });


    // Full calendar rendar
    calendar.render();

    document.getElementById("myModal1").addEventListener("click", function () {
        if (lastClickedDate) {
            // Chọn lại ô cell trước đó khi click trên modal
            calendar.select(lastClickedDate);
        }
    });
});

// LAMNP 17/10/2023, 20/10/2023 UPDATE
// ADD sự kiện click vào cell day có ca hiển thị sang thông tin
// Hàm để hiển thị các sự kiện trong bảng
function displayEventsInTable(events) {
    // Xóa tất cả dữ liệu cũ trong bảng
    tableBody.innerHTML = "";
    // Thêm các sự kiện mới vào bảng
    events.forEach(function (event) {
        // Chuyển đổi một thời gian cụ thể sang múi giờ Việt Nam
        // Sử dụng múi giờ Việt Nam (GMT+7)
        const startTimeVietnam = event.start.toLocaleTimeString("en-US", {
            timeZone: "Asia/Ho_Chi_Minh",
            hour: "2-digit",
            minute: "2-digit",
        });
        const endTimeVietnam = event.end.toLocaleTimeString("en-US", {
            timeZone: "Asia/Ho_Chi_Minh",
            hour: "2-digit",
            minute: "2-digit",
        });

        // Lấy ra ngày từ sự kiện
        const dateVietnam = event.start.toLocaleDateString("en-US", {
            timeZone: "Asia/Ho_Chi_Minh",
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        });

        // Chuyển định dạng ngày tháng năm thành "dd - MM - yyyy"
        const dateParts = dateVietnam.split("/");
        const formattedDate =
            dateParts[1] + "-" + dateParts[0] + "-" + dateParts[2];

        addToTable(event.title, startTimeVietnam, endTimeVietnam, formattedDate);
    });
}

// Hàm để so sánh hai ngày xem chúng có cùng một ngày khôngs
function isSameDay(date1, date2) {
    return (
        date1.getDate() === date2.getDate() &&
        date1.getMonth() === date2.getMonth() &&
        date1.getFullYear() === date2.getFullYear()
    );
}

// LAMNP 20/10/2023 UPDATE TABLE
// Hàm để thêm thông tin ca vào bảng
function addToTable(caInfo, timeStart, timeEnd, dateVietnam) {
    // Create a row for the event information
    var rowInfo = tableBody.insertRow();
    var cell1 = rowInfo.insertCell(0);
    cell1.style.whiteSpace = "pre-line"; // Đặt white-space để hiển thị dòng mới
    cell1.textContent =
        caInfo + ": " + timeStart + " - " + timeEnd + "\n Ngày: " + dateVietnam;

    // Sử dụng insertAdjacentHTML để thêm mã HTML
    var cell2 = rowInfo.insertCell(1);
    cell2.insertAdjacentHTML(
        "beforeend",
        `
       <div class="d-flex flex-column">
         <button class="btn btn-danger btn-sm xoa-san-button" type="button">
           Xóa
         </button>
         <button class="btn btn-outline-primary btn-sm mt-2 doi-san-button" type="button">
           Đổi sân
         </button>
       </div>
     `
    );

    // Lấy các nút Chi Tiết và Đặt Sân bằng class
    var chiTietButton = cell2.querySelector(".xoa-san-button");
    var datSanButton = cell2.querySelector(".doi-san-button");

    // Thêm xử lý sự kiện cho nút Chi Tiết
    chiTietButton.addEventListener("click", function () {
        // Thực hiện xử lý khi nút Chi Tiết được nhấn
        // Ví dụ: Hiển thị thông tin chi tiết
        alert("Chi Tiết: " + caInfo);
    });

    // Thêm xử lý sự kiện cho nút Đặt Sân
    datSanButton.addEventListener("click", function () {
        // Thực hiện xử lý khi nút Đặt Sân được nhấn
        // Ví dụ: Đặt sân
        alert("Đặt Sân: " + caInfo);
    });
}

//  LAMNP
//  SET UP CALENDAR MOBISCROLL
mobiscroll.setOptions({
    locale: mobiscroll.localeEn,
    theme: "material",
    themeVariant: "light",
});

// ADD EVENT CELL DAY
document.getElementById("luuCaCellDay").addEventListener("click", function () {
    // Click vào check box trước
    var checkboxes = document.querySelectorAll(".custom-checkbox");
    //
    var events = [];
    var selectComboboxDateChoise = document.getElementById("dateSelect");
    checkboxes.forEach(function (checkbox) {
        // Kiểm tra xem checkbox có được chọn (checked) hay không
        var checkboxButton = checkbox.parentElement;

        if (checkbox.checked) {
            var inputElement = checkboxButton.querySelector("input[type='number']");
            // Lấy content bên trong thẻ Strong
            var caNameElement = checkboxButton.querySelector("strong");

            // Lấy thông tin trong <span class="checkbox-label">
            var strongElements = checkboxButton.querySelectorAll("strong");
            var spanElements = checkboxButton.querySelectorAll("span");

            // Lấy các thông tin cụ thể
            var ca = strongElements[0].textContent; // Giá trị của "Ca 5:"
            var thoiGian = spanElements[1].textContent; // Giá trị của "07:00 - 09:00"
            var giaCa = spanElements[2].textContent; // Giá

            // Sử dụng phương thức split() để tách chuỗi thành một mảng dựa trên dấu cách và gạch ngang
            var parts = thoiGian.split(" - ");

            // Lấy phần tử đầu tiên của mảng (07:00) và phần tử thứ hai của mảng (09:00)
            var batDau = parts[0];
            var ketThuc = parts[1];

            //
            if (caNameElement) {
                // Lấy nội dung của phần tử `<strong>` (trong trường hợp này, "Ca 6")
                var caName = caNameElement.textContent.split(":")[0];
                // Lấy ra ngày chọn trong mobiscroll modal
                var selectedDateMultiInModal =
                    $("#demo-multi-day").mobiscroll("getVal");
                var selectedDateRangeInModal = $("#demo-range-selection").mobiscroll(
                    "getVal"
                );
                var selectedWeekInModal = $("#demo-week-select").mobiscroll("getVal");

                //
                if (selectComboboxDateChoise.value == 0) {
                    selectedDateMultiInModal.forEach(function (date) {
                        var day = date.getDate();
                        var month = date.getMonth();
                        var year = date.getFullYear();
                        // Tạo đối tượng sự kiện cho ngày này
                        var event = {
                            title: caName,
                            start: new Date(
                                year,
                                month,
                                day,
                                parseInt(batDau.split(":")[0]), // Giờ bắt đầu
                                parseInt(batDau.split(":")[1]) // Phút bắt đầu
                            ),
                            end: new Date(
                                year,
                                month,
                                day,
                                parseInt(ketThuc.split(":")[0]), // Giờ kết thúc
                                parseInt(ketThuc.split(":")[1]) // Phút kết thúc
                            ),
                            allDay: false, // Đặt thành false nếu bạn muốn sự kiện có thời gian cụ thể
                            color: "rgba(169, 211, 242, 0.9)", // Màu nền cho sự kiện
                        };
                        var element = parseInt(inputElement.value);
                        console.log(element);
                        for (let i = 0; i < element; i++) {
                            events.push(event);
                        }
                        closeModal1();
                    });
                } else if (selectComboboxDateChoise.value == 1) {
                    selectedDateRangeInModal.forEach(function (date) {
                        var day = date.getDate();
                        var month = date.getMonth();
                        var year = date.getFullYear();

                        // Tạo đối tượng sự kiện cho ngày này
                        var event = {
                            title: caName,
                            start: new Date(
                                year,
                                month,
                                day,
                                batDau.split(":")[0],
                                batDau.split(":")[1]
                            ), // Thêm giờ bắt đầu và phút bắt đầu
                            end: new Date(
                                year,
                                month,
                                day,
                                ketThuc.split(":")[0],
                                ketThuc.split(":")[1]
                            ), // Thêm giờ kết thúc và phút kết thúc
                            allDay: false, // Đặt thành false nếu bạn muốn sự kiện có thời gian cụ thể
                        };
                        inputElement.forEach(function () {
                            events.push(event);
                        });
                    });
                } else if (selectComboboxDateChoise.value == 2) {
                    selectedWeekInModal.forEach(function (date) {
                        var day = date.getDate();
                        var month = date.getMonth();
                        var year = date.getFullYear();

                        // Tạo đối tượng sự kiện cho ngày này
                        var event = {
                            title: caName,
                            start: new Date(
                                year,
                                month,
                                day,
                                batDau.split(":")[0],
                                batDau.split(":")[1]
                            ), // Thêm giờ bắt đầu và phút bắt đầu
                            end: new Date(
                                year,
                                month,
                                day,
                                ketThuc.split(":")[0],
                                ketThuc.split(":")[1]
                            ), // Thêm giờ kết thúc và phút kết thúc
                            allDay: false, // Đặt thành false nếu bạn muốn sự kiện có thời gian cụ thể
                        };
                        inputElement.forEach(function () {
                            events.push(event);
                        });
                    });
                }
            }
        }
    });
    calendar.addEventSource(events);
    // Thêm sự kiện vào FullCalendar
    calendar.render(); // Cập nhật lịch sau khi thêm sự kiện
});

$(function () {
    // MULTI
    $("#demo-multi-day")
        .mobiscroll()
        .datepicker({
            controls: ["calendar"],
            display: "inline",
            selectMultiple: true,
        });

    // COUNTER
    $("#demo-range-selection")
        .mobiscroll()
        .datepicker({
            controls: ["calendar"], // More info about controls: https://docs.mobiscroll.com/5-27-1/calendar#opt-controls
            display: "inline", // Specify display mode like: display: 'bottom' or omit setting to use default
            rangeSelectMode: "wizard",
            select: "range", // More info about select: https://docs.mobiscroll.com/5-27-1/calendar#opt-select
            showRangeLabels: true,
        });

    // SINGLE
    $("#demo-single-select-date")
        .mobiscroll()
        .datepicker({
            controls: ["calendar"],
            display: "inline",
            selectMultiple: false,
        });

    //  WEEK
    $("#demo-week-select")
        .mobiscroll()
        .datepicker({
            controls: ["calendar"],
            select: "preset-range",
            display: "inline",
            // firstSelectDay: 1,
            selectSize: 14,
        });
});

$(function () {
    $("#demo-single-select-date")
        .mobiscroll()
        .datepicker({
            controls: ["calendar"],
            display: "inline",
            locale: mobiscroll.localeVi,
            onChange: function (event, inst) {
                var selectedDate = inst.getVal();
                var year = selectedDate.getFullYear();
                var month = selectedDate.getMonth(); // Tháng bắt đầu từ 0
                var day = selectedDate.getDate();

                var newDate = new Date(year, month, day);
                //chọn ngày trên lịch nhỏ Đặt ngày trên FullCalendar
                calendar.gotoDate(newDate);
                calendar.select(newDate);
            },
        });
    mobiscrollInstanceSingleSelectDate = $("#demo-single-select-date").mobiscroll(
        "getInst"
    );
    // Từ Ful Calendar sang modal
    mobiscrollInstanceMultiSelectDate =
        $("#demo-multi-day").mobiscroll("getInst");
    mobiscrollInstanceSelectWeek = $("#demo-week-select").mobiscroll("getInst");
    mobiscrollInstanceSelectRangeDay = $("#demo-range-selection").mobiscroll(
        "getInst"
    );
});

// Hàm chọn từ Fulcalendar -> lịch bé
function handleCalendarDateClick(selectedDate) {
    mobiscrollInstanceSingleSelectDate.setVal(selectedDate);
    mobiscrollInstanceMultiSelectDate.setVal(selectedDate);
    mobiscrollInstanceSelectWeek.setVal(selectedDate);
    mobiscrollInstanceSelectRangeDay.setVal(selectedDate);
}

// LAMNP 14/10/2023
// SỬ LÝ SỰ KIỆN SHOW LỊCH KHI CUS CHỌN ĐẶT LỊCH THEO NGÀY, TUẦN, THÁNG
// Lấy tham chiếu đến select và các div cần ẩn/hiển thị
var dateSelect = document.getElementById("dateSelect");
// Select type choise clendar
var demoMultiDay = document.getElementById("demo-multi-day");
var demoRange = document.getElementById("demo-range-selection");
var demoWeek = document.getElementById("demo-week-select");

// Ẩn mặc định hai calendar
demoRange.style.display = "none";
demoWeek.style.display = "none";

// Gắn sự kiện change cho select
dateSelect.addEventListener("change", function () {
    // Lấy giá trị được chọn trong select
    var selectedValue = dateSelect.value;

    // Ẩn tất cả các div trước
    demoMultiDay.style.display = "none";
    demoRange.style.display = "none";
    demoWeek.style.display = "none";

    // Hiển thị div tương ứng dựa trên giá trị được chọn
    if (selectedValue === "0") {
        demoMultiDay.style.display = "block";
    } else if (selectedValue === "1") {
        demoRange.style.display = "block";
    } else if (selectedValue === "2") {
        demoWeek.style.display = "block";
    }
});

// LAMNP 14/10/2023
// hàm dùng chung đóng modal
function closeModal1() {
    var modal = document.getElementById("myModal1");
    modal.classList.remove("visible");
}
